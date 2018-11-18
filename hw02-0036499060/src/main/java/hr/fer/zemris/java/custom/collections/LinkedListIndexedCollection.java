package hr.fer.zemris.java.custom.collections;

/**
 * Implementation of linked list-backed collection of objects. Duplicates are
 * allowed, but null references are not.
 * 
 * @author MarinoK
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Number of nodes in the list.
	 */
	private int size;

	/**
	 * Reference to the first node of the list.
	 */
	private ListNode first;

	/**
	 * Reference to the last node of the list.
	 */
	private ListNode last;

	/**
	 * Representation of a single node in the list.
	 * 
	 * @author MarinoK
	 *
	 */
	private static class ListNode {

		/**
		 * Pointer to the next list node.
		 */
		private ListNode next;

		/**
		 * Pointer to the previous list node.
		 */
		private ListNode previous;

		/**
		 * Value storage.
		 */
		private Object value;

		/**
		 * Constructor for the ListNode.
		 * 
		 * @param next
		 *            pointer to the next list node
		 * @param previous
		 *            pointer to the previous list node
		 * @param value
		 *            of the node
		 */
		public ListNode(ListNode next, ListNode previous, Object value) {
			this.next = next;
			this.previous = previous;
			this.value = value;
		}

		/**
		 * Simpler constructor, sets previous and next values to null.
		 * 
		 * @param value
		 *            given to the node
		 */
		public ListNode(Object value) {
			this(null, null, value);
		}
	}

	/**
	 * Default constructor, creates an empty collection.
	 */
	public LinkedListIndexedCollection() {
		size = 0;
		first = last = null;
	}

	/**
	 * Constructor that copies the elements from given collection to this new
	 * collection.
	 * 
	 * @param original
	 *            collection to copy
	 */
	public LinkedListIndexedCollection(Collection original) {
		this.addAll(original);
	}

	/**
	 * Method used to fetch the current size of the collection.
	 * 
	 * @return size of the collection
	 */
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Adds the given object at the end of collection.
	 * 
	 * @param value
	 *            object do be added
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Value doesn't exist.");
		}
		insert(value, size);
	}

	/**
	 * Fetches the object that is store at position index.
	 * 
	 * @param index
	 *            at which the object is found
	 * @return object at the index
	 * @throws IndexOutOfBoundsException
	 *             if the index given is not between 0 and size-1
	 */
	public Object get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index should be between 0 and " + size + ". Was " + index);
		}
		return findNode(index).value;
	}

	/**
	 * Removes all elements from the collection.
	 */
	@Override
	public void clear() {
		first = last = null;
		size = 0;
	}

	/**
	 * Inserts the given value at the given position in the list.
	 * 
	 * @param value
	 *            to be added to the node
	 * @param position
	 *            at which the node will be added
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Position should be between 0 and " + size + ". Was " + position);
		}
		ListNode created = new ListNode(value);
		if (isEmpty()) {
			first = last = created;
		} else if (position == 0) {
			created.next = first;
			first.previous = created;
			first = created;
		} else if (position == size) {
			last.next = created;
			created.previous = last;
			last = created;
		} else {
			ListNode walker = first;
			for (int i = 0; i < position; i++) {
				walker = walker.next;
			}
			ListNode before = walker.previous;
			before.next = created;
			created.previous = before;
			created.next = walker;
			walker.previous = created;
		}
		size++;
	}

	/**
	 * Fetches the node at the given index.
	 * 
	 * @param index
	 *            at which the node is found
	 * @return node at the given index
	 */
	public ListNode findNode(int index) {
		ListNode wanted;
		if (index < (size / 2)) {
			wanted = first;
			for (int i = 0; i < index; i++) {
				wanted = wanted.next;
			}
		} else {
			wanted = last;
			for (int i = size; i > index + 1; i--) {
				wanted = wanted.previous;
			}
		}
		return wanted;
	}

	/**
	 * Searches the collection for the given value.
	 * 
	 * @param value
	 *            searched throughout the collection
	 * @return the index of the first occurrence of the given value, or -1 if the
	 *         value is not found
	 */
	int indexOf(Object value) {
		if (value == null)
			return -1;
		ListNode walker = first;
		for (int i = 0; i < size; i++) {
			if (walker.value.equals(value)) {
				return i;
			}
			walker = walker.next;
		}
		return -1;
	}

	/**
	 * Checks if the collection contains given value.
	 * 
	 * @return true if the collection contains given value
	 */
	public boolean contains(Object value) {
		if (indexOf(value) == -1)
			return false;
		return true;
	}

	/**
	 * Removes the element at specified index from the collection.
	 * 
	 * @param index
	 *            whose belonging element will be removed
	 * @throws IndexOutOfBoundsException
	 *             if the index is less than zero or greater than size-1
	 */
	void remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index should be between 0 and " + (size - 1) + ". Was " + index);
		}
		if (index == 0) {
			first = findNode(index).next;
			first.previous = null;
		} else if (index == size - 1) {
			last = findNode(index).previous;
			last.next = null;
		} else {
			ListNode walker = first;
			for (int i = 0; i < index; i++) {
				walker = walker.next;
			}
			ListNode before = walker.previous;
			ListNode after = walker.next;
			before.next = walker.next;
			after.previous = before;
		}
		size--;
	}

	/**
	 * Method used to get all elements from the collection in the form of an array.
	 * 
	 * @return array containing all elements from the collection
	 */
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode walker = first;

		for (int i = 0; i < size; i++) {
			array[i] = walker.value;
			walker = walker.next;
		}
		return array;
	}

	/**
	 * Adds all elements from the other collection to this one.
	 * 
	 * @param other
	 *            collection to be copied, but not altered
	 */
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
	 * Method visits every element in the collection, and processes it.
	 * 
	 * @param processor
	 *            an object to process every element of the collection
	 */
	public void forEach(Processor processor) {
		ListNode walker = first;
		for (int i = 0; i < size; i++) {
			processor.process(walker.value);
			walker = walker.next;
		}
	}
}
