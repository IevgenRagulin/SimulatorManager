package cz.vutbr.fit.simulatormanager.database;

import java.sql.SQLException;
import java.util.Arrays;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;

import cz.vutbr.fit.simulatormanager.database.columns.SimulationCols;

/**
 * When starting working on the project I used to place all the quries in
 * DatabaseHelper. For a better granularity, I would like to place queries to
 * different tables to different files. Here the queries to simulation table are
 * placed
 * 
 * @author zhenia
 *
 */
public class SimulationQueries {

    /**
     * Get simulation by simulationid
     * 
     * @param simulatorModelId
     * @return
     */
    public static Item getSimulationBySimulationId(String simulationId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * FROM simulation WHERE simulationid=" + simulationId,
		Arrays.asList("simulationid"), DatabaseHelper.getPool());
	try {
	    SQLContainer sqlcontainer = new SQLContainer(query);
	    return DatabaseUtil.getLatestItemFromContainer(sqlcontainer);
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't get getSimulationDevicesStateBySimulatorId", e);
	}
    }

    /**
     * Gets a simulator id based on simulation id
     * 
     * @param simulationId
     * @return
     */
    public static Integer getSimulatorIdBySimulationId(String simulationId) {
	Item item = getSimulationBySimulationId(simulationId);
	return (Integer) item.getItemProperty(SimulationCols.simulatorid.toString()).getValue();
    }

    protected static DatabaseHelper dbHelp = new DatabaseHelper();

    /**
     * Returns an Item with the currently running simulation on simulator with
     * id simulatorId. If there is more than one simulation currently running
     * (which should be impossible) returns the newest one
     */
    public static Item getLatestRunningSimulationOnSimulatorWithId(String simulatorId) {
	FreeformQuery query = dbHelp.buildQuery("SELECT * FROM simulation WHERE simulatorid=" + simulatorId
		+ " AND issimulationon=true ORDER BY simulationid DESC LIMIT 1", Arrays.asList("simulationid"));
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
