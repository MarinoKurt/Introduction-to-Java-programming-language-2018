package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Representing a single for-loop construct.
 * 
 * @author MarinoK
 *
 */
public class ForLoopNode extends Node {

	/**
	 * Variable at the beginning of this for loop.
	 */
	private ElementVariable variable;

	/**
	 * Expression to begin the loop with.
	 */
	private Element startExpression;

	/**
	 * Expression with which the loop ends.
	 */
	private Element endExpression;

	/**
	 * Step for the loop. Can be null.
	 */
	private Element stepExpression;

	/**
	 * Constructor for the ForLoopNode.
	 * 
	 * @param variable
	 *            must be a variable, must not be null
	 * @param startExpression
	 *            element, must not be null
	 * @param endExpression
	 *            element, must not be null
	 * @param stepExpression
	 *            can be null
	 * 
	 * @throws SmartScriptParserException
	 *             if the arguments aren't satisfactory
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		if (startExpression == null || variable == null || endExpression == null) {
			throw new SmartScriptParserException("Invalid for loop.");
		}
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Getter for the variable.
	 * 
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Getter for the start expression.
	 * 
	 * @return start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Getter for the end expression.
	 * 
	 * @return end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Getter for the step expression.
	 * 
	 * @return step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	/**
	 * ToString method of the for loop.
	 * 
	 * @return for loop in the form of a string
	 */
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append("{$ FOR ");
		text.append(variable.asText());
		text.append(" ");
		text.append(startExpression.asText());
		text.append(" ");
		text.append(endExpression.asText());
		text.append(" ");
		if (stepExpression != null) {
			text.append(stepExpression.asText());
		}
		text.append(" $}");

		return text.toString();
	}
}