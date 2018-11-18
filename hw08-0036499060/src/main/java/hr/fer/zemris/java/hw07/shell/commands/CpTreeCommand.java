package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
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
 * Command cptree copies the tree given as the first argument, to the directory
 * given as the second argument. Rules are: if the destination directory does
 * not exist, but its parent exists, the source will be copied under the name of
 * destination. If the destination directory exists, the source will be copied
 * into it.
 * 
 * @author MarinoK
 */
public class CpTreeCommand implements ShellCommand {
	/** Path of the given source. */
	private Path sourcePath;
	/** Path of the given destination. */
	private Path destPath;

	/** Command name for the cptree. */
	private static String CPTREE_COMMAND_NAME = "cptree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ShellParser parser = new ShellParser();
		List<String> argumentList = parser.prepareArguments(arguments);

		if (argumentList == null || argumentList.size() != 2) {
			env.writeln("Command cptree must recieve two arguments. Type help for more.");
			return ShellStatus.CONTINUE;
		}

		String sourceString = argumentList.get(0);
		String destString = argumentList.get(1);

		sourcePath = Paths.get(sourceString);
		destPath = Paths.get(destString);

		sourcePath = env.getCurrentDirectory().resolve(sourcePath);
		destPath = env.getCurrentDirectory().resolve(destPath);

		File source = sourcePath.toFile();
		File dest = destPath.toFile();

		if (!source.isDirectory()) {
			env.writeln("Given source is not a directory. For copying files, use copy command.");
			return ShellStatus.CONTINUE;
		}

		if (!source.exists()) {
			env.writeln("Source does not exist.");
			return ShellStatus.CONTINUE;
		}

		if (dest.exists()) {
			int index = sourcePath.toString().lastIndexOf("\\");
			if (index < 0) {
				env.writeln("Problem with creating destination directory.");
				return ShellStatus.CONTINUE;
			}
			Path toCreate = Paths.get(destString + sourcePath.toString().substring(index));
			if (!Files.exists(toCreate)) {
				try {
					Files.createDirectories(toCreate);
				} catch (IOException e) {
					env.writeln("Problem with creating destination directory.");
					return ShellStatus.CONTINUE;
				}
			}
		} else {
			String destParentString = destPath.toString().substring(0, destPath.toString().lastIndexOf('\\'));
			Path destParent = Paths.get(destParentString);
			if (!destParent.toFile().exists()) {
				env.writeln("At the very least," + " the destination's first parent must exist.");
				return ShellStatus.CONTINUE;
			}

			String newFolderName = destString.substring(destString.lastIndexOf('\\'));
			destString = destParentString + newFolderName;
			destPath = Paths.get(destString);
			try {
				Files.createDirectory(destPath);
			} catch (IOException e) {
				env.writeln("Problem with creating destination directory.");
				return ShellStatus.CONTINUE;
			}
			dest = destPath.toFile();
		}

		if (!dest.isDirectory()) {
			env.writeln("Destination must be a directory.");
			return ShellStatus.CONTINUE;
		}

		FileVisitor<Path> visitor = new MyFileVisitor();
		try {
			Files.walkFileTree(sourcePath, visitor);
		} catch (FileAlreadyExistsException exists) {
			env.writeln("Destination file exists. This command does not overwrite. Terminating command.");
		} catch (IOException e) {
			env.writeln("Problem with walking the file tree.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Custom file visitor used for copying the tree.
	 * 
	 * @author MarinoK
	 */
	private class MyFileVisitor extends SimpleFileVisitor<Path> {

		/** Remembers the current destination to copy into. */
		private Path currentDestination;

		/**
		 * Constructor for the file visitor.
		 */
		public MyFileVisitor() {
			this.currentDestination = destPath;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path currentSource, BasicFileAttributes arg1) throws IOException {
			currentDestination = currentDestination.resolve(currentSource.getFileName());
			if (!Files.exists(currentDestination)) {
				Files.createDirectory(currentDestination);
			}

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path currentSource, BasicFileAttributes arg1) throws IOException {
			Path target = currentDestination.resolve(currentSource.getFileName());
			Files.copy(currentSource, target);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path currentSource, IOException arg1) throws IOException {
			currentDestination = currentDestination.getParent();
			return FileVisitResult.CONTINUE;
		}
	}

	@Override
	public String getCommandName() {
		return CPTREE_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command cptree copies the tree given as the first argument, to the directory");
		description.add("given as the second argument. Rules are: if the destination directory does");
		description.add("not exist, but its parent exists, the source will be copied under the name of");
		description.add("destination. If the destination directory exists, the source will be copied");
		description.add("into it.");
		return description;
	}
}
