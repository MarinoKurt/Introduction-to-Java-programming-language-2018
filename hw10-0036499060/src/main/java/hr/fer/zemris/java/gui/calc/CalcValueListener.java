package hr.fer.zemris.java.gui.calc;

/**
 * Contract between the CalcModel and all its listeners. Informs the
 * implementing class that a change occurred.
 */
public interface CalcValueListener {

	/**
	 * Method notifies the implementing class that a value has changed.
	 * 
	 * @param model
	 *            implementation of the CalcModel
	 */
	void valueChanged(CalcModel model);
}