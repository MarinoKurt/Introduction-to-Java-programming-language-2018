package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Model of a single document. Subject in the Observer design pattern. Has all
 * the needed method for working with a single document.
 */
public interface SingleDocumentModel {

	/**
	 * Fetches the text component the text is contained in.
	 * 
	 * @return text component where the text is
	 */
	JTextArea getTextComponent();

	/**
	 * Fetches the file path of the document.
	 * 
	 * @return file path of this document, can be null
	 */
	Path getFilePath();

	/**
	 * File path setter.
	 * 
	 * @param path to be set, can not be null
	 */
	void setFilePath(Path path);

	/**
	 * @return whether the document is modified, but not saved
	 */
	boolean isModified();

	/**
	 * Sets the modified flag.
	 * 
	 * @param modified boolean to be set
	 */
	void setModified(boolean modified);

	/**
	 * Adds the given listener to the list of observers.
	 * 
	 * @param l listener to be added
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes the given listener from the list of observers.
	 * 
	 * @param l listener to be removed
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
