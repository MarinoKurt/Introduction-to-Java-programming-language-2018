package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program used for displaying 3d graphic using ray casting.
 * 
 * @author MarinoK
 */
public class RayCaster {

	/**
	 * Main method runs when the program is run.
	 * 
	 * @param args
	 *            usage not expected
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Getter for the IRayTracerProducer.
	 * 
	 * @return IRayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return (Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width, int height,
				long requestNo, IRayTracerResultObserver observer) -> {

			System.out.println("Započinjem izračune...");

			short[] red = new short[width * height];
			short[] green = new short[width * height];
			short[] blue = new short[width * height];

			Point3D VUV = viewUp.normalize();
			Point3D zAxis = view.sub(eye).normalize();
			Point3D yAxis = VUV.sub(zAxis.scalarMultiply(zAxis.scalarProduct(VUV))).normalize();
			Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
			Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.0))
					.add(yAxis.scalarMultiply(vertical / 2.0));
			Scene scene = RayTracerViewer.createPredefinedScene();

			RayCasterFunctions.colorize(0, height, width, height, horizontal, vertical, xAxis, yAxis, screenCorner, eye,
					scene, red, green, blue);

			System.out.println("Izračuni gotovi...");
			observer.acceptResult(red, green, blue, requestNo);
			System.out.println("Dojava gotova...");
		};

	}

}
