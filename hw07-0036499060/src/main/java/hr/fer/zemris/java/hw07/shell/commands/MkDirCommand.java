package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * MkDir command takes a single argument: directory name. It creates the
 * appropriate directory structure.
 * 
 * @author MarinoK
 */
public class MkDirCommand implements ShellCommand {

	/** Command name for the mkdir command. */
	private final String MKDIR_COMMAND_NAME = "mkdir";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		ShellParser parser = new ShellParser();
		List<String> argumentsArray = parser.prepareArguments(arguments);

		if (argumentsArray == null || argumentsArray.size() != 1) {
			env.writeln("Command must recieve one argument. Type help for more.");
			return ShellStatus.CONTINUE;
		}

		File file = Paths.get(argumentsArray.get(0)).toFile();

		if (file.exists()) {
			env.writeln("Directory already exists!");
			return ShellStatus.CONTINUE;
		}
		if (file.mkdir()) {
			env.writeln("Directory created.");
		} else {
			env.writeln("There was a problem in creating the directory.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return MKDIR_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("MkDir command takes a single argument: directory name.");
		description.add("It creates the appropriate directory structure, if it doesn't already exist.");
		return description;
	}

}
