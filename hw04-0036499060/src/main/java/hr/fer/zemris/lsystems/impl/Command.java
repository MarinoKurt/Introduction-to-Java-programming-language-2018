package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface for the commands.
 * 
 * @author MarinoK
 */
public interface Command {

	/**
	 * Method used to execute the command.
	 * 
	 * @param ctx
	 *            context used to keep track of the states
	 * @param painter
	 *            communicates with the graphic interface
	 */
	void execute(Context ctx, Painter painter);
}
