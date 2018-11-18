package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Command moves the turtle, but does not draw the line on the path taken.
 * 
 * @author MarinoK
 */
public class SkipCommand implements Command {

	/**
	 * Factor that multiplies the turtle's displacement.
	 */
	public double step;

	/**
	 * Constructor for the SkipCommand.
	 * 
	 * @param step
	 *            factor that multiplies the turtle's displacement
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	/**
	 * Method used to execute the SkipCommand. Turtle travels for the given step in
	 * its direction, but does not draw the line.
	 * 
	 * @param ctx
	 *            context for keeping track of the current state
	 * @param painter
	 *            communicates with the graphical interface
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();

		double angle = state.getDirection().getAngle();
		double x2 = state.getPosition().getX() + state.getDisplacement() * step * Math.cos(angle);
		double y2 = state.getPosition().getY() + state.getDisplacement() * step * Math.sin(angle);

		state.setPosition(new Vector2D(x2, y2));
	}
}
