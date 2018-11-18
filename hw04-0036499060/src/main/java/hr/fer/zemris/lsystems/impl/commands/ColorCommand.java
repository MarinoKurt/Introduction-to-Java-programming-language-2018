package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command that sets the line color.
 * 
 * @author MarinoK
 */
public class ColorCommand implements Command {

	/**
	 * Color to set turtle's lineColor to.
	 */
	private Color color;

	/**
	 * Constructor for the ColorCommand.
	 * 
	 * @param color
	 *            to set turtle's lineColor to
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	/**
	 * Method sets the turtle's color to the given color.
	 * 
	 * @param ctx
	 *            context for keeping track of the current state
	 * @param painter
	 *            communicates with the graphical interface
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setLineColor(color);
	}
}
