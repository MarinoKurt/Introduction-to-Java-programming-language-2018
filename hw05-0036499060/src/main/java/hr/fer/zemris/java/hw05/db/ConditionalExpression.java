package hr.fer.zemris.java.hw05.db;

/**
 * Conditional expression is used for storing one condition that can be applied
 * on the database
 * 
 * @author MarinoK
 *
 */
public class ConditionalExpression {

	/** Getter for the StudentRecord attribute given. */
	private IFieldValueGetter fieldGetter;

	/** String given by the user, to be compared with values in the database. */
	private String stringLiteral;

	/** Operator that compares two strings. */
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructor for the conditional expression.
	 * 
	 * @param fieldGetter
	 *            one of FieldValueGetters
	 * @param stringLiteral
	 *            string given by the user, to be compared with values in the
	 *            database
	 * @param comparisonOperator
	 *            operator to compare with
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Getter for the FieldGetter.
	 * 
	 * @return instance of IFieldValueGetter, able to fetch StudentRecords attribute
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Getter for the user input string.
	 * 
	 * @return user input string
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter for the comparison operator.
	 * 
	 * @return instance of IComparisonOperator, able to compare two strings
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
