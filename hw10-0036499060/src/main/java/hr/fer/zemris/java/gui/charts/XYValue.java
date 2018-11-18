package hr.fer.zemris.java.gui.charts;

/**
 * Construct that represents the value of the y for the corresponding x in the
 * chart data.
 * 
 * @author MarinoK
 */
public class XYValue {

	/** Value of x coordinate. */
	private int x;

	/** Value of y coordinate. */
	private int y;

	/**
	 * Constructor for the XYValue.
	 * 
	 * @param x
	 *            value
	 * @param y
	 *            value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y value
	 */
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		XYValue other = (XYValue) obj;
		if (x != other.x) return false;
		if (y != other.y) return false;
		return true;
	}

}
