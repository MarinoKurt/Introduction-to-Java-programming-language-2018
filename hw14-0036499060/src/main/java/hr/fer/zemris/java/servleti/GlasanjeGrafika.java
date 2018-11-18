package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.util.List;

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

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet used for preparing graphical representation of voting data.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-grafika" })
public class GlasanjeGrafika extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int pollID = Integer.valueOf(req.getParameter("pollID"));
		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);

		JFreeChart chart = createChart(createDataset(options), DAOProvider.getDao().getPoll(pollID).getTitle());
		byte[] image = ChartUtilities.encodeAsPNG(chart.createBufferedImage(500, 300));

		resp.setContentType("image/png");
		resp.setContentLength(image.length);
		resp.getOutputStream().write(image);
		resp.getOutputStream().flush();
	}

	/**
	 * Auxiliary method used to create the dataset for the graphical representation.
	 * 
	 * @param options
	 *            list of poll options
	 * 
	 * @return dataset
	 */
	private PieDataset createDataset(List<PollOption> options) {
		DefaultPieDataset result = new DefaultPieDataset();
		int voteSum = 0;
		for (PollOption o : options) {
			voteSum += o.getVotesCount();
		}

		for (PollOption o : options) {
			result.setValue(o.getOptionTitle(), o.getVotesCount() / (double) voteSum);
		}
		return result;
	}

	/**
	 * Auxiliary method used to create the chart from the given dataset.
	 * 
	 * @param dataset
	 *            of the voting
	 * @param title
	 *            of the chart
	 * @return chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}

}