package hr.fer.zemris.java.hw16.vector;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw16.util.Util;

/**
 * File visitor in charge of building a data map: path of the file mapped with a
 * vector of frequency for every word in the vocabulary in the given file.
 * 
 * @author MarinoK
 *
 */
public class VectorBuilder extends SimpleFileVisitor<Path> {

	/**
	 * Vocabulary of the library.
	 */
	private List<String> vocabulary;

	/**
	 * Path of the file mapped with a vector of frequency for every word in the
	 * vocabulary in the given file.
	 */
	private Map<Path, VectorND> data;

	/**
	 * Constructor for the vector builder.
	 * 
	 * @param vocabulary
	 *            of the library
	 */
	public VectorBuilder(List<String> vocabulary) {
		this.vocabulary = vocabulary;
		this.data = new HashMap<>();
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {

		String[] words = Util.readAllWords(file);
		VectorND vector = calculateVector(Arrays.asList(words));
		data.put(file, vector);

		return FileVisitResult.CONTINUE;
	}

	/**
	 * Getter for the data map.
	 * 
	 * @return path of the file mapped with a vector of frequency for every word in
	 *         the vocabulary in the given file.
	 */
	public Map<Path, VectorND> getData() {
		return data;
	}

	/**
	 * Auxiliary method used to calculate the vector for the given document.
	 * 
	 * @param document
	 *            to calculate the vector for, in form of a list of lines
	 * @return frequency of every word in the vocabulary in the given document
	 */
	public VectorND calculateVector(List<String> document) {
		double[] values = new double[vocabulary.size()];
		for (String word : document) {
			int wordIndex = vocabulary.indexOf(word);
			if (wordIndex >= 0) {
				values[wordIndex]++;
			}
		}
		return new VectorND(values);
	}

}
