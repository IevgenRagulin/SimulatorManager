package com.example.testvaadin.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.testvaadin.RunningSimulationsView;
import com.example.testvaadin.data.ColumnNames;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;

public class SelectSimulatorCombo extends ComboBox {
	private static final long serialVersionUID = -3343300838743270165L;
	private RunningSimulationsView runningSims;
	private Map<String, RowId> simulatorsIdNamesMapping = new HashMap<String, RowId>();
	private ScheduledExecutorService ses = Executors
			.newSingleThreadScheduledExecutor();;

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
			setSimulatorInfoData(selectedItem);
			setSimulationInfoData(getSimulatorIdByRowId(rowId));
			// scheduleUpdatesForSelectedSimulator(getSimulatorIdByRowId(rowId));
		} else {
			runningSims.getSimulatorInfo().setEnabled(false);
			runningSims.getSimulationInfo().setEnabled(false);
		}
	}

	private Property<?> getSimulatorIdByRowId(RowId rowId) {
		return runningSims.getDBHelp().getSimulatorContainer()
				.getContainerProperty(rowId, "SimulatorId");
	}

	private void scheduleUpdatesForSelectedSimulator(
			final Property<?> simulatorId) {
		ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					setSimulationInfoData(simulatorId);
					System.out.println("Update");
				} catch (Exception e) {
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
	}

	public void unscheduleUpdates() {
		ses.shutdownNow();
	}

	private void setSimulatorInfoData(Item selectedItem) {
		runningSims.getSimulatorInfo().setItemDataSource(selectedItem);
		runningSims.getSimulatorInfo().setReadOnly(true);
		runningSims.getSimulatorInfo().setEnabled(true);
	}

	private void setSimulationInfoData(final Property<?> property) {
		final SQLContainer simulationContainer = runningSims.getDBHelp()
				.getLatestRunningSimulationOnSimulatorWithId(
						property.getValue().toString());
		if (simulationContainer.size() == 0) {
			Notification
					.show("There are no simulations currently running on this simulator");
			runningSims.getSimulationInfo().setEnabled(false);
		} else {
			final RowId id = (RowId) simulationContainer.getIdByIndex(0);
			runningSims.getSimulationInfo().setItemDataSource(
					simulationContainer.getItem(id));
			runningSims.getSimulationInfo().setEnabled(true);
			runningSims.getSimulationInfo().setReadOnly(true);
		}
	}
}
