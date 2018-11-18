package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Implementation of Lindermayer System builder.
 * 
 * @author MarinoK
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Collection that stores symbol-production pairs.
	 */
	private Dictionary productions = new Dictionary();

	/**
	 * Collection that stores symbol-command pairs.
	 */
	private Dictionary commands = new Dictionary();

	/**
	 * Length of one unit of turtle's movement.
	 */
	private double unitLength;

	/**
	 * Scaler used to fit the picture when the depth gets larger.
	 */
	private double unitLengthDegreeScaler;

	/**
	 * Point in the coordinate system from which the turtle starts its journey.
	 */
	private Vector2D origin;

	/**
	 * Angle between the x axis and the direction the turtle is looking.
	 */
	private double angle;

	/**
	 * Initial string, before applying any productions on it.
	 */
	private String axiom;

	/**
	 * Implementation of Lindermayer system, used to generate the string using given
	 * productions, and for drawing using the given painter.
	 * 
	 * @author MarinoK
	 *
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * Method carries out the commands given through the string with applied
		 * productions.
		 */
		@Override
		public void draw(int level, Painter painter) {
			Context ctx = new Context();
			Vector2D direction = new Vector2D(Math.cos(angle), Math.sin(angle));

			double newLength = unitLength * (Math.pow(unitLengthDegreeScaler, level));
			TurtleState startState = new TurtleState(origin, direction, Color.BLACK, newLength);
			ctx.pushState(startState);

			String finalSentence = generate(level);

			char[] finalCharacters = finalSentence.toCharArray();
			for (int i = 0, size = finalCharacters.length; i < size; i++) {
				Command toDo = (Command) commands.get(finalCharacters[i]);
				if (toDo != null) {
					toDo.execute(ctx, painter);
				}
			}
		}

		/**
		 * Method applies the productions on every character in the original axiom, as
		 * many times as level requires it.
		 * 
		 * @return generated string
		 */
		@Override
		public String generate(int level) {
			String sentence = axiom;
			StringBuilder builder;

			while (level > 0) {
				char[] array = sentence.toCharArray();
				builder = new StringBuilder();
				for (int i = 0, size = sentence.length(); i < size; i++) {
					String result = (String) productions.get(array[i]);
					if (result != null) {
						builder.append(result);
					} else {
						builder.append(array[i]);
					}
				}
				sentence = builder.toString();
				level--;
			}
			return sentence;
		}

	}

	/**
	 * Constructor for the LSystemBuilder implementation. Sets default values given
	 * in assignment.
	 */
	public LSystemBuilderImpl() {
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
	}

	/**
	 * Method that returns new Lindermayer system implementation.
	 * 
	 * @return Lindermayer system implementation
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Method used to configure builder settings from text.
	 * 
	 * @return Lindermayer system builder with applied settings from text
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for (int i = 0; i < lines.length; i++) {
			lines[i] = lines[i].trim();
			if (lines[i].equals(""))
				continue;
			try {
				if (lines[i].startsWith("unitLengthDegreeScaler")) {
					double value = 0;
					String[] splittedLine = lines[i].split("\\s+", 2);
					if (splittedLine[0].equals("unitLengthDegreeScaler")) {
						if (splittedLine[1].contains("/")) {
							String[] splittedArgument = splittedLine[1].split("/");
							value = Double.valueOf(splittedArgument[0].trim())
									/ Double.valueOf(splittedArgument[1].trim());
						} else {
							value = Double.valueOf(splittedLine[1]);
						}
						setUnitLengthDegreeScaler(value);
						continue;
					}
				}

				String[] splittedLine = lines[i].split("\\s+");

				if (splittedLine[0].trim().equals("origin")) {
					setOrigin(Double.valueOf(splittedLine[1]), Double.valueOf(splittedLine[2]));
				} else if (splittedLine[0].trim().equals("angle")) {
					setAngle(Double.valueOf(splittedLine[1]));
				} else if (splittedLine[0].trim().equals("unitLength")) {
					setUnitLength(Double.valueOf(splittedLine[1]));
				} else if (splittedLine[0].trim().equals("command")) {
					if (splittedLine.length == 4) {
						registerCommand(splittedLine[1].toCharArray()[0], splittedLine[2] + " " + splittedLine[3]);
					} else {
						registerCommand(splittedLine[1].toCharArray()[0], splittedLine[2]);
					}
				} else if (splittedLine[0].trim().equals("axiom")) {
					setAxiom(splittedLine[1]);
				} else if (splittedLine[0].trim().equals("production")) {
					registerProduction(splittedLine[1].toCharArray()[0], splittedLine[2]);
				} else {
					throw new LSystemException("Invalid input in configureFromText.Was: " + lines[i]);
				}
			} catch (NumberFormatException | LSystemException e) {
				throw new LSystemException("Error in configuring from text: " + e.getMessage());
			}
		}
		return this;
	}

	/**
	 * Method used to add symbol-command pairs to the dictionary.
	 * 
	 * @return Lindermayer system builder with registered command
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		Command command = null;
		String[] splitted = action.split("\\s+");
		try {
			if (splitted[0].toLowerCase().startsWith("draw")) {
				command = new DrawCommand(Double.valueOf(splitted[1]));
			} else if (splitted[0].toLowerCase().startsWith("skip")) {
				command = new SkipCommand(Double.valueOf(splitted[1]));
			} else if (splitted[0].toLowerCase().startsWith("scale")) {
				command = new ScaleCommand(Double.valueOf(splitted[1]));
			} else if (splitted[0].toLowerCase().startsWith("rotate")) {
				command = new RotateCommand(Double.valueOf(splitted[1]));
			} else if (splitted[0].toLowerCase().startsWith("push")) {
				command = new PushCommand();
			} else if (splitted[0].toLowerCase().startsWith("pop")) {
				command = new PopCommand();
			} else if (splitted[0].toLowerCase().startsWith("color")) {
				command = new ColorCommand(Color.decode("#" + splitted[1]));
			}
		} catch (NumberFormatException e) {
			throw new LSystemException("Parsing error. Input was: " + splitted[1]);
		}
		commands.put(symbol, command);
		return this;
	}

	/**
	 * Method used to add symbol-production pairs to the dictionary.
	 * 
	 * @return Lindermayer system builder with registered production
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	/**
	 * Setter for the angle. Transforms the angle from degrees to radians.
	 * 
	 * @param angle
	 *            in degrees
	 * @return Lindermayer system builder with set angle
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		angle = angle * Math.PI / 180.0;
		this.angle = angle;
		return this;
	}

	/**
	 * Setter for the axiom.
	 * 
	 * @param axiom
	 *            beginning string of the system
	 * @return Lindermayer system builder with set axiom
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Setter for the origin.
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * 
	 * @return Lindermayer system builder with set origin
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Setter for the unitLength.
	 * 
	 * @param unitLength
	 *            length of one unit of turtle's movement
	 * @return Lindermayer system builder with set unitLength
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Setter for the unitLengthDegreeScaler.
	 * 
	 * @param unitLengthDegreeScaler
	 *            scaler for the unitLength
	 * @return Lindermayer system builder with set unitLengthDegreeScaler
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}
