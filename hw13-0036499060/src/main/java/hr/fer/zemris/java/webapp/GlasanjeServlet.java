package hr.fer.zemris.java.webapp;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to fetch all the bands that the user can vote for, and prepare
 * the data.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<Band> bands = Utils.getBands(fileName);
		
		req.getSession().setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
