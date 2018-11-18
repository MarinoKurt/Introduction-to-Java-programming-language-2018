package hr.fer.zemris.java.hw06.observer2;

/**
 * Class represents a change in the IntegerStorage class. Usage of this classes
 * instances is to encapsulate all information about the change which happened.
 * 
 * @author MarinoK
 */
public class IntegerStorageChange {

	/**
	 * Reference to integer storage where the change occurred.
	 */
	private IntegerStorage IStorage;

	/**
	 * Former value of the stored integer (after the change).
	 */
	private int oldValue;

	/**
	 * Future value of the store integer (before the change).
	 */
	private int newValue;

	/**
	 * Constructor for the IntegerStorageChange.
	 * 
	 * @param IStorage
	 *            reference to integer storage where the change occurred
	 * @param oldValue
	 *            former value of the stored integer
	 * @param newValue
	 *            future value of the store integer
	 */
	public IntegerStorageChange(IntegerStorage IStorage, int oldValue, int newValue) {
		this.IStorage = IStorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Getter for the IStorage.
	 * 
	 * @return reference to the integer storage where the change occurred
	 */
	public IntegerStorage getIstorage() {
		return IStorage;
	}

	/**
	 * Getter for the former value.
	 * 
	 * @return former value of the stored integer
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Getter for the future value.
	 * 
	 * @return future value of the stored integer
	 */
	public int getNewValue() {
		return newValue;
	}

}
