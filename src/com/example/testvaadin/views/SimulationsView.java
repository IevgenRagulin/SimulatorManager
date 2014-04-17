package com.example.testvaadin.views;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.ErrorLabel;
import com.example.testvaadin.jscomponents.flightcontrols.FlightControls;
import com.example.testvaadin.jscomponents.jshighchart.JsHighChartAltitude;
import com.example.testvaadin.jscomponents.jshighchart.JsHighChartSpeed;
import com.example.testvaadin.jscomponents.pfd.PrimaryFlightDisplay;
import com.vaadin.data.Item;
import com.vaadin.navigator.Navigator;
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

	public PrimaryFlightDisplay getPrimaryFlightDisplay() {
		return primaryFlightDisplay;
	}

	public Label getErrorLabel() {
		return errorLabel;
	}

	public SimulationsView(Navigator navigator) {
		this.navigator = navigator;
		initButtonToMainMenu();
		initPrimaryFlightDisplay();
		initControlYoke();
		initGraphs();
		initLayout();
		setClickListeners();
	}

	protected void initLayout() {
		addComponent(topSimulationLayout);
		addComponent(mainSimulationLayout);
		avionycsLayout.addComponent(primaryFlightDisplay);
		avionycsLayout.addComponent(flightControls);
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

	protected abstract void initGoogleMaps();

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

	protected abstract void resetUI();

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
