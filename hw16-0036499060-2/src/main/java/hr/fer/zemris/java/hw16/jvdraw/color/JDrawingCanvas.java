package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Canvas for drawing. JComponent which tracks the changes of the drawing model.
 * 
 * @author MarinoK
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Model of the drawing. */
	private DrawingModel model;

	/** Currently selected tool. */
	private Tool selectedTool;

	/**
	 * Constructor for the canvas.
	 * 
	 * @param model
	 *            of the drawing
	 */
	public JDrawingCanvas(DrawingModel model) {
		this.model = model;
		this.model.addDrawingModelListener(this);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GeometricalObjectPainter p = new GeometricalObjectPainter(g2d);
		for (int i = 0, n = model.getSize(); i < n; i++) {
			model.getObject(i).accept(p);
		}
		selectedTool.paint(g2d);
	}

	/**
	 * Getter for the selected tool.
	 * 
	 * @return selected drawing tool
	 */
	public Tool getSelectedTool() {
		return selectedTool;
	}

	/**
	 * Setter for the selected tool.
	 * 
	 * @param selectedTool
	 *            selected drawing tool
	 */
	public void setSelectedTool(Tool selectedTool) {
		this.selectedTool = selectedTool;
	}

}
