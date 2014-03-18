package cm.example.testvaadin.simulatorcommunication;

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
	private final static ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);
	private static final Runnable beeper = new Runnable() {
		@Override
		public void run() {
			try {
				updateSimulationsInfo();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	static {
		System.out.println("GOING TO SCHEDULE AT FIXED RATE");
		scheduler.scheduleAtFixedRate(beeper, 0, 1, TimeUnit.SECONDS);
	}

	private SimulationsUpdater() {
	}

	public static void fakeFunction() {

	}

	public static void updateSimulationsInfo() {
		SQLContainer simulatorContainer = dbHelp.getNewSimulatorContainer();
		updateSimulatorsStatus(simulatorContainer);
	}

	public static void updateSimulatorsStatus(SQLContainer simulatorContainer) {
		Collection<?> itemIds = simulatorContainer.getItemIds();

		for (Object itemId : itemIds) {
			Item item = simulatorContainer.getItem(itemId);
			String simulatorId = item
					.getItemProperty(ColumnNames.getSimulatorIdPropName())
					.getValue().toString();
			String simulatorHostname = item
					.getItemProperty(ColumnNames.getSimulatorHostname())
					.getValue().toString();
			int simulatorPort = (Integer) item.getItemProperty(
					ColumnNames.getSimulatorPortName()).getValue();
			updateSimulationStateData(simulatorId, simulatorHostname,
					simulatorPort);
			SimulationItem simulationItem = getLatestSimulationData(simulatorId);
			SimulatorsStatus.setSimulationItem(simulatorId, simulationItem);
		}
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

	private static void updateSimulationStateInDatabase(
			AllSimulationInfo dataFromSimulator, String simulatorId) {
		SQLContainer lastSimCont = dbHelp
				.getLatestSimulationContainer(simulatorId);
		Item lastSim = dbHelp.getLatestItemFromContainer(lastSimCont);

		Boolean isLastSimInDBOn = null;
		Boolean isLastSimInDBPaused = null;
		if (lastSim != null) {
			isLastSimInDBOn = (Boolean) lastSim.getItemProperty(
					ColumnNames.getIssimulationon()).getValue();
			isLastSimInDBPaused = (Boolean) lastSim.getItemProperty(
					ColumnNames.getIssimulationpaused()).getValue();
		}
		Boolean isCurrentSimulationPaused = null;
		if (dataFromSimulator != null) {
			isCurrentSimulationPaused = dataFromSimulator.getSimulationPaused();
		}
		if (dataFromSimulator == null) {
			// simulator is turned off
			updateSimulationStateInDatabaseSimulatorOff(lastSimCont, lastSim,
					isLastSimInDBOn, isLastSimInDBPaused);
		} else if (isCurrentSimulationPaused) {
			// simulator is paused
			updateSimulationStateInDatabaseSimulatorPaused(lastSimCont,
					lastSim, isLastSimInDBOn, isLastSimInDBPaused, simulatorId);
		} else {
			// simulator is running
			updateSimulationStateInDatabaseSimulatorOn(lastSimCont, lastSim,
					isLastSimInDBOn, isLastSimInDBPaused, simulatorId);
		}
	}

	private static void updateSimulationStateInDatabaseSimulatorOff(
			SQLContainer lastSimCont, Item lastSim, Boolean isLastSimInDBOn,
			Boolean isLastSimInDBPaused) {
		if (lastSim == null) {

		} else if (isLastSimInDBOn && isLastSimInDBPaused) {
			DatabaseUpdater.setSimOffNotPausedState(lastSimCont, lastSim);
		} else if (isLastSimInDBOn && !(isLastSimInDBPaused)) {
			DatabaseUpdater.setSimOffNotPausedState(lastSimCont, lastSim);
		} else if (!isLastSimInDBOn && isLastSimInDBPaused) {
			throw new IllegalStateException(
					"Simulator cannot be off and paused at the same time");
		} else if (!isLastSimInDBOn && (!isLastSimInDBPaused)) {

		}
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
