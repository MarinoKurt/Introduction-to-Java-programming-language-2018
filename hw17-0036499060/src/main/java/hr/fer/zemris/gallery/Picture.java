package hr.fer.zemris.gallery;

import java.nio.file.Path;
import java.util.List;

/**
 * Model of a picture.
 * 
 * @author MarinoK
 */
public class Picture {

	/** File name of the picture. */
	private String fileName;

	/** Path of the file. */
	private Path path;

	/** Title of the picture. */
	private String title;

	/** Tags associated with this picture. */
	private List<String> tags;

	/**
	 * Constructor for the picture.
	 * 
	 * @param fileName
	 *            of the picture
	 * @param path
	 *            of the picture
	 * @param title
	 *            of the picture
	 * @param tags
	 *            of the picture
	 */
	public Picture(String fileName, Path path, String title, List<String> tags) {
		this.fileName = fileName;
		this.path = path;
		this.title = title;
		this.tags = tags;
	}

	/**
	 * Getter for the picture path.
	 * 
	 * @return path of the picture
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Setter for the picture path.
	 * 
	 * @param path
	 *            of the picture
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Getter for the picture title.
	 * 
	 * @return title of the picture
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for the picture title.
	 * 
	 * @param title
	 *            of the picture
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for the picture tags.
	 * 
	 * @return tags of the picture
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Setter for the picture tags.
	 * 
	 * @param tags
	 *            of the picture
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * Getter for the picture name.
	 * 
	 * @return name of the picture
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Setter for the picture name.
	 * 
	 * @param fileName
	 *            of the picture
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
