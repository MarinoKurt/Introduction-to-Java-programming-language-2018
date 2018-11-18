package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * One of the demo classes from homework assignment. Creates the Koch curve.
 * 
 * @author MarinoK
 */
public class Glavni1 {

	/**
	 * The main method that runs when the program is run.
	 * 
	 * @param args
	 *            command line arguments, usage not expected
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
	}

	/**
	 * Method used to create Koch curve using Lindermayer system methods to set
	 * parametars. Copied from the homework assignment.
	 * 
	 * @param provider
	 *            Lindermayer System Builder Provider
	 * @return Lindermayer System
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder().registerCommand('F', "draw 1").registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60").setOrigin(0.05, 0.4).setAngle(0).setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0 / 3.0).registerProduction('F', "F+F--F+F").setAxiom("F").build();
	}

}
