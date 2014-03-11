package cm.example.testvaadin.simulatorcommunication;

import com.example.testvaadin.data.DatabaseHelper;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class SimulationsUpdater {
	protected static DatabaseHelper dbHelp = new DatabaseHelper();
	protected static SimulatorsStatus simStatus = null;

	private SimulationsUpdater() {

	}

	public static void updateSimulationsInfo() {
		System.out.println("CALLING UPDATE SIM INFO");
		SQLContainer simulatorContainer = dbHelp.getSimulatorContainer();
		simStatus = new SimulatorsStatus(simulatorContainer);
	}
}
