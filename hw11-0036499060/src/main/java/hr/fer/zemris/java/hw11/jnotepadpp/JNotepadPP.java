package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * JNotepadPP application. Can hold multiple documents in tabs. Offers some of
 * the regular options for editing documents, such as load, save, saveAs,
 * statistics.
 * 
 * @author MarinoK
 */
public class JNotepadPP extends JFrame {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Locale tag for Croatia. */
	private static final String CROATIA_LOCALE = "hr";

	/** Reference to the multiple document model. */
	private MultipleDocumentModel documents;

	/** Listener for the multiple document model. */
	private MultipleDocumentListener multiListener;

	/** Listener for the document caret. */
	private CaretListener caretListener;

	/** Reference to the status bar component: document length meter. */
	private JLabel lengthMeter;

	/** Reference to the status bar component: caret tracker. */
	private JLabel caretTracker;

	/** Reference to the status bar component: clock. */
	private JLabel clockLabel;

	/** List of actions that should be enabled only when the text is selected. */
	private List<Action> selectedTextActions;

	/** Text area of the current document. */
	private JTextArea textArea;

	/** Localization provider. */
	private FormLocalizationProvider localizationProvider = new FormLocalizationProvider(
			LocalizationProvider.getInstance(), this);

	/**
	 * Default constructor for the JNotepadPP.
	 */
	public JNotepadPP() {
		localizationProvider.addLocalizationListener(() -> caretListener.caretUpdate(null));
		this.multiListener = new MyMultipleDocumentListener();
		this.documents = new DefaultMultipleDocumentModel();
		this.documents.addMultipleDocumentListener(multiListener);
		this.caretListener = new MyCaretListener();
		this.selectedTextActions = new ArrayList<>();
		initGUI();
	}

	/**
	 * Main method is called when the program is run.
	 * 
	 * @param args
	 *            usage not expected
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

	// ACTIONS

	/**
	 * Action for the new document creation.
	 */
	private final Action newDocumentAction = new LocalizableAction("new", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			documents.createNewDocument();
		}
	};

	/**
	 * Action for loading the document.
	 */
	private final Action loadDocumentAction = new LocalizableAction("load", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(localizationProvider.getString("load_title"));
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this, localizationProvider.getString("no_file"),
						localizationProvider.getString("error"), JOptionPane.ERROR_MESSAGE);
				return;
			}

			documents.loadDocument(filePath);
		}
	};

	/**
	 * Action for saving the document.
	 */
	private Action saveDocumentAction = new LocalizableAction("save", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!checkCurrent()) return;
			documents.saveDocument(documents.getCurrentDocument(), documents.getCurrentDocument().getFilePath());
		}
	};

	/**
	 * Action for saving the document to a user given path.
	 */
	private Action saveAsDocumentAction = new LocalizableAction("saveas", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!checkCurrent()) return;
			documents.saveDocument(documents.getCurrentDocument(), null);
		}
	};

	/**
	 * Action for quitting the application.
	 */
	private Action quitAction = new LocalizableAction("quit", localizationProvider) {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			checkAndExit();
		}
	};

	/**
	 * Action for closing a document.
	 */
	private Action closeDocumentAction = new LocalizableAction("close", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sdm = documents.getCurrentDocument();
			if (sdm != null) {
				try {
					documents.closeDocument(sdm);
				} catch (RuntimeException abortClosing) {
					return;
				}
			}
		}
	};

	/**
	 * Action for showing the document statistics dialog.
	 */
	private Action statisticsAction = new LocalizableAction("stats", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!checkCurrent()) return;
			SingleDocumentModel current = documents.getCurrentDocument();
			int len = current.getTextComponent().getDocument().getLength();
			char[] characters = current.getTextComponent().getText().toCharArray();
			int nonBlank = 0;
			for (char c : characters) {
				if (!Character.isWhitespace(c)) {
					nonBlank++;
				}
			}
			int lines = current.getTextComponent().getLineCount();
			String stats = String.format(localizationProvider.getString("stats_desc"), len, nonBlank, lines);
			JOptionPane.showMessageDialog(JNotepadPP.this, stats, localizationProvider.getString("stats_title"),
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * Action for cutting text.
	 */
	private Action cutAction = new LocalizableAction("cut", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (documents.getCurrentDocument() != null) {
				documents.getCurrentDocument().getTextComponent().cut();
			}
		}
	};

	/**
	 * Action for copying text.
	 */
	private Action copyAction = new LocalizableAction("copy", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (documents.getCurrentDocument() != null) {
				documents.getCurrentDocument().getTextComponent().copy();
			}
		}
	};

	/**
	 * Action for pasting text.
	 */
	private Action pasteAction = new LocalizableAction("paste", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (documents.getCurrentDocument() != null) {
				documents.getCurrentDocument().getTextComponent().paste();
			}
		}
	};

	/**
	 * Action for sorting the lines in ascending order.
	 */
	private Action ascendingAction = new LocalizableAction("ascending", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				fetchAndSortLines(true);
			} catch (BadLocationException ignorable) {

			}
		}
	};

	/**
	 * Action for sorting the lines in descending order.
	 */
	private Action descendingAction = new LocalizableAction("descending", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				fetchAndSortLines(false);
			} catch (BadLocationException ignorable) {
			}

		}
	};

	/**
	 * Action for eliminating the identical lines in the document.
	 */
	private Action uniqueAction = new LocalizableAction("unique", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Document doc = textArea.getDocument();
			List<String> lines = new ArrayList<>();
			int offset;
			int length;
			try {
				offset = getSelectedLineStart();
				length = getSelectedLineEnd() - getSelectedLineStart();

				for (int lineNum = getFirstSelectedLine(); lineNum <= getLastSelectedLine(); lineNum++) {
					int lineStart = textArea.getLineStartOffset(lineNum);
					int lineEnd = textArea.getLineEndOffset(lineNum);
					String lineText = doc.getText(lineStart, lineEnd - lineStart);
					if (!lineText.endsWith("\n")) {
						lineText = lineText.concat("\n");
					}
					if (!lines.contains(lineText)) {
						lines.add(lineText);
					}
				}
				StringBuilder sb = new StringBuilder();
				for (String line : lines) {
					sb.append(line);
				}
				doc.remove(offset, length);
				doc.insertString(offset, sb.toString(), null);

			} catch (BadLocationException ignorable) {
			}
		}
	};

	/**
	 * Action for changing the selected text case to upper. If no text is selected,
	 * action is disabled.
	 */
	private Action uppercaseAction = new LocalizableAction("upper", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!getTextAndChangeCase(Case.UPPER)) {
				throw new RuntimeException();
			}
		}
	};

	/**
	 * Action for changing the selected text case to lower. If no text is selected,
	 * action is disabled.
	 */
	private Action lowercaseAction = new LocalizableAction("lower", localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!getTextAndChangeCase(Case.LOWER)) {
				throw new IllegalArgumentException();
			}
		}
	};

	/**
	 * Action for inverting the selected text case. If no text is selected, action
	 * is disabled.
	 */
	private Action invertCaseAction = new LocalizableAction("invert", localizationProvider) {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!getTextAndChangeCase(Case.INVERT)) {
				throw new IllegalArgumentException();
			}
		}
	};

	/**
	 * Action for changing language to Croatian.
	 */
	private Action croLanguageAction = new LocalizableAction(CROATIA_LOCALE, localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			LocalizationProvider.getInstance().setLanguage(CROATIA_LOCALE);
		}
	};

	/**
	 * Action for changing language to English.
	 */
	private Action engLanguageAction = new LocalizableAction(Locale.ENGLISH.getLanguage(), localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			LocalizationProvider.getInstance().setLanguage(Locale.ENGLISH.getLanguage());
		}
	};

	/**
	 * Action for changing language to German.
	 */
	private Action gerLanguageAction = new LocalizableAction(Locale.GERMAN.getLanguage(), localizationProvider) {
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			LocalizationProvider.getInstance().setLanguage(Locale.GERMAN.getLanguage());
		}
	};

	// PRIVATE METHODS

	/**
	 * Auxiliary method used to initialize the graphical user interface.
	 */
	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(300, 300);
		setTitle("JNotepadPP");
		setSize(1200, 600);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add((JTabbedPane) documents, BorderLayout.CENTER);
		createStatusBar(new JPanel(new GridLayout(0, 3)));
		createActions();
		createMenus();
		createToolbars();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				checkAndExit();
			}
		});
	}

	/**
	 * Auxiliary method used to add the components to the status bar.
	 * 
	 * @param statusBar
	 *            to be filled
	 */
	private void createStatusBar(JPanel statusBar) {
		getContentPane().add(statusBar, BorderLayout.PAGE_END);

		lengthMeter = new JLabel();
		lengthMeter.setHorizontalAlignment(JLabel.LEFT);
		lengthMeter.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		statusBar.add(lengthMeter);

		caretTracker = new JLabel();
		caretTracker.setHorizontalAlignment(JLabel.CENTER);
		caretTracker.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		statusBar.add(caretTracker);

		clockLabel = new JLabel();
		clockLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		clockLabel.setHorizontalAlignment(JLabel.RIGHT);
		statusBar.add(clockLabel);

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");

		Thread clockThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignorable) {
				}
				String date = sdf.format(new Date());
				clockLabel.setText(date);
			}
		});
		clockThread.setDaemon(true);
		clockThread.start();
	}

	/**
	 * Auxiliary method used for creating actions.
	 */
	private void createActions() {

		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));

		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));

		loadDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));

		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));

		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));

		quitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));

		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));

		statisticsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));

		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 1"));
		selectedTextActions.add(uniqueAction);

		descendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 2"));
		selectedTextActions.add(descendingAction);

		ascendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 3"));
		selectedTextActions.add(ascendingAction);

		lowercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		selectedTextActions.add(lowercaseAction);

		uppercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		selectedTextActions.add(uppercaseAction);

		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		selectedTextActions.add(invertCaseAction);

		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		selectedTextActions.add(cutAction);

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		selectedTextActions.add(copyAction);

		selectedTextActions.forEach(e -> e.setEnabled(false));
	}

	/**
	 * Auxiliary method used for creating menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new LJMenu("file", localizationProvider);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(loadDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(statisticsAction));
		fileMenu.addSeparator();
		fileMenu.add(closeDocumentAction);
		fileMenu.add(new JMenuItem(quitAction));

		JMenu editMenu = new LJMenu("edit", localizationProvider);
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));

		JMenu toolMenu = new LJMenu("tools", localizationProvider);
		menuBar.add(toolMenu);

		JMenu sortMenu = new LJMenu("sort", localizationProvider);
		toolMenu.add(sortMenu);
		sortMenu.add(new JMenuItem(ascendingAction));
		sortMenu.add(new JMenuItem(descendingAction));

		JMenu changeCase = new LJMenu("changecase", localizationProvider);
		toolMenu.add(changeCase);
		changeCase.add(new JMenuItem(uppercaseAction));
		changeCase.add(new JMenuItem(lowercaseAction));
		changeCase.add(new JMenuItem(invertCaseAction));

		toolMenu.addSeparator();
		toolMenu.add(new JMenuItem(uniqueAction));

		JMenu langMenu = new LJMenu("lang", localizationProvider);
		menuBar.add(langMenu);
		langMenu.add(new JMenuItem(croLanguageAction));
		langMenu.add(new JMenuItem(engLanguageAction));
		langMenu.add(new JMenuItem(gerLanguageAction));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Auxiliary method used for creating toolbars.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("ToolBar");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(loadDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(lowercaseAction));
		toolBar.add(new JButton(uppercaseAction));
		toolBar.add(new JButton(invertCaseAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(statisticsAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(closeDocumentAction));
		toolBar.add(new JButton(quitAction));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Auxiliary method used for sorting selected lines of the document in order
	 * depending on the input boolean.
	 * 
	 * @param ascending
	 *            sorts
	 * @throws BadLocationException
	 *             impossible to happen
	 */
	private void fetchAndSortLines(boolean ascending) throws BadLocationException {

		Document doc = textArea.getDocument();
		List<String> lines;
		int offset = getSelectedLineStart();
		int length = getSelectedLineEnd() - getSelectedLineStart();

		String selected = doc.getText(offset, length);
		lines = Arrays.asList(selected.split("\\n"));

		Locale locale = new Locale(localizationProvider.getCurrentLanguage());
		Collator collator = Collator.getInstance(locale);

		if (ascending) {
			lines.sort((o1, o2) -> collator.compare(o1, o2));
		} else {
			lines.sort((o1, o2) -> collator.compare(o2, o1));
		}
		String sep = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			if (line != null) sb.append(sep);
		}
		doc.remove(offset, length);
		doc.insertString(offset, sb.toString(), null);
	}

	/**
	 * @return index of the start of the first selected line
	 * @throws BadLocationException
	 *             impossible to happen
	 */
	private int getSelectedLineStart() throws BadLocationException {
		return textArea.getLineStartOffset(getFirstSelectedLine());
	}

	/**
	 * @return index of the end of the last selected line
	 * @throws BadLocationException
	 *             impossible to happen
	 */
	private int getSelectedLineEnd() throws BadLocationException {
		return textArea.getLineEndOffset(getLastSelectedLine());
	}

	/**
	 * @return index of the first selected line
	 * @throws BadLocationException
	 *             impossible to happen
	 */
	private int getFirstSelectedLine() throws BadLocationException {
		return textArea.getLineOfOffset(Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark()));
	}

	/**
	 * @return index of the last selected line
	 * @throws BadLocationException
	 *             impossible to happen
	 */
	private int getLastSelectedLine() throws BadLocationException {
		return textArea.getLineOfOffset(Math.max(textArea.getCaret().getDot(), textArea.getCaret().getMark()));
	}

	/**
	 * Auxiliary method used to close all the documents. Disposes the window when
	 * finished, if not canceled in the meantime.
	 */
	private void checkAndExit() {
		while (documents.getCurrentDocument() != null) {
			try {
				documents.closeDocument(documents.getCurrentDocument());
			} catch (RuntimeException abortClosing) {
				return;
			}
			if (documents.getNumberOfDocuments() == 0) break;
		}
		dispose();
	}

	/**
	 * Auxiliary method used to change the case of the selected text.
	 * 
	 * @param wantedCase
	 *            enum determining what kind of case transformation will be made
	 * @return false, if there was a error
	 */
	private boolean getTextAndChangeCase(Case wantedCase) {
		if (wantedCase == null) return false;

		JTextArea textArea = documents.getCurrentDocument().getTextComponent();
		Document doc = textArea.getDocument();
		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

		if (len == 0) return false;

		int offset = 0;
		offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());

		String text = null;
		try {
			text = doc.getText(offset, len);
		} catch (BadLocationException e1) {
			return false;
		}

		if (wantedCase == Case.INVERT) {
			char[] znakovi = text.toCharArray();
			for (int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if (Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			text = new String(znakovi);
		} else if (wantedCase == Case.UPPER) {
			text = text.toUpperCase();
		} else {
			text = text.toLowerCase();
		}

		try {
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException e) {
			return false;
		}
		return true;
	}

	/**
	 * Specific implementation of the multiple document listener for this
	 * application. Tracks the current model and adapts the windows title bar
	 * accordingly.
	 */
	private class MyMultipleDocumentListener implements MultipleDocumentListener {

		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			if (previousModel == null && currentModel == null) {
				throw new NullPointerException("previousModel or currentModel can be null but not both.");
			}

			if (currentModel == null || currentModel.getFilePath() == null) {
				setTitle("JNotepadPP");
			} else {
				setTitle(currentModel.getFilePath().toString() + " - JNotepadPP");
			}
			if (currentModel != null) {
				textArea = documents.getCurrentDocument().getTextComponent();
				currentModel.getTextComponent().addCaretListener(caretListener);
				caretTracker.setText("");
				lengthMeter.setText("");
			}
			if (previousModel != null) {
				previousModel.getTextComponent().removeCaretListener(caretListener);
				caretTracker.setText("");
				lengthMeter.setText("");
			}
		}

		@Override
		public void documentAdded(SingleDocumentModel model) {
			// do nothing
		}

		@Override
		public void documentRemoved(SingleDocumentModel model) {
			// do nothing
		}
	}

	/**
	 * Specific implementation of the caret listener for this application. Keeps
	 * track of carets line, column, and the length of the selected text.
	 */
	private class MyCaretListener implements CaretListener {

		@Override
		public void caretUpdate(CaretEvent arg0) {
			SingleDocumentModel currentModel = documents.getCurrentDocument();
			int caretLine = 0;
			int caretCol = 0;
			int selected = 0;
			int docLength = 0;
			if (currentModel != null) {
				JTextArea area = currentModel.getTextComponent();
				docLength = area.getDocument().getLength();
				int caretPos = area.getCaretPosition();
				try {
					caretLine = area.getLineOfOffset(caretPos);
					caretCol = caretPos - area.getLineStartOffset(caretLine);
				} catch (BadLocationException e) {
				}
				selected = Math.abs(area.getCaret().getDot() - area.getCaret().getMark());
			}
			String data = String.format("Ln:%d Col:%d Sel:%d", caretLine, caretCol, selected);
			caretTracker.setText(data);
			String lengthString = String.format("%s: %d", localizationProvider.getString("len"), docLength);
			lengthMeter.setText(lengthString);
			int s = selected;
			selectedTextActions.forEach(e -> e.setEnabled(s == 0 ? false : true));
		}

	}

	/**
	 * Auxiliary method used to check whether the current document exists.
	 * 
	 * @return false, if the current document does not exist
	 */
	private boolean checkCurrent() {
		SingleDocumentModel current = documents.getCurrentDocument();
		if (current == null) {
			JOptionPane.showMessageDialog(JNotepadPP.this, localizationProvider.getString("no_doc"),
					localizationProvider.getString("error"), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
