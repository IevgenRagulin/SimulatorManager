package com.example.testvaadin.components;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.views.RunningSimulationsView;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
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
			planePathPoints, "#d31717", 0.8, 10);

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
	}

	public void initMapWithDataForSimulationWithId(String simulatorId) {
		SQLContainer simulationInfoData = view.getDBHelp()
				.getAllSimulationInfoBySimulatorId(simulatorId);
		planePathPoints = new ArrayList<LatLon>();
		addOldDataToMap(simulationInfoData);
	}

	private void addOldDataToMap(SQLContainer simulationInfoData) {
		planePathPoints = getArrayListOfFlightPathPoints(simulationInfoData);
		this.flightPath.setCoordinates(planePathPoints);
		addPolyline(this.flightPath);
		addMarker(CURRENT_AIRCRAFT_POSITION_TEXT, lastLatLong, false, null);
		setCenter(lastLatLong);
	}

	private ArrayList<LatLon> getArrayListOfFlightPathPoints(
			SQLContainer simulationInfoData) {
		ArrayList<LatLon> planePathPoints = new ArrayList<LatLon>();
		if (simulationInfoData != null) {
			Collection<?> simulationInfoIds = simulationInfoData.getItemIds();
			for (Object i : simulationInfoIds) {
				RowId rowId = (RowId) i;
				Item item = simulationInfoData.getItem(rowId);
				Double lastLat = ((BigDecimal) item.getItemProperty(
						ColumnNames.getLatitude()).getValue()).doubleValue();
				Double lastLon = ((BigDecimal) item.getItemProperty(
						ColumnNames.getLongtitude()).getValue()).doubleValue();
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

	public void addLatestCoordinatesForSimulation(Item item) {
		String simulatorId = item
				.getItemProperty(
						ColumnNames.getSimulationIdForeignKeyPropName())
				.getValue().toString();
		if (!isMapInitializedWithMapHistory) {
			initMapWithDataForSimulationWithId(simulatorId);
			isMapInitializedWithMapHistory = true;
		}
		Double newLongtitude = ((BigDecimal) ((Property<?>) item
				.getItemProperty(ColumnNames.getLongtitude())).getValue())
				.doubleValue();
		Double newLatitude = ((BigDecimal) ((Property<?>) item
				.getItemProperty(ColumnNames.getLatitude())).getValue())
				.doubleValue();
		LatLon newPosition = new LatLon(newLatitude, newLongtitude);
		// Check if coordinates of the new position differ from the previous
		// one. If they don't differ, do nothing. If they do differ, add data on
		// the map
		if (!newPosition.equals(this.lastLatLong)) {
			addMarkerOnMap(newPosition);
		}
	}

	private void addMarkerOnMap(LatLon newPosition) {
		clearMarkers();
		GoogleMapMarker newPositionMarker = new GoogleMapMarker(
				CURRENT_AIRCRAFT_POSITION_TEXT, newPosition, false, null);
		newPositionMarker.setAnimationEnabled(false);
		addMarker(newPositionMarker);
		setCenter(newPosition);
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
