package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.model.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Represents a geometrical object, Subject in the Listener form.
 * 
 * @author MarinoK
 */
public abstract class GeometricalObject {

	/**
	 * List of listeners of the geometrical object.
	 */
	protected List<GeometricalObjectListener> listeners;

	/**
	 * Color of the circle outline.
	 */
	protected Color outlineColor;

	/**
	 * Unique name of the object.
	 */
	protected String name;

	/**
	 * Constructor for the geometrical object.
	 * 
	 * @param outlineColor
	 *            color
	 */
	public GeometricalObject(Color outlineColor) {
		this.listeners = new ArrayList<>();
		this.outlineColor = outlineColor;
	}

	/**
	 * Method used for accepting visitors.
	 * 
	 * @param v
	 *            visitor to accept
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Geometrical object editor factory.
	 * 
	 * @return geometrical object editor for the specific object
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Method used for exporting the object as a text.
	 * 
	 * @return text representation of the object
	 */
	public abstract String toText();

	/**
	 * Adds the given geometrical object listener to the list.
	 * 
	 * @param l
	 *            listener to add
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l);
		listeners.add(l);
	}

	/**
	 * Removes the given geometrical object listener from the list.
	 * 
	 * @param l
	 *            listener to remove
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l);
		listeners.remove(l);
	}

	/**
	 * Notifies all the listener that a change occured.
	 */
	public void fire() {
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

	/**
	 * Getter for the outline color of the circle.
	 * 
	 * @return outline color
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}

	/**
	 * Setter for the outline color of the circle.
	 * 
	 * @param outlineColor
	 *            of the object
	 */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

	@Override
	public String toString() {
		return name;
	}

}