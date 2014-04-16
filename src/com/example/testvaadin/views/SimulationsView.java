package com.example.testvaadin.views;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.ErrorLabel;
import com.example.testvaadin.components.FlightPathGoogleMap;
import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.jscomponents.flightcontrols.FlightControls;
import com.example.testvaadin.jscomponents.jshighchart.JsHighChartAltitude;
import com.example.testvaadin.jscomponents.jshighchart.JsHighChartSpeed;
import com.example.testvaadin.jscomponents.pfd.PrimaryFlightDisplay;
import com.vaadin.data.Item;
import com.vaadin.navigator.Navigator;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public abstract class SimulationsView extends BasicView {
	private static final long serialVersionUID = 6450588437869904764L;
	protected static final String ALTITUDE_CHART_ID = "altitudeChartId";
	protected String SPEED_CHART_ID = "speedChartId";
	protected static final String NO_SIMULATOR_SELECTED = "Please, select simulator";
	protected static final String EMPTY_STRING = "";
	protected static final String NO_RUNNING_SIMULATIONS = "There are no simulations currently running on this simulator";
	protected static final String ERROR_SIMULATOR_NOT_EXISTS = "Simulator with such id doesn't exists in database. Simulator id: ";

	protected static final String MAIN_LAYOUT_CLASS = "mainVertLayout";
	protected Navigator navigator;
	protected ErrorLabel errorLabel = new ErrorLabel("");

	protected FlightPathGoogleMap googleMap = null;

	/* Custom javascript components */
	protected PrimaryFlightDisplay primaryFlightDisplay;
	protected FlightControls flightControls;

	protected ButtonToMainMenu buttonToMainMenu;
	protected HorizontalLayout avionycsLayout = new HorizontalLayout();
	protected HorizontalLayout graphsLayout = new HorizontalLayout();
	protected HorizontalLayout topSimulationLayout = new HorizontalLayout();
	protected VerticalLayout mainSimulationLayout = new VerticalLayout();
	protected JsHighChartAltitude altitudeChart;
	protected JsHighChartSpeed speedChart;

	public FlightPathGoogleMap getGoogleMap() {
		return googleMap;
	}

	public PrimaryFlightDisplay getPrimaryFlightDisplay() {
		return primaryFlightDisplay;
	}

	public Label getErrorLabel() {
		return errorLabel;
	}

	public SimulationsView(Navigator navigator) {
		this.navigator = navigator;
		initButtonToMainMenu();
		setClickListeners();
		initPrimaryFlightDisplay();
		initControlYoke();
		initGoogleMaps();
		initGraphs();
		initLayout();
	}

	protected void initLayout() {
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

	protected void setClickListeners() {
		buttonToMainMenu.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4243499910765394003L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("");
			}
		});
	}

	protected void initGraphs() {
		altitudeChart = new JsHighChartAltitude(this, ALTITUDE_CHART_ID);
		altitudeChart.setId(ALTITUDE_CHART_ID);
		speedChart = new JsHighChartSpeed(this, "Speed", "Knots",
				SPEED_CHART_ID);
		speedChart.setId(SPEED_CHART_ID);

	}

	private void initControlYoke() {
		// -2 means the device doesn't send data
		flightControls = new FlightControls(0, 0, 0, -2, -2, -2, 0, 0);
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

	protected void setPrimaryFlightDisplayInfo(Item selectedPfdInfo) {
		if (selectedPfdInfo != null) {
			primaryFlightDisplay.updateIndividualPFDValues(selectedPfdInfo);
		}
	}

	protected void setFlightControlsInfo(Item selectedDevicesState,
			Item selectedSimulator) {
		if (selectedDevicesState != null) {
			flightControls.updateIndividualFlightControlValues(
					selectedDevicesState, selectedSimulator);
		}
	}

	protected void setSimulationInfoData(Item selectedSimulationInfo,
			String simulatorId) {
		if (selectedSimulationInfo != null) {
			// Add simulation info data to map
			googleMap.addLatestCoordinatesForSimulation(selectedSimulationInfo,
					simulatorId);
		}
	}

	protected void resetUI() {
		primaryFlightDisplay.resetPfd();
		altitudeChart.resetChart();
		speedChart.resetChart();
		googleMap.clearMap();
	}

	/*
	 * Based on selected simulator, updates the UI.
	 */
	protected void handleValueChangeEvent() {
		this.resetUI();
		updateValues();
	}

	/* Based on previous simulator selection, updates the UI */
	protected void updateValues() {
		this.updateUI();
	}

	protected abstract void updateUI();

}
