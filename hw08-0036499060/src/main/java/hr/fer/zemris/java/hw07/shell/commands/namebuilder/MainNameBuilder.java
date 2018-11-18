package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * MainNameBuilder is used to unite all the name builders added to it.
 * 
 * @author MarinoK
 */
public class MainNameBuilder implements NameBuilder {

	/** List of builders to build the whole name. */
	private List<NameBuilder> allBuilders;

	/** Default constructor, initializes the MainNameBuilder. */
	public MainNameBuilder() {
		this.allBuilders = new ArrayList<>();
	}

	/**
	 * Method used for adding name builders to the main name builder.
	 * 
	 * @param nb
	 *            name builder to be added
	 */
	public void add(NameBuilder nb) {
		this.allBuilders.add(nb);
	}

	@Override
	public void execute(NameBuilderInfo info) {
		for (NameBuilder nb : allBuilders) {
			nb.execute(info); // ?
		}
	}
}
