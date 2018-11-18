package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet used for preparing results of the voting for display.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-rezultati" })
public class GlasanjeRezultati extends HttpServlet {
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Integer pollID = Integer.valueOf(req.getParameter("pollID"));

		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);

		Collections.sort(options, (b1, b2) -> Long.compare(b2.getVotesCount(), b1.getVotesCount()));

		List<PollOption> bestOptions = new ArrayList<>();
		long topVote = options.get(0).getVotesCount();
		for (PollOption option : options) {
			if (option.getVotesCount() == topVote) {
				bestOptions.add(option);
			}
		}
		req.setAttribute("pollID", pollID);
		req.setAttribute("bestOptions", bestOptions);
		req.setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
