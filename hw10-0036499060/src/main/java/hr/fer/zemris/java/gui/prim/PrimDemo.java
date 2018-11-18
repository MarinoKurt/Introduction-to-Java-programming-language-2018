package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstration of behavior of the ListModel with two separate lists following
 * the same model.
 * 
 * @author MarinoK
 */
public class PrimDemo extends JFrame {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the PrimDemo.
	 */
	public PrimDemo() {
		setLocation(200, 200);
		setSize(300, 200);
		setTitle("PrimDemo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	/**
	 * Main method runs when the program is run.
	 * 
	 * @param args
	 *            usage not expected
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.setVisible(true);
		});
	}

	/**
	 * Auxiliary method used for graphical user interface initialization.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();

		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JButton next = new JButton("sljedeći");
		cp.add(next, BorderLayout.PAGE_END);

		next.addActionListener(e -> {
			model.next();
		});

		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));

		cp.add(central, BorderLayout.CENTER);
	}

}
