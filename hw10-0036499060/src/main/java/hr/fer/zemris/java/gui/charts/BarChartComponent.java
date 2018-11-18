package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JComponent;

/**
 * Component that draws a bar chart.
 * 
 * @author MarinoK
 */
public class BarChartComponent extends JComponent {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Contains all relevant information for drawing the bar chart. */
	private BarChart chart;

	/** Path of the input file as a string. */
	private String path;

	/** Inset on the west side of the component. */
	private final static int WEST_INSET = 60;

	/** Inset on the south side of the component. */
	private final static int SOUTH_INSET = 60;

	/** Inset on the east side of the component. */
	private final static int EAST_INSET = 30;

	/** Inset on the north side of the component. */
	private final static int NORTH_INSET = 30;

	/**
	 * Constructor for the BarCharComponent.
	 * 
	 * @param chart
	 *            data for drawing
	 * @param path
	 *            of the input file, to be written
	 */
	public BarChartComponent(BarChart chart, String path) {
		this.chart = chart;
		this.path = path;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int h = this.getHeight();
		int w = this.getWidth();
		XYValue origin = new XYValue(WEST_INSET, h - SOUTH_INSET);
		XYValue upperRight = new XYValue(w - EAST_INSET, NORTH_INSET);

		if (chart == null) return;
		g2d.setFont(new Font("Arial", Font.BOLD, 15));
		g2d.setColor(Color.BLACK);

		// yDescription positioning
		String yD = chart.getxDescription();
		int len = g2d.getFontMetrics().stringWidth(yD);
		int x = (int) ((h / 2.0) + ((len) / 2.0));
		int y = WEST_INSET / 3;
		// rotation
		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		g2d.drawString(yD, -x, y);
		g2d.setTransform(defaultAt);

		// yDescription positioning
		String xD = chart.getyDescription();
		len = g2d.getFontMetrics().stringWidth(xD);
		x = (int) ((w / 2.0) - ((len) / 2.0));
		y = h - SOUTH_INSET / 3;
		g2d.drawString(xD, x, y);

		// y coordinate marks positioning
		int yMin = chart.getyMin();
		int yMax = chart.getyMax();
		int step = chart.getStep();
		int numOfYs = (int) ((yMax - yMin) / (double) step) + 1;
		int fontHeight = g2d.getFontMetrics().getHeight();
		double chunkHeight = (origin.getY() / (double) numOfYs);
		for (int yVal = yMin, i = 0; yVal <= yMax; yVal += step, i++) {
			x = WEST_INSET * 2 / 3;
			y = (int) (origin.getY() - chunkHeight * i + fontHeight / 4.0);
			g2d.drawString(String.valueOf(yVal), x, y);
		}

		// x coordinate marks positioning
		int chunkWidth = 0;
		Set<Integer> distinctXs = new TreeSet<>();
		for (XYValue val : chart.getValueList()) {
			distinctXs.add(val.getX());
		}
		int numOfXs = distinctXs.size();
		int i = 0;
		for (int xVal : distinctXs) {
			int fontWidth = g2d.getFontMetrics().stringWidth(String.valueOf(xVal));
			y = h - SOUTH_INSET * 2 / 3;
			chunkWidth = (int) ((w - WEST_INSET - EAST_INSET) / (double) numOfXs);
			x = (int) (origin.getX() + i * chunkWidth + chunkWidth/2.0 - fontWidth/2.0);
			g2d.drawString(String.valueOf(xVal), x, y);
			i++;
		}

		// coordinate lines, arrows
		g2d.setColor(new Color(255, 255, 0));
		g2d.drawLine(origin.getX(), origin.getY(), WEST_INSET, upperRight.getY());
		g2d.drawLine(origin.getX(), origin.getY(), upperRight.getX(), origin.getY());
		Polygon triangle1 = new Polygon();
		triangle1.addPoint(origin.getX(), upperRight.getY() - 5);
		triangle1.addPoint(origin.getX() + 5, upperRight.getY());
		triangle1.addPoint(origin.getX() - 5, upperRight.getY());
		g2d.drawPolygon(triangle1);
		Polygon triangle2 = new Polygon();
		triangle2.addPoint(upperRight.getX() + 5, origin.getY());
		triangle2.addPoint(upperRight.getX(), origin.getY() + 5);
		triangle2.addPoint(upperRight.getX(), origin.getY() - 5);
		g2d.drawPolygon(triangle2);

		// horizontal grid
		g2d.setColor(Color.BLACK);
		for (int j = 1; j < numOfYs; j++) {
			int x1 = origin.getX();
			int y1 = (int) (h - SOUTH_INSET - j * chunkHeight);
			int x2 = (int) (w - WEST_INSET / 2.0);
			g2d.drawLine(x1, y1, x2, y1);
		}

		// bars
		List<XYValue> dataList = chart.getValueList();
		dataList.sort((o1, o2) -> Integer.compare(o1.getX(), o2.getX()));
		i = 0;
		for (XYValue coordinate : dataList) {
			int coef = (int) ((coordinate.getY() - yMin) * chunkHeight / (double) step);
			int x1 = origin.getX() + i * chunkWidth;
			int y1 = (int) (origin.getY() - coef);
			g2d.setColor(new Color(107, 106, 102));
			g2d.fillRect(x1 + 6, y1 + 5, chunkWidth - 1, coef - 6);
			g2d.setColor(new Color(152, 0, 255));
			g2d.fillRect(x1 + 1, y1, chunkWidth - 1, coef);
			i++;
		}

		// vertical grid
		g2d.setColor(Color.BLACK);
		for (int j = 1; j < numOfXs; j++) {
			int x1 = origin.getX() + j * chunkWidth;
			int y1 = h - SOUTH_INSET;
			g2d.drawLine(x1, y1, x1, (int) (SOUTH_INSET / 2.0));
		}

		// path
		len = g2d.getFontMetrics().stringWidth(path);
		x = (int) ((w / 2.0) - ((len) / 2.0));
		y = NORTH_INSET * 2 / 3;
		g2d.drawString(path, x, y);
	}
}
