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
		"select e.* from enginemodel e INNER JOIN simulatormodel ON e.simulatormodelid=simulatormodel.simulatormodelid INNER JOIN simulator ON simulatormodel.simulatormodelid=simulator.simulatormodelid where simulatorid="
			+ simulatorId, Arrays.asList("enginemodelid"), dbHelper.getPool());
	try {
	    return new SQLContainer(query);
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't get getSimulationDevicesStateBySimulatorId", e);
	}
    }
}
