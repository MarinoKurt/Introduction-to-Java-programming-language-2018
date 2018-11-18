package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;

public class FPolygonEditor extends GeometricalObjectEditor {

	private List<Point> newPoints;
	private Map<JTextArea, JTextArea> textAreas;
	private FilledPolygon fp;

	private JTextArea r;

	private JTextArea g;

	private JTextArea b;

	private JTextArea r2;

	private JTextArea g2;

	private JTextArea b2;

	public FPolygonEditor(FilledPolygon fp) {
		newPoints = new ArrayList<>();
		textAreas = new HashMap<>();
		this.fp = fp;
		initPolyDialog();
	}

	private void initPolyDialog() {

		int i = 1;
		for (Point point : fp.getPoints()) {

			add(new JLabel("X coordinate of point number " + i));
			JTextArea p = new JTextArea(String.valueOf(point.x));
			add(p);
			add(new JLabel("Y coordinate of of point number" + i));
			JTextArea p2 = new JTextArea(String.valueOf(point.y));
			add(p2);
			textAreas.put(p, p2);
			i++;

		}

		add(new JLabel("Outline color (RGB):"));
		r = new JTextArea(String.valueOf(fp.getOutlineColor().getRed()));
		g = new JTextArea(String.valueOf(fp.getOutlineColor().getGreen()));
		b = new JTextArea(String.valueOf(fp.getOutlineColor().getBlue()));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		add(panel);
		panel.add(r);
		panel.add(g);
		panel.add(b);
		add(new JLabel("Filling color (RGB):"));
		r2 = new JTextArea(String.valueOf(fp.getFillColor().getRed()));
		g2 = new JTextArea(String.valueOf(fp.getFillColor().getGreen()));
		b2 = new JTextArea(String.valueOf(fp.getFillColor().getBlue()));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(0, 3));
		add(panel2);
		panel2.add(r2);
		panel2.add(g2);
		panel2.add(b2);

	}

	@Override
	public void checkEditing() {

		// TODO Auto-generated method stub

	}

	@Override
	public void acceptEditing() {
		// clearaj prošlu listu pointova i dodaj sve ove?

	}

}
