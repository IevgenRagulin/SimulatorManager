package cz.vutbr.fit.simulatormanager.database;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

import cz.vutbr.fit.simulatormanager.beans.SimulationDevStateBean;
import cz.vutbr.fit.simulatormanager.data.AppConfig;
import cz.vutbr.fit.simulatormanager.database.columns.SimulationCols;
import cz.vutbr.fit.simulatormanager.exception.UnknownSimulatorException;

/**
 * This class is used for accessing the database. It has most of the db queries
 * in it. Later, I decided to save queries in different files
 * (EngineModelQueries, SimulationQueries etc.)
 * 
 * @author zhenia
 *
 */
public class DatabaseHelper implements Serializable {
    private static final long serialVersionUID = -5027557673512708776L;
    final static Logger LOG = LoggerFactory.getLogger(DatabaseHelper.class);
    private static JDBCConnectionPool pool = null;
    private static SQLContainer simulatorContainer = null;
    private static SQLContainer simulatorModelContainer = null;
    private static SQLContainer simulatorEngineContainer = null;

    /**
     * Returns JDBCConnection pool. DatabaseHelper should be the only place
     * where pool is initialized
     * 
     * @return
     */
    public static JDBCConnectionPool getPool() {
	if (pool == null) {
	    initConnectionPool();
	}
	return pool;
    }

    /**
     * This is run on stopping the application to close the idle connections
     */
    public static void destroyConnectionPool() {
	pool.destroy();
    }

    private static void initConnectionPool() {
	if (pool == null) {
	    try {
		pool = new SimpleJDBCConnectionPool("org.postgresql.Driver", AppConfig.getDbUrl(), AppConfig.getDbUserName(),
			AppConfig.getDbUserPassword(), 2, 10);
		LOG.info("Initializing connection pool, database url: {}", AppConfig.getDbUrl());
	    } catch (SQLException e) {
		LOG.error("Couldn't initConnectionPool", e);
		throw new RuntimeException("Couldn't init connection pool", e);
	    }
	}
    }

    @SuppressWarnings("deprecation")
    public FreeformQuery buildQuery(String queryString, List<String> primaryKeyColumns) {
	return new FreeformQuery(queryString, primaryKeyColumns, pool);
    }

    /**
     * Returns SQLContainer with the latest running simulation on simulator with
     * id simulatorId If there is more than one simulation currently running
     * (which should be impossible) returns the newest one
     */
    public SQLContainer getLatestSimulationContainer(String simulatorId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * FROM simulation WHERE simulatorid=" + simulatorId
		+ " ORDER BY simulationid DESC LIMIT 1", Arrays.asList("simulationid"), pool);
	query.setDelegate(new FreeFormQueryDelegateSimulationImpl(simulatorId));
	try {
	    return new SQLContainer(query);
	} catch (SQLException e) {
	    LOG.error("Couldn't getLatestSimulationContainer", e);
	    return null;
	}
    }

    /**
     * Gets LATEST simulation devices state item by simulator id
     */
    public Item getSimulationDevicesStateBySimulatorId(String simulatorId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery(
		"SELECT * FROM SimulationDevicesState WHERE simulationid=(SELECT simulationid FROM simulation WHERE simulatorid="
			+ simulatorId
			+ "AND IsSimulationOn=true ORDER BY SimulationStartedTime DESC LIMIT 1) ORDER BY \"timestamp\" DESC LIMIT 1",
		Arrays.asList("devstateid"), pool);
	try {
	    return DatabaseUtil.getLatestItemFromContainer(new SQLContainer(query));
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't get getSimulationDevicesStateBySimulatorId", e);
	}
    }

    public DatabaseHelper() {
	initConnectionPool();
    }

    /**
     * If simulator container has been created before, returns it. If it hasn't
     * been created before, creates it, returns it.
     */
    public SQLContainer getCachedSimulatorContainer() {
	if (simulatorContainer == null) {
	    simulatorContainer = getNewSimulatorContainer();
	}
	return simulatorContainer;
    }

    /**
     * If simulator model container has been created before, returns it. If it
     * hasn't been created before, creates it, returns it.
     */
    public SQLContainer getSimulatorModelContainer() {
	if (simulatorModelContainer == null) {
	    simulatorModelContainer = getNewSimulatorModelContainer();
	}
	return simulatorModelContainer;
    }

    /**
     * If engine model container has been created before, returns it. If it
     * hasn't been created before, creates it, returns it.
     */
    public SQLContainer getEngineModelContainer() {
	if (simulatorEngineContainer == null) {
	    simulatorEngineContainer = getSQLContainer("enginemodel");
	}
	return simulatorEngineContainer;
    }

    /**
     * Returns SQLContainer with all simulations for simulator with id
     */
    public SQLContainer getSimulationContainerOnSimulatorWithId(String simulatorId) {
	FreeFormQueryTest query = new FreeFormQueryTest("SELECT * FROM simulation where simulatorid=" + simulatorId
		+ " LIMIT ALL ", Arrays.asList("simulationid"), pool);
	query.setDelegate(new FreeFormStatementDelegateSimulationsImpl(simulatorId));
	try {
	    return new SQLContainer(query);
	} catch (SQLException e) {
	    LOG.error("Couldn't getSimulationContainerOnSimulatorWithId model container", e);
	    return null;
	}
    }

    public Item getSimulationPFDBySimulatorId(String simulatorId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery(
		"SELECT * FROM SimulationPFDInfo WHERE simulationid=(SELECT simulationid FROM simulation WHERE simulatorid="
			+ simulatorId
			+ "AND IsSimulationOn=true ORDER BY timestamp DESC LIMIT 1) ORDER BY \"timestamp\" DESC LIMIT 1",
		Arrays.asList("simulationpfdinfoid"), pool);
	try {
	    return DatabaseUtil.getLatestItemFromContainer(new SQLContainer(query));
	} catch (SQLException e) {
	    LOG.error("Couldn't getSimulationPFDBySimulatorId", e);
	    return null;
	}
    }

    /**
     * Gets all simulation pfd info for simulation with id.
     */
    public SQLContainer getPFDInfoBySimulationId(String simulationId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * FROM simulationpfdinfo WHERE simulationid = " + simulationId
		+ "ORDER BY timestamp ASC; ", Arrays.asList("simulationpfdinfoid"), pool);
	try {
	    return new SQLContainer(query);
	} catch (SQLException e) {
	    LOG.error("Couldn't getPFDInfoBySimulationId", e);
	    return null;
	}
    }

    /**
     * Gets all simulation pfd info foSQLContainer simulationInfo = null; r
     * latest simulation.
     */
    public SQLContainer getLatestPFDInfoBySimulatorId(String simulatorId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * FROM simulationpfdinfo si1 "
		+ "WHERE simulationid = (SELECT simulationid FROM simulation WHERE simulatorid = " + simulatorId
		+ " AND issimulationon = true ORDER BY timestamp DESC LIMIT 1) ORDER BY timestamp ASC; ",
		Arrays.asList("simulationpfdinfoid"), pool);
	try {
	    return new SQLContainer(query);
	} catch (SQLException e) {
	    LOG.error("Couldn't getLatestPFDInfoBySimulatorId", e);
	    return null;
	}
    }

    /**
     * Gets all simulation info for latest simulation. Selects only those
     * records where there doesn't exist a records with id-1 with the same
     * values of longtitude, latitude, brakes, flaps For example: id: 1;
     * longtitude 2; flaps true id: 2; longtitude 2; flaps true id: 3;
     * longtitude 3; flapse true Returns only rows with id=2 and id = 3;
     */
    public SQLContainer getAllSimulationInfoBySimulatorId(String simulatorId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery(
		"SELECT * FROM simulationinfo si1 WHERE simulationid = (SELECT simulationid FROM simulation WHERE simulatorid = "
			+ simulatorId
			+ " AND issimulationon = true ORDER BY timestamp DESC LIMIT 1) AND NOT EXISTS (SELECT si2.simulationinfoid "
			+ "FROM simulationinfo si2 WHERE si1.longtitude = si2.longtitude AND si1.latitude = si2.latitude "
			+ "AND si1.simulationinfoid = (si2.simulationinfoid - 1 )) ORDER BY \"timestamp\" ASC; ",
		Arrays.asList("simulationinfoid"), pool);
	try {
	    return new SQLContainer(query);
	} catch (SQLException e) {
	    LOG.error("Couldn't getAllSimulationInfoBySimulatorId", e);
	    return null;
	}
    }

    /**
     * Gets all simulation info for simulation with id.
     */
    public SQLContainer getAllSimulationInfoBySimulationId(String simulationId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * " + "FROM SimulationInfo " + "WHERE simulationid = " + simulationId
		+ "ORDER BY \"timestamp\" ASC; ", Arrays.asList("simulationinfoid"), pool);
	try {
	    return new SQLContainer(query);
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't getAllSimulationInfoBySimulationId", e);
	}
    }

    public SQLContainer getEnginesModelsOnSimulatorModel(String simulatorModelId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * FROM enginemodel " + "WHERE simulatormodelid = " + simulatorModelId
		+ " ORDER BY \"enginemodelorder\" ASC; ", Arrays.asList("enginemodelid"), pool);
	query.setDelegate(new FreeFormStatementDelegateEngineModels(simulatorModelId));
	try {
	    return new SQLContainer(query);
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't getAllSimulationInfoBySimulationId", e);
	}
    }

    /**
     * Gets latest simulation info record for latest simulation.
     */
    public Item getLatestSimulationInfoBySimulatorId(String simulatorId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery(
		"SELECT * FROM SimulationInfo WHERE simulationid=(SELECT SimulationId FROM simulation WHERE simulatorid="
			+ simulatorId
			+ "AND IsSimulationOn=true ORDER BY SimulationStartedTime DESC LIMIT 1) ORDER BY \"timestamp\" DESC LIMIT 1",
		Arrays.asList("simulationinfoid"), pool);
	try {
	    return DatabaseUtil.getLatestItemFromContainer(new SQLContainer(query));
	} catch (SQLException e) {
	    LOG.error("Couldn't getLatestSimulationInfoBySimulatorId", e);
	    return null;
	}

    }

    /**
     * Checks if the last simulation on simulator with simulatorId running
     * according to database
     */
    public boolean isLastSimInDbRunning(String simulatorId) {
	final SQLContainer latestSimulationCont = this.getLatestSimulationContainer(simulatorId);
	Item lastSimDb = DatabaseUtil.getLatestItemFromContainer(latestSimulationCont);
	Boolean isLastSimInDbOn = (Boolean) lastSimDb.getItemProperty(SimulationCols.issimulationon.toString()).getValue();
	return (isLastSimInDbOn != null) && (isLastSimInDbOn);
    }

    /**
     * Get Simulator item by simulatorId
     */
    public Item getSimulatorItemBySimulatorId(String simulatorId) throws UnknownSimulatorException {
	SQLContainer simulatorContainer = getNewSimulatorContainer();
	Collection<?> itemIds = simulatorContainer.getItemIds();
	Item simulatorItem = null;
	for (Object itemId : itemIds) {
	    String itemSimulatorId = ((RowId) itemId).getId()[0].toString();
	    if (itemSimulatorId.equals(simulatorId)) {
		simulatorItem = (Item) simulatorContainer.getItem(itemId);
	    }
	}
	if (simulatorItem == null) {
	    throw new UnknownSimulatorException("There is no simulator with such simulator id in database: " + simulatorId);
	}
	return simulatorItem;
    }

    /**
     * Gets pfd info item by pfdinfoid
     */
    public Item getPFDInfoByPfdInfoId(int pfdInfoId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * " + "FROM SimulationPfdInfo " + "WHERE simulationpfdinfoid = "
		+ pfdInfoId, Arrays.asList("simulationpfdinfoid"), pool);
	try {
	    return DatabaseUtil.getLatestItemFromContainer(new SQLContainer(query));
	} catch (SQLException e) {
	    LOG.error("Couldn't getPFDInfoByPfdInfoId", e);
	    return null;
	}

    }

    /**
     * Gets simulation dev state item info item by pfdinfoid, closest in time to
     * timestamp
     */
    public SimulationDevStateBean getSimulationDevStateInfoByPfdInfoId(int pfdInfoId, long timestamp) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * " + "FROM simulationdevicesstate "
		+ "WHERE simulationid=(SELECT simulationid " + "FROM simulationpfdinfo " + "WHERE simulationpfdinfoid="
		+ pfdInfoId + ") " + "ORDER BY ABS(EXTRACT(EPOCH FROM timestamp-(to_timestamp(" + timestamp
		+ "/1000)::timestamp))) limit 1", Arrays.asList("simulationdevicesstateid"), pool);
	try {
	    Item item = DatabaseUtil.getLatestItemFromContainer(new SQLContainer(query));
	    return new SimulationDevStateBean(item);
	} catch (SQLException e) {
	    LOG.error("Couldn't getSimulationDevStateInfoByPfdInfoId", e);
	    return null;
	}
    }

    /**
     * Gets simulator item by pfdinfoid
     */
    public Item getSimulatorInfoByPfdInfoId(int pfdInfoId) throws SQLException {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * FROM simulator "
		+ "WHERE simulatorid = (SELECT simulatorid FROM simulation " + "WHERE simulationid = (SELECT simulationid FROM "
		+ "simulationpfdinfo WHERE " + "simulationpfdinfoid = " + pfdInfoId + "));", Arrays.asList("simulatorid"), pool);
	return DatabaseUtil.getLatestItemFromContainer(new SQLContainer(query));
    }

    /**
     * Gets simulation info item which corresponds to pfdidinfoid, and whose
     * timestamp is the closest to timestamp
     */
    public Item getSimulationInfoItemByPfdInfoIdTimestemp(int pfdInfoId, long timestamp) throws SQLException {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * " + "FROM simulationinfo " + "WHERE simulationid=(SELECT simulationid "
		+ "FROM simulationpfdinfo " + "WHERE simulationpfdinfoid=" + pfdInfoId + ") "
		+ "ORDER BY ABS(EXTRACT(EPOCH FROM timestamp-(to_timestamp(" + timestamp + "/1000)::timestamp))) limit 1",
		Arrays.asList("simulationinfoid"), pool);
	SQLContainer simulationInfo = null;
	simulationInfo = new SQLContainer(query);
	return DatabaseUtil.getLatestItemFromContainer(simulationInfo);
    }

    /**
     * Creates and returns new simulator model container
     * 
     * @return
     */
    public SQLContainer getNewSimulatorModelContainer() {
	return getSQLContainer("simulatormodel");
    }

    /**
     * Creates and returns new simulator SQLcontainer
     */
    public SQLContainer getNewSimulatorContainer() {
	return getSQLContainer("simulator");
    }

    /**
     * Creates and returns simulation SQLcontainer
     */
    public SQLContainer getSimulationContainer() {
	return getSQLContainer("simulation");
    }

    /**
     * Returns simulation info SQLContainer
     */
    public SQLContainer getSimulationInfoContainer() {
	return getSQLContainer("simulationinfo");
    }

    /**
     * Returns simulation devices state SQLContainer
     */
    public SQLContainer getSimulationDevicesStateContainer() {
	return getSQLContainer("simulationdevicesstate");
    }

    /**
     * Returns simulation engines state SQLContainer
     */
    public SQLContainer getSimulationEnginesStateContainer() {
	return getSQLContainer("simulationenginesstate");
    }

    public SQLContainer getSimulationPFDContainer() {
	return getSQLContainer("simulationpfdinfo");
    }

    /**
     * Default query for getting sql container
     * 
     * @param tablename
     * @return
     */
    private SQLContainer getSQLContainer(String tablename) {
	try {
	    TableQuery tq = new TableQuery(tablename, pool);
	    tq.setVersionColumn("timestamp");
	    SQLContainer sqlContainer = null;
	    sqlContainer = new SQLContainer(tq);
	    return sqlContainer;
	} catch (RuntimeException e) {
	    LOG.error("Database is not initialized. Please go to Configuration and click Init configuration there", e);
	    return null;
	} catch (SQLException e) {
	    LOG.error("Couldn't getSQLContainer", e);
	    return null;
	}
    }

    public void updateCachedSimulatorContainer() {
	this.simulatorContainer = getNewSimulatorContainer();
    }
}
