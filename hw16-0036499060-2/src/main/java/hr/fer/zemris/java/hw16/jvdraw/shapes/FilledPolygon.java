package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.Vector3;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

public class FilledPolygon extends GeometricalObject {

	private Color fillColor;
	private List<Point> points;

	public FilledPolygon(Color outlineColor, Color fillColor, List<Point> points) {
		super(outlineColor);
		this.fillColor = fillColor;
		this.points = points;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FPolygonEditor(this);
	}

	@Override
	public String toText() {
		StringBuilder sb = new StringBuilder("FPOLY");
		
		sb.append(" ");
		sb.append(points.size());

		for (Point x : points) {
			sb.append(" ");
			sb.append(x.x);
			sb.append(" ");
			sb.append(x.y);
		}
		sb.append(" ");

		sb.append(super.getOutlineColor().getRed()).append(" ");
		sb.append(super.getOutlineColor().getGreen()).append(" ");
		sb.append(super.getOutlineColor().getBlue());
		sb.append(fillColor.getRed()).append(" ");
		sb.append(fillColor.getGreen()).append(" ");
		sb.append(fillColor.getBlue());
		return sb.toString();
	}

	public boolean isConvex() {
		List<Vector3> vectors = new ArrayList<>();
		for (Point a : points) {
			vectors.add(new Vector3(a.x, a.y, 0));
		}
		List<Vector3> r = new ArrayList<>();
		int k = vectors.size();
		for (int i = 0; i < k; i++) {
			r.add(vectors.get((i + 1) % k).sub(vectors.get((i + 2) % k)));
		}
		boolean positive = false;
		if (vectors.get(0).getZ() < 1e-3) {
			positive = false;
		} else {
			positive = true;
		}
		for (Vector3 v : vectors) {
			if (positive && v.getZ() < 1e-3) {
				return false;
			}
			if (!positive && v.getZ() > 1e-3) {
				return false;
			}
		}

		return true;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "Polygon sam";
	}

}
