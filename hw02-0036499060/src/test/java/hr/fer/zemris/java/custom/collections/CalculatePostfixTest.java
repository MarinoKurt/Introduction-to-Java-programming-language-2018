package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.custom.collections.demo.StackDemo;

/**
 * Tests for the calculatePostfix method.
 * 
 * @author MarinoK
 */
public class CalculatePostfixTest {

	@Test(expected=RuntimeException.class)
	public void dividedByZeroTest() {
		ObjectStack myStack = new ObjectStack();
		String[] values = "3 2 * 25 - 330 0 / + 22 30 * - 4000 +".split(" ");
		StackDemo.calculatePostfix(myStack, values);
	}
	
	@Test(expected=RuntimeException.class)
	public void badInputTest() {
		ObjectStack myStack = new ObjectStack();
		String[] values = "2 + 2".split(" ");
		StackDemo.calculatePostfix(myStack, values);
	}
	
	@Test
	public void postfixTest1() {
		ObjectStack myStack = new ObjectStack();
		String[] values = "3 2 * 25 - 330 6 / + 22 30 * - 4000 +".split(" ");
		Assert.assertEquals(3376, StackDemo.calculatePostfix(myStack, values));
	}
	
	@Test
	public void postfixTest2() {
		ObjectStack myStack = new ObjectStack();
		String[] values = "30 30 * 15 30 / - 2 + 12 - 3 12 * +".split(" ");
		Assert.assertEquals(926, StackDemo.calculatePostfix(myStack, values));
	}
}
