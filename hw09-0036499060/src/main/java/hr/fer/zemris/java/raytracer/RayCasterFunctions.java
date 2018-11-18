package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Utility class containing methods for rendering the scene.
 * 
 * @author MarinoK
 */
public class RayCasterFunctions {

	/** Constant used for comparing double values. */
	private static final double EPSILON = 1E-6;

	/**
	 * Method used to determine whether the ray should be colored (if there is an
	 * intersection), or stay black (else).
	 * 
	 * @param scene
	 *            of the display
	 * @param sight
	 *            ray from the human eye
	 * @param rgb
	 *            array of rgb values used for coloring
	 */
	protected static void tracer(Scene scene, Ray sight, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection intersection = findClosestIntersection(scene, sight);

		if (intersection == null) return;

		determineColorFor(scene, sight, intersection, rgb);
	}

	/**
	 * Method used to determine the color for the given ray intersection.
	 * 
	 * @param scene
	 *            of the display
	 * @param sight
	 *            ray from the human eye
	 * @param intersection
	 *            intersection with the object
	 * @param rgb
	 *            array of rgb values for coloring
	 */
	private static void determineColorFor(Scene scene, Ray sight, RayIntersection intersection, short[] rgb) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;

		List<LightSource> lights = scene.getLights();
		for (LightSource lamp : lights) {
			Ray ray = Ray.fromPoints(lamp.getPoint(), intersection.getPoint());
			RayIntersection obscuringIntersection = findClosestIntersection(scene, ray);
			if (!(obscuringIntersection == null)) {
				double s2ToLamp = lamp.getPoint().sub(obscuringIntersection.getPoint()).norm();
				double sToLamp = lamp.getPoint().sub(intersection.getPoint()).norm();

				if (s2ToLamp < sToLamp - EPSILON) {
					continue;
				}
			}

			Point3D l = lamp.getPoint().sub(intersection.getPoint()).normalize();
			Point3D n = intersection.getNormal().normalize();

			double ln = l.scalarProduct(n);

			if (ln < 0) continue;

			rgb[0] += (short) (lamp.getR() * intersection.getKdr() * ln);
			rgb[1] += (short) (lamp.getG() * intersection.getKdg() * ln);
			rgb[2] += (short) (lamp.getB() * intersection.getKdb() * ln);

			Point3D r = (n.scalarMultiply(2 * (l.scalarProduct(n)))).sub(l).normalize();
			Point3D v = sight.start.sub(intersection.getPoint()).normalize();
			double rv = r.scalarProduct(v);

			if (rv < 0) continue;

			double x = Math.pow(rv, intersection.getKrn());
			rgb[0] += (short) (lamp.getR() * intersection.getKrr() * x);
			rgb[1] += (short) (lamp.getG() * intersection.getKrg() * x);
			rgb[2] += (short) (lamp.getB() * intersection.getKrb() * x);

		}
	}

	/**
	 * Method searches for the intersection of the given ray, that is closest to the
	 * ray source.
	 * 
	 * @param scene
	 *            of the display
	 * @param ray
	 *            from the source to the object
	 * @return rayIntersection closest to the source of the ray
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {

		List<GraphicalObject> allObjects = scene.getObjects();
		RayIntersection minDistance = null;
		RayIntersection candidate = null;
		for (GraphicalObject object : allObjects) {
			candidate = object.findClosestRayIntersection(ray);
			if (candidate != null) {
				if (minDistance == null || candidate.getDistance() < minDistance.getDistance()) {
					minDistance = candidate;
				}
			}
		}
		return minDistance;
	}

	/**
	 * @param yMin
	 *            Minimal y value
	 * @param yMax
	 *            Maximal y value
	 * @param screenCorner
	 *            Coordinates of the corner of the mapped screen
	 * @param height
	 *            Height of the mapped screen
	 * @param width
	 *            Width of the mapped screen
	 * @param horizontal
	 *            Horizontal value used for determine current screen point
	 * @param vertical
	 *            Vertical value used for determine current screen point
	 * @param xAxis
	 *            Vector of the x axis
	 * @param yAxis
	 *            Vector of the y axis
	 * @param eye
	 *            Coordinates of the observer
	 * @param red
	 *            Array containing values for the color red
	 * @param green
	 *            Array containing values for the color green
	 * @param blue
	 *            Array containing values for the color blue
	 * @param scene
	 *            Current display containing all objects and light sources
	 */
	public static void colorize(int yMin, int yMax, int width, int height, double horizontal, double vertical,
			Point3D xAxis, Point3D yAxis, Point3D screenCorner, Point3D eye, Scene scene, short[] red, short[] green,
			short[] blue) {
		short[] rgb = new short[3];

		int offset = yMin * width;

		for (int y = yMin; y < yMax; y++) {
			for (int x = 0; x < width; x++) {

				Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1.0)))
						.sub(yAxis.scalarMultiply(vertical * y / (height - 1.0)));
				Ray sight = Ray.fromPoints(eye, screenPoint);
				RayCasterFunctions.tracer(scene, sight, rgb);
				red[offset] = rgb[0] > 255 ? 255 : rgb[0];
				green[offset] = rgb[1] > 255 ? 255 : rgb[1];
				blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
				offset++;
			}
		}
	}
}
