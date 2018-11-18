package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * Command that takes an path to be set as the current directory.
 * 
 * @author MarinoK
 */
public class CdCommand implements ShellCommand {

	/** Name of the cd command. */
	private static final String CD_COMMAND_NAME = "cd";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ShellParser parser = new ShellParser();
		List<String> argumentsArray = parser.prepareArguments(arguments);

		if (argumentsArray == null || argumentsArray.size() != 1) {
			env.writeln("Command cd must recieve one argument. Type help for more.");
			return ShellStatus.CONTINUE;
		}

		try {
			Path givenPath = Paths.get(argumentsArray.get(0));
			givenPath = env.getCurrentDirectory().resolve(givenPath);
			env.setCurrentDirectory(givenPath.normalize());
		} catch (InvalidPathException e) {
			env.writeln("Invalid path given. Try again.");
			return ShellStatus.CONTINUE;
		}
		env.writeln("Current directory updated.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return CD_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Takes an argument: a path, to be set as the current directory.");
		return description;
	}
}
