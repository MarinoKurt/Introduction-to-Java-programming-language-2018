package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface, strategy used for comparing strings.
 * 
 * @author MarinoK
 */
public interface IComparisonOperator {

	/**
	 * Method used to return if given strings satisfy the implemented condition.
	 * 
	 * @param value1
	 *            first string
	 * @param value2
	 *            second string
	 * @return true, if strings satisfy the implemented condition, false otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
