package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Possible states for the SmartScriptLexer.
 * 
 * @author MarinoK
 */
public enum SmartScriptLexerState {

	/**
	 * In this state, lexer reads everything as a string, until it reaches an entry
	 * tag.
	 */
	TEXT,

	/**
	 * In this state, lexer treats text as described in the token enumeration, until
	 * it reaches the exit tag.
	 */
	TAG
}
