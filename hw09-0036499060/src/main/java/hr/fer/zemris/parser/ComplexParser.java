package hr.fer.zemris.parser;

import hr.fer.zemris.math.Complex;

/**
 * Parser for complex numbers.
 * 
 * @author MarinoK
 */
public class ComplexParser {
	
	/**
	 * Parses the string into an instance of Complex.
	 * 
	 * @param fullString
	 *            to parse
	 * @return instance of Complex, defined by the received string
	 */
	public static Complex parse(String fullString) {
		double real = 0, imaginary = 0;
		boolean firstPositive = true, secondPositive = false, twoArguments = false, firstImaginary = false,
				secondImaginary = false;
		fullString = fullString.replace("\\s+", "");
		String withoutFirstCharacter = fullString.substring(1, fullString.length());
		String firstCharacter = fullString.substring(0, 1);
		String[] arguments = new String[2];

		try {
			real = Double.parseDouble(fullString);
			return new Complex(real, 0);
		} catch (NumberFormatException ignorable) {
		}

		if (firstCharacter.contains("i")) {
			firstPositive = true;
			firstImaginary = true;
			secondImaginary = false;
			if (withoutFirstCharacter.equals("")) {
				return new Complex(0, 1);
			}
			if (withoutFirstCharacter.startsWith("+")) {
				real = Double.parseDouble(withoutFirstCharacter.substring(1, withoutFirstCharacter.length()));
				return new Complex(real, 1);
			}
			if (withoutFirstCharacter.startsWith("-")) {
				real = Double.parseDouble(withoutFirstCharacter.substring(1, withoutFirstCharacter.length()));
				real *= -1;
				return new Complex(real, 1);
			}

			fullString = withoutFirstCharacter;
		} else if (firstCharacter.contains("-")) {
			firstPositive = false;
			if (withoutFirstCharacter.equals("i")) {
				return new Complex(0, -1);
			}
			try {
				real = Double.parseDouble(withoutFirstCharacter);
				return new Complex(-real, 0);
			} catch (NumberFormatException ignorable) {
			}
			fullString = withoutFirstCharacter;
		}

		if (withoutFirstCharacter.equals("")) {
			try {
				real = Double.parseDouble(firstCharacter);
				return new Complex(real, 0);
			} catch (NumberFormatException parseFail) {
				System.out.println("Not a complex number in the right form. Should be like: \"4+3i\"");
			}
		}

		if (fullString.contains("+")) {
			arguments = fullString.split("\\+");
			twoArguments = true;
			secondPositive = true;
		} else if (fullString.contains("-")) {
			arguments = fullString.split("\\-");
			twoArguments = true;
			secondPositive = false;
		} else {
			twoArguments = false;
			arguments[0] = fullString;
		}

		if (arguments[0].contains("i")) {
			firstImaginary = true;
			secondImaginary = false;
			arguments[0] = arguments[0].replace("i", "");
			if (arguments[0].equals("")) {
				arguments[0] = "1";
			}
		}
		if (twoArguments) {
			if (arguments[1].contains("i")) {
				firstImaginary = false;
				secondImaginary = true;
				arguments[1] = arguments[1].replace("i", "");
				if (arguments[1].equals("")) {
					arguments[1] = "1";
				}
			}
		}
		try {
			if (twoArguments) {
				if (firstImaginary) {
					imaginary = Double.parseDouble(arguments[0]);
					real = Double.parseDouble(arguments[1]);
				} else {
					real = Double.parseDouble(arguments[0]);
					imaginary = Double.parseDouble(arguments[1]);
				}
			} else {
				if (firstImaginary) {
					imaginary = Double.parseDouble(arguments[0]);
				} else {
					real = Double.parseDouble(arguments[0]);
				}
			}
		} catch (NumberFormatException parseFail) {
			System.out.println("Not a complex number in the right form. Should be like: \"4+3i\"");
		}
		if (real == 0 && imaginary == 0) {
			return Complex.ZERO;
		}
		if (!firstPositive) {
			if (firstImaginary) {
				imaginary *= -1;
			} else {
				real *= -1;
			}
		}
		if (!secondPositive) {
			if (secondImaginary) {
				imaginary *= -1;
			} else {
				real *= -1;
			}
		}
		return new Complex(real, imaginary);
	}
}
