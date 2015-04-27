package cz.vutbr.fit.simulatormanager.simulatorcommunication;

import java.sql.SQLException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

import cz.vutbr.fit.simulatormanager.beans.AllEngineInfo;
import cz.vutbr.fit.simulatormanager.beans.SimulationInfoBean;
import cz.vutbr.fit.simulatormanager.data.AppConfig;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelperPureJDBC;
import cz.vutbr.fit.simulatormanager.database.columns.SimulationCols;
import cz.vutbr.fit.simulatormanager.database.columns.SimulationInfoCols;
import cz.vutbr.fit.simulatormanager.items.SimulationDevStateItem;
import cz.vutbr.fit.simulatormanager.items.SimulationInfoItem;
import cz.vutbr.fit.simulatormanager.items.SimulationPFDItem;
import cz.vutbr.fit.simulatormanager.util.DistanceUtil;

/**
 * Class which writes data about simulations to database, determines if data
 * should or should not be written to database
 * 
 * @author zhenia
 *
 */
public class DatabaseUpdater {
    final static Logger LOG = LoggerFactory.getLogger(DatabaseUpdater.class);

    protected static final double HALF_METER = 0.5;
    private static DatabaseHelper dbHelp = new DatabaseHelper();
    // in combination with saveToDbFrequency used to determine if we should save
    // data to db
    private static int addedCount = 0;
    // we save sim info less often then other data as too much data make google
    // maps slow
    private static int addedSimInfoCount = 0;

    /**
     * Adds simulation info to database. Writes data only with specified
     * frequency. Saves data only if plane has moved over more than half meter
     * (we don't want to write data about a plane that is staying on the same
     * place)
     */
    public static void addSimulationInfoToDb(SQLContainer lastSimCont, String simulatorId, RowId simulationId)
	    throws UnsupportedOperationException, SQLException {
	LOG.debug("addSimulationInfoToDb, simulatorId: {}", simulatorId);
	int saveToDbFrequency = Math.round(AppConfig.getWriteToDbFrequency()
		/ AppConfig.getSimulatorGetDataFrequency());
	int saveSimInfoToDbFrequency = Math.round(AppConfig.getWritePositionToDbFrequency()
		/ AppConfig.getSimulatorGetDataFrequency());

	Integer simulationIdInt = Integer.valueOf(simulationId.toString());
	SimulationInfoItem currentSimItem = SimulatorsStatus.getSimulationInfoItemBySimulatorId(simulatorId);
	SimulationInfoItem prevSimItem = SimulatorsStatus.getPrevSimulationInfoItemBySimulatorId(simulatorId);
	// save data to db only if plane has moved or if there is no info in
	// memory about previous simulation
	if (DistanceUtil.hasPlaneMovedMoreThan(currentSimItem, prevSimItem, HALF_METER)) {
	    addedCount = (addedCount + 1) % saveToDbFrequency;
	    addedSimInfoCount = (addedSimInfoCount + 1) % saveSimInfoToDbFrequency;
	    // save data to db only every saveToDbFrequencyStep
	    if (shouldWeSaveDataToDb()) {
		LOG.debug("addSimulationInfoToDb, simulator with id: {} has moved more than {}", simulatorId, HALF_METER);
		addDevicesStateInfoToDatabase(simulationIdInt, simulatorId);
		addPfdInfoToDatabase(simulationIdInt, simulatorId);
		addEnginesInfoToDatabase(simulationIdInt, simulatorId);
	    }
	    if (shouldWeSaveSimulationInfoToDatabase()) {
		LOG.debug("addSimulationInfoToDb, going to save sim info to db, simulator id: {}", simulatorId);
		addSimulationInfoInfoToDatabase(simulationIdInt, simulatorId);
	    }
	}
    }

    /**
     * Returns true if addedCount==0 (we save data to db every saveToDbFrequency
     * time)
     */
    private static boolean shouldWeSaveDataToDb() {
	LOG.debug("shouldWeSaveDataToDb {}", addedCount == 0);
	return addedCount == 0;
    }

    /**
     * Returns true if addedSimInfoCount==0 (we save data to db every
     * saveSimInfoToDbFrequency time)
     */
    private static boolean shouldWeSaveSimulationInfoToDatabase() {
	return addedSimInfoCount == 0;
    }

    /**
     * Saves data about devices state to database
     */
    @SuppressWarnings("unchecked")
    private static void addDevicesStateInfoToDatabase(Integer simulationIdInt, String simulatorId)
	    throws UnsupportedOperationException, SQLException {
	SQLContainer simDevStCont = dbHelp.getSimulationDevicesStateContainer();
	SimulationDevStateItem simDevStItem = SimulatorsStatus.getSimulationDevStateItemBySimulatorId(simulatorId);
	RowId newSimDvStId = (RowId) simDevStCont.addItem();
	Collection<?> itemPropIds = simDevStItem.getItemPropertyIds();
	// set reference key to simulation id
	simDevStCont.getContainerProperty(newSimDvStId, SimulationInfoCols.simulation_simulationid.toString()).setValue(
		simulationIdInt);

	// Set values: elevator, eleron, rudder, throttle...
	for (Object prop : itemPropIds) {
	    String propertyName = ((String) prop);
	    simDevStCont.getContainerProperty(newSimDvStId, propertyName).setValue(
		    simDevStItem.getItemProperty(propertyName).getValue());
	}

	commitChangeInSQLContainer(simDevStCont);
    }

    /**
     * Adds primary flight display info to database
     */
    @SuppressWarnings("unchecked")
    private static void addPfdInfoToDatabase(Integer simulationIdInt, String simulatorId) throws UnsupportedOperationException,
	    SQLException {
	SQLContainer simPfdCont = dbHelp.getSimulationPFDContainer();
	SimulationPFDItem simPfdItem = SimulatorsStatus.getSimulationPFDItemBySimulatorId(simulatorId);
	RowId newPfdId = (RowId) simPfdCont.addItem();
	// set reference key to simulation id
	simPfdCont.getContainerProperty(newPfdId, SimulationInfoCols.simulation_simulationid.toString())
		.setValue(simulationIdInt);

	Collection<?> itemPropIds = simPfdItem.getItemPropertyIds();
	// set values roll, pitch, heading, truecourse...
	for (Object prop : itemPropIds) {
	    String propertyName = ((String) prop);
	    simPfdCont.getContainerProperty(newPfdId, propertyName).setValue(simPfdItem.getItemProperty(propertyName).getValue());
	}
	commitChangeInSQLContainer(simPfdCont);

    }

    @SuppressWarnings("unchecked")
    private static void addSimulationInfoInfoToDatabase(Integer simulationId, String simulatorId)
	    throws UnsupportedOperationException, SQLException {
	SQLContainer simInfoCont = dbHelp.getSimulationInfoContainer();
	SimulationInfoBean simInfoBean = SimulatorsStatus.getSimulationInfoItemBySimulatorId(simulatorId).getBean();
	RowId newInfoId = (RowId) simInfoCont.addItem();
	LOG.debug("addSimulationInfoInfoToDatabase() - simulationIdInt:{}, simulatorId:{}, data: {}", simulationId, simulatorId,
		simInfoBean);
	simInfoCont.getContainerProperty(newInfoId, SimulationInfoCols.simulation_simulationid.toString()).setValue(simulationId);
	simInfoCont.getContainerProperty(newInfoId, SimulationInfoCols.longtitude.toString()).setValue(
		simInfoBean.getLongtitude());
	simInfoCont.getContainerProperty(newInfoId, SimulationInfoCols.latitude.toString()).setValue(simInfoBean.getLatitude());
	commitChangeInSQLContainer(simInfoCont);

    }

    /**
     * Saves data about engines state to database
     */
    private static void addEnginesInfoToDatabase(Integer simulationIdInt, String simulatorId)
	    throws UnsupportedOperationException, SQLException {
	AllEngineInfo allEnginesInfo = SimulatorsStatus.getSimulationEngineItemBySimulatorId(simulatorId);
	if (allEnginesInfo != null) {
	    DatabaseHelperPureJDBC.insertEnginesInfo(allEnginesInfo, simulationIdInt);
	}
    }

    /**
     * Create new simulation session which is in RUNNING, PAUSED state
     * 
     */
    @SuppressWarnings("unchecked")
    public static RowId createNewRunningPausedSimulation(SQLContainer lastSimCont, String simulatorId)
	    throws UnsupportedOperationException, SQLException {
	RowId id = (RowId) lastSimCont.addItem();
	lastSimCont.getContainerProperty(id, SimulationCols.simulator_simulatorid.toString()).setValue(
		Integer.valueOf(simulatorId));
	lastSimCont.getContainerProperty(id, SimulationCols.issimulationon.toString()).setValue(SimulatorsStatus.SIMULATION_ON);
	lastSimCont.getContainerProperty(id, SimulationCols.issimulationpaused.toString()).setValue(
		SimulatorsStatus.SIMULATION_PAUSED);
	commitChangeInSQLContainer(lastSimCont);
	return id;
    }

    /**
     * Create new simulation session which is in RUNNING, NOT PAUSED state
     */
    @SuppressWarnings("unchecked")
    public static RowId createNewRunningNotPausedSimulation(SQLContainer lastSimCont, String simulatorId)
	    throws UnsupportedOperationException, SQLException {
	LOG.debug("Create new RUNNING, NOT PAUSED simulation");
	RowId id = (RowId) lastSimCont.addItem();
	lastSimCont.getContainerProperty(id, SimulationCols.simulator_simulatorid.toString()).setValue(
		Integer.valueOf(simulatorId));
	lastSimCont.getContainerProperty(id, SimulationCols.issimulationon.toString()).setValue(SimulatorsStatus.SIMULATION_ON);
	lastSimCont.getContainerProperty(id, SimulationCols.issimulationpaused.toString()).setValue(
		SimulatorsStatus.SIMULATION_NOT_PAUSED);
	commitChangeInSQLContainer(lastSimCont);
	return id;
    }

    /**
     * Set existing simulation to RUNNING, PAUSED state
     */
    public static void setSimOnPausedState(SQLContainer lastSimCont, Item simulation) throws UnsupportedOperationException,
	    SQLException {
	setSimState(lastSimCont, simulation, SimulatorsStatus.SIMULATION_ON, SimulatorsStatus.SIMULATION_PAUSED);
    }

    /**
     * Set existing simulation to RUNNING, NOT PAUSED state
     */
    public static void setSimOnNotPausedState(SQLContainer lastSimCont, Item simulation) throws UnsupportedOperationException,
	    SQLException {
	setSimState(lastSimCont, simulation, SimulatorsStatus.SIMULATION_ON, SimulatorsStatus.SIMULATION_NOT_PAUSED);
    }

    @SuppressWarnings("unchecked")
    private static void setSimState(SQLContainer lastSimCont, Item simulation, boolean statusRun, boolean statusPause)
	    throws UnsupportedOperationException, SQLException {
	LOG.debug("Set sim state. ACTIVE: {}, PAUSED: {}", statusRun, statusPause);
	simulation.getItemProperty(SimulationCols.issimulationon.toString()).setValue(statusRun);
	simulation.getItemProperty(SimulationCols.issimulationpaused.toString()).setValue(statusPause);
	commitChangeInSQLContainer(lastSimCont);
    }

    /**
     * Set simulation to OFF, NOT PAUSED state
     */
    @SuppressWarnings("unchecked")
    public static void setSimOffNotPausedState(SQLContainer lastSimCont, Item lastSim) throws UnsupportedOperationException,
	    SQLException {
	LOG.debug("Set sim state. ACTIVE: {}, PAUSED: {}", false, false);
	lastSim.getItemProperty(SimulationCols.issimulationon.toString()).setValue(SimulatorsStatus.SIMULATION_OFF);
	lastSim.getItemProperty(SimulationCols.issimulationpaused.toString()).setValue(SimulatorsStatus.SIMULATION_NOT_PAUSED);
	commitChangeInSQLContainer(lastSimCont);
    }

    private static void commitChangeInSQLContainer(SQLContainer sqlCont) throws UnsupportedOperationException, SQLException {
	sqlCont.commit();
    }
}
