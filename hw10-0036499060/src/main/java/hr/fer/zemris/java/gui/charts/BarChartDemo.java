package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstration of the bar chart drawing.
 * 
 * @author MarinoK
 */
public class BarChartDemo extends JFrame {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Contains all relevant information for drawing the bar chart. */
	private static BarChart chart;

	/** Path of the input file. */
	private static Path path;

	/**
	 * Constructor for the BarChartDemo.
	 */
	public BarChartDemo() {
		setLocation(300, 300);
		setSize(600, 600);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	/**
	 * Main method runs when the program is run.
	 * 
	 * @param args
	 *            expected path to the input file
	 */
	public static void main(String[] args) {
		Scanner input = null;
		try {
			path = Paths.get(args[0]);
			// uncomment the line below to access the file easier
			// path = Paths.get(".\\src\\main\\resources\\data5.txt"); // data1-5 available

			input = new Scanner(path);
		} catch (InvalidPathException | NoSuchFileException pathProblem) {
			System.err.println(pathProblem.getMessage());
			System.out.println("Invalid path. Exiting.");
			System.exit(1);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			System.err.println("Problem with the input. Exiting.");
			System.exit(1);
		}
		try {
			String yDescription = input.nextLine();
			String xDescription = input.nextLine();
			String[] coordinates = input.nextLine().split("\\s+");
			List<XYValue> coordinateList = new ArrayList<>();
			for (String c : coordinates) {
				String[] ints = c.split(",");
				int x = Integer.valueOf(ints[0]);
				int y = Integer.valueOf(ints[1]);
				coordinateList.add(new XYValue(x, y));
			}
			int yMin = Integer.valueOf(input.nextLine());
			int yMax = Integer.valueOf(input.nextLine());
			int step = Integer.valueOf(input.nextLine());

			chart = new BarChart(coordinateList, xDescription, yDescription, yMin, yMax, step);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("Problem with the input. Exiting.");
			System.exit(1);
		}

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo();
			Container cp = frame.getContentPane();
			cp.setLayout(new BorderLayout());
			cp.setBackground(new Color(130, 192, 255));
			cp.add(new BarChartComponent(chart, path.toString()), BorderLayout.CENTER);
			frame.setVisible(true);
		});
	}

}
