package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * One of the demo classes from homework assignment. Shows the graphic interface
 * where the user can choose input file containing Lindermayer System Builder settings.
 * 
 * @author MarinoK
 */
public class Glavni3 {

	/**
	 * The main method that runs when the program is run.
	 * 
	 * @param args
	 *            command line arguments, usage not expected
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);

	}
}
