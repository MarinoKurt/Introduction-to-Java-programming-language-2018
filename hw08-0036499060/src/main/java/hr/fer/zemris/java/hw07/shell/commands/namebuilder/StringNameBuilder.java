package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

/**
 * Implementation of the NameBuilder interface that literally copies the regular
 * string parts of the given file name to the new file name.
 * 
 * @author MarinoK
 */
public class StringNameBuilder implements NameBuilder {

	/** Extracted part of the former file name. */
	private String namePart;

	/**
	 * Constructor, takes the former and future part of the name.
	 * 
	 * @param namePart former and future part of the name
	 */
	public StringNameBuilder(String namePart) {
		this.namePart = namePart;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(namePart);
	}
}
