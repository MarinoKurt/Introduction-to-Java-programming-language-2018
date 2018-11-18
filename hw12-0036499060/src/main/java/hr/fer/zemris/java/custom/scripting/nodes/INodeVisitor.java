package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node visitor interface.
 */
public interface INodeVisitor {

	/**
	 * Method used to visit a text node.
	 * 
	 * @param node
	 *            text node
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Method used to visit a for loop node.
	 * 
	 * @param node
	 *            for loop node
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Method used to visit a echo node.
	 * 
	 * @param node
	 *            echo node
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Method used to visit a document node.
	 * 
	 * @param node
	 *            document node
	 */
	public void visitDocumentNode(DocumentNode node);
}
