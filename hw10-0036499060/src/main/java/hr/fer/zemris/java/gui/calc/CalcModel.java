package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Interface for a model of a calculator.
 */
public interface CalcModel {

	/**
	 * Adds the given listener to the list of listeners.
	 * 
	 * @param l
	 *            listener to add
	 */
	void addCalcValueListener(CalcValueListener l);

	/**
	 * Removes the given listener from the list of listeners.
	 * 
	 * @param l
	 *            listener to remove
	 */
	void removeCalcValueListener(CalcValueListener l);

	/**
	 * Returns "0" if the remembered string is null.
	 * 
	 * @return number that user typed in, as a String
	 */
	String toString();

	/**
	 * 
	 * @return number that user typed in, as a double
	 */
	double getValue();

	/**
	 * Sets the given value as the remembered string, if it is not infinity or NaN.
	 * 
	 * @param value
	 *            to be set
	 */
	void setValue(double value);

	/**
	 * Clears the currently remembered string.
	 */
	void clear();

	/**
	 * Clears the currently remembered string, the active operand, and the pending
	 * operation.
	 */
	void clearAll();

	/**
	 * Changes the sign of the remembered string.
	 */
	void swapSign();

	/**
	 * Inserts the decimal point to the remembered string, if there already isn't
	 * one.
	 */
	void insertDecimalPoint();

	/**
	 * Inserts the given digit at the end of the remembered string.
	 * 
	 * @param digit
	 *            to be inserted
	 */
	void insertDigit(int digit);

	/**
	 * @return true, if the operand is set
	 */
	boolean isActiveOperandSet();

	/**
	 * @return active operand, if it is set
	 * @throws IllegalStateException
	 *             if the operand isn't set
	 */
	double getActiveOperand();

	/**
	 * @param activeOperand
	 *            value to set as the active operand
	 */
	void setActiveOperand(double activeOperand);

	/**
	 * Clears just the active operand.
	 */
	void clearActiveOperand();

	/**
	 * @return pending binary operation as an instance of DoubleBinaryOperator
	 */
	DoubleBinaryOperator getPendingBinaryOperation();

	/**
	 * @param op
	 *            value to be set as the pending binary operation
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}