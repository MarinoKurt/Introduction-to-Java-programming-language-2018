package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * Interface for the drawing model listeners.
 * 
 * @author MarinoK
 */
public interface DrawingModelListener {

	/**
	 * Called upon whenever objects are added.
	 * 
	 * @param source
	 *            model
	 * @param index0
	 *            beginning of the change
	 * @param index1
	 *            end of the change
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Called upon whenever objects are removed.
	 * 
	 * @param source
	 *            model
	 * @param index0
	 *            beginning of the change
	 * @param index1
	 *            end of the change
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Called upon whenever objects are changed.
	 * 
	 * @param source
	 *            model
	 * @param index0
	 *            beginning of the change
	 * @param index1
	 *            end of the change
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}