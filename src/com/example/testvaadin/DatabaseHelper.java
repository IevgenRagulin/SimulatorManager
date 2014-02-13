package com.example.testvaadin;

import java.sql.SQLException;
import java.util.Arrays;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DatabaseHelper {
	JDBCConnectionPool pool = null;

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
						+ "AND \"IsSimulationOn\"=true ORDER BY \"SimulationStartedTime\" DESC LIMIT 1)",
				Arrays.asList("DevStateId"), pool);
		SQLContainer simulationDevicesState = null;
		try {
			simulationDevicesState = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return simulationDevicesState;
	}

	public DatabaseHelper() {
		initConnectionPool();
	}

	public SQLContainer getSimulatorContainer() {
		TableQuery tq = new TableQuery("simulator", pool);
		tq.setVersionColumn("Timestamp");
		SQLContainer simulatorContainer = null;
		try {
			simulatorContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

}
