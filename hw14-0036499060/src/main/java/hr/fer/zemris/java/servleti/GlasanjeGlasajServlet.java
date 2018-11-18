package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * Servlet used for remembering users vote.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String votedID = req.getParameter("id");
		long id = Long.valueOf(votedID);

		long pollID = DAOProvider.getDao().getPollOption(id).getPollID();

		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement updateSt;
		try {
			updateSt = con.prepareStatement("UPDATE pollOptions SET votesCount = votesCount+1  WHERE id = ?");
			updateSt.setLong(1, id);
			updateSt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}

}
