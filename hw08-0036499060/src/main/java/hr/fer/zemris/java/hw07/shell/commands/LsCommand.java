package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * Command ls takes a single argument, directory, and writes its listing. Output
 * is formatted in four columns. First column indicates if current object is
 * directory (d), readable (r), writable (w) and executable (x). Opposed to
 * these letters is the character '-', marking that the file/directory is not of
 * that property. Second column contains object size in bytes that is right
 * aligned and occupies 10 characters. Follows file creation date/time and
 * finally file name.
 * 
 * @author MarinoK
 */
public class LsCommand implements ShellCommand {

	/** Command name for the ls command. */
	private static final String LS_COMMAND_NAME = "ls";

	/** Width of the row containing file size. */
	private static final int ROW_WIDTH = 10;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		ShellParser parser = new ShellParser();
		List<String> argumentList = parser.prepareArguments(arguments);
		
		if(argumentList==null || argumentList.size()!=1) { 
			env.writeln("Command ls must recieve one argument. Type help for more.");
			return ShellStatus.CONTINUE;
		}
		
		Path rootPath = null;
		try {
			rootPath = Paths.get(argumentList.get(0));
		} catch (InvalidPathException e) {
			env.writeln("Invalid path.");
			return ShellStatus.CONTINUE;
		}
		rootPath = env.getCurrentDirectory().resolve(rootPath);
		File root = rootPath.toFile();
		
		if (!root.exists()) {
			env.writeln("Non existing file.");
			return ShellStatus.CONTINUE;
		}

		if (!root.isDirectory()) {
			env.writeln("Given file is not a directory.");
			return ShellStatus.CONTINUE;
		}

		File[] children = root.listFiles();

		if (children == null) {
			System.out.println("File access denied.");
			return ShellStatus.CONTINUE;
		}

		for (File child : children) {
			env.writeln(fileToString(child));
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Auxiliary method used to get the file attributes in a particular form, as a
	 * string.
	 * 
	 * @param file
	 *            to be read
	 * @return string of particular file attributes
	 */
	private String fileToString(File file) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = file.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = null;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

		StringBuilder oneLine = new StringBuilder();
		if (file.isDirectory()) {
			oneLine.append("d");
		} else {
			oneLine.append("-");
		}
		if (file.canRead()) {
			oneLine.append("r");
		} else {
			oneLine.append("-");
		}
		if (file.canWrite()) {
			oneLine.append("w");
		} else {
			oneLine.append("-");
		}
		if (file.canExecute()) {
			oneLine.append("x");
		} else {
			oneLine.append("-");
		}
		oneLine.append(" ");

		String fileSize = String.valueOf(file.length());
		for (int i = 0; i < ROW_WIDTH - fileSize.length(); i++) {
			oneLine.append(" ");
		}
		oneLine.append(fileSize);
		oneLine.append(" ");

		oneLine.append(formattedDateTime);
		oneLine.append(" ");

		oneLine.append(file.getName());

		return oneLine.toString();
	}

	@Override
	public String getCommandName() {
		return LS_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command ls takes a single argument, directory, and writes its listing.");
		description.add("Output is formatted in four columns.");
		description.add(
				"First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).");
		description.add(
				"Opposed to these letters is the character '-', marking that the file/directory is not of that property. ");
		description.add(
				"Second column contains object size in bytes. Follows file creation date/time and finally file name.");
		return description;
	}

}
