package hr.fer.zemris.math;

/**
 * Class represents a two-dimensional vector.
 * 
 * @author MarinoK
 *
 */
public class Vector2D {

	/**
	 * Represents the value of the x coordinate.
	 */
	private double x;

	/**
	 * Represents the value of the y coordinate.
	 */
	private double y;

	/**
	 * Constructor for the vector.
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for the x coordinate.
	 * 
	 * @return x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for the y coordinate.
	 * 
	 * @return y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Method used to translate this vector for a given offset.
	 * 
	 * @param offset
	 *            in form of a vector
	 */
	public void translate(Vector2D offset) {
		this.x += offset.getX();
		this.y += offset.getY();
	}

	/**
	 * Method translates this vector for a given offset, but rather than changing
	 * this vector, it returns a new vector.
	 * 
	 * @param offset
	 *            in form of a vector
	 * @return new, translated vector
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(this.x + offset.getX(), this.y + offset.getY());
	}

	/**
	 * Rotates this vector for an angle given.
	 * 
	 * @param angle
	 *            to rotate vector, in radians
	 */
	public void rotate(double angle) {
		double x2 = Math.cos(angle) * this.x - Math.sin(angle) * this.y; // cosβx1−sinβy1
		double y2 = Math.sin(angle) * this.x + Math.cos(angle) * this.y; // sinβx1+cosβy1
		this.x = x2;
		this.y = y2;
	}

	/**
	 * Rotates this vector for an angle given, and returns the result as a new
	 * vector.
	 * 
	 * @param angle
	 *            to rotate vector, in radians
	 * @return new, rotated vector
	 */
	public Vector2D rotated(double angle) {
		double x2 = Math.cos(angle) * this.x - Math.sin(angle) * this.y; // cosβx1−sinβy1
		double y2 = Math.sin(angle) * this.x + Math.cos(angle) * this.y; // sinβx1+cosβy1
		return new Vector2D(x2, y2);
	}

	/**
	 * Multiplies this vector with a scaler.
	 * 
	 * @param scaler
	 *            to multiply with
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}

	/**
	 * Multiplies this vector with a scaler, returns the result as a new vector.
	 * 
	 * @param scaler
	 *            to multiply with
	 * @return new, scaled vector
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}

	/**
	 * Method used to copy the current vector.
	 * 
	 * @return a copy of the vector
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	/**
	 * Method used to get the vectors angle.
	 * 
	 * @return angle in form of a double, in radians
	 */
	public double getAngle() {
		return Math.atan2(y, x);
	}

	/**
	 * Normalizes the vector.
	 * 
	 * @return normalized vector
	 */
	public Vector2D normalize() {
		double magnitude = Math.sqrt(x * x + y * y);
		if (magnitude == 0) {
			return null;
		}
		return new Vector2D(x / magnitude, y / magnitude);
	}
}
