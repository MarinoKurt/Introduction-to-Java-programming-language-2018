package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Implements a simple syntax analysis system.
 * 
 * @author MarinoK
 *
 */
public class SmartScriptParser {

	/**
	 * Minimal number for the for loop expressions.
	 */
	private static final int MAX_FOR_EXPRESSIONS = 3;

	/**
	 * Lexical system that communicates with the parser.
	 */
	private SmartScriptLexer lexer;

	/**
	 * Stack used to build the document tree.
	 */
	private ObjectStack stack;

	/**
	 * Root node of the document tree.
	 */
	private DocumentNode documentNode;

	/**
	 * Latest token output from the lexer.
	 */
	private SmartToken lexerOutput;

	/**
	 * Constructor for the parser.
	 * 
	 * @param docBody
	 *            document to be parsed in form of a string
	 */
	public SmartScriptParser(String docBody) {
		this.documentNode = new DocumentNode();

		this.stack = new ObjectStack();
		stack.push(documentNode);

		try {
			this.lexer = new SmartScriptLexer(docBody);
			actualParsing();
		} catch (Exception anything) {
			throw new SmartScriptParserException(anything.getMessage());
		}

		if (!stack.peek().equals(documentNode)) {
			throw new SmartScriptParserException("Irregular number of closing tags.");
		}
	}

	/**
	 * Method used to parse input document into a tree based of the document node.
	 * 
	 * @throws SmartScriptParserException
	 *             if the input is invalid
	 */
	private void actualParsing() {
		while (lexer.getToken() != null) {

			lexerOutput = lexer.nextToken();
			if (lexerOutput.getType() == SmartTokenType.EOF)
				break;

			if (lexer.getState() == SmartScriptLexerState.TEXT) {

				if (lexerOutput.getType() != SmartTokenType.STRING) {
					if (lexerOutput.getType() == SmartTokenType.TAG_ENTRY) {
						lexer.setState(SmartScriptLexerState.TAG);
						continue;
					} else {
						throw new SmartScriptParserException("Invalid type for text mode.");
					}
				}
				Node textNode = new TextNode((String) lexerOutput.getValue());
				((Node) stack.peek()).addChildNode(textNode);

			} else if (lexer.getState() == SmartScriptLexerState.TAG) {

				if (lexerOutput.getValue().toString().toLowerCase().equals("for")) {
					parseFor();

				} else if (lexerOutput.getValue().toString().equals("=")) {
					parseEcho();

				} else if (lexerOutput.getValue().toString().equals("end")) {
					Node endNode = new TextNode("{$END$}");
					((Node) stack.peek()).addChildNode(endNode);

					lexerOutput = lexer.nextToken();
					if (lexerOutput.getType() != SmartTokenType.TAG_EXIT) {
						throw new SmartScriptParserException("Invalid end tag.");
					}
					lexer.setState(SmartScriptLexerState.TEXT);
					try{
						stack.pop();
					} catch (Exception emptyStack) {
						throw new SmartScriptParserException("Irregular number of closing tags.");
					}

				} else {
					throw new SmartScriptParserException("Invalid tag name. Was: " + lexerOutput.getValue().toString());
				}
			} else {
				throw new SmartScriptParserException("Invalid lexer state. Was: " + lexer.getState());
			}
		}
	}

	/**
	 * Auxiliary method for parsing echo tags.
	 */
	private void parseEcho() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		lexerOutput = lexer.nextToken();

		while (lexerOutput.getType() != SmartTokenType.TAG_EXIT) {
			Element echoArgument = constructElement();
			collection.add(echoArgument);
			lexerOutput = lexer.nextToken();
			if (lexerOutput.getType() == SmartTokenType.EOF) {
				throw new SmartScriptParserException("Invalid = tag.");
			}
		}
		if (lexerOutput.getType() == SmartTokenType.TAG_EXIT) {
			lexer.setState(SmartScriptLexerState.TEXT);
		} else {
			throw new SmartScriptParserException("Invalid = tag.");
		}
		try {
			Element[] elements = Arrays.copyOf(collection.toArray(), collection.toArray().length, Element[].class);
			Node echoNode = new EchoNode(elements);
			((Node) stack.peek()).addChildNode(echoNode);
		} catch (Exception e) {
			System.out.println("echos fault");
			System.out.println(e.getStackTrace());
		}

	}

	/**
	 * Auxiliary method for parsing for tags.
	 */
	private void parseFor() {
		boolean thirdArgument = true;
		lexerOutput = lexer.nextToken();
		if (lexerOutput.getType() != SmartTokenType.VARIABLE) {
			throw new SmartScriptParserException("Bad variable for the for loop. Was: " + lexerOutput);
		}
		ElementVariable forVariable = new ElementVariable((String) lexerOutput.getValue());
		lexerOutput = lexer.nextToken();
		Element[] array = new Element[MAX_FOR_EXPRESSIONS];
		for (int i = 0; i < MAX_FOR_EXPRESSIONS; i++) {

			if (i == MAX_FOR_EXPRESSIONS - 1 && lexerOutput.getType() == SmartTokenType.TAG_EXIT) { // optional element
				thirdArgument = false;
				break;
			}
			checkForArgument();
			array[i] = constructElement();
			lexerOutput = lexer.nextToken();
		}

		if (lexerOutput.getType() == SmartTokenType.TAG_EXIT) {
			lexer.setState(SmartScriptLexerState.TEXT);
		} else {
			throw new SmartScriptParserException("Invalid for loop: Irregular exit.");
		}
		Node forNode;
		if (thirdArgument) {
			forNode = new ForLoopNode(forVariable, array[0], array[1], array[2]);
		} else {
			forNode = new ForLoopNode(forVariable, array[0], array[1], null);
		}
		((Node) stack.peek()).addChildNode(forNode);
		stack.push(forNode);
	}

	/**
	 * Auxiliary method for checking arguments of the for loop.
	 */
	private void checkForArgument() {
		if (lexerOutput.getType() != SmartTokenType.STRING && lexerOutput.getType() != SmartTokenType.CONSTANT_DOUBLE
				&& lexerOutput.getType() != SmartTokenType.CONSTANT_INTEGER && lexerOutput.getType() != SmartTokenType.VARIABLE) {
			throw new SmartScriptParserException("Bad for loop argument. Was: " + lexerOutput);
		}
	}

	/**
	 * Creates an element from the current token depending on the type of the
	 * received token. Does not call for the next token.
	 * 
	 * @return constructed element
	 */
	private Element constructElement() {
		if (lexerOutput.getType() == SmartTokenType.STRING) {
			return new ElementString((String) lexerOutput.getValue());

		} else if (lexerOutput.getType() == SmartTokenType.VARIABLE) {
			return new ElementVariable((String) lexerOutput.getValue());

		} else if (lexerOutput.getType() == SmartTokenType.OPERATOR) {
			return new ElementOperator((String) lexerOutput.getValue());

		} else if (lexerOutput.getType() == SmartTokenType.FUNCTION) {
			return new ElementFunction((String) lexerOutput.getValue());

		} else if (lexerOutput.getType() == SmartTokenType.CONSTANT_DOUBLE) {
			return new ElementConstantDouble((double) lexerOutput.getValue());

		} else if (lexerOutput.getType() == SmartTokenType.CONSTANT_INTEGER) {
			return new ElementConstantInteger((Integer) lexerOutput.getValue());
		} else {
			throw new SmartScriptParserException("Unsupported type for element creation. Was: " + lexerOutput);
		}
	}

	/**
	 * Getter for the document node.
	 * 
	 * @return root node of the document
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}
