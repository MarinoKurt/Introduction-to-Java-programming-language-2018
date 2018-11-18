package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker which displays the given parameters.
 * 
 * @author MarinoK
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		synchronized (context) {

			StringBuilder sb = new StringBuilder();

			String head = "<html>\r\n" + "<head>\r\n" + "<title>Table of parameters</title>\r\n" + "</head>\r\n"
					+ "<body>\r\n" + "<h1>Parameters</h1>\r\n" + "<p>Given parameters:</p>\r\n" + "<table>\r\n"
					+ "<thead>\r\n" + "<tr><th>Name</th><th>Value</th></tr>\r\n" + "</thead>\r\n" + "<tbody>\r\n";
			sb.append(head);

			String tableRowFormat = "<tr><td>%s</td><td>%s</td></tr>\r\n";

			for (String pn : context.getParameterNames()) {
				String tableRow = String.format(tableRowFormat, pn, context.getParameter(pn));
				sb.append(tableRow);
			}

			String tail = "</tbody>\r\n" + "</table>\r\n" + "</body>\r\n" + "</html>";

			sb.append(tail);

			context.setMimeType("text/html");
			context.write(sb.toString());

		}
	}

}
