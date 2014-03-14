package com.example.testvaadin.views;

import cm.example.testvaadin.simulatorcommunication.SimulatorsStatus;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.ErrorLabel;
import com.example.testvaadin.components.FlightPathGoogleMap;
import com.example.testvaadin.components.InfoLabel;
import com.example.testvaadin.components.SelectSimulatorCombo;
import com.example.testvaadin.components.SimulationStateFieldGroup;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.javascriptcomponents.PrimaryFlightDisplay;
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
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;

public class RunningSimulationsView extends BasicView implements View {
	public class StatusRefreshListener implements RefreshListener {
		private static final long serialVersionUID = 392864906906738406L;

		public void refresh(final Refresher source) {
			getSelectSimulator().handleValueChangeEvent();
			// System.out.println("refreshing");
		}
	}

	private static final String NO_SIMULATOR_SELECTED = "Please, select simulator";
	private static final String EMPTY_STRING = "";
	private static final String NO_RUNNING_SIMULATIONS = "There are no simulations currently running on this simulator";

	private static final long serialVersionUID = -1785707193097941934L;
	private Navigator navigator;
	private FormLayout simulatorInfoLayout = new FormLayout();
	private FormLayout simulationLayout = new FormLayout();
	private FormLayout simulationInfoLayout = new FormLayout();
	private FormLayout simulationDevicesStateLayout = new FormLayout();
	private ErrorLabel errorLabel = new ErrorLabel("");
	private InfoLabel simulatorInfoLabel = new InfoLabel("Simulator info");
	private InfoLabel simulationLabel = new InfoLabel("Simulation");
	private InfoLabel simulationInfoLabel = new InfoLabel("Simulation info");
	private InfoLabel simulatorDevicesStateLabel = new InfoLabel(
			"Simulator devices state");
	private SimulationStateFieldGroup simulatorInfo;
	private SimulationStateFieldGroup simulation;
	private SimulationStateFieldGroup simulationInfo;
	private SimulationStateFieldGroup simulationDevicesState;
	private PrimaryFlightDisplay primaryFlightDisplay;
	private SelectSimulatorCombo selectSimulator;
	private String apiKey = "AIzaSyDObpG4jhLAo88_GE8FHJhg-COWVgi_gr4";
	private FlightPathGoogleMap googleMap = null;

	public FlightPathGoogleMap getGoogleMap() {
		return googleMap;
	}

	public SelectSimulatorCombo getSelectSimulator() {
		return selectSimulator;
	}

	private ButtonToMainMenu buttonToMainMenu;

	public SimulationStateFieldGroup getSimulatorInfo() {
		return simulatorInfo;
	}

	public SimulationStateFieldGroup getSimulation() {
		return simulation;
	}

	public SimulationStateFieldGroup getSimulationInfo() {
		return simulationInfo;
	}

	public void setSimulationInfo(SimulationStateFieldGroup simulationInfo) {
		this.simulation = simulationInfo;
	}

	public SimulationStateFieldGroup getSimulatorDevicesState() {
		return simulationDevicesState;
	}

	public PrimaryFlightDisplay getPrimaryFlightDisplay() {
		return primaryFlightDisplay;
	}

	public FormLayout getSimulatorInfoLayout() {
		return simulatorInfoLayout;
	}

	public FormLayout getSimulationInfoLayout() {
		return simulationLayout;
	}

	public Label getErrorLabel() {
		return errorLabel;
	}

	public RunningSimulationsView(Navigator navigator) {
		// dbHelp.getLatestSimulationContainer("1");
		this.navigator = navigator;
		initButtonToMainMenu();
		initSelectSimulatorCombo();
		initSimulatorsInfo();
		initSimulationInfo();
		initSimulationDevicesState();
		initSimulationInfoInfo();
		initLayout();
		setClickListeners();
		initPageRefresher();
		initPrimaryFlightDisplay();
		initGoogleMaps();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		primaryFlightDisplay.resetPfd();
		selectSimulator.initSelectSimulator();
		selectSimulator.handleValueChangeEvent();
	}

	private void initGoogleMaps() {
		if (googleMap != null) {
			googleMap.clearMap();
		} else {
			googleMap = new FlightPathGoogleMap(
					new LatLon(60.440963, 22.25122), 4.0, apiKey, this);
			addComponent(googleMap);
		}
	}

	private void initPrimaryFlightDisplay() {
		System.out
				.println("----------------------------------------------------------INITING PFD");
		primaryFlightDisplay = new PrimaryFlightDisplay("index.html", 0, 0, 0,
				0, 0, 0);
		addComponent(primaryFlightDisplay);
	}

	private void initButtonToMainMenu() {
		buttonToMainMenu = new ButtonToMainMenu(navigator);
	}

	private void initPageRefresher() {
		StatusRefreshListener listener = new StatusRefreshListener();
		final Refresher refresher = new Refresher();
		refresher.addListener(listener);
		refresher.setRefreshInterval(1000);
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

	private void initSimulationInfo() {
		simulation = new SimulationStateFieldGroup(
				ColumnNames.getSimulationBeanCols(), simulationLayout);
		simulation.setEnabled(false);
	}

	private void initSimulationInfoInfo() {
		simulationInfo = new SimulationStateFieldGroup(
				ColumnNames.getSimulationInfoBeanCols(), simulationInfoLayout);
		simulationInfo.setEnabled(false);
	}

	private void initSimulationDevicesState() {
		simulationDevicesState = new SimulationStateFieldGroup(
				ColumnNames.getSimulationDevicesStateBeanCols(),
				simulationDevicesStateLayout);
		simulationDevicesState.setEnabled(false);
	}

	private void initSelectSimulatorCombo() {
		selectSimulator = new SelectSimulatorCombo(this);
	}

	private void initSimulatorsInfo() {
		simulatorInfo = new SimulationStateFieldGroup(
				ColumnNames.getSimulatorMainCols(), simulatorInfoLayout);
		simulatorInfo.setEnabled(false);
	}

	private void initLayout() {
		addComponent(buttonToMainMenu);
		addComponent(selectSimulator);
		addComponent(errorLabel);
		addComponent(simulatorInfoLabel);
		addComponent(simulatorInfoLayout);
		addComponent(simulationLabel);
		addComponent(simulationLayout);
		addComponent(simulatorDevicesStateLabel);
		addComponent(simulationDevicesStateLayout);
		addComponent(simulationInfoLabel);
		addComponent(simulationInfoLayout);
	}

	public void setAllSimulationSimulatorData(Item selectedSimulator) {
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
		setSimulationDevicesStateInfo(selectedDevicesState);
		// Set PFD info
		Item selectedPFD = SimulatorsStatus
				.getSimulationPFDItemBySimulatorId(simulatorId);
		setPrimaryFlightDisplayInfo(selectedPFD);

	}

	private void setSimulatorInfoData(Item selectedSimulator) {
		getSimulatorInfo().setItemDataSource(selectedSimulator);
		getSimulatorInfo().setReadOnly(true);
		getSimulatorInfo().setEnabled(true);
	}

	private void setSimulationData(final Item selectedSimulation) {
		getErrorLabel().setValue(EMPTY_STRING);
		if (selectedSimulation != null) {
			// getSimulation().setItemDataSource(
			// SimulatorsStatus.getSimulationItem());
			getSimulation().setItemDataSource(selectedSimulation);
			getSimulation().setEnabled(true);
			getSimulation().setReadOnly(true);
		} else {
			getSimulation().setEnabled(false);
		}
	}

	private void setSimulationInfoData(Item selectedSimulationInfo,
			String simulatorId) {
		if (selectedSimulationInfo != null) {
			// Set simulation info data
			getSimulationInfo().setItemDataSource(selectedSimulationInfo);
			getSimulationInfo().setEnabled(true);
			getSimulationInfo().setReadOnly(true);
			// Add simulation info data to map
			googleMap.addLatestCoordinatesForSimulation(selectedSimulationInfo,
					simulatorId);
		} else {
			getSimulationInfo().setEnabled(false);
		}
	}

	private void setSimulationDevicesStateInfo(Item selectedDevicesState) {
		if (selectedDevicesState != null) {
			getSimulatorDevicesState().setItemDataSource(selectedDevicesState);
			getSimulatorDevicesState().setEnabled(true);
			getSimulatorDevicesState().setReadOnly(true);
		} else {
			getSimulatorDevicesState().setEnabled(false);
		}
	}

	private void setPrimaryFlightDisplayInfo(Item selectedPfdInfo) {
		if (selectedPfdInfo != null) {
			primaryFlightDisplay.updateIndividualPFDValues(selectedPfdInfo);
		} else {
		}
	}

	public Property<?> getSimulatorIdByRowId(RowId rowId) {
		return getDBHelp().getSimulatorContainer().getContainerProperty(rowId,
				ColumnNames.getSimulatorIdPropName());
	}

	public void setSimulatorNotSelectedState() {
		getErrorLabel().setValue(NO_SIMULATOR_SELECTED);
		getSimulatorInfo().setEnabled(false);
		getSimulation().setEnabled(false);
		getSimulatorDevicesState().setEnabled(false);
		getSimulationInfo().setEnabled(false);
		initGoogleMaps();
	}

	public void setNoSimulationsRunningState(Item selectedSimulator) {
		setSimulatorInfoData(selectedSimulator);
		getErrorLabel().setValue(NO_RUNNING_SIMULATIONS);
		getSimulation().setEnabled(false);
		getSimulatorDevicesState().setEnabled(false);
		getSimulationInfo().setEnabled(false);
		initGoogleMaps();
	}

}
