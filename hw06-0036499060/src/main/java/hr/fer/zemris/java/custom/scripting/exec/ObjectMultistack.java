package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Collection that is a combination of map and stack implementations. For every
 * key, it contains a stack of values.
 * 
 * @author MarinoK
 */
public class ObjectMultistack {

	/**
	 * Map to store the key-value entries.
	 */
	private Map<String, MultistackEntry> multistack = new HashMap<>();

	/**
	 * Method used to push a value onto the stack paired with the given key.
	 * 
	 * @param name
	 *            key of the map, determines on which stack will the value be pushed
	 * @param valueWrapper
	 *            value to be pushed on the stack
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		MultistackEntry newEntry = new MultistackEntry(valueWrapper, multistack.get(name));
		multistack.put(name, newEntry);
	}

	/**
	 * Method used to remove the last element pushed on the particular stack,
	 * determined by the key.
	 * 
	 * @param name
	 *            key of the map, determines from which stack will the value be
	 *            popped
	 * @return value popped from the stack
	 */
	public ValueWrapper pop(String name) {
		if(!multistack.containsKey(name)) throw new NullPointerException("There is no stack under the name: " + name);
		MultistackEntry wanted = multistack.get(name);
		if (wanted == null) throw new EmptyStackException();

		if (wanted.getNext() == null) {
			multistack.put(name, null);
		} else {
			multistack.put(name, wanted.getNext());
		}
		return wanted.getObject();
	}

	/**
	 * Method used to fetch the last element pushed on the particular stack,
	 * determined by the key.
	 * 
	 * @param name
	 *            key of the map, determines from which stack will the value be
	 *            popped
	 * @return value popped from the stack
	 */
	public ValueWrapper peek(String name) {
		MultistackEntry wanted = multistack.get(name);
		if (wanted == null) throw new EmptyStackException();
		return wanted.getObject();
	}

	/**
	 * Method used to check whether the particular stack, determined by the given
	 * key, is empty.
	 * 
	 * @param name
	 *            key of the map, determines from which stack will the value be
	 *            popped
	 * @return true, if the particular stack is empty, else otherwise
	 */
	public boolean isEmpty(String name) {
		return multistack.get(name) == null;
	}

	/**
	 * Structure used as a node in the stack. Points to the next node that was
	 * pushed on the stack before the current node. Also, contains a value.
	 * 
	 * @author MarinoK
	 */
	private static class MultistackEntry {

		/**
		 * Value for this entry.
		 */
		private ValueWrapper object;

		/**
		 * Reference to the next node that was pushed on the stack before the current
		 * one.
		 */
		private MultistackEntry next;

		/**
		 * Default constructor for the MultistackEntry.
		 * 
		 * @param object
		 *            value of the entry, must not be null
		 * @param next
		 *            reference to the next node on the stack
		 */
		public MultistackEntry(ValueWrapper object, MultistackEntry next) {
			this.object = Objects.requireNonNull(object);
			this.next = next;
		}

		/**
		 * Getter for the value.
		 * 
		 * @return value as a instance of ValueWrapper
		 */
		public ValueWrapper getObject() {
			return object;
		}

		/**
		 * Getter for the next node reference.
		 * 
		 * @return next MultistackEntry in the stack
		 */
		public MultistackEntry getNext() {
			return next;
		}
	}
}
