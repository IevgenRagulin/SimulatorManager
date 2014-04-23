package com.example.testvaadin.components;

import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.views.SimulationsView;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.tapio.googlemaps.client.LatLon;

public class FlightPathGoogleMapPastSim extends FlightPathGoogleMapBase {
	private static final long serialVersionUID = -6094967132253571095L;

	public FlightPathGoogleMapPastSim(LatLon center, double zoom,
			String apiKeyOrClientId, SimulationsView view) {
		super(center, zoom, apiKeyOrClientId, view);
	}

	@Override
	public void clearMap() {
		clearMarkers();
		if (flightPath != null) {
			removePolyline(flightPath);
		}
	}

	public void moveMarkerOnMap(Item item, Double trueCourse) {
		if (item != null) {
			Double newLongtitude = (Double) ((Property<?>) item
					.getItemProperty(ColumnNames.getLongtitude())).getValue();
			Double newLatitude = (Double) ((Property<?>) item
					.getItemProperty(ColumnNames.getLatitude())).getValue();
			LatLon newPosition = new LatLon(newLatitude, newLongtitude);
			latestCoordinatesWindow.setContent(newPosition.getLat() + " "
					+ newPosition.getLon());
			newPositionMarker.setPosition(newPosition);
			newPositionMarker.setIconUrl(getIconUrl(trueCourse));
			this.lastLatLong = newPosition;
			addMarker(newPositionMarker);
			this.markAsDirty();
		}
	}

}
