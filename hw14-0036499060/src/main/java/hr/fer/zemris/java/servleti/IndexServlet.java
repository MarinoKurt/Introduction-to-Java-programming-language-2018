package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * Servlet which prepares the polls attribute for the homepage of the
 * application.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/servleti/index.html" })
public class IndexServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Poll> polls = DAOProvider.getDao().getPolls();

		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
