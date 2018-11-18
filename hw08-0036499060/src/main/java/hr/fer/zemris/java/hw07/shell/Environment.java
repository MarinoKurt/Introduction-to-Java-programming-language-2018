package hr.fer.zemris.java.hw07.shell;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Map;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;

/**
 * Interface used to communicate between the user and the shell.
 * 
 * @author MarinoK
 */
public interface Environment {

	/**
	 * Getter for the current directory.
	 * 
	 * @return current directory, as a path
	 */
	Path getCurrentDirectory();

	/**
	 * Setter for the current directory.
	 * 
	 * @param path
	 *            to be set as the current directory
	 * @throws InvalidPathException
	 *             in case the given path does not exist
	 */
	void setCurrentDirectory(Path path) throws InvalidPathException;

	/**
	 * Getter for the shared data.
	 * 
	 * @param key
	 *            used for identifying the data
	 * @return shared data under the given key
	 */
	Object getSharedData(String key);

	/**
	 * Setter for the shared data.
	 * 
	 * @param key
	 *            to identify the data
	 * @param value
	 *            to store under the given key
	 */
	void setSharedData(String key, Object value);

	/**
	 * Reads from the arranged input, returns read result in form of a string.
	 * 
	 * @return read string
	 * @throws ShellIOException
	 *             if the text could not be read
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes the given text without terminating the line.
	 * 
	 * @param text
	 *            to be written
	 * @throws ShellIOException
	 *             if the text could not be written
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes the given text and terminates the line.
	 * 
	 * @param text
	 *            to be written
	 * @throws ShellIOException
	 *             if the text could not be written
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns a map that binds this environment's command names to their
	 * implementations.
	 * 
	 * @return map of string-command pairs
	 */
	Map<String, ShellCommand> commands();

	/**
	 * Getter for the MultilineSymbol.
	 * 
	 * @return MultilineSymbol
	 */
	Character getMultilineSymbol();

	/**
	 * Setter for the MultilineSymbol.
	 * 
	 * @param symbol
	 *            to be added as MultilineSymbol.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Getter for the PromptSymbol.
	 * 
	 * @return PromptSymbol
	 */
	Character getPromptSymbol();

	/**
	 * Setter for the PromptSymbol.
	 * 
	 * @param symbol
	 *            to be added as PromptSymbol.
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Getter for the MorelinesSymbol.
	 * 
	 * @return MorelinesSymbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Setter for the MorelinesSymbol.
	 * 
	 * @param symbol
	 *            to be added as MorelinesSymbol.
	 */
	void setMorelinesSymbol(Character symbol);

}
