package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of the calculator model. Alerts all the added listeners every
 * time a change occurs.
 * 
 * @author MarinoK
 */
public class CalcModelImpl implements CalcModel {

	/** Current number on the calculator display. */
	private String givenString;

	/**
	 * Operand that was on the calculator display before the binary operator was
	 * pressed.
	 */
	private double activeOperand;

	/** Memorizes the pending binary operation. */
	private DoubleBinaryOperator pendingOperation;

	/** Boolean that marks whether the active operand is set. */
	private boolean isActiveOperandSet;

	/** List of listeners to notify if the value changes. */
	private List<CalcValueListener> listeners;

	/**
	 * Constructor for the calculator model implementation.
	 */
	public CalcModelImpl() {
		this.givenString = null;
		this.activeOperand = 0.0;
		this.pendingOperation = null;
		this.isActiveOperandSet = false;
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l);
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l);
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return givenString == null ? 0.0 : Double.valueOf(givenString);
	}

	@Override
	public void setValue(double value) {
		if (!Double.isNaN(value) && Double.isFinite(value)) {
			givenString = String.valueOf(value);
		}
		fire();
	}

	@Override
	public void clear() {
		this.givenString = null;
		fire();
	}

	@Override
	public void clearAll() {
		this.givenString = null;
		this.isActiveOperandSet = false;
		this.pendingOperation = null;
		this.activeOperand = 0.0;
		fire();
	}

	@Override
	public void swapSign() {
		if (givenString == null || givenString.equals("")) return;

		givenString = "-" + givenString;

		if (givenString.contains("--")) {
			givenString = givenString.replace("--", "");
		}
		fire();
	}

	@Override
	public void insertDecimalPoint() {
		if (givenString == null) {
			givenString = "0.";
		} else if (!givenString.contains(".")) {
			givenString = givenString.concat(".");
		}
		fire();
	}

	@Override
	public void insertDigit(int digit) {
		if (this.givenString == null) {
			this.givenString = "";
		} else if (this.givenString.equals("0")) {
			if (digit == 0) return;
			givenString = String.valueOf(digit);
			fire();
			return;
		}

		if (givenString.endsWith("0.")) {
			givenString = "0." + String.valueOf(digit);
			fire();
			return;
		}
		if (givenString.equals("")) {
			givenString = String.valueOf(digit);
			fire();
			return;
		}

		double before = Double.valueOf(givenString);
		String attempt = givenString.concat(String.valueOf(digit));
		double after = Double.valueOf(attempt);

		if (Math.abs(after) < Math.abs(before) || Double.isInfinite(after)) return;

		givenString = attempt;
		fire();

	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperandSet;
	}

	@Override
	public double getActiveOperand() {
		if (!isActiveOperandSet) throw new IllegalStateException();
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.isActiveOperandSet = true;
		fire();
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = 0.0;
		this.isActiveOperandSet = false;
		fire();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		fire();
	}

	@Override
	public String toString() {
		return givenString == null ? "0" : givenString;
	}

	/**
	 * Method used to notify all the added listeners that a value changed.
	 */
	private void fire() {
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}
}
