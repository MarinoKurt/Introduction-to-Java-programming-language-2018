package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the ConditionalExpression.
 * 
 * @author MarinoK
 */
public class ConditionalExpressionTest {

	@Test
	public void condExpTest1() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Iv*",
				ComparisonOperators.LIKE);
		StudentRecord record = new StudentRecord("5210972", "Ivić", "Ivica", 4);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		Assert.assertEquals(true, recordSatisfies);
	}
	
	@Test
	public void condExpTest2() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.JMBAG, "251051",
				ComparisonOperators.GREATER);
		StudentRecord record = new StudentRecord("251052", "Prpić", "David", 4);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		Assert.assertEquals(true, recordSatisfies);
	}
	
	@Test
	public void condExpTest3() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.JMBAG, "251052",
				ComparisonOperators.EQUALS);
		StudentRecord record = new StudentRecord("251052", "Prpić", "David", 4);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		Assert.assertEquals(true, recordSatisfies);
	}
}
