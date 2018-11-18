package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

/**
 * Interface used for building filenames.
 * 
 * @author MarinoK
 */
public interface NameBuilder {

	/**
	 * Method used for executing the name builder.
	 * 
	 * @param info
	 *            data used for executing
	 */
	void execute(NameBuilderInfo info);
}
