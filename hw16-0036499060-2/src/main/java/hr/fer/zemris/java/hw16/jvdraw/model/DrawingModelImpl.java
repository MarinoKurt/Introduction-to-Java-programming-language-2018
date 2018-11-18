package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Implementation of the drawing model used to keep track of the objects in the
 * picture.
 * 
 * @author MarinoK
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener, ModificationSubject {

	/**
	 * List of geometrical objects in the model.
	 */
	private List<GeometricalObject> objects;

	/**
	 * List of drawing listeners.
	 */
	private List<DrawingModelListener> drawingListeners;

	/**
	 * List of modification listeners.
	 */
	private List<ModificationListener> modificationListeners;

	/**
	 * Constructor for the drawing model implementation.
	 */
	public DrawingModelImpl() {
		this.objects = new ArrayList<>();
		this.drawingListeners = new ArrayList<>();
		this.modificationListeners = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(Objects.requireNonNull(object));
		System.out.println("DODAN OBJEKT");
		drawingListeners.forEach(l -> l.objectsAdded(this, getSize(), getSize()));
		modificationListeners.forEach(l -> l.changeOccured());
	}

	@Override
	public void remove(int index) {
		objects.remove(index);
		drawingListeners.forEach(l -> l.objectsRemoved(this, getSize(), getSize()));
		modificationListeners.forEach(l -> l.changeOccured());
	}

	@Override
	public void changeOrder(int index, int offset) {
		if (index + offset < 0 || index + offset >= objects.size()) {
			return;
		}
		GeometricalObject obj = objects.get(index);
		objects.remove(index);
		objects.add(index + offset, obj);
		drawingListeners.forEach(l -> l.objectsChanged(this, index, index + offset));
		modificationListeners.forEach(l -> l.changeOccured());
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		drawingListeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		drawingListeners.remove(Objects.requireNonNull(l));
	}

	@Override
	public void clear() {
		int exSize = objects.size();
		objects.clear();
		drawingListeners.forEach(l -> l.objectsRemoved(this, 0, exSize));
		modificationListeners.forEach(l -> l.changeOccured());
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		drawingListeners.forEach(l -> l.objectsChanged(this, 0, objects.size()));
		modificationListeners.forEach(l -> l.changeOccured());
	}

	@Override
	public void addModificationListener(ModificationListener l) {
		modificationListeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeModificationListener(ModificationListener l) {
		modificationListeners.remove(Objects.requireNonNull(l));
	}

}
