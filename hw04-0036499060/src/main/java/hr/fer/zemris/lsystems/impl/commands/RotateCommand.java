package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command sets the turtle's direction to a given angle.
 * 
 * @author MarinoK
 *
 */
public class RotateCommand implements Command {

	/**
	 * Angle in which the turtle will look, when the command executes.
	 */
	private double angle;

	/**
	 * Constructor for the RotateCommand. Transforms the angle from degrees to
	 * radians.
	 * 
	 * @param angle
	 *            to set the turtle's angle to, in degrees
	 */
	public RotateCommand(double angle) {
		angle = angle * Math.PI / 180.0;
		this.angle = angle;
	}

	/**
	 * Method used to execute the RotateCommand. Changes the direction in which the
	 * turtle is currently looking to the angle given.
	 * 
	 * @param ctx
	 *            context for keeping track of the current state
	 * @param painter
	 *            communicates with the graphical interface
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(angle);
	}
}
