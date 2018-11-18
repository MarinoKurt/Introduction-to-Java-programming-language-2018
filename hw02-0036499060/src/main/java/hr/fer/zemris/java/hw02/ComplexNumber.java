package hr.fer.zemris.java.hw02;

import static java.lang.Math.*;

/**
 * Represents an unmodifiable complex number.
 * 
 * @author MarinoK
 */
public class ComplexNumber {

	/**
	 * Maximal number of arguments for the parser.
	 */
	private static final int MAXIMUM_ARGUMENTS = 2;

	/**
	 * Real part of the complex number.
	 */
	private final double real;

	/**
	 * Imaginary part of the complex number.
	 */
	private final double imaginary;

	/**
	 * Constructor for the complex number.
	 * 
	 * @param real
	 *            part of the complex number
	 * @param imaginary
	 *            part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Factory method.
	 * 
	 * @param real
	 *            part of the complex number
	 * @return newly created complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Factory method.
	 * 
	 * @param imaginary
	 *            part of the complex number
	 * @return newly created complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Factory method.
	 * 
	 * @param magnitude
	 *            of the complex number
	 * @param angle
	 *            of the complex number
	 * @return newly created complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Parses the string into an instance of ComplexNumber.
	 * 
	 * @param fullString
	 *            to parse
	 * @return instance of ComplexNumber, defined by the received string
	 */
	public static ComplexNumber parse(String fullString) { //not pretty, but i didn't know better without using regex
		double real = 0, imaginary = 0;
		boolean firstPositive = true, secondPositive = false, twoArguments = false, firstImaginary = false,
				secondImaginary = false;
		fullString = fullString.replace("\\s+", "");
		String withoutFirstCharacter = fullString.substring(1, fullString.length());
		String firstCharacter = fullString.substring(0, 1);
		String[] arguments = new String[MAXIMUM_ARGUMENTS];
		
		try {
			real = Double.parseDouble(fullString);
			return fromReal(real);
		} catch (NumberFormatException ignorable) {
		}

		if (firstCharacter.contains("i")) {
			firstPositive = true;
			firstImaginary = true;
			secondImaginary = false;
			if (withoutFirstCharacter.equals("")) {
				return fromImaginary(1);
			}
			if (withoutFirstCharacter.startsWith("+")) {
				real = Double.parseDouble(withoutFirstCharacter.substring(1, withoutFirstCharacter.length()));
				return new ComplexNumber(real, 1);
			}
			if (withoutFirstCharacter.startsWith("-")) {
				real = Double.parseDouble(withoutFirstCharacter.substring(1, withoutFirstCharacter.length()));
				real *= -1;
				return new ComplexNumber(real, 1);
			}

			fullString = withoutFirstCharacter;
		} else if (firstCharacter.contains("-")) {
			firstPositive = false;
			if (withoutFirstCharacter.equals("i")) {
				return fromImaginary(-1);
			}
			try {
				real = Double.parseDouble(withoutFirstCharacter);
					return fromReal(-real);
			} catch (NumberFormatException ignorable) {
			}
			fullString = withoutFirstCharacter;
		}
		
		if (withoutFirstCharacter.equals("")) {
			try {
				real = Double.parseDouble(firstCharacter);
				return fromReal(real);
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
			System.out.println("nisu pridružene vrijednosti, bug");
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

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Real part of the complex number getter.
	 * 
	 * @return real part of the complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Imaginary part of the complex number getter.
	 * 
	 * @return imaginary part of the complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Magnitude of the complex number calculator.
	 * 
	 * @return magnitude of the complex number
	 */
	public double getMagnitude() {
		return sqrt(pow(real, 2) + pow(imaginary, 2));
	}

	/**
	 * Angle of the complex number calculator.
	 * 
	 * @return angle of the complex number
	 */
	public double getAngle() {
		double angle = 0;

		if (real > 0) {
			angle = atan(imaginary / real);
		} else if (real < 0) {
			if (imaginary >= 0) {
				angle = atan(imaginary / real) + PI;
			} else {
				angle = atan(imaginary / real) - PI;
			}
		} else if (real == 0) {
			if (imaginary > 0) {
				angle = PI / 2;
			} else if (imaginary < 0) {
				angle = -PI / 2;
			} else {
				angle = 0;
			}
		}
		return angle;
	}

	/**
	 * Adds the given complex number to this one, returns new complex number.
	 * 
	 * @param c
	 *            complex number to add
	 * @return result of the operation as a complex number
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Subtracts the given complex number from this one, returns new complex number.
	 * 
	 * @param c
	 *            complex number to subtract from this one
	 * @return result of the operation as a complex number
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Multiplies the given complex number with this one, returns new complex
	 * number.
	 * 
	 * @param c
	 *            complex number to multiply with this one
	 * @return result of the operation as a complex number
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return fromMagnitudeAndAngle(this.getMagnitude() * c.getMagnitude(),
				(this.getAngle() + c.getAngle()) % (2 * PI));
	}

	/**
	 * Divides the this complex number with given one, returns new complex number.
	 * 
	 * @param c
	 *            complex number to divide this one with
	 * @return result of the operation as a complex number
	 */
	public ComplexNumber div(ComplexNumber c) {
		return fromMagnitudeAndAngle(this.getMagnitude() / c.getMagnitude(), (this.getAngle() - c.getAngle()));
	}

	/**
	 * Calculates the value of complex number to the power of given number.
	 * 
	 * @param n
	 *            number of times we will multiply this complex number with itself
	 * @return result of the operation as a complex number
	 */
	public ComplexNumber power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Argument must be a non-negative integer. Was: " + n);

		return fromMagnitudeAndAngle(pow(this.getMagnitude(), n), this.getAngle() * n);
	}

	/**
	 * Calculates all roots of the complex number.
	 * 
	 * @param n
	 *            we will calculate the nth root of the complex number
	 * @return results of the operation as array of complex numbers
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("Argument must be a integer larger than zero. Was: " + n);

		ComplexNumber[] roots = new ComplexNumber[n];
		for (int k = 0; k < n; k++) {
			roots[k] = fromMagnitudeAndAngle(pow(this.getMagnitude(), 1.0 / n), (this.getAngle() + 2 * k * PI) / n);
		}
		return roots;
	}

	/**
	 * Method that returns this complex number in form of a string.
	 * 
	 * @return this complex number in form of a string
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (getReal() != 0) {
			builder.append(getReal());
		}
		if (getImaginary() != 0) {
			if (getImaginary() > 0 && getReal() != 0) {
				builder.append("+");
			}
			builder.append(getImaginary());
			builder.append("i");
		}
		return builder.toString();
	}
}
