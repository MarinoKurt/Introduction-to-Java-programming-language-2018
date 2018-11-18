package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Database containing StudentRecords.
 * 
 * @author MarinoK
 */
public class StudentDatabase {

	/**
	 * Lines of the text file containing the database: all students with all
	 * StudentRecord parameters separated by blanks.
	 */
	private List<String> lines;

	/** Table of JMBAG-record pairs, used for fast retrieval of records in O(1). */
	private SimpleHashtable<String, StudentRecord> backbone;

	/** List of all the studentRecords added. */
	private List<StudentRecord> recordList;

	/**
	 * Constructor for the studentDatabase.
	 * 
	 * @param lines
	 *            from the file input, every line is expected to contain all
	 *            StudentRecords attributes, separated by spaces
	 */
	public StudentDatabase(List<String> lines) {
		this.lines = lines;
		backbone = new SimpleHashtable<>(70);
		recordList = new LinkedList<>();
		fillDatabase();
	}

	/**
	 * Auxiliary method which fills the database from the input lines given in
	 * constructor.
	 */
	private void fillDatabase() {
		for (String line : lines) {
			String[] lineSplitted = line.split("\t");
			String jmbag = lineSplitted[0];
			StudentRecord record = new StudentRecord(jmbag, lineSplitted[1], lineSplitted[2],
					Integer.valueOf(lineSplitted[3]));
			backbone.put(jmbag, record);
			recordList.add(record);
		}

	}

	/**
	 * Fetches the StudentRecord with the given JMBAG in O(1).
	 * 
	 * @param jmbag
	 *            to be searched
	 * @return StudentRecord with the given JMBAG, or null if the record is not in
	 *         the database
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return backbone.get(jmbag);
	}

	/**
	 * Filters all records from the database, returns a list of records which
	 * satisfied the condition given over the IFilter instance.
	 * 
	 * @param filter
	 *            implementation of the IFilter interface
	 * @return list of studentRecords, contains only those records which satisfied
	 *         the condition
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredRecords = new LinkedList<>();
		for (StudentRecord record : recordList) {
			if (filter.accepts(record)) {
				filteredRecords.add(record);
			}
		}
		return filteredRecords;
	}

}
