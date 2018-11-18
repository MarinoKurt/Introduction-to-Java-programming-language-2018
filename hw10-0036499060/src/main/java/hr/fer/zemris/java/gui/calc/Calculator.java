package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.components.BlueButton;
import hr.fer.zemris.java.gui.calc.components.Display;
import hr.fer.zemris.java.gui.calc.components.UnaryOperationButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Implementation of a graphical calculator a bit similar to the Windows XPs.
 * Does not take the input digits from the keyboard.
 * 
 * @author MarinoK
 */
public class Calculator extends JFrame {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Display of the calculator. */
	private Display display;

	/** Memorizes whether the inverting button is selected. */
	private boolean isInverted;

	/** Model of the calculator. */
	private CalcModelImpl model;

	/** Stack of the calculator. */
	private Stack<Double> stack;

	/** Boolean memorizes has the calculation just happened. */
	private boolean calculationHappened;

	/**
	 * Constructor for the calculator.
	 */
	public Calculator() {
		this.model = new CalcModelImpl();
		this.isInverted = false;
		stack = new Stack<>();
		initGUI();
	}

	/**
	 * Main method runs when the program is run.
	 * 
	 * @param args
	 *            usage not expected
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Calculator window = new Calculator();

			window.setLocation(200, 200);
			window.setSize(600, 600);
			window.setVisible(true);
		});
	}

	/**
	 * Method initializes the graphical user interface.
	 */
	private void initGUI() {
		Container p = getContentPane();
		p.setLayout(new CalcLayout(5));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		addDisplay(p);
		addNumbers(p);
		addFunctions(p);
		addOperators(p);
		addInverse(p);
	}

	/**
	 * Auxiliary method used to add the calculator display to the GUI.
	 * 
	 * @param p
	 *            container to add to
	 */
	private void addDisplay(Container p) {
		display = new Display("0");
		display.setHorizontalAlignment(SwingConstants.TRAILING);
		display.setBackground(new Color(255, 221, 0));
		display.setBorder(BorderFactory.createLineBorder(Color.blue));
		display.setOpaque(true);
		display.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		p.add(display, new RCPosition(1, 1));
		model.addCalcValueListener(display);
	}

	/**
	 * Auxiliary method used to add the inverse button to the GUI.
	 * 
	 * @param p
	 *            container to add to
	 */
	private void addInverse(Container p) {
		JCheckBox inverse = new JCheckBox("inv");
		inverse.setBackground(new Color(108, 165, 226));
		inverse.setBorder(BorderFactory.createLineBorder(Color.black));
		inverse.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		inverse.setOpaque(true);
		p.add(inverse, new RCPosition(5, 7));
		inverse.addActionListener((e) -> {
			if (inverse.getModel().isSelected()) {
				isInverted = true;
			} else {
				isInverted = false;
			}
		});
	}

	/**
	 * Auxiliary method used to add all the operators to the GUI.
	 * 
	 * @param p
	 *            container to add to
	 */
	private void addOperators(Container p) {
		int c = 6;
		int r = 1;
		addOperatorButton("n^x", c - 1, r, (a, b) -> Math.pow(a, b), p); // "n√x"
		addOperatorButton("+", r++, c, (a, b) -> a + b, p);
		addOperatorButton("-", r++, c, (a, b) -> a - b, p);
		addOperatorButton("*", r++, c, (a, b) -> a * b, p);
		addOperatorButton("/", r++, c, (a, b) -> a / b, p);

		addButton("=", r++, c, p).addActionListener(e -> {
			double result = 0;
			if (model.isActiveOperandSet()) {
				result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
				model.setActiveOperand(result); // TODO myb krivo
				checkResult(result);
				model.setValue(result);
			}
			calculationHappened = true;
			model.setPendingBinaryOperation(null);
			;
			model.clearActiveOperand();
		});

		addButton("+/-", 5, 4, p).addActionListener(e -> {
			model.swapSign();
		});

		addButton(".", 5, 5, p).addActionListener(e -> {
			model.insertDecimalPoint();
		});

	}

	/**
	 * Auxiliary method used to add all the function buttons to the GUI.
	 * 
	 * @param p
	 *            container to add to
	 */
	private void addFunctions(Container p) {

		int i = 1;
		int j = 2;
		addUnaryOperationButton("1/x", j++, i, e -> 1 / e, e -> 1 / e, p);
		addUnaryOperationButton("log", j++, i, Math::log, e -> Math.pow(10, e), p);
		addUnaryOperationButton("ln", j++, i, x -> Math.log(x) / Math.log(Math.E), Math::exp, p);
		i = 2;
		j = 2;
		addUnaryOperationButton("sin", j++, i, Math::sin, Math::asin, p);
		addUnaryOperationButton("cos", j++, i, Math::cos, Math::acos, p);
		addUnaryOperationButton("tan", j++, i, Math::tan, Math::atan, p);
		addUnaryOperationButton("ctg", j++, i++, Math::atan, Math::tan, p);
		j = 1;
		addButton("clr", j++, 7, p).addActionListener(e -> {
			model.clear();
		});
		addButton("res", j++, 7, p).addActionListener(e -> {
			model.clearAll();
		});
		addButton("push", j++, 7, p).addActionListener(e -> {
			stack.push(model.getValue());
		});
		addButton("pop", j, 7, p).addActionListener(e -> {
			double stackValue = 0.0;
			boolean empty = false;
			try {
				stackValue = stack.pop();
			} catch (EmptyStackException ex) {
				empty = true;
				JOptionPane.showMessageDialog(this, "The stack is empty.", "Empty stack", JOptionPane.ERROR_MESSAGE);
			}
			if (!empty) {
				model.setValue(stackValue);
			}
		});
	}

	/**
	 * Auxiliary method used to add the number buttons to the GUI.
	 * 
	 * @param p
	 *            container to add to
	 */
	private void addNumbers(Container p) {

		for (int i = 7; i < 10; i++) {
			addNumberButton(i, 2, i - 4, p);
		}
		for (int i = 4; i < 7; i++) {
			addNumberButton(i, 3, i - 1, p);
		}
		for (int i = 1; i < 4; i++) {
			addNumberButton(i, 4, i + 2, p);
		}
		addNumberButton(0, 5, 3, p);
	}

	/**
	 * Auxiliary method used to check whether the result is displayable on the
	 * display. Shows a message dialog if it is not.
	 * 
	 * @param result
	 *            to be checked
	 */
	private void checkResult(double result) {
		if (Double.isNaN(result) || Double.isInfinite(result)) {
			JOptionPane.showMessageDialog(this, "Result is not a number.", "Invalid result", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Auxiliary method used to add one blue, inversible button that represents a
	 * unary operation to the GUI.
	 * 
	 * @param name
	 *            of the button
	 * @param row
	 *            of the button
	 * @param column
	 *            of the button
	 * @param normalOperation
	 *            of the button
	 * @param inverseOperation
	 *            of the button
	 * @param p
	 *            container to add to
	 * @return the button as a instance of UnaryOperationButton
	 */
	private UnaryOperationButton addUnaryOperationButton(String name, int row, int column,
			DoubleUnaryOperator normalOperation, DoubleUnaryOperator inverseOperation, Container p) {

		RCPosition position = new RCPosition(row, column);
		UnaryOperationButton uob = new UnaryOperationButton(name, normalOperation, inverseOperation);
		uob.addActionListener(e -> {
			double result;
			if (!isInverted) {
				result = uob.getOperator().applyAsDouble(model.getValue());
			} else {
				result = uob.getInvertedOperator().applyAsDouble(model.getValue());
			}
			checkResult(result);
			model.setValue(result);
		});

		p.add(uob, position);
		return uob;
	}

	/**
	 * Auxiliary method used to add a blue button to the GUI.
	 * 
	 * @param name
	 *            of the button
	 * @param row
	 *            of the button
	 * @param column
	 *            of the button
	 * @param p
	 *            container to add to
	 * @return button as a instance of BlueButton
	 */
	private BlueButton addButton(String name, int row, int column, Container p) {
		RCPosition position = new RCPosition(row, column);
		BlueButton bb = new BlueButton(name);
		p.add(bb, position);
		return bb;
	}

	/**
	 * Auxiliary method used to add a blue button that represents a digit to the
	 * GUI.
	 * 
	 * @param number
	 *            to be added
	 * @param row
	 *            of the button
	 * @param column
	 *            of the button
	 * @param p
	 *            container to add to
	 * @return the added button as a instance of BlueButton
	 */
	private BlueButton addNumberButton(int number, int row, int column, Container p) {
		BlueButton bb = addButton(String.valueOf(number), row, column, p);
		bb.addActionListener((e) -> {

			if (calculationHappened) {
				model.clear();
				calculationHappened = false;
			}
			model.insertDigit(number);
		});
		return bb;
	}

	/**
	 * Auxiliary method used to add one blue, operator button to the GUI.
	 * 
	 * @param name
	 *            of the button
	 * @param r
	 *            row number
	 * @param c
	 *            column number
	 * @param operator
	 *            binary function
	 * @param p
	 *            container to add to
	 * @return button added
	 */
	private BlueButton addOperatorButton(String name, int r, int c, DoubleBinaryOperator operator, Container p) {
		RCPosition position = new RCPosition(r, c);
		BlueButton ob = new BlueButton(name);
		p.add(ob, position);
		ob.addActionListener(e -> {

			if (model.isActiveOperandSet() && model.getPendingBinaryOperation() != null) {
				double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
						model.getValue());
				calculationHappened = true;
				checkResult(result);
				model.setActiveOperand(result);
			} else {
				model.setActiveOperand(model.getValue());
			}
			model.setPendingBinaryOperation(operator);
			model.clear();
		});

		return ob;
	}
}
