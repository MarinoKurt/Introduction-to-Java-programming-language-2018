package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Geometrical object visitor that paints the stored objects on the graphic it's
 * initialized with.
 * 
 * @author MarinoK
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Graphics to draw with.
	 */
	private Graphics2D g2d;

	/**
	 * Constructor for the geometrical object painter.
	 * 
	 * @param g2d
	 *            graphics
	 * 
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		g2d.setColor(line.getOutlineColor());
		g2d.drawLine(line.getA().x, line.getA().y, line.getB().x, line.getB().y);
	}

	@Override
	public void visit(Circle circle) {
		drawCircle(circle.getCenter(), circle.getRadius(), circle.getOutlineColor(), null);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		drawCircle(filledCircle.getCenter(), filledCircle.getRadius(), filledCircle.getOutlineColor(),
				filledCircle.getFillColor());
	}

	/**
	 * Auxiliary method used to draw a circle.
	 * 
	 * @param center
	 *            of the circle
	 * @param radius
	 *            of the circle
	 * @param fgColor
	 *            of the circle
	 * @param bgColor
	 *            of the circle, null if it's an empty circle
	 */
	private void drawCircle(Point center, double radius, Color fgColor, Color bgColor) {
		g2d.setColor(fgColor);
		g2d.drawOval((int) (center.x - radius), (int) (center.y - radius), (int) (2 * radius), (int) (2 * radius));
		if (bgColor == null) return;
		g2d.setColor(bgColor);
		g2d.fillOval((int) (center.x - radius), (int) (center.y - radius), (int) (2 * radius), (int) (2 * radius));
	}

	@Override
	public void visit(FilledPolygon filledPolygon) {
		List<Point> list = filledPolygon.getPoints();
		for (Point a : list) {
			int i = list.indexOf(a);
			int j = i - 1;
			int k = i + 1;
			boolean jotB = false;
			boolean jotK = false;
			Point jot = null;
			Point ka = null;
			g2d.setColor(filledPolygon.getOutlineColor());
			if (j >= 0 && j < list.size()) {
				jot = list.get(j);
				g2d.drawLine(jot.x, jot.y, a.x, a.y);
				jotB = true;
			}
			if (k >= 0 && k < list.size()) {
				ka = list.get(k);
				g2d.drawLine(ka.x, ka.y, a.x, a.y);
				jotK = true;
			}

			if (jotB && jotK) {
				g2d.setColor(filledPolygon.getFillColor());
				g2d.fillPolygon(new int[] { a.x, jot.x, ka.x }, new int[] { a.y, jot.y, ka.y }, 3);
			}
		}

	}
}
