package hr.fer.zemris.java.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Servlet used to prepare a Excel file containing n pages. On page i is a table
 * with two columns. The first column contains integer numbers from parameter a
 * to parameter b. The second column contains i-th powers of these numbers.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/powers" })
public class PowerServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String aS = req.getParameter("a");
		String bS = req.getParameter("b");
		String nS = req.getParameter("n");

		if (nS == null || aS == null || bS == null) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		int a = Integer.valueOf(aS);
		int b = Integer.valueOf(bS);
		int n = Integer.valueOf(nS);

		if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		if (a > b) {
			int c = a;
			a = b;
			b = c;
		}
		resp.setHeader("Content-Disposition", "attachment;filename=powers.xls");
		resp.setContentType("application/octet-stream");
		generateTable(a, b, n, resp);

	}

	/**
	 * Auxiliary method used to generate and send the Excel file.
	 * 
	 * @param a
	 *            lower bound
	 * @param b
	 *            upper bound
	 * @param n
	 *            number of sheets
	 * @param resp
	 *            response of the servlet
	 * @throws IOException
	 *             if there is a problem with communication
	 */
	private void generateTable(int a, int b, int n, HttpServletResponse resp) throws IOException {
		Workbook workbook = new HSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		for (int i = 1; i <= n; i++) {
			Sheet sheet = workbook.createSheet("Sheet number " + String.valueOf(i));
			int rowNum = 0;
			for (int j = a; j <= b; j++) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}
		workbook.write(resp.getOutputStream());
		workbook.close();
	}
}
