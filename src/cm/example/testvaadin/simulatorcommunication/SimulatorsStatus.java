package cm.example.testvaadin.simulatorcommunication;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.items.SimulationDevStateItem;
import com.example.testvaadin.items.SimulationInfoItem;
import com.example.testvaadin.items.SimulationItem;
import com.example.testvaadin.items.SimulationPFDItem;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class SimulatorsStatus {
	protected static final boolean SIMULATION_ON = true;
	protected static final boolean SIMULATION_OFF = false;
	protected static final boolean SIMULATION_PAUSED = true;
	protected static final boolean SIMULATION_NOT_PAUSED = false;

	protected static Map<String, SimulationItem> simulatorIdSimulationItem = new HashMap<String, SimulationItem>();
	protected static Map<String, SimulationDevStateItem> simulatorIdSimDevStateItem = new HashMap<String, SimulationDevStateItem>();
	protected static Map<String, SimulationInfoItem> simulatorIdSimInfoItem = new HashMap<String, SimulationInfoItem>();
	protected static Map<String, SimulationPFDItem> simulatorIdSimPFDItem = new HashMap<String, SimulationPFDItem>();

	protected static void setSimulationDevStateItem(String simulatorId,
			SimulationDevStateItem simDevStateItem) {
		simulatorIdSimDevStateItem.put(simulatorId, simDevStateItem);
	}

	protected static void setSimulationItem(String simulatorId,
			SimulationItem simItem) {
		simulatorIdSimulationItem.put(simulatorId, simItem);
	}

	protected static void setSimulationInfoItem(String simulatorId,
			SimulationInfoItem simInfoItem) {
		simulatorIdSimInfoItem.put(simulatorId, simInfoItem);
	}

	protected static void setSimulationPFDItem(String simulatorId,
			SimulationPFDItem simPFDItem) {
		simulatorIdSimPFDItem.put(simulatorId, simPFDItem);
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

	public static SimulationInfoItem getSimulationInfoItemBySimulatorId(
			String simulatorId) {
		return simulatorIdSimInfoItem.get(simulatorId);
	}

	public static SimulationPFDItem getSimulationPFDItemBySimulatorId(
			String simulatorId) {
		return simulatorIdSimPFDItem.get(simulatorId);
	}

	@SuppressWarnings("unchecked")
	public static void setSimOnPausedState(SQLContainer lastSimCont,
			Item simulation) {
		simulation.getItemProperty(ColumnNames.getIssimulationon()).setValue(
				SimulatorsStatus.SIMULATION_ON);
		simulation.getItemProperty(ColumnNames.getIssimulationpaused())
				.setValue(SimulatorsStatus.SIMULATION_PAUSED);
		commitChangeInSQLContainer(lastSimCont);
	}

	@SuppressWarnings("unchecked")
	public static void createNewRunningPausedSimulation(
			SQLContainer lastSimCont, String simulatorId) {
		Object id = lastSimCont.addItem();
		lastSimCont.getContainerProperty(id,
				ColumnNames.getSimulatoridForeignKey()).setValue(
				Integer.valueOf(simulatorId));
		lastSimCont.getContainerProperty(id, ColumnNames.getIssimulationon())
				.setValue(true);
		lastSimCont.getContainerProperty(id,
				ColumnNames.getIssimulationpaused()).setValue(true);
		commitChangeInSQLContainer(lastSimCont);
	}

	@SuppressWarnings("unchecked")
	public static void createNewRunningNotPausedSimulation(
			SQLContainer lastSimCont, String simulatorId) {
		Object id = lastSimCont.addItem();
		lastSimCont.getContainerProperty(id,
				ColumnNames.getSimulatoridForeignKey()).setValue(
				Integer.valueOf(simulatorId));
		lastSimCont.getContainerProperty(id, ColumnNames.getIssimulationon())
				.setValue(true);
		lastSimCont.getContainerProperty(id,
				ColumnNames.getIssimulationpaused()).setValue(false);
		commitChangeInSQLContainer(lastSimCont);
	}

	@SuppressWarnings("unchecked")
	public static void setSimOnNotPausedState(SQLContainer lastSimCont,
			Item simulation) {
		simulation.getItemProperty(ColumnNames.getIssimulationon()).setValue(
				SimulatorsStatus.SIMULATION_ON);
		simulation.getItemProperty(ColumnNames.getIssimulationpaused())
				.setValue(SimulatorsStatus.SIMULATION_NOT_PAUSED);
		commitChangeInSQLContainer(lastSimCont);
	}

	private static void commitChangeInSQLContainer(SQLContainer sqlCont) {
		try {
			sqlCont.commit();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void setSimOffNotPausedState(SQLContainer lastSimCont,
			Item lastSim) {
		lastSim.getItemProperty(ColumnNames.getIssimulationon()).setValue(
				SimulatorsStatus.SIMULATION_OFF);
		lastSim.getItemProperty(ColumnNames.getIssimulationpaused()).setValue(
				SimulatorsStatus.SIMULATION_NOT_PAUSED);
		commitChangeInSQLContainer(lastSimCont);
	}
}
