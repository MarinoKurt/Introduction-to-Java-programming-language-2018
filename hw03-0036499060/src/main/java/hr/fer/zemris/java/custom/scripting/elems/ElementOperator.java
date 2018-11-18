package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element that represents an operator.
 * Inherits Element, has a text value of a symbol.
 * 
 * @author MarinoK
 */
public class ElementOperator extends Element {

	/**
	 * Symbol of the element. Read-only.
	 */
	private String symbol;

	/**
	 * Default constructor, takes a symbol.
	 * 
	 * @param symbol
	 *            for the element
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Getter for the symbol of the element.
	 * 
	 * @return symbol as a string
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Returns the symbol of the element.
	 * 
	 * @return symbol as a string
	 */
	@Override
	public String asText() {
		return getSymbol();
	}
}
