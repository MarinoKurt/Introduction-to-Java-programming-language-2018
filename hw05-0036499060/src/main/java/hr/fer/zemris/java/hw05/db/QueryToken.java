package hr.fer.zemris.java.hw05.db;

/**
 * Token used for communication between QueryLexer and QueryParser.
 * 
 * @author MarinoK
 */
public class QueryToken {

	/**
	 * Type of the token.
	 */
	private QueryTokenType type;

	/**
	 * Value of the token.
	 */
	private String value;

	/**
	 * Constructor for the token.
	 * 
	 * @param type
	 *            of token
	 * @param value
	 *            of token
	 */
	public QueryToken(QueryTokenType type, String value) {
		this.value = value;
		this.type = type;
	}

	/**
	 * Getter for the Query token value.
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Getter for the Query token type.
	 * 
	 * @return type
	 */
	public QueryTokenType getType() {
		return type;
	}
}
