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
	return (Integer) item.getItemProperty(SimulationCols.simulator_simulatorid.toString()).getValue();
    }
}
