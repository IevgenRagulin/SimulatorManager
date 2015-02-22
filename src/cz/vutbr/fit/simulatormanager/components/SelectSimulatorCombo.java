package cz.vutbr.fit.simulatormanager.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.ui.ComboBox;

import cz.vutbr.fit.simulatormanager.data.SimulatorCols;
import cz.vutbr.fit.simulatormanager.views.RunningSimulationsView;

public class SelectSimulatorCombo extends ComboBox {
	private static final long serialVersionUID = -3343300838743270165L;
	private RunningSimulationsView runningSims;
	private Map<String, RowId> simulatorsIdNamesMapping = new HashMap<String, RowId>();

	public SelectSimulatorCombo(RunningSimulationsView runningSimulations) {
		super("Simulator name:");
		setBuffered(false);
		this.runningSims = runningSimulations;
		initSelectSimulator();
		setEventListeners();
	}

	public void initSelectSimulator() {
		this.setImmediate(true);
		Collection<?> itemIds = runningSims.getDBHelp().getSimulatorContainer().getItemIds();
		this.removeAllItems();
		for (Object itemId : itemIds) {
			Property<?> simulatorName = getSimulatorNameById((RowId) itemId);
			this.addItem(simulatorName.getValue());
			simulatorsIdNamesMapping.put((String) simulatorName.getValue(), (RowId) itemId);
		}
	}

	private Property<?> getSimulatorNameById(RowId itemId) {
		return runningSims.getDBHelp().getSimulatorContainer().getItem(itemId).getItemProperty(SimulatorCols.simulatorname.toString());
	}

	private void setEventListeners() {
		this.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -2862814943819650227L;

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				// handleValueChangeEvent();
			}
		});
	}

}
