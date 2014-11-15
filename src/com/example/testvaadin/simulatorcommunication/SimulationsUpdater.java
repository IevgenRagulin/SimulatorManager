package com.example.testvaadin.simulatorcommunication;

import com.example.testvaadin.beans.AllEngineInfo;
import com.example.testvaadin.beans.AllSimulationInfo;
import com.example.testvaadin.beans.SimulationBean;
import com.example.testvaadin.beans.SimulationDevStateBean;
import com.example.testvaadin.beans.SimulationInfoBean;
import com.example.testvaadin.beans.SimulationPFDBean;
import com.example.testvaadin.data.DatabaseHelper;
import com.example.testvaadin.data.SimulationCols;
import com.example.testvaadin.items.SimulationDevStateItem;
import com.example.testvaadin.items.SimulationEnginesStateItem;
import com.example.testvaadin.items.SimulationInfoItem;
import com.example.testvaadin.items.SimulationItem;
import com.example.testvaadin.items.SimulationPFDItem;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class SimulationsUpdater implements Runnable {
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
			SimulationItem simulationItem = getLatestSimulationData();
			SimulatorsStatus.setSimulationItem(simulatorId, simulationItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void updateSimulatorStatusInAThread(final String simulatorId, final String simulatorHostname, final int simulatorPort) {
		updateSimulationStateData();
		SimulationItem simulationItem = getLatestSimulationData();
		SimulatorsStatus.setSimulationItem(simulatorId, simulationItem);
	}

	protected void updateSimulationStateData() {
		// System.out.println("Going to contact simulator " + simulatorId);
		AllSimulationInfo allSimInfo = SocketHelper.getSimulationData(simulatorHostname, simulatorPort);
		AllEngineInfo allEngineInfo = SocketHelper.getEngineData(simulatorHostname, simulatorPort);

		if (allSimInfo != null) {
			updateSimulDevStateData(allSimInfo);
			updateSimulInfoData(allSimInfo);
			updateSimulPFDData(allSimInfo);
		}

		if (allEngineInfo != null) {
			updateSimulEnginesStateData(allEngineInfo);
		}

		updateSimulationStateInDatabase(allSimInfo, allEngineInfo);
	}

	/*
	 * Sets simulation status (running, paused, not running), writes latest data
	 * from simulator to database TODO: abstract checking status to interface
	 */
	private void updateSimulationStateInDatabase(AllSimulationInfo dataFromSimulator, AllEngineInfo allEngineInfo) {
		SQLContainer lastSimCont = dbHelp.getLatestSimulationContainer(simulatorId);
		Item lastSimDb = dbHelp.getLatestItemFromContainer(lastSimCont);
		// is simulator on or paused based on data from db
		boolean isLastSimInDBOn = false;
		Boolean isLastSimInDBPaused = null;
		// is simulator on or paused based on simulators response
		Boolean isCurrentSimulationPaused = null;
		Boolean isCurrentSimulationRunning = SimulationStatusProviderSimpleImpl.isSimulatorRunning(dataFromSimulator, simulatorId);
		if (lastSimDb != null) {
			isLastSimInDBOn = (Boolean) lastSimDb.getItemProperty(SimulationCols.issimulationon.toString()).getValue();
			isLastSimInDBPaused = (Boolean) lastSimDb.getItemProperty(SimulationCols.issimulationpaused.toString()).getValue();
		}

		if (dataFromSimulator != null) {
			isCurrentSimulationPaused = dataFromSimulator.getSimulationPaused();
		}

		if (!isCurrentSimulationRunning) {
			// simulator is not running
			updateSimulationStateInDatabaseSimulatorOff(lastSimCont, lastSimDb, isLastSimInDBOn, isLastSimInDBPaused);
		} else if ((dataFromSimulator != null) && (isCurrentSimulationPaused)) {
			// simulator is paused
			updateSimulationStateInDatabaseSimulatorPaused(lastSimCont, lastSimDb, isLastSimInDBOn, isLastSimInDBPaused);
		} else if (dataFromSimulator != null) {
			// simulator is running
			updateSimulationStateInDatabaseSimulatorOn(lastSimCont, lastSimDb, isLastSimInDBOn, isLastSimInDBPaused);
		}
	}

	/*
	 * Set simulation to OFF; NOT PAUSED state in database
	 */
	private void updateSimulationStateInDatabaseSimulatorOff(SQLContainer lastSimCont, Item lastSim, Boolean isLastSimInDBOn,
			Boolean isLastSimInDBPaused) {
		if (lastSim == null) {
			// if we don't have any simulations in db, do nothing
		} else if (hasSimulationStateChanged(isLastSimInDBOn, isLastSimInDBPaused, SimulatorsStatus.SIMULATION_OFF,
				SimulatorsStatus.SIMULATION_NOT_PAUSED)) {
			DatabaseUpdater.setSimOffNotPausedState(lastSimCont, lastSim);
		} else {
			// if nothing changed, do nothing
		}
	}

	private boolean hasSimulationStateChanged(Boolean isLastSimInDBOn, Boolean isLastSimInDBPaused, Boolean isSimulatorActuallyOn,
			Boolean isSimulatorActuallyPaused) {
		return !((isLastSimInDBOn.equals(isSimulatorActuallyOn)) && (isLastSimInDBPaused.equals(isSimulatorActuallyPaused)));
	}

	private void updateSimulationStateInDatabaseSimulatorPaused(SQLContainer lastSimCont, Item lastSim, Boolean isLastSimInDBOn,
			Boolean isLastSimInDBPaused) {
		// simulator is on, but is on pause
		if (lastSim == null) {
			DatabaseUpdater.createNewRunningPausedSimulation(lastSimCont, simulatorId);
		} else if (isLastSimInDBOn && isLastSimInDBPaused) {
			DatabaseUpdater.setSimOnPausedState(lastSimCont, lastSim);
		} else if (isLastSimInDBOn && !(isLastSimInDBPaused)) {
			DatabaseUpdater.setSimOnPausedState(lastSimCont, lastSim);
		} else if (!isLastSimInDBOn && isLastSimInDBPaused) {
			throw new IllegalStateException("Simulator cannot be off and paused at the same time");
		} else if (!isLastSimInDBOn && (!isLastSimInDBPaused)) {
			DatabaseUpdater.createNewRunningPausedSimulation(lastSimCont, simulatorId);
		}
		// TODO DELETE THIS!!!!!!!
		System.out.println("SIMULATOR IS PAUSED. GOING TO SAVE DATA");
		RowId simulationId = (RowId) lastSimCont.getIdByIndex(0);
		DatabaseUpdater.addSimulationInfoToDatabase(lastSimCont, simulatorId, simulationId);
	}

	private void updateSimulationStateInDatabaseSimulatorOn(SQLContainer lastSimCont, Item lastSim, Boolean isLastSimInDBOn,
			Boolean isLastSimInDBPaused) {
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
		DatabaseUpdater.addSimulationInfoToDatabase(lastSimCont, simulatorId, simulationId);
	}

	private void updateSimulPFDData(AllSimulationInfo allSimInfo) {
		SimulationPFDBean simPFDBean = new SimulationPFDBean(allSimInfo);
		SimulationPFDItem simPFDItem = new SimulationPFDItem(simPFDBean);
		SimulatorsStatus.setSimulationPFDItem(simulatorId, simPFDItem);
	}

	private void updateSimulInfoData(AllSimulationInfo allSimInfo) {
		SimulationInfoBean simInfoBean = new SimulationInfoBean(allSimInfo);
		SimulationInfoItem simInfoItem = new SimulationInfoItem(simInfoBean);
		SimulatorsStatus.setSimulationInfoItem(simulatorId, simInfoItem);
	}

	private void updateSimulDevStateData(AllSimulationInfo allSimInfo) {
		SimulationDevStateBean bean = new SimulationDevStateBean(allSimInfo);
		SimulationDevStateItem item = new SimulationDevStateItem(bean);
		SimulatorsStatus.setSimulationDevStateItem(simulatorId, item);
	}

	private void updateSimulEnginesStateData(AllEngineInfo allSimInfoBean) {
		SimulationEnginesStateItem item = new SimulationEnginesStateItem(allSimInfoBean);
		SimulatorsStatus.setSimulationEnginesStateItem(simulatorId, item);
	}

	public SimulationItem getLatestSimulationData() {
		Item latestRunningSimulation = dbHelp.getLatestRunningSimulationOnSimulatorWithId(simulatorId);
		SimulationBean simBean = new SimulationBean(latestRunningSimulation);
		SimulationItem simItem = new SimulationItem(simBean);
		return simItem;
	}

}
