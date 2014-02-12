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

	public SelectSimulatorCombo(RunningSimulationsView runningSimulations) {
		super("Simulator name:");
		this.runningSims = runningSimulations;
		initSelectSimulator();
	}

	private void initSelectSimulator() {
		this.setImmediate(true);
		Collection<?> itemIds = runningSims.getSqlContainer().getItemIds();
		for (Object itemId : itemIds) {
			Property simulatorName = runningSims.getSqlContainer()
					.getItem(itemId)
					.getItemProperty(ColumnNames.getSimulatorNamePropName());
			this.addItem(simulatorName.getValue());
			simulatorsIdNamesMapping.put((String) simulatorName.getValue(),
					(RowId) itemId);
		}

		this.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -2862814943819650227L;

			@Override
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				String value = (String) SelectSimulatorCombo.this.getValue();
				RowId rowId = simulatorsIdNamesMapping.get(value);
				Item selectedItem = runningSims.getSqlContainer()
						.getItem(rowId);
				setSimulatorInfoData(selectedItem);
				setSimulationInfoData(runningSims.getSqlContainer()
						.getContainerProperty(rowId, "SimulatorId"));

			}
		});
	}

	private void setSimulatorInfoData(Item selectedItem) {
		runningSims.getSimulatorInfo().setItemDataSource(selectedItem);
		runningSims.getSimulatorInfo().setReadOnly(true);
		runningSims.getSimulatorInfo().setEnabled(true);
	}

	private void setSimulationInfoData(final Property property) {
		final SQLContainer simulationContainer = runningSims.getDBHelp()
				.getLatestRunningSimulationOnSimulatorWithId(
						property.getValue().toString());
		if (simulationContainer.size() == 0) {
			Notification
					.show("There are now simulations currently running on this simulator");
		} else {
			final RowId id = (RowId) simulationContainer.getIdByIndex(0);
			// System.out.println(id)
			/*
			 * runningSims.getSimulationInfo().setItemDataSource(
			 * simulationContainer.getItem(id));
			 */
			runningSims.getSimulationInfo().setItemDataSource(null);
			// runningSims.getSimulationInfo().setEnabled(true);
			// runningSims.getSimulationInfo().setReadOnly(false);

			ScheduledExecutorService ses = Executors
					.newSingleThreadScheduledExecutor();

			ses.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					// System.out.println("run");
					SQLContainer simulationContainerUpd = runningSims
							.getDBHelp()
							.getLatestRunningSimulationOnSimulatorWithId(
									property.getValue().toString());
					// System.out.println(simulationContainerUpd.getItem(id)

					updateUi(simulationContainerUpd.getItem(id));
					/*
					 * runningSims.getSimulationInfo().setItemDataSource(
					 * simulationContainerUpd.getItem(id));
					 * simulationContainer.refresh();
					 * simulationContainerUpd.refresh();
					 */
				}
			}, 0, 1, TimeUnit.SECONDS);
		}
	}

	private void updateUi(Item item) {
		System.out.println("check if sim is paused"
				+ item.getItemProperty("IsSimulationPaused").getValue()
						.toString());
		runningSims.setSimulationInfo(new SimulationStateFieldGroup(
				runningSims, ColumnNames.getSimulationCols(), runningSims
						.getSimulationInfoLayout()));
		runningSims.getSimulationInfo().setEnabled(
				runningSims.getSimulationInfo().isEnabled());
		Notification.show("notification");
		runningSims.getSimulationInfo().setItemDataSource(item);

	}

	private void setSimulationDevicesState(Item selectedItem) {

	}
}
