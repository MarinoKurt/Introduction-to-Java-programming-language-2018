package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;

/**
 * Editor for the filled circle object. Checks the given parameters before
 * accepting them.
 * 
 * @author MarinoK
 */
public class FilledCircleEditor extends CircleEditor {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Filled circle to be edited. */
	private FilledCircle filledCircle;

	/** Value of the red component of the color. */
	private int fr;

	/** Value of the green component of the color. */
	private int fg;

	/** Value of the blue component of the color. */
	private int fb;

	/** Text area for the red component of the color. */
	private JTextArea fR;

	/** Text area for the green component of the color. */
	private JTextArea fG;

	/** Text area for the blue component of the color. */
	private JTextArea fB;

	/**
	 * Constructor for the filled circle editor.
	 * 
	 * @param filledCircle
	 *            to edit
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		super((Circle) filledCircle);
		this.filledCircle = filledCircle;
		this.fr = filledCircle.getFillColor().getRed();
		this.fg = filledCircle.getFillColor().getGreen();
		this.fb = filledCircle.getFillColor().getBlue();
		fR = new JTextArea(String.valueOf(fr));
		fG = new JTextArea(String.valueOf(fg));
		fB = new JTextArea(String.valueOf(fb));
		add(new JLabel("Filling color (RGB):"));
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new GridLayout(0, 3));
		add(colorPanel);
		colorPanel.add(fR);
		colorPanel.add(fG);
		colorPanel.add(fB);
	}

	@Override
	public void checkEditing() {
		checkCircleEditing();
		fr = Integer.valueOf(fR.getText());
		fg = Integer.valueOf(fG.getText());
		fb = Integer.valueOf(fB.getText());
		if (fr < 0 || fr > 255 || fg < 0 || fg > 255 || fb < 0 || fb > 255) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void acceptEditing() {
		acceptCircleEditing();
		filledCircle.setFillColor(new Color(fr, fg, fb));
	}

}
