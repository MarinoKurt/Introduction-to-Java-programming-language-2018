package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Interface determines how to alert the geometric object listener that the
 * object has changed.
 * 
 * @author MarinoK
 *
 */
public interface GeometricalObjectListener {

	/**
	 * Called upon every time the object changes.
	 * 
	 * @param o
	 *            altered object
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}