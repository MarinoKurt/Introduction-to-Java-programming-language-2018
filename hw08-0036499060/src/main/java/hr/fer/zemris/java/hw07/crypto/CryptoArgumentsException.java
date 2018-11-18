package hr.fer.zemris.java.hw07.crypto;

/**
 * Custom exception used for communicating with the user regarding arguments for
 * the Crypto program.
 * 
 * @author MarinoK
 */
public class CryptoArgumentsException extends RuntimeException {

	/** Default serial version ID for the exception. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor, delivers a default message to the user.
	 */
	public CryptoArgumentsException() {
		this("Expected arguments: checksha/encrypt/decrypt + filepath + optional destination path.");
	}

	/**
	 * Constructor that takes a message for the user.
	 * 
	 * @param message
	 *            for the user
	 */
	public CryptoArgumentsException(String message) {
		super(message);
	}
}
