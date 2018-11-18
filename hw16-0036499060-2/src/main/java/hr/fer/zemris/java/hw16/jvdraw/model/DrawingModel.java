package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Interface offers methods to keep track of the drawed objects.
 * 
 * @author MarinoK
 */
public interface DrawingModel {

	/**
	 * Getter for the model size.
	 * 
	 * @return number of objects in the model
	 */
	public int getSize();

	/**
	 * Fetches the object at the given index.
	 * 
	 * @param index
	 *            of the required object
	 * @return object at the given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds the given geometrical object to the model.
	 * 
	 * @param object
	 *            to add
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes the geometrical object at the given index from the model.
	 * 
	 * @param index
	 *            to remove
	 */
	public void remove(int index);

	/**
	 * Moves the object for offset number of index in the list.
	 * 
	 * @param index
	 *            of the object to move
	 * @param offset
	 *            to move the object for
	 */
	public void changeOrder(int index, int offset);

	/**
	 * Clears all the objects from the model.
	 */
	public void clear();

	/**
	 * Adds the given drawing model listener to the list of listeners.
	 * 
	 * @param l
	 *            listener to add
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the given drawing model listener from the list of listeners.
	 * 
	 * @param l
	 *            listener to remove
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

}