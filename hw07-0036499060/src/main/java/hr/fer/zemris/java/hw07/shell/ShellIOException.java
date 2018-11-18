package hr.fer.zemris.java.hw07.shell;

/**
 * Custom exception for MyShell.
 * 
 * @author MarinoK
 */
public class ShellIOException extends RuntimeException {

	/**
	 * Default serial version ID for ShellIOException.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for ShellIOException.
	 */
	public ShellIOException() {
	}

	/**
	 * Constructor for the exception that takes a message.
	 * 
	 * @param message
	 *            for the user
	 */
	public ShellIOException(String message) {
		super(message);
	}

}
