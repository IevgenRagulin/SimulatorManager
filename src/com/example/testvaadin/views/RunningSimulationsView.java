package com.example.testvaadin.views;

import cm.example.testvaadin.simulatorcommunication.SimulatorsStatus;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.ErrorLabel;
import com.example.testvaadin.components.FlightPathGoogleMap;
import com.example.testvaadin.components.SelectSimulatorCombo;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.javascriptcomponents.flightcontrols.FlightControls;
import com.example.testvaadin.javascriptcomponents.pfd.PrimaryFlightDisplay;
import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class RunningSimulationsView extends BasicView implements View {
	public static final int REFRESH_INTERVAL = 300;

	public class StatusRefreshListener implements RefreshListener {
		private static final long serialVersionUID = 392864906906738406L;

		public void refresh(final Refresher source) {
			getSelectSimulator().handleValueChangeEvent();
		}
	}

	private static final String NO_SIMULATOR_SELECTED = "Please, select simulator";
	private static final String EMPTY_STRING = "";
	private static final String NO_RUNNING_SIMULATIONS = "There are no simulations currently running on this simulator";

	private static final long serialVersionUID = -1785707193097941934L;
	private Navigator navigator;
	// private HorizontalLayout simulatorInfoLayout = new HorizontalLayout();
	// private HorizontalLayout simulationLayout = new HorizontalLayout();
	// private FormLayout simulationInfoLayout = new FormLayout();
	private ErrorLabel errorLabel = new ErrorLabel("");
	// private InfoLabel simulatorInfoLabel = new InfoLabel("Simulator info");
	// private InfoLabel simulationLabel = new InfoLabel("Simulation");
	// private SimulationStateFieldGroup simulatorInfo;
	// private SimulationStateFieldGroup simulation;

	private SelectSimulatorCombo selectSimulator;
	// TODO: make configurable from app configuration
	private String API_KEY = "AIzaSyDObpG4jhLAo88_GE8FHJhg-COWVgi_gr4";// eragulin
																		// //
																		// AIzaSyA3ofOOv8Q8vtkqLnUbmyWRMtAG2lKVOfg";
	private FlightPathGoogleMap googleMap = null;

	/* Custom javascript components */
	private PrimaryFlightDisplay primaryFlightDisplay;
	private FlightControls flightControls;

	public FlightPathGoogleMap getGoogleMap() {
		return googleMap;
	}

	public SelectSimulatorCombo getSelectSimulator() {
		return selectSimulator;
	}

	private ButtonToMainMenu buttonToMainMenu;
	private HorizontalLayout avionycsLayout = new HorizontalLayout();
	private VerticalLayout mainSimulationLayout = new VerticalLayout();

	// public SimulationStateFieldGroup getSimulatorInfo() {
	// return simulatorInfo;
	// }

	// public SimulationStateFieldGroup getSimulation() {
	// return simulation;
	// }

	// public void setSimulationInfo(SimulationStateFieldGroup simulationInfo) {
	// this.simulation = simulationInfo;
	// }

	public PrimaryFlightDisplay getPrimaryFlightDisplay() {
		return primaryFlightDisplay;
	}

	// public HorizontalLayout getSimulatorInfoLayout() {
	// return simulatorInfoLayout;
	// }

	// public HorizontalLayout getSimulationInfoLayout() {
	// return simulationLayout;
	// }

	public Label getErrorLabel() {
		return errorLabel;
	}

	public RunningSimulationsView(Navigator navigator) {
		this.navigator = navigator;
		initButtonToMainMenu();
		initSelectSimulatorCombo();
		// initSimulatorsInfo();
		// initSimulationInfo();
		initLayout();
		initGoogleMaps();
		setClickListeners();
		initPageRefresher();
		initPrimaryFlightDisplay();
		initControlYoke();

	}

	private void initControlYoke() {
		// -2 means the device doesn't send data
		flightControls = new FlightControls(0, 0, 0, -2, -2, -2, 0, 0);
		avionycsLayout.addComponent(flightControls);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		primaryFlightDisplay.resetPfd();
		selectSimulator.initSelectSimulator();
		selectSimulator.handleValueChangeEvent();
	}

	private void initGoogleMaps() {
		System.out.println("going to init google maps" + googleMap);
		if (googleMap != null) {
			googleMap.clearMap();
		} else {
			System.out.println("going to call google map constructor");
			googleMap = new FlightPathGoogleMap(
					new LatLon(60.440963, 22.25122), 4.0, API_KEY, this);
			mainSimulationLayout.addComponent(googleMap);
		}
	}

	private void initPrimaryFlightDisplay() {
		primaryFlightDisplay = new PrimaryFlightDisplay(1, 0, 0, 0, 0, 0, 0, 0);
		avionycsLayout.addComponent(primaryFlightDisplay);
	}

	private void initButtonToMainMenu() {
		buttonToMainMenu = new ButtonToMainMenu(navigator);
	}

	private void initPageRefresher() {
		// Set refresher which updates the UI
		StatusRefreshListener listener = new StatusRefreshListener();
		final Refresher refresher = new Refresher();
		refresher.addListener(listener);
		// Set update interval in miliseconds
		refresher.setRefreshInterval(REFRESH_INTERVAL);
		addExtension(refresher);
	}

	private void setClickListeners() {
		buttonToMainMenu.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4243499910765394003L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("");
			}
		});
	}

	// private void initSimulationInfo() {
	// simulation = new SimulationStateFieldGroup(
	// ColumnNames.getSimulationBeanCols(), simulationLayout);
	// }

	private void initSelectSimulatorCombo() {
		selectSimulator = new SelectSimulatorCombo(this);
	}

	// private void initSimulatorsInfo() {
	// simulatorInfo = new SimulationStateFieldGroup(
	// ColumnNames.getSimulatorMainCols(), simulatorInfoLayout);
	// }

	private void initLayout() {
		addComponent(buttonToMainMenu);
		addComponent(selectSimulator);
		addComponent(errorLabel);
		// addComponent(simulatorInfoLabel);
		// addComponent(simulatorInfoLayout);
		// mainSimulationLayout.addComponent(simulationLabel);
		// mainSimulationLayout.addComponent(simulationLayout);
		// mainSimulationLayout.addComponent(simulationInfoLayout);
		addComponent(mainSimulationLayout);
		mainSimulationLayout.addComponent(avionycsLayout);
	}

	public void setAllSimulationSimulatorData(Item selectedSimulator) {
		mainSimulationLayout.setVisible(true);
		// simulatorInfoLabel.setVisible(true);
		// simulatorInfoLayout.setVisible(true);
		// Set simulator info data
		setSimulatorInfoData(selectedSimulator);
		String simulatorId = selectedSimulator
				.getItemProperty(ColumnNames.getSimulatorIdPropName())
				.getValue().toString();
		// Set simulation data
		Item selectedSimulation = SimulatorsStatus
				.getSimulationItemBySimulatorId(simulatorId);
		setSimulationData(selectedSimulation);
		// Set simulation info data
		Item selectedSimulationInfo = SimulatorsStatus
				.getSimulationInfoItemBySimulatorId(simulatorId);
		setSimulationInfoData(selectedSimulationInfo, simulatorId);
		// Set simulation devices state
		Item selectedDevicesState = SimulatorsStatus
				.getSimulationDevStateItemBySimulatorId(simulatorId);
		setFlightControlsInfo(selectedDevicesState, selectedSimulator);
		// Set PFD info
		Item selectedPFD = SimulatorsStatus
				.getSimulationPFDItemBySimulatorId(simulatorId);
		setPrimaryFlightDisplayInfo(selectedPFD);

	}

	private void setFlightControlsInfo(Item selectedDevicesState,
			Item selectedSimulator) {
		if (selectedDevicesState != null) {
			flightControls.updateIndividualFlightControlValues(
					selectedDevicesState, selectedSimulator);
		}
	}

	private void setSimulatorInfoData(Item selectedSimulator) {
		// If some data was updated, update the data on UI. Commented this out
		// because no performance was noticed. It seems that Vaadin
		// optimizes it automatically
		// if (!getSimulatorInfo().equalsItem(selectedSimulator)) {
		// getSimulatorInfo().setItemDataSource(selectedSimulator);
		// }
		// getSimulatorInfo().setItemDataSource(selectedSimulator);
		// getSimulatorInfo().setReadOnly(true);
	}

	private void setSimulationData(final Item selectedSimulation) {
		getErrorLabel().setValue(EMPTY_STRING);
		/*
		 * if (selectedSimulation != null) {
		 * getSimulation().setItemDataSource(selectedSimulation);
		 * getSimulation().setEnabled(true); getSimulation().setReadOnly(true);
		 * } else { getSimulation().setEnabled(false); }
		 */
	}

	private void setSimulationInfoData(Item selectedSimulationInfo,
			String simulatorId) {
		if (selectedSimulationInfo != null) {
			// Add simulation info data to map
			googleMap.addLatestCoordinatesForSimulation(selectedSimulationInfo,
					simulatorId);
		}
	}

	private void setPrimaryFlightDisplayInfo(Item selectedPfdInfo) {
		if (selectedPfdInfo != null) {
			primaryFlightDisplay.updateIndividualPFDValues(selectedPfdInfo);
		}
	}

	public Property<?> getSimulatorIdByRowId(RowId rowId) {
		return getDBHelp().getSimulatorContainer().getContainerProperty(rowId,
				ColumnNames.getSimulatorIdPropName());
	}

	public void setSimulatorNotSelectedState() {
		getErrorLabel().setValue(NO_SIMULATOR_SELECTED);
		mainSimulationLayout.setVisible(false);
		// simulatorInfoLabel.setVisible(false);
		// simulatorInfoLayout.setVisible(false);
	}

	public void setNoSimulationsRunningState(Item selectedSimulator) {
		getErrorLabel().setValue(NO_RUNNING_SIMULATIONS);
		mainSimulationLayout.setVisible(false);
		// simulatorInfoLabel.setVisible(true);
		// simulatorInfoLayout.setVisible(true);
		// simulatorInfo.setItemDataSource(selectedSimulator);
		// simulatorInfo.setReadOnly(true);
	}

}
