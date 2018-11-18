package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which takes no arguments. It prints out the current directory as an
 * absolute path.
 * 
 * @author MarinoK
 */
public class PwdCommand implements ShellCommand {

	/** Name of the pwd command. */
	private static final String PWD_COMMAND_NAME = "pwd";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments!=null) {
			if(!arguments.trim().equals("")) {
				env.writeln("Command pdw takes no arguments.");
				return ShellStatus.CONTINUE;
			}
		}
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return PWD_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Takes no arguments. Prints out the current directory as an absolute path.");
		return description;
	}

}
