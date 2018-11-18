package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Editor for the line object. Checks the given parameters before accepting
 * them. Expected usage: try check editing, if it doesn't throw anything, accept
 * editing.
 * 
 * @author MarinoK
 */
public class LineEditor extends GeometricalObjectEditor {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Line to be edited. */
	private Line line;

	/** Text area for the x1 coordinate. */
	private JTextArea x1txt;

	/** Text area for the y1 coordinate. */
	private JTextArea y1txt;

	/** Text area for the x2 coordinate. */
	private JTextArea x2txt;

	/** Text area for the y2 coordinate. */
	private JTextArea y2txt;

	/** X coordinate of the start point. */
	private int x1;

	/** Y coordinate of the start point. */
	private int y1;

	/** X coordinate of the end point. */
	private int x2;

	/** Y coordinate of the end point. */
	private int y2;

	/** Text area for the red component of the color. */
	private JTextArea r;

	/** Text area for the green component of the color. */
	private JTextArea g;

	/** Text area for the blue component of the color. */
	private JTextArea b;

	/** Value of the red component of the color. */
	private int red;

	/** Value of the green component of the color. */
	private int green;

	/** Value of the blue component of the color. */
	private int blue;

	/**
	 * Constructor for the line editor.
	 * 
	 * @param line
	 *            to be edited
	 */
	public LineEditor(Line line) {
		this.line = line;
		initLineDialog();
	}

	@Override
	public void checkEditing() {
		x1 = Integer.valueOf(x1txt.getText());
		y1 = Integer.valueOf(y1txt.getText());
		x2 = Integer.valueOf(x2txt.getText());
		y2 = Integer.valueOf(y2txt.getText());
		if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
			throw new IllegalArgumentException();
		}
		red = Integer.valueOf(r.getText());
		green = Integer.valueOf(g.getText());
		blue = Integer.valueOf(b.getText());
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void acceptEditing() {
		line.setA(new Point(x1, y1));
		line.setB(new Point(x2, y2));
		line.setOutlineColor(new Color(red, green, blue));
		line.fire();
	}

	/**
	 * Method initializes the dialog used for editing lines.
	 */
	private void initLineDialog() {
		add(new JLabel("X coordinate of the starting point:"));
		x1txt = new JTextArea(String.valueOf(line.getA().x));
		add(x1txt);

		add(new JLabel("Y coordinate of the starting point:"));
		y1txt = new JTextArea(String.valueOf(line.getA().y));
		add(y1txt);

		add(new JLabel("X coordinate of the ending point:"));
		x2txt = new JTextArea(String.valueOf(line.getB().x));
		add(x2txt);

		add(new JLabel("Y coordinate of the ending point:"));
		y2txt = new JTextArea(String.valueOf(line.getB().y));
		add(y2txt);

		add(new JLabel("Outline color (RGB):"));
		r = new JTextArea(String.valueOf(line.getOutlineColor().getRed()));
		g = new JTextArea(String.valueOf(line.getOutlineColor().getGreen()));
		b = new JTextArea(String.valueOf(line.getOutlineColor().getBlue()));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		add(panel);
		panel.add(r);
		panel.add(g);
		panel.add(b);
	}

}
