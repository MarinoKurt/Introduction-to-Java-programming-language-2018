package hr.fer.zemris.java.custom.collections;

/**
 * Custom exception for the case when the stack is empty,
 * but the user wants to fetch an element from the stack.
 * 
 * @author MarinoK
 *
 */
public class EmptyStackException extends RuntimeException {
	
	
	/**
	 * Automatically generated serial version ID for this exception.
	 */
	private static final long serialVersionUID = -1192700721350791522L;

	/**
	 * Constructor that takes a string message.
	 * 
	 * @param message that will be printed if the exception is thrown
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * Default constructor.
	 */
	public EmptyStackException() {
		super();
	}
}
