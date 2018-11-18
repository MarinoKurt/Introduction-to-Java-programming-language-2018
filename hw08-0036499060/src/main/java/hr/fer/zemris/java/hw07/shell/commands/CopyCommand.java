package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ShellParser;

/**
 * The copy command expects two arguments: source file name and destination file
 * name. If destination file exists, user will be asked is it OK to overwrite
 * it. If the destination is a directory, the source file will be copied into
 * that directory.
 * 
 * @author MarinoK
 */
public class CopyCommand implements ShellCommand {

	/** Command name for the copy command. */
	private final String COPY_COMMAND_NAME = "copy";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		ShellParser parser = new ShellParser();
		List<String> argumentList = parser.prepareArguments(arguments);

		if (argumentList == null || argumentList.size() > 2 || argumentList.size() < 1) {
			env.writeln("Command must recieve one or two arguments. Type help for more.");
			return ShellStatus.CONTINUE;
		}
		
		String sourceString = argumentList.get(0);
		String destString = argumentList.get(1);

		Path sourcePath = Paths.get(sourceString);
		Path destPath = Paths.get(destString);

		sourcePath = env.getCurrentDirectory().resolve(sourcePath);
		destPath = env.getCurrentDirectory().resolve(destPath);

		File source = sourcePath.toFile();
		File dest = destPath.toFile();
		
		if (source.isDirectory()) {
			env.writeln("Cannot copy a directory.");
			return ShellStatus.CONTINUE;
		}

		if (!source.exists()) {
			env.writeln("Source does not exist.");
			return ShellStatus.CONTINUE;
		}

		if (dest.isDirectory()) {
			String fileName = sourceString.substring(sourceString.lastIndexOf('\\'));
			destString = destString + fileName;
			dest = Paths.get(destString).toFile();
		}

		if (dest.exists()) {
			String response;
			do {
				env.writeln("Destination file exists. Do you want to override? y/n");
				env.write(env.getPromptSymbol().toString());
				response = env.readLine();
			} while (!(response.toLowerCase().equals("y") || response.toLowerCase().equals("n")));
			if (response.equals("n")) {
				env.writeln("OK. Awaiting next command.");
				return ShellStatus.CONTINUE;
			}
		}

		try {
			copy(source, dest);
		} catch (IOException channelProblem) {
			env.writeln("Problem reading file.");
			return ShellStatus.CONTINUE;
		}

		env.writeln("File copied.");
		return ShellStatus.CONTINUE;
	}

	/**
	 * Copies the file from the source to the destination.
	 * 
	 * @param source
	 *            file to be copied
	 * @param dest
	 *            file to be created
	 * @throws IOException
	 *             if a problem with reading or writing arises
	 */
	@SuppressWarnings("resource") // are closed.
	private static void copy(File source, File dest) throws IOException {

		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}

	@Override
	public String getCommandName() {
		return COPY_COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("The copy command expects two arguments: source file name and destination file name.");
		description.add("If destination file exists, user will be asked is it OK to overwrite it.");
		description.add("If the destination is a directory, the source file"
				+ " will be copied into that directory, under the former name.");
		return description;
	}
}
