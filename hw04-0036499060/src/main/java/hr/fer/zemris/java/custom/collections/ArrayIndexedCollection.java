package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Array indexed collection with possible duplicates, without null references.
 * 
 * @author MarinoK
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Constant represents default capacity at the creation of the collection.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * Private variable to store the current size of the collection.
	 */
	private int size;

	/**
	 * Current maximum capacity of the collection.
	 */
	private int capacity;

	/**
	 * Array of object elements.
	 */
	private Object[] elements;

	/**
	 * Default constructor, allocates memory for an array of default capacity.
	 */
	protected ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor that takes desired capacity value.
	 * 
	 * @param initialCapacity
	 *            capacity at the creation, must be a positive integer larger than
	 *            zero
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity must be greater than 1. Was " + initialCapacity);
		}
		elements = new Object[initialCapacity];
		capacity = initialCapacity;
		size = 0;
	}

	/**
	 * Constructor that takes the collection to be copied into newly created one.
	 * 
	 * @param original
	 *            collection to be copied
	 */
	protected ArrayIndexedCollection(Collection original) {
		this(original, DEFAULT_CAPACITY);
	}

	/**
	 * Constructor that takes both collection to be copied, and initial capacity.
	 * 
	 * @param original
	 *            collection to be copied
	 * @param initialCapacity
	 *            capacity at the creation, must be a positive integer larger than
	 *            zero
	 */
	protected ArrayIndexedCollection(Collection original, int initialCapacity) {
		if (original == null) {
			throw new NullPointerException("Given collection doesn't exist.");
		}

		new ArrayIndexedCollection(original.size() > initialCapacity ? original.size() : initialCapacity);

		this.addAll(original);
	}

	/**
	 * Method used to determine whether collection is empty.
	 * 
	 * @return true, if the collection contains no objects
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	};

	/**
	 * Method used to calculate the size of the collection.
	 * 
	 * @return number of objects in this collection
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given objects into this collection. Average complexity is O(n).
	 * 
	 * @param value
	 *            to be added to this collection
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Given object doesn't exist.");
		}
		insert(value, size());
	}

	/**
	 * Method used to determine if the given value is contained in this collection.
	 * 
	 * @param value
	 *            to be searched throughout this collection
	 * @return true if the value is contained in this collection, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		if (isEmpty() || value == null)
			return false;

		for (int i = 0; i < size(); i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes the given element from the collection.
	 * 
	 * @param value
	 *            element to be removed
	 * @return true if the element is found and removed, false otherwise
	 */
	@Override
	public boolean remove(Object value) {
		if (isEmpty() || value == null || !contains(value))
			return false;

		remove(indexOf(value));
		return true;
	}

	/**
	 * Method is used to get the collection in the form of an array.
	 * 
	 * @return collection in the form of an array
	 */
	public Object[] toArray() {
		Object[] copy = Arrays.copyOf(elements, elements.length);
		return copy;
	}
	

	/**
	 * Method visits every element in the collection, and processes it.
	 * 
	 * @param processor
	 *            an object to process every element of the collection
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size(); i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Method adds all elements from the given collection into this collection.
	 * 
	 * @param other
	 *            collection to be added to the current
	 */
	@Override
	public void addAll(Collection other) {
		class ProcessorAll extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new ProcessorAll());
	}

	/**
	 * Removes all elements from this collection.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size(); i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Method inserts the given value at the given position in array, moving all
	 * further elements by one slot to the right.
	 * 
	 * @param value
	 *            value to be inserted into array
	 * @param position
	 *            position at which the value will be inserted
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size()) {
			throw new IndexOutOfBoundsException(
					"Index must be between 0 and " + size() + ". Was " + position + ".");
		}
		if(position==0 && size==0) {
			elements[0]=value;
		}
		elements = checkSize(size + 1);
		for (int i = size() - 1; i >= position; i--) {
			elements[i + 1] = elements[i];
		}
		elements[position] = value;
		size++;
	}

	/**
	 * Method checks is there enough space to add the element, and allocates an
	 * array of double capacity if there isn't.
	 * 
	 * @param spaceNeeded
	 *            amount of slots needed to add the element
	 * @return enlarged array, if needed
	 */
	private Object[] checkSize(int spaceNeeded) {
		if (capacity < spaceNeeded) {
			return Arrays.copyOf(this.elements, capacity + capacity);
		}
		return elements;
	}

	/**
	 * Method removes the value at the given index, and moves all further elements
	 * to fill the "empty" slot.
	 * 
	 * @param index
	 *            of the value to be removed
	 */
	public void remove(int index) {
		if (index < 0 || index > (size() - 1)) {
			throw new IndexOutOfBoundsException("Index must be between 0 and " + (size() - 1) + ". Was " + index + ".");
		}
		for (int i = index; i < size() - 1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
	}

	/**
	 * Method used to find the index of the given value. Complexity is O(n).
	 * 
	 * @param value
	 *            to be searched throughout the array
	 * @return index of the value, or -1 if the value isn't found
	 */
	public int indexOf(Object value) {
		if (isEmpty() || !contains(value) || value == null)
			return -1;

		for (int i = 0; i < size(); i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Method retrieves the object on the given index in the array.
	 * 
	 * @param index
	 *            of the demanded object
	 * @return object at the given index
	 */
	public Object get(int index) {
		if (index < 0 || index > (size() - 1)) {
			throw new IndexOutOfBoundsException("Index must be between 0 and " + (size() - 1) + ". Was " + index + ".");
		}
		return elements[index];
	}
}
