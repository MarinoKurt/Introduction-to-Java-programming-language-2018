package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Command moves the turtle and draws a line on the path taken.
 * 
 * @author MarinoK
 */
public class DrawCommand implements Command {

	/**
	 * Factor that multiplies the turtle's displacement.
	 */
	public double step;

	/**
	 * Constructor for the DrawCommand.
	 * 
	 * @param step
	 *            factor that multiplies the turtle's displacement
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * Method used to execute the DrawCommand. Turtle travels for the given step in
	 * its direction and draws the line on the path taken.
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

		painter.drawLine(state.getPosition().getX(), state.getPosition().getY(), x2, y2, state.getLineColor(),
				1);
		
		state.setPosition(new Vector2D(x2,y2));
	}

}
