package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Provider for the color property with the proper method for listeners addition
 * and removal.
 * 
 * @author MarinoK
 *
 */
public interface IColorProvider {

	/**
	 * Getter for the current color of the implementing object.
	 * 
	 * @return color of the object
	 */
	public Color getCurrentColor();

	/**
	 * Adds the given color change listener to the list of listeners of the object.
	 * 
	 * @param listener
	 *            to add
	 */
	public void addColorChangeListener(ColorChangeListener listener);

	/**
	 * Removes the given color change listener from the list of listeners of the
	 * object.
	 * 
	 * @param listener
	 *            to remove
	 */
	public void removeColorChangeListener(ColorChangeListener listener);
}