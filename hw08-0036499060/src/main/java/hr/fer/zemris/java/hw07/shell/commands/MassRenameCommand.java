package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.commands.namebuilder.NameBuilder;
import hr.fer.zemris.java.hw07.shell.commands.namebuilder.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.commands.namebuilder.NameBuilderInfoImpl;
import hr.fer.zemris.java.hw07.shell.commands.namebuilder.NameBuilderParser;
import hr.fer.zemris.java.hw07.shell.commands.namebuilder.NameBuilderParserException;
import hr.fer.zemris.java.hw07.shell.parser.ParserMode;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * Command takes two directories, a subcommand, a mask, and, in some cases, more
 * arguments. It checks which files stored directly in the first directory match
 * the given mask, and applies the action on them, or just displays them in the
 * shell console, depending on the subcommand.
 * 
 * @author MarinoK
 */
public class MassRenameCommand implements ShellCommand {

	/** Name for the subcommand filter. */
	private static final String FILTER = "filter";
	/** Name for the subcommand groups. */
	private static final String GROUPS = "groups";
	/** Name for the subcommand show. */
	private static final String SHOW = "show";
	/** Name for the subcommand execute. */
	private static final String EXECUTE = "execute";

	/** Source directory. */
	private static File source = null;
	/** Destination directory. */
	private static File dest = null;
	/** Pattern for filtering files. */
	private static Pattern pattern = null;
	/** List of arguments given by the user. */
	private static List<String> argumentList = null;
	/** Environment used to communicate with the user. */
	private static Environment env = null;

	/** Command name for the mass rename command. */
	private static String MASS_RENAME_COMMAND_NAME = "massrename";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		MassRenameCommand.env = env;
		ShellParser parser = new ShellParser(ParserMode.MASS_MODE);
		argumentList = parser.prepareArguments(arguments);

		if (argumentList == null || argumentList.size() > 5 || argumentList.size() < 4) {
			env.writeln("Command massrename must recieve four or five arguments. Type help for more.");
			return ShellStatus.CONTINUE;
		}

		String sourceString = argumentList.get(0);
		String destString = argumentList.get(1);

		Path sourcePath = Paths.get(sourceString);
		Path destPath = Paths.get(destString);

		sourcePath = env.getCurrentDirectory().resolve(sourcePath);
		destPath = env.getCurrentDirectory().resolve(destPath);

		source = sourcePath.toFile();
		dest = destPath.toFile();

		if (!source.isDirectory() || !dest.isDirectory()) {
			env.writeln("Given paths must be directories.");
			return ShellStatus.CONTINUE;
		}

		try {
			pattern = Pattern.compile(argumentList.get(3), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		} catch (PatternSyntaxException invalidPattern) {
			env.writeln("Invalid pattern given. Try again.");
			return ShellStatus.CONTINUE;
		}

		switch (argumentList.get(2).toLowerCase().trim()) {
		case FILTER:
			if (argumentList.size() != 4) {
				env.writeln("Subcommand filter must recieve one additional argument. Type help for more.");
				return ShellStatus.CONTINUE;
			}
			filter(true);
			break;
		case GROUPS:
			if (argumentList.size() != 4) {
				env.writeln("Subcommand groups must recieve one additional argument. Type help for more.");
				return ShellStatus.CONTINUE;
			}
			groups();
			break;
		case SHOW:
			if (argumentList.size() != 5) {
				env.writeln("Subcommand show must recieve two additional arguments. Type help for more.");
				return ShellStatus.CONTINUE;
			}
			show(true);
			break;
		case EXECUTE:
			if (argumentList.size() != 5) {
				env.writeln("Subcommand execute must recieve two additional arguments. Type help for more.");
				return ShellStatus.CONTINUE;
			}
			try {
				execute();
			} catch (FileAlreadyExistsException exists) {
				env.writeln("One of the destination files already exists. This command does not overwrite files.");
				return ShellStatus.CONTINUE;
			} catch (IOException e) {
				env.writeln("Problem with renaming files.");
				return ShellStatus.CONTINUE;
			}
			break;
		default:
			env.write("Invalid subcommand. Type help for more.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Auxiliary method used for filtering files, and printing them, if the print
	 * boolean is true.
	 * 
	 * @param print
	 *            boolean used for filtering data without printing
	 * @return list of non-directory children
	 */
	private List<Path> filter(boolean print) {
		File[] children = source.listFiles();
		List<Path> paths = new ArrayList<>();
		for (File child : children) {
			if (!child.isDirectory()) {
				paths.add(child.toPath());
			}
		}
		if (!print) return paths;
		boolean found = false;
		for (Path path : paths) {
			String fileName = path.toFile().getName();
			if (fileName.matches(pattern.toString())) {
				found = true;
				env.writeln(fileName);
			}
		}
		if (!found) {
			env.writeln("No such files found.");
		}
		return paths;
	}

	/**
	 * Auxiliary method used for printing out the filtered files and their names
	 * separated in given groups.
	 */
	private void groups() {
		List<Path> paths = filter(false);
		for (Path path : paths) {
			String fileName = path.toFile().getName();
			Matcher matcher = pattern.matcher(fileName);
			if (fileName.matches(pattern.toString())) {
				env.write(fileName);
				for (int i = 0; matcher.matches() && i <= matcher.groupCount(); i++) {
					env.write(" " + i + ": " + matcher.group(i));
				}
				env.writeln("");
			}
		}
	}

	/**
	 * Auxiliary method used for printing out how the filtered files would look, if
	 * we choose to execute the command.
	 * 
	 * @param print
	 *            boolean used for building names without printing
	 * @return map of files mapped to their new names
	 */
	private Map<Path, String> show(boolean print) {
		List<Path> paths = filter(false);
		NameBuilderParser parser = null;
		NameBuilder builder = null;
		try {
			parser = new NameBuilderParser(argumentList.get(4));
			builder = parser.getNameBuilder();
		} catch (NameBuilderParserException e) {
			env.writeln("Problem with the expression. Try again.");
		}

		Map<Path, String> newNames = new HashMap<>();
		for (Path path : paths) {
			Matcher matcher = pattern.matcher(path.toFile().getName());
			if (!matcher.matches()) continue;
			NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
			builder.execute(info);
			String newName = info.getStringBuilder().toString();
			if (print) {
				env.writeln(path.toFile().getName() + " => " + newName);
			} else {
				newNames.put(path, newName);
			}
		}
		return newNames;
	}

	/**
	 * Auxiliary method used for executing the command, moving/renaming actual
	 * files.
	 * 
	 * @throws IOException
	 *             if a problem arises while moving file
	 */
	private void execute() throws IOException {
		Map<Path, String> newNames = show(false);
		for (Path path : newNames.keySet()) {
			Path target = Paths.get(dest.toString() + "\\" + newNames.get(path).toString());
			Files.move(path, target);
		}
	}

	@Override
	public String getCommandName() {
		return MASS_RENAME_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command takes two directories, a subcommand, a mask, and, in some cases, more");
		description.add("arguments. It checks which files stored directly in the first directory match");
		description.add("the given mask, and applies the action on them, or just displays them in the");
		description.add("shell console, depending on the subcommand.");
		return description;
	}

}
