package hr.fer.zemris.java.hw16.jvdraw.export;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Visitor which collects the text representations of all the objects in the
 * current model.
 * 
 * @author MarinoK
 */
public class TextCollector implements GeometricalObjectVisitor {

	/** String builder for the collector. */
	private StringBuilder sb;

	/**
	 * Default constructor for the text collector.
	 */
	public TextCollector() {
		sb = new StringBuilder();
	}

	@Override
	public void visit(Line line) {
		sb.append(line.toText());
		sb.append("\n");
	}

	@Override
	public void visit(Circle circle) {
		sb.append(circle.toText());
		sb.append("\n");
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		sb.append(filledCircle.toText());
		sb.append("\n");
	}

	/**
	 * Fetches the gathered text
	 * 
	 * @return textual representation of the current model
	 */
	public String getText() {
		return sb.toString();
	}

	@Override
	public void visit(FilledPolygon filledPolygon) {
		sb.append(filledPolygon.toText());
		sb.append("\n");
	}

}
