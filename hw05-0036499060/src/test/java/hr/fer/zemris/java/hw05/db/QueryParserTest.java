package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic test class for the QueryParser.
 * 
 * @author MarinoK
 */
public class QueryParserTest {

	QueryParser qp;

	@Test
	public void directQuerytest() {
		qp = new QueryParser("jmbag=\"0000000003\"");
		Assert.assertEquals(true, qp.isDirectQuery());
		Assert.assertEquals("0000000003", qp.getQueriedJMBAG());
	}

	@Test
	public void lastNameQuerytest() {
		qp = new QueryParser("  lastName  =    \"Blažić\" ");
		Assert.assertEquals(false, qp.isDirectQuery());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void notDirectQuerytest() {
		qp = new QueryParser("  lastName  =    \"Blažić\" ");
		qp.getQueriedJMBAG();
	}

	@Test
	public void doubleQuerytest() {
		qp = new QueryParser("firstName>\"A\" and lastName LIKE \"B*ć\"");
		Assert.assertEquals(false, qp.isDirectQuery());
	}

	@Test
	public void tripleQuerytest() {
		qp = new QueryParser("firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		Assert.assertEquals(false, qp.isDirectQuery());
	}

}
