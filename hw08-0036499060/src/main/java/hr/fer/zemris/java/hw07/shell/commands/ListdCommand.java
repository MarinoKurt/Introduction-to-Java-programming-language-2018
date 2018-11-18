package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Listd command lists all the paths stored on the stack using pushd command.
 * 
 * @author MarinoK
 */
public class ListdCommand implements ShellCommand {

	/** Command name for the listd command. */
	private static String LISTD_COMMAND_NAME = "listd";

	/** Key used for storing the stack in shared data. */
	private static String STACK_KEY = "cdstack";

	@SuppressWarnings("unchecked") // we are certain that a stack will be present at the given key in sharedData
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments != null && !arguments.trim().equals("")) {
			env.writeln("Dropd command takes no arguments.");
			return ShellStatus.CONTINUE;
		}

		if (env.getSharedData(STACK_KEY) == null || ((Stack<Path>) env.getSharedData(STACK_KEY)).isEmpty()) {
			env.writeln("Directory stack is currently empty. Type help for more.");
			return ShellStatus.CONTINUE;
		}
		Stack<Path> dirStack = ((Stack<Path>) env.getSharedData(STACK_KEY));
		for (Path path : dirStack) {
			env.writeln(path.toString());
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return LISTD_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Listd command lists all the paths stored on the stack using pushd command.");
		return description;
	}

}