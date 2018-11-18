package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to exit from the shell. Takes no arguments.
 * 
 * @author MarinoK
 */
public class ExitCommand implements ShellCommand {

	/** Name of the exit command. */
	private static final String EXIT_COMMAND_NAME = "exit";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments!= null && !arguments.trim().equals("")) {
			env.writeln("Exit command takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return EXIT_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Exits from the shell. Takes no arguments.");
		return description;
	}

}
