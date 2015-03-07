package cz.vutbr.fit.simulatormanager.dao;

import java.sql.SQLException;
import java.util.Arrays;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;

import cz.vutbr.fit.simulatormanager.beans.SimulationBean;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;
import cz.vutbr.fit.simulatormanager.database.DatabaseUtil;
import cz.vutbr.fit.simulatormanager.items.SimulationItem;


public class SimulationDao {
	protected static DatabaseHelper dbHelp = new DatabaseHelper();
	
	/*
	 * Returns an Item with the currently running simulation on simulator with
	 * id simulatorId If there is more than one simulation currently running
	 * (which should be impossible) returns the newest one
	 */
	public Item getLatestRunningSimulationOnSimulatorWithId(String simulatorId) {
		FreeformQuery query = dbHelp.buildQuery("SELECT * FROM simulation WHERE Simulator_SimulatorId=" + simulatorId
				+ " AND IsSimulationOn=true ORDER BY simulationid DESC LIMIT 1", Arrays.asList("simulationid"));
		SQLContainer runningSimulations = null;
		try {
			runningSimulations = new SQLContainer(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Item latestRunning = DatabaseUtil.getLatestItemFromContainer(runningSimulations);
		return latestRunning;
		
	}
}
