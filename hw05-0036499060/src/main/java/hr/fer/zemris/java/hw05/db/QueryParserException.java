package hr.fer.zemris.java.hw05.db;

/**
 * Custom exception for the QueryParser.
 * 
 * @author MarinoK
 */
public class QueryParserException extends RuntimeException {

	/**
	 * Default serial version for the query parser exception.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for the query parser exception.
	 */
	public QueryParserException() {
		super();
	}

	/**
	 * Constructor for the query parser exception that takes a message.
	 * 
	 * @param message
	 *            to give to the user
	 */
	public QueryParserException(String message) {
		super(message);
	}

}
