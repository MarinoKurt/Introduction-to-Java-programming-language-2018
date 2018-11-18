package hr.fer.zemris.java.hw16.jvdraw.visitors;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Interface for the geometrical object visitors. Has a special method for every
 * sort of a geometrical object supported.
 * 
 * @author MarinoK
 */
public interface GeometricalObjectVisitor {

	/**
	 * Visits the given line.
	 * 
	 * @param line
	 *            to visit
	 */
	public abstract void visit(Line line);

	/**
	 * Visits the given circle.
	 * 
	 * @param circle
	 *            to visit
	 */
	public abstract void visit(Circle circle);

	/**
	 * Visits the given filled circle.
	 * 
	 * @param filledCircle
	 *            to visit
	 */
	public abstract void visit(FilledCircle filledCircle);

	public abstract void visit(FilledPolygon filledPolygon);
}