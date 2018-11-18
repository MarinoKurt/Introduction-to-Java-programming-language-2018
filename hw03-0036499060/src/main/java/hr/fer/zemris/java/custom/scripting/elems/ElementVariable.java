package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits Element, has a name.
 * 
 * @author MarinoK
 */
public class ElementVariable extends Element {

	/**
	 * Name of the element. Read-only.
	 */
	private String name;

	/**
	 * Default constructor, takes a name.
	 * 
	 * @param name
	 *            for the element
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 * Getter for the name of the element.
	 * 
	 * @return name as a string
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the name of the element.
	 * 
	 * @return name as a string
	 */
	@Override
	public String asText() {
		return getName();
	}
}
