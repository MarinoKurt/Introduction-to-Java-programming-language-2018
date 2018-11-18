package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command used for pushing the current state of the turtle on the stack.
 * 
 * @author MarinoK
 */
public class PushCommand implements Command {

	/**
	 * Method pushes a copy of the current state on the stack.
	 * 
	 * @param ctx
	 *            context for keeping track of the current state
	 * @param painter
	 *            communicates with the graphical interface
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
