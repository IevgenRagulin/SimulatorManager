package cz.vutbr.fit.simulatormanager.components;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.tapio.googlemaps.client.LatLon;

import cz.vutbr.fit.simulatormanager.data.AppConfig;
import cz.vutbr.fit.simulatormanager.items.SimulationInfoItem;
import cz.vutbr.fit.simulatormanager.simulatorcommunication.SimulatorsStatus;
import cz.vutbr.fit.simulatormanager.util.DistanceUtil;
import cz.vutbr.fit.simulatormanager.views.SimulationsView;

/**
 * Google map for running simulation
 * 
 * @author ievgen
 *
 */
public class FlightPathRunningSim extends FlightPathBase {
	private static final long serialVersionUID = -4678405407558787986L;
	final static Logger logger = LoggerFactory.getLogger(FlightPathRunningSim.class);

	// in combination with addNewPositionFrequency from application
	// configuration used to determine if we
	// should add new coord on the map
	private static int addedCount = 0;
	private boolean isMapInitializedWithMapHistory = false;
	private static int ONE_KM = 1000;

	public FlightPathRunningSim(LatLon center, double zoom, String apiKeyOrClientId, SimulationsView view) {
		super(center, zoom, apiKeyOrClientId, view);
	}

	public void addLatestCoordForSimulation(SimulationInfoItem simulationInfo, String simulatorId) {
		if (hasNewSimulationStarted(simulationInfo, simulatorId)) {
			logger.info("addLatestCoordForSimulation() - new simulation started, clear map, simulatorId: {}",
					simulatorId);
			clearMap();
		}
		int addNewPositionFrequency = Math.round(AppConfig.getUpdateAirplanePositionFrequency()
				/ AppConfig.getRefreshUiFrequency());
		if (!isMapInitializedWithMapHistory) {
			initMapWithDataForSimulatorWithId(simulatorId);
			isMapInitializedWithMapHistory = true;
		}
		if (shouldWeAddMarkerOnMap()) {
			Double trueCourse = SimulatorsStatus.getSimulationPFDItemBySimulatorId(simulatorId).getBean()
					.getTruecourse();
			addMarkerOnMap(simulationInfo, trueCourse);
		}
		addedCount = (addedCount + 1) % addNewPositionFrequency;
	}

	/**
	 * Check if map represent the same simulation session as before or if a new
	 * simulation has started by comparing current and previous position of
	 * simulator
	 * 
	 * @param simulatorId
	 * 
	 * @return
	 */
	private boolean hasNewSimulationStarted(SimulationInfoItem item, String simulatorId) {
		SimulationInfoItem prevSimInfo = SimulatorsStatus.getPrevSimulationInfoItemBySimulatorId(simulatorId);
		return (DistanceUtil.hasPlaneMovedMoreThan(item, prevSimInfo, ONE_KM));
	}

	/**
	 * Initializes map with latest simulation info for simulator with id
	 * simulatorId. Modifies marker position
	 */
	private void initMapWithDataForSimulatorWithId(String simulatorId) {
		logger.info("initMapWithDataForSimulatorWithId() - init map with data from database");
		SQLContainer simulationInfoData = view.getDBHelp().getAllSimulationInfoBySimulatorId(simulatorId);
		Double trueCourse = SimulatorsStatus.getSimulationPFDItemBySimulatorId(simulatorId).getBean().getTruecourse();
		planePathPoints = new ArrayList<LatLon>();// TODO: delete this line?
		if (simulationInfoData.size() > 0) {
			addOldDataToMap(simulationInfoData, trueCourse);
		}
	}

	/**
	 * Returns true if addedCount==0 (we save data to db every
	 * saveToDbFrequencytime)
	 */

	private static boolean shouldWeAddMarkerOnMap() {
		logger.debug("shouldWeAddMarkerOnMap() - {}", addedCount == 0);
		return addedCount == 0;
	}

	@Override
	public void clearMap() {
		logger.info("clear() - clearing map");
		isMapInitializedWithMapHistory = false;
		clearMarkers();
		if (flightPath != null) {
			removePolyline(flightPath);
		}
	}

}
