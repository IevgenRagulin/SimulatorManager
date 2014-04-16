package com.example.testvaadin.views;

import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.items.SimulationPFDItem;
import com.example.testvaadin.simulatorcommunication.SimulatorsStatus;
import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.data.Item;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public class RunningSimulationsView extends SimulationsView implements View {

	protected String selectedSimulatorId = null;

	public RunningSimulationsView(Navigator navigator) {
		super(navigator);
		initPageRefresher();
	}

	protected static final long serialVersionUID = -1785707193097941934L;

	public class StatusRefreshListener implements RefreshListener {
		private static final long serialVersionUID = 392864906906738406L;

		public void refresh(final Refresher source) {
			RunningSimulationsView.this.updateValues();
			;
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() == null || event.getParameters().isEmpty()) {
			setSimulatorNotSelectedState();
		} else {
			selectedSimulatorId = event.getParameters();
			this.handleValueChangeEvent();
		}
	}

	private void initPageRefresher() {
		// Set refresher which updates the UI
		StatusRefreshListener listener = new StatusRefreshListener();
		final Refresher refresher = new Refresher();
		refresher.addListener(listener);
		// Set update interval in miliseconds
		refresher.setRefreshInterval(ApplicationConfiguration
				.getRefreshUiFrequency());
		addExtension(refresher);
	}

	private void setAllSimulationSimulatorData(Item selectedSimulator) {
		getErrorLabel().setValue("Chosen simulator id: " + selectedSimulatorId);
		mainSimulationLayout.setVisible(true);
		String simulatorId = selectedSimulator
				.getItemProperty(ColumnNames.getSimulatorIdPropName())
				.getValue().toString();
		// Set simulation data
		// Item selectedSimulation = SimulatorsStatus
		// .getSimulationItemBySimulatorId(simulatorId);
		// Set simulation info data
		Item selectedSimulationInfo = SimulatorsStatus
				.getSimulationInfoItemBySimulatorId(simulatorId);
		setSimulationInfoData(selectedSimulationInfo, simulatorId);
		// Set simulation devices state
		Item selectedDevicesState = SimulatorsStatus
				.getSimulationDevStateItemBySimulatorId(simulatorId);
		setFlightControlsInfo(selectedDevicesState, selectedSimulator);
		// Set PFD info
		SimulationPFDItem selectedPFD = SimulatorsStatus
				.getSimulationPFDItemBySimulatorId(simulatorId);
		setPrimaryFlightDisplayInfo(selectedPFD);
		altitudeChart.addNewPoint(simulatorId, selectedPFD);
		speedChart.addNewPoint(simulatorId, selectedPFD);
	}

	private void setSimulatorNotSelectedState() {
		getErrorLabel().setValue(NO_SIMULATOR_SELECTED);
		mainSimulationLayout.setVisible(false);
	}

	private void setNoSimulationsRunningState() {
		getErrorLabel().setValue(
				NO_RUNNING_SIMULATIONS + ". Chosen simulator id: "
						+ selectedSimulatorId);
		mainSimulationLayout.setVisible(false);
	}

	protected void updateUI() {
		Item selectedSimulator = null;
		try {
			selectedSimulator = dbHelp
					.getSimulatorItemBySimulatorId(selectedSimulatorId);
			// If simulator is selected
			if (selectedSimulator != null) {
				// If there are no running simulations on simulator
				if (!dbHelp.isLastSimInDbRunning(selectedSimulatorId)) {
					this.setNoSimulationsRunningState();
				} else {
					this.setAllSimulationSimulatorData(selectedSimulator);
				}
			} else {
				this.setSimulatorNotSelectedState();
			}
		} catch (IllegalArgumentException e) {
			this.setSimulatorNotSelectedState();
			errorLabel.setValue(ERROR_SIMULATOR_NOT_EXISTS
					+ selectedSimulatorId);
		}
	}

}
