package cm.example.testvaadin.simulatorcommunication;

import java.util.Collection;

import com.example.testvaadin.beans.SimulationBean;
import com.example.testvaadin.data.ColumnNames;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class SimulatorsStatus {
	private static SQLContainer simulatorContainer = null;
	private static SimulationItem simulationItem = null;

	public SimulatorsStatus(SQLContainer simulatorContainer) {
		this.simulatorContainer = simulatorContainer;
		Collection<?> itemIds = simulatorContainer.getItemIds();
		SimulationBean simulationBean = new SimulationBean("1", "1", true,
				true, "10:00", "12:00");
		simulationItem = new SimulationItem(simulationBean);
		IndexedContainer container = new IndexedContainer();

		for (Object itemId : itemIds) {
			Item item = simulatorContainer.getItem(itemId);
			System.out.println(simulatorContainer.getItem(itemId)
					.getItemProperty(ColumnNames.getSimulatorIdPropName())
					.getValue().toString());
		}

	}

	// public static SQLContainer getSimulatorContainer() {
	// return simulatorContainer;
	// }

	public static SimulationItem getSimulationItem() {
		return simulationItem;
	}
}
