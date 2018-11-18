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
 * Servlet that calculates the trigonometric values of integers between the two
 * given parameters.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns={"/trigonometric"})
public class TrigonometricServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String aString = null;
		String bString = null;

		aString = req.getParameter("a");
		bString = req.getParameter("b");

		if (aString == null) {
			aString = "0";
		}
		if (bString == null) {
			bString = "360";
		}

		int a = 0;
		int b = 360;

		try {
			a = Integer.valueOf(aString);
		} catch (NumberFormatException ax) {
			a = 0;
		}

		try {
			b = Integer.valueOf(bString);
		} catch (NumberFormatException bx) {
			b = 360;
		}

		if (a > b) {
			int c = a;
			a = b;
			b = c;
		}

		if (b > a + 720) {
			b = a + 720;
		}

		List<Trigonomentry> data = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			data.add(new Trigonomentry(i));
		}

		req.getSession().setAttribute("data", data);
		req.getSession().setAttribute("a", a);
		req.getSession().setAttribute("b", b);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);

	}

	/**
	 * Represents a trigonometric entry. I do apologize for the bad pun.
	 */
	public static class Trigonomentry {
		/** Value of angles sin. */
		private double sin;
		/** Value of angles cos. */
		private double cos;
		/** Value of angle. */
		private int angle;

		/**
		 * Constructor for the trigonometric entry.
		 * 
		 * @param angle
		 *            of the entry
		 */
		public Trigonomentry(int angle) {
			this.angle = angle;
			double rad = angle * Math.PI / (double) 180;
			this.sin = Math.sin(rad);
			this.cos = Math.cos(rad);
		}

		/**
		 * @return sin
		 */
		public double getSin() {
			return sin;
		}

		/**
		 * @return cos
		 */
		public double getCos() {
			return cos;
		}

		/**
		 * @return angle
		 */
		public int getAngle() {
			return angle;
		}
	}

}