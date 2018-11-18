package hr.fer.zemris.lsystems.impl;

/**
 * Custom exception for the Lindermayer System. Thrown when invalid input is
 * given.
 * 
 * @author MarinoK
 *
 */
public class LSystemException extends RuntimeException {

	/**
	 * Default serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for the Lindermayer System exception.
	 */
	public LSystemException() {
		super();
	}

	/**
	 * Constructor for the Lindermayer System exception that takes a message.
	 * 
	 * @param message
	 *            for the user
	 */
	public LSystemException(String message) {
		super(message);
	}

}
