package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element that represents a function.
 * 
 * @author MarinoK
 */
public class ElementFunction extends Element {

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
	public ElementFunction(String name) {
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
		return "@"+getName();
	}
}
