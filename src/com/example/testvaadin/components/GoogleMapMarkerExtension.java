package com.example.testvaadin.components;

import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;

public class GoogleMapMarkerExtension extends GoogleMapMarker {
	private static final long serialVersionUID = 2230100293285986898L;
	private LatLon anchor = new LatLon(0, 0);

	public GoogleMapMarkerExtension(String caption, LatLon position,
			boolean draggable, LatLon anchor) {
		super(caption, position, draggable);
		this.anchor = anchor;
	}

	public GoogleMapMarkerExtension() {
		super();
	}

	/**
	 * Returns the anchor of the marker.
	 * 
	 * @return The anchor of the marker.
	 */
	public LatLon getAnchor() {
		return anchor;
	}

	public void setAnchor(LatLon anchor) {
		this.anchor = anchor;
	}
}
