package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the QueryFilter.
 * 
 * @author MarinoK
 */
public class QueryFilterTest {

	@Test
	public void queryFilterTest1() {
		List<ConditionalExpression> conditions = new LinkedList<>();
		ConditionalExpression expr1 = new ConditionalExpression(FieldValueGetters.LAST_NAME, "*ić",
				ComparisonOperators.LIKE);

		ConditionalExpression expr2 = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "David",
				ComparisonOperators.GREATER_OR_EQUALS);

		ConditionalExpression expr3 = new ConditionalExpression(FieldValueGetters.JMBAG, "*3",
				ComparisonOperators.LIKE);
		conditions.add(expr1);
		conditions.add(expr2);
		conditions.add(expr3);

		QueryFilter qf = new QueryFilter(conditions);

		StudentRecord record1 = new StudentRecord("000003", "Prpić", "David", 4);
		StudentRecord record2 = new StudentRecord("000002", "Katić", "Mare", 4);
		StudentRecord record3 = new StudentRecord("000013", "Car", "Ane", 4);

		Assert.assertEquals(true, qf.accepts(record1));
		Assert.assertEquals(false, qf.accepts(record2));
		Assert.assertEquals(false, qf.accepts(record3));
	}

	@Test
	public void queryFilterTest2() {
		List<ConditionalExpression> conditions = new LinkedList<>();
		ConditionalExpression expr1 = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Prpić",
				ComparisonOperators.NOT_EQUAL);

		ConditionalExpression expr2 = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "*e",
				ComparisonOperators.LIKE);

		ConditionalExpression expr3 = new ConditionalExpression(FieldValueGetters.JMBAG, "000005",
				ComparisonOperators.LESS);
		conditions.add(expr1);
		conditions.add(expr2);
		conditions.add(expr3);

		QueryFilter qf = new QueryFilter(conditions);

		StudentRecord record1 = new StudentRecord("000003", "Prpić", "David", 4);
		StudentRecord record2 = new StudentRecord("000002", "Katić", "Mare", 4);
		StudentRecord record3 = new StudentRecord("000013", "Car", "Ane", 4);
		StudentRecord record4 = new StudentRecord("000023", "Prpić", "Filip", 4);

		Assert.assertEquals(false, qf.accepts(record1));
		Assert.assertEquals(true, qf.accepts(record2));
		Assert.assertEquals(false, qf.accepts(record3));
		Assert.assertEquals(false, qf.accepts(record4));
	}
}
