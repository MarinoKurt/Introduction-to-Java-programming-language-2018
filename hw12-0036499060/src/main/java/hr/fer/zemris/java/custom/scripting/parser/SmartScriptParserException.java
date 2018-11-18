package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Custom exception thrown whenever the parser encounters an unsolvable issue
 * with the input.
 * 
 * @author MarinoK
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public SmartScriptParserException() {
	}

	/**
	 * Constructor that takes a message.
	 * 
	 * @param string
	 *            to be output to the user
	 */
	public SmartScriptParserException(String string) {
		super(string);
	}
}
