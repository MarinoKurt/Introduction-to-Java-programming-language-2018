package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;

/**
 * Graphical component that is fully colored. When pressed, displays a
 * JColorChooser. Its default size is 15x15. Behaves as the Subject of the
 * Observer pattern.
 * 
 * @author MarinoK
 */
public class JColorArea extends JComponent implements IColorProvider {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * List of color change listeners.
	 */
	private List<ColorChangeListener> listeners;

	/**
	 * Currently selected color.
	 */
	private Color selectedColor;

	/** Default size of the component. */
	private final Dimension DEFAULT_SIZE = new Dimension(15, 15);

	/**
	 * Constructor for the JColorArea.
	 * 
	 * @param selectedColor
	 *            color at the initialization
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = Objects.requireNonNull(selectedColor);
		this.listeners = new ArrayList<>();
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Choose new color", selectedColor);
				JColorArea.this.colorChanged(newColor);
			}

		});
	}

	/**
	 * Is called upon whenever a color changes. Notifies the listeners about the
	 * change, and sets the current color.
	 * 
	 * @param newColor
	 *            to be set
	 */
	protected void colorChanged(Color newColor) {
		if (newColor == null) return;
		for (ColorChangeListener l : listeners) {
			l.newColorSelected(this, selectedColor, newColor);
		}
		this.selectedColor = newColor;
		repaint();
	}

	@Override
	public Color getCurrentColor() {
		return this.selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l);
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l);
		listeners.remove(l);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(selectedColor);
		g2d.fillRect(getInsets().left, getInsets().top, getSize().width, getSize().height);
	}

	@Override
	public Dimension getPreferredSize() {
		return DEFAULT_SIZE;
	}

	@Override
	public Dimension getMaximumSize() {
		return DEFAULT_SIZE;
	}

	@Override
	public Dimension getMinimumSize() {
		return DEFAULT_SIZE;
	}

}
