package hr.fer.zemris.java.p12.dao;

/**
 * Custom exception for the DAO layer.
 */
public class DAOException extends RuntimeException {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the DAO Exception.
	 * 
	 * @param message
	 *            for the user
	 * @param cause
	 *            of the exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor for the DAO Exception.
	 * 
	 * @param message
	 *            for the user
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor for the DAO Exception.
	 * 
	 * @param cause
	 *            of the exception
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}