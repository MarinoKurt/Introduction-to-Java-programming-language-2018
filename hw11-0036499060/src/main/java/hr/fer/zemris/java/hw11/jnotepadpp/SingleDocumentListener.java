package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Contract between the listener and the Subject. All the single document
 * listeners must implement this interface.
 */
public interface SingleDocumentListener {

	/**
	 * Called when the modify status of the document is updated.
	 * 
	 * @param model
	 *            which modify status was updated
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Called when the file path of the document is updated.
	 * 
	 * @param model
	 *            which file path was updated
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
