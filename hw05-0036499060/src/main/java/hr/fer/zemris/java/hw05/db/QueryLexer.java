package hr.fer.zemris.java.hw05.db;

/**
 * Implements a simple lexical analysis system used for queries for
 * StudentDatabase.
 * 
 * @author MarinoK
 */
public class QueryLexer {

	/** Input text as an array of characters. */
	private char[] data;

	/** Index of the first unprocessed character. */
	private int currentIndex;

	/** Most recently recognized token. */
	private QueryToken token;

	/**
	 * Constructor for the QueryLexer.
	 * 
	 * @param text
	 *            string to be processed
	 */
	public QueryLexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Must not be null.");
		}
		this.data = text.toCharArray();
		this.currentIndex = 0;
	}

	/**
	 * Getter for the token.
	 * 
	 * @return most recently recognized token
	 */
	private QueryToken getToken() {
		return token;
	}

	/**
	 * Method used to fetch the next token from text.
	 * 
	 * @return next token from text
	 */
	public QueryToken nextToken() {
		extractNextToken();
		return getToken();
	}

	/**
	 * Method used for extracting next token from text.
	 */
	private void extractNextToken() {
		skipBlanks();
		if (currentIndex >= data.length) {
			token = new QueryToken(QueryTokenType.EOQ, null);
			return;
		}

		if (data[currentIndex] == '<' || data[currentIndex] == '>' || data[currentIndex] == '!') {
			if (data[currentIndex + 1] == '=') { // if current+1 doesn't exist, the query is invalid anyways
				String operator = new StringBuilder().append(data[currentIndex]).append(data[currentIndex + 1])
						.toString();
				token = new QueryToken(QueryTokenType.OPERATOR, operator);
				currentIndex += 2;
				return;
			} else {
				if (data[currentIndex] != '!') {
					token = new QueryToken(QueryTokenType.OPERATOR, String.valueOf(data[currentIndex]));
					currentIndex++;
					return;
				}
			}
		}

		if (data[currentIndex] == '=') {
			token = new QueryToken(QueryTokenType.OPERATOR, String.valueOf(data[currentIndex]));
			currentIndex++;
			return;
		}

		if (data[currentIndex] == '"') {
			currentIndex++;
			String literal = giveUntil('"');
			token = new QueryToken(QueryTokenType.STRING_LITERAL, literal);
			return;
		}

		if (Character.isLetter(data[currentIndex])) {
			int startIndex = currentIndex;
			while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
				currentIndex++;
			}
			int endIndex = currentIndex;
			String unit = new String(data, startIndex, endIndex - startIndex);
			if (unit.equals("LIKE")) {
				token = new QueryToken(QueryTokenType.OPERATOR, unit);
				return;
			} else if (unit.toLowerCase().equals("and")) {
				token = new QueryToken(QueryTokenType.LOGICAL_OPERATOR_AND, unit);
				return;
			}
			token = new QueryToken(QueryTokenType.ATTRIBUTE_NAME, unit);
			return;
		}

		throw new QueryLexerException("Invalid input near index: " + currentIndex);

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
		while (currentIndex < data.length && data[currentIndex] != end) {
			currentIndex++;
		}
		int endIndex = currentIndex;
		String value = new String(data, startIndex, endIndex - startIndex);
		currentIndex++; // TODO not sure if needed
		return value;
	}

}
