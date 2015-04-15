package cz.vutbr.fit.simulatormanager.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.data.Item;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.tapio.googlemaps.client.LatLon;

import cz.vutbr.fit.simulatormanager.beans.AllEngineInfo;
import cz.vutbr.fit.simulatormanager.components.FlightPathRunningSim;
import cz.vutbr.fit.simulatormanager.components.SimulatorConfigurationChecker;
import cz.vutbr.fit.simulatormanager.data.ApplicationConfiguration;
import cz.vutbr.fit.simulatormanager.database.columns.SimulatorCols;
import cz.vutbr.fit.simulatormanager.exception.UnknownSimulatorException;
import cz.vutbr.fit.simulatormanager.items.SimulationDevStateItem;
import cz.vutbr.fit.simulatormanager.items.SimulationInfoItem;
import cz.vutbr.fit.simulatormanager.items.SimulationPFDItem;
import cz.vutbr.fit.simulatormanager.simulatorcommunication.SimulatorsStatus;

public class RunningSimulationsView extends SimulationsView implements View {

    final static Logger LOG = LoggerFactory.getLogger(RunningSimulationsView.class);

    protected static final long serialVersionUID = 1L;
    protected String selectedSimulatorId = null;
    protected FlightPathRunningSim googleMap = null;

    // once in 300 UI updates we verify if simulator configuration is valid. If
    // it's not valid, display error message
    protected boolean isSimulatorConfigurationValid = true;
    private static final int VERIFY_CONFIGURATION_FREQUENCY = 300;
    private int uiUpdatesLeftTillNextConfigurationVerification = 0;

    public RunningSimulationsView(Navigator navigator) {
	super(navigator);
	LOG.info("new RunningSimulationsView() - Initializing running simulations view");
	initGoogleMaps();
	avionycsLayout.addComponent(googleMap);

    }

    public FlightPathRunningSim getGoogleMap() {
	return googleMap;
    }

    public class StatusRefreshListener implements RefreshListener {
	private static final long serialVersionUID = 1L;

	public void refresh(final Refresher source) {
	    RunningSimulationsView.this.updateUI();
	}
    }

    @Override
    public void enter(ViewChangeEvent event) {
	// on entering the page we want to check the configuration
	uiUpdatesLeftTillNextConfigurationVerification = 0;
	if (event.getParameters() == null || event.getParameters().isEmpty()) {
	    setSimulatorNotSelectedState();
	} else {
	    selectedSimulatorId = event.getParameters();
	    this.handleValueChangeEvent();
	    initPageRefresher();
	}
    }

    private void initPageRefresher() {
	LOG.debug("initPageRefresher() - initializing refresher which updates the UI");
	// Set refresher which updates the UI
	StatusRefreshListener listener = new StatusRefreshListener();
	final Refresher refresher = new Refresher();
	refresher.addListener(listener);
	// Set update interval in miliseconds
	LOG.debug("initPageRefresher() - set refresh interval: {}", ApplicationConfiguration.getRefreshUiFrequency());
	refresher.setRefreshInterval(ApplicationConfiguration.getRefreshUiFrequency());
	addExtension(refresher);
    }

    private void setAllSimulationSimulatorData(Item selectedSimulator) {
	getErrorLabel().setValue("Chosen simulator id: " + selectedSimulatorId);
	mainSimulationLayout.setVisible(true);
	String simulatorId = selectedSimulator.getItemProperty(SimulatorCols.simulatorid.toString()).getValue()
		.toString();
	// Set simulation info
	SimulationInfoItem selectedSimulationInfo = SimulatorsStatus.getSimulationInfoItemBySimulatorId(simulatorId);
	setSimulationInfoData(selectedSimulationInfo, simulatorId);
	// Set simulation devices state
	SimulationDevStateItem selectedDevicesState = SimulatorsStatus
		.getSimulationDevStateItemBySimulatorId(simulatorId);
	setFlightControlsInfo(selectedDevicesState, selectedSimulator);
	// Set PFD info
	SimulationPFDItem selectedPFD = SimulatorsStatus.getSimulationPFDItemBySimulatorId(simulatorId);
	setPrimaryFlightDisplayInfo(selectedPFD);
	addNewPointToCharts(simulatorId, selectedPFD);
	// Set engines and fuel info
	AllEngineInfo enginesInfo = SimulatorsStatus.getSimulationEngineItemBySimulatorId(simulatorId);
	setEnginesInfo(simulatorId, enginesInfo, selectedDevicesState);
    }

    private void addNewPointToCharts(String simulatorId, SimulationPFDItem selectedPFD) {
	if (selectedPFD != null) {
	    // Add new point to charts
	    altitudeChart.addNewPoint(simulatorId, selectedPFD);
	    speedChart.addNewPoint(simulatorId, selectedPFD);
	}
    }

    private void setSimulationInfoData(SimulationInfoItem simulationInfo, String simulatorId) {
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
	getErrorLabel().setValue(NO_RUNNING_SIMULATIONS + ". Chosen simulator id: " + selectedSimulatorId);
	initGoogleMaps();
	mainSimulationLayout.setVisible(false);
    }

    protected void updateUI() {
	Item selectedSimulator = null;
	try {
	    selectedSimulator = dbHelp.getSimulatorItemBySimulatorId(selectedSimulatorId);
	    // If simulator is selected
	    if (selectedSimulator != null) {
		// If there are no running simulations on simulator
		if (!dbHelp.isLastSimInDbRunning(selectedSimulatorId)) {
		    this.setNoSimulationsRunningState();
		} else {
		    validateConfiguration(selectedSimulator, selectedSimulatorId);
		    if (true) {
			// TODO: Add this back!
			// if (isSimulatorConfigurationValid) {
			this.setAllSimulationSimulatorData(selectedSimulator);
		    }
		}
	    } else {
		this.setSimulatorNotSelectedState();
	    }
	} catch (UnknownSimulatorException e) {
	    setSimulatorNotSelectedState();
	    errorLabel.setValue(ERROR_SIMULATOR_NOT_EXISTS + selectedSimulatorId);
	}
    }

    /**
     * Verifies if the configuration of currently selected simulator valid. If
     * it's valid, does nothing. If invalid, show error message
     * 
     * @param selectedSimulator
     * @param simulatorId
     */
    private void validateConfiguration(Item selectedSimulator, String simulatorId) {
	if (uiUpdatesLeftTillNextConfigurationVerification == 0) {
	    uiUpdatesLeftTillNextConfigurationVerification = VERIFY_CONFIGURATION_FREQUENCY;
	    String hostname = (String) selectedSimulator.getItemProperty(SimulatorCols.hostname.toString()).getValue();
	    int port = (int) selectedSimulator.getItemProperty(SimulatorCols.port.toString()).getValue();
	    isSimulatorConfigurationValid = (new SimulatorConfigurationChecker(hostname, port, simulatorId))
		    .verifyConfiguration(SimulatorConfigurationChecker.DO_NOT_SHOW_SUCCESS_MESSAGE);
	}
	uiUpdatesLeftTillNextConfigurationVerification--;
    }

    @Override
    protected void resetUI() {
	primaryFlightDisplay.resetPfd();
	// googleMap.clearMap();
    }

    @Override
    protected void initGoogleMaps() {
	LOG.debug("initGoogleMaps(). Google maps object: {}", googleMap);

	if (this.googleMap != null) {
	    this.googleMap.clearMap();
	} else {
	    LOG.info("initGoogleMaps() - creating new FlightPathGoogleMapRunningSim");
	    this.googleMap = new FlightPathRunningSim(new LatLon(60.440963, 22.25122), 4.0,
		    ApplicationConfiguration.getGoogleMapApiKey(), this);
	}
    }

}
