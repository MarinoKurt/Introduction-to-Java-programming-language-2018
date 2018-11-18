package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Model of a filled circle.
 * 
 * @author MarinoK
 */
public class FilledCircle extends Circle {

	/**
	 * Color that fills this circle.
	 */
	private Color fillColor;

	/**
	 * Constructor for the filled circle.
	 * 
	 * @param center
	 *            of the circle
	 * @param radius
	 *            of the circle
	 * @param outlineColor
	 *            of the circle
	 * @param fillColor
	 *            of the circle
	 */
	public FilledCircle(Point center, double radius, Color outlineColor, Color fillColor) {
		super(center, radius, outlineColor);
		this.fillColor = fillColor;
		this.setName();
	}

	/**
	 * Method used to calculate the name of the object.
	 */
	private final void setName() {
		StringBuilder sb = new StringBuilder("Filled circle (");
		sb.append(getCenter().x);
		sb.append(",");
		sb.append(getCenter().y);
		sb.append("), ");
		sb.append(Math.round(getRadius()));
		sb.append(", #");
		sb.append(Integer.toHexString(fillColor.getRGB()).substring(2).toUpperCase());
		this.name = sb.toString();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/**
	 * Getter for the fill color.
	 * 
	 * @return fill color
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * Setter for the fill color.
	 * 
	 * @param fillColor
	 *            of the circle
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	@Override
	public String toText() {
		StringBuilder sb = new StringBuilder("F");
		sb.append(super.toText()).append(" ");
		sb.append(fillColor.getRed()).append(" ");
		sb.append(fillColor.getGreen()).append(" ");
		sb.append(fillColor.getBlue());
		return sb.toString();
	}

}
