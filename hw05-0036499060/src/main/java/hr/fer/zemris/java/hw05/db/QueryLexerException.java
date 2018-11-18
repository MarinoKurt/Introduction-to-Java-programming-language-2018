package hr.fer.zemris.java.hw05.db;

/**
 * Custom exception for the QueryLexer.
 * 
 * @author MarinoK
 */
public class QueryLexerException extends RuntimeException {

	/**
	 * Default serial version for the query lexer exception.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for the query lexer exception.
	 */
	public QueryLexerException() {
		super();
	}

	/**
	 * Constructor for the query lexer exception that takes a message.
	 * 
	 * @param message
	 *            to give to the user
	 */
	public QueryLexerException(String message) {
		super(message);
	}

}
