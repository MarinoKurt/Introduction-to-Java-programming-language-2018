package hr.fer.zemris.java.custom.scripting.exec.test;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * Tests for the ValueWrapper class, first four copied from the homework file.
 */
public class ValueWrapperTest {

	@Test
	public void nullTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
		Assert.assertEquals(v1.getValue(), 0);
		Assert.assertEquals(v1.getValue().getClass(), Integer.class);
		Assert.assertEquals(v2.getValue(), null);
	}

	@Test
	public void doubleTest() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
		Assert.assertEquals(v3.getValue(), 13.0);
		Assert.assertEquals(v3.getValue().getClass(), Double.class);
		Assert.assertEquals(v4.getValue(), 1);
		Assert.assertEquals(v4.getValue().getClass(), Integer.class);
	}

	@Test
	public void intTest() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
		Assert.assertEquals(v5.getValue(), 13);
		Assert.assertEquals(v5.getValue().getClass(), Integer.class);
		Assert.assertEquals(v6.getValue(), 1);
		Assert.assertEquals(v6.getValue().getClass(), Integer.class);
	}

	@Test(expected = RuntimeException.class)
	public void nonNumericStringTest() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue()); // throws RuntimeException
	}
	
	@Test(expected = RuntimeException.class)
	public void divByZeroTest() {
		ValueWrapper w1 = new ValueWrapper(5);
		ValueWrapper w2 = new ValueWrapper(Integer.valueOf(0));
		w1.divide(w2.getValue()); 
	}
	
	@Test
	public void multiplyNullTest() {
		ValueWrapper w3 = new ValueWrapper("13.5");
		ValueWrapper w4 = new ValueWrapper(Integer.valueOf(0));
		Assert.assertEquals(w3.getValue(), "13.5");
		Assert.assertEquals(w3.getValue().getClass(), String.class);
		w3.multiply(w4.getValue()); 
		Assert.assertEquals(w3.getValue(), 0.0);
		Assert.assertEquals(w3.getValue().getClass(), Double.class);
		Assert.assertEquals(w4.getValue(), 0);
		Assert.assertEquals(w4.getValue().getClass(), Integer.class);
	}
	
	@Test
	public void nullCompareTest() {
		ValueWrapper w5 = new ValueWrapper(null);
		ValueWrapper w6 = new ValueWrapper(null);
		Assert.assertEquals(w5.getValue(), null);
		Assert.assertEquals(w6.getValue(), null);
		Assert.assertEquals(w5.numCompare(w6.getValue()), 0); 
		Assert.assertEquals(w5.getValue(), null);
		Assert.assertEquals(w6.getValue(), null);
	}
	
	@Test
	public void intVsDoubleCompareTest() {
		ValueWrapper w7 = new ValueWrapper("7");
		ValueWrapper w8 = new ValueWrapper("7.2");
		Assert.assertEquals(w7.getValue(), "7");
		Assert.assertEquals(w7.getValue().getClass(), String.class);
		Assert.assertEquals(w8.getValue(), "7.2");
		Assert.assertEquals(w8.getValue().getClass(), String.class);
		Assert.assertEquals(w7.numCompare(w8.getValue()), 1); 
		Assert.assertEquals(w7.getValue(), "7");
		Assert.assertEquals(w7.getValue().getClass(), String.class);
		Assert.assertEquals(w8.getValue(), "7.2");
		Assert.assertEquals(w8.getValue().getClass(), String.class);
	}
	
	@Test
	public void subtractNullTest() {
		ValueWrapper z1 = new ValueWrapper(13);
		ValueWrapper z2 = new ValueWrapper(null);
		Assert.assertEquals(z1.getValue(), 13);
		Assert.assertEquals(z1.getValue().getClass(), Integer.class);
		z1.subtract(z2.getValue()); 
		Assert.assertEquals(z1.getValue(), 13);
		Assert.assertEquals(z1.getValue().getClass(), Integer.class);
		Assert.assertEquals(z2.getValue(), null);
	}
	
	@Test
	public void divideIntTest() {
		ValueWrapper z1 = new ValueWrapper(5);
		ValueWrapper z2 = new ValueWrapper(2);
		Assert.assertEquals(z1.getValue(), 5);
		Assert.assertEquals(z1.getValue().getClass(), Integer.class);
		Assert.assertEquals(z2.getValue(), 2);
		Assert.assertEquals(z2.getValue().getClass(), Integer.class);
		z1.divide(z2.getValue()); 
		Assert.assertEquals(z1.getValue(), 2);
		Assert.assertEquals(z1.getValue().getClass(), Integer.class);
		Assert.assertEquals(z2.getValue(), 2);
	}
	
	@Test
	public void divideDoubleTest() {
		ValueWrapper z1 = new ValueWrapper(5);
		ValueWrapper z2 = new ValueWrapper(2.0);
		Assert.assertEquals(z1.getValue(), 5);
		Assert.assertEquals(z1.getValue().getClass(), Integer.class);
		Assert.assertEquals(z2.getValue(), 2.0);
		Assert.assertEquals(z2.getValue().getClass(), Double.class);
		z1.divide(z2.getValue()); 
		Assert.assertEquals(z1.getValue(), 2.5);
		Assert.assertEquals(z1.getValue().getClass(), Double.class);
		Assert.assertEquals(z2.getValue(), 2.0);
	}
	
}
