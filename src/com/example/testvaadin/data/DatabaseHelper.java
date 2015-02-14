package com.example.testvaadin.data;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.example.testvaadin.exception.UnknownSimulatorException;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DatabaseHelper implements Serializable {
	private static final long serialVersionUID = -5027557673512708776L;
	private static JDBCConnectionPool pool = null;
	private SQLContainer simulatorContainer = null;


	@SuppressWarnings("deprecation")
	public FreeformQuery buildQuery (String queryString, List<String> primaryKeyColumns) {
		return new FreeformQuery(queryString, primaryKeyColumns, pool);
	}

	/*
	 * Returns SQLContainer with the latest running simulation on simulator with
	 * id simulatorId If there is more than one simulation currently running
	 * (which should be impossible) returns the newest one
	 */
	public SQLContainer getLatestSimulationContainer(String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery("SELECT * FROM simulation WHERE Simulator_SimulatorId=" + simulatorId
				+ " ORDER BY simulationid DESC LIMIT 1", Arrays.asList("simulationid"), pool);
		SQLContainer runningSimulations = null;
		query.setDelegate(new FreeFormQueryDelegateSimulationImpl(simulatorId));
		try {
			runningSimulations = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return runningSimulations;
	}

	/*
	 * Returns an Item with the latest running simulation on simulator with id
	 * simulatorId If there is more than one simulation currently running (which
	 * should be impossible) returns the newest one
	 */
	/*
	 * public Item getLatestSimulationOnSimulatorWithId(String simulatorId) {
	 * 
	 * @SuppressWarnings("deprecation") FreeformQuery query = new FreeformQuery(
	 * "SELECT * FROM simulation WHERE Simulator_SimulatorId=" + simulatorId +
	 * " ORDER BY simulationid DESC LIMIT 1", Arrays.asList("simulationid"),
	 * pool); SQLContainer runningSimulations = null; try { runningSimulations =
	 * new SQLContainer(query); } catch (SQLException e) { e.printStackTrace();
	 * } return getLatestItemFromContainer(runningSimulations); }
	 */



	/*
	 * Gets LATEST simulation devices state item by simulator id
	 */
	public Item getSimulationDevicesStateBySimulatorId(String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery(
				"SELECT * FROM SimulationDevicesState WHERE Simulation_SimulationId=(SELECT SimulationId FROM simulation WHERE Simulator_SimulatorId="
						+ simulatorId
						+ "AND IsSimulationOn=true ORDER BY SimulationStartedTime DESC LIMIT 1) ORDER BY \"timestamp\" DESC LIMIT 1",
				Arrays.asList("devstateid"), pool);
		SQLContainer simulationDevicesState = null;
		try {
			simulationDevicesState = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return DatabaseUtil.getLatestItemFromContainer(simulationDevicesState);
	}

	public DatabaseHelper() {
		initConnectionPool();
	}

	private void initConnectionPool() {
		if (pool == null) {
			try {
				pool = new SimpleJDBCConnectionPool("org.postgresql.Driver", ApplicationConfiguration.getDbUrl(),
						ApplicationConfiguration.getDbUserName(), ApplicationConfiguration.getDbUserPassword(), 2, 10);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * If simulator container has been created before, returns it. If it hasn't
	 * been created before, creates it, returns it.
	 */
	public SQLContainer getSimulatorContainer() {
		if (simulatorContainer == null) {
			TableQuery tq = new TableQuery("simulator", pool);
			tq.setVersionColumn("timestamp");
			try {
				simulatorContainer = new SQLContainer(tq);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return simulatorContainer;
	}

	/*
	 * Creates simulator SQLcontainer, returns it
	 */
	public SQLContainer getNewSimulatorContainer() {
		SQLContainer newSqlContainer = null;
		TableQuery tq = new TableQuery("simulator", pool);
		tq.setVersionColumn("timestamp");
		try {
			newSqlContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newSqlContainer;
	}

	/*
	 * Returns simulation SQLcontainer
	 */
	public SQLContainer getSimulationContainer() {
		TableQuery tq = new TableQuery("simulation", pool);
		tq.setVersionColumn("simulationid");
		SQLContainer simulationContainer = null;
		try {
			simulationContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationContainer;
	}

	/*
	 * Returns SQLContainer with all simulations for simulator with id
	 */
	public SQLContainer getSimulationContainerOnSimulatorWithId(String simulatorId) {
		FreeFormQueryTest query = new FreeFormQueryTest("SELECT * FROM simulation where simulator_simulatorid=" + simulatorId
				+ " ORDER BY \"timestamp\" LIMIT ALL ", Arrays.asList("simulationid"), pool);
		SQLContainer simulationPFD = null;
		query.setDelegate(new FreeFormStatementDelegateSimulationsImpl(simulatorId));

		try {
			simulationPFD = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return simulationPFD;
	}

	/* Returns simulation info SQLContainer */
	public SQLContainer getSimulationInfoContainer() {
		TableQuery tq = new TableQuery("simulationinfo", pool);
		tq.setVersionColumn("timestamp");
		SQLContainer simulationDevicesStateContainer = null;
		try {
			simulationDevicesStateContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationDevicesStateContainer;
	}

	/* Returns simulation devices state SQLContainer */
	public SQLContainer getSimulationDevicesStateContainer() {
		TableQuery tq = new TableQuery("simulationdevicesstate", pool);
		tq.setVersionColumn("timestamp");
		SQLContainer simulationDevicesStateContainer = null;
		try {
			simulationDevicesStateContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationDevicesStateContainer;
	}

	/* Returns simulation engines state SQLContainer */
	public SQLContainer getSimulationEnginesStateContainer() {
		TableQuery tq = new TableQuery("simulationenginesstate", pool);
		tq.setVersionColumn("timestamp");
		SQLContainer simulationEnginesStateContainer = null;
		try {
			simulationEnginesStateContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationEnginesStateContainer;
	}

	public SQLContainer getSimulationPFDContainer() {
		TableQuery tq = new TableQuery("simulationpfdinfo", pool);
		tq.setVersionColumn("timestamp");
		SQLContainer simulationDevicesStateContainer = null;
		try {
			simulationDevicesStateContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationDevicesStateContainer;
	}

	public Item getSimulationPFDBySimulatorId(String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery(
				"SELECT * FROM SimulationPFDInfo WHERE Simulation_SimulationId=(SELECT SimulationId FROM simulation WHERE Simulator_SimulatorId="
						+ simulatorId + "AND IsSimulationOn=true ORDER BY timestamp DESC LIMIT 1) ORDER BY \"timestamp\" DESC LIMIT 1",
				Arrays.asList("PFDInfoId"), pool);
		SQLContainer simulationPFD = null;
		try {
			simulationPFD = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return DatabaseUtil.getLatestItemFromContainer(simulationPFD);
	}

	/*
	 * Gets all simulation pfd info for simulation with id.
	 */
	public SQLContainer getPFDInfoBySimulationId(String simulationId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery("SELECT * " + "FROM SimulationPfdInfo " + "WHERE Simulation_SimulationId = " + simulationId
				+ "ORDER BY timestamp ASC; ", Arrays.asList("pfdinfoid"), pool);
		SQLContainer simulationInfo = null;
		try {
			simulationInfo = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationInfo;
	}

	/*
	 * Gets all simulation pfd info for latest simulation.
	 */
	public SQLContainer getLatestPFDInfoBySimulatorId(String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery("SELECT * " + "FROM SimulationPfdInfo si1 "
				+ "WHERE Simulation_SimulationId = (SELECT SimulationId " + "FROM simulation " + "WHERE Simulator_SimulatorId = "
				+ simulatorId + " AND IsSimulationOn = true " + "ORDER BY timestamp DESC LIMIT 1) " + "ORDER BY timestamp ASC; ",
				Arrays.asList("pfdinfoid"), pool);
		SQLContainer simulationInfo = null;
		try {
			simulationInfo = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationInfo;
	}

	/*
	 * Gets all simulation info for latest simulation. Selects only those
	 * records where there doesn't exist a records with id-1 with the same
	 * values of longtitude, latitude, brakes, flaps For example: id: 1;
	 * longtitude 2; flaps true id: 2; longtitude 2; flaps true id: 3;
	 * longtitude 3; flapse true Returns only rows with id=2 and id = 3;
	 */
	public SQLContainer getAllSimulationInfoBySimulatorId(String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery("SELECT * " + "FROM SimulationInfo si1 "
				+ "WHERE Simulation_SimulationId = (SELECT SimulationId " + "FROM simulation " + "WHERE Simulator_SimulatorId = "
				+ simulatorId + " AND IsSimulationOn = true " + "ORDER BY timestamp DESC LIMIT 1) " + "AND NOT EXISTS "
				+ "(SELECT si2.SimulationInfoId " + "FROM SimulationInfo si2 " + "WHERE si1.Longtitude = si2.Longtitude "
				+ "AND si1.Latitude = si2.Latitude " + "AND si1.SimulationInfoId = (si2.SimulationInfoId - 1 )) "
				+ "ORDER BY \"timestamp\" ASC; ", Arrays.asList("simulationinfoid"), pool);
		SQLContainer simulationInfo = null;
		try {
			simulationInfo = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationInfo;
	}

	/*
	 * Gets all simulation info for simulation with id.
	 */
	public SQLContainer getAllSimulationInfoBySimulationId(String simulationId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery("SELECT * " + "FROM SimulationInfo " + "WHERE Simulation_SimulationId = " + simulationId
				+ "ORDER BY \"timestamp\" ASC; ", Arrays.asList("simulationinfoid"), pool);
		SQLContainer simulationInfo = null;
		try {
			simulationInfo = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationInfo;
	}

	/*
	 * Gets latest simulation info record for latest simulation.
	 */
	public Item getLatestSimulationInfoBySimulatorId(String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery(
				"SELECT * FROM SimulationInfo WHERE Simulation_SimulationId=(SELECT SimulationId FROM simulation WHERE Simulator_SimulatorId="
						+ simulatorId
						+ "AND IsSimulationOn=true ORDER BY SimulationStartedTime DESC LIMIT 1) ORDER BY \"timestamp\" DESC LIMIT 1",
				Arrays.asList("simulationinfoid"), pool);
		SQLContainer simulationInfo = null;
		try {
			simulationInfo = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return DatabaseUtil.getLatestItemFromContainer(simulationInfo);
	}

	/*
	 * Checks if the last simulation on simulator with simulatorId running
	 * according to database
	 */
	public boolean isLastSimInDbRunning(String simulatorId) {
		final SQLContainer latestSimulationCont = this.getLatestSimulationContainer(simulatorId);
		Item lastSimDb = DatabaseUtil.getLatestItemFromContainer(latestSimulationCont);
		Boolean isLastSimInDbOn = (Boolean) lastSimDb.getItemProperty(SimulationCols.issimulationon.toString()).getValue();
		return (isLastSimInDbOn != null) && (isLastSimInDbOn);
	}

	/*
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

	/*
	 * Gets pfd info item by pfdinfoid
	 */
	public Item getPFDInfoByPfdInfoId(int pfdInfoId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery("SELECT * " + "FROM SimulationPfdInfo " + "WHERE pfdinfoid = " + pfdInfoId,
				Arrays.asList("pfdinfoid"), pool);
		SQLContainer simulationInfo = null;
		try {
			simulationInfo = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return DatabaseUtil.getLatestItemFromContainer(simulationInfo);
	}

	/*
	 * Gets simulation dev state item info item by pfdinfoid, closest in time to
	 * timestamp
	 */
	public Item getSimulationDevStateInfoByPfdInfoId(int pfdInfoId, long timestamp) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery("SELECT * " + "FROM simulationdevicesstate "
				+ "WHERE simulation_simulationid=(SELECT simulation_simulationid " + "FROM simulationpfdinfo " + "WHERE pfdinfoid="
				+ pfdInfoId + ") " + "ORDER BY ABS(EXTRACT(EPOCH FROM timestamp-(to_timestamp(" + timestamp
				+ "/1000)::timestamp))) limit 1", Arrays.asList("devstateid"), pool);
		SQLContainer simulationInfo = null;
		try {
			simulationInfo = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return DatabaseUtil.getLatestItemFromContainer(simulationInfo);
	}

	/*
	 * Gets simulator item by pfdinfoid
	 */
	public Item getSimulatorInfoByPfdInfoId(int pfdInfoId) throws SQLException {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery("SELECT * FROM simulator "
				+ "WHERE simulatorid = (SELECT simulator_simulatorid FROM simulation "
				+ "WHERE simulationid = (SELECT simulation_simulationid FROM " + "simulationpfdinfo WHERE " + "pfdinfoid = " + pfdInfoId
				+ "));", Arrays.asList("simulatorid"), pool);
		SQLContainer simulationInfo = null;
		simulationInfo = new SQLContainer(query);
		return DatabaseUtil.getLatestItemFromContainer(simulationInfo);
	}

	/*
	 * Gets simulation info item which corresponds to pfdidinfoid, and whose
	 * timestamp is the closest to timestamp
	 */
	public Item getSimulationInfoItemByPfdInfoIdTimestemp(int pfdInfoId, long timestamp) throws SQLException {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery("SELECT * " + "FROM simulationinfo "
				+ "WHERE simulation_simulationid=(SELECT simulation_simulationid " + "FROM simulationpfdinfo " + "WHERE pfdinfoid="
				+ pfdInfoId + ") " + "ORDER BY ABS(EXTRACT(EPOCH FROM timestamp-(to_timestamp(" + timestamp
				+ "/1000)::timestamp))) limit 1", Arrays.asList("simulationinfoid"), pool);
		SQLContainer simulationInfo = null;
		simulationInfo = new SQLContainer(query);
		return DatabaseUtil.getLatestItemFromContainer(simulationInfo);
	}
}
