package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import hr.fer.zemris.java.hw16.vector.VectorND;
import hr.fer.zemris.java.hw16.vocabulary.VocabularyManager;

/**
 * Console for using the search engine. Takes commands query, results, type and
 * exit. Both type and results can only be performed after a query. Query takes
 * a set of words and finds the most similar document in the library.
 * 
 * @author MarinoK
 */
public class Konzola {

	/** Path of the file containing croatian stopwords. */
	private static final String STOPWORDS_PATH = "./src/main/resources/hrvatski_stoprijeci.txt";

	/** Path of the library root. */
	@SuppressWarnings("unused")
	private static final String ARTICLES_PATH = "./src/main/resources/clanci"; // here only to help You review this
																				// homework.

	/** Command name for the query command. */
	private static final String COMMAND_QUERY = "query";

	/** Command name for the type command. */
	private static final String COMMAND_TYPE = "type";

	/** Command name for the results command. */
	private static final String COMMAND_RESULTS = "results";

	/** Command name for the exit command. */
	private static final String COMMAND_EXIT = "exit";

	/** Threshold used for comparing double values. */
	private static final double THRESHOLD = 1e-3;

	/** Vocabulary manager used to work with the vocabulary. */
	private static VocabularyManager manager;

	/** Data gathered on the path in form of a path-vector pair. */
	private static Map<Path, VectorND> data;

	/** Map of similar documents. Ordered by similarity. */
	private static Map<Double, Path> similarityMap;

	/**
	 * Main method is run when the program is run.
	 * 
	 * @param args
	 *            expected one argument; path to the library root folder
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Invalid number of arguments. Console expected one argument;"
					+ " a path to the library root folder.");
			System.exit(1);
		}

		Path libraryRoot = null;
		try {
			libraryRoot = Paths.get(args[0]);
		} catch (InvalidPathException e) {
			System.out.println("Invalid library root path.");
			System.exit(1);
		}

		List<String> stopwords = null;
		try {
			stopwords = Files.readAllLines(Paths.get(STOPWORDS_PATH));
		} catch (IOException e) {
			System.err.println("Problem reading stopwords file!");
			System.exit(1);
		}

		try {
			manager = new VocabularyManager(libraryRoot, stopwords);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> vocabulary = manager.getVocabulary();

		System.out.println("Vocabulary size is: " + vocabulary.size() + " words.");
		System.out.println("Library size is: " + manager.getLibraryCount() + " documents.");

		Scanner sc = new Scanner(System.in);
		boolean queryDone = false;

		while (true) {
			System.out.println();
			System.out.print("Enter command > ");
			String input = sc.nextLine();
			String[] arguments = input.split("\\s+");
			if (arguments.length == 0) {
				System.out.println("Please input a valid command.");
			}

			switch (arguments[0]) {
			case (COMMAND_EXIT):
				System.out.println("Goodbye!");
				sc.close();
				System.exit(0);
			case (COMMAND_RESULTS):
				if (arguments.length != 1) {
					System.out.println("Results command takes no arguments.");
					continue;
				}
				if (!queryDone) {
					System.out.println("Cannot give results without a query! Give me a query first.");
					continue;
				}
				displayResults();
				break;
			case (COMMAND_TYPE):
				int index;
				if (arguments.length != 2) {
					System.out.println("Please provide only the index of the document to display.");
					continue;
				}
				try {
					index = Integer.parseInt(arguments[1]);
				} catch (NumberFormatException e) {
					System.out.println("Index must be an integer. Try again.");
					continue;
				}
				if (!queryDone) {
					System.out.println("Cannot give any document without a query! Give me a query first.");
					continue;
				}
				typeDocument(index);
				break;
			case (COMMAND_QUERY):
				if (arguments.length < 2) {
					System.out.println("Please provide the words you request in the query.");
					continue;
				}
				List<String> familiarWords = null;
				try {
					familiarWords = filterQuery(arguments);
				} catch (RuntimeException e) {
					System.out.println("Invalid query! Try again.");
					e.printStackTrace();
					continue;
				}
				if (familiarWords == null) continue;
				processQuery(familiarWords);
				System.out.println("Top 10 results:");
				displayResults();
				queryDone = true;
				break;
			default:
				System.out.println("Unknown command. Avaliable commands are: query, type, results, exit.");
			}

		}

	}

	/**
	 * Filters the given arguments from the command to a list of words that are in
	 * the vocabulary.
	 * 
	 * @param arguments
	 *            of the command, to be interpreted as words
	 * @return list of words that the user requested, and that are in the vocabulary
	 */
	private static List<String> filterQuery(String[] arguments) {
		StringBuilder sb = new StringBuilder("Query is: [");
		List<String> familiarWords = new ArrayList<>();
		for (String arg : arguments) {
			if (manager.getVocabulary().contains(arg)) {
				familiarWords.add(arg);
			}
		}
		if (familiarWords.isEmpty()) {
			System.out.println("Your query does not contain a word from the vocabulary! Try again.");
			return null;
		}
		familiarWords.forEach(e -> sb.append(e + ", "));
		sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		System.out.println(sb.toString());
		return familiarWords;
	}

	/**
	 * Method to process the given query. Calculates the vector for the user input,
	 * and prepares a similarity map of the documents.
	 * 
	 * @param familiarWords
	 *            list of words that the user requested, and that are in the
	 *            vocabulary
	 */
	private static void processQuery(List<String> familiarWords) {

		try {
			manager.calculateDocumentVectors();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		data = manager.getData();
		VectorND userInputVector = manager.calculateVectors(familiarWords);

		VectorND idf = calculateIDF();

		for (Map.Entry<Path, VectorND> entry : data.entrySet()) {
			entry.setValue(entry.getValue().mul(idf));
		}

		userInputVector = userInputVector.mul(idf);

		similarityMap = new TreeMap<>((Comparator<Double>) (o1, o2) -> o2.compareTo(o1));

		for (Map.Entry<Path, VectorND> entry : data.entrySet()) {
			double sim = userInputVector.dot(entry.getValue()) / (userInputVector.norm() * entry.getValue().norm());
			if (sim < THRESHOLD) continue;
			sim = sim * 10000;
			sim = Math.round(sim);
			sim = sim / 10000;
			similarityMap.put(sim, entry.getKey());
		}
	}

	/**
	 * Calculates the inverse document frequency vector for each word in the
	 * vocabulary.
	 * 
	 * @return inverse document frequency vector of the full vocabulary, in the
	 *         given library
	 */
	private static VectorND calculateIDF() {
		double values[] = new double[manager.getVocabulary().size()];
		int i = 0;
		int libCount = manager.getLibraryCount();
		for (int j = 0; j < manager.getVocabulary().size(); j++) {
			double occurences = 0;
			for (Map.Entry<Path, VectorND> entry : data.entrySet()) {
				if (entry.getValue().getValues()[i] > THRESHOLD) {
					occurences++;
				}
			}
			
			values[i++] = Math.log10(libCount / occurences);
			System.out.println(i-1 + " " + values[i-1]);

		}
		return new VectorND(values);
	}

	/**
	 * Auxiliary method used to print the full content from the file at the given
	 * index in the similarity map to the system output.
	 * 
	 * @param index
	 *            of the file in the similarity map
	 */
	private static void typeDocument(int index) {
		Map.Entry<Double, Path> entry = null;
		Iterator<Entry<Double, Path>> iterator = similarityMap.entrySet().iterator();
		for (int i = -1; i < index; i++) {
			if (iterator.hasNext()) {
				entry = iterator.next();
			} else {
				System.out.println("Given index is not in the result set. Try again.");
				return;
			}
		}
		Path toWrite = entry.getValue();
		List<String> lines = null;
		try {
			lines = Files.readAllLines(toWrite);
		} catch (IOException e) {
			System.out.println("Error while reading file.");
			return;
		}
		System.out.println("----------------------------------------------------------------");
		System.out.println("Document: " + toWrite.toAbsolutePath().toString());
		System.out.println("----------------------------------------------------------------");
		lines.forEach(e -> System.out.println(e));
		System.out.println("----------------------------------------------------------------");
	}

	/**
	 * Auxiliary method used to display the results to the system output.
	 */
	private static void displayResults() {
		int counter = 0;
		for (Map.Entry<Double, Path> similar : similarityMap.entrySet()) {
			System.out.printf("[%d] (%.4f) %s%n", counter, similar.getKey(),
					similar.getValue().toAbsolutePath().toString());
			counter++;
			if (counter == 10) break;
		}
	}
}
