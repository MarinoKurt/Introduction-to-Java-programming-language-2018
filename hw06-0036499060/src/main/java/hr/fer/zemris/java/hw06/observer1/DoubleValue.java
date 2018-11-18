package hr.fer.zemris.java.hw06.observer1;

/**
 * Observer which prints out the double value, every time the value changes, for
 * the number of times given in the constructor.
 * 
 * @author MarinoK
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Number of times this observer reacts to the value change.
	 */
	private int changesLeft;

	/**
	 * Default constructor for the DoubleValue observer.
	 * 
	 * @param changesLeft
	 *            number of times this observer reacts to the value change
	 */
	public DoubleValue(int changesLeft) {
		this.changesLeft = changesLeft;
	}

	/**
	 * Writes the double value to the System.out for as many times as specified in
	 * the constructor.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Double value: " + 2 * istorage.getValue());
		changesLeft--;
		if (changesLeft == 0) {
			istorage.removeObserver(this);
		}

	}

}
