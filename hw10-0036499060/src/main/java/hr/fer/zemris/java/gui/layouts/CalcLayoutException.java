package hr.fer.zemris.java.gui.layouts;

/**
 * Custom exception for the calculator layout.
 * 
 * @author MarinoK
 */
public class CalcLayoutException extends RuntimeException {

	/** Default serial version id. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for the exception.
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Constructor which takes the message for the user.
	 * 
	 * @param message
	 *            for the user
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

}
