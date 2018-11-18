package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class keeps track of the turtle states using a stack.
 * 
 * @author MarinoK
 */
public class Context {

	/**
	 * Stack used to store the turtle states.
	 */
	ObjectStack stack;

	/**
	 * Constructor for context.
	 */
	public Context() {
		this.stack = new ObjectStack();
	}

	/**
	 * Returns the current state of the turtle.
	 * 
	 * @return turtles state
	 */
	public TurtleState getCurrentState() {
		return (TurtleState) stack.peek();
	}

	/**
	 * Pushes the given state on the stack.
	 * 
	 * @param state
	 *            to be pushed
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Pops the current state off the stack.
	 */
	public void popState() {
		stack.pop();
	}
}
