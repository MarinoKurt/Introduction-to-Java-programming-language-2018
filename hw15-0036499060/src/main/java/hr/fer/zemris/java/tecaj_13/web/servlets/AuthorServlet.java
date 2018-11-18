package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.CommentForm;
import hr.fer.zemris.java.tecaj_13.forms.EntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet for the author, entry, new and edit pages.
 * 
 * @author MarinoK
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
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
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.isEmpty() || pathInfo.length() == 1) {
			req.setAttribute("message", "Provide a URL containing .../author/NICK/ENTRY");
			req.getRequestDispatcher("/WEB-INF/pages/Notification.jsp").forward(req, resp);
			return;
		}
		String[] parameters = pathInfo.substring(1).split("/");

		if (parameters.length > 3) {
			req.setAttribute("message", "Too much URL parameters. Try with: author/NICK/ENTRY");
			req.getRequestDispatcher("/WEB-INF/pages/Notification.jsp").forward(req, resp);
			return;
		}

		String authorNick = parameters[0];
		BlogUser author = DAOProvider.getDAO().getBlogUser(authorNick);
		req.setAttribute("author", author);
		if (parameters.length == 1) {
			req.getRequestDispatcher("/WEB-INF/pages/Author.jsp").forward(req, resp);
			return;
		}

		String parameter = parameters[1];
		if (parameter.matches("new")) {
			authorize(authorNick, req, resp);
			req.setAttribute("type", "new");
			processNew(author, req, resp);
			return;
		}

		Long eID = 0L;
		try {
			eID = Long.valueOf(parameter);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Entry ID must be a non negative number.");
			req.getRequestDispatcher("/WEB-INF/pages/Notification.jsp").forward(req, resp);
			return;
		}

		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(eID);
		checkIfNull(entry, req, resp);
		req.setAttribute("blogEntry", entry);
		if (parameters.length == 2) {
			String method = req.getParameter("method");
			if ("Comment".equals(method)) {

				CommentForm cform = new CommentForm();
				cform.fillFromHttpRequest(req);
				cform.validate();

				if (cform.hasAnyMissteps()) {
					req.setAttribute("comment", cform);
					req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
					return;
				}
				BlogComment comment = new BlogComment();
				comment.setMessage(cform.getMessage());
				comment.setBlogEntry(entry);
				comment.setPostedOn(new Date());

				String email = (String) req.getSession().getAttribute("current.user.em");
				if (email == null) {
					email = cform.getEmail();
				}

				comment.setUsersEMail(email);
				DAOProvider.getDAO().storeComment(comment);
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" + authorNick + "/" + entry.getId());
				return;
			}
			req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
			return;
		}

		String command = parameters[2];
		if (command.matches("edit")) {
			authorize(authorNick, req, resp);
			req.setAttribute("type", "edit");
			processEdit(entry, req, resp);
			return;
		}

		req.setAttribute("message", "Well done! Weird error you've achieved.");
		req.getRequestDispatcher("/WEB-INF/pages/Notification.jsp").forward(req, resp);

	}

	/**
	 * @param entry
	 *            to edit
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
	private void processEdit(BlogEntry entry, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		EntryForm entryForm = new EntryForm();
		entryForm.setText(entry.getText());
		entryForm.setTitle(entry.getTitle());

		String method = req.getParameter("method");
		if (!"Submit".equals(method)) {
			req.setAttribute("entryForm", entryForm);
			if ("Cancel".equals(method)) {
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" + entry.getCreator().getNick() + "/"
						+ entry.getId());
				return;
			} else {
				req.getRequestDispatcher("/WEB-INF/pages/EditEntry.jsp").forward(req, resp);
				return;
			}
		}
		entryForm.fillFromHttpRequest(req);
		entryForm.validate();

		if (entryForm.hasAnyMissteps()) {
			req.setAttribute("entryForm", entryForm);
			req.getRequestDispatcher("/WEB-INF/pages/EditEntry.jsp").forward(req, resp);
			return;
		}

		entryForm.fillIntoEntry(entry);
		entry.setLastModifiedAt(new Date());
		System.out.println(entry.getText());
		System.out.println(entry.getLastModifiedAt());

		resp.sendRedirect(
				req.getContextPath() + "/servleti/author/" + entry.getCreator().getNick() + "/" + entry.getId());
		return;

	}

	/**
	 * @param author
	 *            of the new entry
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
	private void processNew(BlogUser author, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String method = req.getParameter("method");
		if (!"Submit".equals(method)) {
			req.getRequestDispatcher("/WEB-INF/pages/EditEntry.jsp").forward(req, resp);
			return;
		}
		EntryForm entryForm = new EntryForm();
		entryForm.fillFromHttpRequest(req);
		entryForm.validate();

		if (entryForm.hasAnyMissteps()) {
			req.setAttribute("entryForm", entryForm);
			req.getRequestDispatcher("/WEB-INF/pages/EditEntry.jsp").forward(req, resp);
			return;
		}

		BlogEntry createdEntry = new BlogEntry();
		entryForm.fillIntoEntry(createdEntry);
		createdEntry.setCreatedAt(new Date());
		createdEntry.setLastModifiedAt(createdEntry.getCreatedAt());
		createdEntry.setCreator(author);

		createdEntry = DAOProvider.getDAO().storeEntry(createdEntry);

		req.setAttribute("message", "Successfully stored!");
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + author.getNick());
		return;
	}

	/**
	 * Auxiliary method checks whether the given object is null, and forwards the
	 * request to the dedicated page.
	 * 
	 * @param entry
	 *            to check
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
	private void checkIfNull(Object entry, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (entry != null) return;
		req.setAttribute("message", "Non-existing user or post.");
		req.getRequestDispatcher("/WEB-INF/pages/Notification.jsp").forward(req, resp);
	}

	/**
	 * Auxiliary method used to check whether the user is authorized for the
	 * requested action.
	 * 
	 * @param authorNick
	 *            of the owner
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
	private void authorize(String authorNick, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String currentUser = (String) req.getSession().getAttribute("current.user.nick");
		if (currentUser == null || !currentUser.equals(authorNick)) {
			req.setAttribute("message",
					"You do not have the authorization to edit or create a new entry on this blog.");
			req.getRequestDispatcher("/WEB-INF/pages/Notification.jsp").forward(req, resp);
		}
	}
}
