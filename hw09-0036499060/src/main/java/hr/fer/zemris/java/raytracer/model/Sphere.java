package hr.fer.zemris.java.raytracer.model;

/**
 * Implementation of a graphical object, representing a sphere.
 * 
 * @author MarinoK
 */
public class Sphere extends GraphicalObject {

	/** Center point of the sphere. */
	private Point3D center;

	/** Radius of the sphere. */
	private double radius;

	/** Coefficient for the red diffuse component of the light. */
	private double kdr;

	/** Coefficient for the green diffuse component of the light. */
	private double kdg;

	/** Coefficient for the blue diffuse component of the light. */
	private double kdb;

	/** Coefficient for the red reflective component of the light. */
	private double krr;

	/** Coefficient for the green reflective component of the light. */
	private double krg;

	/** Coefficient for the blue reflective component of the light. */
	private double krb;

	/** Coefficient describing the graining of the surface. */
	private double krn;

	/**
	 * @param center
	 *            Center point of the sphere
	 * @param radius
	 *            Radius of the sphere
	 * @param kdr
	 *            Coefficient for the red diffuse component of the light
	 * @param kdg
	 *            Coefficient for the green diffuse component of the light
	 * @param kdb
	 *            Coefficient for the blue diffuse component of the light
	 * @param krr
	 *            Coefficient for the red reflective component of the light
	 * @param krg
	 *            Coefficient for the green reflective component of the light
	 * @param krb
	 *            Coefficient for the blue reflective component of the light
	 * @param krn
	 *            Coefficient describing the graining of the surface
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		
		double distanceFromEye;
		Point3D O = ray.start;
		Point3D dir = ray.direction;
		Point3D CO = O.sub(center);

		// Taken from: https://en.wikipedia.org/wiki/Line%E2%80%93sphere_intersection
		double underTheRoot = dir.scalarProduct(CO) * dir.scalarProduct(CO) - CO.norm()*CO.norm() + radius * radius;

		if (underTheRoot < 0) {
			return null;
		} else if (underTheRoot > 0) {
			double d1 = -(dir.scalarProduct(CO)) + Math.sqrt(underTheRoot);
			double d2 = -(dir.scalarProduct(CO)) - Math.sqrt(underTheRoot);
			distanceFromEye = d1 < d2 ? d1 : d2;
		} else {
			distanceFromEye = -(dir.scalarProduct(CO));
		}
		return new SphereIntersection(O.add(dir.scalarMultiply(distanceFromEye)), distanceFromEye, true);
	}
	

	/**
	 * Implementation of the ray intersection for spheres.
	 */
	private class SphereIntersection extends RayIntersection {

		/**
		 * Constructor for the SphereIntersection.
		 * 
		 * @param point
		 *            of the intersection
		 * @param distance
		 *            to the eye
		 * @param outer
		 *            is the point outside of the object
		 */
		public SphereIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}

		@Override
		public Point3D getNormal() {
			return getPoint().sub(center).normalize();
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdb() {
			return kdb;
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKrn() {
			return krn;
		}

	}
}
