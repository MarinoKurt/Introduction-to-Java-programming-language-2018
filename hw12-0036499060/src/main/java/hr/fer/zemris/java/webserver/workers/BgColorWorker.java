package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker who sets the background color.
 * 
 * @author MarinoK
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		synchronized (context) {
			String color = context.getParameter("bgcolor");
			Color bgc = Color.decode("#" + color);

			boolean updated = false;

			if (bgc == null) {
				updated = false;
			} else {
				context.setPersistentParameter("bgcolor", color);
				updated = true;
			}

			StringBuilder sb = new StringBuilder();

			sb.append("Color is ");
			if (!updated) {
				sb.append("not ");
			}
			sb.append("updated.<br>");

			sb.append("<a href=\"/index2.html\">Home</a><br>");

			context.write(sb.toString());
		}
	}

}
