package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.color.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

public class FPolygonTool implements Tool {

	private List<Point> potentialPoints;
	private List<Point> tempPoints;
	private boolean currentlyDrawing = false;
	private DrawingModel model;
	private IColorProvider fgColorProvider;
	private IColorProvider bgColorProvider;
	private JDrawingCanvas canvas;
	boolean moving;

	public FPolygonTool(DrawingModel model, IColorProvider fgColorProvider, IColorProvider bgColorProvider,
			JDrawingCanvas canvas) {
		this.model = model;
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		this.canvas = canvas;
		this.potentialPoints = new ArrayList<>();
		this.tempPoints = new ArrayList<>();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		moving = false;
		if (SwingUtilities.isRightMouseButton(e)) {
			potentialPoints.clear();
			currentlyDrawing = false;
		}

		if (!currentlyDrawing) {
			Point point = e.getPoint();
			potentialPoints.add(point);
			currentlyDrawing = true;
		} else {
			Point point = e.getPoint();
			if (tooClose(point) != null) {
				System.out.println("UŠAO TOO CLOSE");
				if (potentialPoints.size() >= 3 && tooClose(point).equals(potentialPoints.get(0))) {
					potentialPoints.add(point);
					FilledPolygon fp = new FilledPolygon(fgColorProvider.getCurrentColor(),
							bgColorProvider.getCurrentColor(), new ArrayList<>(potentialPoints));
					if (fp.isConvex()) {
						model.add(fp);
						fp.addGeometricalObjectListener((GeometricalObjectListener) model);
						potentialPoints.clear();
					} else {
						JOptionPane.showConfirmDialog(canvas, "CONVEX! try again", "Error",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
					}
					currentlyDrawing = false;
				} else {
					// ignore click?
				}
			} else {
				potentialPoints.add(point);
			}

		}

	}

	private Point tooClose(Point point) {
		for (Point former : potentialPoints) {
			if (former.distance(point) <= 3) {
				return former;
			}
		}
		return null;
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (currentlyDrawing) {
			moving = true;
			tempPoints.clear();
			tempPoints.addAll(potentialPoints);
			tempPoints.add(e.getPoint());
			canvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics2D g2d) {
		if (potentialPoints == null) return;
		FilledPolygon fp = new FilledPolygon(fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor(),
				potentialPoints);
		if (moving) {
			fp = new FilledPolygon(fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor(), tempPoints);
		}
		fp.accept(new GeometricalObjectPainter(g2d));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
