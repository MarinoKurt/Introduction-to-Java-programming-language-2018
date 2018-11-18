package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Calculates the bounding box of the full drawing by visiting each of its
 * elements.
 * 
 * @author MarinoK
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/** Minimal Y value. */
	private int minY = Integer.MAX_VALUE;

	/** Minimal X value. */
	private int minX = Integer.MAX_VALUE;

	/** Maximal Y value. */
	private int maxY;

	/** Maximal X value. */
	private int maxX;

	@Override
	public void visit(Line line) {
		update(line.getA().x, line.getB().x, line.getA().y, line.getB().y);
	}

	@Override
	public void visit(Circle circle) {
		int cx = circle.getCenter().x;
		int cy = circle.getCenter().y;
		int r = (int) Math.ceil(circle.getRadius()); // TODO snap?
		update(cx - r, cx + r, cy - r, cy + r);

	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visit((Circle) filledCircle);
	}

	/**
	 * Getter for the bounding box.
	 * 
	 * @return the bounding box, as a rectangle
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}

	/**
	 * Auxiliary method used to calculate the minimal bounding box for the drawing.
	 * 
	 * @param x1
	 *            minimal x value of the object
	 * @param x2
	 *            maximal x value of the object
	 * @param y1
	 *            minimal y value of the object
	 * @param y2
	 *            maximal y value of the object
	 */
	private void update(int x1, int x2, int y1, int y2) {
		minY = Math.min(Math.min(minY, y1), y2);
		maxY = Math.max(Math.max(maxY, y1), y2);
		minX = Math.min(Math.min(minX, x1), x2);
		maxX = Math.max(Math.max(maxX, x1), x2);
	}

	@Override
	public void visit(FilledPolygon filledPolygon) {
		int minYFP = Integer.MAX_VALUE;
		int maxYFP = 0;
		int minXFP = Integer.MAX_VALUE;
		int maxXFP = 0;
		
		for(Point po : filledPolygon.getPoints()) {
			minYFP = Math.min(minYFP,po.y);
			maxYFP = Math.max(maxYFP, po.y);
			minXFP = Math.min(minXFP, po.x);
			maxXFP = Math.max(maxXFP, po.x);
		}
		
		minY = Math.min(minYFP,minY);
		maxY = Math.max(maxYFP, maxY);
		minX = Math.min(minXFP, minX);
		maxX = Math.max(maxXFP, maxX);
	}
}
