package hr.fer.zemris.java.hw06.observer2;

/**
 * Observer which prints the square of the value, every time it changes.
 * 
 * @author MarinoK
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints the square of the value to the System.out.
	 */
	@Override
	public void valueChanged(IntegerStorageChange IStorageChange) {
		int value = IStorageChange.getNewValue();
		System.out.println("Provided new value: " + value + ",square is " + value * value);
	}
}
