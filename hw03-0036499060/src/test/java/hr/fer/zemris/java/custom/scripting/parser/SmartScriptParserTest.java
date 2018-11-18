package hr.fer.zemris.java.custom.scripting.parser;

import static hr.fer.zemris.java.hw03.SmartScriptTester.createOriginalDocumentBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

/**
 * Tests for the SmartScriptParser.
 * 
 * @author MarinoK
 */
public class SmartScriptParserTest {

	private static final int NUMBER_OF_DOCUMENTS = 5;

	@Test(expected = SmartScriptParserException.class)
	public void tooManyForArgumentsTest() {
		String docBody = "{$ FOR year 1 10 \"1\" \"10\" $}{$END$}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
	}

	@Test(expected = SmartScriptParserException.class)
	public void tooFewForArgumentsTest() {
		String docBody = "{$ FOR year $}{$END$}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
	}

	@Test(expected = SmartScriptParserException.class)
	public void unclosedTagTest() {
		String docBody = "{$ FOR year 1 10 \"1\" $}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
	}

	@Test(expected = SmartScriptParserException.class)
	public void functionElementTest() {
		String docBody = "{$ FOR year @sin 10 $}{$END$}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
	}

	@Test(expected = SmartScriptParserException.class)
	public void operatorAsVariableTest() {
		String docBody = "{$ FOR * \"1\" -10 \"1\" $}{$END$}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
	}

	@Test(expected = SmartScriptParserException.class)
	public void numberAsVariableTest() {
		String docBody = "{$ FOR 3 1 10 1 $}{$END$}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
	}

	@Test(expected = RuntimeException.class)
	public void escapedEndTest() {
		String docBody = "{$ FOR year 1 last_year $} \\{$END$}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
	}

	@Test
	public void structural() {
		for (int i = 0; i < NUMBER_OF_DOCUMENTS; i++) {
			String path = "doc" + i + ".txt";
			String docBody = loader("doc1.txt");
			SmartScriptParser parser = new SmartScriptParser(docBody);
			DocumentNode document = parser.getDocumentNode();
			String originalDocumentBody = createOriginalDocumentBody(document);
			SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
			DocumentNode document2 = parser2.getDocumentNode();
			Assert.assertEquals(document.numberOfChildren(), document2.numberOfChildren());
			for (int j = 0, children = document.numberOfChildren(); j < children; j++) {
				Assert.assertEquals(document.getChild(j).numberOfChildren(), document2.getChild(j).numberOfChildren());
			}
		}
	}

	/**
	 * Function to read from a file. Taken from homework file.
	 * 
	 * @param filename
	 *            name of the file
	 * @return file in a string
	 */
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
}
