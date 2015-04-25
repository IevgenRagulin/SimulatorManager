package cz.vutbr.fit.simulatormanager.views;

import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.tapio.googlemaps.client.LatLon;

import cz.vutbr.fit.simulatormanager.beans.AllEngineInfo;
import cz.vutbr.fit.simulatormanager.beans.SimulationDevStateBean;
import cz.vutbr.fit.simulatormanager.components.FlightPathPastSim;
import cz.vutbr.fit.simulatormanager.data.ApplicationConfiguration;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelperPureJDBC;
import cz.vutbr.fit.simulatormanager.database.SimulationQueries;
import cz.vutbr.fit.simulatormanager.database.columns.SimulationPfdInfoCols;
import cz.vutbr.fit.simulatormanager.jscomponents.jshighchart.JsHighChart.ValueChangeListener;

public class PastSimulationsView extends SimulationsView implements View {

    private static final long serialVersionUID = -3892686063360142032L;
    private final static Logger LOG = LoggerFactory.getLogger(PastSimulationsView.class);

    // info about this simulation is displayed
    private String selectedSimulationId;
    // the simulator id of the selected simulation
    private String selectedSimulatorId;
    protected static final String NO_SIMULATION_SELECTED = "Please, select simulation";

    protected FlightPathPastSim googleMap = null;

    public PastSimulationsView(Navigator navigator) {
	super(navigator);
	initGoogleMaps();
	avionycsLayout.addComponent(googleMap);
	setClickListeners();
    }

    protected void setClickListeners() {
	altitudeChart.addValueChangeListener(new ValueChangeListener() {
	    private static final long serialVersionUID = -192918332232524200L;

	    @Override
	    public void valueChange() {
		LOG.info("Clicked on altitude chart. pfdinfoid: {}. Timestamp: {}", altitudeChart.getClickedId(),
			altitudeChart.getTimestamp());
		try {
		    setUi(altitudeChart.getClickedId(), altitudeChart.getTimestamp());
		} catch (SQLException e) {
		    throw new RuntimeException("Sql exception when clicking on altitude chart ", e);
		}
	    }
	});
	speedChart.addValueChangeListener(new ValueChangeListener() {
	    private static final long serialVersionUID = -192918332232524200L;

	    @Override
	    public void valueChange() {
		LOG.info("Clicked on speed chart. pfdinfoid: {}. Timestamp: {}", altitudeChart.getClickedId(),
			altitudeChart.getTimestamp());
		try {
		    setUi(speedChart.getClickedId(), speedChart.getTimestamp());
		} catch (SQLException e) {
		    throw new RuntimeException("Sql exception when clicking on speed chart ", e);
		}
	    }
	});
    }

    @Override
    protected void updateUI() {
	LOG.info("updateUI()");
	// init google map with old data
	addFlightPathToMap(selectedSimulationId);
	altitudeChart.initChartWithDataForSimulationWithId(selectedSimulationId);
	speedChart.initChartWithDataForSimulationWithId(selectedSimulationId);
    }

    /**
     * Based on pfdclickedId and timestamp, set the devices state info, pfd
     * info, google map info, engines and fuel info
     */
    private void setUi(int pfdClickedId, long timestamp) throws SQLException {
	Item pfdItem = dbHelp.getPFDInfoByPfdInfoId(pfdClickedId);
	SimulationDevStateBean devState = dbHelp.getSimulationDevStateInfoByPfdInfoId(pfdClickedId, timestamp);
	Item itemSimulator = dbHelp.getSimulatorInfoByPfdInfoId(pfdClickedId);
	Item simulationInfoItem = dbHelp.getSimulationInfoItemByPfdInfoIdTimestemp(pfdClickedId, timestamp);
	Optional<AllEngineInfo> allEngineInfo = DatabaseHelperPureJDBC.getEnginesInfo(pfdClickedId, timestamp);
	setDevStateInfo(devState, itemSimulator);
	setPfdInfo(pfdItem);
	setGoogleMapInfo(simulationInfoItem, pfdItem);

	if (allEngineInfo.isPresent()) {
	    LOG.info("engines info is present: {}", allEngineInfo.toString());
	    setEnginesInfo(selectedSimulatorId, allEngineInfo.get(), devState);
	}
    }

    private void addFlightPathToMap(String simulationId) {
	SQLContainer simulationInfo = dbHelp.getAllSimulationInfoBySimulationId(simulationId);
	LOG.info("addFlightPathToMap - add old data to map for simulation id: {}, containerSize: {}", simulationId,
		simulationInfo.size());
	googleMap.addOldDataToMap(simulationInfo, 0.0);

    }

    protected void setGoogleMapInfo(Item simulationInfoItem, Item pfdInfoItem) {
	Double trueCourse = (Double) pfdInfoItem.getItemProperty(SimulationPfdInfoCols.truecourse.toString()).getValue();
	googleMap.moveMarkerOnMap(simulationInfoItem, trueCourse);

    }

    protected void setDevStateInfo(SimulationDevStateBean itemDevState, Item itemSimulator) {
	flightControls.updateIndividualFlightControlValues(itemDevState, itemSimulator);
    }

    private void setPfdInfo(Item item) {
	primaryFlightDisplay.updateIndividualPFDValues(item);
    }

    @Override
    public void enter(ViewChangeEvent event) {
	super.enter(event);
	if (event.getParameters() == null || event.getParameters().isEmpty()) {
	    setSimulationNotSelectedState();
	} else {
	    selectedSimulationId = event.getParameters();
	    selectedSimulatorId = SimulationQueries.getSimulatorIdBySimulationId(selectedSimulationId).toString();
	    this.handleValueChangeEvent();
	    setEnginesInfo(selectedSimulatorId, null, null);
	}
    }

    private void setSimulationNotSelectedState() {
	getErrorLabel().setValue(NO_SIMULATION_SELECTED);
	mainSimulationLayout.setVisible(false);
    }

    @Override
    protected void initGoogleMaps() {
	LOG.info("initGoogleMaps(). Google maps object: {}", googleMap);
	if (googleMap != null) {
	    googleMap.clearMap();
	} else {
	    LOG.info("initGoogleMaps() - creating new FlightPathGoogleMapPastSim");
	    this.googleMap = new FlightPathPastSim(new LatLon(60.440963, 22.25122), 4.0,
		    ApplicationConfiguration.getGoogleMapApiKey(), this);
	}
    }

    @Override
    protected void resetUI() {
	LOG.info("resetUI()");
	primaryFlightDisplay.resetPfd();
	altitudeChart.resetChart();
	speedChart.resetChart();
	googleMap.clearMap();

    }

}
