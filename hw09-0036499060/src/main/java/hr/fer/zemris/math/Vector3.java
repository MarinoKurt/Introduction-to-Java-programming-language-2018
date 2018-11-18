package hr.fer.zemris.math;

import static java.lang.Math.sqrt;

/**
 * Represents an immutable vector in 3d space. All mathematical operations on
 * this vector return a new object as a result, leaving both operands unchanged.
 * 
 * @author MarinoK
 */
public class Vector3 {

	/** Represents the x coordinate of the vector. */
	private final double x;

	/** Represents the y coordinate of the vector. */
	private final double y;

	/** Represents the z coordinate of the vector. */
	private final double z;

	/**
	 * Constructor for the 3d vector.
	 * 
	 * @param x
	 *            coordinate of the vector
	 * @param y
	 *            coordinate of the vector
	 * @param z
	 *            coordinate of the vector
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return norm(length) of the vector
	 */
	public double norm() {
		if(x==0 && y==0 && z==0) return 0;
		return sqrt(x * x + y * y + z * z);
	}

	/**
	 * @return a normalized vector as a new object
	 */
	public Vector3 normalized() {
		return new Vector3(x == 0 ? 0 : x / x, y == 0 ? 0 : y / y, z == 0 ? 0 : z / z);
	}

	/**
	 * Method for the addition of two vectors.
	 * 
	 * @param other
	 *            operand for the addition
	 * @return sum of two vectors, as a new vector
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x + other.getX(), this.y + other.getY(), this.z + other.getZ());
	}

	/**
	 * Method for the subtraction of two vectors. Subtracts the other vector from
	 * this one.
	 * 
	 * @param other
	 *            operand for the subtraction
	 * @return difference between the two vectors, as a new vector
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(this.x - other.getX(), this.y - other.getY(), this.z - other.getZ());
	}

	/**
	 * Calculates the dot product in three dimensions.
	 * 
	 * @param other
	 *            vector for the product
	 * @return value of the dot product, as a double
	 */
	public double dot(Vector3 other) {
		return (this.x * other.getX() + this.y * other.getY() + this.z * other.getZ());
	}

	/**
	 * Cross product of this and the given vector.
	 * 
	 * @param other
	 *            operand of the cross product
	 * @return result as a new vector
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(this.y * other.getZ() - this.z * other.getY(), this.z * other.getX() - this.x * other.getZ(),
				this.x * other.getY() - this.y * other.getX());
	}

	/**
	 * Scales this vector with the given factor.
	 * 
	 * @param s
	 *            factor of scaling
	 * @return result as a new vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(this.x * s, this.y * s, this.z * s);
	}

	/**
	 * Calculates the cosine of the angle between this and the other vector.
	 * 
	 * @param other
	 *            vector
	 * @return cosine of the angle between this and the other vector
	 */
	public double cosAngle(Vector3 other) {
		if(this.norm()==0 || other.norm()==0) throw new IllegalArgumentException();
		return (this.dot(other) / (this.norm() * other.norm()));
	}

	/**
	 * @return x coordinate of the vector
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return y coordinate of the vector
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return z coordinate of the vector
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @return this vector, as an array of doubles containing x, y, and z
	 *         coordinates of the vector
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	/**
	 * @return this vector, as a string, in form of (x, y, z)
	 */
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}
}
