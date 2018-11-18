package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * Pushd command pushes the current directory onto the stack, and sets the given
 * path as the current directory.
 * 
 * @author MarinoK
 */
public class PushdCommand implements ShellCommand {

	/** Command name for the pushd command. */
	private static String PUSHD_COMMAND_NAME = "pushd";

	/** Key used for storing the stack in shared data. */
	private static String STACK_KEY = "cdstack";

	@SuppressWarnings("unchecked") // we are certain that a stack will be present at the given key in sharedData
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ShellParser parser = new ShellParser();
		List<String> argumentsArray = parser.prepareArguments(arguments);
		if (argumentsArray == null || argumentsArray.size() != 1) {
			env.writeln("Command pushd must recieve one argument. Type help for more.");
			return ShellStatus.CONTINUE;
		}
		Path givenPath = null;
		try {
			givenPath = Paths.get(argumentsArray.get(0));
			givenPath = env.getCurrentDirectory().resolve(givenPath);
			if(!Files.exists(givenPath)) throw new InvalidPathException("",	"");
		} catch (InvalidPathException e) {
			env.writeln("Invalid path given. Try again.");
			return ShellStatus.CONTINUE;
		}

		if (env.getSharedData(STACK_KEY) == null) {
			env.setSharedData(STACK_KEY, new Stack<Path>());
		}
		env.writeln(env.getCurrentDirectory().toString() + " pushed onto the directory stack.");
		((Stack<Path>) env.getSharedData(STACK_KEY)).push(env.getCurrentDirectory());
		env.setCurrentDirectory(givenPath.normalize());
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return PUSHD_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Pushd command pushes the current directory onto the stack, ");
		description.add("and sets the given path as the current directory.");
		return description;
	}

}
