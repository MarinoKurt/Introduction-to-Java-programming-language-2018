package hr.fer.zemris.java.gui.calc.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Class extends JButton, and designs it for this assignment.
 * 
 * @author MarinoK
 */
public class BlueButton extends JButton {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the BlueButton.
	 * 
	 * @param name
	 *            of the button
	 */
	public BlueButton(String name) {
		super(name);
		setOpaque(true);
		setBackground(new Color(112, 156, 221));
		setBorder(BorderFactory.createLineBorder(Color.blue));
		setOpaque(true);
		setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		setHorizontalAlignment(SwingConstants.CENTER);
		setForeground(Color.BLACK);
	}

}
