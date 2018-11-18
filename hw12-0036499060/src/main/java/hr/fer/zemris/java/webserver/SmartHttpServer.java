package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Implementation of HTTP Server which uses multiple threads to serve clients.
 * Server uses the configuration given in properties files in the project.
 * 
 * @author MarinoK
 */
public class SmartHttpServer {

	/** Server IP address. */
	@SuppressWarnings("unused")
	private String address;

	/** Server domain name. */
	private String domainName;

	/** Port on which the server listens for requests. */
	private int port;

	/** Number of worker threads on this server. */
	private int workerThreads;

	/** How long will this server remember client cookies. */
	private int sessionTimeout;

	/** Map of mime types from properties file. */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/** Main server thread. */
	private ServerThread serverThread;

	/** Pool of worker threads. */
	private ExecutorService threadPool;

	/** Location of the files to serve the client. */
	private Path documentRoot;

	/** Map of IWebWorkers. */
	private Map<String, IWebWorker> workersMap = new HashMap<>();

	/** Maps the session IDs to their session map entries. */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();

	/** Random used for generating session IDs. */
	private Random sessionRandom = new Random();

	/**
	 * Main method runs when the program is run.
	 * 
	 * @param args
	 *            expected properties file path
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected properties file path.");
			return;
		}

		new SmartHttpServer(args[0]);
	}

	/**
	 * Constructor for the Smart HTTP Server.
	 * 
	 * @param configFileName
	 *            path to the main configuration file
	 */
	public SmartHttpServer(String configFileName) {
		try {
			readProperties(configFileName);
		} catch (IOException file) {
			System.err.println("Problem reading properties.");
			System.exit(1);
		}
		start();
		initializeCookieCollector();
	}

	/**
	 * Method used to initialize the thread which erases expired cookies.
	 */
	private void initializeCookieCollector() {
		Thread cookieCollector = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
				}
				for (Map.Entry<String, SmartHttpServer.SessionMapEntry> entry : sessions.entrySet()) {
					SessionMapEntry sme = entry.getValue();
					if (sme.validUntil < (System.currentTimeMillis() / 1000)) {
						sessions.remove(entry.getKey());
					}
				}
			}
		});
		cookieCollector.setDaemon(true);
		cookieCollector.start();
	}

	/**
	 * Method to start the server thread and the thread pool.
	 */
	protected synchronized void start() {

		if (serverThread == null) {
			serverThread = new ServerThread();
		}
		if (!serverThread.isAlive()) {
			serverThread.start();
		}

		if (threadPool == null || threadPool.isShutdown()) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}
	}

	/**
	 * Method to stop the server thread and the thread pool.
	 */
	protected synchronized void stop() {
		threadPool.shutdown();
		serverThread.shutdown();
	}

	/**
	 * Thread used to delegate the client work to Client Workers.
	 */
	protected class ServerThread extends Thread {

		/** False until the thread is shutdown. */
		private boolean stop = false;

		@Override
		public void run() {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress) null, port));
				// serverSocket.setSoTimeout(5000);
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (!stop) {
				Socket client = null;
				try {
					client = serverSocket.accept();
				} catch (IOException e) {
					continue;
				}
				ClientWorker cw = new ClientWorker(client);
				threadPool.submit(cw);
			}
		}

		/**
		 * Method used to shutdown the thread.
		 */
		public void shutdown() {
			stop = true;
		}
	}

	/**
	 * Represents the relevant set of data for each session.
	 */
	private static class SessionMapEntry {
		
		/** Session ID. */
		String sid;

		/** Session host. */
		String host;
		
		/** Time when the session entry expires. */
		long validUntil;
		
		/** Map of the parameters relevant for the session. */
		Map<String, String> map;

	}

	/**
	 * Runnable that serves the client.
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/** Client socket. */
		private Socket csocket;

		/** Input stream to read from. */
		private PushbackInputStream istream;

		/** Output stream to write to. */
		private OutputStream ostream;

		/** HTTP version. */
		private String version;

		/** HTTP method. */
		private String method;

		/** Requested host. */
		private String host;

		/** Given parameters. */
		private Map<String, String> params = new HashMap<String, String>();

		/** Given temporary parameters. */
		private Map<String, String> tempParams = new HashMap<String, String>();

		/** Given persistent parameters. */
		private Map<String, String> permParams = new HashMap<String, String>();

		/** List of cookies. */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		/** Session ID. */
		@SuppressWarnings("unused")
		private String sid;

		/** Context of the request. */
		private RequestContext context = null;

		/**
		 * Constructor for the client worker.
		 * 
		 * @param csocket
		 *            client socket
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = csocket;
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		@Override
		public void run() {
			List<String> request = null;
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				request = readRequest();

				if (!processRequest(request)) return;
			} catch (IOException e) {
			} finally {
				try {
					csocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}

		/**
		 * Method used to process the given request. Returns false if an error occurs.
		 * 
		 * @param request
		 *            of the client
		 * @return false if an error occurs
		 * @throws IOException
		 *             if there is a problem with communication
		 */
		private boolean processRequest(List<String> request) throws IOException {
			if (request == null) {
				sendError(400, "Invalid header");
				return false;
			}

			if (request.size() < 1) {
				sendError(400, "Invalid header");
				return false;
			}

			String requestedPath = parseFirstLine(request.get(0));

			method = method.toUpperCase();
			version = version.toUpperCase();
			if (!method.equals("GET") || !(version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))) {
				sendError(400, "Invalid method or version.");
				return false;
			}

			host = domainName;
			for (String line : request) {
				line = line.toLowerCase();
				if (line.startsWith("host: ")) {
					host = line.replace("host: ", "").trim();
					if (host.contains(":")) {
						host = host.split(":")[0];
						break;
					}
				}
			}

			checkSession(request);

			String[] rPathParts = requestedPath.split("\\?");
			if (rPathParts.length > 1) {
				String paramString = rPathParts[1];
				parseParameters(paramString);
			}

			String urlPath = rPathParts[0];
			try {
				internalDispatchRequest(urlPath, true);
			} catch (Exception e) {
				System.err.println("InternalDispatch problem");
				e.printStackTrace();
				return false;
			}
			return true;
		}

		/**
		 * Method used for working with cookies. Checks whether the cookies for the
		 * current session exist.
		 * 
		 * @param request
		 *            of the client
		 */
		private void checkSession(List<String> request) {
			synchronized (SmartHttpServer.this) {
				String sidCandidate = null;

				for (String line : request) {
					if (!line.toLowerCase().startsWith("cookie: ")) {
						continue;
					}
					line = line.toLowerCase().replace("cookie:", "");
					String[] cookies = line.trim().split(";");
					for (String c : cookies) {
						if (c.trim().toLowerCase().startsWith("sid")) {
							sidCandidate = c.split("=")[1].trim().toUpperCase();
							sidCandidate = sidCandidate.replace("\"", "");
						}
					}
				}
				boolean needForSid = false;
				boolean problem = false;
				if (sidCandidate == null) {
					problem = true;
					needForSid = true;
				}

				if (!problem) {
					SessionMapEntry oldEntry = sessions.get(sidCandidate);
					if (oldEntry == null) {
						needForSid = false;
						problem = true;
					} else if (oldEntry.validUntil < System.currentTimeMillis() / 1000) {
						problem = true;
					} else if (!oldEntry.host.toLowerCase().equals(host.toLowerCase())) {
						sessions.remove(oldEntry.sid);
						problem = true;
					}
				}
				SessionMapEntry sme = new SessionMapEntry();
				if (problem) {
					sme.host = host;
					sme.map = new ConcurrentHashMap<>();
					if (needForSid) {
						sme.sid = sidGenerator();
					} else {
						sme.sid = sidCandidate;
					}
					sme.validUntil = (System.currentTimeMillis() / 1000) + sessionTimeout;
					sessions.put(sme.sid, sme);
					try {
						outputCookies.add(new RCCookie("sid", sme.sid, null, host, "/"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					SessionMapEntry formerEntry = sessions.get(sidCandidate);
					long exparation = formerEntry.validUntil;
					exparation = (System.currentTimeMillis() / 1000) + sessionTimeout;
					formerEntry.validUntil = exparation;
					sme = formerEntry;
				}
				try {
					permParams = sme.map;
				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("permParams problem");
				}
			}
		}

		/**
		 * Method used to process dispatch requests with regard to their caller.
		 * 
		 * @param urlPath
		 *            of the requested
		 * @param directCall
		 *            if true, user can not access private files and scripts
		 * @throws Exception
		 *             if a problem arises
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {

			Path requestedFile = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath();
			documentRoot = documentRoot.toAbsolutePath();
			// if requestedPath is not below documentRoot, return response status 403
			// forbidden
			if (!requestedFile.startsWith(documentRoot)) {
				sendError(403, "Forbidden");
				return;
			}
			if (context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
			}

			if (requestedFile.toString().contains("private") && directCall) {
				sendError(404, "Not found");
				return;
			}

			for (Map.Entry<String, IWebWorker> entry : workersMap.entrySet()) {
				if (requestedFile.toString().endsWith(entry.getKey().substring(1))) {
					entry.getValue().processRequest(context);
					return;
				}
			}

			if (requestedFile.toString().contains("\\ext\\")) {
				String fqcn = "hr.fer.zemris.java.webserver.workers."
						+ requestedFile.toString().split("ext")[1].substring(1);
				((IWebWorker) workerProvider(fqcn)).processRequest(context);
				return;
			}

			if (!Files.isRegularFile(requestedFile) || !Files.isReadable(requestedFile)) {
				sendError(404, "File not found");
				return;
			}

			String[] fileByDot = requestedFile.toFile().getName().split("\\.");
			String extension = fileByDot[fileByDot.length - 1].toLowerCase();

			if (extension.equals("smscr")) {
				byte[] script = Files.readAllBytes(requestedFile);
				String scriptText = new String(script, StandardCharsets.UTF_8);
				context.setMimeType("text/plain");
				SmartScriptParser parser = new SmartScriptParser(scriptText);
				SmartScriptEngine engine = new SmartScriptEngine(parser.getDocumentNode(), context);
				engine.execute();
				return;
			}

			String mimeType = mimeTypes.get(extension);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			context.setMimeType(mimeType);
			context.setStatusCode(200);

			byte[] fileData;
			fileData = Files.readAllBytes(requestedFile);
			context.write(fileData);
		}

		/**
		 * Auxiliary method used to parse the first line of the request.
		 * 
		 * @param firstLine
		 *            of the request
		 * @return requested path
		 */
		private String parseFirstLine(String firstLine) {
			String[] bySpaces = firstLine.split("\\s+");
			this.method = bySpaces[0];
			this.version = bySpaces[2];
			String requestedPath = bySpaces[1];
			return requestedPath;
		}

		/**
		 * Method used to parse the parameters and fill the parameters map.
		 * 
		 * @param paramString
		 *            containing all the requested parameters
		 */
		private void parseParameters(String paramString) {

			String[] parameters = paramString.split("&");
			for (String p : parameters) {
				String[] pair = p.split("=");
				if (pair.length != 2) continue;
				params.put(pair[0], pair[1]);
			}
		}

		/**
		 * Auxiliary method used to send the error to the client.
		 * 
		 * @param statusCode
		 *            of the error
		 * @param statusText
		 *            of the error
		 * @throws IOException
		 *             if there is a problem with communication
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: SmartHTTPServer\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();
		}

		/**
		 * Auxiliary method used to read the request and return it as a list of strings.
		 * 
		 * @return list of lines of the request
		 * @throws IOException
		 *             if there is a problem with communication
		 */
		private List<String> readRequest() throws IOException {

			byte[] request = byteReader();
			if (request == null) {
				sendError(400, "Bad request");
				return null;
			}
			String allBytes = new String(request, StandardCharsets.ISO_8859_1); // TODO?
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : allBytes.split("\n")) {
				if (s.isEmpty()) break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (currentLine != null && !currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Byte reader taken from the example on the lectures.
		 * 
		 * @return array of bytes
		 * @throws IOException
		 *             if there is a problem with communication
		 */
		private byte[] byteReader() throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = istream.read();
				if (b == -1) return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10) state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

	}

	/**
	 * Auxiliary method to read the properties from the assigned files, and prepare
	 * the server for its launch.
	 * 
	 * @param configFileName
	 *            file name of the main configuration file
	 * @throws IOException
	 *             if there is a problem with communication
	 */
	private void readProperties(String configFileName) throws IOException {
		Properties serverProps = new Properties();
		serverProps.load(Files.newInputStream(Paths.get(configFileName)));

		address = (String) serverProps.get("server.address");
		domainName = (String) serverProps.get("server.domainName");
		port = Integer.valueOf((String) serverProps.get("server.port"));
		workerThreads = Integer.valueOf((String) serverProps.get("server.workerThreads"));
		sessionTimeout = Integer.valueOf((String) serverProps.get("session.timeout"));

		documentRoot = Paths.get((String) serverProps.get("server.documentRoot"));

		Path mimeConfig = Paths.get((String) serverProps.get("server.mimeConfig"));
		Properties mimeProps = new Properties();
		mimeProps.load(Files.newInputStream(mimeConfig));
		mimeProps.forEach((s1, s2) -> mimeTypes.put((String) s1, (String) s2));

		Path workersConfig = Paths.get((String) serverProps.get("server.workers"));
		List<String> workersInfo = Files.readAllLines(workersConfig);
		for (String wi : workersInfo) {
			if (wi.equals("")) continue;
			String[] pair = wi.split("=");
			IWebWorker iww = workerProvider(pair[1].trim());
			workersMap.put(pair[0].trim(), iww);
		}

	}

	/**
	 * Method used to return the web worker from its fully qualified class name.
	 * 
	 * @param fqcn
	 *            fully qualified class name of the worker
	 * @return class implementing the IWebWorker interface
	 */
	@SuppressWarnings("deprecation")
	private IWebWorker workerProvider(String fqcn) {
		Class<?> referenceToClass = null;
		try {
			referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Object newObject = null;
		try {
			newObject = referenceToClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return (IWebWorker) newObject;
	}

	/**
	 * Auxiliary method used to generate a random session ID.
	 * 
	 * @return session id
	 */
	private String sidGenerator() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWZXY";
		String sid = "";
		int i = 20;
		while (i > 0) {
			sid += alphabet.charAt(sessionRandom.nextInt(alphabet.length()));
			i--;
		}
		return sid;
	}

}
