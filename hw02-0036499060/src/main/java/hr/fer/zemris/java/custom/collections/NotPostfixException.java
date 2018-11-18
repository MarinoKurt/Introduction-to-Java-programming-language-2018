package hr.fer.zemris.java.custom.collections;

/**
 * Custom exception for the case when the input string doesn't meet the postfix
 * notation.
 * 
 * @author MarinoK
 *
 */
public class NotPostfixException extends IllegalArgumentException {

	/**
	 * Automatically generated serial version ID for this exception.
	 */
	private static final long serialVersionUID = -2716922156803990872L;

	/**
	 * Default constructor.
	 */
	public NotPostfixException() {
		super("Bad input. Expected a integer postfix expression immediately surrounded by quotation marks, such as: \"8 2 -\".");
	}

	/**
	 * Constructor that takes a message for the user.
	 * 
	 * @param message to be output
	 */
	public NotPostfixException(String message) {
		super(message);
	}

	/**
	 * Constructor that takes a instance of Throwable and a message for the user.
	 * 
	 * @param message to be output
	 * @param badInput exception that is thrown before this one
	 */
	public NotPostfixException(String message, Throwable badInput) {
		super(message, badInput);
	}

	/**
	 * Constructor that takes a instance of Throwable.
	 * 
	 * @param badInput exception that is thrown before this one
	 */
	public NotPostfixException(Throwable badInput) {
		super(badInput);
	}

}
