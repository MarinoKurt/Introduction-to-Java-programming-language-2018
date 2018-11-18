package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command used for popping the current state of the turtle from the stack.
 * 
 * @author MarinoK
 */
public class PopCommand implements Command {

	/**
	 * Method pops the current state from the stack.
	 * 
	 * @param ctx
	 *            context for keeping track of the current state
	 * @param painter
	 *            communicates with the graphical interface
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
