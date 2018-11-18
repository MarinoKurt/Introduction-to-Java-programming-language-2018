package hr.fer.zemris.java.hw07.shell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexDumpCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkDirCommand;
import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeCommand;

/**
 * Implementation of an environment used by the shell to communicate with the
 * user.
 * 
 * @author MarinoK
 */
public class ShellEnvironment implements Environment {

	/** Character used for emphasizing the first line in a command. */
	private Character promptSymbol;

	/**
	 * Character expected from user, used for requesting to write multi line
	 * commands.
	 */
	private Character morelinesSymbol;

	/** Character used for emphasizing multi line commands. */
	private Character multiLineSymbol;

	/** Map that binds the command names to command implementations. */
	private SortedMap<String, ShellCommand> commandMap;

	/** Scanner used to read user input. */
	private Scanner sc;

	/**
	 * Default constructor for the shell environment.
	 */
	public ShellEnvironment() {
		this.init();
	}

	/**
	 * Auxiliary method used for initializing the environment and its default
	 * symbols and commands.
	 */
	private void init() {
		this.promptSymbol = '>';
		this.morelinesSymbol = '\\';
		this.multiLineSymbol = '|';
		this.commandMap = new TreeMap<String, ShellCommand>();
		this.addCommands();
		this.sc = new Scanner(System.in);
	}

	/**
	 * Auxiliary method used to prepare all the available commands.
	 */
	private void addCommands() {
		List<ShellCommand> commandList = new ArrayList<>();
		commandList.add(new CharsetsCommand());
		commandList.add(new CatCommand());
		commandList.add(new LsCommand());
		commandList.add(new TreeCommand());
		commandList.add(new CopyCommand());
		commandList.add(new MkDirCommand());
		commandList.add(new HexDumpCommand());
		commandList.add(new HelpCommand());
		commandList.add(new SymbolCommand());
		commandList.add(new ExitCommand());

		for (ShellCommand cmd : commandList) {
			commandMap.put(cmd.getCommandName(), cmd);
		}
	}

	@Override
	public String readLine() throws ShellIOException {
		try {
			return sc.nextLine();
		} catch (Exception e) {
			throw new ShellIOException("Fault in reading line.");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		} catch (Exception e) {
			throw new ShellIOException("Fault in writing.");
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch (Exception e) {
			throw new ShellIOException("Fault in writing line.");
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commandMap);
	}

	@Override
	public Character getMultilineSymbol() {
		return this.multiLineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		if (symbol == null) throw new NullPointerException("MultilineSymbol must not be null.");
		this.multiLineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return this.promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		if (symbol == null) throw new NullPointerException("PromptSymbol must not be null.");
		this.promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		if (symbol == null) throw new NullPointerException("MoreLinesSymbol must not be null.");
		this.morelinesSymbol = symbol;
	}

}
