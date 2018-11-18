package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

import java.util.regex.Matcher;

/**
 * Implementation of the NameBuilderInfo. Represents encapsulated data used for
 * building file names.
 * 
 * @author MarinoK
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {

	/** Matcher used for grouping name parts. */
	private Matcher matcher;

	/** String builder used for building the file name. */
	private StringBuilder sb;

	/**
	 * Default constructor.
	 * 
	 * @param matcher
	 *            used for grouping name parts
	 */
	public NameBuilderInfoImpl(Matcher matcher) {
		this.matcher = matcher;
		this.sb = new StringBuilder();
	}

	public StringBuilder getStringBuilder() {
		return this.sb;
	}

	public String getGroup(int index) {
		String retVal = null;
		try {
			retVal = matcher.group(index);
		} catch (IndexOutOfBoundsException e) {
			throw new NameBuilderParserException("No such matcher group.");
		}
		return retVal;
	}

}
