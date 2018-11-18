package hr.fer.zemris.java.custom.collections;

/**
 * Implementation of the stack-like collection, adapted to match the user's
 * requirements.
 * 
 * @author MarinoK
 */
public class ObjectStack {

	/**
	 * Collection used for actual element storage.
	 */
	private ArrayIndexedCollection backbone = new ArrayIndexedCollection();

	/**
	 * Method used to determine whether the stack is empty.
	 * 
	 * @return true, if the collection contains no objects
	 */
	public boolean isEmpty() {
		return backbone.isEmpty();
	}

	/**
	 * Method used to calculate the size of the collection.
	 * 
	 * @return number of objects in this collection
	 */
	public int size() {
		return backbone.size();
	}

	/**
	 * Pushes the given value on the stack. Does not accept null values.
	 * 
	 * @param value
	 *            non-null value to be added on the stack
	 */
	public void push(Object value) {
		backbone.add(value);
	}

	/**
	 * Removes the last value pushed on the stack, returns it.
	 * 
	 * @return top value from the stack
	 */
	public Object pop() {
		Object top = peek();
		backbone.remove(top);
		return top;
	}

	/**
	 * Returns the last value pushed on the stack, without removing it from the
	 * stack.
	 * 
	 * @return top value from the stack
	 */
	public Object peek() {
		if (isEmpty()) {
			throw new EmptyStackException("The stack is empty!");
		}
		return backbone.get(size() - 1);
	}

	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		backbone.clear();
	}
}
