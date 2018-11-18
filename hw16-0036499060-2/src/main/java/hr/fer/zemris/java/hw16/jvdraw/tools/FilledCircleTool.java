package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.color.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Tool used for following mouse actions and drawing a filled circle.
 * 
 * @author MarinoK
 */
public class FilledCircleTool extends CircleTool {

	/**
	 * Provider for the background color.
	 */
	private IColorProvider bgColorProvider;

	/**
	 * Constructor for the filled circle tool.
	 * 
	 * @param model
	 *            of the drawing
	 * @param fgColorProvider
	 *            for the foreground color
	 * @param bgColorProvider
	 *            for the background color
	 * @param canvas
	 *            to draw on
	 * 
	 */
	public FilledCircleTool(DrawingModel model, IColorProvider fgColorProvider, IColorProvider bgColorProvider,
			JDrawingCanvas canvas) {
		super(model, fgColorProvider, canvas);
		this.bgColorProvider = bgColorProvider;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!currentlyDrawing) {
			center = e.getPoint();
			currentlyDrawing = true;
		} else {
			radius = e.getPoint().distance(center);
			FilledCircle fc = new FilledCircle(center, radius, fgColorProvider.getCurrentColor(),
					bgColorProvider.getCurrentColor());
			model.add(fc);
			fc.addGeometricalObjectListener((GeometricalObjectListener) model);
			center = null;
			currentlyDrawing = false;
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (center == null) return;
		FilledCircle fc = new FilledCircle(center, radius, fgColorProvider.getCurrentColor(),
				bgColorProvider.getCurrentColor());
		fc.accept(new GeometricalObjectPainter(g2d));
	}

}
