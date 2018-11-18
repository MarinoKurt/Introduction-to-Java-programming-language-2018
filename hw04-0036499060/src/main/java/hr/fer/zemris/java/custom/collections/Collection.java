package hr.fer.zemris.java.custom.collections;

/**
 * 
 * Represents a general collection of objects. Class that extends Collection
 * must implement the methods that are not implemented here.
 * 
 * @author MarinoK
 *
 */
public class Collection {

	/**
	 * Default constructor for the collection.
	 */
	protected Collection() {
	};

	/**
	 * Method used to determine whether collection is empty. Class that extends
	 * Collection must implement the method.
	 * 
	 * @return true, if the collection contains no objects
	 */
	public boolean isEmpty() {
		return size() == 0;
	};

	/**
	 * Method used to calculate the size of the collection. Here, implemented to
	 * always return zero. Class that extends Collection must implement the method.
	 * 
	 * @return number of objects in this collection
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given objects into this collection. Here, implemented to do nothing.
	 * Class that extends Collection must implement the method.
	 * 
	 * @param value
	 *            to be added to this collection
	 */
	public void add(Object value) {
	}

	/**
	 * Method used to determine if the given value is contained in this collection.
	 * Here, implemented to do nothing, and always return false. Class that extends
	 * Collection must implement the method.
	 * 
	 * @param value
	 *            to be searched throughout this collection
	 * @return true if the value is contained in this collection, false otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes the given element from the collection. Here, implemented to do
	 * nothing, and always return false. Class that extends Collection must
	 * implement the method.
	 * 
	 * @param value
	 *            element to be removed
	 * @return true if the element is found and removed, false otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Returns an array of elements from the collection. Here, guaranteed to throw
	 * an exception. Class that extends Collection must implement the method.
	 * 
	 * @return nothing at this point, given in homework task
	 * @throws UnsupportedOperationException
	 *             always
	 */
	public Object[] toArray() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method visits every element in the collection, and processes it. Here,
	 * implemented to do nothing. Class that extends Collection must implement the
	 * method.
	 * 
	 * @param processor
	 *            an object to process every element of the collection
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Method adds all elements from the given collection into this collection.
	 * Here, implemented to do nothing. Class that extends Collection must implement
	 * the method.
	 * 
	 * @param other
	 *            collection to be added to the current
	 */
	public void addAll(Collection other) {
		class ProcessorAll extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		forEach(new ProcessorAll());
	}

	/**
	 * Method removes all the elements from the collection. Here, implemented to do
	 * nothing. Class that extends Collection must implement the method.
	 */
	public void clear() {
	}
}
