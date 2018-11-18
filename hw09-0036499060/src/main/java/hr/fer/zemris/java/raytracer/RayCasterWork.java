package hr.fer.zemris.java.raytracer;

import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Implementation of the recursive action interface used for dividing the work
 * of rendering the scene to multiple threads.
 * 
 * @author MarinoK
 */
public class RayCasterWork extends RecursiveAction {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Used to determine whether the job is small enough to be computed at once. */
	private static final int WORK_SIZE_THRESHOLD = 16;

	/** Coordinates of the corner of the mapped screen. */
	private static Point3D screenCorner;

	/** Height of the mapped screen. */
	private static int height;

	/** Width of the mapped screen. */
	private static int width;

	/** Horizontal value used for determine current screen point. */
	private static double horizontal;

	/** Vertical value used for determine current screen point. */
	private static double vertical;

	/** Vector of the x axis. */
	private static Point3D xAxis;

	/** Vector of the y axis. */
	private static Point3D yAxis;

	/** Coordinates of the observer. */
	private static Point3D eye;

	/** Array containing values for the color red. */
	private static short[] red;

	/** Array containing values for the color green. */
	private static short[] green;

	/** Array containing values for the color blue. */
	private static short[] blue;

	/** Current display containing all objects and light sources. */
	private static Scene scene;

	/** Minimum y value to color. */
	private int yMin;

	/** Maximum y value to color. */
	private int yMax;

	/**
	 * Used for initializing this RayCasterWork.
	 * 
	 * @param screenCornerG
	 *            Coordinates of the corner of the mapped screen
	 * @param heightG
	 *            Height of the mapped screen
	 * @param widthG
	 *            Width of the mapped screen
	 * @param horizontalG
	 *            Horizontal value used for determine current screen point
	 * @param verticalG
	 *            Vertical value used for determine current screen point
	 * @param xAxisG
	 *            Vector of the x axis
	 * @param yAxisG
	 *            Vector of the y axis
	 * @param eyeG
	 *            Coordinates of the observer
	 * @param redG
	 *            Array containing values for the color red
	 * @param greenG
	 *            Array containing values for the color green
	 * @param blueG
	 *            Array containing values for the color blue
	 * @param sceneG
	 *            Current display containing all objects and light sources
	 */
	public static void init(Point3D screenCornerG, int heightG, int widthG, double horizontalG, double verticalG,
			Point3D xAxisG, Point3D yAxisG, Point3D eyeG, short[] redG, short[] greenG, short[] blueG, Scene sceneG) {
		screenCorner = screenCornerG;
		height = heightG;
		width = widthG;
		horizontal = horizontalG;
		vertical = verticalG;
		xAxis = xAxisG;
		yAxis = yAxisG;
		eye = eyeG;
		red = redG;
		green = greenG;
		blue = blueG;
		scene = sceneG;
	}

	/**
	 * Default constructor for the RayCasterWork.
	 * 
	 * @param yMin
	 *            Minimum y value to color
	 * @param yMax
	 *            Maximum y value to color
	 */
	public RayCasterWork(int yMin, int yMax) {
		this.yMin = yMin;
		this.yMax = yMax;
	}

	@Override
	protected void compute() {
		System.out.println(yMax + " min: " + yMin);
		int difference = yMax - yMin;
		if (difference <= WORK_SIZE_THRESHOLD) {
			computeDirect();
			return;
		}
		RayCasterWork p1 = new RayCasterWork(yMin, yMax - difference / 2);
		RayCasterWork p2 = new RayCasterWork(yMax - difference / 2, yMax);
		invokeAll(p1, p2);
	}

	/**
	 * Method used for the actual work. Called only when the work is divided into
	 * small enough pieces, according the threshold.
	 */
	private void computeDirect() {
		RayCasterFunctions.colorize(yMin, yMax, width, height, horizontal, vertical, xAxis, yAxis, screenCorner, eye,
				scene, red, green, blue);
	}
}
