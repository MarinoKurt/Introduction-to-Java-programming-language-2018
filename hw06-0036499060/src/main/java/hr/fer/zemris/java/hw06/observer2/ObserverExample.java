package hr.fer.zemris.java.hw06.observer2;

/**
 * Example for working with observers copied from the homework file. Modified
 * for the second part of the first task.
 */
public class ObserverExample {

	/**
	 * Main method runs when the program is run.
	 * 
	 * @param args
	 *            command line input, not expected
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		istorage.removeObserver(observer);

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
