package hr.fer.zemris.java.webapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for remembering users vote.
 * 
 * @author MarinoK
 */
@WebServlet(urlPatterns = { "/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String votedID = (String) req.getParameter("id");
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		synchronized (this) {

			Path resultsPath = Paths.get(fileName);
			if (!resultsPath.toFile().exists()) {
				resultsPath.toFile().createNewFile();
			}
			boolean createLine = true;
			List<String> linesToWrite = new ArrayList<>();

			try {
				BufferedReader br = new BufferedReader(new FileReader(resultsPath.toFile()));
				String line = br.readLine();
				while (line != null) {
					if (line.startsWith(votedID)) {
						String[] pair = line.split("\t");
						if (pair.length != 2) {
							line = br.readLine();
							continue;
						}
						line = pair[0] + "\t" + String.valueOf(Integer.valueOf(pair[1]) + 1);
						createLine = false;
					}
					linesToWrite.add(line);
					line = br.readLine();
				}
				br.close();
				BufferedWriter out = new BufferedWriter(new FileWriter(resultsPath.toFile()));
				for (String s : linesToWrite) {
					if (s.trim().length() == 0) continue;
					out.write(s + "\n");
				}

				if (createLine) {
					out.write(votedID + "\t" + 1 + "\n");
				}
				out.flush();
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

}
