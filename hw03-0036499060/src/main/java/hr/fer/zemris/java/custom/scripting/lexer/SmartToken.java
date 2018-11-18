package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Represents one token that lexer can provide.
 * 
 * @author MarinoK
 *
 */
public class SmartToken {

	/**
	 * Type of the token.
	 */
	private SmartTokenType type;

	/**
	 * Value of the token.
	 */
	private Object value;

	/**
	 * Constructor for the token.
	 * 
	 * @param type of token
	 * @param value of token
	 */
	public SmartToken(SmartTokenType type, Object value) {
		this.value = value;
		this.type = type;
	}

	/**
	 * Getter for the smart token value.
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Getter for the smart token type.
	 * 
	 * @return type
	 */
	public SmartTokenType getType() {
		return type;
	}

	/**
	 * ToString method of the token.
	 * 
	 * @return the token type and value in a string
	 */
	@Override
	public String toString() {
		if (value != null) {
			return value.toString() + ", type :" + type;
		} else {
			return "type : " + type.toString();
		}
	}
}
