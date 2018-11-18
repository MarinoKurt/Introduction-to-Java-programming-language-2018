package hr.fer.zemris.java.gui.calc.components;

import java.util.function.DoubleUnaryOperator;

/**
 * Button that remembers two functions: normal function and its inverse
 * function.
 * 
 * @author MarinoK
 */
public class UnaryOperationButton extends BlueButton {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Normal operator function. */
	private DoubleUnaryOperator operator;

	/** Inverted operator function. */
	private DoubleUnaryOperator invertedOperator;

	/**
	 * Constructor for the UnaryOperationButton.
	 * 
	 * @param name
	 *            of the button
	 * @param normal
	 *            operator function
	 * @param inverse
	 *            operator function
	 */
	public UnaryOperationButton(String name, DoubleUnaryOperator normal, DoubleUnaryOperator inverse) {
		super(name);
		this.operator = normal;
		this.invertedOperator = inverse;
	}

	/**
	 * @return normal operator function
	 */
	public DoubleUnaryOperator getOperator() {
		return operator;
	}

	/**
	 * @return inverted operator function
	 */
	public DoubleUnaryOperator getInvertedOperator() {
		return invertedOperator;
	}

}
