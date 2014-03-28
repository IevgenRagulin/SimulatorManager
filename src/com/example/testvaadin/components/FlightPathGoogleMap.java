package com.example.testvaadin.components;

import java.util.ArrayList;
import java.util.Collection;

import cm.example.testvaadin.simulatorcommunication.SimulatorsStatus;

import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.views.RunningSimulationsView;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapInfoWindow;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;

public class FlightPathGoogleMap extends GoogleMap {
	private static final String CURRENT_AIRCRAFT_POSITION_TEXT = "Current aircraft position";
	private static final String MAP_STYLE_NAME = "flightPathMap";
	private final String MAP_WIDTH = "500px";
	private final String MAP_HEIGHT = "500px";
	private boolean isMapInitializedWithMapHistory = false;
	private LatLon lastLatLong = null;
	private ArrayList<LatLon> planePathPoints = new ArrayList<LatLon>();
	private GoogleMapPolyline flightPath = new GoogleMapPolyline(
			planePathPoints, "#d31717", 0.8, 3);
	GoogleMapMarker newPositionMarker = new GoogleMapMarker();
	private GoogleMapInfoWindow latestCoordinatesWindow = new GoogleMapInfoWindow(
			"Kakola used to be a provincial prison.", newPositionMarker);
	private final double[] possibleIconPos = { 0, 22.5, 45.0, 67.5, 90.0,
			112.5, 135.0, 157.5, 180.0, 202.5, 225.0, 247.5, 270.0, 292.5,
			315.0, 337.5 };

	private RunningSimulationsView view = null;

	public FlightPathGoogleMap(LatLon center, double zoom,
			String apiKeyOrClientId, RunningSimulationsView view) {
		super(center, zoom, apiKeyOrClientId);
		this.view = view;
		setMapConfiguration();
	}

	private void setMapConfiguration() {
		setMapType(GoogleMap.MapType.Terrain);
		setSizeFull();
		setMinZoom(1.0);
		setMaxZoom(16.0);
		setWidth(MAP_WIDTH);
		setHeight(MAP_HEIGHT);
		setPrimaryStyleName(MAP_STYLE_NAME);
		// Display coordinates on clicking the marker
		OpenInfoWindowOnMarkerClickListener infoWindowOpener = new OpenInfoWindowOnMarkerClickListener(
				this, newPositionMarker, latestCoordinatesWindow);
		this.addMarkerClickListener(infoWindowOpener);
		newPositionMarker.setAnimationEnabled(false);
		latestCoordinatesWindow.setWidth("100px");
	}

	public void initMapWithDataForSimulationWithId(String simulatorId) {
		SQLContainer simulationInfoData = view.getDBHelp()
				.getAllSimulationInfoBySimulatorId(simulatorId);
		Double trueCourse = SimulatorsStatus
				.getSimulationPFDItemBySimulatorId(simulatorId).getBean()
				.getTruecourse();
		planePathPoints = new ArrayList<LatLon>();
		addOldDataToMap(simulationInfoData, trueCourse);
	}

	private void addOldDataToMap(SQLContainer simulationInfoData,
			Double trueCourse) {
		planePathPoints = getArrayListOfFlightPathPoints(simulationInfoData);
		this.flightPath.setCoordinates(planePathPoints);
		addPolyline(this.flightPath);
		addMarker(newPositionMarker);
		setCenter(lastLatLong);
		addMarkerOnMap(lastLatLong, trueCourse);
	}

	private String getIconUrl(Double trueCourse) {
		return "VAADIN/themes/testvaadin/plane_icons/"
				+ chooseIconBasedOnCourse(trueCourse);
	}

	/**
	 * Returns icon name based on course. True course is between 0 and 360. I.e.
	 * if true course is 47 degrees, the method choosest 45 degrees item
	 * 
	 * @param trueCourse
	 *            - airplane course
	 * @return icon file name
	 */
	private String chooseIconBasedOnCourse(Double trueCourse) {
		trueCourse = trueCourse % 360;
		Double closestIconPos = possibleIconPos[0];
		for (int i = 0; i < possibleIconPos.length; i++) {
			if (Math.abs(possibleIconPos[i] - trueCourse) < Math
					.abs((closestIconPos - trueCourse))) {
				closestIconPos = possibleIconPos[i];
			}
		}
		System.out.println("chosen icon" + closestIconPos.toString() + ".png");
		return closestIconPos.toString() + ".png";
	}

	private ArrayList<LatLon> getArrayListOfFlightPathPoints(
			SQLContainer simulationInfoData) {
		ArrayList<LatLon> planePathPoints = new ArrayList<LatLon>();
		if (simulationInfoData != null) {
			Collection<?> simulationInfoIds = simulationInfoData.getItemIds();
			for (Object i : simulationInfoIds) {
				RowId rowId = (RowId) i;
				Item item = simulationInfoData.getItem(rowId);
				Double lastLat = ((Double) item.getItemProperty(
						ColumnNames.getLatitude()).getValue());
				Double lastLon = ((Double) item.getItemProperty(
						ColumnNames.getLongtitude()).getValue());
				lastLatLong = new LatLon(lastLat, lastLon);
				planePathPoints.add(lastLatLong);
			}
		}
		return planePathPoints;
	}

	public void clearMap() {
		isMapInitializedWithMapHistory = false;
		clearMarkers();
		if (flightPath != null) {
			removePolyline(flightPath);
		}
	}

	public void addLatestCoordinatesForSimulation(Item item, String simulatorId) {
		if (!isMapInitializedWithMapHistory) {
			initMapWithDataForSimulationWithId(simulatorId);
			isMapInitializedWithMapHistory = true;
		}
		Double newLongtitude = (Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getLongtitude())).getValue();
		Double newLatitude = (Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getLatitude())).getValue();
		LatLon newPosition = new LatLon(newLatitude, newLongtitude);
		Double trueCourse = SimulatorsStatus
				.getSimulationPFDItemBySimulatorId(simulatorId).getBean()
				.getTruecourse();
		latestCoordinatesWindow.setContent(newPosition.getLat() + " "
				+ newPosition.getLon());
		// Check if coordinates of the new position differ from the previous
		// one. If they don't differ, do nothing. If they do differ, add data on
		// the map
		if (!newPosition.equals(this.lastLatLong) && (newPosition != null)) {
			addMarkerOnMap(newPosition, trueCourse);
		}
	}

	private void addMarkerOnMap(LatLon newPosition, Double trueCourse) {
		newPositionMarker.setPosition(newPosition);
		newPositionMarker.setIconUrl(getIconUrl(trueCourse));
		this.lastLatLong = newPosition;
		addLatestCoordinatesToFlightPath(newPosition);
	}

	private void addLatestCoordinatesToFlightPath(LatLon newPosition) {
		planePathPoints.add(newPosition);
		flightPath.setCoordinates(planePathPoints);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4678405407558787986L;

}
