package hr.fer.zemris.java.custom.scripting.exec.test;

import java.util.EmptyStackException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * Test class for the ObjectMultistack.
 * 
 * @author MarinoK
 */
public class ObjectMultistackTest {
	
	ObjectMultistack multistack;

	@Before
	public void initialization() {
		multistack = new ObjectMultistack();
		ValueWrapper num1 = new ValueWrapper(Integer.valueOf(1));
		multistack.push("numbers", num1);
		ValueWrapper word1 = new ValueWrapper("word1");
		multistack.push("words", word1);
		ValueWrapper num2 = new ValueWrapper(Integer.valueOf(2));
		multistack.push("numbers", num2);
		ValueWrapper word2 = new ValueWrapper("word2");
		multistack.push("words", word2);
		ValueWrapper num3 = new ValueWrapper(Integer.valueOf(3));
		multistack.push("numbers", num3);
		ValueWrapper word3 = new ValueWrapper("word3");
		multistack.push("words", word3);
		
	}

	@Test
	public void peekPopTest() {
		Assert.assertEquals("word3",multistack.peek("words").getValue());
		multistack.pop("words");
		Assert.assertEquals("word2",multistack.peek("words").getValue());
		Assert.assertEquals(3,multistack.peek("numbers").getValue());
	}
	
	@Test
	public void isEmptyTest() {
		multistack.pop("words");
		Assert.assertEquals("word2",multistack.peek("words").getValue());
		Assert.assertEquals(3,multistack.peek("numbers").getValue());
		multistack.pop("numbers");
		Assert.assertEquals("word2",multistack.peek("words").getValue());
		Assert.assertEquals(2,multistack.peek("numbers").getValue());
		multistack.pop("words");
		Assert.assertEquals("word1",multistack.peek("words").getValue());
		Assert.assertEquals(2,multistack.peek("numbers").getValue());
		multistack.pop("numbers");
		Assert.assertEquals("word1",multistack.peek("words").getValue());
		Assert.assertEquals(1,multistack.peek("numbers").getValue());
		multistack.pop("words");
		Assert.assertEquals(1,multistack.peek("numbers").getValue());
		multistack.pop("numbers");
		Assert.assertEquals(true ,multistack.isEmpty("words"));
		Assert.assertEquals(true ,multistack.isEmpty("numbers"));
	}
	
	@Test (expected = EmptyStackException.class)
	public void emptyStackPopTest() {
		multistack.pop("numbers");
		multistack.pop("numbers");
		multistack.pop("numbers");
		multistack.pop("numbers");
	}

	
	@Test (expected = EmptyStackException.class)
	public void emptyStackPeekTest() {
		multistack.pop("numbers");
		multistack.pop("numbers");
		multistack.pop("numbers");
		multistack.peek("numbers");
	}
	
	@Test (expected = NullPointerException.class)
	public void nonExistentStack() {
		multistack.pop("airplanes");
	}
	
	@Test (expected = NullPointerException.class)
	public void nullStack() {
		multistack.pop(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void pushNull() {
		multistack.push("numbers", null);
	}
	
	@Test
	public void pushWrappedNull() {
		multistack.push("trains", new ValueWrapper(null));
		Assert.assertEquals(multistack.peek("trains").getValue(), null);
		Assert.assertEquals(multistack.isEmpty("trains"), false);
	}
}
