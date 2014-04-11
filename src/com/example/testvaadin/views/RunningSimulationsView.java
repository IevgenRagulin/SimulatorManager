package com.example.testvaadin.views;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.ErrorLabel;
import com.example.testvaadin.components.FlightPathGoogleMap;
import com.example.testvaadin.components.SelectSimulatorCombo;
import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.items.SimulationPFDItem;
import com.example.testvaadin.jscomponents.flightcontrols.FlightControls;
import com.example.testvaadin.jscomponents.jshighchart.JsHighChartAltitude;
import com.example.testvaadin.jscomponents.jshighchart.JsHighChartSpeed;
import com.example.testvaadin.jscomponents.pfd.PrimaryFlightDisplay;
import com.example.testvaadin.simulatorcommunication.SimulatorsStatus;
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

	private static final String ALTITUDE_CHART_ID = "altitudeChartId";
	private String SPEED_CHART_ID = "speedChartId";
	private static final String NO_SIMULATOR_SELECTED = "Please, select simulator";
	private static final String EMPTY_STRING = "";
	private static final String NO_RUNNING_SIMULATIONS = "There are no simulations currently running on this simulator";

	private static final long serialVersionUID = -1785707193097941934L;
	private static final String MAIN_LAYOUT_CLASS = "mainVertLayout";
	private Navigator navigator;
	private ErrorLabel errorLabel = new ErrorLabel("");

	private SelectSimulatorCombo selectSimulator;
	private FlightPathGoogleMap googleMap = null;

	/* Custom javascript components */
	private PrimaryFlightDisplay primaryFlightDisplay;
	private FlightControls flightControls;

	private ButtonToMainMenu buttonToMainMenu;
	private HorizontalLayout avionycsLayout = new HorizontalLayout();
	private HorizontalLayout graphsLayout = new HorizontalLayout();
	private VerticalLayout topSimulationLayout = new VerticalLayout();
	private VerticalLayout mainSimulationLayout = new VerticalLayout();
	private JsHighChartAltitude altitudeChart;
	private JsHighChartSpeed speedChart;

	public class StatusRefreshListener implements RefreshListener {
		private static final long serialVersionUID = 392864906906738406L;

		public void refresh(final Refresher source) {
			getSelectSimulator().handleUpdatingValues();
			;
		}
	}

	public FlightPathGoogleMap getGoogleMap() {
		return googleMap;
	}

	public SelectSimulatorCombo getSelectSimulator() {
		return selectSimulator;
	}

	public PrimaryFlightDisplay getPrimaryFlightDisplay() {
		return primaryFlightDisplay;
	}

	public Label getErrorLabel() {
		return errorLabel;
	}

	public RunningSimulationsView(Navigator navigator) {
		this.navigator = navigator;
		initButtonToMainMenu();
		initSelectSimulatorCombo();
		initLayout();
		setClickListeners();
		initPageRefresher();
		initPrimaryFlightDisplay();
		initControlYoke();
		initGoogleMaps();
		initGraphs();

	}

	private void initGraphs() {
		altitudeChart = new JsHighChartAltitude(this, ALTITUDE_CHART_ID);
		altitudeChart.setId(ALTITUDE_CHART_ID);
		speedChart = new JsHighChartSpeed(this, "Speed", "Knots",
				SPEED_CHART_ID);
		speedChart.setId(SPEED_CHART_ID);
		graphsLayout.addComponent(altitudeChart);
		graphsLayout.addComponent(speedChart);
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
					new LatLon(60.440963, 22.25122), 4.0,
					ApplicationConfiguration.getGoogleMapApiKey(), this);
			avionycsLayout.addComponent(googleMap);
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
		refresher.setRefreshInterval(ApplicationConfiguration
				.getRefreshUiFrequency());
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

	private void initSelectSimulatorCombo() {
		selectSimulator = new SelectSimulatorCombo(this);
	}

	private void initLayout() {
		addComponent(topSimulationLayout);
		addComponent(mainSimulationLayout);
		topSimulationLayout.addComponent(buttonToMainMenu);
		topSimulationLayout.addComponent(selectSimulator);
		topSimulationLayout.addComponent(errorLabel);
		mainSimulationLayout.addComponent(avionycsLayout);
		mainSimulationLayout.addComponent(graphsLayout);
		topSimulationLayout.setPrimaryStyleName(MAIN_LAYOUT_CLASS);
		mainSimulationLayout.setPrimaryStyleName(MAIN_LAYOUT_CLASS);
	}

	public void setAllSimulationSimulatorData(Item selectedSimulator) {
		mainSimulationLayout.setVisible(true);
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
		SimulationPFDItem selectedPFD = SimulatorsStatus
				.getSimulationPFDItemBySimulatorId(simulatorId);
		setPrimaryFlightDisplayInfo(selectedPFD);
		altitudeChart.addNewPoint(simulatorId, selectedPFD);
		speedChart.addNewPoint(simulatorId, selectedPFD);
	}

	private void setFlightControlsInfo(Item selectedDevicesState,
			Item selectedSimulator) {
		if (selectedDevicesState != null) {
			flightControls.updateIndividualFlightControlValues(
					selectedDevicesState, selectedSimulator);
		}
	}

	private void setSimulationData(final Item selectedSimulation) {
		getErrorLabel().setValue(EMPTY_STRING);
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
	}

	public void setNoSimulationsRunningState(Item selectedSimulator) {
		getErrorLabel().setValue(NO_RUNNING_SIMULATIONS);
		mainSimulationLayout.setVisible(false);
	}

	public void resetUI() {
		googleMap.clearMap();
	}

}
