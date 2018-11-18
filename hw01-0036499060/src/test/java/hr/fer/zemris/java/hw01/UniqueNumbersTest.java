package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.*;

/**
 * Tests for treeSize, addNode. ContainsValue test is contained
 * within addNode test.
 * 
 * @author MarinoK
 *
 */

public class UniqueNumbersTest {

	@Test
	public void numberOfElements() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 76);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 77);
		head = UniqueNumbers.addNode(head, 35);
		head = UniqueNumbers.addNode(head, 33);
		head = UniqueNumbers.addNode(head, 356);
		Assert.assertEquals(7, UniqueNumbers.treeSize(head));
	}

	@Test
	public void numberOfElementsWithRepetition() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 76);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 76);
		head = UniqueNumbers.addNode(head, 21);
		Assert.assertEquals(3, UniqueNumbers.treeSize(head));
	}
	

}
