package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Used for the representation of double value expressions.
 * 
 * @author MarinoK
 */
public class ElementConstantDouble extends Element {

	/**
	 * Value of the element.
	 */
	private double value;

	/**
	 * Default constructor, takes a value.
	 * 
	 * @param value
	 *            for the element
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Getter for the value of the element.
	 * 
	 * @return value of the element
	 */
	public double getValue() {
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
