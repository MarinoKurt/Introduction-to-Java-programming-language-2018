package hr.fer.zemris.java.webserver;

/**
 * Functional interface for the dispatcher.
 */
public interface IDispatcher {
	
	/**
	 * Method used to dispatch the request.
	 * 
	 * @param urlPath
	 *            of the requested file
	 * @throws Exception
	 *             if error occurs
	 */
	void dispatchRequest(String urlPath) throws Exception;
}