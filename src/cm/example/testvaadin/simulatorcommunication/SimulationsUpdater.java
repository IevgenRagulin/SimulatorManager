package cm.example.testvaadin.simulatorcommunication;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.example.testvaadin.beans.SimulationBean;
import com.example.testvaadin.beans.SimulationDevStateBean;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.data.DatabaseHelper;
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
			System.out.println("beep");
			updateSimulationsInfo();
		}
	};
	private static final ScheduledFuture<?> beeperHandle = scheduler
			.scheduleAtFixedRate(beeper, 0, 1, TimeUnit.SECONDS);

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
			String simulatorId = simulatorContainer.getItem(itemId)
					.getItemProperty(ColumnNames.getSimulatorIdPropName())
					.getValue().toString();
			String simulatorHostname = simulatorContainer.getItem(itemId)
					.getItemProperty(ColumnNames.getSimulatorHostname())
					.getValue().toString();
			int simulatorPort = (Integer) simulatorContainer.getItem(itemId)
					.getItemProperty(ColumnNames.getSimulatorPortName())
					.getValue();

			SimulationItem simulationItem = getSimulationData(simulatorId);
			SimulatorsStatus.setSimulationItem(simulatorId, simulationItem);
			updateSimulationStateData(simulatorId, simulatorHostname,
					simulatorPort);
		}
	}

	private static void updateSimulationStateData(String simulatorId,
			String simulatorHostname, int simulatorPort) {
		SimulationDevStateBean bean = new SimulationDevStateBean(1, 1, 1, 1, 1,
				false, 1);
		SimulationDevStateItem item = new SimulationDevStateItem(bean);
		SocketHelper.getSimulationData(simulatorHostname, simulatorPort);
		SimulatorsStatus.setSimulationDevStateItem(simulatorId, item);
	}

	public static SimulationItem getSimulationData(String simulatorId) {
		Item latestRunningSimulation = dbHelp
				.getLatestRunningSimulationOnSimulatorWithId(simulatorId);
		SimulationBean simBean = new SimulationBean(latestRunningSimulation);
		SimulationItem simItem = new SimulationItem(simBean);
		return simItem;
	}

}
