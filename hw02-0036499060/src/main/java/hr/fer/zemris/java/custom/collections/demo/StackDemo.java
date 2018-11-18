package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.collections.NotPostfixException;

/**
 * Command-line application which accepts a single command-line argument, in
 * postfix representation, in quotation marks.
 * 
 * @author MarinoK
 *
 */
public class StackDemo {

	/**
	 * The main method that runs when the program is run.
	 * 
	 * @param args
	 *            command line arguments, expected postfix input, immediately
	 *            surrounded by quotation marks, divided by spaces
	 */
	public static void main(String[] args) {

		ObjectStack myStack = new ObjectStack();
		try {
			if (args.length != 1)
				throw new NotPostfixException();

			String[] elements = args[0].split("\\s+");
			System.out.println("Expression evaluates to " + calculatePostfix(myStack, elements) + ".");

		} catch (NotPostfixException badInput) {
			System.out.println(badInput.getMessage());
		}
	}

	/**
	 * Method used to calculate the given postfix expression.
	 * 
	 * @param myStack
	 *            stack used to calculate the postfix expression
	 * @param values
	 *            expression separated into numbers and operations
	 * @return result of the expression
	 */
	public static Object calculatePostfix(ObjectStack myStack, String[] values) {
		for (String one : values) {
			if (one.equals("+") || one.equals("-") || one.equals("*") || one.equals("/") || one.equals("%")) {
				int x = 0;
				int y = 0;
				Object result = 0;
				try {
					y = Integer.valueOf(String.valueOf(myStack.pop()));
					x = Integer.valueOf(String.valueOf(myStack.pop()));
				} catch (NumberFormatException | ClassCastException badInput) {
					throw new NotPostfixException(badInput);
				}
				switch (one) {
				case "+":
					result = x + y;
					break;
				case "-":
					result = x - y;
					break;
				case "*":
					result = x * y;
					break;
				case "%":
					result = x % y;
					break;
				case "/":
					try {
						result = x / y;
					} catch (ArithmeticException dividedByZero) {
						throw new NotPostfixException("You just divided by zero.");
					}
					break;
				default:
					break;
				}
				myStack.push(result);

			} else {
				try {
					Integer.parseInt(one);
				} catch (ClassCastException | NumberFormatException badInput) {
					throw new NotPostfixException();
				}
				myStack.push(one);
			}
		}

		if (myStack.size() != 1) {
			throw new NotPostfixException();
		} else {
			return myStack.pop();
		}
	}
}
