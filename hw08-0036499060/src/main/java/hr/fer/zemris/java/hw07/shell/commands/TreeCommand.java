package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * The tree command expects a single argument, directory name and prints a tree
 * whose root is the given directory.
 * 
 * @author MarinoK
 */
public class TreeCommand implements ShellCommand {

	/** Command name for the tree command. */
	private final String TREE_COMMAND_NAME = "tree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		
		ShellParser parser = new ShellParser();
		List<String> argumentsArray = parser.prepareArguments(arguments);

		if (argumentsArray == null || argumentsArray.size() > 1) {
			env.writeln("Command must recieve one argument. Type help for more.");
			return ShellStatus.CONTINUE;
		}
		
		Path rootPath = null;
		try {
			rootPath = Paths.get(argumentsArray.get(0));
		} catch (InvalidPathException e) {
			env.writeln("Invalid path.");
			return ShellStatus.CONTINUE;
		}
		rootPath = env.getCurrentDirectory().resolve(rootPath);
		File root = rootPath.toFile();
		tree(root, 0, env);
		return ShellStatus.CONTINUE;
	}

	/**
	 * Auxiliary method used to walk the file tree, and print out the files.
	 * 
	 * @param file
	 *            root of the file tree to print
	 * @param level
	 *            depth of the recursion
	 * @param env
	 *            environment to print out the file names to
	 */
	public static void tree(File file, int level, Environment env) {
		
		if(level==0) {
			if(file.isDirectory()) {
				File[] children = file.listFiles();
				if(children.length == 0) {
					env.writeln("Directory is empty.");
				}
			}
		}

		if (!file.exists()) {
			env.writeln("Non existing file.");
			return;
		}
		if (!file.isDirectory()) {
			env.writeln("File is not a directory.");
			return;
		}
		File[] children = file.listFiles();
		if (children == null) {
			env.writeln("File access denied.");
			return;
		}
		for (File child : children) {
			for (int i = level; i > 0; i--) {
				env.write("  ");
			}
			env.writeln(child.getName());

			if (!child.isFile()) {
				tree(child, level + 1, env);
			}
		}
	}

	@Override
	public String getCommandName() {
		return TREE_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("The tree command expects a single argument, directory name.");
		description.add("Command tree prints a tree whose root is the given directory.");
		return description;
	}

}
