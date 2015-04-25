package cz.vutbr.fit.simulatormanager.database;

import java.sql.SQLException;
import java.util.Arrays;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;

/**
 * When starting working on the project I used to place all the quries in
 * DatabaseHelper. For a better granularity, I would like to place queries to
 * different tables to different files. Here the queries to engine model table
 * are placed
 * 
 * @author zhenia
 *
 */
public class EngineModelQueries {
    /**
     * Gets engine models by simulator id.
     */
    public static SQLContainer getEngineModelsBySimulatorId(String simulatorId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery(
		"SELECT e.* from enginemodel e INNER JOIN simulatormodel ON e.simulatormodelid=simulatormodel.simulatormodelid INNER JOIN simulator ON simulatormodel.simulatormodelid=simulator.simulatormodelid WHERE simulatorid="
			+ simulatorId, Arrays.asList("enginemodelid"), DatabaseHelper.getPool());
	try {
	    return new SQLContainer(query);
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't get getSimulationDevicesStateBySimulatorId", e);
	}
    }

    /**
     * Get engine models by simulator model id (engines configured for specified
     * simulator model)
     * 
     * @param simulatorModelId
     * @return
     */
    public static SQLContainer getEngineModelsBySimulatorModelId(String simulatorModelId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * FROM enginemodel WHERE simulatormodelid=" + simulatorModelId,
		Arrays.asList("enginemodelid"), DatabaseHelper.getPool());
	try {
	    return new SQLContainer(query);
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't get getSimulationDevicesStateBySimulatorId", e);
	}
    }
}
