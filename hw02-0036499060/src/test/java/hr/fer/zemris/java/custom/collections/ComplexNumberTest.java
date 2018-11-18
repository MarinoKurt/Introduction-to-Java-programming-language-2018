package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;
import static hr.fer.zemris.java.hw02.ComplexNumber.*;
import org.junit.Test;

import hr.fer.zemris.java.hw02.ComplexNumber;
import static java.lang.Math.PI;

/**
 * Tests for the ComplexNumber class methods.
 * @author MarinoK
 *
 */
public class ComplexNumberTest {

	@Test
	public void parseTest() {
		ComplexNumber x01 = parse("-3");
		assertEquals(-3, x01.getReal(), 0.001);
		assertEquals(0, x01.getImaginary(), 0.001);
		
		ComplexNumber x02 = parse("3.51");
		assertEquals(3.51, x02.getReal(), 0.001);
		assertEquals(0, x02.getImaginary(), 0.001);
		
		ComplexNumber x03 = parse("-3.51");
		assertEquals(-3.51, x03.getReal(), 0.001);
		assertEquals(0, x03.getImaginary(), 0.001);
		
		ComplexNumber x1 = parse("4+3i");
		assertEquals(4, x1.getReal(), 0.001);
		assertEquals(3, x1.getImaginary(), 0.001);

		ComplexNumber x2 = parse("4i-3");
		assertEquals(-3, x2.getReal(), 0.001);
		assertEquals(4, x2.getImaginary(), 0.001);

		ComplexNumber x3 = parse("-5-3i");
		assertEquals(-5, x3.getReal(), 0.001);
		assertEquals(-3, x3.getImaginary(), 0.001);

		ComplexNumber x4 = parse("-5i");
		assertEquals(0, x4.getReal(), 0.001);
		assertEquals(-5, x4.getImaginary(), 0.001);

		ComplexNumber x5 = parse("5i");
		assertEquals(0, x5.getReal(), 0.001);
		assertEquals(5, x5.getImaginary(), 0.001);
	
		ComplexNumber x6 = parse("i");
		assertEquals(0, x6.getReal(), 0.001);
		assertEquals(1, x6.getImaginary(), 0.001);
	
		ComplexNumber x7 = parse("-i");
		assertEquals(0, x7.getReal(), 0.001);
		assertEquals(-1, x7.getImaginary(), 0.001);
		
		ComplexNumber x8 = parse("4");
		assertEquals(4, x8.getReal(), 0.001);
		assertEquals(0, x8.getImaginary(), 0.001);
		
		ComplexNumber x9 = parse("-2.71-3.15i");
		assertEquals(-2.71, x9.getReal(), 0.001);
		assertEquals(-3.15, x9.getImaginary(), 0.001);
		
		ComplexNumber x0 = parse("i-3");
		assertEquals(-3, x0.getReal(), 0.001);
		assertEquals(1, x0.getImaginary(), 0.001);
		
	}

	@Test
	public void powerTest() {
		ComplexNumber y1 = parse("2+2i");
		ComplexNumber y2 = y1.power(2);
		assertEquals(0, y2.getReal(), 0.001);
		assertEquals(8, y2.getImaginary(), 0.001);

		ComplexNumber y3 = parse("3-8i");
		ComplexNumber y4 = y3.power(2);
		assertEquals(-55, y4.getReal(), 0.001);
		assertEquals(-48, y4.getImaginary(), 0.001);

		ComplexNumber y5 = parse("-5+4i");
		ComplexNumber y6 = y5.power(7);
		assertEquals(-4765, y6.getReal(), 0.001);
		assertEquals(-441284, y6.getImaginary(), 0.001);
	}

	@Test
	public void rootTest() {
		ComplexNumber y1 = parse("4+4i");
		ComplexNumber[] array1 = y1.root(2);
		
		assertEquals(2.197, array1[0].getReal(), 0.001);
		assertEquals(0.91, array1[0].getImaginary(), 0.001);
		
		System.out.println();
		ComplexNumber y2 = parse("4-4i");
		ComplexNumber[] array2 = y1.root(4);
		
		assertEquals(1.512, array2[0].getReal(), 0.001);
		assertEquals(0.3, array2[0].getImaginary(), 0.001);
		
		assertEquals(-0.3, array2[1].getReal(), 0.001);
		assertEquals(1.512, array2[1].getImaginary(), 0.001);
		
		assertEquals(-1.512, array2[2].getReal(), 0.001);
		assertEquals(-0.3, array2[2].getImaginary(), 0.001);
		
		assertEquals(0.3, array2[3].getReal(), 0.001);
		assertEquals(-1.512, array2[3].getImaginary(), 0.001);
	}
	
	@Test
	public void addTest() {
		System.out.println();
		ComplexNumber first = ComplexNumber.parse("2+2i");
		ComplexNumber second = ComplexNumber.parse("2-2i");
		ComplexNumber result1 = first.add(second);
		assertEquals(4, result1.getReal(), 0.001);
		
		ComplexNumber number1 = parse("2.2161+5.241i");
		ComplexNumber number2 = parse("-30.235631-25.21366i");
		ComplexNumber result2 = number1.add(number2);
		assertEquals(-28.019, result2.getReal(), 0.001);
		assertEquals(-19.972, result2.getImaginary(), 0.001);
	}
	
	@Test
	public void subTest() {
		System.out.println();
		ComplexNumber first = parse("2+2i");
		ComplexNumber second = parse("2-2i");
		ComplexNumber result1 = first.sub(second);
		assertEquals(0, result1.getReal(), 0.001);
		assertEquals(4, result1.getImaginary(), 0.001);
		
		ComplexNumber number1 = parse("2.2161+5.241i");
		ComplexNumber number2 = parse("-30.235631-25.21366i");
		ComplexNumber result2 = number1.sub(number2);
		assertEquals(32.451, result2.getReal(), 0.001);
		assertEquals(30.454, result2.getImaginary(), 0.001);
	}
	
	@Test
	public void mulTest() {
		System.out.println();
		ComplexNumber first = parse("2+2i");
		ComplexNumber second = parse("2-2i");
		ComplexNumber result1 = first.mul(second);
		assertEquals(8, result1.getReal(), 0.001);
		assertEquals(0, result1.getImaginary(), 0.001);
		
		ComplexNumber number1 = ComplexNumber.parse("2.2161+5.241i");
		ComplexNumber number2 = ComplexNumber.parse("-30.235631-25.21366i");
		ComplexNumber result2 = number1.sub(number2);
		assertEquals(32.451, result2.getReal(), 0.001);
		assertEquals(30.454, result2.getImaginary(), 0.001);
	}
	
	@Test
	public void divTest() {
		System.out.println();
		ComplexNumber first = parse("2+2i");
		ComplexNumber second = parse("2-2i");
		ComplexNumber result1 = first.div(second);
		assertEquals(0, result1.getReal(), 0.001);
		assertEquals(1, result1.getImaginary(), 0.001);
		
		ComplexNumber number1 = ComplexNumber.parse("2.2161+5.241i");
		ComplexNumber number2 = ComplexNumber.parse("-30.235631-25.21366i");
		ComplexNumber result2 = number1.div(number2);
		assertEquals(-0.128, result2.getReal(), 0.001);
		assertEquals(-0.066, result2.getImaginary(), 0.001);
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		ComplexNumber first = fromMagnitudeAndAngle(8.544, -1.212);
		assertEquals(3, first.getReal(), 0.001);
		assertEquals(-8, first.getImaginary(), 0.001);
		
		ComplexNumber second = fromMagnitudeAndAngle(30.0167, -1.537475);
		assertEquals(1, second.getReal(), 0.001);
		assertEquals(-30, second.getImaginary(), 0.001);
		
		ComplexNumber third = fromMagnitudeAndAngle(30.0167, 36.16163684);
		assertEquals(1, third.getReal(), 0.001);
		assertEquals(-30, third.getImaginary(), 0.001);
	}
	
	@Test
	public void angleTest() {
		ComplexNumber x6 = parse("4+4i");
		assertEquals(PI / 4, x6.getAngle(), 0.001);

		ComplexNumber x7 = parse("4-4i");
		assertEquals(-PI / 4, x7.getAngle(), 0.001);

		ComplexNumber x8 = parse("-4-4i");
		assertEquals((-3 * PI) / 4, x8.getAngle(), 0.001);

		ComplexNumber x9 = parse("-4+4i");
		assertEquals((3 * PI) / 4, x9.getAngle(), 0.001);
	}
	
	@Test
	public void magnitudeTest() {
		
		ComplexNumber x6 = parse("4+4i");
		assertEquals(5.656, x6.getMagnitude(), 0.001);

		ComplexNumber x7 = parse("-30-21i");
		assertEquals(36.619, x7.getMagnitude(), 0.001);
	}
	
	@Test
	public void toStringTest() {
		String complexNum = "4.0+4.0i";
		ComplexNumber number = parse(complexNum);
		assertEquals(complexNum, number.toString());
	}
}
