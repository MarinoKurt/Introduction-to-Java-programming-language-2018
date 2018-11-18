package hr.fer.zemris.java.hw16.vocabulary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw16.vector.VectorBuilder;
import hr.fer.zemris.java.hw16.vector.VectorND;

/**
 * Manager for the vocabulary. All the communication with the vocabulary goes
 * through this class.
 * 
 * @author MarinoK
 */
public class VocabularyManager {

	/**
	 * Vocabulary builder.
	 */
	private VocabularyBuilder builder;

	/**
	 * Vector builder.
	 */
	private VectorBuilder processor;

	/**
	 * True, if the vocabulary is initialized.
	 */
	private boolean vocabularyReady;

	/**
	 * True, if the vectors are initialized.
	 */
	private boolean vectorsReady;

	/**
	 * Root of the library folder.
	 */
	private Path libraryRoot;

	/**
	 * Constructor for the vocabulary manager.
	 * 
	 * @param libraryRoot
	 *            of the library folder
	 * @param stopwords
	 *            to ignore while building the vocabulary
	 * @throws IOException
	 *             if there is a problem walking the file tree
	 */
	public VocabularyManager(Path libraryRoot, List<String> stopwords) throws IOException {
		this.builder = new VocabularyBuilder(stopwords);
		this.libraryRoot = libraryRoot;
		prepareVocabulary();
	}

	/**
	 * Auxiliary method that prepares the vocabulary using the vocabulary builder.
	 * 
	 * @throws IOException
	 *             if there is a problem walking the file tree
	 */
	public void prepareVocabulary() throws IOException {
		if (builder == null) throw new UnsupportedOperationException();
		Files.walkFileTree(libraryRoot, builder);
		vocabularyReady = true;
	}

	/**
	 * Getter for the vocabulary.
	 * 
	 * @return vocabulary of the library
	 * 
	 * @throws UnsupportedOperationException
	 *             if the vocabulary is not yet initialized
	 */
	public List<String> getVocabulary() {
		if (!vocabularyReady) throw new UnsupportedOperationException();
		return builder.getVocabulary();
	}

	/**
	 * Getter for the gathered data.
	 * 
	 * @return data map, path of the file mapped with a vector of frequency for
	 *         every word in the vocabulary in the given file.
	 */
	public Map<Path, VectorND> getData() {
		if (!vectorsReady) throw new UnsupportedOperationException();
		return processor.getData();
	}

	/**
	 * Method used to calculate the library document vectors using the vector
	 * builder.
	 * 
	 * @throws IOException
	 *             if there is a problem walking the file tree
	 */
	public void calculateDocumentVectors() throws IOException {
		this.processor = new VectorBuilder(getVocabulary());
		Files.walkFileTree(this.libraryRoot, this.processor);
		vectorsReady = true;
	}

	/**
	 * Auxiliary method used to calculate the vector for the given document.
	 * 
	 * @param document
	 *            to calculate the vector for, in form of a list of lines
	 * @return frequency of every word in the vocabulary in the given document
	 */
	public VectorND calculateVectors(List<String> document) {
		return this.processor.calculateVector(document);
	}

	/**
	 * Getter for the library count.
	 * 
	 * @return number of documents in the library
	 */
	public int getLibraryCount() {
		return this.builder.getLibraryCount();
	}

}
