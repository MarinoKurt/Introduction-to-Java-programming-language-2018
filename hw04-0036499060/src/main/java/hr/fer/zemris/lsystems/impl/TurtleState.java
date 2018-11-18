package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Represents the state of the "turtle", the object that is moving in the
 * coordinate system.
 * 
 * @author MarinoK
 */
public class TurtleState {

	/**
	 * Current position of the turtle.
	 */
	private Vector2D position;

	/**
	 * Direction in which the turtle is going. Unit vector.
	 */
	private Vector2D direction;

	/**
	 * Color of the line that the turtle draws.
	 */
	private Color lineColor;

	/**
	 * Effective distance of one step of the turtle.
	 */
	private double displacement;

	/**
	 * Constructor for the turtle state.
	 * 
	 * @param position
	 *            of the turtle
	 * @param direction
	 *            of the turtle
	 * @param lineColor
	 *            of the turtle
	 * @param displacement
	 *            of the turtle
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color lineColor, double displacement) {
		this.position = position.copy();
		this.direction = direction.copy();
		this.lineColor = lineColor;
		this.displacement = displacement;
	}

	/**
	 * Getter for the position.
	 * 
	 * @return position, in form of a vector
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Getter for the direction.
	 * 
	 * @return direction, in form of a vector
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Getter for the line color.
	 * 
	 * @return color, in form of a Color object
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Getter for the displacement.
	 * 
	 * @return displacement as a double
	 */
	public double getDisplacement() {
		return displacement;
	}

	/**
	 * Setter for the position.
	 * 
	 * @param position
	 *            as a vector
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Setter for the direction.
	 * 
	 * @param direction
	 *            as a vector
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Setter for the line color.
	 * 
	 * @param lineColor
	 *            as a Color object
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * Setter for the displacement.
	 * 
	 * @param displacement
	 *            as a double
	 */
	public void setDisplacement(double displacement) {
		this.displacement = displacement;
	}

	/**
	 * Method used to copy the current state of the turtle, and return it.
	 * 
	 * @return the current turtle state
	 */
	public TurtleState copy() {
		return new TurtleState(position, direction, lineColor, displacement);
	}
}
