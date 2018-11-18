package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Filter for multiple conditional expressions.
 * 
 * @author MarinoK
 */
public class QueryFilter implements IFilter {

	/**
	 * List of conditions that query must satisfy to pass this filter.
	 */
	List<ConditionalExpression> conditions;

	/**
	 * Constructor for the QueryFilter.
	 * 
	 * @param conditions
	 *            in form of a list of conditional expressions
	 */
	public QueryFilter(List<ConditionalExpression> conditions) {
		this.conditions = conditions;
	}

	@Override
	public boolean accepts(StudentRecord record) {

		for (ConditionalExpression condition : conditions) {
			boolean satisfies = condition.getComparisonOperator().satisfied(condition.getFieldGetter().get(record),
					condition.getStringLiteral());

			if (!satisfies) return false;
		}
		return true;
	}

}
