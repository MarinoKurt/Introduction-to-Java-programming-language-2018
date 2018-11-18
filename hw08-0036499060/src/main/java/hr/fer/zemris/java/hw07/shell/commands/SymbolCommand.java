package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * Command used to manage symbols used for formatting user input. If user gives
 * only one argument, name of the symbol role, current character for that symbol
 * role will be output. If user gives two arguments after the command, second
 * argument must be the new character for the given symbol role.
 * 
 * @author MarinoK
 */
public class SymbolCommand implements ShellCommand {

	/** Command name for the symbol command. */
	private static final String SYMBOL_COMMAND_NAME = "symbol";

	/** Constant for prompt symbol, used to recognize user input. */
	private static final String PROMPT = "PROMPT";

	/** Constant for morelines symbol, used to recognize user input. */
	private static final String MORELINES = "MORELINES";

	/** Constant for multiline symbol, used to recognize user input. */
	private static final String MULTILINE = "MULTILINE";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		ShellParser parser = new ShellParser();
		List<String> argumentsList = parser.prepareArguments(arguments);
		if (argumentsList == null || argumentsList.size()>2 || argumentsList.size()<1) {
			env.writeln("Command symbol must recieve one or two arguments. Type help for more.");
			return ShellStatus.CONTINUE;
		}
		String selection;
		char selectionValue;

		if (argumentsList.size() == 1) {
			switch (argumentsList.get(0)) {
			case PROMPT:
				selection = PROMPT;
				selectionValue = env.getPromptSymbol();
				break;
			case MORELINES:
				selection = MORELINES;
				selectionValue = env.getMorelinesSymbol();
				break;
			case MULTILINE:
				selection = MULTILINE;
				selectionValue = env.getMultilineSymbol();
				break;
			default:
				env.writeln("Unknown symbol required. Try again.");
				return ShellStatus.CONTINUE;
			}
			env.writeln("Symbol for " + selection + " is '" + selectionValue + "'");
		} else if (argumentsList.size() == 2) {
			char formerSymbol;

			if (argumentsList.get(1).length() != 1) {
				env.writeln("Symbol must be one character.");
				return ShellStatus.CONTINUE;
			}
			char futureSymbol = argumentsList.get(1).charAt(0);

			switch (argumentsList.get(0)) {
			case PROMPT:
				selection = PROMPT;
				formerSymbol = env.getPromptSymbol();
				env.setPromptSymbol(futureSymbol);
				break;
			case MORELINES:
				selection = MORELINES;
				formerSymbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(futureSymbol);
				break;
			case MULTILINE:
				selection = MULTILINE;
				formerSymbol = env.getMultilineSymbol();
				env.setMultilineSymbol(futureSymbol);
				break;
			default:
				env.writeln("Unknown symbol required. Try again.");
				return ShellStatus.CONTINUE;
			}
			env.writeln("Symbol for " + selection + " changed from '" + formerSymbol + "' to '" + futureSymbol + "'");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return SYMBOL_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command used to manage symbols used for formatting user input.");
		description.add("If user gives only one argument, name of the symbol role,");
		description.add("current character for that symbol role will be output.");
		description.add("If user gives two arguments after the command, second argument");
		description.add("must be the new character for the given symbol role.");
		return description;
	}

}