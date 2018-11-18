package hr.fer.zemris.math.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.math.Complex;

/**
 * Test class for the Complex.
 * 
 * @author MarinoK
 */
@SuppressWarnings("javadoc")
public class ComplexTest {

	@Test
	public void multiplyTest() {
		Complex c1 = new Complex(3, 5);
		Complex c2 = new Complex(-1, 0);
		Complex c3 = c1.multiply(c2);
		Assert.assertEquals(-3, c3.getRe(), 1E-6);
		Assert.assertEquals(-5, c3.getIm(), 1E-6);
	}

	@Test
	public void divideTest() {
		Complex c1 = new Complex(12, 431);
		Complex c2 = new Complex(-1, 0);
		Complex c3 = c1.divide(c2);
		Assert.assertEquals(-12, c3.getRe(), 1E-6);
		Assert.assertEquals(-431, c3.getIm(), 1E-6);
	}

	@Test
	public void addTest() {
		Complex c1 = new Complex(12, 431);
		Complex c2 = new Complex(-6, -230);
		Complex c3 = c1.add(c2);
		Assert.assertEquals(6, c3.getRe(), 1E-6);
		Assert.assertEquals(201, c3.getIm(), 1E-6);
	}

	@Test
	public void subTest() {
		Complex c1 = new Complex(12, 431);
		Complex c2 = new Complex(-6, -230);
		Complex c3 = c1.sub(c2);
		Assert.assertEquals(18, c3.getRe(), 1E-6);
		Assert.assertEquals(661, c3.getIm(), 1E-6);
	}

	@Test
	public void negateTest() {
		Complex c1 = new Complex(12, 431);
		Complex c3 = c1.negate();
		Assert.assertEquals(-12, c3.getRe(), 1E-6);
		Assert.assertEquals(-431, c3.getIm(), 1E-6);
	}

	@Test
	public void powerTest() {
		Complex y1 = new Complex(2, 2);
		Complex y2 = y1.power(2);
		assertEquals(0, y2.getRe(), 0.001);
		assertEquals(8, y2.getIm(), 0.001);

		Complex y3 = new Complex(3, -8);
		Complex y4 = y3.power(2);
		assertEquals(-55, y4.getRe(), 0.001);
		assertEquals(-48, y4.getIm(), 0.001);

		Complex y5 = new Complex(-5, 4);
		Complex y6 = y5.power(7);
		assertEquals(-4765, y6.getRe(), 0.001);
		assertEquals(-441284, y6.getIm(), 0.001);
	}

	@Test
	public void rootTest() {
		Complex c1 = new Complex(4, 4);
		List<Complex> list1 = c1.root(2);

		assertEquals(2.197, list1.get(0).getRe(), 0.001);
		assertEquals(0.91, list1.get(0).getIm(), 0.001);

		List<Complex> list2 = c1.root(4);

		assertEquals(1.512, list2.get(0).getRe(), 0.001);
		assertEquals(0.3, list2.get(0).getIm(), 0.001);

		assertEquals(-0.3, list2.get(1).getRe(), 0.001);
		assertEquals(1.512, list2.get(1).getIm(), 0.001);

		assertEquals(-1.512, list2.get(2).getRe(), 0.001);
		assertEquals(-0.3, list2.get(2).getIm(), 0.001);

		assertEquals(0.3, list2.get(3).getRe(), 0.001);
		assertEquals(-1.512, list2.get(3).getIm(), 0.001);
	}
}
