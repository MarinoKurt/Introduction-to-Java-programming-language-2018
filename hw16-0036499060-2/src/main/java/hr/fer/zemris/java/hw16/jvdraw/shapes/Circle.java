package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.editors.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Model of a circle.
 * 
 * @author MarinoK
 */
public class Circle extends GeometricalObject {

	/**
	 * Center of the circle.
	 */
	private Point center;

	/**
	 * Circle radius.
	 */
	private double radius;

	/**
	 * Constructor for the circle.
	 * 
	 * @param center
	 *            of the circle
	 * @param radius
	 *            of the circle
	 * @param outlineColor
	 *            color
	 */
	public Circle(Point center, double radius, Color outlineColor) {
		super(outlineColor);
		this.center = center;
		this.radius = radius;
		setName();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	/**
	 * Fetches the circle center.
	 * 
	 * @return circle center, as a point
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Sets the circle center.
	 * 
	 * @param center
	 *            circle center, as a point
	 */
	public void setCenter(Point center) {
		this.center = center;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

	/**
	 * Sets the circle center.
	 * 
	 * @param x
	 *            coordinate of the center
	 * @param y
	 *            coordinate of the center
	 */
	public void setCenter(int x, int y) {
		this.center = new Point(x, y);
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

	/**
	 * Getter for the circle radius.
	 * 
	 * @return radius of the circle as a double
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Setter for the circle radius.
	 * 
	 * @param radius
	 *            of the circle as a double
	 */
	public void setRadius(double radius) {
		this.radius = radius;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String toText() {
		StringBuilder sb = new StringBuilder("CIRCLE ");
		sb.append(center.x).append(" ");
		sb.append(center.y).append(" ");
		sb.append(radius).append(" ");
		sb.append(outlineColor.getRed()).append(" ");
		sb.append(outlineColor.getGreen()).append(" ");
		sb.append(outlineColor.getBlue());
		return sb.toString();
	}
	

	/**
	 * Method used to calculate the name of the object.
	 */
	private final void setName() {
		StringBuilder sb = new StringBuilder("Circle (");
		sb.append(getCenter().x);
		sb.append(",");
		sb.append(getCenter().y);
		sb.append("), ");
		sb.append(Math.round(getRadius()));
		this.name = sb.toString();
	}

}
