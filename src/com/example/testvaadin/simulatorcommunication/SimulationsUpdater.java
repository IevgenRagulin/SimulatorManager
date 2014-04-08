package com.example.testvaadin.simulatorcommunication;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.testvaadin.beans.SimulationBean;
import com.example.testvaadin.beans.SimulationDevStateBean;
import com.example.testvaadin.beans.SimulationInfoBean;
import com.example.testvaadin.beans.SimulationPFDBean;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.data.DatabaseHelper;
import com.example.testvaadin.items.SimulationDevStateItem;
import com.example.testvaadin.items.SimulationInfoItem;
import com.example.testvaadin.items.SimulationItem;
import com.example.testvaadin.items.SimulationPFDItem;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class SimulationsUpdater {
	protected static DatabaseHelper dbHelp = new DatabaseHelper();
	protected static SimulatorsStatus simStatus = null;
	protected static final int UPDATE_RATE_MS = 300;

	private final static ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);
	private static final Runnable beeper = new Runnable() {
		@Override
		public void run() {
			try {
				updateRunningSimsStatus();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	static {
		System.out.println("GOING TO SCHEDULE AT FIXED RATE");
		scheduler.scheduleAtFixedRate(beeper, 0, UPDATE_RATE_MS,
				TimeUnit.MILLISECONDS);
	}

	private SimulationsUpdater() {
	}

	public static void fakeFunction() {

	}

	public static void updateRunningSimsStatus() {
		SQLContainer simulatorContainer = dbHelp.getNewSimulatorContainer();
		updateSimulatorsStatus(simulatorContainer);
	}

	public static void updateSimulatorsStatus(SQLContainer simulatorContainer) {
		Collection<?> itemIds = simulatorContainer.getItemIds();
		// Iterate over simulators, update information about running simulations
		for (Object itemId : itemIds) {
			Item simulatorItem = simulatorContainer.getItem(itemId);
			String simulatorId = getSimulatorIdFromSimItem(simulatorItem);
			String simulatorHostname = getSimulatorHostnameFromSimItem(simulatorItem);
			int simulatorPort = getSimulatorPortFromSimItem(simulatorItem);
			updateSimulatorStatusInAThread(simulatorId, simulatorHostname,
					simulatorPort);
		}
	}

	private static String getSimulatorIdFromSimItem(Item simulatorItem) {
		return simulatorItem
				.getItemProperty(ColumnNames.getSimulatorIdPropName())
				.getValue().toString();
	}

	private static String getSimulatorHostnameFromSimItem(Item simulatorItem) {
		return simulatorItem
				.getItemProperty(ColumnNames.getSimulatorHostname()).getValue()
				.toString();
	}

	private static int getSimulatorPortFromSimItem(Item simulatorItem) {
		return (Integer) simulatorItem.getItemProperty(
				ColumnNames.getSimulatorPortName()).getValue();
	}

	private static void updateSimulatorStatusInAThread(
			final String simulatorId, final String simulatorHostname,
			final int simulatorPort) {
		// new Thread(new Runnable() {
		// public void run() {
		updateSimulationStateData(simulatorId, simulatorHostname, simulatorPort);
		SimulationItem simulationItem = getLatestSimulationData(simulatorId);
		SimulatorsStatus.setSimulationItem(simulatorId, simulationItem);
		// }
		// }).start();

	}

	private static void updateSimulationStateData(String simulatorId,
			String simulatorHostname, int simulatorPort) {
		AllSimulationInfo allSimInfo = SocketHelper.getSimulationData(
				simulatorHostname, simulatorPort);
		if (allSimInfo != null) {
			SimulationsUpdater.updateSimulDevStateData(allSimInfo, simulatorId);
			SimulationsUpdater.updateSimulInfoData(allSimInfo, simulatorId);
			SimulationsUpdater.updateSimulPFDData(allSimInfo, simulatorId);
		}
		updateSimulationStateInDatabase(allSimInfo, simulatorId);
	}

	/*
	 * Sets simulation status (running, paused, not running), writes latest data
	 * from simulator to database TODO: abstract checking status to interface
	 */
	private static void updateSimulationStateInDatabase(
			AllSimulationInfo dataFromSimulator, String simulatorId) {
		SQLContainer lastSimCont = dbHelp
				.getLatestSimulationContainer(simulatorId);
		Item lastSimDb = dbHelp.getLatestItemFromContainer(lastSimCont);
		// is simulator on or paused based on data from db
		boolean isLastSimInDBOn = false;
		Boolean isLastSimInDBPaused = null;
		// is simulator on or paused based on simulators response
		Boolean isCurrentSimulationPaused = null;
		SimulationStatusProviderSimpleImpl simStatusChecker = new SimulationStatusProviderSimpleImpl();
		Boolean isCurrentSimulationRunning = simStatusChecker
				.isSimulatorRunning(dataFromSimulator, simulatorId);

		if (lastSimDb != null) {
			isLastSimInDBOn = (Boolean) lastSimDb.getItemProperty(
					ColumnNames.getIssimulationon()).getValue();
			isLastSimInDBPaused = (Boolean) lastSimDb.getItemProperty(
					ColumnNames.getIssimulationpaused()).getValue();
		}

		if (dataFromSimulator != null) {
			isCurrentSimulationPaused = dataFromSimulator.getSimulationPaused();
		}

		if (!isCurrentSimulationRunning) {
			// simulator is not running
			updateSimulationStateInDatabaseSimulatorOff(lastSimCont, lastSimDb,
					isLastSimInDBOn, isLastSimInDBPaused);
		} else if ((dataFromSimulator != null) && (isCurrentSimulationPaused)) {
			// simulator is paused
			updateSimulationStateInDatabaseSimulatorPaused(lastSimCont,
					lastSimDb, isLastSimInDBOn, isLastSimInDBPaused,
					simulatorId);
		} else if (dataFromSimulator != null) {
			// simulator is running
			updateSimulationStateInDatabaseSimulatorOn(lastSimCont, lastSimDb,
					isLastSimInDBOn, isLastSimInDBPaused, simulatorId);
		}
	}

	private static boolean shouldWeCreateNewSimInDb(SQLContainer lastSimCont,
			Item lastSim, Boolean isLastSimInDBOn) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * Set simulation to OFF; NOT PAUSED state in database
	 */
	private static void updateSimulationStateInDatabaseSimulatorOff(
			SQLContainer lastSimCont, Item lastSim, Boolean isLastSimInDBOn,
			Boolean isLastSimInDBPaused) {
		if (lastSim == null) {
			// if we don't have any simulations in db, do nothing
		} else if (hasSimulationStateChanged(isLastSimInDBOn,
				isLastSimInDBPaused, SimulatorsStatus.SIMULATION_OFF,
				SimulatorsStatus.SIMULATION_NOT_PAUSED)) {
			DatabaseUpdater.setSimOffNotPausedState(lastSimCont, lastSim);
		} else {
			// if nothing changed, do nothing
		}
	}

	private static boolean hasSimulationStateChanged(Boolean isLastSimInDBOn,
			Boolean isLastSimInDBPaused, Boolean isSimulatorActuallyOn,
			Boolean isSimulatorActuallyPaused) {
		return !((isLastSimInDBOn.equals(isSimulatorActuallyOn)) && (isLastSimInDBPaused
				.equals(isSimulatorActuallyPaused)));
	}

	private static void updateSimulationStateInDatabaseSimulatorPaused(
			SQLContainer lastSimCont, Item lastSim, Boolean isLastSimInDBOn,
			Boolean isLastSimInDBPaused, String simulatorId) {
		// simulator is on, but is on pause
		if (lastSim == null) {
			DatabaseUpdater.createNewRunningPausedSimulation(lastSimCont,
					simulatorId);
		} else if (isLastSimInDBOn && isLastSimInDBPaused) {

		} else if (isLastSimInDBOn && !(isLastSimInDBPaused)) {
			DatabaseUpdater.setSimOnPausedState(lastSimCont, lastSim);
		} else if (!isLastSimInDBOn && isLastSimInDBPaused) {
			throw new IllegalStateException(
					"Simulator cannot be off and paused at the same time");
		} else if (!isLastSimInDBOn && (!isLastSimInDBPaused)) {
			DatabaseUpdater.createNewRunningPausedSimulation(lastSimCont,
					simulatorId);
		}
	}

	private static void updateSimulationStateInDatabaseSimulatorOn(
			SQLContainer lastSimCont, Item lastSim, Boolean isLastSimInDBOn,
			Boolean isLastSimInDBPaused, String simulatorId) {
		RowId simulationId = null;
		if (lastSim == null) {
			DatabaseUpdater.createNewRunningNotPausedSimulation(lastSimCont,
					simulatorId);
			simulationId = (RowId) dbHelp.getLatestSimulationContainer(
					simulatorId).getIdByIndex(0);
		} else if (isLastSimInDBOn && isLastSimInDBPaused) {
			DatabaseUpdater.setSimOnNotPausedState(lastSimCont, lastSim);
			simulationId = (RowId) lastSimCont.getIdByIndex(0);
		} else if (isLastSimInDBOn && !(isLastSimInDBPaused)) {
			simulationId = (RowId) lastSimCont.getIdByIndex(0);
		} else if (!isLastSimInDBOn && isLastSimInDBPaused) {
			throw new IllegalStateException(
					"Simulator cannot be off and paused at the same time");
		} else if (!isLastSimInDBOn && (!isLastSimInDBPaused)) {
			DatabaseUpdater.createNewRunningNotPausedSimulation(lastSimCont,
					simulatorId);
			simulationId = (RowId) dbHelp.getLatestSimulationContainer(
					simulatorId).getIdByIndex(0);
		}
		DatabaseUpdater.addSimulationInfoToDatabase(lastSimCont, simulatorId,
				simulationId);
	}

	private static void updateSimulPFDData(AllSimulationInfo allSimInfo,
			String simulatorId) {
		SimulationPFDBean simPFDBean = new SimulationPFDBean(allSimInfo);
		SimulationPFDItem simPFDItem = new SimulationPFDItem(simPFDBean);
		SimulatorsStatus.setSimulationPFDItem(simulatorId, simPFDItem);
	}

	private static void updateSimulInfoData(AllSimulationInfo allSimInfo,
			String simulatorId) {
		SimulationInfoBean simInfoBean = new SimulationInfoBean(allSimInfo);
		SimulationInfoItem simInfoItem = new SimulationInfoItem(simInfoBean);
		SimulatorsStatus.setSimulationInfoItem(simulatorId, simInfoItem);
	}

	private static void updateSimulDevStateData(AllSimulationInfo allSimInfo,
			String simulatorId) {
		SimulationDevStateBean bean = new SimulationDevStateBean(allSimInfo);
		SimulationDevStateItem item = new SimulationDevStateItem(bean);
		SimulatorsStatus.setSimulationDevStateItem(simulatorId, item);
	}

	public static SimulationItem getLatestSimulationData(String simulatorId) {
		Item latestRunningSimulation = dbHelp
				.getLatestRunningSimulationOnSimulatorWithId(simulatorId);
		SimulationBean simBean = new SimulationBean(latestRunningSimulation);
		SimulationItem simItem = new SimulationItem(simBean);
		return simItem;
	}

}
