package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Help command, if started with no arguments, will list names of all supported
 * commands. If the user provides a single argument, being the name of the
 * command, help command will print the name and the description of the selected
 * command.
 * 
 * @author MarinoK
 *
 */
public class HelpCommand implements ShellCommand {

	/** Name for the help command. */
	private static final String HELP_COMMAND_NAME = "help";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (arguments == null || arguments.trim().equals("")) {
			env.writeln("List of avaliable commands: ");
			Set<String> commandNames = env.commands().keySet();
			for (String commandName : commandNames) {
				env.writeln(commandName);
			}
		} else {
			if (arguments.split("\\s+").length > 1) {
				env.writeln("Help expects one argument if you want it to write out the"
						+ " command description. For a list of commands, type just help.");
				return ShellStatus.CONTINUE;
			}
			arguments = arguments.trim();
			ShellCommand command = env.commands().get(arguments);
			if (command == null) {
				env.writeln("Command " + arguments + " does not exist. Try again.");
				return ShellStatus.CONTINUE;
			}
			env.writeln("Help for: " + command.getCommandName());
			List<String> description = command.getCommandDescription();
			for (String descLine : description) {
				env.writeln(descLine);
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return HELP_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Help command, if started with no arguments, will list names of all supported commands.");
		description.add("If the user provides a single argument, being the name of the command, ");
		description.add("help command will print the name and the description of the selected command.");
		return description;
	}

}
