package com.example.testvaadin.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.example.testvaadin.RunningSimulationsView;
import com.example.testvaadin.data.ColumnNames;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.ComboBox;

public class SelectSimulatorCombo extends ComboBox {
	private static final String NO_SIMULATOR_SELECTED = "Please, select simulator";
	private static final String EMPTY_STRING = "";
	private static final String NO_RUNNING_SIMULATIONS = "There are no simulations currently running on this simulator";
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

	public void handleValueChangeEvent() {
		// unscheduleUpdates();
		String value = (String) SelectSimulatorCombo.this.getValue();
		RowId rowId = simulatorsIdNamesMapping.get(value);
		Item selectedItem = runningSims.getDBHelp().getSimulatorContainer()
				.getItem(rowId);
		if (selectedItem != null) {
			setAllSimulationSimulatorData(rowId);
		} else {
			setSimulatorNotSelectedState();
		}
	}

	private void setAllSimulationSimulatorData(RowId rowId) {
		setSimulatorInfoData(rowId);
		setSimulationInfoData(getSimulatorIdByRowId(rowId));
		setSimulationDevicesStateInfo(getSimulatorIdByRowId(rowId));
	}

	private void setSimulatorNotSelectedState() {
		runningSims.getErrorLabel().setValue(NO_SIMULATOR_SELECTED);
		runningSims.getSimulatorInfo().setEnabled(false);
		runningSims.getSimulationInfo().setEnabled(false);
		runningSims.getSimulatorDevicesState().setEnabled(false);
	}

	private void setNoSimulationsRunningState() {
		runningSims.getErrorLabel().setValue(NO_RUNNING_SIMULATIONS);
		runningSims.getSimulationInfo().setEnabled(false);
		runningSims.getSimulatorDevicesState().setEnabled(false);
	}

	private Property<?> getSimulatorIdByRowId(RowId rowId) {
		return runningSims.getDBHelp().getSimulatorContainer()
				.getContainerProperty(rowId, "SimulatorId");
	}

	private void setSimulatorInfoData(RowId rowId) {
		Item selectedItem = runningSims.getDBHelp().getSimulatorContainer()
				.getItem(rowId);
		runningSims.getSimulatorInfo().setItemDataSource(selectedItem);
		runningSims.getSimulatorInfo().setReadOnly(true);
		runningSims.getSimulatorInfo().setEnabled(true);
	}

	private void setSimulationInfoData(final Property<?> property) {
		final SQLContainer simulationContainer = runningSims.getDBHelp()
				.getLatestRunningSimulationOnSimulatorWithId(
						property.getValue().toString());
		if (simulationContainer.size() == 0) {
			setNoSimulationsRunningState();
		} else {
			runningSims.getErrorLabel().setValue(EMPTY_STRING);
			final RowId id = (RowId) simulationContainer.getIdByIndex(0);
			runningSims.getSimulationInfo().setItemDataSource(
					simulationContainer.getItem(id));
			runningSims.getSimulationInfo().setEnabled(true);
			runningSims.getSimulationInfo().setReadOnly(true);
		}
	}

	private void setSimulationDevicesStateInfo(final Property<?> property) {
		final SQLContainer simulationDevicesStateContainer = runningSims
				.getDBHelp().getSimulationDevicesStateBySimulatorId(
						property.getValue().toString());
		if (simulationDevicesStateContainer.size() != 0) {
			final RowId id = (RowId) simulationDevicesStateContainer
					.getIdByIndex(0);
			runningSims.getSimulatorDevicesState().setItemDataSource(
					simulationDevicesStateContainer.getItem(id));
			runningSims.getSimulatorDevicesState().setEnabled(true);
			runningSims.getSimulatorDevicesState().setReadOnly(true);
		}
	}
}
