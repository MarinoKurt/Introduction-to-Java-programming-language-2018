package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Listener assigned to connect to the database with the credentials given in
 * the properties file. Creates the needed tables at startup, if they don't
 * already exist.
 * 
 * @author MarinoK
 *
 */
@WebListener
public class InitializationListener implements ServletContextListener {

	/** Actual root path of the web application. */
	private static String actualRootPath;

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Properties dbProperties = new Properties();
		String configFile = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
		actualRootPath = sce.getServletContext().getRealPath(".");
		try {
			dbProperties.load(Files.newInputStream(Paths.get(configFile)));
		} catch (IOException e) {
			throw new RuntimeException("Configuration data missing from the properties file.");
		}

		String dbName = String.valueOf(dbProperties.get("name"));
		String port = String.valueOf(dbProperties.get("port"));
		String username = String.valueOf(dbProperties.get("user"));
		String password = String.valueOf(dbProperties.get("password"));
		String host = String.valueOf(dbProperties.get("host"));

		if (dbName == null || port == null || username == null || password == null || host == null) {
			throw new RuntimeException("Configuration data missing from the properties file.");
		}

		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + username + ";password="
				+ password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();

		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		Connection con = null;
		try {
			con = cpds.getConnection();
			prepareDB(con);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException ignorable) {
				ignorable.printStackTrace();
			}
		}

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Auxiliary method used to prepare the database for the application; insert
	 * needed data and create the tables, if they don't already exist.
	 * 
	 * @param con
	 *            connection
	 * @throws IOException
	 *             if there is a problem with communication
	 * @throws SQLException
	 *             if there is a problem with the database
	 */
	private static void prepareDB(Connection con) throws IOException, SQLException {

		ArrayList<String> existingTables = getTablesList(con);

		// creating tables only if they aren't already created

		if (!existingTables.contains("POLLS")) {
			PreparedStatement createFirstTable = con
					.prepareStatement("CREATE TABLE Polls(id BIGINT PRIMARY KEY GENERATED ALWAYS "
							+ "AS IDENTITY,title VARCHAR(150) NOT NULL,message CLOB(2048) NOT NULL)");
			createFirstTable.executeUpdate();
		}

		if (!existingTables.contains("POLLOPTIONS")) {
			PreparedStatement createSecondTable = con.prepareStatement(
					"CREATE TABLE PollOptions(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,optionTitle "
							+ "VARCHAR(100) NOT NULL,optionLink VARCHAR(150) NOT NULL,pollID BIGINT,votesCount BIGINT,"
							+ "FOREIGN KEY (pollID) REFERENCES Polls(id))");
			createSecondTable.executeUpdate();
		}

		// inserting only the data that isn't already inserted

		String selectPollsQuery = "SELECT * FROM POLLS";
		Statement getAllPolls = con.createStatement();
		ResultSet allPolls = getAllPolls.executeQuery(selectPollsQuery);

		String bandPollTitle = "Glasanje za omiljeni bend";
		PreparedStatement pollStatement = con.prepareStatement("INSERT into Polls (title, message) VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		boolean write = true;
		ResultSet results = null;
		while (allPolls.next()) {
			if (allPolls.getString("title").equalsIgnoreCase(bandPollTitle)) {
				write = false;
			}
		}
		if (write) {
			pollStatement.setString(1, bandPollTitle);
			pollStatement.setString(2,
					"Koji Vam je najdraži od sljedećih bendova? Kliknite na link kako biste glasali!");
			pollStatement.executeUpdate();
			results = pollStatement.getGeneratedKeys();
		}

		long pollID = getPollID(con, results, bandPollTitle);
		insertPollOptions("bands.data", pollID, con);

		String hwPollTitle = "Glasanje za omiljenu zadaću";
		write = true;
		results = null;
		allPolls = getAllPolls.executeQuery(selectPollsQuery);
		while (allPolls.next()) {
			if (allPolls.getString("title").equalsIgnoreCase(hwPollTitle)) {
				write = false;
			}
		}
		if (write) {
			pollStatement.setString(1, hwPollTitle);
			pollStatement.setString(2,
					"Koja Vam je omiljena od sljedećih zadaća? Kliknite na link kako biste glasali!");
			pollStatement.executeUpdate();
			results = pollStatement.getGeneratedKeys();
		}

		pollID = getPollID(con, results, hwPollTitle);
		insertPollOptions("hws.data", pollID, con);

	}

	/**
	 * Auxiliary method which fetches the poll ID to be assigned to poll options.
	 * 
	 * @param con
	 *            connection
	 * @param results
	 *            former result set
	 * @param title
	 *            of the poll
	 * @return poll ID
	 * @throws SQLException
	 *             if there is a problem accessing database
	 */
	private static long getPollID(Connection con, ResultSet results, String title) throws SQLException {
		long pollID = 0;
		if (results != null && results.next()) {
			pollID = results.getLong(1);
		} else {
			Statement pollIDStatement = con.createStatement();
			ResultSet pollIDRes = pollIDStatement.executeQuery("SELECT * FROM POLLS WHERE TITLE = '" + title + "'");
			if (pollIDRes.next()) {
				pollID = pollIDRes.getLong(1);
			}
		}
		return pollID;
	}

	/**
	 * Auxiliary method used to insert poll options read from a file.
	 * 
	 * @param path
	 *            of the file containing poll data
	 * @param pollID
	 *            of the parenting poll
	 * @param con
	 *            connection
	 * @throws SQLException
	 *             if there is a problem accessing database
	 * @throws IOException
	 *             if there is a problem with reading the file
	 */
	private static void insertPollOptions(String path, long pollID, Connection con) throws SQLException, IOException {
		List<String> options = Files.readAllLines(Paths.get(actualRootPath + "/WEB-INF/" + path));
		PreparedStatement pollOptionStatement = con.prepareStatement(
				"INSERT into PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);

		for (String optionInfo : options) {
			String[] splittedInfo = optionInfo.split("\t");
			boolean writeOption = true;
			ResultSet existingOptions = con.createStatement().executeQuery("SELECT * FROM POLLOPTIONS");
			while (existingOptions.next()) {
				String former = existingOptions.getString("optionTitle");
				if (former.equalsIgnoreCase(splittedInfo[0])) {
					writeOption = false;
				}
			}
			if (writeOption) {
				pollOptionStatement.setString(1, splittedInfo[0]);
				pollOptionStatement.setString(2, splittedInfo[1]);
				pollOptionStatement.setLong(3, pollID);
				pollOptionStatement.setString(4, splittedInfo[2]);
				pollOptionStatement.executeUpdate();
			}
		}
	}

	/**
	 * Method fetches the list of tables in the database.
	 * 
	 * @param conn
	 *            connection
	 * @return list of table names
	 * @throws SQLException
	 *             if there is a problem accessing database
	 */
	public static ArrayList<String> getTablesList(Connection conn) throws SQLException {
		ArrayList<String> listofTable = new ArrayList<String>();
		DatabaseMetaData md = conn.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);

		while (rs.next()) {
			if (rs.getString(4).equalsIgnoreCase("TABLE")) {
				listofTable.add(rs.getString(3));
			}
		}
		return listofTable;
	}

}