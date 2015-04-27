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
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.VaadinService;

import cz.vutbr.fit.simulatormanager.beans.AllEngineInfo;
import cz.vutbr.fit.simulatormanager.data.AppConfig;
import cz.vutbr.fit.simulatormanager.database.columns.EngineCols;

/*
 * Uses pure jdbc connection (instead of Vaadin containers) to make basic queries to database 
 */
public class DatabaseHelperPureJDBC {

    final static Logger LOG = LoggerFactory.getLogger(DatabaseHelperPureJDBC.class);

    private static final String BASEPATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
    private static final String CLEAN_QUERY_PATH = BASEPATH + "/WEB-INF/SQL/cleanDatabase.sql";
    private static final String INIT_QUERY_PATH = BASEPATH + "/WEB-INF/SQL/initDatabase.sql";

    /**
     * Returns true if database is running, false otherwise
     * 
     * @return
     */
    public static boolean isDatabaseRunning() {
	try (Connection connection = getConnection()) {
	} catch (SQLException e) {
	    LOG.error("SQL Exception occured when getting connection to database", e);
	    return false;
	}
	return true;
    }

    /**
     * Gets connections. Uses username, password, db url form @link
     * {@link AppConfig}
     * 
     * @return
     * @throws SQLException
     */
    private static Connection getConnection() throws SQLException {
	return DriverManager.getConnection(AppConfig.getDbUrl(), AppConfig.getDbUserName(),
		AppConfig.getDbUserPassword());
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
	return tableExists("simulationenginesstate") && tableExists("simulationdevicesstate") && tableExists("simulationinfo")
		&& tableExists("simulationpfdinfo") && tableExists("simulation") && tableExists("simulator")
		&& tableExists("enginemodel") && tableExists("simulatormodel");
    }

    /**
     * Check if table exists
     * 
     * @param tableName
     * @return
     */
    public static boolean tableExists(String tableName) {
	LOG.info("Going to check if table: {} exists", tableName);

	boolean tableExists = false;
	try (Connection connection = getConnection();
		PreparedStatement stmt = connection
			.prepareStatement("select count(*) as NUM from information_schema.tables where table_name=?")) {
	    stmt.setString(1, tableName);
	    try (ResultSet resultSet = stmt.executeQuery()) {
		while (resultSet.next()) {
		    int num = resultSet.getInt("NUM");
		    if (num > 0) {
			tableExists = true;
		    } else {
			LOG.error("Tabled with name: {} doesn't exist", tableName);
		    }
		}
	    }
	} catch (SQLException e) {
	    LOG.error("SQL exception occured when checking if table exists", e);
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
	String initedSuccessfully = "Initialized database successfully";
	try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
	    String[] queries = DatabaseHelperPureJDBC.getDtbQueriesFromFile(INIT_QUERY_PATH);
	    executeArrayOfQueries(queries, connection, stmt);
	    LOG.info("Database has been initialized with no exceptions");
	} catch (SQLException e) {
	    LOG.error("SQLException occured while initializing DB", e);
	    initedSuccessfully = "SQL exception occured while initializing database";
	} catch (IOException e) {
	    LOG.error("IOException occured while initializing DB", e);
	    initedSuccessfully = "IOException occured (file with queries not found?) while initializing database";
	}
	return initedSuccessfully;
    }

    /**
     * Returns if can db conn can be created, false otherwise
     * 
     * @return
     */
    public static boolean testDatabaseConnection() {
	try (Connection connection = getConnection()) {
	    if (connection != null) {
		return true;
	    }
	} catch (SQLException e) {
	    LOG.error("SQL exception occured when testing database connection", e);
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
	String cleanedSuccessfully = "Cleaned database successfully";
	try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
	    String[] queries = DatabaseHelperPureJDBC.getDtbQueriesFromFile(CLEAN_QUERY_PATH);
	    executeArrayOfQueries(queries, connection, stmt);
	} catch (SQLException e) {
	    cleanedSuccessfully = "SQL exception occured";
	    LOG.error("SQL exception occured when cleaning database", e);
	} catch (IOException e) {
	    cleanedSuccessfully = "IOException occured (file with queries not found?)";
	    LOG.error("IOException exception occured when cleaning database", e);
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
    private static void executeArrayOfQueries(String[] queries, Connection connection, Statement statement) throws SQLException {
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

    /**
     * Inserts engines info to the database. We use pure JDBC instead of
     * SQLContainer, because SQLContainer doesn't support inserting arrays of
     * data
     * 
     * @param allEnginesInfo
     */
    public static void insertEnginesInfo(AllEngineInfo allEnginesInfo, Integer simulationId) {
	try (Connection connection = getConnection();
		PreparedStatement stmt = buildInsertEnginesStatement(connection, allEnginesInfo, simulationId)) {
	    stmt.executeUpdate();
	} catch (SQLException e) {
	    LOG.error("SQLException occured when trying to insert engines info", e);
	}
    }

    /**
     * Gets latest engines info which corresponds to pfdidinfoid, and whose
     * timestamp is the closest to timestamp
     */
    public static Optional<AllEngineInfo> getEnginesInfo(Integer pfdInfoId, long timestamp) {
	String selectQuery = "SELECT * FROM simulationenginesstate WHERE simulation_simulationid=(SELECT simulation_simulationid FROM simulationpfdinfo WHERE pfdinfoid=?)  ORDER BY ABS(EXTRACT(EPOCH FROM timestamp-(to_timestamp(?/1000)::timestamp))) limit 1";
	try (Connection connection = getConnection();
		PreparedStatement stmt = buildSelectEnginesStatement(connection, pfdInfoId, timestamp, selectQuery)) {
	    ResultSet rs = stmt.executeQuery();
	    AllEngineInfo enginesInfo = buildAllEngineInfo(rs);
	    return Optional.of(enginesInfo);
	} catch (SQLException e) {
	    LOG.error("SQLException occured when trying to select engines info for pfdinfoid id: {}" + pfdInfoId, e);
	    return Optional.empty();
	}
    }

    private static PreparedStatement buildSelectEnginesStatement(Connection connection, Integer pfdInfoId, long timestamp,
	    String selectQuery) throws SQLException {
	PreparedStatement stmt = connection.prepareStatement(selectQuery);
	stmt.setInt(1, pfdInfoId);
	stmt.setLong(2, timestamp);
	return stmt;
    }

    private static AllEngineInfo buildAllEngineInfo(ResultSet rs) throws SQLException {
	AllEngineInfo enginesInfo = new AllEngineInfo();
	rs.next();
	if (rs != null) {
	    int enginesNum = (Integer) rs.getInt("engines_num");
	    enginesInfo.setNumberOfEngines(enginesNum);

	    Float[] rpm = getFloatArray(rs, EngineCols.rpm.toString());
	    enginesInfo.setRpm(rpm);

	    Float[] pwr = getFloatArray(rs, EngineCols.pwr.toString());
	    enginesInfo.setPwr(pwr);

	    Float[] pwp = getFloatArray(rs, EngineCols.pwp.toString());
	    enginesInfo.setPwp(pwp);

	    Float[] mp_ = getFloatArray(rs, EngineCols.mp_.toString());
	    enginesInfo.setMp_(mp_);

	    Float[] et1 = getFloatArray(rs, EngineCols.et1.toString());
	    enginesInfo.setEt1(et1);

	    Float[] et2 = getFloatArray(rs, EngineCols.et2.toString());
	    enginesInfo.setEt2(et2);

	    Float[] ct1 = getFloatArray(rs, EngineCols.ct1.toString());
	    enginesInfo.setCt1(ct1);

	    Float[] ct2 = getFloatArray(rs, EngineCols.ct2.toString());
	    enginesInfo.setCt2(ct2);

	    Float[] est = getFloatArray(rs, EngineCols.est.toString());
	    enginesInfo.setEst(est);

	    Float[] ff_ = getFloatArray(rs, EngineCols.ff_.toString());
	    enginesInfo.setFf_(ff_);

	    Float[] fp_ = getFloatArray(rs, EngineCols.fp_.toString());
	    enginesInfo.setFp_(fp_);

	    Float[] op_ = getFloatArray(rs, EngineCols.op_.toString());
	    enginesInfo.setOp_(op_);

	    Float[] ot_ = getFloatArray(rs, EngineCols.ot_.toString());
	    enginesInfo.setOt_(ot_);

	    Float[] n1_ = getFloatArray(rs, EngineCols.n1_.toString());
	    enginesInfo.setN1_(n1_);

	    Float[] n2_ = getFloatArray(rs, EngineCols.n2_.toString());
	    enginesInfo.setN2_(n2_);

	    Float[] vib = getFloatArray(rs, EngineCols.vib.toString());
	    enginesInfo.setVib(vib);

	    Float[] vlt = getFloatArray(rs, EngineCols.vlt.toString());
	    enginesInfo.setVlt(vlt);

	    Float[] amp = getFloatArray(rs, EngineCols.amp.toString());
	    enginesInfo.setAmp(amp);
	}
	return enginesInfo;
    }

    private static Float[] getFloatArray(ResultSet rs, String keyName) throws SQLException {
	return (Float[]) rs.getArray(keyName).getArray();
    }

    /**
     * Build insert PreparedStatement based on AllEngineInfo.
     * 
     * @param conn
     * @param engInfo
     * @return
     * @throws SQLException
     */
    private static PreparedStatement buildInsertEnginesStatement(Connection conn, AllEngineInfo engInfo, Integer simulationId)
	    throws SQLException {
	PreparedStatement stmt = null;
	stmt = conn
		.prepareStatement("INSERT INTO simulationenginesstate (simulation_simulationid, engines_num, rpm, pwr, pwp, mp_, et1, et2, ct1, ct2, est, ff_, fp_, op_, ot_, n1_, n2_, vib, vlt, amp) VALUES "
			+ "							(?,                       ?,           ?,    ?,   ?,  ?,   ?,    ?,   ?,   ?,   ?,   ?,   ?,  ?,   ?,   ?,  ?,    ?,   ?,   ?)");
	stmt.setInt(1, simulationId);
	stmt.setInt(2, engInfo.getNumberOfEngines());
	stmt.setArray(3, creatArrayOfFloat(conn, engInfo.getRpm()));
	stmt.setArray(4, creatArrayOfFloat(conn, engInfo.getPwr()));
	stmt.setArray(5, creatArrayOfFloat(conn, engInfo.getPwp()));
	stmt.setArray(6, creatArrayOfFloat(conn, engInfo.getMp_()));
	stmt.setArray(7, creatArrayOfFloat(conn, engInfo.getEt1()));
	stmt.setArray(8, creatArrayOfFloat(conn, engInfo.getEt2()));
	stmt.setArray(9, creatArrayOfFloat(conn, engInfo.getCt1()));
	stmt.setArray(10, creatArrayOfFloat(conn, engInfo.getCt2()));
	stmt.setArray(11, creatArrayOfFloat(conn, engInfo.getEst()));
	stmt.setArray(12, creatArrayOfFloat(conn, engInfo.getFf_()));
	stmt.setArray(13, creatArrayOfFloat(conn, engInfo.getFp_()));
	stmt.setArray(14, creatArrayOfFloat(conn, engInfo.getOp_()));
	stmt.setArray(15, creatArrayOfFloat(conn, engInfo.getOt_()));
	stmt.setArray(16, creatArrayOfFloat(conn, engInfo.getN1_()));
	stmt.setArray(17, creatArrayOfFloat(conn, engInfo.getN2_()));
	stmt.setArray(18, creatArrayOfFloat(conn, engInfo.getVib()));
	stmt.setArray(19, creatArrayOfFloat(conn, engInfo.getVlt()));
	stmt.setArray(20, creatArrayOfFloat(conn, engInfo.getAmp()));
	return stmt;
    }

    private static Array creatArrayOfFloat(Connection connection, Float[] values) {
	try {
	    return connection.createArrayOf("float4", values);
	} catch (SQLException e) {
	    LOG.error("SQL exception occured when creating array of floars", e);
	    return null;
	}
    }
}
