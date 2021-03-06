package cz.vutbr.fit.simulatormanager.components;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapInfoWindow;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;

import cz.vutbr.fit.simulatormanager.database.columns.SimulationInfoCols;
import cz.vutbr.fit.simulatormanager.items.SimulationInfoItem;
import cz.vutbr.fit.simulatormanager.views.SimulationsView;

public abstract class FlightPathBase extends GoogleMap {
	private static final long serialVersionUID = 523060061909689421L;
	final static Logger logger = LoggerFactory.getLogger(FlightPathBase.class);

	private static final String PLANE_ICONS_PATH = "VAADIN/themes/testvaadinn/plane_icons/";
	private static final String MAP_STYLE_NAME = "flightPathMap";
	private final String MAP_WIDTH = "350px";
	private final String MAP_HEIGHT = "350px";
	protected ArrayList<LatLon> planePathPoints = new ArrayList<LatLon>();
	protected GoogleMapPolyline flightPath = new GoogleMapPolyline(planePathPoints, "#d31717", 0.8, 3);
	GoogleMapMarker newPositionMarker = new GoogleMapMarker();
	protected GoogleMapInfoWindow latestCoordinatesWindow = new GoogleMapInfoWindow("", newPositionMarker);
	private final double[] possibleIconPos = { 0, 22.5, 45.0, 67.5, 90.0, 112.5, 135.0, 157.5, 180.0, 202.5, 225.0,
			247.5, 270.0, 292.5, 315.0, 337.5 };

	protected SimulationsView view = null;
	protected LatLon lastLatLong = null;

	public FlightPathBase(LatLon center, double zoom, String apiKeyOrClientId, SimulationsView view) {
		super(center, zoom, apiKeyOrClientId);
		logger.info("new FlightPathGoogleMapBase()");
		this.view = view;
		setMapConfiguration();
		addPolyline(this.flightPath);

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
		OpenInfoWindowOnMarkerClickListener infoWindowOpener = new OpenInfoWindowOnMarkerClickListener(this,
				newPositionMarker, latestCoordinatesWindow);
		this.addMarkerClickListener(infoWindowOpener);
		newPositionMarker.setAnimationEnabled(false);
		latestCoordinatesWindow.setWidth("100px");
	}

	public void addOldDataToMap(SQLContainer simulationInfoData, Double trueCourse) {
		planePathPoints = getArrayListOfFlightPathPoints(simulationInfoData);
		logger.info("addOldDataToMap() - planePathPoints.size: {}", planePathPoints.size());
		this.flightPath.setCoordinates(planePathPoints);
		setCenter(lastLatLong);
		// add marker to the last position
		addMarkerOnMap(lastLatLong, trueCourse);
	}

	protected String getIconUrl(Double trueCourse) {
		return PLANE_ICONS_PATH + chooseIconBasedOnCourse(trueCourse);
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
			if (Math.abs(possibleIconPos[i] - trueCourse) < Math.abs((closestIconPos - trueCourse))) {
				closestIconPos = possibleIconPos[i];
			}
		}
		return closestIconPos.toString() + ".png";
	}

	private ArrayList<LatLon> getArrayListOfFlightPathPoints(SQLContainer simulationInfoData) {
		ArrayList<LatLon> planePathPoints = new ArrayList<LatLon>();
		if (simulationInfoData != null) {
			Collection<?> simulationInfoIds = simulationInfoData.getItemIds();
			for (Object i : simulationInfoIds) {
				RowId rowId = (RowId) i;
				Item item = simulationInfoData.getItem(rowId);
				Double lastLat = ((Double) item.getItemProperty(SimulationInfoCols.latitude.toString()).getValue());
				Double lastLon = ((Double) item.getItemProperty(SimulationInfoCols.longtitude.toString()).getValue());
				lastLatLong = new LatLon(lastLat, lastLon);
				planePathPoints.add(lastLatLong);
			}
		}
		return planePathPoints;
	}

	public abstract void clearMap();

	protected void addMarkerOnMap(SimulationInfoItem item, Double trueCourse) {
		Double newLongtitude = item.getBean().getLongtitude();
		Double newLatitude = item.getBean().getLatitude();
		LatLon newPosition = new LatLon(newLatitude, newLongtitude);

		latestCoordinatesWindow.setContent(newPosition.getLat() + " " + newPosition.getLon());
		// Check if coordinates of the new position differ from the previous
		// one. If they don't differ, do nothing. If they do differ, add
		// data on
		// the map
		if (!newPosition.equals(this.lastLatLong) && (newPosition != null)) {
			addMarkerOnMap(newPosition, trueCourse);
		}
		this.markAsDirty();
	}

	protected void addMarkerOnMap(LatLon newPosition, Double trueCourse) {
		newPositionMarker.setPosition(newPosition);
		newPositionMarker.setIconUrl(getIconUrl(trueCourse));
		/*
		 * This is needed to position the marker's image's center at the
		 * coordinate By default bottom center of the marker's image is located
		 * at the coordinate. All 6 values need to be set, otherwise anchoring
		 * will not work
		 */
		newPositionMarker.setAnchX(15.0);
		newPositionMarker.setAnchY(15.0);
		newPositionMarker.setOrigX(0.0);
		newPositionMarker.setOrigY(0.0);
		newPositionMarker.setSizeX(30.0);// set width of the icon
		newPositionMarker.setSizeY(30.0);// set height of the icon
		this.lastLatLong = newPosition;
		addLatestCoordinatesToFlightPath(newPosition);
		addMarker(newPositionMarker);
	}

	private void addLatestCoordinatesToFlightPath(LatLon newPosition) {
		planePathPoints.add(newPosition);
		flightPath.setCoordinates(planePathPoints);
	}
}
