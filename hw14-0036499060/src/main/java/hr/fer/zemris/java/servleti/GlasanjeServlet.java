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
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet used to fetch all the bands that the user can vote for, and prepare
 * the data.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/servleti/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int pollID = Integer.valueOf(req.getParameter("pollID"));
		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
		Poll chosenOne = DAOProvider.getDao().getPoll(pollID);
		
		req.setAttribute("poll", chosenOne);
		req.setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIzbor.jsp").forward(req, resp);
	}

}
