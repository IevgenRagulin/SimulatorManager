package cz.vutbr.fit.simulatormanager.database;

import java.sql.SQLException;
import java.util.Arrays;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;

import cz.vutbr.fit.simulatormanager.database.columns.SimulatorModelCols;

/**
 * When starting working on the project I used to place all the quries in
 * DatabaseHelper. For a better granularity, I would like to place queries to
 * different tables to different files. Here the queries to simulator model
 * table are placed
 * 
 * @author zhenia
 *
 */
public class SimulatorModelQueries {
    private static DatabaseHelper dbHelper = new DatabaseHelper();

    /**
     * Gets simulator model by simulator id
     */
    public static Item getSimulatorModelBySimulatorId(String simulatorId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery(
		"SELECT * FROM SimulatorModel INNER JOIN Simulator ON simulatormodel.simulatormodelid=simulator.simulatormodelid where simulatorid="
			+ simulatorId, Arrays.asList("simulatormodelid"), DatabaseHelper.getPool());
	try {
	    return DatabaseUtil.getLatestItemFromContainer(new SQLContainer(query));
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't get getSimulatorModelBySimulatorId", e);
	}
    }

    /**
     * Gets simulator model by simulator model id
     */
    public static Item getSimulatorModelBySimulatorModelId(String simulatorModelId) {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * FROM SimulatorModel WHERE simulatormodelid=" + simulatorModelId,
		Arrays.asList("simulatormodelid"), DatabaseHelper.getPool());
	try {
	    return DatabaseUtil.getLatestItemFromContainer(new SQLContainer(query));
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't get getSimulationDevicesStateBySimulatorId", e);
	}
    }

    /**
     * Get simulator model with largest simulator model id
     * 
     * @param simulatorModelId
     * @return
     */
    public static Item getLatestSimulatorModel() {
	@SuppressWarnings("deprecation")
	FreeformQuery query = new FreeformQuery("SELECT * FROM SimulatorModel ORDER BY simulatormodelid desc",
		Arrays.asList("simulatormodelid"), dbHelper.getPool());
	try {
	    return DatabaseUtil.getLatestItemFromContainer(new SQLContainer(query));
	} catch (SQLException e) {
	    throw new RuntimeException("Couldn't get getSimulationDevicesStateBySimulatorId", e);
	}
    }

    /**
     * Get id of the simulator model which has been added the last
     * 
     * @return
     */
    public static Integer getLatestSimulatorModelId() {
	Item simulatorModel = getLatestSimulatorModel();
	return (Integer) simulatorModel.getItemProperty(SimulatorModelCols.simulatormodelid.toString()).getValue();
    }

    public static int getMaxSpeedOnFlapsForSimulatorModel(String simulatorModelId) {
	Item simulatorModelItem = getSimulatorModelBySimulatorModelId(simulatorModelId);
	int maxSpeedOnFlaps = (int) simulatorModelItem.getItemProperty(SimulatorModelCols.maxspeedonflaps.toString()).getValue();
	return maxSpeedOnFlaps;
    }

    public static int getNumOfLandGearsForSimulatorModel(String simulatorModelId) {
	Item simulatorModelItem = getSimulatorModelBySimulatorModelId(simulatorModelId);
	int numOfLandingGears = (int) simulatorModelItem.getItemProperty(SimulatorModelCols.numberoflandinggears.toString())
		.getValue();
	return numOfLandingGears;
    }
}
