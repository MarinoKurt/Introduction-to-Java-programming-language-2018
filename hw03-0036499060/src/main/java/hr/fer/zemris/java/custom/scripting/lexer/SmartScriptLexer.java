package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * Implements a simple lexical analysis system.
 * 
 * @author MarinoK
 *
 */
public class SmartScriptLexer {

	/**
	 * Input text as an array of characters.
	 */
	private char[] data;

	/**
	 * Index of the first unprocessed character.
	 */
	private int currentIndex;

	/**
	 * Current recognized token.
	 */
	private SmartToken token;

	/**
	 * State of the lexer, determines how will the input string be processed.
	 */
	private SmartScriptLexerState state;

	/**
	 * Constructor for the lexer, initializes the lexer.
	 * 
	 * @param text
	 *            string to be processed
	 * @throws IllegalArgumentException
	 *             if the given string is null
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Must not be null.");
		}
		data = text.toCharArray();
		currentIndex = 0;
		setState(SmartScriptLexerState.TEXT);
		this.token = new SmartToken(SmartTokenType.STRING, null);
	}

	/**
	 * Setter for the lexer state.
	 * 
	 * @param state
	 *            to which the lexer will be switched
	 * @throws IllegalArgumentException
	 *             if the state given is null
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("State must be a SmartScriptLexerState.");
		}
		this.state = state;
	}

	/**
	 * Getter for the lexer state.
	 * 
	 * @return lexers current state
	 */
	public SmartScriptLexerState getState() {
		return state;
	}

	/**
	 * Method used to extract the next token from the input.
	 * 
	 * @throws LexerException
	 *             if the lexer state is not defined
	 */
	public void extractNextToken() {
		if (currentIndex >= data.length) {
			token = new SmartToken(SmartTokenType.EOF, null);
			return;
		}

		if (state == SmartScriptLexerState.TEXT) {
			if (currentIndex < data.length - 1) {
				if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
					token = new SmartToken(SmartTokenType.TAG_ENTRY, null);
					currentIndex += 2;
					return;
				}
			}
			dealWithText();
			return;
		}

		if (state == SmartScriptLexerState.TAG) {
			skipBlanks();
			if (currentIndex < data.length - 1) {
				if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
					token = new SmartToken(SmartTokenType.TAG_EXIT, null);
					currentIndex += 2;
					return;
				}
			}
			dealWithTag();
			return;
		}
		throw new LexerException("Undefined state of lexer.");
	}

	/**
	 * Auxiliary method used to process input in the text state of lexer.
	 */
	private void dealWithText() {
		int startIndex = currentIndex;
		currentIndex++;
		check: while (currentIndex < data.length) {
			if (currentIndex < data.length - 1) {
				if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
					if (data[currentIndex - 1] == '\\') {
						continue check;
					}
					break check;
				}
				if (data[currentIndex] == '\\') {
					if (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{') {
						currentIndex++;
					} else {
						throw new SmartScriptParserException("Invalid escape in the text.");
					}
				}
			}
			currentIndex++;
		}

		int endIndex = currentIndex;
		String value = new String(data, startIndex, endIndex - startIndex);
		if (value.contains("\\{")) {
			try {
				value = value.replaceAll("\\\\\\{", "{");
			} catch (Exception e) {
				System.out.println("Replaces fault" + e.getMessage());
			}
		}
		if (value.contains("\\\\")) {
			try {
				value = value.replace("\\\\", "\\");
			} catch (Exception e) {
				System.out.println("Replaces2 fault" + e.getMessage());
			}
		}

		token = new SmartToken(SmartTokenType.STRING, value);
	}

	/**
	 * Auxiliary method used for processing input while the lexer is in tag state.
	 * 
	 * @throws LexerException
	 *             if the tag is invalid
	 */
	private void dealWithTag() {

		if (takeEnd())
			return;
		if (takeEquals())
			return;
		if (takeVariable())
			return;
		if (takeFunction())
			return;
		if (takeOperator())
			return;
		if (takeNumber())
			return;
		if (takeString())
			return;

		throw new LexerException("Problem with dealing with tag.");
	}

	/**
	 * Auxiliary method used to process a special kind of tag.
	 * 
	 * @return true, if the word is consumed
	 */
	private boolean takeEnd() {
		int triedIndex = currentIndex;
		String wholeTag = giveUntil('$');
		if (wholeTag.toLowerCase().startsWith("end")) {
			token = new SmartToken(SmartTokenType.VARIABLE, "end");
			currentIndex = triedIndex + 3;
			return true;
		}
		currentIndex = triedIndex;
		return false;
	}

	/**
	 * Auxiliary method used to process a special kind of tag.
	 * 
	 * @return true, if the word is consumed
	 */
	private boolean takeEquals() {
		if (data[currentIndex] == '=') {
			token = new SmartToken(SmartTokenType.OPERATOR, String.valueOf(data[currentIndex]));
			currentIndex++;
			return true;
		}
		return false;
	}

	/**
	 * Auxiliary method used to process strings in the tag mode.
	 * 
	 * @return true, if the word is consumed
	 */
	private boolean takeString() {
		String value;
		if (data[currentIndex] == '"') {
			value = giveString();
			if (value.contains("$}")) {
				throw new LexerException("String escapes the tag.");
			}
			if (value.contains("\\\"")) {
				value = value.replace("\\\"", "\"");
			}
			token = new SmartToken(SmartTokenType.STRING, value.substring(1, value.length()));
			currentIndex++;
			return true;
		}
		return false;
	}

	/**
	 * Another auxiliary method used for processing strings in the tag mode.
	 * 
	 * @return string, until the non-escaped " appears
	 */
	private String giveString() {
		int startIndex = currentIndex;
		currentIndex++;
		while (currentIndex < data.length && data[currentIndex] != '"') {
			if (currentIndex < data.length - 1) {
				if (data[currentIndex] == '\\') {
					if (data[currentIndex + 1] == '"' || data[currentIndex + 1] == 'n' || data[currentIndex + 1] == 'r'
							|| data[currentIndex + 1] == 't') {
						currentIndex++;
					} else {
						throw new LexerException("Illegal escape in string.");
					}
				}
			}
			currentIndex++;
		}

		int endIndex = currentIndex;
		String value = new String(data, startIndex, endIndex - startIndex);
		return value;
	}

	/**
	 * Auxiliary method used to process functions in the tag mode.
	 * 
	 * @return true, if the word is consumed
	 */
	private boolean takeFunction() {
		String value;
		if (data[currentIndex] == '@') {
			currentIndex++;
			value = giveUntil(' ');
			if (Character.isLetter(value.substring(0).toCharArray()[0])) {
				checkName(value);
				token = new SmartToken(SmartTokenType.FUNCTION, value);
				return true;
			}
		}
		return false;
	}

	/**
	 * Auxiliary method used to process operators in the tag mode.
	 * 
	 * @return true, if the word is consumed
	 */
	private boolean takeOperator() {
		if (data[currentIndex] == '^' || data[currentIndex] == '+' || data[currentIndex] == '*'
				|| data[currentIndex] == '\\' || data[currentIndex] == '-') {
			if (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])) {
				if (takeNumber())
					return true;
			}
			token = new SmartToken(SmartTokenType.OPERATOR, String.valueOf(data[currentIndex]));
			currentIndex++;
			return true;
		}
		return false;
	}

	/**
	 * Auxiliary method used to process variables in the tag mode.
	 * 
	 * @return true, if the word is consumed
	 */
	private boolean takeVariable() {
		if (Character.isLetter(data[currentIndex])) {
			int triedIndex = currentIndex;
			String potential = giveUntil(' ');
			if (potential.contains("$}")) {
				currentIndex = triedIndex;
				potential = giveUntil('$');
			}
			checkName(potential);
			token = new SmartToken(SmartTokenType.VARIABLE, potential);
			return true;
		}
		return false;
	}

	/**
	 * Auxiliary method used to process nubmbers, both integers and doubles, in the
	 * tag mode.
	 * 
	 * @return true, if the word is consumed
	 */
	private boolean takeNumber() {
		if (Character.isDigit(data[currentIndex]) || data[currentIndex] == '-') {
			int failSafe = currentIndex;
			String potential = giveUntil(' ');
			if (potential.contains("$}")) {
				currentIndex = failSafe;
				potential = giveUntil('$');
			}
			try {
				if (potential.contains(".")) {
					token = new SmartToken(SmartTokenType.CONSTANT_DOUBLE, Double.valueOf(potential));
				} else {
					token = new SmartToken(SmartTokenType.CONSTANT_INTEGER, Integer.valueOf(potential));
				}
			} catch (Exception numberProblem) {
				throw new LexerException("Unparsabile number!");
			}
			return true;
		}
		return false;
	}

	/**
	 * Auxiliary method used to check whether the varible/function name is valid.
	 * 
	 * @param value
	 *            to be checked
	 */
	private void checkName(String value) {
		char[] check = value.toCharArray();
		for (char unit : check) {
			if (!Character.isLetter(unit) && !Character.isDigit(unit) && unit != '_') {
				throw new LexerException(
						"Variables, functions start with a letter followed by letters, digits, or underscores.");
			}
		}
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
	 * Generates and returns the next token.
	 * 
	 * @return following token from the input string
	 * @throws LexerException
	 *             if the input string is not satisfying
	 */
	public SmartToken nextToken() {
		extractNextToken();
		return getToken();
	}

	/**
	 * Getter for the current token.
	 * 
	 * @return current token
	 */
	public SmartToken getToken() {
		return token;
	}
}
