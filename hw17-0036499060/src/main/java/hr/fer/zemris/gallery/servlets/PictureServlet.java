package hr.fer.zemris.gallery.servlets;

import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.gallery.Picture;
import hr.fer.zemris.gallery.PictureDBProvider;

/**
 * Servlet used for fetching full sized pictures.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/servlets/picture" })
public class PictureServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = req.getParameter("name");
		if (name != null) {
			name = name.trim();
			if (name.isEmpty()) {
				name = null;
			}
		}
		Picture pic = PictureDBProvider.getDB().getForName(name);

		byte[] image = Files.readAllBytes(pic.getPath());

		resp.setContentType("image/png");
		resp.setContentLength(image.length);
		resp.getOutputStream().write(image);
		resp.getOutputStream().flush();
	}
}
