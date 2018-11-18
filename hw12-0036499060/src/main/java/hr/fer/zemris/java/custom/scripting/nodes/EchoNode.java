package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Node representing a command which generates some textual output dynamically.
 * 
 * @author MarinoK
 *
 */
public class EchoNode extends Node {

	/**
	 * Array of elements.
	 */
	private Element[] elements;

	/**
	 * Constructor that takes array of elements.
	 * 
	 * @param elements
	 *            to join
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Getter for the elements array.
	 * 
	 * @return array of elements
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * ToString method of the echo node.
	 * 
	 * @return echo node in form of a string
	 */
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append("{$ = ");
		int i = 0;
		try {
			while (i < elements.length && elements[i] != null) {
					text.append(elements[i].asText());
					text.append(" ");
					i++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SmartScriptParserException("Echo error.");
		}
		text.append("$}");
		return text.toString();
	}
	
	@Override
	public void accept(INodeVisitor visitor){
		visitor.visitEchoNode(this);
	}

}
