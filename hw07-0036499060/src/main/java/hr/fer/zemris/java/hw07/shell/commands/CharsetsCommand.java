package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command charsets takes no arguments and lists names of supported charsets for
 * this shell. A single charset name is written per line.
 * 
 * @author MarinoK
 */
public class CharsetsCommand implements ShellCommand {

	/** Command name for the charsets command. */
	private static final String CHARSETS_COMMAND_NAME = "charsets";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments!=null) {
			env.writeln("Command charsets takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		Map<String, Charset> charset = Charset.availableCharsets();
		for (Map.Entry<String, Charset> character : charset.entrySet()) {
			env.writeln(character.toString());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return CHARSETS_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command charsets takes no arguments and lists names of supported charsets for this shell.");
		description.add("A single charset name is written per line.");
		return description;
	}

}
