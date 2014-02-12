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
	private SQLContainer simulatorContainer = null;
	private SQLContainer simulationContainer = null;
	private SQLContainer simulationDevicesStateContainer = null;

	public SQLContainer getSimulatorContainer() {
		return simulatorContainer;
	}

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

	public SQLContainer getSimulationDevicesStateContainer() {
		return simulationDevicesStateContainer;
	}

	public DatabaseHelper() {
		initConnectionPool();
		initSimulatorContainer();
		initSimulationContainer();
		initSimulationDevicesStateContainer();
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

	private void initSimulationContainer() {
		TableQuery tq = new TableQuery("simulation", pool);
		tq.setVersionColumn("Timestamp");
		try {
			simulationContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initSimulationDevicesStateContainer() {
		TableQuery tq = new TableQuery("simulationdevicesstate", pool);
		tq.setVersionColumn("Timestamp");
		try {
			simulationDevicesStateContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initConnectionPool() {
		try {
			pool = new SimpleJDBCConnectionPool("org.postgresql.Driver",
					"jdbc:postgresql://localhost/postgres", "postgres",
					"password", 2, 5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
