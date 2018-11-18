package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command takes no arguments. It drops the top directory from the directory
 * stack. Does not change the current directory.
 * 
 * @author MarinoK
 */
public class DropdCommand implements ShellCommand {

	/** Command name for the dropd command. */
	private static String DROPD_COMMAND_NAME = "dropd";

	/** Key used for storing the stack in shared data. */
	private static String STACK_KEY = "cdstack";

	@SuppressWarnings("unchecked") // we are certain that, if anything is present at the given key, it will be a
									// stack of paths
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments != null && !arguments.trim().equals("")) {
			env.writeln("Dropd command takes no arguments.");
			return ShellStatus.CONTINUE;
		}

		if (env.getSharedData(STACK_KEY) == null || ((Stack<Path>) env.getSharedData(STACK_KEY)).isEmpty()) {
			env.writeln("No path was pushed onto the stack using pushd command. Type help for more.");
			return ShellStatus.CONTINUE;
		}

		((Stack<Path>) env.getSharedData(STACK_KEY)).pop();

		env.writeln("Top directory from the stack dropped.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return DROPD_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command takes no arguments. It drops the top directory from the directory stack.");
		description.add("Does not change the current directory.");
		return description;
	}

}
