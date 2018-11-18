package hr.fer.zemris.java.webapp;

import java.io.IOException;
import java.util.List;

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
 * Servlet used to generate the Excel file based on the gathered votes.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/glasanje-xls" })
public class GlasanjeTablica extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String resultsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		List<Band> bands = Utils.getVotedBands(fileName, resultsFileName);

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		int rowNum = 0;
		for (Band band : bands) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(band.name);
			row.createCell(1).setCellValue(band.votes);
		}
		sheet.autoSizeColumn(0);

		resp.setHeader("Content-Disposition", "attachment;filename=rezultatiGlasanja.xls");
		resp.setContentType("application/octet-stream");
		workbook.write(resp.getOutputStream());
		workbook.close();
	}

}
