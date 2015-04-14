package cz.vutbr.fit.simulatormanager.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import cz.vutbr.fit.simulatormanager.beans.AllEngineInfo;
import cz.vutbr.fit.simulatormanager.components.ErrorLabel;
import cz.vutbr.fit.simulatormanager.components.MainMenuBar;
import cz.vutbr.fit.simulatormanager.jscomponents.enginespanel.EnginesPanel;
import cz.vutbr.fit.simulatormanager.jscomponents.flightcontrols.FlightControls;
import cz.vutbr.fit.simulatormanager.jscomponents.jshighchart.JsHighChartAltitude;
import cz.vutbr.fit.simulatormanager.jscomponents.jshighchart.JsHighChartSpeed;
import cz.vutbr.fit.simulatormanager.jscomponents.pfd.PrimaryFlightDisplay;
import cz.vutbr.fit.simulatormanager.types.PageType;

/**
 * Abstract class which is extended by RunningSimulationsView and
 * PastSimulationsView
 * 
 * @author ievgen
 *
 */
public abstract class SimulationsView extends BasicView {

    final static Logger LOG = LoggerFactory.getLogger(SimulationsView.class);

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

    protected HorizontalLayout avionycsLayout = new HorizontalLayout();
    protected HorizontalLayout graphsLayout = new HorizontalLayout();
    protected HorizontalLayout enginesLayout = new HorizontalLayout();
    protected HorizontalLayout topSimulationLayout = new HorizontalLayout();
    protected VerticalLayout mainSimulationLayout = new VerticalLayout();
    protected JsHighChartAltitude altitudeChart;
    protected JsHighChartSpeed speedChart;
    protected MainMenuBar mainMenu;
    protected EnginesPanel enginesPanel = new EnginesPanel();

    public PrimaryFlightDisplay getPrimaryFlightDisplay() {
	return primaryFlightDisplay;
    }

    public Label getErrorLabel() {
	return errorLabel;
    }

    public SimulationsView(Navigator navigator) {
	this.navigator = navigator;
	initPrimaryFlightDisplay();
	initControlYoke();
	initGraphs();
	initLayout();
    }

    protected void initLayout() {
	mainMenu = MainMenuBar.getInstance(navigator, PageType.VIEW_SIMULATION);
	addComponent(mainMenu);
	addComponent(topSimulationLayout);
	addComponent(mainSimulationLayout);
	avionycsLayout.addComponent(primaryFlightDisplay);
	avionycsLayout.addComponent(flightControls);
	topSimulationLayout.addComponent(errorLabel);
	topSimulationLayout.setComponentAlignment(errorLabel, Alignment.TOP_LEFT);
	mainSimulationLayout.addComponent(avionycsLayout);
	mainSimulationLayout.addComponent(graphsLayout);
	mainSimulationLayout.addComponent(enginesLayout);
	topSimulationLayout.setPrimaryStyleName(MAIN_LAYOUT_CLASS);
	mainSimulationLayout.setPrimaryStyleName(MAIN_LAYOUT_CLASS);
	graphsLayout.addComponent(altitudeChart);
	graphsLayout.addComponent(speedChart);
	enginesLayout.addComponent(enginesPanel);
    }

    protected void initGraphs() {
	altitudeChart = new JsHighChartAltitude(this, ALTITUDE_CHART_ID);
	altitudeChart.setId(ALTITUDE_CHART_ID);
	speedChart = new JsHighChartSpeed(this, "Speed", "Knots", SPEED_CHART_ID);
	speedChart.setId(SPEED_CHART_ID);

    }

    private void initControlYoke() {
	// -2 means the device doesn't send data
	flightControls = new FlightControls(0, 0, 0, -2, -2, -2, 0, 0, true, false, 3, 0, 0, 0);
    }

    protected abstract void initGoogleMaps();

    private void initPrimaryFlightDisplay() {
	primaryFlightDisplay = new PrimaryFlightDisplay(1, 0, 0, 0, 0, 0, 0, 0);
    }

    protected void setPrimaryFlightDisplayInfo(Item selectedPfdInfo) {
	if (selectedPfdInfo != null) {
	    primaryFlightDisplay.updateIndividualPFDValues(selectedPfdInfo);
	}
    }

    protected void setFlightControlsInfo(Item selectedDevicesState, Item selectedSimulator) {
	if (selectedDevicesState != null) {
	    flightControls.updateIndividualFlightControlValues(selectedDevicesState, selectedSimulator);
	}
    }

    protected void setEnginesInfo(String simulatorId, AllEngineInfo enginesInfo) {
	if (enginesInfo != null) {
	    enginesPanel.updateIndividualEngineValues(simulatorId, enginesInfo);
	}
    }

    protected abstract void resetUI();

    /**
     * Based on selected simulator, updates the UI.
     */
    protected void handleValueChangeEvent() {
	this.resetUI();
	updateUI();
    }

    /* Based on previous simulator selection, updates the UI */
    protected abstract void updateUI();

}
