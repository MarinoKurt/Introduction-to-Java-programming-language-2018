package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.Factorial.calculateFactorial;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testing of the calculateFactorial method. Cases below 1 or above 20 are
 * intentionally not covered, the program is constructed to send only filtered
 * input to tested method. The behavior for negative numbers is undefined.
 * 
 * @author MarinoK
 *
 */
public class CalculateFactorialTest {

	@Test(expected = RuntimeException.class)
	public void negativeFactorial() {
		calculateFactorial(-1);
	}

	@Test
	public void zeroFactorial() {
		Assert.assertEquals(1, calculateFactorial(0));
	}

	@Test
	public void oneFactorial() {
		Assert.assertEquals(1, calculateFactorial(1));
	}

	@Test
	public void twoFactorial() {
		Assert.assertEquals(2 * calculateFactorial(1), calculateFactorial(2));
	}

	@Test
	public void threeFactorial() {
		Assert.assertEquals(3 * calculateFactorial(2), calculateFactorial(3));
	}

	@Test
	public void fourFactorial() {
		Assert.assertEquals(4 * calculateFactorial(3), calculateFactorial(4));
	}

	@Test
	public void fiveFactorial() {
		Assert.assertEquals(5 * calculateFactorial(4), calculateFactorial(5));
	}

	@Test
	public void sixFactorial() {
		Assert.assertEquals(6 * calculateFactorial(5), calculateFactorial(6));
	}

	@Test
	public void sevenFactorial() {
		Assert.assertEquals(7 * calculateFactorial(6), calculateFactorial(7));
	}

	@Test
	public void eightFactorial() {
		Assert.assertEquals(8 * calculateFactorial(7), calculateFactorial(8));
	}

	@Test
	public void nineFactorial() {
		Assert.assertEquals(9 * calculateFactorial(8), calculateFactorial(9));
	}

	@Test
	public void tenFactorial() {
		Assert.assertEquals(10 * calculateFactorial(9), calculateFactorial(10));
	}

	@Test
	public void elevenFactorial() {
		Assert.assertEquals(11 * calculateFactorial(10), calculateFactorial(11));
	}

	@Test
	public void twelveFactorial() {
		Assert.assertEquals(12 * calculateFactorial(11), calculateFactorial(12));
	}

	@Test
	public void thirteenFactorial() {
		Assert.assertEquals(13 * calculateFactorial(12), calculateFactorial(13));
	}

	@Test
	public void fourteenFactorial() {
		Assert.assertEquals(14 * calculateFactorial(13), calculateFactorial(14));
	}

	@Test
	public void fifteenFactorial() {
		Assert.assertEquals(15 * calculateFactorial(14), calculateFactorial(15));
	}

	@Test
	public void sixteenFactorial() {
		Assert.assertEquals(16 * calculateFactorial(15), calculateFactorial(16));
	}

	@Test
	public void seventeenFactorial() {
		Assert.assertEquals(17 * calculateFactorial(16), calculateFactorial(17));
	}

	@Test
	public void eighteenFactorial() {
		Assert.assertEquals(18 * calculateFactorial(17), calculateFactorial(18));
	}

	@Test
	public void nineteenFactorial() {
		Assert.assertEquals(19 * calculateFactorial(18), calculateFactorial(19));
	}

	@Test
	public void twentyFactorial() {
		Assert.assertEquals(20 * calculateFactorial(19), calculateFactorial(20));
	}

}
