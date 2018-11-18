package hr.fer.zemris.gallery.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.gallery.Picture;
import hr.fer.zemris.gallery.PictureDBProvider;

/**
 * Servlet serves a thumbnail based on the given parameter name. If there is no
 * thumbnail under a given name, servlet produces it.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/servlets/thumbnail" })
public class ThumbnailServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Default thumbnail size, both witdh and height. */
	private static final int THUMBNAIL_SIZE = 150;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = req.getParameter("name");
		if (name != null) {
			name = name.trim();
			if (name.isEmpty()) {
				name = null;
			}
		}

		String thumbFolder = req.getServletContext().getRealPath("/WEB-INF/thumbnails");
		Path thumbnailLocation = Paths.get(thumbFolder);
		if (!Files.exists(thumbnailLocation)) {
			Files.createDirectories(thumbnailLocation);
		}

		Picture pic = PictureDBProvider.getDB().getForName(name);

		Path thumbnailPath = Paths.get(thumbFolder + "/" + pic.getFileName());

		if (!thumbnailPath.toFile().exists()) {
			BufferedImage img = ImageIO.read(pic.getPath().toFile());
			BufferedImage thumbnail = toBufferedImage(
					img.getScaledInstance(THUMBNAIL_SIZE, THUMBNAIL_SIZE, Image.SCALE_DEFAULT));

			ImageIO.write(thumbnail, "jpg", thumbnailPath.toFile());
		}

		byte[] image = Files.readAllBytes(thumbnailPath);

		resp.setContentType("image/png");
		resp.setContentLength(image.length);
		resp.getOutputStream().write(image);
		resp.getOutputStream().flush();
	}

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img
	 *            The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

}
