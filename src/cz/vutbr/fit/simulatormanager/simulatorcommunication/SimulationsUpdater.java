package cz.vutbr.fit.simulatormanager.simulatorcommunication;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

import cz.vutbr.fit.simulatormanager.beans.AllEngineInfo;
import cz.vutbr.fit.simulatormanager.beans.AllSimulationInfo;
import cz.vutbr.fit.simulatormanager.beans.SimulationBean;
import cz.vutbr.fit.simulatormanager.beans.SimulationDevStateBean;
import cz.vutbr.fit.simulatormanager.beans.SimulationInfoBean;
import cz.vutbr.fit.simulatormanager.beans.SimulationPFDBean;
import cz.vutbr.fit.simulatormanager.dao.SimulationDao;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;
import cz.vutbr.fit.simulatormanager.database.DatabaseUtil;
import cz.vutbr.fit.simulatormanager.database.columns.SimulationCols;
import cz.vutbr.fit.simulatormanager.items.SimulationDevStateItem;
import cz.vutbr.fit.simulatormanager.items.SimulationInfoItem;
import cz.vutbr.fit.simulatormanager.items.SimulationItem;
import cz.vutbr.fit.simulatormanager.items.SimulationPFDItem;

public class SimulationsUpdater implements Runnable {
    final static Logger LOG = LoggerFactory.getLogger(SimulationsUpdater.class);

    protected static DatabaseHelper dbHelp = new DatabaseHelper();
    protected static SimulatorsStatus simStatus = null;
    private String simulatorId = null;
    private String simulatorHostname = null;
    private int simulatorPort;

    public SimulationsUpdater(final String simulatorId, final String simulatorHostname, final int simulatorPort) {
	this.simulatorId = simulatorId;
	this.simulatorHostname = simulatorHostname;
	this.simulatorPort = simulatorPort;
    }

    @Override
    public void run() {
	try {
	    updateSimulationStateData();
	    SimulationItem simulationItem = getLatestSimulationFromDb(simulatorId);
	    SimulatorsStatus.setSimulationItem(simulatorId, simulationItem);
	} catch (Exception e) {
	    LOG.error("Exception occured while trying to update simulation state data", e);
	}
    }

    protected synchronized void updateSimulationStateData() throws UnsupportedOperationException, SQLException {
	AllSimulationInfo allSimInfo = AWComClient.getSimulationData(simulatorHostname, simulatorPort);
	AllEngineInfo allEngineInfo = AWComClient.getEngineData(simulatorHostname, simulatorPort);
	if (allSimInfo != null) {
	    updateSimulDevState(allSimInfo);
	    updateSimulInfo(allSimInfo);
	    updateSimulPFD(allSimInfo);
	}

	if (allEngineInfo != null) {
	    updateSimulEnginesState(allEngineInfo);
	}

	updateSimulationStateInDatabase(allSimInfo, allEngineInfo);
    }

    /**
     * Sets simulation status (running, paused, not running), writes latest data
     * from simulator to database TODO: abstract checking status to interface
     */
    private synchronized void updateSimulationStateInDatabase(AllSimulationInfo dataFromSimulator,
	    AllEngineInfo allEngineInfo) throws UnsupportedOperationException, SQLException {
	SQLContainer lastSimCont = dbHelp.getLatestSimulationContainer(simulatorId);
	Item lastSimDb = DatabaseUtil.getLatestItemFromContainer(lastSimCont);
	// is simulator on or paused based on data from db
	boolean isLastSimInDBOn = false;
	Boolean isLastSimInDBPaused = null;
	// is simulator on or paused based on simulators response
	Boolean isCurrentSimPaused = null;
	Boolean isCurrentSimRunning = SimulationStatusProviderSimpleImpl.isSimulatorRunning(dataFromSimulator,
		simulatorId);
	if (lastSimDb != null) {
	    isLastSimInDBOn = (Boolean) lastSimDb.getItemProperty(SimulationCols.issimulationon.toString()).getValue();
	    isLastSimInDBPaused = (Boolean) lastSimDb.getItemProperty(SimulationCols.issimulationpaused.toString())
		    .getValue();
	}

	if (dataFromSimulator != null) {
	    isCurrentSimPaused = dataFromSimulator.getSimulationPaused();
	}

	LOG.trace("isLastSimInDBOn: {}, isLastSimInDBPaused: {}", isLastSimInDBOn, isLastSimInDBPaused);
	LOG.trace("isCurrentSimulationPaused: {}, isCurrentSimulationRunning: {}", isCurrentSimPaused,
		isCurrentSimRunning);

	if (!isCurrentSimRunning) {
	    // simulator is not running
	    updateSimulationStateInDatabaseSimulatorOff(lastSimCont, lastSimDb, isLastSimInDBOn, isLastSimInDBPaused);
	} else if ((dataFromSimulator != null) && (isCurrentSimPaused)) {
	    // simulator is paused
	    updateSimulationStateInDatabaseSimulatorPaused(lastSimCont, lastSimDb, isLastSimInDBOn, isLastSimInDBPaused);
	} else if (dataFromSimulator != null) {
	    // simulator is running
	    updateSimulationStateInDatabaseSimulatorOn(lastSimCont, lastSimDb, isLastSimInDBOn, isLastSimInDBPaused);
	}
	LOG.trace("Update sim state in db. Simulator id: {}. dataFromSimulator: {}", simulatorId, dataFromSimulator);
    }

    /**
     * Set simulation to OFF; NOT PAUSED state in database
     */
    private synchronized void updateSimulationStateInDatabaseSimulatorOff(SQLContainer lastSimCont, Item lastSim,
	    Boolean isLastSimInDBOn, Boolean isLastSimInDBPaused) throws UnsupportedOperationException, SQLException {
	if (lastSim == null) {
	    // if we don't have any simulations in db, do nothing
	} else if (hasSimulationStateChanged(isLastSimInDBOn, isLastSimInDBPaused, SimulatorsStatus.SIMULATION_OFF,
		SimulatorsStatus.SIMULATION_NOT_PAUSED)) {
	    DatabaseUpdater.setSimOffNotPausedState(lastSimCont, lastSim);
	} else {
	    // if nothing changed, do nothing
	}
    }

    private synchronized boolean hasSimulationStateChanged(Boolean isLastSimInDBOn, Boolean isLastSimInDBPaused,
	    Boolean isSimulatorActuallyOn, Boolean isSimulatorActuallyPaused) {
	return ((!isLastSimInDBOn.equals(isSimulatorActuallyOn)) || (!isLastSimInDBPaused
		.equals(isSimulatorActuallyPaused)));
    }

    private synchronized void updateSimulationStateInDatabaseSimulatorPaused(SQLContainer lastSimCont, Item lastSim,
	    Boolean isLastSimInDBOn, Boolean isLastSimInDBPaused) throws UnsupportedOperationException, SQLException {
	// simulator is on, but is on pause
	if (lastSim == null) {
	    DatabaseUpdater.createNewRunningPausedSimulation(lastSimCont, simulatorId);
	} else if (isLastSimInDBOn && isLastSimInDBPaused) {
	    // DatabaseUpdater.setSimOnPausedState(lastSimCont, lastSim);
	} else if (isLastSimInDBOn && !(isLastSimInDBPaused)) {
	    DatabaseUpdater.setSimOnPausedState(lastSimCont, lastSim);
	} else if (!isLastSimInDBOn && isLastSimInDBPaused) {
	    throw new IllegalStateException("Simulator cannot be off and paused at the same time");
	} else if (!isLastSimInDBOn && (!isLastSimInDBPaused)) {
	    DatabaseUpdater.createNewRunningPausedSimulation(lastSimCont, simulatorId);
	}
    }

    private synchronized void updateSimulationStateInDatabaseSimulatorOn(SQLContainer lastSimCont, Item lastSim,
	    Boolean isLastSimInDBOn, Boolean isLastSimInDBPaused) throws UnsupportedOperationException, SQLException {
	RowId simulationId = null;
	if (lastSim == null) {
	    DatabaseUpdater.createNewRunningNotPausedSimulation(lastSimCont, simulatorId);
	    simulationId = (RowId) dbHelp.getLatestSimulationContainer(simulatorId).getIdByIndex(0);
	} else if (isLastSimInDBOn && isLastSimInDBPaused) {
	    DatabaseUpdater.setSimOnNotPausedState(lastSimCont, lastSim);
	    simulationId = (RowId) lastSimCont.getIdByIndex(0);
	} else if (isLastSimInDBOn && !(isLastSimInDBPaused)) {
	    DatabaseUpdater.setSimOnNotPausedState(lastSimCont, lastSim);
	    simulationId = (RowId) lastSimCont.getIdByIndex(0);
	} else if (!isLastSimInDBOn && isLastSimInDBPaused) {
	    throw new IllegalStateException("Simulator cannot be off and paused at the same time");
	} else if (!isLastSimInDBOn && (!isLastSimInDBPaused)) {
	    DatabaseUpdater.createNewRunningNotPausedSimulation(lastSimCont, simulatorId);
	    simulationId = (RowId) dbHelp.getLatestSimulationContainer(simulatorId).getIdByIndex(0);
	}
	DatabaseUpdater.addSimulationInfoToDb(lastSimCont, simulatorId, simulationId);
    }

    private synchronized void updateSimulPFD(AllSimulationInfo allSimInfo) {
	SimulationPFDBean simPFDBean = new SimulationPFDBean(allSimInfo);
	SimulationPFDItem simPFDItem = new SimulationPFDItem(simPFDBean);
	SimulatorsStatus.setSimulationPFDItem(simulatorId, simPFDItem);
    }

    private synchronized void updateSimulInfo(AllSimulationInfo allSimInfo) {
	SimulationInfoBean simInfoBean = new SimulationInfoBean(allSimInfo);
	SimulationInfoItem simInfoItem = new SimulationInfoItem(simInfoBean);
	SimulatorsStatus.setSimulationInfoItem(simulatorId, simInfoItem);
    }

    private synchronized void updateSimulDevState(AllSimulationInfo allSimInfo) {
	SimulationDevStateBean bean = new SimulationDevStateBean(allSimInfo);
	SimulationDevStateItem item = new SimulationDevStateItem(bean);
	SimulatorsStatus.setSimulationDevStateItem(simulatorId, item);
    }

    private synchronized void updateSimulEnginesState(AllEngineInfo allSimInfoBean) {
	SimulatorsStatus.setSimulationEnginesStateItem(simulatorId, allSimInfoBean);
    }

    public synchronized SimulationItem getLatestSimulationFromDb(String simulatorId) {
	SimulationDao simulationDao = new SimulationDao();
	Item latestRunningSimulation = simulationDao.getLatestRunningSimulationOnSimulatorWithId(simulatorId);
	SimulationBean simBean = new SimulationBean(latestRunningSimulation);
	SimulationItem simItem = new SimulationItem(simBean);
	return simItem;
    }

}
