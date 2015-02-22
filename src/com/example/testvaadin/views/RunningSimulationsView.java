package com.example.testvaadin.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.testvaadin.components.FlightPathRunningSim;
import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.data.SimulatorCols;
import com.example.testvaadin.exception.UnknownSimulatorException;
import com.example.testvaadin.items.SimulationInfoItem;
import com.example.testvaadin.items.SimulationPFDItem;
import com.example.testvaadin.simulatorcommunication.SimulatorsStatus;
import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.data.Item;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.tapio.googlemaps.client.LatLon;

public class RunningSimulationsView extends SimulationsView implements View {

	final static Logger logger = LoggerFactory
			.getLogger(RunningSimulationsView.class);

	protected static final long serialVersionUID = -1785707193097941934L;

	protected String selectedSimulatorId = null;

	protected FlightPathRunningSim googleMap = null;

	public RunningSimulationsView(Navigator navigator) {
		super(navigator);
		logger.info("new RunningSimulationsView() - Initializing running simulations view");
		initGoogleMaps();
		avionycsLayout.addComponent(googleMap);

	}

	public FlightPathRunningSim getGoogleMap() {
		return googleMap;
	}

	public class StatusRefreshListener implements RefreshListener {
		private static final long serialVersionUID = 392864906906738406L;

		public void refresh(final Refresher source) {
			RunningSimulationsView.this.updateUI();
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() == null || event.getParameters().isEmpty()) {
			setSimulatorNotSelectedState();
		} else {
			selectedSimulatorId = event.getParameters();
			this.handleValueChangeEvent();
			initPageRefresher();
		}
	}

	private void initPageRefresher() {
		logger.info("initPageRefresher() - initializing refresher which updates the UI");
		// Set refresher which updates the UI
		StatusRefreshListener listener = new StatusRefreshListener();
		final Refresher refresher = new Refresher();
		refresher.addListener(listener);
		// Set update interval in miliseconds
		logger.info("initPageRefresher() - set refresh interval: {}",
				ApplicationConfiguration.getRefreshUiFrequency());
		refresher.setRefreshInterval(ApplicationConfiguration
				.getRefreshUiFrequency());
		addExtension(refresher);
	}

	private void setAllSimulationSimulatorData(Item selectedSimulator) {
		getErrorLabel().setValue("Chosen simulator id: " + selectedSimulatorId);
		mainSimulationLayout.setVisible(true);
		String simulatorId = selectedSimulator
				.getItemProperty(SimulatorCols.simulatorid.toString())
				.getValue().toString();
		// Set simulation data
		// Item selectedSimulation =
		// SimulatorsStatus.getSimulationItemBySimulatorId(simulatorId);
		// Set simulation info data
		SimulationInfoItem selectedSimulationInfo = SimulatorsStatus
				.getSimulationInfoItemBySimulatorId(simulatorId);
		setSimulationInfoData(selectedSimulationInfo, simulatorId);
		// Set simulation devices state
		Item selectedDevicesState = SimulatorsStatus
				.getSimulationDevStateItemBySimulatorId(simulatorId);
		setFlightControlsInfo(selectedDevicesState, selectedSimulator);
		// Set PFD info
		SimulationPFDItem selectedPFD = SimulatorsStatus
				.getSimulationPFDItemBySimulatorId(simulatorId);
		if (selectedPFD != null) {
			setPrimaryFlightDisplayInfo(selectedPFD);
			altitudeChart.addNewPoint(simulatorId, selectedPFD);
			speedChart.addNewPoint(simulatorId, selectedPFD);
		}
	}

	private void setSimulationInfoData(SimulationInfoItem simulationInfo,
			String simulatorId) {
		if (simulationInfo != null) {
			// Add simulation info data to map
			googleMap.addLatestCoordForSimulation(simulationInfo, simulatorId);
		}
	}

	private void setSimulatorNotSelectedState() {
		getErrorLabel().setValue(NO_SIMULATOR_SELECTED);
		initGoogleMaps();
		mainSimulationLayout.setVisible(false);
	}

	private void setNoSimulationsRunningState() {
		getErrorLabel().setValue(
				NO_RUNNING_SIMULATIONS + ". Chosen simulator id: "
						+ selectedSimulatorId);
		initGoogleMaps();
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
		} catch (UnknownSimulatorException e) {
			setSimulatorNotSelectedState();
			errorLabel.setValue(ERROR_SIMULATOR_NOT_EXISTS
					+ selectedSimulatorId);
		}
	}

	@Override
	protected void resetUI() {
		primaryFlightDisplay.resetPfd();

		// googleMap.clearMap();
	}

	@Override
	protected void initGoogleMaps() {
		logger.info("initGoogleMaps(). Google maps object: {}", googleMap);

		if (this.googleMap != null) {
			this.googleMap.clearMap();
		} else {
			logger.info("initGoogleMaps() - creating new FlightPathGoogleMapRunningSim");
			this.googleMap = new FlightPathRunningSim(new LatLon(60.440963,
					22.25122), 4.0,
					ApplicationConfiguration.getGoogleMapApiKey(), this);
		}
	}

}
