package hr.fer.zemris.java.hw06.observer2;

/**
 * Contract used to communicate with observers. Every time the value changes,
 * valueChanged method of each observer will be called.
 */
public interface IntegerStorageObserver {

	/**
	 * Method used to perform an action after the value has changed.
	 * 
	 * @param IStorageChange
	 *            represents the value change
	 */
	public void valueChanged(IntegerStorageChange IStorageChange);
}
