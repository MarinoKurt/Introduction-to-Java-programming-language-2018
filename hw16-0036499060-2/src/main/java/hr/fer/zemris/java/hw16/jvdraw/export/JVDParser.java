package hr.fer.zemris.java.hw16.jvdraw.export;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Parser for the jvd files.
 * 
 * @author MarinoK
 */
public class JVDParser {

	/** Name of the geometrical object (line). */
	private static final String LINE = "LINE";

	/** Name of the geometrical object (circle). */
	private static final String CIRCLE = "CIRCLE";

	/** Name of the geometrical object (filled circle). */
	private static final String FILLED_CIRCLE = "FCIRCLE";

	/** Arguments of the geometrical object. */
	private static String[] args;

	/** List of read objects. */
	private static List<GeometricalObject> objects = new ArrayList<>();

	/**
	 * Used to read objects from given lines of a file.
	 * 
	 * @param lines
	 *            of the file
	 * @return list of geometrical objects
	 */
	public static List<GeometricalObject> readObjects(List<String> lines) {
		for (String line : lines) {
			if (line.isEmpty()) continue;
			args = line.split("\\s+");
			switch (args[0]) {
			case LINE:
				parseAndStoreLine();
				break;
			case CIRCLE:
				parseAndStoreCircle();
				break;
			case FILLED_CIRCLE:
				parseAndStoreFilledCircle();
				break;
			case "FPOLY":
				parseAndStorePoly();
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}
		return objects;
	}

	private static void parseAndStorePoly() {
		// TODO AKO stigneš..
		try {
			List<Point> toBe = new ArrayList<>();
			for (int k = 2; k < args.length - 7; k = k + 2) {
				Point a = new Point(Integer.valueOf(args[k]), Integer.valueOf(args[k + 1]));
				toBe.add(a);
			}
			Color c = new Color(Integer.valueOf(args[args.length - 6]), Integer.valueOf(args[args.length - 5]),
					Integer.valueOf(args.length - 4));

			Color c2 = new Color(Integer.valueOf(args[args.length - 3]), Integer.valueOf(args[args.length - 2]),
					Integer.valueOf(args[args.length - 1]));
			FilledPolygon fp = new FilledPolygon(c, c2, toBe);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Method for parsing line objects.
	 */
	private static void parseAndStoreLine() {
		try {
			Point a = new Point(Integer.valueOf(args[1]), Integer.valueOf(args[2]));
			Point b = new Point(Integer.valueOf(args[3]), Integer.valueOf(args[4]));
			Color c = new Color(Integer.valueOf(args[5]), Integer.valueOf(args[6]), Integer.valueOf(args[7]));
			Line newLine = new Line(a, b, c);
			objects.add(newLine);
		} catch (NumberFormatException | IndexOutOfBoundsException ex) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Method for parsing circle objects.
	 */
	private static void parseAndStoreCircle() {
		try {
			Point center = new Point(Integer.valueOf(args[1]), Integer.valueOf(args[2]));
			double radius = Double.parseDouble(args[3]);
			Color outlineColor = new Color(Integer.valueOf(args[4]), Integer.valueOf(args[5]),
					Integer.valueOf(args[6]));
			Circle newCircle = new Circle(center, radius, outlineColor);
			objects.add(newCircle);
		} catch (NumberFormatException | IndexOutOfBoundsException ex) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Method for parsing filled circle objects.
	 */
	private static void parseAndStoreFilledCircle() {
		try {
			Point center = new Point(Integer.valueOf(args[1]), Integer.valueOf(args[2]));
			double radius = Double.parseDouble(args[3]);
			Color outlineColor = new Color(Integer.valueOf(args[4]), Integer.valueOf(args[5]),
					Integer.valueOf(args[6]));
			Color fillColor = new Color(Integer.valueOf(args[7]), Integer.valueOf(args[8]), Integer.valueOf(args[9]));
			FilledCircle newCircle = new FilledCircle(center, radius, outlineColor, fillColor);
			objects.add(newCircle);
		} catch (NumberFormatException | IndexOutOfBoundsException ex) {
			throw new IllegalArgumentException();
		}
	}
}
