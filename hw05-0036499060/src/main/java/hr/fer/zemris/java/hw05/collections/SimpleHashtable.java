package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of a parameterized hashtable used for storing key-value pairs.
 * 
 * @author MarinoK
 *
 * @param <K>
 *            type of key
 * @param <V>
 *            type of value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/** Default number of slots for the hashtable. */
	private static final int DEFAULT_SIZE = 16;

	/** Maximum percentage of non-empty slots */
	private static final double MAXIMUM_OCCUPANCY = 0.75;

	/** Number of entries of the hashtable. */
	private int size;

	/**
	 * Array of references to the first entry of each slot.
	 */
	private TableEntry<K, V>[] table;

	/** Keeps track of the modifications. */
	private int modificationCount;

	/**
	 * Default constructor for the SimpleHashtable, creates a hashtable with a
	 * default number of slots.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this.size = 0;
		this.modificationCount = 0;
		table = (TableEntry<K, V>[]) (new TableEntry[DEFAULT_SIZE]);
	}

	/**
	 * Constructor for the SimpleHashtable that takes the wanted number of slots,
	 * and creates a table with size as big as the smallest 2^n bigger than the
	 * given number. For example, if the given size is 30, the constructor will
	 * create a hashtable with the size set to 32.
	 * 
	 * @param capacity
	 *            wanted number of slots
	 */
	@SuppressWarnings("unchecked") // because of the array of parameterized references
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Size of the hashtable must be at least 1. Was: " + capacity);
		}
		int exp = 0;
		while (Math.pow(2, exp) < capacity) {
			exp++;
		}
		capacity = (int) Math.pow(2, exp);
		this.table = (TableEntry<K, V>[]) (new TableEntry[capacity]);
		this.size = 0;
		this.modificationCount = 0;
	}

	/**
	 * Represents the key-value pair in the hashtable.
	 * 
	 * @param <K>
	 *            type of key
	 * @param <V>
	 *            type of value
	 */
	public static class TableEntry<K, V> {

		/** Represents the unique key of the entry. Can not be null. */
		private K key;

		/** Represents the value of the entry. Can be null. */
		private V value;

		/** Reference to the next entry in the slot. */
		private TableEntry<K, V> next;

		/**
		 * Constructor for the TableEntry.
		 * 
		 * @param key
		 *            unique key of the entry, must not be null
		 * @param value
		 *            value of the entry
		 * @param next
		 *            reference to the next entry
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter for the value.
		 * 
		 * @return value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter for the value.
		 * 
		 * @param value
		 *            to set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Getter for the key.
		 * 
		 * @return key
		 */
		public K getKey() {
			return key;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(key.toString());
			sb.append("=");
			sb.append(value == null ? "null" : value.toString());
			return sb.toString();
		}

	}

	/**
	 * Adds the given key-value pair to the table. If the table already contains
	 * this key, given value will replace the former one.
	 * 
	 * @param key
	 *            to put, must not be null
	 * @param value
	 *            to put, can be null
	 * 
	 * @throws IllegalArgumentException
	 *             if the given key is null
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key must not be null.");
		}
		int slot = Math.abs(key.hashCode()) % table.length;

		if (table[slot] == null) {
			table[slot] = new TableEntry<K, V>(key, value, null);
			size++;
			modificationCount++;
		} else {
			TableEntry<K, V> walker = table[slot];
			while (true) {
				if (key.equals(walker.getKey())) {
					walker.value = value;
					break;
				} else if (walker.next == null) {
					walker.next = new TableEntry<K, V>(key, value, null);
					size++;
					modificationCount++;
					break;
				}
				walker = walker.next;
			}
		}
		checkOccupancy();
	}

	/**
	 * Checks if the table is too occupied to perform well. If the ratio of entries
	 * and table slots is over 3/4, the slot number will double.
	 */
	private void checkOccupancy() {
		double occupancy = ((double) size) / table.length;
		if (occupancy > MAXIMUM_OCCUPANCY) resizeTable();
	}

	/**
	 * Doubles the size of the table, rehashes the entries.
	 */
	@SuppressWarnings("unchecked") // because of the array of parameterized references
	private void resizeTable() {
		TableEntry<K, V>[] futureTable = (TableEntry<K, V>[]) (new TableEntry[table.length * 2]);
		TableEntry<K, V>[] oldTable = table;
		table = futureTable; // to set table.length to the right size for the put() to rehash correctly
		size = 0;
		for (int i = 0; i < oldTable.length; i++) {
			TableEntry<K, V> walker = oldTable[i];

			while (walker != null) {
				this.put(walker.getKey(), walker.getValue());
				walker = walker.next;
			}
		}
		modificationCount++;
	}

	/**
	 * Returns the value for the given key, if the table contains it. Will return
	 * null in two cases: if the table does not contain the value for the given key,
	 * or if the value for the given key is null.
	 * 
	 * @param key
	 *            whose value should be returned
	 * @return value of the given key, or null
	 */
	public V get(Object key) {
		if (key == null) return null;

		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> walker = table[slot];

		if (walker == null) return null;

		while (walker != null) {
			if (walker.getKey().equals(key)) {
				return walker.getValue();
			}
			walker = walker.next;
		}
		return null;
	}

	/**
	 * Returns the number of entries in this hashtable.
	 * 
	 * @return number of entries
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks whether this hashtable contains the given key.
	 * 
	 * @param key
	 *            to search for
	 * @return true, if the key is found, else otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) return false;

		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> walker = table[slot];

		while (walker != null) {
			if (walker.getKey().equals(key)) return true;
			walker = walker.next;
		}
		return false;
	}

	/**
	 * Checks whether this hashtable contains the given value.
	 * 
	 * @param value
	 *            to search for
	 * @return true, if the value is found, else otherwise
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> walker = table[i];

			while (walker != null) {
				if (walker.getValue() == null) {
					if (value == null) return true;
				} else {
					if (walker.getValue().equals(value)) return true;
				}
				walker = walker.next;
			}
		}
		return false;
	}

	/**
	 * Removes the entry containing the given key. If the entry does not exist in
	 * this table, method does nothing.
	 * 
	 * @param key
	 *            whose entry will be removed
	 */
	public void remove(Object key) {
		if (key == null) return;

		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> walker = table[slot];

		if (walker == null) return;

		if (walker.getKey().equals(key)) {
			table[slot] = walker.next;
			size--;
			modificationCount++;
		}

		TableEntry<K, V> follower = table[slot];
		walker = walker.next;

		while (walker != null) {
			if (walker.getKey().equals(key)) {
				follower.next = walker.next;
				size--;
				modificationCount++;
			}
			follower = follower.next;
			walker = walker.next;
		}
	}

	/**
	 * Checks if the table has no entries.
	 * 
	 * @return true if the table has no entries, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * ToString method for the hashtable.
	 * 
	 * @return string representation of a simple hashtable
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");

		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> walker = table[i];

			if (walker == null) continue;

			while (walker != null) {
				sb.append(walker.toString());
				sb.append(", ");
				walker = walker.next;
			}
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.deleteCharAt(sb.lastIndexOf(" "));
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Erases all entries from the table.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		table = null;
		size = 0;
	}

	/**
	 * Implementation of iterator which iterates through table entries. Implements
	 * all mandatory iterator methods, and remove.
	 */
	private class IteratorImpl implements Iterator<TableEntry<K, V>> {

		/** Memorizes the slot of the last given entry. */
		private int lastGivenSlot;

		/** Memorizes the last given entry. */
		private TableEntry<K, V> lastGivenEntry;

		/** Memorizes the modificationCount at the initialization of the iterator. */
		private int modificationState;

		/** Memorizes if the element can be removed. */
		private boolean canRemove;

		/** Default constructor for the IteratorImpl. */
		@SuppressWarnings("unchecked")
		public IteratorImpl() {
			this.lastGivenSlot = 0;
			this.lastGivenEntry = new TableEntry<K, V>((K) new Object(), null, table[0]);
			this.modificationState = modificationCount;
			canRemove = false;
		}

		@Override
		public boolean hasNext() { // documentation inherited
			checkModifications();
			if (lastGivenSlot > table.length) return false;
			if (lastGivenSlot == table.length - 1 && lastGivenEntry.next == null) return false;
			return true;
		}

		@Override
		public TableEntry<K, V> next() { // documentation inherited
			checkModifications();
			if (lastGivenEntry.next != null) {
				lastGivenEntry = lastGivenEntry.next;
				canRemove = true;
				return lastGivenEntry;
			} else {
				lastGivenSlot++;
				for (int slot = lastGivenSlot; slot <= table.length; slot++, lastGivenSlot++) {
					if (table[slot] != null) {
						lastGivenEntry = table[slot];
						canRemove = true;
						return lastGivenEntry;
					}
				}
			}
			throw new NoSuchElementException();
		}

		@Override
		public void remove() { // documentation inherited
			checkModifications();
			if (!canRemove) {
				throw new IllegalStateException("Can not remove the same element twice.");
			}

			SimpleHashtable.this.remove(lastGivenEntry.getKey());
			canRemove = false;
			modificationState++;
		}

		/**
		 * Auxiliary method used for checking for modifications from outside the
		 * iterator.
		 * 
		 * @throws ConcurrentModificationException
		 *             if the collection has been changed from outside this iterator
		 */
		private void checkModifications() {
			if (modificationCount != modificationState) {
				throw new ConcurrentModificationException(
						"Collection must not be tampered from outside of iterator while iterating.");
			}
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() { // documentation inherited
		return new IteratorImpl();
	}

}
