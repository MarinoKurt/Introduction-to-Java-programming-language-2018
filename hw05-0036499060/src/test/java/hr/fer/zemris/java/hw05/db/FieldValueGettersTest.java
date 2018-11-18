package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the FieldValueGetters.
 * 
 * @author MarinoK
 */
public class FieldValueGettersTest {

	@Test
	public void fieldValueGettersTest1() {
		StudentRecord record = new StudentRecord("5210972", "Ivić", "Ivica", 4);
		Assert.assertEquals("Ivica", FieldValueGetters.FIRST_NAME.get(record));
		Assert.assertEquals("Ivić", FieldValueGetters.LAST_NAME.get(record));
		Assert.assertEquals("5210972", FieldValueGetters.JMBAG.get(record));
	}
	
	
	@Test
	public void fieldValueGettersTest2() {
		StudentRecord record = new StudentRecord("251051", "Prpić", "Marco", 4);
		Assert.assertEquals("Marco", FieldValueGetters.FIRST_NAME.get(record));
		Assert.assertEquals("Prpić", FieldValueGetters.LAST_NAME.get(record));
		Assert.assertEquals("251051", FieldValueGetters.JMBAG.get(record));
	}
}
