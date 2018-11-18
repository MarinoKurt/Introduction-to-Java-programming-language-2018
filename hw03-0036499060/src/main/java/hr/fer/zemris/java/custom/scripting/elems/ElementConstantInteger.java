package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Used for the representation of integer expressions.
 * 
 * @author MarinoK
 */
public class ElementConstantInteger extends Element {

	/**
	 * Value of the element.
	 */
	private int value;

	/**
	 * Default constructor, takes a value.
	 * 
	 * @param value
	 *            for the element
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Getter for the value of the element.
	 * 
	 * @return value of the element
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the value of the element.
	 * 
	 * @return value as a string
	 */
	@Override
	public String asText() {
		return String.valueOf(getValue());
	}
}
