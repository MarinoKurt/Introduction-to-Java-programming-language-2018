package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw07.shell.parser.ParserMode;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * Implementation of a shell. Communicates with the user over the console.
 * Supports all commands given from input 'help'.
 * 
 * @author MarinoK
 */
public class MyShell {

	/**
	 * Main method is run when the program is run.
	 * 
	 * @param args
	 *            usage not expected
	 */
	public static void main(String[] args) {

		Environment env = new ShellEnvironment();
		ShellStatus status = ShellStatus.CONTINUE;
		ShellCommand command = null;
		String commandName = null;
		String arguments = null;
		StringBuilder currentInput = new StringBuilder();
		ShellParser parser = new ShellParser(ParserMode.LINE_MODE);

		env.writeln("Welcome to MyShell v 1.0");
		while (!status.equals(ShellStatus.TERMINATE)) {
			env.write(env.getPromptSymbol() + " ");

			do {
				String processedInput = parser.getCleanLine(env.readLine(), env.getMorelinesSymbol());
				currentInput.append(processedInput);
				if (!parser.isMultiline()) {
					break;
				}
				env.write(env.getMultilineSymbol() + " ");
			} while (true);

			String[] splitIntoTwo = currentInput.toString().split("\\s+", 2);
			commandName = splitIntoTwo[0];
			if (splitIntoTwo.length > 1) {
				arguments = splitIntoTwo[1];
			}

			command = env.commands().get(commandName);
			currentInput = new StringBuilder();
			if (command == null) {
				arguments = null;
				env.writeln("Invalid command. Try again.");
				continue;
			}
			try {
				status = command.executeCommand(env, arguments);
			} catch (ShellIOException e) {
				env.writeln("Communication with the user is impossible. Exiting. ");
				status = ShellStatus.TERMINATE;
			}
			arguments = null;

		}
		env.writeln("Goodbye.");
	}
}