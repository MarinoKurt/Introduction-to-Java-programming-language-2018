package hr.fer.zemris.java.custom.scripting.lexer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the lexer of the second assignment in homework.
 * 
 * @author MarinoK
 *
 */
public class SmartScriptLexerTest {
	
	@Test
	public void textTest() {
		String text = "Lexer should return a String type of token, and a EOF.";
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		SmartToken lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(text, lexerOutput.getValue());
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(SmartTokenType.EOF, lexerOutput.getType());
	}

	
	
	@Test
	public void tagTest() {
		String text = "Lexer should recognize {$";
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		SmartToken lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals("Lexer should recognize ", lexerOutput.getValue());
		Assert.assertEquals(SmartTokenType.STRING, lexerOutput.getType());
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(SmartTokenType.TAG_ENTRY, lexerOutput.getType());
		lexerOutput = lexer.nextToken();
		Assert.assertEquals(SmartTokenType.EOF, lexerOutput.getType());
		System.out.println(lexerOutput);
	}
	
	@Test
	public void variableAndTagTest() {
		String text = "Lexer{$ x $}string.";
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		
		SmartToken lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals("Lexer", lexerOutput.getValue());
		Assert.assertEquals(SmartTokenType.STRING, lexerOutput.getType());
		
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(SmartTokenType.TAG_ENTRY, lexerOutput.getType());
		lexer.setState(SmartScriptLexerState.TAG);
		
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals("x", lexerOutput.getValue());
		Assert.assertEquals(SmartTokenType.VARIABLE, lexerOutput.getType());
		
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(SmartTokenType.TAG_EXIT, lexerOutput.getType());
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals("string.", lexerOutput.getValue());
		Assert.assertEquals(SmartTokenType.STRING, lexerOutput.getType());
	}
	
	@Test
	public void numberTest() {
		String text = "3.42 1 42222";
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartToken lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(SmartTokenType.CONSTANT_DOUBLE, lexerOutput.getType());
		Assert.assertEquals(3.42, (double) lexerOutput.getValue(), 0.001);
		
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(1, lexerOutput.getValue());
		Assert.assertEquals(SmartTokenType.CONSTANT_INTEGER, lexerOutput.getType());
	
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(42222, lexerOutput.getValue());
		Assert.assertEquals(SmartTokenType.CONSTANT_INTEGER, lexerOutput.getType());
	}
	
	
	@Test(expected = RuntimeException.class)
	public void tooLargeNumberTest() {
		String text = "5721927512509271041279";
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartToken lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
	}
	
	@Test
	public void operatorVsNegativeNumberTest() {
		String text = "* - -3";
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartToken lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(SmartTokenType.OPERATOR, lexerOutput.getType());
		Assert.assertEquals("*", lexerOutput.getValue());
		
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals("-", lexerOutput.getValue());
		Assert.assertEquals(SmartTokenType.OPERATOR, lexerOutput.getType());
	
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(-3, lexerOutput.getValue());
		Assert.assertEquals(SmartTokenType.CONSTANT_INTEGER, lexerOutput.getType());
	}
	
	@Test
	public void variableAndFunctionTest() {
		String text = "var152 @func";
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartToken lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(SmartTokenType.VARIABLE, lexerOutput.getType());
		Assert.assertEquals("var152", lexerOutput.getValue());
		
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals("func", lexerOutput.getValue());
		Assert.assertEquals(SmartTokenType.FUNCTION, lexerOutput.getType());
	}
	
	@Test
	public void specialCaseStringInTagTest() {
		String text = "\"-1\"10";
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartToken lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(SmartTokenType.STRING, lexerOutput.getType());
		Assert.assertEquals("-1", lexerOutput.getValue());
		
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(10, lexerOutput.getValue());
		Assert.assertEquals(SmartTokenType.CONSTANT_INTEGER, lexerOutput.getType());
	}
	
	@Test
	public void stringInTagTest() {
		String text = "@sin \"insideString\" 10";
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartToken lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(SmartTokenType.FUNCTION, lexerOutput.getType());
		Assert.assertEquals("sin", lexerOutput.getValue());
		
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(SmartTokenType.STRING, lexerOutput.getType());
		Assert.assertEquals("insideString", lexerOutput.getValue());
		
		lexerOutput = lexer.nextToken();
		System.out.println(lexerOutput);
		Assert.assertEquals(10, lexerOutput.getValue());
		Assert.assertEquals(SmartTokenType.CONSTANT_INTEGER, lexerOutput.getType());
	}
}
