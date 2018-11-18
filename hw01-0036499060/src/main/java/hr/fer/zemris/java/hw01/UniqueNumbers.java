package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program implements a binary tree. User can add integer elements to the tree.
 * When user concludes the input by typing 'kraj', the program prints out the
 * elements, both in the ascending and descending order.
 * 
 * @author MarinoK
 * @version 1.0
 */
public class UniqueNumbers {

	/**
	 * Class defines a node in this implementation of a binary tree.
	 * 
	 * @author MarinoK
	 *
	 */
	static class TreeNode {

		/**
		 * Points to the node with the smaller value than the current one,
		 * if it exists.
		 */
		TreeNode left;

		/**
		 * Points to the node with the bigger value than the current one,
		 * if it exists.
		 */
		TreeNode right;

		/**
		 * Whole number value of the current node, given by the user.
		 */
		int value;

		/**
		 * Constructor creates a node with the given value. It's left and right pointers
		 * are null by default because of the way the tree behaves by definition.
		 * 
		 * @param value
		 *            of the node to be created
		 */
		public TreeNode(int value) {
			super();
			this.left = null;
			this.right = null;
			this.value = value;
		}
	}

	/**
	 * The main method that runs when the program is run.
	 * 
	 * @param args
	 *            command line arguments, usage not expected
	 */
	public static void main(String[] args) {
		TreeNode head = null;

		head = loadInput(head);

		if (head != null) {
			System.out.println(treeSize(head));
		}

		System.out.print("Ispis od najmanjeg: ");
		sortNodes(head, true);
		System.out.println();
		System.out.print("Ispis od najvećeg: ");
		sortNodes(head, false);
	}

	/**
	 * Method reads from the console until the string 'kraj' appears, and delegates
	 * further processing of the data to filterInput.
	 * 
	 * @param head
	 *            root node of a tree
	 * @return root node of the, now altered, tree
	 */
	private static TreeNode loadInput(TreeNode head) {
		String token;
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.print("Unesite broj > ");
			token = scan.next();
			if (token.toLowerCase().equals("kraj")) {
				break;
			}
			head = filterInput(token, head);
		}
		scan.close();
		return head;
	}

	/**
	 * Method is used to filter out the non-number data which can be input by the
	 * user. It parses the data, and ideally adds it to the tree.
	 * 
	 * @param userInput
	 *            ideally, a number to add to the tree
	 * @param head
	 *            root node of the tree
	 * @return root node of the, now altered, tree
	 */
	private static TreeNode filterInput(String userInput, TreeNode head) {
		int parsedInput;
		try {
			parsedInput = Integer.parseInt(userInput);
		} catch (NumberFormatException parseFailed) {
			System.out.println("'" + userInput + "' nije cijeli broj.");
			return head;
		}
		return addNode(head, parsedInput);
	}

	/**
	 * Method checks is the given value already added to the tree.
	 * 
	 * @param head
	 *            root of the tree
	 * @param value
	 *            that will be searched for throughout the tree
	 * @return true if the value is contained
	 */
	static boolean containsValue(TreeNode head, int value) {
		while (head != null) {
			if (value == head.value) {
				return true;
			} else if (head.left == null && head.right == null) {
				return false;
			} else if (value < head.value) {
				if (head.left != null) {
					head = head.left;
				} else {
					return false;
				}
			} else if (value > head.value) {
				if (head.right != null) {
					head = head.right;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Method adds the given value to the tree, if it isn't already contained.
	 * 
	 * @param head
	 *            root of the tree
	 * @param value
	 *            to be added to the tree
	 * @return root of the, now altered, tree
	 */
	static TreeNode addNode(TreeNode head, int value) {

		if (head == null) {
			head = new TreeNode(value);
			System.out.println("Dodano.");
		} else if (containsValue(head, value)) {
			System.out.println("Broj već postoji. Preskačem.");
			return head;
		} else {
			TreeNode walker = head;
			while (true) {
				if (value < walker.value) {
					if (walker.left != null) {
						walker = walker.left;
					} else {
						walker.left = new TreeNode(value);
						System.out.println("Dodano.");
						break;
					}
				} else {
					if (walker.right != null) {
						walker = walker.right;
					} else {
						walker.right = new TreeNode(value);
						System.out.println("Dodano.");
						break;
					}
				}
			}
		}

		return head;
	}

	/**
	 * Method prints out the node values in ascending or descending order, depending
	 * on the parameter direction, using an inorder traversal in the requested
	 * direction.
	 *
	 * @param head
	 *            root of the tree
	 *
	 * @param direction
	 *            true for ascending, false for descending
	 */
	static void sortNodes(TreeNode head, boolean direction) {
		if (head == null) {
			return;
		}
		if (direction) {
			sortNodes(head.left, direction);
			System.out.print(head.value + " ");
			sortNodes(head.right, direction);
		} else {
			sortNodes(head.right, direction);
			System.out.print(head.value + " ");
			sortNodes(head.left, direction);
		}

	}

	/**
	 * Method counts the nodes of a tree in a recursive manner.
	 * 
	 * @param head
	 *            root of the given tree
	 * @return number of elements in the tree
	 */
	static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		}
		if (head.left == null && head.right == null) {
			return 1;
		}
		return treeSize(head.left) + treeSize(head.right) + 1;
	}
}
