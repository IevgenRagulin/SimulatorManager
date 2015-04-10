package cz.vutbr.fit.simulatormanager.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.VaadinService;

import cz.vutbr.fit.simulatormanager.data.ApplicationConfiguration;

/*
 * Uses pure jdbc connection (instead of Vaadin containers) to make basic queries to database 
 */
public class DatabaseHelperPureJDBC {

    final static Logger LOG = LoggerFactory.getLogger(DatabaseHelperPureJDBC.class);

    private static final String BASEPATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
    private static final String CLEAN_QUERY_PATH = BASEPATH + "/WEB-INF/SQL/cleanDatabase.sql";
    private static final String INIT_QUERY_PATH = BASEPATH + "/WEB-INF/SQL/initDatabase.sql";

    public static Array createSqlArray(Float[] values) {
	try {
	    return getConnection().createArrayOf("float 4", values);
	} catch (SQLException e) {
	    LOG.error("SQL Exception was thrown", e);
	    return null;
	}
    }

    /**
     * Returns true if database is running, false otherwise
     * 
     * @return
     */
    public static boolean isDatabaseRunning() {
	Connection connection = null;
	try {
	    try {
		connection = getConnection();
	    } catch (SQLException e) {
		LOG.error("SQL Exception occured when getting connection to database", e);
		return false;
	    }
	} finally {
	    if (connection != null) {
		closeConnection(connection);
	    }
	}
	return true;
    }

    /**
     * Gets connections. Uses username, password, db url form @link
     * {@link ApplicationConfiguration}
     * 
     * @return
     * @throws SQLException
     */
    private static Connection getConnection() throws SQLException {
	return DriverManager.getConnection(ApplicationConfiguration.getDbUrl(),
		ApplicationConfiguration.getDbUserName(), ApplicationConfiguration.getDbUserPassword());
    }

    /**
     * Called by NavigatorUI on application startup. Creates tables etc.
     */
    public static void initDatabaseIfNeeded() {
	LOG.info("Going to init database");
	if (!isDatabaseInitialized()) {
	    initDatabase();
	}
    }

    /**
     * Check if all neccessary tables exist
     * 
     * @return
     */
    public static boolean isDatabaseInitialized() {
	return tableExists("simulationenginesstate") && tableExists("simulationdevicesstate")
		&& tableExists("simulationinfo") && tableExists("simulationpfdinfo") && tableExists("simulation")
		&& tableExists("simulator") && tableExists("enginemodel") && tableExists("simulatormodel");
    }

    /**
     * Check if table exists
     * 
     * @param tableName
     * @return
     */
    public static boolean tableExists(String tableName) {
	LOG.info("Going to check if table: {} exists", tableName);
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
     * 
     * @return
     */
    public static String initDatabase() {
	LOG.info("Going to init database");
	Connection connection = null;
	Statement stmt = null;
	String initedSuccessfully = "Initialized database successfully";
	try {
	    connection = getConnection();
	    stmt = connection.createStatement();
	    String[] queries = DatabaseHelperPureJDBC.getDtbQueriesFromFile(INIT_QUERY_PATH);
	    executeArrayOfQueries(queries, connection, stmt);
	} catch (SQLException e) {
	    LOG.error("SQLException occured while initializing DB", e);
	    initedSuccessfully = "SQL exception occured while initializing database";
	} catch (IOException e) {
	    LOG.error("IOException occured while initializing DB", e);
	    initedSuccessfully = "IOException occured (file with queries not found?) while initializing database";
	} finally {
	    closeConnection(connection);
	    closeStatement(stmt);
	}
	return initedSuccessfully;
    }

    /**
     * Returns if can db conn can be created, false otherwise
     * 
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
	    closeConnection(connection);
	}
	return false;
    }

    /**
     * Deletes all data from all tables. Returns message describing the error or
     * saying that the execution was successful
     * 
     * @return
     */
    public static String cleanDatabase() {
	LOG.info("Going to clean database");
	Connection connection = null;
	Statement stmt = null;
	String cleanedSuccessfully = "Cleaned database successfully";
	try {
	    connection = getConnection();
	    stmt = connection.createStatement();
	    String[] queries = DatabaseHelperPureJDBC.getDtbQueriesFromFile(CLEAN_QUERY_PATH);
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
     * 
     * @param queries
     * @param connection
     * @param statement
     * @throws SQLException
     */
    private static void executeArrayOfQueries(String[] queries, Connection connection, Statement statement)
	    throws SQLException {
	LOG.info("Execute array of queries: {}", queries.length);
	for (int i = 0; i < queries.length; i++) {
	    // don't execute empty queries
	    if (!queries[i].trim().equals("")) {
		LOG.info("Executing query: {} ", queries[i]);
		statement.executeUpdate(queries[i]);
	    }
	}
    }

    /**
     * Reads text file under filePath, returns array of queries. Separator
     * between queries is ;
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    private static String[] getDtbQueriesFromFile(String filePath) throws IOException {
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
		statement.close();
	    }
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't close statement", e);
	}
    }

    /*
     * Closes connection if open
     */
    private static void closeConnection(Connection connection) {
	try {
	    if ((connection != null) && (!connection.isClosed())) {
		connection.close();
	    }
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't close database connection", e);
	}
    }
}
