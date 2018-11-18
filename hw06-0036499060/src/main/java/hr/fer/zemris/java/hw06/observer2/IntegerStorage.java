package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.observer2.IntegerStorageObserver;

/**
 * Class stores one integer value, and notifies the observers whenever it
 * changes.
 * 
 * @author MarinoK
 */
public class IntegerStorage {

	/**
	 * Value of the stored integer.
	 */
	private int value;

	/**
	 * List of all the observers who keep track of the value changes.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Constructor for the IntegerStorage.
	 * 
	 * @param initialValue
	 *            value of the integer stored
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * Method used to add observer to the list of observers. All the observers on
	 * the list will be notified whenever the value changes.
	 * 
	 * @param observer
	 *            to be added
	 * @throws NullPointerException
	 *             if the observer is null
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observer != null) {
			observers.add(observer);
		} else {
			throw new NullPointerException("Observer must not be null.");
		}
	}

	/**
	 * Method used to remove the observer from the list of observers to be notified.
	 * 
	 * @param observer
	 *            to remove
	 * @throws NullPointerException
	 *             if the observer is null
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observer != null) {
			observers.remove(observer);
		} else {
			throw new NullPointerException("Observer must not be null.");
		}
	}

	/**
	 * Method used to clear the observers list.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Getter for the value.
	 * 
	 * @return value of the integer stored
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter for the value, if the value is different from former value, it will
	 * notify all the listeners that a change has happened.
	 * 
	 * @param value
	 *            to set
	 */
	public void setValue(int value) {
		if (this.value != value) {
			int oldValue = this.value;
			this.value = value;
			
			if (observers != null) {
				Object[] observersArray = observers.toArray();
				for (Object observer : observersArray) {
					((IntegerStorageObserver) observer).valueChanged(new IntegerStorageChange(this, oldValue, value));
				}
			}
		}
	}

}
