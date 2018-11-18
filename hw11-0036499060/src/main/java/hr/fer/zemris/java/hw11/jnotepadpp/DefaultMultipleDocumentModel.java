package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * JTabbed pane that behaves like the multiple document model. It is capable of
 * holding documents, and remembering the current document, which the user can
 * edit. Objects of this class are Subjects for MultipleDocumentListeners
 * (Observer design pattern). Also creates the tab icon which follow is the
 * document modified and unsaved.
 * 
 * @author MarinoK
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** List of document this model contains. */
	private List<SingleDocumentModel> documents;

	/** List of observers. */
	private List<MultipleDocumentListener> observers;

	/** Document that is currently in the focus. */
	private SingleDocumentModel currentDocument;

	/** Listener of the single document model. */
	private MySingleDocumentListener singleListener;

	/** Icon for the unmodified documents. */
	private Icon UNMODIFIED_ICON;

	/** Icon for the modified documents. */
	private Icon MODIFIED_ICON;

	/**
	 * Default constructor for the DefaultMultipleDocumentModel.
	 */
	public DefaultMultipleDocumentModel() {
		this.documents = new ArrayList<>();
		this.observers = new ArrayList<>();
		this.singleListener = new MySingleDocumentListener();
		this.currentDocument = null;
		getIcons();
		addTabListener();
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel createdDocument = new DefaultSingleDocumentModel(null, "");
		SingleDocumentModel previous = currentDocument;
		if (currentDocument != null) {
			currentDocument.removeSingleDocumentListener(singleListener);
		}
		currentDocument = createdDocument;
		documents.add(currentDocument);
		currentDocument.addSingleDocumentListener(singleListener);
		JScrollPane sp = new JScrollPane(createdDocument.getTextComponent());
		addTab("Untitled", UNMODIFIED_ICON, sp, "Path not avaliable");
		setSelectedComponent(sp);
		fireCurrentDocument(previous);
		return createdDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);

		for (int i = 0; i < documents.size(); i++) {
			SingleDocumentModel sdm = documents.get(i);
			if (sdm.getFilePath() == null) continue;
			if (sdm.getFilePath().equals(path)) {
				setSelectedIndex(i);
				SingleDocumentModel previous = currentDocument;
				currentDocument = sdm;
				fireCurrentDocument(previous);
				return sdm;
			}
		}

		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch (Exception ex) {
			LocalizationProvider provider = LocalizationProvider.getInstance();
			JOptionPane.showMessageDialog(this, provider.getString("no_file"), provider.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String text = new String(bytes, StandardCharsets.UTF_8);
		SingleDocumentModel previous = currentDocument;
		this.currentDocument = new DefaultSingleDocumentModel(path, text);
		documents.add(currentDocument);
		addTab(path.toFile().getName(), UNMODIFIED_ICON, new JScrollPane(currentDocument.getTextComponent()),
				path.toString());
		setSelectedIndex(this.getTabCount() - 1);
		fireCurrentDocument(previous);
		return getCurrentDocument();
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		LocalizationProvider provider = LocalizationProvider.getInstance();
		if (newPath == null) {
			SingleDocumentModel altered = saveAs(model);
			if (altered == null) return;
		} else {
			model.setFilePath(newPath);
		}
		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(model.getFilePath(), podatci);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, provider.getString("save_error_desc"), provider.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(this, provider.getString("save_suc"), provider.getString("info"),
				JOptionPane.INFORMATION_MESSAGE);
		model.setModified(false);
		setTitleAt(getSelectedIndex(), model.getFilePath().toFile().getName());
		return;
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if (model.isModified()) {
			int result = JOptionPane.showConfirmDialog(this,
					LocalizationProvider.getInstance().getString("save_changes"));
			if (result == JOptionPane.YES_OPTION) {
				saveDocument(model, model.getFilePath());
			} else if (result == JOptionPane.NO_OPTION) {
			
			} else {
				throw new RuntimeException("Abort closing.");
			}
		}
		int index = this.getSelectedIndex();
		if (index >= 0) {
			this.remove(index);
			documents.remove(model);
			if (getSelectedIndex() >= 0) {
				SingleDocumentModel previous = currentDocument;
				currentDocument = documents.get(getSelectedIndex());
				fireCurrentDocument(previous);
			}
		} else {
			LocalizationProvider provider = LocalizationProvider.getInstance();
			JOptionPane.showMessageDialog(this, provider.getString("nothing_opened"), provider.getString("error"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		observers.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		observers.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index >= documents.size()) return null;
		return documents.get(index);
	}

	/**
	 * Specific implementation of the singleDocumentListener used in this
	 * application. Changes the tab icons, and keeps track of the document file
	 * path.
	 */
	private class MySingleDocumentListener implements SingleDocumentListener {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			if (model.isModified()) {
				setIconAt(getSelectedIndex(), MODIFIED_ICON);
			} else {
				setIconAt(getSelectedIndex(), UNMODIFIED_ICON);
			}
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			fireCurrentDocument(null);
		}

	}

	/**
	 * Auxiliary method used to alert all the listeners that the current document
	 * has changed.
	 * 
	 * @param previous
	 *            document
	 */
	private void fireCurrentDocument(SingleDocumentModel previous) {
		List<MultipleDocumentListener> observersCopy = new ArrayList<>(observers);
		for (MultipleDocumentListener dl : observersCopy) {
			dl.currentDocumentChanged(previous, currentDocument);
		}
	}

	/**
	 * Auxiliary method used to show the SaveAs dialog to the user, and handle the
	 * communication. If the communication was fruitful, returns the given document
	 * with altered path. Otherwise, returns null.
	 * 
	 * @param model
	 *            whose path should be determined
	 * @return document with altered path, or null if the user made a mistake
	 */
	private SingleDocumentModel saveAs(SingleDocumentModel model) {
		LocalizationProvider provider = LocalizationProvider.getInstance();
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(provider.getString("saveas_title"));
		if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(this, provider.getString("nothing_saved"), provider.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return null;
		} else {
			Path chosenPath = jfc.getSelectedFile().toPath();
			for (SingleDocumentModel doc : documents) {
				if (doc.getFilePath() == null) continue;
				if (doc.getFilePath().equals(chosenPath)) {
					JOptionPane.showMessageDialog(this, provider.getString("overwrite_impos"),
							provider.getString("error"), JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
			model.setFilePath(chosenPath);
			setToolTipTextAt(getSelectedIndex(), chosenPath.toString());
			return model;
		}

	}

	/**
	 * Auxiliary method used to add a listener which will keep currentDocument, and
	 * its listeners, updated.
	 */
	private void addTabListener() {
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel prev = currentDocument;
				int index = getSelectedIndex();
				if (index != -1) {
					if (currentDocument != null) {
						currentDocument.removeSingleDocumentListener(singleListener);
					}
					currentDocument = documents.get(index);
					currentDocument.addSingleDocumentListener(singleListener);
				} else {
					currentDocument = null;
				}
				fireCurrentDocument(prev);
			}
		});
	}

	/**
	 * Auxiliary method used to fetch needed icons.
	 */
	private void getIcons() {
		this.UNMODIFIED_ICON = getFromResource("icons/greendisk.png");
		this.MODIFIED_ICON = getFromResource("icons/reddisk.png");
	}

	/**
	 * Fetches the icon from resource.
	 * 
	 * @param path
	 *            of the icon
	 * @return icon
	 */
	private Icon getFromResource(String path) {
		byte[] bytes = null;
		try (InputStream is = this.getClass().getResourceAsStream(path)) {
			bytes = is.readAllBytes();
		} catch (IOException e) {
			System.err.println("problem with reading bytes from icon");
		}
		return new ImageIcon(bytes);
	}
}
