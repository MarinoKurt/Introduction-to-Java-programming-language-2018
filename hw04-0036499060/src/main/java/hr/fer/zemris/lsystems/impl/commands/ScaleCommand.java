package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command that multiplies the turtle's displacement with a given factor.
 * 
 * @author MarinoK
 */
public class ScaleCommand implements Command {

	/**
	 * Factor to multiply the turtle's displacement with.
	 */
	private double factor;

	/**
	 * Constructor for the ScaleCommand.
	 * 
	 * @param factor
	 *            to multiply the turtle's displacement with
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Method multiplies the turtle's displacement with the given factor.
	 * 
	 * @param ctx
	 *            context for keeping track of the current state
	 * @param painter
	 *            communicates with the graphical interface
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setDisplacement(ctx.getCurrentState().getDisplacement() * factor);
	}

}
