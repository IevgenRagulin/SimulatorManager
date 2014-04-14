package com.example.testvaadin.views;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.ErrorLabel;
import com.example.testvaadin.components.FlightPathGoogleMap;
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
import com.vaadin.ui.Alignment;
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
	private static final String ERROR_SIMULATOR_NOT_EXISTS = "Simulator with such id doesn't exists in database. Simulator id: ";

	private static final long serialVersionUID = -1785707193097941934L;
	private static final String MAIN_LAYOUT_CLASS = "mainVertLayout";
	private Navigator navigator;
	private ErrorLabel errorLabel = new ErrorLabel("");

	private FlightPathGoogleMap googleMap = null;

	/* Custom javascript components */
	private PrimaryFlightDisplay primaryFlightDisplay;
	private FlightControls flightControls;

	private ButtonToMainMenu buttonToMainMenu;
	private HorizontalLayout avionycsLayout = new HorizontalLayout();
	private HorizontalLayout graphsLayout = new HorizontalLayout();
	private HorizontalLayout topSimulationLayout = new HorizontalLayout();
	private VerticalLayout mainSimulationLayout = new VerticalLayout();
	private JsHighChartAltitude altitudeChart;
	private JsHighChartSpeed speedChart;

	private String selectedSimulatorId = null;

	public class StatusRefreshListener implements RefreshListener {
		private static final long serialVersionUID = 392864906906738406L;

		public void refresh(final Refresher source) {
			RunningSimulationsView.this.handleUpdatingValues();
			;
		}
	}

	// TODO: Extract to superclass
	public FlightPathGoogleMap getGoogleMap() {
		return googleMap;
	}

	// TODO: Extract to superclass
	public PrimaryFlightDisplay getPrimaryFlightDisplay() {
		return primaryFlightDisplay;
	}

	// TODO: Extract to superclass
	public Label getErrorLabel() {
		return errorLabel;
	}

	// TODO: Extract to superclass
	public RunningSimulationsView(Navigator navigator) {
		this.navigator = navigator;
		initButtonToMainMenu();
		setClickListeners();
		initPageRefresher();
		initPrimaryFlightDisplay();
		initControlYoke();
		initGoogleMaps();
		initGraphs();
		initLayout();

	}

	// TODO: Extract to superclass
	private void initGraphs() {
		altitudeChart = new JsHighChartAltitude(this, ALTITUDE_CHART_ID);
		altitudeChart.setId(ALTITUDE_CHART_ID);
		speedChart = new JsHighChartSpeed(this, "Speed", "Knots",
				SPEED_CHART_ID);
		speedChart.setId(SPEED_CHART_ID);

	}

	// TODO: Extract to superclass
	private void initControlYoke() {
		// -2 means the device doesn't send data
		flightControls = new FlightControls(0, 0, 0, -2, -2, -2, 0, 0);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() == null || event.getParameters().isEmpty()) {
			setSimulatorNotSelectedState();
		} else {
			selectedSimulatorId = event.getParameters();
			primaryFlightDisplay.resetPfd();
			altitudeChart.resetChart();
			speedChart.resetChart();
			this.handleValueChangeEvent();
		}
	}

	/*
	 * Based on selected simulator, updates the UI.
	 */
	public void handleValueChangeEvent() {
		this.resetUI();
		handleUpdatingValues();
	}

	/* Based on previous simulator selection, updates the UI */
	public void handleUpdatingValues() {
		// String value = (String) SelectSimulatorCombo.this.getValue();
		// RowId rowId = simulatorsIdNamesMapping.get(value);
		this.updateUI();
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
		}
	}

	private void initPrimaryFlightDisplay() {
		primaryFlightDisplay = new PrimaryFlightDisplay(1, 0, 0, 0, 0, 0, 0, 0);
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

	private void initLayout() {
		addComponent(topSimulationLayout);
		addComponent(mainSimulationLayout);
		avionycsLayout.addComponent(primaryFlightDisplay);
		avionycsLayout.addComponent(flightControls);
		avionycsLayout.addComponent(googleMap);
		topSimulationLayout.addComponent(buttonToMainMenu);
		topSimulationLayout.addComponent(errorLabel);
		topSimulationLayout.setComponentAlignment(errorLabel,
				Alignment.TOP_LEFT);
		mainSimulationLayout.addComponent(avionycsLayout);
		mainSimulationLayout.addComponent(graphsLayout);
		topSimulationLayout.setPrimaryStyleName(MAIN_LAYOUT_CLASS);
		mainSimulationLayout.setPrimaryStyleName(MAIN_LAYOUT_CLASS);
		graphsLayout.addComponent(altitudeChart);
		graphsLayout.addComponent(speedChart);

	}

	public void setAllSimulationSimulatorData(Item selectedSimulator) {
		getErrorLabel().setValue("Chosen simulator id: " + selectedSimulatorId);
		mainSimulationLayout.setVisible(true);
		String simulatorId = selectedSimulator
				.getItemProperty(ColumnNames.getSimulatorIdPropName())
				.getValue().toString();
		System.out.println("HEYHO BEFORE setting selected simulation info");
		// Set simulation data
		// Item selectedSimulation = SimulatorsStatus
		// .getSimulationItemBySimulatorId(simulatorId);
		// Set simulation info data
		Item selectedSimulationInfo = SimulatorsStatus
				.getSimulationInfoItemBySimulatorId(simulatorId);
		setSimulationInfoData(selectedSimulationInfo, simulatorId);
		// Set simulation devices state
		System.out.println("HEYHO BEFORE setting selected dev state");
		Item selectedDevicesState = SimulatorsStatus
				.getSimulationDevStateItemBySimulatorId(simulatorId);
		setFlightControlsInfo(selectedDevicesState, selectedSimulator);
		// Set PFD info
		System.out.println("HEYHO BEFORE setting pfd info");
		SimulationPFDItem selectedPFD = SimulatorsStatus
				.getSimulationPFDItemBySimulatorId(simulatorId);
		setPrimaryFlightDisplayInfo(selectedPFD);
		System.out.println("HEYHO BEFORE adding point to charts");
		altitudeChart.addNewPoint(simulatorId, selectedPFD);
		speedChart.addNewPoint(simulatorId, selectedPFD);
		System.out.println("HEYHO AFTER adding point to charts");
	}

	private void setFlightControlsInfo(Item selectedDevicesState,
			Item selectedSimulator) {
		if (selectedDevicesState != null) {
			flightControls.updateIndividualFlightControlValues(
					selectedDevicesState, selectedSimulator);
		}
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

	public void setNoSimulationsRunningState() {
		getErrorLabel().setValue(
				NO_RUNNING_SIMULATIONS + ". Chosen simulator id: "
						+ selectedSimulatorId);
		mainSimulationLayout.setVisible(false);
	}

	public void resetUI() {
		googleMap.clearMap();
	}

	public void updateUI() {
		Item selectedSimulator = null;
		try {
			System.out.println("HEYHO BEFORE GETTING SIMULATOR ITEM");
			selectedSimulator = dbHelp
					.getSimulatorItemBySimulatorId(selectedSimulatorId);
			System.out.println("HEYHO AFTER GETTING SIMULATOR ITEM");
			// If simulator is selected
			if (selectedSimulator != null) {
				System.out.println("HEYHO NO RUNNING SIMS");
				// If there are no running simulations on simulator
				if (!dbHelp.isLastSimInDbRunning(selectedSimulatorId)) {
					this.setNoSimulationsRunningState();
				} else {
					System.out.println("HEYHO THERE ARE RUNNING SIMS");
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
