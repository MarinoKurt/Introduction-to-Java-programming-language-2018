package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeration describing the state of the lexer.
 * 
 * @author Marino
 */
public enum LexerState {
	/**
	 * In this state, lexer will sort the input to tokens intuitively, with the
	 * exception of characters after the escape character \, which will always be a
	 * word.
	 */
	BASIC,

	/**
	 * In this state, lexer will group any characters separated by white spaces,
	 * until a # character changes it's state.
	 */
	EXTENDED
}
