package hr.fer.zemris.java.hw06.observer1;

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
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value: " + value + ",square is " + value * value);
	}
}
