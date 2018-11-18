package hr.fer.zemris.java.hw03;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Tester method mostly taken from homework instructions. Only original method
 * is createOriginalDocumentBody.
 * 
 * @author MarinoK
 */
public class SmartScriptTester {

	/**
	 * Main method runs when the program is run.
	 * 
	 * @param args
	 *            command line arguments, expected path to document
	 */
	public static void main(String[] args) {

		try {
			parseAndPrintDocument(args[0]);
		} catch (FileNotFoundException e) {
			System.out.println("Wrong path. Was: " + args[0]);
			System.out.println("Avaliable paths: examples/docN.txt, where N [1-6]");
		}
	}
	
	/**
	 * Method used to easily test the parser from a path. Content taken from
	 * homework file.
	 * 
	 * @param path
	 *            of the document to be parsed
	 * @throws FileNotFoundException
	 *             if the path is invalid
	 */
	private static void parseAndPrintDocument(String path) throws FileNotFoundException {
		String docBody = "";
		try {
			docBody = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println("File not found.");
			throw new FileNotFoundException();
		}
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document! " + e.getMessage());
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);

	}

	/**
	 * Method used to recreate original input string from the document root node.
	 * 
	 * @param document
	 *            root node
	 * @return document in form of a string
	 */
	public static String createOriginalDocumentBody(Node document) {
		Node walker = document;
		StringBuilder text = new StringBuilder();

		for (int i = 0, children = walker.numberOfChildren(); i < children; i++) {
			text.append(walker.getChild(i).toString());
			if (walker.getChild(i).numberOfChildren() != 0) {
				text.append(createOriginalDocumentBody(walker.getChild(i)));
			}
		}
		return text.toString();
	}
}
