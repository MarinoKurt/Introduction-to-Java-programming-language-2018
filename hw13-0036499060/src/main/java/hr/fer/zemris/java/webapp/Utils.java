package hr.fer.zemris.java.webapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for the voting application.
 * 
 * @author MarinoK
 */
public class Utils {

	/**
	 * Method used to load the bands from the "database".
	 * 
	 * @param fileName
	 *            of the bands source file
	 * @return list of bands
	 */
	public static List<Band> getBands(String fileName) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		List<Band> bands = new ArrayList<>();

		for (String line : lines) {
			String[] info = line.split("\t");
			if (info.length != 3) continue;
			bands.add(new Band(Integer.valueOf(info[0]), info[1], info[2]));
		}
		return bands;
	}

	/**
	 * Method used to load the bands from the "database".
	 * 
	 * @param fileName
	 *            of the bands source file
	 * @param resultsFileName
	 *            of the votes source file
	 * @return list of bands
	 * @throws IOException
	 *             if there is a problem with communication
	 */
	public static List<Band> getVotedBands(String fileName, String resultsFileName) throws IOException {

		List<Band> bands = getBands(fileName);

		Path resultsPath = Paths.get(resultsFileName);
		if (!resultsPath.toFile().exists()) {
			resultsPath.toFile().createNewFile();
		}

		List<String> readLines = Files.readAllLines(resultsPath);
		for (Band x : bands) {
			x.setVotes(0);
		}

		Collections.sort(bands, (b1, b2) -> Integer.compare(b1.id, b2.id));

		for (String line : readLines) {
			String[] numbers = line.split("\t");
			int lineID = Integer.valueOf(numbers[0]);
			int lineVotes = Integer.valueOf(numbers[1]);
			if (bands.size() >= lineID - 1) {
				bands.get(lineID - 1).setVotes(lineVotes);
			}
		}

		Collections.sort(bands, (b1, b2) -> Integer.compare(b2.getVotes(), b1.getVotes()));

		return bands;
	}

}
