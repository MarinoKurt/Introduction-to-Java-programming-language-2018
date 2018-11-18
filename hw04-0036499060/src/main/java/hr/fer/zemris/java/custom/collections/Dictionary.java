package hr.fer.zemris.java.custom.collections;

/**
 * Collection of key-value paired entries.
 * 
 * @author MarinoK
 *
 */
public class Dictionary {

	/**
	 * Collection to adapt for dictionary usage.
	 */
	private ArrayIndexedCollection backbone;

	/**
	 * Represents an entry with a key-value pair.
	 * 
	 * @author MarinoK
	 */
	private class Entry {

		/**
		 * Object unique for the collection, used to reach its paired value.
		 */
		private Object key;

		/**
		 * Value stored under the paired key.
		 */
		private Object value;

		/**
		 * Constructor for entry.
		 * 
		 * @param key
		 *            must not be null
		 * @param value
		 *            value to be stored
		 */
		public Entry(Object key, Object value) {
			if (key == null)
				throw new NullPointerException();
			this.key = key;
			this.value = value;
		}

		/**
		 * Getter for the key.
		 * 
		 * @return key
		 */
		public Object getKey() {
			return key;
		}

		/**
		 * Getter for the value.
		 * 
		 * @return value
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Setter for the value.
		 * 
		 * @param value
		 *            to set
		 */
		public void setValue(Object value) {
			this.value = value;
		}
	}

	/**
	 * Constructor for the dictionary.
	 */
	public Dictionary() {
		this.backbone = new ArrayIndexedCollection();
	}

	/**
	 * Method used to determine whether collection is empty.
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
	 * Removes all elements from this collection.
	 */
	public void clear() {
		backbone.clear();
	}

	/**
	 * Adds the key-value pair to the collection.
	 * 
	 * @param key
	 *            to add
	 * @param value
	 *            to add
	 */
	public void put(Object key, Object value) {

		if (get(key) == null) {
			Entry entry = new Entry(key, value);
			backbone.add(entry);
		} else {
			class ProcessorAll extends Processor {
				@Override
				public void process(Object entry) {
					if (key.equals((((Entry) entry).getKey()))) {
						((Entry) entry).setValue(value);
					}
				}
			}
			backbone.forEach(new ProcessorAll());
		}
	}

	/**
	 * Fetches the value under the given key.
	 * 
	 * @param key
	 *            to be searched for
	 * @return the value under the key, or null if the value does not exist
	 */
	public Object get(Object key) {
		Object[] array = backbone.toArray();
		for (int i = 0, size = array.length; i < size; i++) {
			if (array[i] != null) {

				if (key.equals(((Entry) array[i]).getKey())) {
					return ((Entry) array[i]).getValue();
				}
			}
		}
		return null;
	}

}
