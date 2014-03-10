package com.example.testvaadin.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DatabaseHelper {
	JDBCConnectionPool pool = null;
	private SQLContainer simulatorContainer = null;
	private final String DB_URL = "jdbc:postgresql://localhost/postgres";
	private String DB_USER = "postgres";
	private String DB_PASS = "password";

	/*
	 * Returns an SQLContainer with the currently running simulation on
	 * simulator with id simulatorId If there is more than one simulation
	 * currently running (which should be impossible) returns the newest one
	 */
	public SQLContainer getLatestRunningSimulationOnSimulatorWithId(
			String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery(
				"SELECT * FROM simulation WHERE \"Simulator_SimulatorId\"="
						+ simulatorId
						+ " AND \"IsSimulationOn\"=true ORDER BY \"SimulationStartedTime\" DESC LIMIT 1",
				Arrays.asList("SimulationId"), pool);
		SQLContainer runningSimulations = null;
		try {
			runningSimulations = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return runningSimulations;
	}

	public SQLContainer getSimulationDevicesStateBySimulatorId(
			String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery(
				"SELECT * FROM SimulationDevicesState WHERE \"Simulation_SimulationId\"=(SELECT \"SimulationId\" FROM simulation WHERE \"Simulator_SimulatorId\"="
						+ simulatorId
						+ "AND \"IsSimulationOn\"=true ORDER BY \"SimulationStartedTime\" DESC LIMIT 1) ORDER BY \"Timestamp\" DESC LIMIT 1",
				Arrays.asList("DevStateId"), pool);
		SQLContainer simulationDevicesState = null;
		try {
			simulationDevicesState = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationDevicesState;
	}

	public void removeSimulatorWithId(String simulatorId) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public DatabaseHelper() {
		initConnectionPool();
		initSimulatorContainer();
	}

	private void initSimulatorContainer() {
		TableQuery tq = new TableQuery("simulator", pool);
		tq.setVersionColumn("Timestamp");
		try {
			simulatorContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public SQLContainer getSimulatorContainer() {
		return simulatorContainer;
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

	public SQLContainer getSimulationDevicesStateContainer() {
		TableQuery tq = new TableQuery("simulationdevicesstate", pool);
		tq.setVersionColumn("Timestamp");
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
					"jdbc:postgresql://localhost/postgres", "postgres",
					"password", 2, 5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public SQLContainer getSimulationPFDBySimulatorId(String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery(
				"SELECT * FROM SimulationPFDInfo WHERE \"Simulation_SimulationId\"=(SELECT \"SimulationId\" FROM simulation WHERE \"Simulator_SimulatorId\"="
						+ simulatorId
						+ "AND \"IsSimulationOn\"=true ORDER BY \"SimulationStartedTime\" DESC LIMIT 1) ORDER BY \"Timestamp\" DESC LIMIT 1",
				Arrays.asList("PFDInfoId"), pool);
		SQLContainer simulationPFD = null;
		try {
			simulationPFD = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationPFD;
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
		FreeformQuery query = new FreeformQuery(
				"SELECT * "
						+ "FROM SimulationInfo si1 "
						+ "WHERE \"Simulation_SimulationId\" = (SELECT \"SimulationId\""
						+ "FROM simulation "
						+ "WHERE \"Simulator_SimulatorId\" = "
						+ simulatorId
						+ " AND \"IsSimulationOn\" = true "
						+ "ORDER BY \"SimulationStartedTime\" DESC LIMIT 1) "
						+ "AND NOT EXISTS "
						+ "(SELECT si2.\"SimulationInfoId\" "
						+ "FROM SimulationInfo si2 "
						+ "WHERE si1.\"Longtitude\" = si2.\"Longtitude\" "
						+ "AND si1.\"Latitude\" = si2.\"Latitude\" "
						+ "AND si1.\"BrakesOn\" = si2.\"BrakesOn\" "
						+ "AND si1.\"FlapsOn\" = si2.\"FlapsOn\" "
						+ "AND si1.\"SimulationInfoId\" = (si2.\"SimulationInfoId\" - 1 )) "
						+ "ORDER BY \"Timestamp\" ASC; ",
				Arrays.asList("SimulationInfoId"), pool);
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
	public SQLContainer getLatestSimulationInfoBySimulatorId(String simulatorId) {
		@SuppressWarnings("deprecation")
		FreeformQuery query = new FreeformQuery(
				"SELECT * FROM SimulationInfo WHERE \"Simulation_SimulationId\"=(SELECT \"SimulationId\" FROM simulation WHERE \"Simulator_SimulatorId\"="
						+ simulatorId
						+ "AND \"IsSimulationOn\"=true ORDER BY \"SimulationStartedTime\" DESC LIMIT 1) ORDER BY \"Timestamp\" DESC LIMIT 1",
				Arrays.asList("SimulationInfoId"), pool);
		SQLContainer simulationInfo = null;
		try {
			simulationInfo = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationInfo;
	}

}
