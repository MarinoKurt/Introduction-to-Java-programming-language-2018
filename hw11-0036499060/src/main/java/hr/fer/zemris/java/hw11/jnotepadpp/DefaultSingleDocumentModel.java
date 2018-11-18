package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implementation of the SingleDocumentModel which has its list of observers.
 * 
 * @author MarinoK
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/** True if modified and unsaved. */
	private boolean isModified;

	/** Path of the document. */
	private Path documentPath;

	/** List of observers. */
	private List<SingleDocumentListener> observers;

	/** Text component containing the content of the document. */
	private JTextArea textArea;

	/**
	 * Constructor for the DefaultSingleDocumentModel.
	 * 
	 * @param documentPath
	 *            of the document
	 * @param content
	 *            of the document
	 */
	public DefaultSingleDocumentModel(Path documentPath, String content) {
		this.isModified = false;
		this.documentPath = documentPath;
		this.observers = new ArrayList<>();
		this.textArea = new JTextArea(content);
		textArea.getDocument().addDocumentListener(new MyDocumentListener());
	}

	@Override
	public Path getFilePath() {
		return documentPath;
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public JTextArea getTextComponent() {
		return this.textArea;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		this.documentPath = path;
		pathFire();
	}

	@Override
	public void setModified(boolean modified) {
		if (this.isModified != modified) {
			this.isModified = modified;
			modifyFire();
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l);
		observers.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l);
		observers.remove(l);
	}

	/**
	 * Auxiliary method used to notify all the observers that a change happened in
	 * the modification flag.
	 */
	private void modifyFire() {
		List<SingleDocumentListener> observersCopy = new ArrayList<>(observers);
		for (SingleDocumentListener dl : observersCopy) {
			dl.documentModifyStatusUpdated(this);
		}
	}

	/**
	 * Auxiliary method used to notify all the observers that a change happened in
	 * the document path.
	 */
	private void pathFire() {
		List<SingleDocumentListener> observersCopy = new ArrayList<>(observers);
		for (SingleDocumentListener dl : observersCopy) {
			dl.documentFilePathUpdated(this);
		}
	}

	/**
	 * Specific implementation of the DocumentListener for this application. Treats
	 * all document changes alike.
	 */
	private class MyDocumentListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			fireModifications();
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			fireModifications();
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			fireModifications();
		}

		/**
		 * Auxiliary method used to set the modified flag and notify observers. If the
		 * flag is already set, does nothing.
		 */
		private void fireModifications() {
			if (!isModified) {
				isModified = true;
				modifyFire();
			}
		}
	}

}
