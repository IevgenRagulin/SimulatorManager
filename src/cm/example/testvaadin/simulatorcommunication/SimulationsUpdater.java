package cm.example.testvaadin.simulatorcommunication;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
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
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class SimulationsUpdater {
	protected static DatabaseHelper dbHelp = new DatabaseHelper();
	protected static SimulatorsStatus simStatus = null;
	private final static ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);
	private static final Runnable beeper = new Runnable() {
		@Override
		public void run() {
			updateSimulationsInfo();
		}
	};
	private static final ScheduledFuture<?> beeperHandle = scheduler
			.scheduleAtFixedRate(beeper, 0, 5, TimeUnit.SECONDS);

	private SimulationsUpdater() {
	}

	public static void fakeFunction() {

	}

	public static void updateSimulationsInfo() {
		SQLContainer simulatorContainer = dbHelp.getSimulatorContainer();
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

			SimulationItem simulationItem = getLatestSimulationData(simulatorId);
			SimulatorsStatus.setSimulationItem(simulatorId, simulationItem);
			updateSimulationStateData(simulatorId, simulatorHostname,
					simulatorPort);
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
