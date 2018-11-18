package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Representation of a textual data piece.
 * 
 * @author MarinoK
 *
 */
public class TextNode extends Node {

	/**
	 * Read-only text value of the node.
	 */
	private String text;

	/**
	 * Default constructor, takes a string.
	 * 
	 * @param text
	 *            of the node
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Getter for the text value.
	 * 
	 * @return text value of the node
	 */
	public String getText() {
		return text;
	}

	/**
	 * ToString method of TextNode.
	 * 
	 * @return text value of the node
	 */
	@Override
	public String toString() {
		String value = getText();
		if (value.contains("\\") && !value.toLowerCase().contains("end")) {
			try {
				value = value.replace("\\", "\\\\");
			} catch (Exception e) {
				System.out.println("xReplaces2 fault" + e.getMessage());
			}
		}
		if (value.contains("{") && !value.toLowerCase().contains("end")) {
			try {
				value = value.replace("{", "\\{");
			} catch (Exception e) {
				System.out.println("xReplaces fault" + e.getMessage());
			}
		}

		if (value.contains("body\\{")) {
			try {
				value = value.replace("body\\{", "body{");
			} catch (Exception e) {
				System.out.println("xReplaces3 fault" + e.getMessage());
			}
		}

		return value;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
