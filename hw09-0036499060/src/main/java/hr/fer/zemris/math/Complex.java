package hr.fer.zemris.math;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a immutable complex number. All operations on this complex
 * number return the result as a new complex number.
 *
 * @author MarinoK
 */
public class Complex {

	/** Complex number zero. */
	public static final Complex ZERO = new Complex(0, 0);
	/** Complex number one. */
	public static final Complex ONE = new Complex(1, 0);
	/** Complex number negative one. */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/** Complex number imaginary one. */
	public static final Complex IM = new Complex(0, 1);
	/** Complex number negative imaginary one. */
	public static final Complex IM_NEG = new Complex(0, -1);
	/** Used for ignoring values that are too small when printing the number. */
	private static final double THRESHOLD = 0.000001;

	/** Real part of the complex number. */
	private final double real;

	/** Imaginary part of the complex number. */
	private final double imaginary;

	/** Constructor for the complex number. Creates a zero complex number. */
	public Complex() {
		this(0, 0);
	};

	/**
	 * Constructor for the complex number.
	 * 
	 * @param re
	 *            real part of the complex number
	 * @param im
	 *            imaginary part of the complex number
	 */
	public Complex(double re, double im) {
		this.real = re;
		this.imaginary = im;
	}

	/**
	 * Calculates the module of the complex number.
	 * 
	 * @return module, as a double
	 */
	public double module() {
		return sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Multiplies this complex number with given one.
	 * 
	 * @param c
	 *            multiplication operand
	 * @return new complex number as a result
	 */
	public Complex multiply(Complex c) {
		return fromMagnitudeAndAngle(this.module() * c.module(), (this.getAngle() + c.getAngle()) % (2 * PI));
	}

	/**
	 * Divides the this complex number with given one, returns new complex number.
	 * 
	 * @param c
	 *            complex number to divide this one with
	 * @return result of the operation as a complex number
	 */
	public Complex divide(Complex c) {
		if (c.module() == 0) throw new NumberFormatException("Dividing by zero.");
		return fromMagnitudeAndAngle(this.module() / c.module(), (this.getAngle() - c.getAngle()));
	}

	/**
	 * Adds the given complex number to this one, returns new complex number.
	 * 
	 * @param c
	 *            complex number to be added
	 * @return result of the operation as a new complex number
	 */
	public Complex add(Complex c) {
		return new Complex(this.real + c.getRe(), this.imaginary + c.getIm());
	}

	/**
	 * Subtracts the given complex number from this one, returns new complex number.
	 * 
	 * @param c
	 *            complex number to subtract from this one
	 * @return result of the operation as a new complex number
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real - c.getRe(), this.imaginary - c.getIm());
	}

	/**
	 * Negates this complex number.
	 * 
	 * @return negated number as a new complex number
	 */
	public Complex negate() {
		return new Complex(-this.real, -this.imaginary);
	}

	/**
	 * Calculates the value of complex number to the power of given number.
	 * 
	 * @param n
	 *            number of times we will multiply this complex number with itself
	 * @return result of the operation as a complex number
	 */
	public Complex power(int n) {
		if (n < 0) throw new IllegalArgumentException("Argument must be a non-negative integer. Was: " + n);

		return fromMagnitudeAndAngle(pow(this.module(), n), this.getAngle() * n);
	}

	/**
	 * Calculates all roots of the complex number.
	 * 
	 * @param n
	 *            we will calculate the nth root of the complex number
	 * @return results of the operation as array of complex numbers
	 */
	public List<Complex> root(int n) {
		if (n <= 0) throw new IllegalArgumentException("Argument must be a integer larger than zero. Was: " + n);

		List<Complex> roots = new ArrayList<>();
		for (int k = 0; k < n; k++) {
			roots.add(fromMagnitudeAndAngle(pow(this.module(), 1.0 / n), (this.getAngle() + 2 * k * PI) / n));
		}
		return roots;
	}

	/**
	 * Method that returns this complex number in form of a string.
	 * 
	 * @return this complex number in form of a string
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (this.real > THRESHOLD || this.real < -THRESHOLD) {
			builder.append(String.format("%.2f", this.real));
		}
		if (this.imaginary > THRESHOLD || this.imaginary < -THRESHOLD) {
			if (this.imaginary > THRESHOLD && (this.real > THRESHOLD || this.real < -THRESHOLD)) {
				builder.append("+");
			}
			builder.append(String.format("%.2f", this.imaginary));
			builder.append("i");
		}
		
		if(builder.length()<1) builder.append(0);
		return builder.toString();
	}

	/**
	 * Angle of the complex number calculator.
	 * 
	 * @return angle of the complex number
	 */
	public double getAngle() {
		return atan2(imaginary, real);
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
	public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		return new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Getter for the real part of the complex number.
	 * 
	 * @return real part
	 */
	public double getRe() {
		return real;
	}

	/**
	 * Getter for the imaginary part of the complex number.
	 * 
	 * @return imaginary part
	 */
	public double getIm() {
		return imaginary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary)) return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real)) return false;
		return true;
	}

	
}
