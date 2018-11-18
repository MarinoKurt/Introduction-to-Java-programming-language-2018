package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * Command RmTree deletes the given directory and all its content, provided that
 * the directory exists.
 * 
 * @author MarinoK
 */
public class RmTreeCommand implements ShellCommand {

	/** Command name for the rmtree command. */
	private static String RMTREE_COMMAND_NAME = "rmtree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ShellParser parser = new ShellParser();
		List<String> argumentsArray = parser.prepareArguments(arguments);
		if (argumentsArray == null || argumentsArray.size() != 1) {
			env.writeln("Command rmtree must recieve one argument. Type help for more.");
			return ShellStatus.CONTINUE;
		}
		Path givenPath = null;
		try {
			givenPath = Paths.get(argumentsArray.get(0));
			givenPath = env.getCurrentDirectory().resolve(givenPath);
		} catch (InvalidPathException e) {
			env.writeln("Invalid path given. Try again.");
			return ShellStatus.CONTINUE;
		}
		try {
			Files.walkFileTree(givenPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			env.writeln("Something went wrong with deleting the tree.");
			return ShellStatus.CONTINUE;
		}

		env.writeln("Tree deleted.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return RMTREE_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command RmTree deletes the given directory and all its content,");
		description.add("provided that the directory exists.");
		return description;
	}

}
