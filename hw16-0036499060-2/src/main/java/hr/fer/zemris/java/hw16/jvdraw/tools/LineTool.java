package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.color.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Tool used for following mouse actions and drawing an line.
 * 
 * @author MarinoK
 */
public class LineTool implements Tool {

	/**
	 * Starting point of the line.
	 */
	private Point a;

	/**
	 * Ending point of the line.
	 */
	private Point b;

	/**
	 * True if the drawing is happening.
	 */
	private boolean currentlyDrawing = false;

	/**
	 * Model containing information about the objects on screen.
	 */
	private DrawingModel model;

	/**
	 * Provider for the foreground color.
	 */
	private IColorProvider fgColorProvider;

	/**
	 * Canvas to paint on.
	 */
	private JDrawingCanvas canvas;

	/**
	 * Constructor for the LineTool.
	 * 
	 * @param model
	 *            of the drawing
	 * @param fgColorProvider
	 *            for the foreground color
	 * @param canvas
	 *            to paint on
	 */
	public LineTool(DrawingModel model, IColorProvider fgColorProvider, JDrawingCanvas canvas) {
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
		if (currentlyDrawing) {
			b = e.getPoint();
			Line l = new Line(a, b, fgColorProvider.getCurrentColor());
			l.addGeometricalObjectListener((GeometricalObjectListener) model);
			a = b = null;
			model.add(l);
			currentlyDrawing = false;
		} else {
			a = e.getPoint();
			currentlyDrawing = true;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentlyDrawing) {
			b = e.getPoint();
			canvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (a == null || b == null || g2d == null) return;
		Line l = new Line(a, b, fgColorProvider.getCurrentColor());
		l.accept(new GeometricalObjectPainter(g2d));
	}

}
