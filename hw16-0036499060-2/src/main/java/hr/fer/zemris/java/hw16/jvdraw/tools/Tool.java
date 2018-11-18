package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Interface used for communicating with a drawing tool. Implementing classes
 * react to the given mouse events.
 * 
 * @author MarinoK
 */
public interface Tool {
	
	/**
	 * Executes whenever a mouse button is pressed.
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Executes whenever a mouse button is released.
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Executes whenever a mouse button is clicked.
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Executes whenever cursor is moved.
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Executes whenever a drag action happens.
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Method that draws the object.
	 * 
	 * @param g2d
	 *            graphics
	 */
	public void paint(Graphics2D g2d);
}