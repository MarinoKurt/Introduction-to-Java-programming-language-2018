package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the dictionary.
 * 
 * @author MarinoK
 */
public class DictionaryTest {

	Dictionary map = new Dictionary();

	@Before
	public void initialization() {
		map.put(1, "one");
		map.put(2, "two");
		map.put(3, "three");
		map.put(4, "four");
		map.put(5, "five");
	}

	@Test
	public void getATest() {
		Assert.assertEquals(map.get(2), "two");
		Assert.assertEquals(map.get(3), "three");
		Assert.assertEquals(map.get(4), "four");
		Assert.assertEquals(map.get(5), "five");
		
	}

	@Test
	public void sizeTest() {
		Assert.assertEquals(map.size(), 5);
		map.put(5, "five");
		Assert.assertEquals(map.size(), 5);
		map.put(6, "six");
		Assert.assertEquals(map.size(), 6);
	}
	
	@Test
	public void replaceTest() {
		map.put(2, "x");
		Assert.assertEquals(map.get(2), "x");
	}

	@Test
	public void clearTest() {
		map.clear();
		Assert.assertEquals(map.size(), 0);
		Assert.assertEquals(map.isEmpty(), true);
	}
}
