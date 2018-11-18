package hr.fer.zemris.java.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to change the background color.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/setcolor" })
public class ColorServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String selectedColor = req.getParameter("color");
		String color = null;
		if (selectedColor != null) {
			switch (selectedColor) {
			case "red":
				color = "#ff003f";
				break;
			case "green":
				color = "#74fc86";
				break;
			case "cyan":
				color = "#00ffff";
				break;
			default:
				color = "#ffffff";
			}
			req.getSession().setAttribute("pickedBgCol", color);
		}

		req.getRequestDispatcher("/colors.jsp").forward(req, resp);

	}

}
