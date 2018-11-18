package hr.fer.zemris.java.hw07.shell.parser;

/**
 * Modes for the parser.
 * 
 * @author MarinoK
 */
public enum ParserMode {

	/** Used for handling morelines symbol, forming commands. */
	LINE_MODE,

	/** Used for handling command arguments. */
	ARGUMENT_MODE
}
