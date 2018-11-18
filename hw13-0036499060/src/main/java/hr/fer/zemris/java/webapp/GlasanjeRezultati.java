package hr.fer.zemris.java.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for preparing results of the voting for display.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/glasanje-rezultati" })
public class GlasanjeRezultati extends HttpServlet {
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String resultsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		List<Band> bands = Utils.getVotedBands(fileName, resultsFileName);

		List<Band> bestBands = new ArrayList<>();
		int topVote = bands.get(0).votes;
		for (Band band : bands) {
			if (band.votes == topVote) {
				bestBands.add(band);
			}
		}

		req.getSession().setAttribute("bestBands", bestBands);
		req.getSession().setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
