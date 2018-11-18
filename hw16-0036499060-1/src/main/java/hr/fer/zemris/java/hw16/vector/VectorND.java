package hr.fer.zemris.java.hw16.vector;

/**
 * Representation of a vector in n-dimensional space.
 * 
 * @author MarinoK
 */
public class VectorND {

	/**
	 * Values of the vector.
	 */
	private double[] values;

	/**
	 * Default constructor for the vector.
	 * 
	 * @param values
	 *            of the vector
	 */
	public VectorND(double[] values) {
		this.values = values.clone();
	}

	/**
	 * Getter for the vector values.
	 * 
	 * @return values of the vector
	 */
	public double[] getValues() {
		return values;
	}

	/**
	 * Setter for the vector values.
	 * 
	 * @param values
	 *            of the vector
	 */
	public void setValues(double[] values) {
		this.values = values;
	}

	/**
	 * Calculates the norm of the vector.
	 * 
	 * @return vector norm
	 */
	public double norm() {
		double norm = 0;
		for (double x : values) {
			norm += x * x;
		}
		norm = Math.sqrt(norm);
		return norm;
	}

	/**
	 * Calculates the dot product of the two vectors.
	 * 
	 * @param other
	 *            vector
	 * @return dot product of this and the other vector
	 */
	public double dot(VectorND other) {
		if (this.values.length != other.getValues().length) {
			throw new UnsupportedOperationException();
		}
		double dot = 0;
		double[] otherValues = other.getValues();
		for (int i = 0; i < values.length; i++) {
			dot += values[i] * otherValues[i];
		}
		return dot;
	}

	/**
	 * Multiplies this vector with the other vector. Does not alter any of the given
	 * vectors, but returns the result as a new vector.
	 * 
	 * @param other
	 *            vector
	 * @return result of the multiplication as a new vector
	 */
	public VectorND mul(VectorND other) {
		if (this.values.length != other.getValues().length) {
			throw new UnsupportedOperationException();
		}

		double[] newValues = new double[values.length];
		double[] otherValues = other.getValues();
		for (int i = 0; i < values.length; i++) {
			newValues[i] = values[i] * otherValues[i];
		}
		return new VectorND(newValues);
	}
}
