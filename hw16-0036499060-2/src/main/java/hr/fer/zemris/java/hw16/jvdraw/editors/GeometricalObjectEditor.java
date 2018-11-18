package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * Editor for the geometrical object. Checks the given parameters before
 * accepting them. Expected usage: try check editing, if it doesn't throw
 * anything, accept editing.
 * 
 * @author MarinoK
 */
public abstract class GeometricalObjectEditor extends JPanel {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Checks the given editing.
	 */
	public abstract void checkEditing();

	/**
	 * Accepts the given editing.
	 */
	public abstract void acceptEditing();

	/**
	 * Constructor for the geometrical object editor.
	 */
	public GeometricalObjectEditor() {
		super();
		setLayout(new GridLayout(0, 2));
	}

}