package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for LinkedListIndexedCollection class methods.
 * 
 * @author MarinoK
 */
public class LinkedListIndexedCollectionTest {

	@Test
	public void addTest() {
		LinkedListIndexedCollection testC = new LinkedListIndexedCollection();
		testC.add(1);
		testC.add(2);
		testC.add(3);
		testC.add(4);
		testC.add(5);
		
		Object[] array = testC.toArray();
		Assert.assertEquals(5, testC.size());
	}
	
	@Test
	public void insertTest() {		//contains get test
		LinkedListIndexedCollection testC = new LinkedListIndexedCollection();
		testC.add(1);
		testC.add(2);
		testC.add(3);
		testC.add(4);
		testC.insert(5,2);
		Object[] array = testC.toArray();
		Assert.assertEquals(5, testC.get(2));
	}
	
	@Test
	public void removeTest() {
		LinkedListIndexedCollection testC = new LinkedListIndexedCollection();
		testC.add(1);
		testC.add(2);
		testC.add(3);
		testC.add(4);
		testC.add(5);
		testC.remove(2);
		testC.remove(0);
		testC.remove(2);
		Assert.assertEquals(2, testC.size());
	}
	
	
	@Test
	public void indexOfTest() {
		LinkedListIndexedCollection testC = new LinkedListIndexedCollection();
		testC.add(1);
		testC.add(2);
		testC.add(3);
		testC.add(4);
		testC.add(5);
		Assert.assertEquals(4, testC.indexOf(5));
	}
	
	@Test
	public void addAllTest() {
		LinkedListIndexedCollection testC = new LinkedListIndexedCollection();
		testC.add(1);
		testC.add(2);
		testC.add(3);
		testC.add(4);
		testC.add(5);
		LinkedListIndexedCollection other = new LinkedListIndexedCollection(testC);
		Assert.assertEquals(4, other.indexOf(5));
	}
}
