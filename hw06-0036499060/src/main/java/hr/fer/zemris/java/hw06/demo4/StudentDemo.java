package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Demonstration for managing the artificial student database using stream API.
 * 
 * @author MarinoK
 */
public class StudentDemo {

	/**
	 * Threshold for points used to filter students.
	 */
	private static final double THRESHOLD = 25;

	/**
	 * Maximum possible grade, used to filter students.
	 */
	private static final int TOP_GRADE = 5;

	/**
	 * Grade of students who failed the subject.
	 */
	private static final int FAILED = 1;

	/**
	 * Main method run when the program is run.
	 * 
	 * @param args
	 *            command line arguments, usage not expected
	 */
	public static void main(String[] args) {

		Path path = Paths.get("./src/main/resources/studenti.txt");
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			throw new InvalidPathException(path.toString(), "Invalid path. Was");
		}

		List<StudentRecord> records = convert(lines);
		vratiBodovaViseOd25(records);
		vratiBrojOdlikasa(records);
		vratiListuOdlikasa(records);
		vratiSortiranuListuOdlikasa(records);
		vratiPopisNepolozenih(records);
		razvrstajStudentePoOcjenama(records);
		vratiBrojStudenataPoOcjenama(records);
		razvrstajProlazPad(records);
	}

	/**
	 * Method that returns number of students who achieved more than 25 points on
	 * all the exams.
	 * 
	 * @param records
	 *            list of student records
	 * @return number of students who achieved more than 25 points
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> (s.getTotalPoints() > THRESHOLD))
				.count();
	}

	/**
	 * Method that returns number of students who achieved the maximum grade.
	 * 
	 * @param records
	 *            list of student records
	 * @return number of students who achieved the maximum grade
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> (s.getFinalGrade() == TOP_GRADE))
				.count();
	}

	/**
	 * Method that returns list of students who achieved the maximum grade.
	 * 
	 * @param records
	 *            list of student records
	 * @return list of student records, for students who achieved the maximum grade
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> (s.getFinalGrade() == TOP_GRADE))
				.collect(Collectors.toList());
	}

	/**
	 * Method that returns a sorted list of students who achieved the maximum grade.
	 * 
	 * @param records
	 *            list of student records
	 * @return sorted list of student records, for students who achieved the maximum
	 *         grade
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> (s.getFinalGrade() == TOP_GRADE))
				.sorted((s1, s2) -> s1.getTotalPoints()
						.compareTo(s2.getTotalPoints()))
				.collect(Collectors.toList());
	}

	/**
	 * Method that returns list of students who achieved the minimum grade.
	 * 
	 * @param records
	 *            list of student records
	 * @return list of student records, for students who achieved the minimum grade
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> (s.getFinalGrade() == FAILED))
				.map(s -> s.getJmbag())
				.collect(Collectors.toList());
	}

	/**
	 * Method used for organizing a map where the student's grade is the key, and
	 * joined value is the list of corresponding student records.
	 * 
	 * @param records
	 *            list of student records
	 * @return map where the student's grade is the key, and joined value is a list
	 *         of corresponding student records
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}

	/**
	 * Method used for organizing a map where the student's grade is the key, and
	 * the number of students with the specific grade is the value.
	 * 
	 * @param records
	 *            list of student records
	 * @return map where the student's grade is the key, and the number of students
	 *         with the specific grade is the value
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.toMap(StudentRecord::getFinalGrade, (s) -> 1, Integer::sum));
	}

	/**
	 * Method used for organizing a map where the key is boolean value whether the
	 * student passed the subject, and corresponding value is a list of students who
	 * passed (for true), or failed (for false).
	 * 
	 * @param records
	 *            list of student records
	 * @return map where the key is boolean value whether the student passed the
	 *         subject, and corresponding value is a list of students who passed
	 *         (for true), or failed (for false).
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.partitioningBy(s -> s.getFinalGrade() > 1));
	}

	/**
	 * Auxiliary method used to convert a list of strings to a list of student
	 * records, based on the expected format of the strings in the list.
	 * 
	 * @param lines
	 *            list of strings containing student data
	 * @return list of student records containing read student data
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> allRecords = new ArrayList<>();
		for (String line : lines) {
			if (line.trim()
					.equals(""))
				continue;
			String[] fields = line.split("\t");

			try {
				allRecords.add(new StudentRecord(fields[0], fields[1], fields[2], Double.valueOf(fields[3]),
						Double.valueOf(fields[4]), Double.valueOf(fields[5]), Integer.valueOf(fields[6])));
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				System.out.println("Invalid input line. Was: " + line);
			}
		}
		return allRecords;
	}
}
