package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Represents a model which memorizes all the relevant information for drawing a
 * bar chart.
 * 
 * @author MarinoK
 */
public class BarChart {

	/** List of paired values. */
	private List<XYValue> valueList;
	/** Description of the value on the x axis. */
	private String xDescription;
	/** Description of the value on the y axis. */
	private String yDescription;
	/** Minimal value on the y axis. */
	private int yMin;
	/** Maximal value on the y axis. */
	private int yMax;
	/** Step of the y axis of the chart. */
	private int step;

	/**
	 * Constructor for the BarChart.
	 * 
	 * @param valueList
	 *            list of paired values
	 * @param xDescription
	 *            description of the value on the x axis
	 * @param yDescription
	 *            description of the value on the y axis
	 * @param yMin
	 *            minimal value on the y axis
	 * @param yMax
	 *            maximal value on the y axis
	 * @param step
	 *            of the y axis
	 */
	public BarChart(List<XYValue> valueList, String xDescription, String yDescription, int yMin, int yMax, int step) {
		if (yMin >= yMax) throw new IllegalArgumentException("yMax must be greater than yMin.");
		this.valueList = valueList;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMin = yMin;
		this.step = step;
		this.yMax = determineYMax(yMax);
	}

	/**
	 * @return list of paired values
	 */
	public List<XYValue> getValueList() {
		return valueList;
	}

	/**
	 * @return description of the value on the x axis
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * @return description of the value on the y axis
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * @return minimal value on the y axis
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @return maximal value on the y axis
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @return step of the y axis
	 */
	public int getStep() {
		return step;
	}

	/**
	 * Auxiliary method used for determining the maximum value of the y coordinate,
	 * according to the given rule.
	 * 
	 * @param givenYMax
	 *            given by the user
	 * @return value equal or greater to the givenYMax, depending on its
	 *         divisibility by the step
	 */
	private int determineYMax(int givenYMax) {
		if ((givenYMax - yMin) % step == 0) {
			return givenYMax;
		} else {
			int newYMax = yMin;
			for (; newYMax < givenYMax; newYMax += step)
				;
			return newYMax;
		}
	}
}
