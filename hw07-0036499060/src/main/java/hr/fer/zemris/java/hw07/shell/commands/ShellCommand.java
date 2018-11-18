package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Interface used to communicate between commands and environment.
 * 
 * @author MarinoK
 */
public interface ShellCommand {

	/**
	 * Method used to execute the command in the given environment, with the given
	 * arguments.
	 * 
	 * @param env
	 *            reference to the environment of the shell
	 * @param arguments
	 *            for the command, in form of a possibly large, unprocessed string
	 * @return ShellStatus used to determine whether to continue receiving commands
	 *         or to exit
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Getter for the command name.
	 * 
	 * @return command name in form of a string
	 */
	String getCommandName();

	/**
	 * Getter for the command description.
	 * 
	 * @return description of the command, in form of a list of strings
	 */
	List<String> getCommandDescription();
}
