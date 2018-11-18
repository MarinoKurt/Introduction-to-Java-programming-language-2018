package hr.fer.zemris.gallery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Picture database. Used for managing a group of pictures.
 * 
 * @author MarinoK
 */
public class PictureDB {

	/** Root directory of the relevant files. */
	private String root;

	/** List of all the pictures. */
	List<Picture> pictures;

	/** Collection of all tags. */
	Collection<String> allTags;

	/**
	 * Constructor for the picture database.
	 * 
	 * @param rootPath
	 *            of the directory
	 */
	public PictureDB(String rootPath) {
		root = rootPath;
		pictures = new ArrayList<>();
		allTags = new TreeSet<>();
		initialize();
	}

	/**
	 * Getter for the tags.
	 * 
	 * @return list of tags
	 */
	public List<String> getTags() {
		return new ArrayList<String>(allTags);
	}

	/**
	 * Fetches the picture under the given name.
	 * 
	 * @param name
	 *            of the picture
	 * @return picture
	 */
	public Picture getForName(String name) {
		for (Picture p : pictures) {
			if (p.getFileName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Fetches all the pictures that are associated with the given tag.
	 * 
	 * @param tag
	 *            to search for
	 * @return list of pictures
	 */
	public List<Picture> getForTag(String tag) {
		List<Picture> filtered = new ArrayList<>();
		for (Picture p : pictures) {
			if (p.getTags().contains(tag)) {
				filtered.add(p);
			}
		}
		return filtered;
	}

	/**
	 * Auxiliary method to initialize the database from the files in the project.
	 */
	private void initialize() {
		Path descriptions = Paths.get(root + "/opisnik.txt");
		List<String> descs = null;

		try {
			descs = Files.readAllLines(descriptions);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String fileName = null;
		String title = null;
		List<String> tags = null;

		for (int i = 0, n = descs.size(); i < n; i++) {
			switch (i % 3) {
			case 0:
				fileName = descs.get(i);
				break;
			case 1:
				title = descs.get(i);
				break;
			case 2:
				tags = Arrays.asList(descs.get(i).split(","));
				tags.replaceAll(String::trim);
				allTags.addAll(tags);
			default:
				if (fileName != null && title != null && tags != null) {
					Path path = Paths.get(root + "/slike/" + fileName);
					pictures.add(new Picture(fileName, path, title, tags));
				}
			}
		}

	}

}
