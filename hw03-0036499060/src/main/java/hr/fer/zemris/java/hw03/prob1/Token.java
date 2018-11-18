package hr.fer.zemris.java.hw03.prob1;

/**
 * Represents one token.
 * 
 * @author MarinoK
 */
public class Token {

	/**
	 * Type of this token.
	 */
	private TokenType type;

	/**
	 * Value of this token.
	 */
	private Object value;

	/**
	 * Constructor for the token.
	 * 
	 * @param type of token
	 * @param value of token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter for the token value.
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Getter for the token type.
	 * 
	 * @return type
	 */
	public TokenType getType() {
		return type;
	}
}
