package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

/**
 * Implementation of the NameBuilder interface that writes the groups from the
 * matcher.
 * 
 * @author MarinoK
 */
public class GroupNameBuilder implements NameBuilder {

	/** Used for storing matchers group number. */
	private int group;
	
	/** Character to fill the string, so that it would be of desired width. */
	private char fill;
	
	/** Desired width for this part of the name. */
	private Integer width;

	/**
	 * Constructor for the group without width preferences.
	 * @param group of the string part
	 */
	public GroupNameBuilder(int group) {
		this.group = group;
	}

	/**
	 * Constructor for the group with width preferences.
	 * @param group of the string part
	 * @param fill character to fill the string
	 * @param width desired width for this part of the name
	 */
	public GroupNameBuilder(int group, char fill, int width) {
		this.group = group;
		this.fill = fill;
		this.width = width;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		String groupString = info.getGroup(group);

		if (width != null) {
			int diff = width - groupString.length();
			while (diff > 0) {
				info.getStringBuilder().append(fill);
				diff--;
			}
		}
		info.getStringBuilder().append(groupString);
	}

}
