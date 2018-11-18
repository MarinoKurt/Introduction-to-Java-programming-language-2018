package hr.fer.zemris.java.webserver;

/**
 * Functional interface for the web worker to implement.
 */
public interface IWebWorker {

	/**
	 * Method used to process the web workers task on the given context.
	 * 
	 * @param context
	 *            to work with
	 * @throws Exception
	 *             if error occurs
	 */
	public void processRequest(RequestContext context) throws Exception;
}