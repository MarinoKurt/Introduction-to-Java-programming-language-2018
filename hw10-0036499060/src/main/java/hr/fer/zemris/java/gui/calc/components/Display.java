package hr.fer.zemris.java.gui.calc.components;

import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.CalcValueListener;

/**
 * Calculator display. Implements the CalcValueListener so it can recieve all
 * the updates from the CalcModel.
 * 
 * @author MarinoK
 */
public class Display extends JLabel implements CalcValueListener {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the Display.
	 * 
	 * @param name
	 *            of the label
	 */
	public Display(String name) {
		super(name);
	}

	@Override
	public void valueChanged(CalcModel model) {
		this.setText(model.toString());
	}

}
