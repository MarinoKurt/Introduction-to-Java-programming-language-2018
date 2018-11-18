package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Multiple document container that provides all the needed methods for working
 * with a number of documents.
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates a blank, unnamed document.
	 * 
	 * @return new, blank document as SingleDocumentModel
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Fetches the document in focus.
	 * 
	 * @return document that is being edited, or just in focus
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads the document from the given path.
	 * 
	 * @param path
	 *            of the desired document. Must not be null
	 * @return document at the given path
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves the given document to the given path.
	 * 
	 * @param model
	 *            document to be saved
	 * @param newPath
	 *            path to save the document to. If null, the model is asked for its
	 *            file path. If its path is also null, a new path is required, and
	 *            updated as the models file path afterwards
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes the given document, regardless of its state.
	 * 
	 * @param model
	 *            document to be closed
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds a multiple document listener to the list of observers.
	 * 
	 * @param l
	 *            listener to be added
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes a multiple document listener from the list of observers.
	 * 
	 * @param l
	 *            listener to be added
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * @return number of currently contained documents
	 */
	int getNumberOfDocuments();

	/**
	 * Fetches the document at the given index.
	 * 
	 * @param index
	 *            of the required document
	 * @return the document at the given index
	 */
	SingleDocumentModel getDocument(int index);
}
