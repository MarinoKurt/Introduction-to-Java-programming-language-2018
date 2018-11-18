package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Program for interaction of Student Database with the user.
 * 
 * @author MarinoK
 */
public class StudentDB {

	/**
	 * Method runs when the program is run. Expected user input from System.in.
	 * 
	 * @param args
	 *            command line arguments, usage not expected
	 */
	public static void main(String[] args) {
		QueryParser parser = null;
		Scanner input = new Scanner(System.in);
		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get("./src/main/resources/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Invalid path.");
		}

		StudentDatabase db = new StudentDatabase(lines);

		while (true) {
			System.out.print("> ");
			String line = input.nextLine();
			if (line.startsWith("query")) {
				try {
					parser = new QueryParser(line.substring(5));
					printMatches(parser, db);
				} catch (QueryLexerException | QueryParserException | IllegalArgumentException e) {
					System.out.println(e.getMessage());
					continue;
				}
			} else if (line.matches("exit")) {
				System.out.println("Goodbye!");
				input.close();
				break;
			} else {
				System.out.println("Invalid command. Avaliable commands are: query, exit. Was: " + line);
			}
		}
	}

	/**
	 * Auxiliary method used for getting the specific output.
	 * 
	 * @param parser
	 *            current parser
	 * @param db
	 *            current database
	 */
	private static void printMatches(QueryParser parser, StudentDatabase db) {
		/** Length of every JMBAG string. */
		final int JMBAG_LENGTH = 10;

		/** Number of blank spaces in every major column in output. */
		final int BLANK_COLUMNS = 2;

		/** Length of every grade. */
		final int GRADE_LENGTH = 1;

		/** List of all the matches from the database. */
		List<StudentRecord> matches = new LinkedList<>();

		if (parser.isDirectQuery()) {
			System.out.println("Using index for record retrieval.");
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			matches.add(r);
		} else {
			for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
				matches.add(r);
			}
		}

		if (matches.isEmpty()) {
			System.out.println("Records selected: 0");
		} else {
			int longestLastNameLength = 0;
			int longestFirstNameLength = 0;
			for (StudentRecord match : matches) {
				if (match.getFirstName().length() > longestFirstNameLength) {
					longestFirstNameLength = match.getFirstName().length();
				}
				if (match.getLastName().length() > longestLastNameLength) {
					longestLastNameLength = match.getLastName().length();
				}
			}
			StringBuilder headerFooter = new StringBuilder("+");
			for (int i = 0; i < JMBAG_LENGTH + BLANK_COLUMNS; i++) {
				headerFooter.append("=");
			}
			headerFooter.append("+");
			for (int i = 0; i < longestLastNameLength + BLANK_COLUMNS; i++) {
				headerFooter.append("=");
			}
			headerFooter.append("+");
			for (int i = 0; i < longestFirstNameLength + BLANK_COLUMNS; i++) {
				headerFooter.append("=");
			}
			headerFooter.append("+");
			for (int i = 0; i < GRADE_LENGTH + BLANK_COLUMNS; i++) {
				headerFooter.append("=");
			}
			headerFooter.append("+");

			System.out.println(headerFooter);

			StringBuilder oneRecord;
			for (StudentRecord match : matches) {
				oneRecord = new StringBuilder("| ");
				oneRecord.append(match.getJmbag()).append(" | ");
				oneRecord.append(match.getLastName());
				for (int i = match.getLastName().length(); i < longestLastNameLength; i++) {
					oneRecord.append(" ");
				}
				oneRecord.append(" | ");
				oneRecord.append(match.getFirstName());
				for (int i = match.getFirstName().length(); i < longestFirstNameLength; i++) {
					oneRecord.append(" ");
				}
				oneRecord.append(" | ");
				oneRecord.append(match.getFinalGrade()).append(" |");
				System.out.println(oneRecord);
			}
			System.out.println(headerFooter);
			System.out.println("Records selected: " + matches.size());
		}

	}
}
