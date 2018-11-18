package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Representation of the request context.
 * 
 * @author MarinoK
 */
public class RequestContext {

	/** Output stream of the request context. */
	private OutputStream outputStream;
	/** Encoding of the request context. */
	private Charset charset;

	/** Encoding of the request context. */
	private String encoding;
	/** Status code of the request context. */
	private int statusCode;
	/** Status text of the request context. */
	private String statusText;
	/** Mime type of the request context. */
	private String mimeType;

	/** Map of parameters. */
	private Map<String, String> parameters;
	/** Map of temporary parameters. */
	private Map<String, String> temporaryParameters;
	/** Map of persistent parameters. */
	private Map<String, String> persistentParameters;
	/** List of output cookies. */
	private List<RCCookie> outputCookies;

	/** Dispatcher. */
	private IDispatcher dispatcher;

	/** Boolean marking is the header already generated. */
	private boolean headerGenerated;

	/**
	 * Constructor for the request context. Sets header values to their defaults.
	 * 
	 * @param outputStream
	 *            to write to
	 * @param parameters
	 *            map of parameters
	 * @param persistentParameters
	 *            map of persistent parameters
	 * @param outputCookies
	 *            list of cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {

		Objects.requireNonNull(outputStream);
		this.outputStream = outputStream;

		this.encoding = "UTF-8";
		this.statusCode = 200;
		this.statusText = "OK";
		this.mimeType = "text/html";
		this.headerGenerated = false;
		this.temporaryParameters = new HashMap<>();

		if (parameters == null) {
			this.parameters = new HashMap<>();
		} else {
			this.parameters = parameters;
		}

		if (persistentParameters == null) {
			this.persistentParameters = new HashMap<>();
		} else {
			this.persistentParameters = persistentParameters;
		}

		if (outputCookies == null) {
			this.outputCookies = new ArrayList<>();
		} else {
			this.outputCookies = outputCookies;
		}

	}

	/**
	 * Constructor for the request context that takes two additional parameters.
	 * 
	 * @param outputStream
	 *            to write to
	 * @param parameters
	 *            map of parameters
	 * @param persistentParameters
	 *            map of persistent parameters
	 * @param outputCookies
	 *            list of cookies
	 * @param temporaryParameters
	 *            map of temporary parameters
	 * @param dispatcher
	 *            dispatcher
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.dispatcher = dispatcher;
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Method to write the given data to the output stream.
	 * 
	 * @param data
	 *            to be written
	 * @return reference to this request context
	 * @throws IOException
	 *             if a problem with the stream encounters
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			writeHeader();
		}
		outputStream.write(data);
		outputStream.flush();
		return this;
	}

	/**
	 * Method to write the given text to the output stream.
	 * 
	 * @param text
	 *            to be written
	 * @return reference to this request context
	 * @throws IOException
	 *             if a problem with the stream encounters
	 */
	public RequestContext write(String text) throws IOException {
		charset = Charset.forName(encoding);
		return write(text.getBytes(charset));
	}

	/**
	 * Retrieves the dispatcher.
	 * 
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Method that retrieves value from parameters map (or null if no association
	 * exists).
	 * 
	 * @param name
	 *            of the parameter
	 * @return value joined with the parameter
	 */
	public String getParameter(String name) {
		return this.parameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in parameters map as an
	 * unmodifiable set.
	 * 
	 * @return unmodifiable set of names
	 */
	public Set<String> getParameterNames() {
		Map<String, String> parametersCopy = Collections.unmodifiableMap(parameters);
		return parametersCopy.keySet();
	}

	/**
	 * Method that retrieves value from persistentParameters map (or null if no
	 * association exists)
	 * 
	 * @param name
	 *            of the parameter
	 * @return value joined with the parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in persistent parameters map as
	 * an unmodifiable set.
	 * 
	 * @return unmodifiable set of names
	 */
	public Set<String> getPersistentParameterNames() {
		Map<String, String> persistentParametersCopy = Collections.unmodifiableMap(persistentParameters);
		return persistentParametersCopy.keySet();
	}

	/**
	 * Method that stores a value to persistentParameters map.
	 * 
	 * @param name
	 *            key
	 * @param value
	 *            value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Method that removes a value from persistentParameters map.
	 * 
	 * @param name
	 *            of the parameter to be removed
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	};

	/**
	 * Method that retrieves value from temporaryParameters map (or null if no
	 * association exists).
	 * 
	 * @param name
	 *            of the temporary parameter
	 * @return value of the temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	};

	/**
	 * Method that retrieves names of all parameters in temporary parameters map.
	 * 
	 * @return unmodifiable set of names
	 */
	public Set<String> getTemporaryParameterNames() {
		Map<String, String> temporaryParametersCopy = Collections.unmodifiableMap(temporaryParameters);
		return temporaryParametersCopy.keySet();
	}

	/**
	 * Method that stores a value to temporaryParameters map.
	 * 
	 * @param name
	 *            of the parameter
	 * @param value
	 *            of the parameter
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporaryParameters map:
	 * 
	 * @param name
	 *            of the parameter to be removed
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Method that adds a RCCookie to output cookies collection.
	 * 
	 * @param rcCookie
	 *            cookie to be added
	 */
	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}

	/**
	 * Method that removes a RCCookie from the output cookies collection.
	 * 
	 * @param rcCookie
	 *            cookie to be removed
	 */
	public void removeRCCookie(RCCookie rcCookie) {
		outputCookies.remove(rcCookie);
	}

	/**
	 * Setter for the encoding.
	 * 
	 * @param encoding
	 *            of the request context
	 * @throws RuntimeException
	 *             if the header is already generated
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) throw new RuntimeException("Cannot modify encoding after header is generated.");
		this.encoding = encoding;
	}

	/**
	 * Setter for the status code.
	 * 
	 * @param statusCode
	 *            of the request context
	 * @throws RuntimeException
	 *             if the header is already generated
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) throw new RuntimeException("Cannot modify status code after header is generated.");
		this.statusCode = statusCode;
	}

	/**
	 * Setter for the status text.
	 * 
	 * @param statusText
	 *            of the request context
	 * @throws RuntimeException
	 *             if the header is already generated
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) throw new RuntimeException("Cannot modify status text after header is generated.");
		this.statusText = statusText;
	}

	/**
	 * Setter for the mime type.
	 * 
	 * @param mimeType
	 *            of the request context
	 * @throws RuntimeException
	 *             if the header is already generated
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) throw new RuntimeException("Cannot modify mime type after header is generated.");
		this.mimeType = mimeType;
	}

	/**
	 * Represents a cookie.
	 */
	public static class RCCookie {

		/** Name of the cookie. */
		private String name;
		/** Value of the cookie. */
		private String value;
		/** Domain of the cookie. */
		private String domain;
		/** Path of the cookie. */
		private String path;
		/** Maximum age of the cookie. */
		private Integer maxAge;

		/**
		 * Constructor for the RCCookie.
		 * 
		 * @param name
		 *            of the cookie
		 * @param value
		 *            of the cookie
		 * @param maxAge
		 *            of the cookie
		 * @param domain
		 *            of the cookie
		 * @param path
		 *            of the cookie
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * @return name of the cookie
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return value of the cookie
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @return domain of the cookie
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * @return path of the cookie
		 */
		public String getPath() {
			return path;
		}

		/**
		 * @return maximum age of the cookie
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

	}

	/**
	 * Auxiliary method used to write the header.
	 * 
	 * @throws IOException
	 *             if a problem with the stream encounters
	 */
	private void writeHeader() throws IOException {

		StringBuilder headerSB = new StringBuilder();

		headerSB.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");

		headerSB.append("Content-Type: " + mimeType);
		if (mimeType.startsWith("text/")) {
			headerSB.append("; charset=" + encoding);
		}
		headerSB.append("\r\n");
		if (!outputCookies.isEmpty()) {
			for (RCCookie cookie : outputCookies) {
				headerSB.append("Set-Cookie: ");
				if (cookie.getName() != null && cookie.getValue() != null) {
					headerSB.append(String.format("%s=\"%s\"; ", cookie.getName(), cookie.getValue()));
				}
				if (cookie.getDomain() != null) {
					headerSB.append(String.format("%s=%s; ", "Domain", cookie.getDomain()));
				}
				if (cookie.getPath() != null) {
					headerSB.append(String.format("%s=%s; ", "Path", cookie.getPath()));
				}
				if (cookie.getMaxAge() != null) {
					headerSB.append(String.format("%s=%s; ", "Max-Age", cookie.getMaxAge()));
				}
				headerSB.append("HttpOnly");
				headerSB.append("\r\n");
			}
		}
		headerSB.append("\r\n");

		outputStream.write(headerSB.toString().getBytes(StandardCharsets.ISO_8859_1));

		headerGenerated = true;
	}

}
