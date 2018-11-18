package hr.fer.zemris.java.hw16.jvdraw.export;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Representation of a jvd file.
 * 
 * @author MarinoK
 */
public class FileModel {

	/** Path of the file. */
	private Path path;

	/**
	 * Loads the object in given lines into the given model. Assumes the path is
	 * set.
	 * 
	 * @param model
	 *            to load the objects into
	 * @param lines
	 *            containing objects representation
	 */
	public void load(DrawingModel model, List<String> lines) {
		List<GeometricalObject> objects = JVDParser.readObjects(lines);
		objects.forEach(o -> model.add(o));
	}

	/**
	 * Saves the objects from the current model to a file. Assumes the path is set.
	 * 
	 * @param model
	 *            to save from
	 * @throws IOException
	 *             if there is a problem with communication
	 */
	public void save(DrawingModel model) throws IOException {
		TextCollector collector = new TextCollector();
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(collector);
		}
		String text = collector.getText();
		if (!path.toString().endsWith(".jvd")) {
			path = Paths.get(path.toString().concat(".jvd"));
		}
		Files.write(path, text.getBytes(), StandardOpenOption.CREATE);
	}

	/**
	 * Getter for the path of the file.
	 * 
	 * @return path of the file
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Getter for the path of the file.
	 * 
	 * @param path
	 *            of the file
	 */
	public void setPath(Path path) {
		this.path = path;
	}

}
