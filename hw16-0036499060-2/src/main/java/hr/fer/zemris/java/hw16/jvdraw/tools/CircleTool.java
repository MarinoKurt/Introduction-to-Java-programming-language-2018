package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.color.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Tool used for following mouse actions and drawing an empty circle.
 * 
 * @author MarinoK
 */
public class CircleTool implements Tool {

	/**
	 * Center of the circle.
	 */
	protected Point center;

	/**
	 * Radius of the circle.
	 */
	protected double radius;

	/**
	 * True if the drawing is happening.
	 */
	protected boolean currentlyDrawing;

	/**
	 * Model containing information about the objects on screen.
	 */
	protected DrawingModel model;

	/**
	 * Foreground color provider.
	 */
	protected IColorProvider fgColorProvider;

	/**
	 * Canvas to paint on.
	 */
	protected JDrawingCanvas canvas;

	/**
	 * Constructor for the circle tool.
	 * 
	 * @param model
	 *            of the drawing
	 * @param fgColorProvider
	 *            for the color
	 * @param canvas
	 *            to paint on
	 */
	public CircleTool(DrawingModel model, IColorProvider fgColorProvider, JDrawingCanvas canvas) {
		this.model = model;
		this.fgColorProvider = fgColorProvider;
		this.canvas = canvas;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!currentlyDrawing) {
			center = e.getPoint();
			currentlyDrawing = true;
		} else {
			radius = e.getPoint().distance(center);
			Circle c = new Circle(center, radius, fgColorProvider.getCurrentColor());
			model.add(c);
			c.addGeometricalObjectListener((GeometricalObjectListener) model);
			center = null;
			currentlyDrawing = false;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentlyDrawing) {
			radius = e.getPoint().distance(center);
			canvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (center == null) return;
		Circle c = new Circle(center, radius, fgColorProvider.getCurrentColor());
		c.accept(new GeometricalObjectPainter(g2d));
	}

}
