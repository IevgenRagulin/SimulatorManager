package com.example.testvaadin.data;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DatabaseHelper implements Serializable {
	private static final long serialVersionUID = -5027557673512708776L;
	JDBCConnectionPool pool = null;
	private SQLContainer simulatorContainer = null;

	/*
	 * Returns an Item with the currently running simulation on simulator with
	 * id simulatorId If there is more than one simulation currently running
	 * (which should be impossible) returns the newest one
	 */
	public Item getLatestRunningSimulationOnSimulatorWithId(String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery(
				"SELECT * FROM simulation WHERE Simulator_SimulatorId="
						+ simulatorId
						+ " AND IsSimulationOn=true ORDER BY simulationid DESC LIMIT 1",
				Arrays.asList("simulationid"), pool);
		SQLContainer runningSimulations = null;
		try {
			runningSimulations = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getLatestItemFromContainer(runningSimulations);
	}

	/*
	 * Returns SQLContainer with the latest running simulation on simulator with
	 * id simulatorId If there is more than one simulation currently running
	 * (which should be impossible) returns the newest one
	 */
	public SQLContainer getLatestSimulationContainer(String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery(
				"SELECT * FROM simulation WHERE Simulator_SimulatorId="
						+ simulatorId + " ORDER BY simulationid DESC LIMIT 1",
				Arrays.asList("simulationid"), pool);
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

	public Item getLatestItemFromContainer(SQLContainer container) {
		Item latestItem = null;
		if ((container != null) && (container.size() != 0)) {
			final RowId id = (RowId) container.getIdByIndex(0);
			latestItem = container.getItem(id);
		}
		return latestItem;
	}

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
		return getLatestItemFromContainer(simulationDevicesState);
	}

	public DatabaseHelper() {
		initConnectionPool();
	}

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

	public SQLContainer getNewSimulatorContainer() {
		SQLContainer newSimulatorContainer = null;
		TableQuery tq = new TableQuery("simulator", pool);
		tq.setVersionColumn("timestamp");
		try {
			newSimulatorContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newSimulatorContainer;
	}

	public SQLContainer getSimulationContainer() {
		TableQuery tq = new TableQuery("simulation", pool);
		tq.setVersionColumn("Timestamp");
		SQLContainer simulationContainer = null;
		try {
			simulationContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationContainer;
	}

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

	private void initConnectionPool() {
		try {
			pool = new SimpleJDBCConnectionPool("org.postgresql.Driver",
					ApplicationConfiguration.getDbUrl(),
					ApplicationConfiguration.getDbUserName(),
					ApplicationConfiguration.getDbUserPassword(), 2, 5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Item getSimulationPFDBySimulatorId(String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery(
				"SELECT * FROM SimulationPFDInfo WHERE Simulation_SimulationId=(SELECT SimulationId FROM simulation WHERE Simulator_SimulatorId="
						+ simulatorId
						+ "AND IsSimulationOn=true ORDER BY SimulationStartedTime DESC LIMIT 1) ORDER BY \"timestamp\" DESC LIMIT 1",
				Arrays.asList("PFDInfoId"), pool);
		SQLContainer simulationPFD = null;
		try {
			simulationPFD = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getLatestItemFromContainer(simulationPFD);
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
		FreeformQuery query = new FreeformQuery("SELECT * "
				+ "FROM SimulationInfo si1 "
				+ "WHERE Simulation_SimulationId = (SELECT SimulationId "
				+ "FROM simulation " + "WHERE Simulator_SimulatorId = "
				+ simulatorId + " AND IsSimulationOn = true "
				+ "ORDER BY SimulationStartedTime DESC LIMIT 1) "
				+ "AND NOT EXISTS " + "(SELECT si2.SimulationInfoId "
				+ "FROM SimulationInfo si2 "
				+ "WHERE si1.Longtitude = si2.Longtitude "
				+ "AND si1.Latitude = si2.Latitude "
				+ "AND si1.SimulationInfoId = (si2.SimulationInfoId - 1 )) "
				+ "ORDER BY \"timestamp\" ASC; ",
				Arrays.asList("simulationinfoid"), pool);
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
		return getLatestItemFromContainer(simulationInfo);
	}

}
