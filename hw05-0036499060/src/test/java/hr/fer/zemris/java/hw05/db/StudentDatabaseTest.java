package hr.fer.zemris.java.hw05.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the StudentDatabase and its methods.
 * 
 * @author MarinoK
 */
public class StudentDatabaseTest {

	private static final int DATABASE_SIZE = 63;
	StudentDatabase db;

	@Before
	public void initialization() throws FileNotFoundException {
		java.util.List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get("./src/main/resources/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new FileNotFoundException("Invalid path.");
		}
		db = new StudentDatabase(lines);
	}

	@Test
	public void filterTest() {

		IFilter AlwaysTrue = (record) -> true;
		IFilter AlwaysFalse = (record) -> false;

		java.util.List<StudentRecord> all = db.filter(AlwaysTrue);
		java.util.List<StudentRecord> none = db.filter(AlwaysFalse);

		Assert.assertEquals(DATABASE_SIZE, all.size());
		Assert.assertEquals(0, none.size());
	}

	@Test
	public void forJMBAGTest() {
		StudentRecord record = db.forJMBAG("0000000027");
		Assert.assertEquals("Komunjer", record.getLastName());
		Assert.assertEquals("Luka", record.getFirstName());
		Assert.assertEquals(4, record.getFinalGrade());

		StudentRecord record2 = db.forJMBAG("0000000028");
		StudentRecord record2manual = new StudentRecord("0000000028", "Kosanović", "Nenad", 5);
		Assert.assertEquals(record2, record2manual);
	}

}
