package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Used for representation of structured documents.
 * 
 * @author MarinoK
 */
public abstract class Node {

	/**
	 * Collection used to store children.
	 */
	private ArrayIndexedCollection collection;

	/**
	 * Number of direct children of the node.
	 */
	private int numberOfChildren;

	/**
	 * Default constructor.
	 */
	public Node() {
		this.numberOfChildren = 0;
	}

	/**
	 * Adds given child to a collection of children.
	 * 
	 * @param child
	 *            node to add to the collection
	 */
	public void addChildNode(Node child) {
		if (numberOfChildren() == 0) {
			this.collection = new ArrayIndexedCollection();
		}
		collection.add(child);
		numberOfChildren++;
	}

	/**
	 * Method used to get the number of this node's children.
	 * 
	 * @return number of this node's children
	 */
	public int numberOfChildren() {
		return numberOfChildren;
	}

	/**
	 * Return selected child, if it exists.
	 * 
	 * @param index
	 *            of the child
	 * @return child at the index
	 * @throws IndexOutOfBoundsException
	 *             if the index is not valid
	 */
	public Node getChild(int index) {
		return (Node) collection.get(index);
	}

	/**
	 * Method used to accept the visitor.
	 * 
	 * @param visitor
	 *            given visitor
	 */
	public abstract void accept(INodeVisitor visitor);
}
