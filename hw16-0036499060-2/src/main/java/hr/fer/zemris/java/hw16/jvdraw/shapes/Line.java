package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Representation of a line.
 *
 * @author MarinoK
 */
public class Line extends GeometricalObject {

	/**
	 * Starting point of the line.
	 */
	private Point a;

	/**
	 * Ending point of the line.
	 */
	private Point b;

	/**
	 * Constructor for the line.
	 * 
	 * @param a
	 *            starting point
	 * @param b
	 *            ending point
	 * @param color
	 *            of the line
	 */
	public Line(Point a, Point b, Color color) {
		super(color);
		this.a = a;
		this.b = b;
		setName();
	}

	/**
	 * Getter for the line starting point.
	 * 
	 * @return starting point
	 */
	public Point getA() {
		return a;
	}

	/**
	 * Setter for the line starting point.
	 * 
	 * @param a
	 *            starting point
	 */
	public void setA(Point a) {
		this.a = a;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
		setName();
	}

	/**
	 * Getter for the line ending point.
	 * 
	 * @return ending point
	 */
	public Point getB() {
		return b;
	}

	/**
	 * Setter for the line ending point.
	 * 
	 * @param b
	 *            ending point
	 */
	public void setB(Point b) {
		this.b = b;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
		setName();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String toText() {
		StringBuilder sb = new StringBuilder("LINE ");
		sb.append(a.x).append(" ");
		sb.append(a.y).append(" ");
		sb.append(b.x).append(" ");
		sb.append(b.y).append(" ");
		sb.append(outlineColor.getRed()).append(" ");
		sb.append(outlineColor.getGreen()).append(" ");
		sb.append(outlineColor.getBlue());
		return sb.toString();
	}

	/**
	 * Method used to calculate the name of the object.
	 */
	private final void setName() {
		StringBuilder sb = new StringBuilder("Line (");
		sb.append(getA().x);
		sb.append(",");
		sb.append(getA().y);
		sb.append(")-(");
		sb.append(getB().x);
		sb.append(",");
		sb.append(getB().y);
		sb.append(")");
		this.name = sb.toString();
	}
}
