package hr.fer.zemris.java.hw06.demo2;

/**
 * Demo given in the homework file.
 */
public class PrimesDemo2 {

	/**
	 * Main method runs when the program is run.
	 * 
	 * @param args
	 *            command line input, not expected
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
