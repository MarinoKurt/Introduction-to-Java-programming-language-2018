package hr.fer.zemris.java.hw05.collections.test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;
import hr.fer.zemris.java.hw05.collections.SimpleHashtable.TableEntry;

/**
 * Tests for the SimpleHashtable, covering nearly all the methods from the
 * homework and iterator behavior.
 * 
 * @author MarinoK
 */
public class SimpleHashtableTest {

	SimpleHashtable<String, Integer> examMarks;

	@Before
	public void initialization() {
		examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Iva", 4);
		examMarks.put("Pero", 3);
		examMarks.put("Olivija", 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(0);
	}

	@Test(expected = NullPointerException.class)
	public void putNullKeyTest() {
		examMarks.put(null, 5);
	}

	@Test
	public void overwriteTest() {
		examMarks.put("Ivana", 5);
		Assert.assertEquals(7, examMarks.size());
	}

	@Test
	public void queryTest() {
		Assert.assertEquals(5, (int) examMarks.get("Kristina"));
		Assert.assertEquals(4, (int) examMarks.get("Olivija"));
		Assert.assertEquals(3, (int) examMarks.get("Pero"));
		Assert.assertEquals(4, (int) examMarks.get("Iva"));
		Assert.assertEquals(null, examMarks.get("Wolverine"));
	}

	@Test
	public void removeTest() {
		examMarks.remove("Olivija");
		Assert.assertEquals(6, examMarks.size());
		examMarks.remove("Ante");
		Assert.assertEquals(5, examMarks.size());
		examMarks.remove("Iva");
		Assert.assertEquals(4, examMarks.size());
		examMarks.remove("Jasna");
		Assert.assertEquals(3, examMarks.size());
		examMarks.remove("Kristina");
		Assert.assertEquals(2, examMarks.size());
		examMarks.remove("Pero");
		Assert.assertEquals(1, examMarks.size());
		examMarks.remove("Ivana");
		Assert.assertEquals(0, examMarks.size());
		Assert.assertEquals(true, examMarks.isEmpty());
	}

	@Test
	public void containsTest() {
		Assert.assertEquals(true, examMarks.containsKey("Ivana"));
		Assert.assertEquals(false, examMarks.containsKey("Kiki"));
		Assert.assertEquals(true, examMarks.containsKey("Pero"));
		Assert.assertEquals(true, examMarks.containsKey("Kristina"));
		Assert.assertEquals(true, examMarks.containsValue(5));
		Assert.assertEquals(false, examMarks.containsValue(6));
	}

	@Test
	public void containsNullTest() {
		Assert.assertEquals(false, examMarks.containsValue(null));
		Assert.assertEquals(false, examMarks.containsKey(null));
		examMarks.put("Vladimir", null);
		Assert.assertEquals(true, examMarks.containsValue(null));
		Assert.assertEquals(false, examMarks.containsKey(null));
	}

	@Test
	public void iteratorVsToStringTest() {
		java.util.Iterator<TableEntry<String, Integer>> it = examMarks.iterator();
		StringBuilder sb = new StringBuilder("[");
		while (it.hasNext()) {
			sb.append(it.next().toString());
			sb.append(", ");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.deleteCharAt(sb.lastIndexOf(" "));
		sb.append("]");
		System.out.println("Iterator vs toString test output:");
		System.out.println(sb.toString());
		System.out.println(examMarks.toString());
		Assert.assertEquals(sb.toString(), examMarks.toString());
	}

//	@Test //copied from homework, writes to system.out
//	public void forEachIteratorTest() {
//		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
//			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
//		}
//	}

	 @Test //copied from homework, writes to system.out
	 public void product() {
	 for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
	 for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
	 System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(),
	 pair1.getValue(), pair2.getKey(),
	 pair2.getValue());
	 }
	 }
	 }

	@Test
	public void iteratorRemoveTest() {
		Iterator<TableEntry<String, Integer>> iter = examMarks.iterator();
		System.out.println("Iterator remove test output:");
		System.out.println(examMarks.toString());
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
			}
		}
		System.out.println(examMarks.toString());
	}

	@Test(expected = IllegalStateException.class)
	public void iteratorDoubleRemoveTest() {
		Iterator<TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();
			}
		}
	}

	@Test(expected = ConcurrentModificationException.class)
	public void tamperingOutsideIterator() {
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
	}

	 @Test
	 public void clearingWithIterator() {
	 Iterator<SimpleHashtable.TableEntry<String, Integer>> iter =
	 examMarks.iterator();
	 while (iter.hasNext()) {
	 iter.next();
	 iter.remove();
	 }
	 Assert.assertEquals(0, examMarks.size());
	 }
}
