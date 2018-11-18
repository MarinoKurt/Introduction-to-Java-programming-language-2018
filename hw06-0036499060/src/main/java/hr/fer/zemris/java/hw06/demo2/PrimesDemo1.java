package hr.fer.zemris.java.hw06.demo2;

/**
 * Demo given in the homework file.
 */
public class PrimesDemo1 {

	/**
	 * Main method runs when the program is run.
	 * 
	 * @param args
	 *            command line input, not expected
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(30); // 5: how many of them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}
}
