package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Program writes reproduced smart script from a file to the system output.
 * 
 * @author MarinoK
 */
public class TreeWriter {

	/**
	 * Main method runs when program is run.
	 * 
	 * @param args
	 *            expected a file name
	 */
	public static void main(String[] args) {

		if (args.length != 1 || !args[0].endsWith(".smscr")) {
			System.out.println("Expected a file name as an argument.");
			return;
		}

		String docBody = loader(args[0]);
		SmartScriptParser p = new SmartScriptParser(docBody);
		INodeVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);

	}

	/**
	 * Auxiliary method for loading a file to a string.
	 * 
	 * @param filename
	 *            of the file
	 * @return file content as a string
	 */
	static String loader(String filename) {
		byte[] docText = null;
		try {
			docText = Files.readAllBytes(Paths.get(filename));
		} catch (IOException e) {
			System.out.println("Error reading file.");
			return null;
		}
		return new String(docText, StandardCharsets.UTF_8);
	}

	/**
	 * Node visitor which writes the nodes to the system output.
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.toString());
			for (int i = 0, children = node.numberOfChildren(); i < children; i++) {
				node.getChild(i).accept(this);
			}
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, children = node.numberOfChildren(); i < children; i++) {
				node.getChild(i).accept(this);
			}
		}
	}
}
