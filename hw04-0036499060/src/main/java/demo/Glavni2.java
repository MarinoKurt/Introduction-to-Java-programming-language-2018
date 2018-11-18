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
public class Glavni2 {

	/**
	 * The main method that runs when the program is run.
	 * 
	 * @param args
	 *            command line arguments, usage not expected
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}

	/**
	 * Method used to create Koch curve using Lindermayer text data to set
	 * parameters. Copied from the homework assignment.
	 * 
	 * @param provider
	 *            Lindermayer System Builder Provider
	 * @return Lindermayer System
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] { "origin 0.05 0.4", "angle 0", "unitLength 0.9",
				"unitLengthDegreeScaler 1.0/ 3.0", "", "command F draw 1", "command + rotate 60",
				"command - rotate -60", "", "axiom F", "", "production F F+F--F+F" };
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
}
