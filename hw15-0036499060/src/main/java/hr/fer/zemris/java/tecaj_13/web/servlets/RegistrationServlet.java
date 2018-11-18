package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.RegistrationForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet used for registrating new users.
 * 
 * @author MarinoK
 */
@WebServlet("/servleti/register")
public class RegistrationServlet extends HttpServlet {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/RegistrationForm.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	/**
	 * Auxiliary method used to process any post or get request.
	 * 
	 * @param req
	 *            request
	 * @param resp
	 *            response
	 * @throws ServletException
	 *             general exception a servlet can throw when it encounters
	 *             difficulty
	 * @throws IOException
	 *             if there is a problem with communication
	 */
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		if (req.getSession().getAttribute("current.user.nick") != null) {
			req.setAttribute("message", "You can't register while logged in!");
			req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
			return;
		}

		String method = req.getParameter("method");
		if (!"Submit".equals(method)) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}

		RegistrationForm form = new RegistrationForm();
		form.fillFromHttpRequest(req);
		form.validate();

		if (form.hasAnyMissteps()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/RegistrationForm.jsp").forward(req, resp);
			return;
		}
		BlogUser user = new BlogUser();
		form.fillIntoUser(user);
		DAOProvider.getDAO().storeUser(user);

		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

}
