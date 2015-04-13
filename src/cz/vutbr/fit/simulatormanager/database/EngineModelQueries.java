package cz.vutbr.fit.simulatormanager.database;

import java.sql.SQLException;
import java.util.Arrays;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;

public class EngineModelQueries {
    private static DatabaseHelper dbHelper = new DatabaseHelper();

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
