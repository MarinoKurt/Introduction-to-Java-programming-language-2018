package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Types of tokens that SmartScriptLexer can provide.
 * 
 * @author MarinoK
 *
 */
public enum SmartTokenType {

	/**
	 * Token type that lexer sends when it comes to the end of the file.
	 */
	EOF,

	/**
	 * Token type for the double constant.
	 */
	CONSTANT_DOUBLE,

	/**
	 * Token type for the integer constant.
	 */
	CONSTANT_INTEGER,

	/**
	 * Token type for the function expression, can not contain anything but letters,
	 * digits, or underscores. Begins with a letter.
	 */
	FUNCTION,

	/**
	 * Token type for the operator, can be + (plus), - (minus), * (multiplication), / (division), ^ (power).
	 */
	OPERATOR,

	/**
	 * Token type for the string.
	 */
	STRING,

	/**
	 * Token type for the variable expression, can not contain anything but letters,
	 * digits, or underscores. Begins with a letter.
	 */
	VARIABLE,

	/**
	 * Token type for the tag entry, "{$".
	 */
	TAG_ENTRY,

	/**
	 * Token type for the tag entry, "$}".
	 */
	TAG_EXIT
}
