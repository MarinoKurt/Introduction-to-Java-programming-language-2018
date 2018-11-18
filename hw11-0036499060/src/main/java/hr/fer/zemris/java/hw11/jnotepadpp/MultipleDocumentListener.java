package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Contract between the listener and the Subject. All multiple document
 * listeners must implement this interface.
 */
public interface MultipleDocumentListener {

	/**
	 * Called when the current document changes.
	 * 
	 * @param previousModel
	 *            reference to the previously current document
	 * @param currentModel
	 *            current document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Called when a document is added to the MultipleDocumentModel.
	 * 
	 * @param model
	 *            added document
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Called when a document is removed from the MultipleDocumentModel.
	 * 
	 * @param model
	 *            removed document
	 */
	void documentRemoved(SingleDocumentModel model);
}
