package hr.fer.zemris.java.hw06.observer1;

/**
 * Observer which prints out the number of value changes since its
 * initialization, every time the change occurs.
 * 
 * @author MarinoK
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Value used for tracking the number of value changes.
	 */
	private int counter;

	/**
	 * Default constructor for the ChangeCounter observer.
	 */
	public ChangeCounter() {
		this.counter = 0;
	}

	/**
	 * Prints out the number of value changes since its initialization.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}

}
