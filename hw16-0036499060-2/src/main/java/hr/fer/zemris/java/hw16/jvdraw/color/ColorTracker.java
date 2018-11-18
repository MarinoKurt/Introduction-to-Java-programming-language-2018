package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;

/**
 * JLabel which displays the selected foreground and background colors using
 * their RGB components.
 * 
 * @author MarinoK
 */
public class ColorTracker extends JLabel implements ColorChangeListener {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Format of the label text. */
	private final String LABEL_FORMAT = "Foreground color: (%d, %d, %d), background color: (%d, %d, %d).";

	/**
	 * Background color area.
	 */
	private IColorProvider bgColorArea;

	/**
	 * Foreground color area.
	 */
	private IColorProvider fgColorArea;

	/**
	 * Currently selected background color.
	 */
	private Color backgroundColor;

	/**
	 * Currently selected foreground color.
	 */
	private Color foregroundColor;

	/**
	 * Constructor for the color tracker label.
	 * 
	 * @param fgColorArea
	 *            foreground color area
	 * @param bgColorArea
	 *            background color area
	 */
	public ColorTracker(IColorProvider fgColorArea, IColorProvider bgColorArea) {
		this.fgColorArea = fgColorArea;
		this.bgColorArea = bgColorArea;
		this.backgroundColor = bgColorArea.getCurrentColor();
		this.foregroundColor = fgColorArea.getCurrentColor();
		fgColorArea.addColorChangeListener(this);
		bgColorArea.addColorChangeListener(this);
		updateText();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		if (source == fgColorArea) {
			this.foregroundColor = newColor;
		} else if (source == bgColorArea) {
			this.backgroundColor = newColor;
		} else {
			throw new UnsupportedOperationException("Color tracker cares only about background and foreground color!");
		}
		updateText();
	}

	/**
	 * Auxiliary method which updates the text of the label.
	 */
	private void updateText() {
		this.setText(String.format(LABEL_FORMAT, foregroundColor.getRed(), foregroundColor.getGreen(),
				foregroundColor.getBlue(), backgroundColor.getRed(), backgroundColor.getGreen(),
				backgroundColor.getBlue()));
	}

}
