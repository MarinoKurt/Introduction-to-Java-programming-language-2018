package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command takes no arguments. It pops the top path from the same stack that
 * pushd command pushed it onto, and sets it as the current directory.
 * 
 * @author MarinoK
 */
public class PopdCommand implements ShellCommand {

	/** Command name for the popd command. */
	private static String POPD_COMMAND_NAME = "popd";

	/** Key used for storing the stack in shared data. */
	private static String STACK_KEY = "cdstack";

	@SuppressWarnings("unchecked") // we are certain that, if anything is present at the given key, it will be a
									// stack of paths
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments != null && !arguments.trim().equals("")) {
			env.writeln("Popd command takes no arguments.");
			return ShellStatus.CONTINUE;
		}

		if (env.getSharedData(STACK_KEY) == null || ((Stack<Path>) env.getSharedData(STACK_KEY)).isEmpty()) {
			env.writeln("No path was pushed onto the stack using pushd command. Type help for more.");
			return ShellStatus.CONTINUE;
		}

		Path stackTop = ((Stack<Path>) env.getSharedData(STACK_KEY)).pop();
		if (!stackTop.toFile().exists()) {
			env.writeln("The stored path was deleted/altered in the meantime. Current directory remains unchanged.");
			return ShellStatus.CONTINUE;
		}
		
		env.setCurrentDirectory(stackTop);
		env.writeln("Current directory changed.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return POPD_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command takes no arguments. It pops the top path from the same stack that ");
		description.add("pushd command pushed it onto, and sets it as the current directory.");
		return description;
	}

}
