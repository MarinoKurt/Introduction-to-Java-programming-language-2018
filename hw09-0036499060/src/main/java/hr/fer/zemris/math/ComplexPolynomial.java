package hr.fer.zemris.math;

/**
 * Complex polynomial in its regular form, A*Z^n+B*z^(n-1)*...*N*Z^0, where
 * A...N are factors given in the constructor.
 * 
 * @author MarinoK
 */
public class ComplexPolynomial {

	/** Order of this polynomial. */
	private int order;

	/** Factors of the polynomial. */
	private Complex[] factors;

	/**
	 * Constructor for the ComplexPolynomial.
	 * 
	 * @param factors
	 *            of the polynomial
	 */
	public ComplexPolynomial(Complex... factors) {
		this.order = factors.length;
		this.factors = factors;
	}

	/**
	 * Returns the order of this polynomial.
	 * 
	 * @return order of this polynomial
	 */
	public short order() {
		int counter = 0;
		for (int i = 0; i < factors.length; i++) {
			if (factors[i].equals(Complex.ZERO)) {
				counter++;
			} else {
				break;
			}
		}
		return (short) (factors.length - counter - 1);
	}

	/**
	 * Computes a new polynomial, this multiplied with the given.
	 * 
	 * @param p
	 *            polynomial to be multiplied
	 * @return the result of the multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] mulFactors = p.getFactors();
		Complex[] result = new Complex[mulFactors.length + order - 1];

		for (int i = 0; i < order; i++) {
			for (int j = 0; j < mulFactors.length; j++) {
				result[i + j] = result[i + j] == null ? factors[i].multiply(mulFactors[j])
						: result[i + j].add(factors[i].multiply(mulFactors[j]));
			}
		}

		return new ComplexPolynomial(result);
	}

	/**
	 * Getter for the factors of the polynomial.
	 * 
	 * @return factors of the polynomial, as an array
	 */
	public Complex[] getFactors() {
		return factors;
	}

	/**
	 * Calculates the first derivative of this polynomial.
	 * 
	 * @return first derivative of this polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] result = new Complex[factors.length - 1];
		for (int i = 1; i < factors.length; i++) {
			result[i - 1] = factors[i].multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(result);
	}

	/**
	 * Calculates the value of the polynomial at the given complex number.
	 * 
	 * @param z
	 *            complex number
	 * @return value of the polynomial at the given complex number
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		for (int i = 0; i < factors.length; i++) {
			Complex tmp = factors[i].multiply(z.power(i));
			result = result.add(tmp);
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.length - 1; i >= 0; i--) {
			if (factors[i] == null || factors[i].toString().equals("0")) continue;
			sb.append("(" + factors[i] + ")*z^" + i);
			sb.append("+");
		}
		String expression = sb.toString();
		if (expression.endsWith("+")) {
			expression = expression.substring(0, expression.length() - 1);
		}
		expression = expression.replace("*z^0", "").replace("^1", "");
		return expression;
	}

}
