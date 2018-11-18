package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Engine used to run the smart scripts.
 * 
 * @author MarinoK
 */
public class SmartScriptEngine {

	/** Root document node. */
	private DocumentNode documentNode;

	/** Context of the engine used to communicate throughout HTML. */
	private RequestContext requestContext;

	/** Multistack used for storing objects. */
	private ObjectMultistack multistack = new ObjectMultistack();

	/** NodeVisitor implemented especially for running the scripts. */
	private INodeVisitor visitor = new EngineVisitor();

	/**
	 * Constructor for the SmartScriptEngine.
	 * 
	 * @param documentNode
	 *            root node
	 * @param requestContext
	 *            context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Method used to execute the script.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

	/**
	 * NodeVisitor implemented especially for running the scripts.
	 */
	private class EngineVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variableName = node.getVariable().asText();
			String startExp = node.getStartExpression().asText();
			String endExp = node.getEndExpression().asText();
			String stepExp = node.getStepExpression().asText();

			Integer start = 0;
			Integer step = 0;
			Integer end = 0;
			try {
				start = Integer.valueOf(startExp);
				step = Integer.valueOf(stepExp);
				end = Integer.valueOf(endExp);
			} catch (NumberFormatException e) {
				System.err.println("Problem with parsing forLoop values.");
			}

			multistack.push(variableName, new ValueWrapper(start));

			while (Integer.compare(start, end) <= 0) {

				for (int i = 0, children = node.numberOfChildren(); i < children - 1; i++) {
					node.getChild(i).accept(this);
				}

				start = (Integer) multistack.pop(variableName).getValue();
				start += step;
				multistack.push(variableName, new ValueWrapper(start));
			}

			multistack.pop(variableName);

		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> temporaryStack = new Stack<>();
			Functions funs = new Functions(temporaryStack, requestContext);
			for (Element e : node.getElements()) {

				if (e instanceof ElementConstantInteger) {
					int val = ((ElementConstantInteger) e).getValue();
					temporaryStack.push(val);
				} else if (e instanceof ElementConstantDouble) {
					double val = ((ElementConstantDouble) e).getValue();
					temporaryStack.push(val);
				} else if (e instanceof ElementString) {
					String val = ((ElementString) e).getValue();

					temporaryStack.push(val);

				} else if (e instanceof ElementVariable) {
					String variableName = ((ElementVariable) e).getName();
					String variableValue = String.valueOf(multistack.peek(variableName).getValue());
					temporaryStack.push(variableValue);
				} else if (e instanceof ElementOperator) {
					String symbol = ((ElementOperator) e).getSymbol();

					ValueWrapper firstValue = new ValueWrapper(temporaryStack.pop());
					ValueWrapper secondValue = new ValueWrapper(temporaryStack.pop());

					switch (symbol) {
					case "+":
						firstValue.add(secondValue.getValue());
						break;
					case "-":
						firstValue.subtract(secondValue.getValue());
						break;
					case "*":
						firstValue.multiply(secondValue.getValue());
						break;
					case "/":
						firstValue.divide(secondValue.getValue());
						break;
					default:
						throw new UnsupportedOperationException();
					}
					temporaryStack.push(firstValue.getValue());

				} else if (e instanceof ElementFunction) {
					String name = ((ElementFunction) e).getName();
					funs.getFunction(name).run();
				}
			}

			List<Object> toWrite = new LinkedList<>();
			while (!temporaryStack.isEmpty()) {
				toWrite.add(0, temporaryStack.pop());
			}
			for (Object w : toWrite) {
				try {
					requestContext.write(w.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, children = node.numberOfChildren(); i < children; i++) {
				node.getChild(i).accept(this);
			}
			try {
				requestContext.write("\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}
}
