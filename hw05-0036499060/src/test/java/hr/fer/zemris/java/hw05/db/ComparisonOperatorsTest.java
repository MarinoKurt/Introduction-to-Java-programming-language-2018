package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the comparison operators.
 * 
 * @author MarinoK
 */
public class ComparisonOperatorsTest {

	@Test
	public void comparisonOperatorTest() {
		IComparisonOperator operLess = ComparisonOperators.LESS;
		Assert.assertEquals(true, operLess.satisfied("Ana", "Jasna"));

		IComparisonOperator operLike1 = ComparisonOperators.LIKE;
		Assert.assertEquals(true, operLike1.satisfied("Banana", "*na"));

		IComparisonOperator operLike2 = ComparisonOperators.LIKE;
		Assert.assertEquals(true, operLike2.satisfied("Anatomija", "An*"));

		IComparisonOperator operLike3 = ComparisonOperators.LIKE;
		Assert.assertEquals(true, operLike3.satisfied("Anatomija", "Ana*a"));

		IComparisonOperator operLike4 = ComparisonOperators.LIKE;
		Assert.assertEquals(true, operLike4.satisfied("Krokodil", "Kro*il"));

		IComparisonOperator operLike5 = ComparisonOperators.LIKE;
		Assert.assertEquals(false, operLike5.satisfied("AAA", "AA*AA"));
		
		IComparisonOperator operLike6 = ComparisonOperators.LIKE;
		Assert.assertEquals(true, operLike5.satisfied("Mačić", "Mač*"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void twoWildcardsException() {
		IComparisonOperator operLike = ComparisonOperators.LIKE;
		Assert.assertEquals(true, operLike.satisfied("Krokodil", "*ro*il"));
	}

}
