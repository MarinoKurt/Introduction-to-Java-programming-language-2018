package hr.fer.zemris.java.hw07.shell.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Syntactical and lexical analyzer combination for the shell. Default parser mode is argument mode.
 * 
 * @author MarinoK
 */
public class ShellParser {

	/**
	 * Boolean used to determine whether the command is divided in multiple lines.
	 */
	private boolean isMultiline;

	/** Mode of the parser. Individual modes explained in ParserMode enum. */
	private ParserMode mode;

	/**
	 * Array of characters used for filtering out the escape sequences in strings.
	 */
	private char[] data;

	/**
	 * Index of the first unprocessed character.
	 */
	private int currentIndex;

	/**
	 * Constructor for the shell parser, initializes the parser in given mode.
	 * 
	 * @param mode
	 *            for the parser
	 */
	public ShellParser(ParserMode mode) {
		this.isMultiline = false;
		this.mode = mode;
	}

	/**
	 * Default constructor for the shell parser, initializes the parser in its
	 * default mode - argument mode.
	 */
	public ShellParser() {
		this.mode = ParserMode.ARGUMENT_MODE;
	}

	/**
	 * Method used only in line parser mode. Used to separate the arguments for
	 * shell commands.
	 * 
	 * @param arguments
	 *            unseparated arguments
	 * @return list of arguments as strings
	 */
	public List<String> prepareArguments(String arguments) {
		if (mode.equals(ParserMode.LINE_MODE)) throw new UnsupportedOperationException();
		if (arguments == null) return null;

		List<String> prepared = new ArrayList<>();
		data = arguments.toCharArray();
		currentIndex = 0;

		while (currentIndex < data.length) {
			skipBlanks();
			if (data[currentIndex] == '"') {
				prepared.add(giveString());
			} else {
				prepared.add(giveUntil(' '));
			}
		}

		return prepared;
	}

	/**
	 * Auxiliary method used to go pass throughout the data more effectively. Idea
	 * from the "Programming in Java" book by professor Čupić.
	 * 
	 * @param end
	 *            character, first character that does not join the return string
	 * 
	 * @return string from the current index until the end character, excluded
	 */
	private String giveUntil(char end) {
		int startIndex = currentIndex;
		currentIndex++;
		while (currentIndex < data.length && data[currentIndex] != end) {
			currentIndex++;
		}
		int endIndex = currentIndex;
		String value = new String(data, startIndex, endIndex - startIndex);
		return value;
	}

	/**
	 * Method used to skip all the blanks in the input. Taken from the "Programming
	 * in Java" book by professor Čupić.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
	}

	/**
	 * Auxiliary method used to return a string, and support all the given
	 * restrictions and escaping.
	 * 
	 * @return string until the next unescaped symbol '"'
	 */
	private String giveString() {
		currentIndex++;
		int startIndex = currentIndex;
		while (currentIndex < data.length && data[currentIndex] != '"') {
			if (currentIndex < data.length - 1) {
				if (data[currentIndex] == '\\') {
					if (data[currentIndex + 1] == '"' || data[currentIndex + 1] == '\\') {
						currentIndex++;
					}
				}
			}
			currentIndex++;
		}
		int endIndex = currentIndex;
		String value = new String(data, startIndex, endIndex - startIndex);
		currentIndex++;
		return value;
	}

	/**
	 * Method used to get a line without the given symbol at the end.
	 * 
	 * @param readLine
	 *            line to be processed
	 * @param symbol
	 *            to be removed from the end of the line, if it is there
	 * @return line, without the last symbol, if it matches the given symbol
	 * 
	 * @throws UnsupportedOperationException
	 *             if the parser is in argument mode
	 */
	public String getCleanLine(String readLine, char symbol) {
		if (mode.equals(ParserMode.ARGUMENT_MODE)) throw new UnsupportedOperationException();

		readLine = readLine.trim();
		String multiline = String.valueOf(symbol);

		isMultiline = readLine.endsWith(multiline);

		return isMultiline ? readLine.substring(0, readLine.lastIndexOf(multiline)) : readLine;
	}

	/**
	 * Getter for the isMultiline boolean.
	 * 
	 * @return true, if the command contained multiline symbol
	 * @throws UnsupportedOperationException
	 *             if the parser is in argument mode
	 */
	public boolean isMultiline() {
		if (mode.equals(ParserMode.ARGUMENT_MODE)) throw new UnsupportedOperationException();
		return isMultiline;
	}

}
