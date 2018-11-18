package hr.fer.zemris.java.hw07.shell;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.*;

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

	/** Memorizes the current directory. */
	private Path currentDir;

	/** Used for storing data under a specific key. */
	private Map<String, Object> sharedData;

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
		this.sharedData = new TreeMap<>();
		this.addCommands();
		this.sc = new Scanner(System.in);
		this.currentDir = Paths.get(".").toAbsolutePath().normalize();
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
		commandList.add(new PwdCommand());
		commandList.add(new CdCommand());
		commandList.add(new PushdCommand());
		commandList.add(new PopdCommand());
		commandList.add(new ListdCommand());
		commandList.add(new DropdCommand());
		commandList.add(new RmTreeCommand());
		commandList.add(new CpTreeCommand());
		commandList.add(new MassRenameCommand());

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

	@Override
	public Path getCurrentDirectory() {
		return currentDir;
	}

	@Override
	public void setCurrentDirectory(Path path) throws InvalidPathException {
		if (path == null) {
			throw new InvalidPathException(null, "Given path does not exist.");
		} else if (!path.toFile().exists()) {
			throw new InvalidPathException(path.toString(), "Given path does not exist.");
		}
		this.currentDir = path;
	}

	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		sharedData.put(key, value);
	}

}
