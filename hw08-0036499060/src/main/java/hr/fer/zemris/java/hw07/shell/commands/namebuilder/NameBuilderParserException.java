package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

/**
 * Custom exception for the NameBuilderParser. Thrown when input is not
 * satisfactory.
 * 
 * @author MarinoK
 */
public class NameBuilderParserException extends RuntimeException {

	/**
	 * Default serial version ID for the NameBuilderParserException.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor for the NameBuilderParserException.
	 */
	public NameBuilderParserException() {
	}

	/**
	 * Constructor for the NameBuilderParserException that takes a message.
	 * 
	 * @param message
	 *            for the user
	 */
	public NameBuilderParserException(String message) {
		super(message);
	}

}
