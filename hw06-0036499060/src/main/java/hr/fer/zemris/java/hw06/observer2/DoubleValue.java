package hr.fer.zemris.java.hw06.observer2;

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
	public void valueChanged(IntegerStorageChange IStorageChange) {
		System.out.println("Double value: " + 2 * IStorageChange.getNewValue());
		changesLeft--;
		if (changesLeft == 0) {
			IStorageChange.getIstorage().removeObserver(this);
		}
	}

}
