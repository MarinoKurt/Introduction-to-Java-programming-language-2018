package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Interface for the color change listener.
 * 
 * @author MarinoK
 *
 */
public interface ColorChangeListener {
	/**
	 * Method that runs whenever a color of the object changes.
	 * 
	 * @param source
	 *            object
	 * @param oldColor
	 *            former color
	 * @param newColor
	 *            color to be
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}