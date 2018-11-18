package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.crypto.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * Hexdump command expects a single argument: file name. It produces a
 * hex-output from the given file. Given file should not be a directory.
 * 
 * @author MarinoK
 */
public class HexDumpCommand implements ShellCommand {

	/** Column width for the left column. */
	private static final int COLUMN_WIDTH = 8;

	/** Minimum char value allowed in the right column.*/
	private static final byte MIN_CHAR_VALUE = 32;

	/** Maximum char value allowed in the right column.*/
	private static final byte MAX_CHAR_VALUE = 127;

	/** Command name for the hexdump command. */
	private final String HEXDUMP_COMMAND_NAME = "hexdump";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ShellParser parser = new ShellParser();
		List<String> argumentsList = parser.prepareArguments(arguments);

		if (argumentsList == null || argumentsList.size() != 1) {
			env.writeln("Command hexdump must recieve one argument. Type help for more.");
			return ShellStatus.CONTINUE;
		}

		Path filePath = Paths.get(argumentsList.get(0));
		filePath = env.getCurrentDirectory().resolve(filePath);
		File file = filePath.toFile();
		
		if (!file.exists()) {
			env.writeln("Given file does not exist.");
			return ShellStatus.CONTINUE;
		}
		if (file.isDirectory()) {
			env.writeln("Given file must not be a directory.");
			return ShellStatus.CONTINUE;
		}

		hexPrintFile(file, env);

		return ShellStatus.CONTINUE;
	}

	/**
	 * Auxiliary method used to print out the hex dump of the file.
	 * 
	 * @param file
	 *            to be printed
	 * @param env
	 *            to be printed to
	 */
	private void hexPrintFile(File file, Environment env) {
		try (InputStream ios = new FileInputStream(file)) {
			byte[] buffer = new byte[16];

			StringBuilder line = new StringBuilder();
			for (int i = 0; ios.read(buffer) != -1; i += 16) {

				String counter = Integer.toHexString(i);
				for (int x = 0; x < COLUMN_WIDTH - counter.length(); x++) {
					line.append("0");
				}

				line.append(counter);
				line.append(": ");

				for (int j = 0; j < buffer.length; j++) {
					byte[] lonely = { buffer[j] };
					String hexa = Util.bytetohex(lonely);
					line.append(hexa.toUpperCase());
					line.append(" ");

					if (j == 7) line.append("|");
				}
				line.append("|");

				for (int a = 0; a < buffer.length; a++) {
					if (buffer[a] < MIN_CHAR_VALUE || buffer[a] > MAX_CHAR_VALUE) {
						line.append(".");
					} else {
						line.append((char) (buffer[a]));
					}
				}

				env.writeln(line.toString());
				line = new StringBuilder();
			}

		} catch (IOException e) {
			env.writeln("There was a problem in writing the hex dump.");
		}
	}

	@Override
	public String getCommandName() {
		return HEXDUMP_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Hexdump command expects a single argument: file name.");
		description.add("It produces a hex-output from the given file.");
		description.add("Given file should not be a directory.");
		return description;
	}

}
