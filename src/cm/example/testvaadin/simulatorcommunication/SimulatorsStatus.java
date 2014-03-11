package cm.example.testvaadin.simulatorcommunication;

import java.util.HashMap;
import java.util.Map;

public class SimulatorsStatus {
	protected static Map<String, SimulationItem> simulatorIdSimulationItem = new HashMap<String, SimulationItem>();
	protected static Map<String, SimulationDevStateItem> simulatorIdSimDevStateItem = new HashMap<String, SimulationDevStateItem>();

	protected static void setSimulationDevStateItem(String simulatorId,
			SimulationDevStateItem simDevStateItem) {
		simulatorIdSimDevStateItem.put(simulatorId, simDevStateItem);
	}

	protected static void setSimulationItem(String simulatorId,
			SimulationItem simItem) {
		simulatorIdSimulationItem.put(simulatorId, simItem);
	}

	public void fakeFunction() {

	}

	public static SimulationItem getSimulationItemBySimulatorId(
			String simulatorId) {
		return simulatorIdSimulationItem.get(simulatorId);
	}

	public static SimulationDevStateItem getSimulationDevStateItemBySimulatorId(
			String simulatorId) {
		return simulatorIdSimDevStateItem.get(simulatorId);
	}
}
