package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * Command cat takes one or two arguments. The first argument is path to some
 * file and is mandatory. The second argument is charset name that should be
 * used to interpret chars from bytes. This command opens given file and writes
 * its content to console.
 * 
 * @author MarinoK
 */
public class CatCommand implements ShellCommand {

	/** Command name for the cat command. */
	private static final String CAT_COMMAND_NAME = "cat";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Charset charset = null;
		ShellParser parser = new ShellParser();
		List<String> argumentList = parser.prepareArguments(arguments);
		Path file = null;

		if (argumentList == null || argumentList.size() > 2 || argumentList.size() < 1) {
			env.writeln("Command must recieve one or two arguments. Type help for more.");
			return ShellStatus.CONTINUE;
		}

		try {
			file = Paths.get(argumentList.get(0));
		} catch (InvalidPathException e) {
			env.writeln("Invalid path");
		}

		if (argumentList.size() == 1) {
			charset = Charset.defaultCharset();

		} else {
			try {
				charset = Charset.forName(argumentList.get(1));
			} catch (UnsupportedCharsetException e) {
				env.writeln("Charset is unsupported. Try again.");
				return ShellStatus.CONTINUE;
			} catch (IllegalCharsetNameException e) {
				env.writeln("Charset does not exist. Try again.");
				return ShellStatus.CONTINUE;
			}
		}

		try (BufferedReader in = Files.newBufferedReader(file, charset)) {
			String line;
			do {
				line = in.readLine();
				if (line == null) break;
				env.writeln(line);
			} while (true);
		} catch (IOException e) {
			env.writeln("Invalid path.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return CAT_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command cat takes one or two arguments.");
		description.add("The first argument is path to some file and is mandatory.");
		description.add("The second argument is charset name that should be used to interpret chars from bytes. ");
		description.add("This command opens given file and writes its content to console.");
		return description;
	}

}
