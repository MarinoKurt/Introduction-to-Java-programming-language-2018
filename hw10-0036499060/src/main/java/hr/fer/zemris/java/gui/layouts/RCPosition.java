package hr.fer.zemris.java.gui.layouts;

/**
 * 
 * 
 * @author MarinoK
 */
public class RCPosition {

	/** Maximal number of rows. */
	private static final int MAX_ROW = 5;
	/** Maximal number of columns. */
	private static final int MAX_COLUMN = 7;
	/** Minimal number of rows or columns. */
	private static final int MIN_ROW_COLUMN = 1;

	/** Number of the row. */
	private final int row;

	/** Number of the column. */
	private final int column;

	/**
	 * Constructor for the RCPosition.
	 * 
	 * @param row
	 *            number
	 * @param column
	 *            number
	 * @throws CalcLayoutException
	 *             if the invalid arguments are given.
	 */
	public RCPosition(int row, int column) {
		if (row < MIN_ROW_COLUMN || row > MAX_ROW || column > MAX_COLUMN || column < MIN_ROW_COLUMN) {
			throw new CalcLayoutException("Invalid number of columns or rows given.");
		}
		if (row == 1 && (column < 6 && column > 1)) {
			throw new CalcLayoutException("Positions from 1,2 - 1,5 are not avaliable.");
		}
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter for the row number.
	 * 
	 * @return row number
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter for the column number.
	 * 
	 * @return column number
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column) return false;
		if (row != other.row) return false;
		return true;
	}

}
