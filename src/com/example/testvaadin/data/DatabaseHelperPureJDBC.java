package com.example.testvaadin.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.server.VaadinService;

/*
 * Uses pure jdbc connection (instead of Vaadin containers) to make basic queries to database 
 */
public class DatabaseHelperPureJDBC {

	private static final String BASEPATH = VaadinService.getCurrent()
			.getBaseDirectory().getAbsolutePath();
	private static final String CLEAN_QUERY_PATH = BASEPATH
			+ "/WEB-INF/SQL/cleanDatabase.sql";
	private static final String INIT_QUERY_PATH = BASEPATH
			+ "/WEB-INF/SQL/initDatabase.sql";

	/*
	 * Gets connection. Uses username, password, db url from
	 * ApplicationConfiguration class
	 */
	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(ApplicationConfiguration.getDbUrl(),
				ApplicationConfiguration.getDbUserName(),
				ApplicationConfiguration.getDbUserPassword());
	}

	/*
	 * Drops all tables, creates all tables. Returns message describing the
	 * error or saying that the execution was successful
	 */
	public static String initDatabase() {
		Connection connection = null;
		Statement stmt = null;
		String initedSuccessfully = "Initialized database successfully";
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			String[] queries = DatabaseHelperPureJDBC
					.getDtbQueriesFromFile(INIT_QUERY_PATH);
			executeArrayOfQueries(queries, connection, stmt);
			System.out.println("executed stmt");
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

	/*
	 * Returns true if can be created successfully, false otherwise.
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

	/*
	 * Deletes all data from all tables. Returns message describing the error or
	 * saying that the execution was successful
	 */
	public static String cleanDatabase() {
		Connection connection = null;
		Statement stmt = null;
		String cleanedSuccessfully = "Cleaned database successfully";
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			System.out.println("created stmt");
			String[] queries = DatabaseHelperPureJDBC
					.getDtbQueriesFromFile(CLEAN_QUERY_PATH);
			System.out.println("got queries");
			executeArrayOfQueries(queries, connection, stmt);
			System.out.println("executed stmt");
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

	/*
	 * Executes array of queries
	 */
	private static void executeArrayOfQueries(String[] queries,
			Connection connection, Statement statement) throws SQLException {
		for (int i = 0; i < queries.length; i++) {
			// we ensure that there is no spaces before or after the request
			// string
			// in order to not execute empty statements
			if (!queries[i].trim().equals("")) {
				statement.executeUpdate(queries[i]);
			}
		}
	}

	/*
	 * Reads text file under filePath, returns array of queries. Separator
	 * between queries is ;
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
