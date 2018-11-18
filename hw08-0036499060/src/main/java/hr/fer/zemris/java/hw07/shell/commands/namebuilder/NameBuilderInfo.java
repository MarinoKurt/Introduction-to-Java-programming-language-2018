package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

/**
 * Interface for encapsulating data used for building filenames.
 * 
 * @author MarinoK
 */
public interface NameBuilderInfo {

	/**
	 * Getter for the string builder used for storing the filename.
	 * 
	 * @return string builder, used for storing the filename
	 */
	StringBuilder getStringBuilder();

	/**
	 * Fetches the group at the given index.
	 * 
	 * @param index
	 *            of the group
	 * @return string containing the given group
	 */
	String getGroup(int index);

}
