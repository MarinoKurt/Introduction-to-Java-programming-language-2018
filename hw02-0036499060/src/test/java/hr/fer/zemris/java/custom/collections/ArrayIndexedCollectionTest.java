package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the ArrayIndexedCollection class and all its methods.
 * 
 * @author MarinoK
 *
 */
public class ArrayIndexedCollectionTest {
	
	@Test
	public void sizeTest() {
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		testCollection.add(1);
		testCollection.add(2);
		testCollection.add(3);
		testCollection.add(4);
		testCollection.add(5);
		Assert.assertEquals(5, testCollection.size());
	}

	@Test
	public void indexOfTest() {
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		testCollection.add(1);
		testCollection.add(2);
		testCollection.add(3);
		testCollection.add(4);
		testCollection.add(5);
		Assert.assertEquals(2, testCollection.indexOf(3));
	}
	
	@Test
	public void insertTest() {
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		testCollection.add("a");
		testCollection.add("b");
		testCollection.add("c");
		testCollection.add("d");
		testCollection.add("e");
		testCollection.add("f");
		testCollection.add("g");
		testCollection.add("h");
		testCollection.add("i");
		testCollection.add("j");
		testCollection.add("k");
		testCollection.add("l");
		testCollection.add("m");
		testCollection.add("n");
		testCollection.insert("x", 10);
		Assert.assertEquals(10, testCollection.indexOf("x"));
	}
	
	@Test
	public void checkSizeTest() {
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		testCollection.add("a");
		testCollection.add("b");
		testCollection.add("c");
		testCollection.add("d");
		testCollection.add("e");
		testCollection.add("f");
		testCollection.add("g");
		testCollection.add("h");
		testCollection.add("i");
		testCollection.add("j");
		testCollection.add("k");
		testCollection.add("l");
		testCollection.add("m");
		testCollection.add("n");
		testCollection.add("o");
		testCollection.insert("x", 10);
		testCollection.insert("z", 3);
		testCollection.insert("y", 13);
		Assert.assertEquals(18, testCollection.size());
	}
	
	@Test
	public void containsTest() {
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		testCollection.add("a");
		testCollection.add("b");
		testCollection.add("c");
		testCollection.add("d");
		testCollection.add("e");
		testCollection.add("f");
		testCollection.add("g");
		testCollection.add("h");
		testCollection.add("i");
		testCollection.add("j");
		testCollection.add("k");
		testCollection.add("l");
		testCollection.add("m");
		testCollection.add("n");
		testCollection.add("o");
		testCollection.insert("x", 10);
		testCollection.insert("z", 3);
		testCollection.insert("y", 13);
		Assert.assertEquals(true, testCollection.contains("x"));
	}
	
	@Test
	public void removeTest() {
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		testCollection.add("a");
		testCollection.add("b");
		testCollection.add("c");
		testCollection.add("d");
		testCollection.add("e");
		testCollection.add("f");
		testCollection.add("g");
		testCollection.add("h");
		testCollection.add("i");
		testCollection.add("j");
		testCollection.add("k");
		testCollection.add("l");
		testCollection.add("m");
		testCollection.add("n");
		testCollection.add("o");
		testCollection.insert("x", 10);
		testCollection.insert("z", 3);
		testCollection.insert("y", 13);
		testCollection.remove("m");
		Assert.assertEquals(false, testCollection.contains("m"));
	}
	
	
	@Test
	public void addAllTest() { //also tests forEach
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		testCollection.add(1);
		testCollection.add(2);
		testCollection.add(3);
		testCollection.add(4);
		testCollection.add(5);
		other.addAll(testCollection);
		Assert.assertEquals(5, other.size());
	}
	
	@Test
	public void clearTest() {
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		testCollection.add(1);
		testCollection.add(2);
		testCollection.add(3);
		testCollection.add(4);
		testCollection.add(5);
		testCollection.clear();
		Assert.assertEquals(0, testCollection.size());
	}
	
	@Test
	public void isEmptyTest() {
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		testCollection.add(1);
		testCollection.add(2);
		testCollection.add(3);
		testCollection.add(4);
		testCollection.add(5);
		testCollection.clear();
		Assert.assertEquals(true, testCollection.isEmpty());
	}
	
	@Test
	public void getTest() {
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		testCollection.add(1);
		testCollection.add(2);
		testCollection.add(3);
		testCollection.add(4);
		testCollection.add(5);
		Assert.assertEquals(3, testCollection.get(2));
	}
	
	@Test
	public void addTest() {
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		testCollection.add(1);
		Assert.assertEquals(1, testCollection.size());
	}
	
	@Test
	public void toArrayTest() {
		ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
		testCollection.add(1);
		testCollection.add(2);
		testCollection.add(3);
		testCollection.add(4);
		testCollection.add(5);
		Object[] array = {1,2,3,4,5};
		Object[] collectionArray = testCollection.toArray();
		for(int i=0; i<array.length; i++) {
			Assert.assertEquals(array[i],collectionArray[i]);
		}
	}
}
