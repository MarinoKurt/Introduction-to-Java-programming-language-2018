package hr.fer.zemris.java.hw16.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * Utility class for the search engine.
 * 
 * @author MarinoK
 */
public class Util {

	/**
	 * Method used for reading all "words" from a file. A word is anything that gets
	 * true from Character.isAlphabetic(c).
	 * 
	 * @param file
	 *            to read the words from
	 * @return an array of strings - words
	 */
	public static String[] readAllWords(Path file) {
		String fileText;
		try {
			fileText = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Problem reading file: " + file.toString());
			return null;
		}

		Pattern p = Pattern.compile("\\s+|\\d+|\\W+", Pattern.UNICODE_CHARACTER_CLASS);
		String[] words = p.split(fileText.toLowerCase());
		return words;
	}

}
