package com.example.testvaadin.components;

import java.util.ArrayList;

import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.simulatorcommunication.SimulatorsStatus;
import com.example.testvaadin.views.SimulationsView;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.tapio.googlemaps.client.LatLon;

public class FlightPathGoogleMapRunningSim extends FlightPathGoogleMapBase {
	private static final long serialVersionUID = -4678405407558787986L;

	// in combination with addNewPositionFrequency from application
	// configuration used to determine if we
	// should add new coord on the map
	private static int addedCount = 0;
	private boolean isMapInitializedWithMapHistory = false;

	public FlightPathGoogleMapRunningSim(LatLon center, double zoom,
			String apiKeyOrClientId, SimulationsView view) {
		super(center, zoom, apiKeyOrClientId, view);
	}

	public void addLatestCoordinatesForSimulation(Item item, String simulatorId) {
		int addNewPositionFrequency = Math.round(ApplicationConfiguration
				.getUpdateAirplanePositionFrequency()
				/ ApplicationConfiguration.getRefreshUiFrequency());
		if (!isMapInitializedWithMapHistory) {
			initMapWithDataForSimulatorWithId(simulatorId);
			isMapInitializedWithMapHistory = true;
		}
		if (shouldWeAddMarkerOnMap()) {
			Double trueCourse = SimulatorsStatus
					.getSimulationPFDItemBySimulatorId(simulatorId).getBean()
					.getTruecourse();
			addMarkerOnMap(item, trueCourse);
		}
		addedCount = (addedCount + 1) % addNewPositionFrequency;
	}

	/*
	 * Initializes map with latest simulation info for simulator with id
	 * simulatorId. Modifies marker position
	 */
	private void initMapWithDataForSimulatorWithId(String simulatorId) {
		SQLContainer simulationInfoData = view.getDBHelp()
				.getAllSimulationInfoBySimulatorId(simulatorId);
		Double trueCourse = SimulatorsStatus
				.getSimulationPFDItemBySimulatorId(simulatorId).getBean()
				.getTruecourse();
		planePathPoints = new ArrayList<LatLon>();
		if (simulationInfoData.size() > 0) {
			addOldDataToMap(simulationInfoData, trueCourse);
		}
	}

	// Returns true if addedCount==0 (we save data to db every saveToDbFrequency
	// time)
	private static boolean shouldWeAddMarkerOnMap() {
		return addedCount == 0;
	}

	@Override
	public void clearMap() {
		isMapInitializedWithMapHistory = false;
		clearMarkers();
		if (flightPath != null) {
			removePolyline(flightPath);
		}
	}

}
