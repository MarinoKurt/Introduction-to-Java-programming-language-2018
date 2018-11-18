package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker for the home page. Uses the private home script.
 * 
 * @author MarinoK
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		synchronized (context) {
			String backgroundColor = context.getPersistentParameter("bgcolor");
			if (backgroundColor == null) {
				backgroundColor = "7f7f7f";
			}
			context.setTemporaryParameter("background", backgroundColor);

			context.getDispatcher().dispatchRequest("/private/home.smscr");
		}
	}

}
