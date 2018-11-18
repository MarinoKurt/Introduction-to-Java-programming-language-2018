package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Custom exception thrown whenever the lexer encounters an unsolvable issue with the
 * input.
 * 
 * @author MarinoK
 */
public class LexerException extends RuntimeException {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructor that takes a message.
	 * 
	 * @param string
	 *            to be output to the user
	 */
	public LexerException(String string) {
		super(string);
	}
}
