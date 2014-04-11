package com.example.testvaadin.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.views.RunningSimulationsView;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.ComboBox;

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
		Collection<?> itemIds = runningSims.getDBHelp().getSimulatorContainer()
				.getItemIds();
		this.removeAllItems();
		for (Object itemId : itemIds) {
			Property<?> simulatorName = getSimulatorNameById((RowId) itemId);
			this.addItem(simulatorName.getValue());
			simulatorsIdNamesMapping.put((String) simulatorName.getValue(),
					(RowId) itemId);
		}
	}

	private Property<?> getSimulatorNameById(RowId itemId) {
		return runningSims.getDBHelp().getSimulatorContainer().getItem(itemId)
				.getItemProperty(ColumnNames.getSimulatorNamePropName());
	}

	private void setEventListeners() {
		this.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -2862814943819650227L;

			@Override
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				handleValueChangeEvent();
			}
		});
	}

	/*
	 * Based on selected simulator, updates the UI.
	 */
	public void handleValueChangeEvent() {
		runningSims.resetUI();
		updateUI();
	}

	/* Based on previous simulator selection, updates the UI */
	public void handleUpdatingValues() {
		System.out.println("updating ui");
		updateUI();
	}

	private boolean isLastSimInDbRunning(Property<?> simulatorId) {
		final SQLContainer latestSimulationCont = runningSims
				.getDBHelp()
				.getLatestSimulationContainer(simulatorId.getValue().toString());
		Item lastSimDb = runningSims.getDBHelp().getLatestItemFromContainer(
				latestSimulationCont);
		Boolean isLastSimInDbOn = (Boolean) lastSimDb.getItemProperty(
				ColumnNames.getIssimulationon()).getValue();
		return (isLastSimInDbOn != null) && (isLastSimInDbOn);
	}

	private void updateUI() {
		String value = (String) SelectSimulatorCombo.this.getValue();
		RowId rowId = simulatorsIdNamesMapping.get(value);
		Item selectedSimulator = runningSims.getDBHelp()
				.getNewSimulatorContainer().getItem(rowId);
		Property<?> simulatorId = runningSims.getSimulatorIdByRowId(rowId);
		// If simulator is selected
		if (selectedSimulator != null) {
			// If there are no running simulations on simulator
			if (!isLastSimInDbRunning(simulatorId)) {
				runningSims.setNoSimulationsRunningState(selectedSimulator);
			} else {
				runningSims.setAllSimulationSimulatorData(selectedSimulator);
			}
		} else {
			runningSims.setSimulatorNotSelectedState();
		}
	}
}
