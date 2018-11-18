package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Layout for the calculator. Has a default number of rows and columns, and the
 * first element is as wide as 5 other elements with their 4 gaps (can be used
 * as the calculator display).
 * 
 * @author MarinoK
 */
public class CalcLayout implements LayoutManager2 {

	/** Maximal number of rows. */
	private static final int MAX_ROW = 5;
	/** Maximal number of columns. */
	private static final int MAX_COLUMN = 7;
	/** Gap between each element, both vertical and horizontal. */
	private int gap;
	/** Maps components to their positions. */
	private Map<Component, RCPosition> componentPosition;

	/**
	 * Constructor for the CalcLayout, assumes that the layout should be gapless.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor for the CalcLayout, takes the gap of the layout.
	 * 
	 * @param gap
	 *            between each element, both vertical and horizontal
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		componentPosition = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component component) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int defaultWidth = (parent.getWidth() - (insets.left + insets.right) - (MAX_COLUMN - 1) * gap) / MAX_COLUMN;
		int height = (parent.getHeight() - (insets.top + insets.bottom) - (MAX_ROW - 1) * gap) / MAX_ROW;

		for (Entry<Component, RCPosition> e : componentPosition.entrySet()) {
			Component component = e.getKey();
			RCPosition pos = e.getValue();
			int width;
			if (pos.getColumn() == 1 && pos.getRow() == 1) {
				width = defaultWidth * 5 + gap * 4;
			} else {
				width = defaultWidth;
			}

			int x = insets.left + width * (pos.getColumn() - 1) + gap * pos.getColumn();
			int y = insets.top + height * (pos.getRow() - 1) + gap * pos.getRow();

			component.setBounds(x, y, width, height);
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();

		int w = 0;
		int h = 0;
		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			RCPosition pos = componentPosition.get(comp);
			Dimension d = comp.getMinimumSize();
			if (pos.getRow() == 1 && pos.getColumn() == 1) {
				int realWidth = ((d.width - 4 * gap) / 5);
				d = new Dimension(realWidth, d.height);
			}
			if (w < d.width) {
				w = d.width;
			}
			if (h < d.height) {
				h = d.height;
			}
		}
		return new Dimension(insets.left + insets.right + MAX_COLUMN * w + (MAX_COLUMN - 1) * gap,
				insets.top + insets.bottom + MAX_ROW * h + (MAX_ROW - 1) * gap);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();

		int w = 0;
		int h = 0;
		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			RCPosition pos = componentPosition.get(comp);
			Dimension d = comp.getPreferredSize();

			if (pos.getRow() == 1 && pos.getColumn() == 1) {
				int realWidth = ((d.width - 4 * gap) / 5);
				d = new Dimension(realWidth, d.height);
			}

			if (w < d.width) {
				w = d.width;
			}
			if (h < d.height) {
				h = d.height;
			}
		}
		return new Dimension(insets.left + insets.right + MAX_COLUMN * w + (MAX_COLUMN - 1) * gap, //+1
				insets.top + insets.bottom + MAX_ROW * h + (MAX_ROW - 1) * gap);
	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();

		int w = 0;
		int h = 0;
		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			RCPosition pos = componentPosition.get(comp);
			Dimension d = comp.getMaximumSize();

			if (pos.getRow() == 1 && pos.getColumn() == 1) {
				int realWidth = ((d.width - 4 * gap) / 5);
				d = new Dimension(realWidth, d.height);
			}

			if (w > d.width) {
				w = d.width;
			}
			if (h > d.height) {
				h = d.height;
			}
		}
		return new Dimension(insets.left + insets.right + MAX_COLUMN * w + (MAX_COLUMN - 1) * gap,
				insets.top + insets.bottom + MAX_ROW * h + (MAX_ROW - 1) * gap);
	}

	@Override
	public void removeLayoutComponent(Component component) {
		componentPosition.remove(component);
	}

	@Override
	public void addLayoutComponent(Component component, Object constraints) {
		RCPosition constraint = null;

		if (constraints.getClass().equals(String.class)) {
			String[] array = ((String) constraints).split(",");
			if (array.length != 2) throw new CalcLayoutException("Constraint must be in form 4,5.");

			try {
				int r = Integer.parseInt(array[0]);
				int c = Integer.parseInt(array[1]);
				constraint = new RCPosition(r, c);
			} catch (NumberFormatException nan) {
				throw new CalcLayoutException("Unparsabile numbers.");
			}

		} else if (constraints.getClass().equals(RCPosition.class)) {
			constraint = (RCPosition) constraints;

		} else {
			throw new CalcLayoutException("Constraints must be a instance of RCPosition or a String.");
		}

		for (RCPosition existing : componentPosition.values()) {
			if (constraint.equals(existing)) {
				throw new CalcLayoutException("Component with the given constraint already exists.");
			}
		}
		componentPosition.put(component, constraint);

	}

	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container arg0) {
	}

}
