package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits Element, has a text value.
 * 
 * @author MarinoK
 */
public class ElementString extends Element {

	/**
	 * Value of the element. Read-only.
	 */
	private String value;

	/**
	 * Default constructor, takes a value.
	 * 
	 * @param value
	 *            for the element
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * Getter for the value of the element.
	 * 
	 * @return value as a string
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns the value of the element.
	 * 
	 * @return value as a string
	 */
	@Override
	public String asText() {
		String value = getValue();
		if(value.contains("\"")) {
			value = value.replace("\"", "\\\"");
		}
		return "\"" + value + "\"";
	}
}
