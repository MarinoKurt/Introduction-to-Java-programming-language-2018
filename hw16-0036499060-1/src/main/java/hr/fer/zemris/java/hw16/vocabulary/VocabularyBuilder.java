package hr.fer.zemris.java.hw16.vocabulary;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.util.Util;

/**
 * File visitor designed to build the vocabulary from the given root of the
 * library.
 * 
 * @author MarinoK
 */
public class VocabularyBuilder extends SimpleFileVisitor<Path> {

	/** All the words from the documents that are not stopwords. */
	private List<String> vocabulary;

	/** List of stopwords to ignore when building the vocabulary. */
	private List<String> stopwords;

	/** Counts the number of documents in the library. */
	private int libraryCount;

	/**
	 * Constructor for the vocabulary builder.
	 * 
	 * @param stopwords
	 *            to ignore while building
	 */
	public VocabularyBuilder(List<String> stopwords) {
		this.stopwords = stopwords;
		this.vocabulary = new ArrayList<>();
		this.libraryCount = 0;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
		libraryCount++;
		String[] words = Util.readAllWords(file);
		for (String word : words) {
			if (!stopwords.contains(word) && !word.isEmpty() && !vocabulary.contains(word)) {
				vocabulary.add(word);
			}
		}
		return FileVisitResult.CONTINUE;
	}

	/**
	 * Getter for the vocabulary.
	 * 
	 * @return vocabulary
	 */
	public List<String> getVocabulary() {
		return vocabulary;
	}

	/**
	 * Getter for the library count.
	 * 
	 * @return number of documents in the library
	 */
	public int getLibraryCount() {
		return libraryCount;
	}
}
