package com.example.testvaadin.views;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.testvaadin.components.FlightPathPastSim;
import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.data.SimulationPfdInfoCols;
import com.example.testvaadin.jscomponents.jshighchart.JsHighChart.ValueChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.tapio.googlemaps.client.LatLon;

public class PastSimulationsView extends SimulationsView implements View {

	private static final long serialVersionUID = -3892686063360142032L;

	final static Logger logger = LoggerFactory.getLogger(PastSimulationsView.class);

	private String selectedSimulationId;
	protected static final String NO_SIMULATION_SELECTED = "Please, select simulation";

	protected FlightPathPastSim googleMap = null;

	public PastSimulationsView(Navigator navigator) {
		super(navigator);
		initGoogleMaps();
		avionycsLayout.addComponent(googleMap);

	}

	@Override
	protected void setClickListeners() {
		super.setClickListeners();
		altitudeChart.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -192918332232524200L;

			@Override
			public void valueChange() {
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
		logger.info("updateUI()");
		// init google map with old data
		addFlightPathToMap(selectedSimulationId);
		altitudeChart.initChartWithDataForSimulationWithId(selectedSimulationId);
		speedChart.initChartWithDataForSimulationWithId(selectedSimulationId);
	}

	private void setUi(int pfdClickedId, long timestamp) throws SQLException {
		Item pfdItem = dbHelp.getPFDInfoByPfdInfoId(pfdClickedId);
		Item itemDevState = dbHelp.getSimulationDevStateInfoByPfdInfoId(pfdClickedId, timestamp);
		Item itemSimulator = dbHelp.getSimulatorInfoByPfdInfoId(pfdClickedId);
		Item simulationInfoItem = dbHelp.getSimulationInfoItemByPfdInfoIdTimestemp(pfdClickedId, timestamp);
		setDevStateInfo(itemDevState, itemSimulator);
		setPfdInfo(pfdItem);
		setGoogleMapInfo(simulationInfoItem, pfdItem);
	}

	private void addFlightPathToMap(String simulationId) {
		SQLContainer simulationInfo = dbHelp.getAllSimulationInfoBySimulationId(simulationId);
		logger.info("addFlightPathToMap - add old data to map for simulation id: {}, containerSize: {}", simulationId,
				simulationInfo.size());
		googleMap.addOldDataToMap(simulationInfo, 0.0);

	}

	protected void setGoogleMapInfo(Item simulationInfoItem, Item pfdInfoItem) {

		Double trueCourse = (Double) pfdInfoItem.getItemProperty(SimulationPfdInfoCols.truecourse.toString())
				.getValue();
		googleMap.moveMarkerOnMap(simulationInfoItem, trueCourse);

	}

	protected void setDevStateInfo(Item itemDevState, Item itemSimulator) {
		flightControls.updateIndividualFlightControlValues(itemDevState, itemSimulator);
	}

	private void setPfdInfo(Item item) {
		primaryFlightDisplay.updateIndividualPFDValues(item);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() == null || event.getParameters().isEmpty()) {
			setSimulationNotSelectedState();
		} else {
			selectedSimulationId = event.getParameters();
			this.handleValueChangeEvent();
		}
	}

	private void setSimulationNotSelectedState() {
		getErrorLabel().setValue(NO_SIMULATION_SELECTED);
		mainSimulationLayout.setVisible(false);
	}

	@Override
	protected void initGoogleMaps() {
		logger.info("initGoogleMaps(). Google maps object: {}", googleMap);
		if (googleMap != null) {
			googleMap.clearMap();
		} else {
			logger.info("initGoogleMaps() - creating new FlightPathGoogleMapPastSim");
			this.googleMap = new FlightPathPastSim(new LatLon(60.440963, 22.25122), 4.0,
					ApplicationConfiguration.getGoogleMapApiKey(), this);
		}
	}

	@Override
	protected void resetUI() {
		logger.info("resetUI()");
		primaryFlightDisplay.resetPfd();
		altitudeChart.resetChart();
		speedChart.resetChart();
		googleMap.clearMap();

	}

}
