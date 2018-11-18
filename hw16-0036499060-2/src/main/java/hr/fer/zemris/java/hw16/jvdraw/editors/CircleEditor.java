package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;

/**
 * Editor for the circle object. Checks the given parameters before accepting
 * them. Expected usage: try check editing, if it doesn't throw anything, accept
 * editing.
 * 
 * @author MarinoK
 */
public class CircleEditor extends GeometricalObjectEditor {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Circle to be edited. */
	private Circle circle;

	/** Text area for the x coordinate of the center. */
	private JTextArea centerX;

	/** Text area for the y coordinate of the center. */
	private JTextArea centerY;

	/** Text area for the radius. */
	private JTextArea radius;

	/** Text area for the red component of the color. */
	private JTextArea r;

	/** Text area for the green component of the color. */
	private JTextArea g;

	/** Text area for the blue component of the color. */
	private JTextArea b;

	/** New radius of the circle. */
	private double newRadius;

	/** New x coordinate of the circle center. */
	private int newCenterX;

	/** New y coordinate of the circle center. */
	private int newCenterY;

	/** Value of the red component of the color. */
	private int red;

	/** Value of the green component of the color. */
	private int green;

	/** Value of the blue component of the color. */
	private int blue;

	/**
	 * Constructor for the circle editor.
	 * 
	 * @param circle
	 *            to be edited
	 */
	public CircleEditor(Circle circle) {
		super();
		this.circle = circle;
		initCircleDialog();
	}

	@Override
	public void checkEditing() {
		checkCircleEditing();
	}

	@Override
	public void acceptEditing() {
		acceptCircleEditing();
	}

	/**
	 * Specialized method to accept circle editing.
	 */
	protected void acceptCircleEditing() {
		circle.setCenter(new Point(newCenterX, newCenterY));
		circle.setRadius(newRadius);
		circle.setOutlineColor(new Color(red, green, blue));
	}

	/**
	 * Specialized method to check circle editing.
	 */
	protected void checkCircleEditing() {
		newCenterX = Integer.valueOf(centerX.getText());
		newCenterY = Integer.valueOf(centerY.getText());
		newRadius = Double.valueOf(radius.getText());
		if (newCenterX < 0 || newCenterY < 0 || newRadius < 1e-3) {
			throw new IllegalArgumentException();
		}
		red = Integer.valueOf(r.getText());
		green = Integer.valueOf(g.getText());
		blue = Integer.valueOf(b.getText());
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Initialized the dialog used to edit circle.
	 */
	private void initCircleDialog() {
		add(new JLabel("X coordinate of the center:"));
		centerX = new JTextArea(String.valueOf(circle.getCenter().x));
		add(centerX);

		add(new JLabel("Y coordinate of the center:"));
		centerY = new JTextArea(String.valueOf(circle.getCenter().y));
		add(centerY);

		add(new JLabel("Radius of the circle:"));
		radius = new JTextArea(String.valueOf(circle.getRadius()));
		add(radius);

		add(new JLabel("Outline color (RGB):"));
		r = new JTextArea(String.valueOf(circle.getOutlineColor().getRed()));
		g = new JTextArea(String.valueOf(circle.getOutlineColor().getGreen()));
		b = new JTextArea(String.valueOf(circle.getOutlineColor().getBlue()));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		add(panel);
		panel.add(r);
		panel.add(g);
		panel.add(b);
	}
}
