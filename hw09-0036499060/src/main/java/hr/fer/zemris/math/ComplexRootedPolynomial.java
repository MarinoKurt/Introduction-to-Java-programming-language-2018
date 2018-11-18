package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a complex polynomial in form of (x-a1)*(x-a2)*...*(x-an), where a
 * is the root given in constructor.
 * 
 * @author MarinoK
 */
public class ComplexRootedPolynomial {

	/** Roots of the polynomial. */
	private Complex[] roots;

	/**
	 * Constructor for the ComplexRootedPolynomial.
	 * 
	 * @param roots
	 *            of the polynomial
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = roots;
	}

	/**
	 * Calculates the value of the polynomial at the given complex number.
	 * 
	 * @param z
	 *            complex number
	 * @return value of the polynomial at the given complex number
	 */
	public Complex apply(Complex z) {
		Complex result;
		Complex[] temp = new Complex[roots.length];

		for (int i = 0; i < roots.length; i++) {
			temp[i] = z.sub(roots[i]);
		}

		result = temp[0];
		for (int i = 1; i < temp.length; i++) {
			result = result.multiply(temp[i]);
		}
		return result;
	}

	/**
	 * Converts this representation of polynomial to regular polynomial.
	 * 
	 * @return polynomial mathematically equal to this one
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE_NEG);

		for (Complex r : roots) {
			ComplexPolynomial mulVal = new ComplexPolynomial(Complex.ONE, r.negate());
			result = result.multiply(mulVal);
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < roots.length; i++) {
			sb.append("(z-(" + roots[i] + "))");
			if (i != roots.length - 1) sb.append("*");
		}
		return sb.toString();
	}

	/**
	 * Method finds index of closest root for given complex number z that is within
	 * threshold; if there is no such root, returns -1.
	 * 
	 * @param z
	 *            complex number
	 * @param threshold
	 *            parameter for filtering
	 * @return index of the closest root
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {

		List<Complex> potential = new ArrayList<>();
		for (int i = 0; i < roots.length; i++) {
			if (z.sub(roots[i]).module() < threshold) {
				potential.add(roots[i]);
			}
		}
		if (potential.isEmpty()) return -1;

		potential.sort((c1, c2) -> Double.compare(c1.module(), c2.module()));

		for (int i = 0; i < roots.length; i++) {
			if (potential.get(0).equals(roots[i])) return i + 1;
		}
		return -1;
	}

}
