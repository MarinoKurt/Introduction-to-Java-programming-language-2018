package hr.fer.zemris.java.hw16.jvdraw.model;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Adapter that connects list model to the drawing model.
 * 
 * @author MarinoK
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Drawing model.
	 */
	private DrawingModel model;

	/**
	 * Constructor for the drawing object list model.
	 * 
	 * @param model
	 *            drawing model
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}
