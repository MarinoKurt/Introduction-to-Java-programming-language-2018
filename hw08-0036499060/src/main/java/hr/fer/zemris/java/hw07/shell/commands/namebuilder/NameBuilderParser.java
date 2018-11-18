package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

/**
 * Parser for the part of the expression used for building a new name for the
 * file.
 * 
 * @author MarinoK
 */
public class NameBuilderParser {

	/** Used to store current position in the expression that is being parsed. */
	private int currentIndex;

	/** Expression being parsed, in form of a array. */
	private char[] data;

	/** Main name builder containing all the produced name builders. */
	private NameBuilder nameBuilder;

	/**
	 * Constructor for the parser.
	 * 
	 * @param expression
	 *            part of the user input
	 */
	public NameBuilderParser(String expression) {
		this.data = expression.toCharArray();
		this.currentIndex = 0;
		this.nameBuilder = new MainNameBuilder();
		while (true) {
			NameBuilder nb = parse();
			if (nb == null) break;
			((MainNameBuilder) nameBuilder).add(nb);
		}
	}

	/**
	 * Auxiliary method used for parsing the user input.
	 * 
	 * @return NameBuilder containing first logical part of the input
	 */
	private NameBuilder parse() {
		if (currentIndex >= data.length) return null;

		skipBlanks();
		if (currentIndex < data.length - 1) {
			if (data[currentIndex] == '$' && data[currentIndex + 1] == '{') {
				currentIndex += 2;
				String argument = giveUntil('}').trim();
				return processGroup(argument);
			} else {
				String namePart = giveUntil('$');
				while (namePart.equals("")) {
					currentIndex++;
					namePart = giveUntil('$');
				}
				return new StringNameBuilder(namePart);
			}
		}
		return null;
	}

	/**
	 * Auxiliary method used for handling group parts of the expression.
	 * 
	 * @param argument
	 *            of the group part of the expression
	 * @return instance of NameBuilder suitable for the given argument
	 */
	private NameBuilder processGroup(String argument) {
		String[] demands;
		Integer group;

		if (argument.contains(",")) {
			demands = argument.split(",");
			Integer width;
			char fill = ' ';
			try {
				group = Integer.valueOf(demands[0].trim());
				if(demands.length!=2) throw new NumberFormatException();
				if (demands[1].startsWith("0")) {
					fill = '0';
					width = Integer.valueOf(demands[1].substring(1));
				} else if (!Character.isDigit((demands[1].charAt(0)))) {
					fill = demands[1].charAt(0);
					width = Integer.valueOf(demands[1].substring(1));
				} else {
					width = Integer.valueOf(demands[1].trim());
				}

				if (group < 0 || width < 0) throw new NumberFormatException();
			} catch (NumberFormatException notAPositiveNumber) {
				throw new NameBuilderParserException("Invalid arguments for the expression.");
			}
			currentIndex++;
			return new GroupNameBuilder(group, fill, width);
		} else {
			try {
				group = Integer.valueOf(argument);
				if(group<0) throw new NumberFormatException();
			} catch (NumberFormatException notAPositiveNumber) {
				throw new NameBuilderParserException("Invalid arguments for the expression.");
			}
			currentIndex++;
			return new GroupNameBuilder(group);
		}

	}

	/**
	 * Getter for the main name builder.
	 * 
	 * @return main name builder
	 */
	public NameBuilder getNameBuilder() {
		if(this.nameBuilder==null) throw new NameBuilderParserException("Name builder not initiated.");
		return this.nameBuilder;
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

}
