package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;

/**
 * Iterable "collection" of primes, calculates the primes when requested.
 * 
 * @author MarinoK
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Amount of primes that this collection is obliged to provide.
	 */
	private int numberOfPrimes;

	/**
	 * Default constructor for the PrimesCollection.
	 * 
	 * @param numberOfPrimes
	 *            amount of primes that this collection is obliged to provide
	 */
	public PrimesCollection(int numberOfPrimes) {
		this.numberOfPrimes = numberOfPrimes;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIterator();
	}

	/**
	 * Iterator for the PrimesCollection.
	 * 
	 * @author MarinoK
	 */
	private class PrimeIterator implements Iterator<Integer> {

		/**
		 * Constant used for returning invalid value from getNextPrime method.
		 */
		private static final int OVERFLOW = -1;

		/**
		 * Memorizes the last given prime, so that the search for the next one can be
		 * faster.
		 */
		private int lastGivenPrime;

		/**
		 * Memorizes how much more values can it iterate through. Takes the value at the
		 * initialization, from outer class.
		 */
		private int primesLeftToGive;

		/**
		 * Default constructor for the iterator.
		 */
		public PrimeIterator() {
			this.lastGivenPrime = 1;
			this.primesLeftToGive = numberOfPrimes;
		}

		@Override
		public boolean hasNext() {
			if (primesLeftToGive <= 0) return false;
			return true;
		}

		/**
		 * @throws RuntimeException
		 *             if the integer overflow happened
		 */
		@Override
		public Integer next() {
			lastGivenPrime = getNextPrime(lastGivenPrime);
			if (lastGivenPrime == OVERFLOW) throw new RuntimeException("Max prime reached.");
			return lastGivenPrime;
		}

		/**
		 * Method used to calculate the next prime number.
		 * 
		 * @param lastGivenPrime
		 *            number from which method searches for the next prime
		 * @return first prime number bigger than lastGivenPrime, or -1 if the integer
		 *         overflow happened
		 */
		private int getNextPrime(int lastGivenPrime) {
			for (int i = lastGivenPrime + 1; i < Integer.MAX_VALUE; ++i) {
				boolean isPrime = true;
				for (int check = 2; check < i; ++check) {
					if (i % check == 0) isPrime = false;
				}
				if (isPrime) {
					primesLeftToGive--;
					return i;
				}
			}
			return OVERFLOW;
		}

	}

}
