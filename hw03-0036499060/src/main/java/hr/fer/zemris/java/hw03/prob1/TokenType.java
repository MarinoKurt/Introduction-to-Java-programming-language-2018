package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeration of the token types that lexer can provide.
 * 
 * @author MarinoK
 *
 */
public enum TokenType {

	/**
	 * The very last token of the reading.
	 */
	EOF,

	/**
	 * Contains letters, numbers or symbols, depending on the current lexer state.
	 */
	WORD,

	/**
	 * Number displayable in long data type.
	 */
	NUMBER,

	/**
	 * All characters that aren't letters, numbers, or end of file mark.
	 */
	SYMBOL

}
