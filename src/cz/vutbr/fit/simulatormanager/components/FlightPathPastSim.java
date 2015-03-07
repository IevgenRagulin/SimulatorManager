package cz.vutbr.fit.simulatormanager.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.tapio.googlemaps.client.LatLon;

import cz.vutbr.fit.simulatormanager.database.columns.SimulationInfoCols;
import cz.vutbr.fit.simulatormanager.views.SimulationsView;

/**
 * Google map for past simulation
 * 
 * @author ievgen
 *
 */
public class FlightPathPastSim extends FlightPathBase {
	private static final long serialVersionUID = -6094967132253571095L;
	final static Logger logger = LoggerFactory.getLogger(FlightPathPastSim.class);

	public FlightPathPastSim(LatLon center, double zoom, String apiKeyOrClientId, SimulationsView view) {
		super(center, zoom, apiKeyOrClientId, view);
	}

	@Override
	public void clearMap() {
		logger.debug("clear() - clearing map");
		clearMarkers();
		if (flightPath != null) {
			removePolyline(flightPath);
		}
	}

	public void moveMarkerOnMap(Item item, Double trueCourse) {
		if (item != null) {
			Double newLongtitude = (Double) ((Property<?>) item.getItemProperty(SimulationInfoCols.longtitude
					.toString())).getValue();
			Double newLatitude = (Double) ((Property<?>) item.getItemProperty(SimulationInfoCols.latitude.toString()))
					.getValue();
			LatLon newPosition = new LatLon(newLatitude, newLongtitude);
			latestCoordinatesWindow.setContent(newPosition.getLat() + " " + newPosition.getLon());
			newPositionMarker.setPosition(newPosition);
			newPositionMarker.setIconUrl(getIconUrl(trueCourse));
			this.lastLatLong = newPosition;
			addMarker(newPositionMarker);
			this.markAsDirty();
		}
	}

}
