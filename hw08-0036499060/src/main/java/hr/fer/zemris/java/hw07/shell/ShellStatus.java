package hr.fer.zemris.java.hw07.shell;

/**
 * Status of the shell, used to determine whether to continue with the
 * communication with the user, or to terminate the program.
 * 
 * @author MarinoK
 */
public enum ShellStatus {

	/** Default shell status. */
	CONTINUE,

	/** Shell status in case there was a problem or the user demands an exit. */
	TERMINATE
}
