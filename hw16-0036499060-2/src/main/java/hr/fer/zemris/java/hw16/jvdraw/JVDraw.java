package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.color.ColorTracker;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.color.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.export.FileModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.model.ModificationListener;
import hr.fer.zemris.java.hw16.jvdraw.model.ModificationSubject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FPolygonEditor;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FPolygonTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Application for drawing, similar to MS Paint, but much simpler. Supports
 * drawing lines, circles and filled circles. Such painting can be saved as a
 * jvd file, and loaded again. Also supports exporting the drawing to png, jpeg
 * and gif formats.
 * 
 * @author MarinoK
 */
public class JVDraw extends JFrame {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Currently selected tool for drawing. */
	private Tool selectedTool;

	/** Foreground color area. */
	private JColorArea fgColorArea;

	/** Background color area. */
	private JColorArea bgColorArea;

	/** Drawing model of the application. */
	private DrawingModel model;

	/** Canvas to draw on. */
	private JDrawingCanvas canvas;

	/** List model of all the objects in the picture. */
	private DrawingObjectListModel listModel;

	/** File model for saving and loading files. */
	private FileModel file;

	/** Boolean marking whether the current model has been modified. */
	private boolean isModified;

	/** Model modification listener. */
	private ModificationListener ml;

	/**
	 * Constructor for JVDraw.
	 */
	public JVDraw() {
		model = new DrawingModelImpl();
		file = new FileModel();
		listModel = new DrawingObjectListModel(model);
		canvas = new JDrawingCanvas(model);
		model.addDrawingModelListener(canvas);
		ml = () -> setModified(true);
		((ModificationSubject) model).addModificationListener(ml);
		initGUI();
	}

	/**
	 * Auxiliary method used to initialize graphical user interface.
	 */
	private void initGUI() {
		setTitle("JVDraw");
		getContentPane().setLayout(new BorderLayout());
		setSize(1500, 800);
		initToolbar();
		initMenus();
		nameActions();

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
	}

	/**
	 * Method runs when the program is run.
	 * 
	 * @param args
	 *            usage not expected
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

	/**
	 * Handles the opening of a file.
	 */
	private Action openAction = new AbstractAction() {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			newAction.actionPerformed(e);
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JVD files", "jvd");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(JVDraw.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				List<String> lines = null;
				Path path = chooser.getSelectedFile().toPath();
				try {
					lines = Files.readAllLines(path);
				} catch (IOException ex) {
				}
				file.setPath(path);
				try {
					file.load(model, lines);
				} catch (Exception ex2) {
					ex2.printStackTrace();
					JOptionPane.showConfirmDialog(canvas, "Invalid file.", "Error", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
				isModified = false;
			}

		}
	};

	/**
	 * Handles the request for the new file.
	 */
	private Action newAction = new AbstractAction() {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isModified) {
				if (!askForSaving()) return;
			}
			model.clear();
			isModified = false;
		}
	};

	/**
	 * Handles the saving of a file.
	 */
	private Action saveAction = new AbstractAction() {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isModified) return;

			if (file.getPath() == null) {
				saveAsAction.actionPerformed(e);
			} else {
				try {
					file.save(model);
					isModified = false;
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showConfirmDialog(JVDraw.this, "Error", "Nothing is saved!",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};

	/**
	 * Handles the saving of a file with an unknown path.
	 */
	private Action saveAsAction = new AbstractAction() {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JVD files", "jvd");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showSaveDialog(JVDraw.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				Path path = chooser.getSelectedFile().toPath();
				file.setPath(path);
				saveAction.actionPerformed(e);
			}

		}
	};

	/**
	 * Handles the exporting of a file.
	 */
	private Action exportAction = new AbstractAction() {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			if (model.getSize() == 0) {
				JOptionPane.showConfirmDialog(JVDraw.this, "Nothing to export.", "Error", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg", "gif");
			fc.setDialogTitle("Export");
			fc.setFileFilter(filter);
			if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			Path toExport = fc.getSelectedFile().toPath();
			String fileName = toExport.toString();
			String extension = "png";
			if (fileName.endsWith(".jpg")) {
				extension = "jpg";
			} else if (fileName.endsWith(".gif")) {
				extension = "gif";
			} else if (fileName.endsWith(".png")) {
				extension = "png";
			} else {
				fileName = fileName.concat(".").concat(extension);
				toExport = Paths.get(fileName);
			}

			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			for (int i = 0; i < model.getSize(); i++) {
				model.getObject(i).accept(bbcalc);
			}
			Rectangle box = bbcalc.getBoundingBox();

			BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = image.createGraphics();
			g.translate(-box.x, -box.y);
			g.setColor(Color.white);
			g.fillRect(box.x, box.y, box.width, box.height);
			GeometricalObjectPainter p = new GeometricalObjectPainter(g);
			for (int i = 0, n = model.getSize(); i < n; i++) {
				model.getObject(i).accept(p);
			}
			g.dispose();

			try {
				ImageIO.write(image, extension, toExport.toFile());
			} catch (IOException e1) {
				JOptionPane.showConfirmDialog(JVDraw.this, "Error", "Error while exporting.",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showConfirmDialog(JVDraw.this, "Success", "Image exported.", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * Handles the exiting from the application.
	 */
	private Action exitAction = new AbstractAction() {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isModified) {
				if (!askForSaving()) return;
			}
			dispose();
		}
	};

	/**
	 * Switches the current tool to line.
	 */
	private Action lineAction = new AbstractAction() {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedTool = new LineTool(model, fgColorArea, canvas);
			canvas.setSelectedTool(selectedTool);
		}
	};
	
	private Action polyAction = new AbstractAction() {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedTool = new FPolygonTool(model, fgColorArea, bgColorArea, canvas);
			canvas.setSelectedTool(selectedTool);
		}
	};

	/**
	 * Switches the current tool to circle.
	 */
	private Action circleAction = new AbstractAction() {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedTool = new CircleTool(model, fgColorArea, canvas);
			canvas.setSelectedTool(selectedTool);
		}
	};

	/**
	 * Switches the current tool to filled circle.
	 */
	private Action filledCircleAction = new AbstractAction() {

		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedTool = new FilledCircleTool(model, fgColorArea, bgColorArea, canvas);
			canvas.setSelectedTool(selectedTool);
		}
	};

	/**
	 * Auxiliary method used to create toolbar.
	 */
	private void initToolbar() {
		JToolBar toolBar = new JToolBar("ToolBar");
		fgColorArea = new JColorArea(Color.BLACK);
		bgColorArea = new JColorArea(Color.BLUE);
		toolBar.add(fgColorArea);
		toolBar.add(bgColorArea);
		toolBar.addSeparator();

		JToggleButton lineButton = new JToggleButton(lineAction);
		JToggleButton circleButton = new JToggleButton(circleAction);
		JToggleButton filledCircleButton = new JToggleButton(filledCircleAction);
		JToggleButton polyButton = new JToggleButton(polyAction);
		
		ButtonGroup bg = new ButtonGroup();

		bg.add(lineButton);
		bg.add(circleButton);
		bg.add(filledCircleButton);
		bg.add(polyButton);

		toolBar.add(lineButton);
		toolBar.add(circleButton);
		toolBar.add(filledCircleButton);
		toolBar.add(polyButton);

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);

		selectedTool = new LineTool(model, fgColorArea, canvas);
		canvas.setSelectedTool(selectedTool);
		canvas.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedTool.mouseClicked(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				selectedTool.mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				selectedTool.mouseReleased(e);
			}

		});
		canvas.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				selectedTool.mouseMoved(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				selectedTool.mouseDragged(e);
			}
		});
		getContentPane().add(canvas, BorderLayout.CENTER);

		getContentPane().add(new ColorTracker(fgColorArea, bgColorArea), BorderLayout.PAGE_END);

		JList<GeometricalObject> list = new JList<>(listModel);
		JScrollPane jsp = new JScrollPane(list);
		jsp.setPreferredSize(getPreferredSize());
		getContentPane().add(jsp, BorderLayout.LINE_END);
		list.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) {
				JList<GeometricalObject> list = (JList<GeometricalObject>) e.getSource();
				if (e.getClickCount() == 2 && model.getSize() > 0) {
					int index = list.locationToIndex(e.getPoint());
					GeometricalObjectEditor editor = model.getObject(index).createGeometricalObjectEditor();
					int result = JOptionPane.showConfirmDialog(canvas, editor, "Alter shape",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					try {
						editor.checkEditing();
					} catch (RuntimeException ex) {
						JOptionPane.showConfirmDialog(canvas, "Invalid parameters. Changes not saved.", "Error",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (result == JOptionPane.OK_OPTION) {
						editor.acceptEditing();
					}
				}

			}
		});
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				int index = list.getSelectedIndex();
				if (key == KeyEvent.VK_DELETE) {
					if (index >= 0 && index < model.getSize()) {
						model.remove(index);
					}
				} else if (key == KeyEvent.VK_ADD) {
					model.changeOrder(index, 1);
					list.setSelectedIndex(++index);
				} else if (key == KeyEvent.VK_SUBTRACT) {
					model.changeOrder(index, -1);
					list.setSelectedIndex(--index);
				}
			}

		});
	}

	/**
	 * Auxiliary method used to check whether it is okay to destroy the current
	 * state of the model.
	 * 
	 * @return true, if it is okay to proceed with the action
	 */
	private boolean askForSaving() {
		int result = JOptionPane.showConfirmDialog(JVDraw.this, "Save changes?", "Save changes",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			saveAction.actionPerformed(null);
		} else if (result == JOptionPane.CLOSED_OPTION || result == JOptionPane.CANCEL_OPTION) {
			return false;
		}
		return true;
	}

	/**
	 * Auxiliary method used to name the actions.
	 */
	private void nameActions() {
		lineAction.putValue(Action.NAME, "Line");
		lineAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		circleAction.putValue(Action.NAME, "Circle");
		circleAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		filledCircleAction.putValue(Action.NAME, "Filled circle");
		filledCircleAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F"));
		polyAction.putValue(Action.NAME, "FilledPolygon");
		
		openAction.putValue(Action.NAME, "Open");
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAsAction.putValue(Action.NAME, "SaveAs");
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		newAction.putValue(Action.NAME, "New");
		newAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
	}

	/**
	 * Auxiliary method used to create menus.
	 */
	private void initMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(newAction));
		fileMenu.add(new JMenuItem(openAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exportAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		this.setJMenuBar(menuBar);
	}

	/**
	 * Returns is the model modified.
	 * 
	 * @return true, if the model is modified
	 */
	public boolean isModified() {
		return isModified;
	}

	/**
	 * Setter for the modified flag.
	 * 
	 * @param isModified
	 *            set to true, if the model is modified
	 */
	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

}
