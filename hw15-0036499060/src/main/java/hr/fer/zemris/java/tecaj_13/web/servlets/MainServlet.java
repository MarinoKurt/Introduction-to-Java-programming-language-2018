package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.LoginForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet for the home page of the blog. Used to login to the blog.
 * 
 * @author MarinoK
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
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

		List<BlogUser> authors = DAOProvider.getDAO().getUsers();
		req.setAttribute("authors", authors);

		String method = req.getParameter("method");
		if (!"Submit".equals(method)) {
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		}

		LoginForm form = new LoginForm();
		form.fillFromHttpRequest(req);
		form.validate();

		if (form.hasAnyMissteps()) {
			req.setAttribute("attemptedLogin", form);
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		}

		req.getSession().setAttribute("current.user.nick", form.getNick());
		BlogUser currentUser = DAOProvider.getDAO().getBlogUser(form.getNick());
		req.getSession().setAttribute("current.user.id", currentUser.getId());
		req.getSession().setAttribute("current.user.fn", currentUser.getFirstName());
		req.getSession().setAttribute("current.user.ln", currentUser.getLastName());
		req.getSession().setAttribute("current.user.em", currentUser.getEmail());

		resp.sendRedirect(req.getContextPath() + "/servleti/main");
		return;
	}

}
