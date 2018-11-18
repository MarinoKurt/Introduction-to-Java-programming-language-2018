package hr.fer.zemris.java.hw03.prob1;

/**
 * Implements a simple lexical analysis system.
 * 
 * @author MarinoK
 *
 */
public class Lexer {

	/**
	 * Input text as an array of characters.
	 */
	private char[] data;

	/**
	 * Current recognized token.
	 */
	private Token token;

	/**
	 * Index of the first unprocessed character.
	 */
	private int currentIndex;

	/**
	 * State of the lexer, determines how will the input string be processed.
	 */
	private LexerState state;

	/**
	 * Constructor for the lexer, initializes the lexer.
	 * 
	 * @param text
	 *            string to be processed
	 * @throws IllegalArgumentException
	 *             if the given string is null
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Must not be null.");
		}
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}

	/**
	 * Setter for the lexer state.
	 * 
	 * @param state
	 *            to which the lexer will be switched
	 * @throws IllegalArgumentException
	 *             if the state given is null
	 */
	public void setState(LexerState state) {
		if (state == null)
			throw new IllegalArgumentException("State must be a LexerState.");
		this.state = state;
	}

	/**
	 * Generates and returns the next token.
	 * 
	 * @return following token from the input string
	 * @throws LexerException
	 *             if the input string is not satisfying
	 */
	public Token nextToken() {
		extractNextToken();
		return getToken();
	}

	/**
	 * Extracts the next token from the input.
	 * 
	 * @throws LexerException
	 *             if the input is not satisfying
	 */
	private void extractNextToken() {
		if (token != null && token.getType() == (TokenType.EOF)) {
			throw new LexerException("There are no tokens left.");
		}
		skipBlanks();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}

		if (state == LexerState.BASIC) {
			basicInterpretation();
		} else {
			extendedInterpretation();
		}
	}

	/**
	 * Interpretation of the input used if the lexer is in the extended state. In
	 * this state, lexer will group any characters separated by white spaces, until
	 * a # character changes it's state.
	 * 
	 * @throws LexerException
	 *             if the input is not satisfying
	 */
	private void extendedInterpretation() {
		if (data[currentIndex] == '#') {
			foundHash();
			return;
		}

		int startIndex = currentIndex;
		currentIndex++;
		while (currentIndex < data.length && data[currentIndex] != ' ' && data[currentIndex] != '#') {
			currentIndex++;
		}
		int endIndex = currentIndex;
		String value = new String(data, startIndex, endIndex - startIndex);
		token = new Token(TokenType.WORD, value);
	}

	/**
	 * Interpretation of the input used if the lexer is in the basic state. In this
	 * state, lexer will sort the input to tokens intuitively, with the exception of
	 * characters after the escape character \, which will always be a word.
	 * 
	 * @throws LexerException
	 *             if the input is not satisfying
	 */
	private void basicInterpretation() {
		boolean firstIsEscape = data[currentIndex] == '\\';

		if (firstIsEscape && currentIndex == data.length - 1) {
			throw new LexerException("Invalid escape!");
		}

		if (Character.isLetter(data[currentIndex]) || firstIsEscape) { // dealing with words and escape characters
			int startIndex = currentIndex;
			currentIndex++;
			while (currentIndex < data.length
					&& (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\' || firstIsEscape)) {
				if (data[currentIndex] == '\\' && currentIndex < data.length - 1) {
					if (Character.isDigit(data[currentIndex + 1])) {
						currentIndex++;
					}
				}
				currentIndex++;
			}
			int endIndex = currentIndex;
			for (int i = startIndex; i < endIndex - 1; i++) {
				if (data[i] == '\\') {
					if (Character.isDigit(data[i + 1])) {
						data[i] = ' ';
					} else if (data[i + 1] == '\\') {
						data[i + 1] = ' ';
					} else {
						throw new LexerException("Invalid escape!");
					}
				}
			}
			while (data[startIndex] == ' ') {
				startIndex++;
			}
			String word = new String(data, startIndex, endIndex - startIndex);
			word = word.replace("\\s+", "");
			word = word.replace(" ", "");
			word = word.trim();
			token = new Token(TokenType.WORD, word);
			return;
		} else if (Character.isDigit(data[currentIndex])) { // dealing with numbers
			int startIndex = currentIndex;
			currentIndex++;
			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				currentIndex++;
			}
			int endIndex = currentIndex;
			String value = new String(data, startIndex, endIndex - startIndex);
			try {
				token = new Token(TokenType.NUMBER, Long.valueOf(value));
			} catch (NumberFormatException parseFailed) {
				throw new LexerException("Number is too big.");
			}
		} else { // any character that is not a digit, a letter, or escaped, is a symbol
			if (data[currentIndex] == '#') {
				foundHash();
				return;
			}
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
		}
	}

	/**
	 * Auxiliary method used to switch states when the # character is found.
	 */
	private void foundHash() {
		token = new Token(TokenType.SYMBOL, data[currentIndex]);
		if (state == LexerState.EXTENDED) {
			setState(LexerState.BASIC);
		} else {
			setState(LexerState.EXTENDED);
		}
		currentIndex++;
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
	 * Getter for the current token.
	 * 
	 * @return most recently recognized token
	 */
	public Token getToken() {
		return token;
	}
}
