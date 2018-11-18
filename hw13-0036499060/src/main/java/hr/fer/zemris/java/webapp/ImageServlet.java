package hr.fer.zemris.java.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Servlet used to prepare a image of OS usage as a pie chart.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/reportImage" })
public class ImageServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		JFreeChart chart = createChart(createDataset(), "OS usage");

		byte[] image = ChartUtilities.encodeAsPNG(chart.createBufferedImage(500, 300));

		resp.setContentType("image/png");
		resp.setContentLength(image.length);
		resp.getOutputStream().write(image);
		resp.getOutputStream().flush();
	}

	/**
	 * Auxiliary method used to create the dataset for the chart.
	 * 
	 * @return dataset
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 1);
		result.setValue("Mac", 1);
		result.setValue("Windows", 98);
		return result;
	}

	/**
	 * Auxiliary method used to create the chart from the given dataset.
	 * 
	 * @param dataset
	 *            of the chart
	 * @param title
	 *            of the chart
	 * @return the chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}

}