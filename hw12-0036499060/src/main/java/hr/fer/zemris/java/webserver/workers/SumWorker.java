package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that calculates the sum of the two given integer parameters. If the
 * parameters are wrongly given or not given at all, assumes a=1, b=2.
 * 
 * @author MarinoK
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		synchronized (context) {
			int a = 1;
			int b = 2;

			String aStr = context.getParameter("a");
			String bStr = context.getParameter("b");

			try {
				a = Integer.parseInt(aStr);
			} catch (NumberFormatException num) {
				a = 1;
			}

			try {
				b = Integer.parseInt(bStr);
			} catch (NumberFormatException num) {
				b = 2;
			}

			if (aStr == null) {
				a = 1;
			}
			if (bStr == null) {
				b = 2;
			}

			String sum = String.valueOf(a + b);
			context.setTemporaryParameter("zbroj", sum);
			context.setTemporaryParameter("a", String.valueOf(a));
			context.setTemporaryParameter("b", String.valueOf(b));

			context.getDispatcher().dispatchRequest("/private/calc.smscr");
		}
	}
}
