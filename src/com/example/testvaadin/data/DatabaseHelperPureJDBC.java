package com.example.testvaadin.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.testvaadin.NavigatorUI;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.server.VaadinService;

/*
 * Uses pure jdbc connection (instead of Vaadin containers) to make basic queries to database 
 */
public class DatabaseHelperPureJDBC {

	final static Logger logger = LoggerFactory
			.getLogger(DatabaseHelperPureJDBC.class);

	private static final String BASEPATH = VaadinService.getCurrent()
			.getBaseDirectory().getAbsolutePath();
	private static final String CLEAN_QUERY_PATH = BASEPATH
			+ "/WEB-INF/SQL/cleanDatabase.sql";
	private static final String INIT_QUERY_PATH = BASEPATH
			+ "/WEB-INF/SQL/initDatabase.sql";

	/**
	 * Gets connections. Uses username, password, db url form @link {@link ApplicationConfiguration}
	 * @return
	 * @throws SQLException
	 */
	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(ApplicationConfiguration.getDbUrl(),
				ApplicationConfiguration.getDbUserName(),
				ApplicationConfiguration.getDbUserPassword());
	}

	/**
	 * Called by NavigatorUI on application startup. Creates tables etc.
	 */
	public static void initDatabaseIfNeeded() {
		if (!tableExists("simulator")) {
			initDatabase();
		}
	}

	/**
	 * Check if table exists
	 * 
	 * @param tableName
	 * @return
	 */
	public static boolean tableExists(String tableName) {
		logger.info("Going to check if table: {} exists", tableName);
		Connection connection = null;
		PreparedStatement stmt = null;
		boolean tableExists = false;
		try {
			connection = getConnection();
			stmt = connection
					.prepareStatement("select count(*) as NUM from information_schema.tables where table_name=?");
			stmt.setString(1, tableName);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				int num = resultSet.getInt("NUM");
				if (num > 0) {
					tableExists = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
			closeStatement(stmt);
		}
		return tableExists;
	}


	/**
	 * Drops all tables, creates all tables. Returns message describing the
	 * error or saying that the execution was successful
	 * @return
	 */
	public static String initDatabase() {
		logger.info("Going to init database");
		Connection connection = null;
		Statement stmt = null;
		String initedSuccessfully = "Initialized database successfully";
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			String[] queries = DatabaseHelperPureJDBC
					.getDtbQueriesFromFile(INIT_QUERY_PATH);
			executeArrayOfQueries(queries, connection, stmt);
		} catch (SQLException e) {
			initedSuccessfully = "SQL exception occured while initializing database";
			e.printStackTrace();
		} catch (IOException e) {
			initedSuccessfully = "IOException occured (file with queries not found?) while initializing database";
			e.printStackTrace();
		} finally {
			closeConnection(connection);
			closeStatement(stmt);
		}
		return initedSuccessfully;
	}

	/**
	 * Returns if can db conn can be created, false otherwise
	 * @return
	 */
	public static boolean testDatabaseConnection() {
		Connection connection = null;
		try {
			connection = getConnection();
			if (connection != null) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * Deletes all data from all tables. Returns message describing the error or saying that the execution was successful
	 * @return
	 */
	public static String cleanDatabase() {
		logger.info("Going to clean database");
		Connection connection = null;
		Statement stmt = null;
		String cleanedSuccessfully = "Cleaned database successfully";
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			String[] queries = DatabaseHelperPureJDBC
					.getDtbQueriesFromFile(CLEAN_QUERY_PATH);
			executeArrayOfQueries(queries, connection, stmt);
		} catch (SQLException e) {
			cleanedSuccessfully = "SQL exception occured";
			e.printStackTrace();
		} catch (IOException e) {
			cleanedSuccessfully = "IOException occured (file with queries not found?)";
			e.printStackTrace();
		} finally {
			closeConnection(connection);
			closeStatement(stmt);
		}
		return cleanedSuccessfully;
	}

	/**
	 * Executes array of queries
	 * @param queries
	 * @param connection
	 * @param statement
	 * @throws SQLException
	 */
	private static void executeArrayOfQueries(String[] queries,
			Connection connection, Statement statement) throws SQLException {
		for (int i = 0; i < queries.length; i++) {
			// don't execute empty queries
			if (!queries[i].trim().equals("")) {
				logger.info("Executing query: {} ", queries[i]);
				statement.executeUpdate(queries[i]);
			}
		}
	}

	/**
	 * Reads text file under filePath, returns array of queries. Separator between queries is ;
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private static String[] getDtbQueriesFromFile(String filePath)
			throws IOException {
		String s = new String();
		StringBuffer sb = new StringBuffer();
		FileReader fr = new FileReader(new File(filePath));
		BufferedReader br = new BufferedReader(fr);

		while ((s = br.readLine()) != null) {
			sb.append(s);
		}
		br.close();
		String[] inst = sb.toString().split(";");
		return inst;
	}

	/*
	 * Closes statement if open
	 */
	private static void closeStatement(Statement statement) {
		try {
			if ((statement != null) && (!statement.isClosed())) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Closes connection if open
	 */
	private static void closeConnection(Connection connection) {
		try {
			if ((connection != null) && (!connection.isClosed())) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
