package com.example.testvaadin.views;

import com.example.testvaadin.components.FlightPathGoogleMapPastSim;
import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.jscomponents.jshighchart.JsHighChart.ValueChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.tapio.googlemaps.client.LatLon;

public class PastSimulationsView extends SimulationsView implements View {
	private static final long serialVersionUID = -3892686063360142032L;
	private String selectedSimulationId;
	protected static final String NO_SIMULATION_SELECTED = "Please, select simulation";
	protected FlightPathGoogleMapPastSim googleMap = null;

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
				setUi(altitudeChart.getClickedId(),
						altitudeChart.getTimestamp());
			}
		});
		speedChart.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -192918332232524200L;

			@Override
			public void valueChange() {
				setUi(speedChart.getClickedId(), speedChart.getTimestamp());
			}
		});
	}

	@Override
	protected void updateUI() {
		// here init google map with old data
		addFlightPathToMap(selectedSimulationId);
		altitudeChart
				.initChartWithDataForSimulationWithId(selectedSimulationId);
		speedChart.initChartWithDataForSimulationWithId(selectedSimulationId);
	}

	private void setUi(int pfdClickedId, long timestamp) {
		Item pfdItem = dbHelp.getPFDInfoByPfdInfoId(pfdClickedId);
		Item itemDevState = dbHelp.getSimulationDevStateInfoByPfdInfoId(
				pfdClickedId, timestamp);
		Item itemSimulator = dbHelp.getSimulatorInfoByPfdInfoId(pfdClickedId);
		Item simulationInfoItem = dbHelp
				.getSimulationInfoItemByPfdInfoIdTimestemp(pfdClickedId,
						timestamp);
		setDevStateInfo(itemDevState, itemSimulator);
		setPfdInfo(pfdItem);
		setGoogleMapInfo(simulationInfoItem, pfdItem);
	}

	private void addFlightPathToMap(String simulationId) {
		SQLContainer simulationInfoData = dbHelp
				.getAllSimulationInfoBySimulationId(simulationId);
		googleMap.addOldDataToMap(simulationInfoData, 0.0);
	}

	protected void setGoogleMapInfo(Item simulationInfoItem, Item pfdInfoItem) {
		Double trueCourse = (Double) pfdInfoItem.getItemProperty(
				ColumnNames.getTrueCourse()).getValue();
		googleMap.moveMarkerOnMap(simulationInfoItem, trueCourse);
	}

	protected void setDevStateInfo(Item itemDevState, Item itemSimulator) {
		flightControls.updateIndividualFlightControlValues(itemDevState,
				itemSimulator);
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
		System.out.println("going to init google maps" + googleMap);
		if (googleMap != null) {
			googleMap.clearMap();
		} else {
			FlightPathGoogleMapPastSim googleMap = new FlightPathGoogleMapPastSim(
					new LatLon(60.440963, 22.25122), 4.0,
					ApplicationConfiguration.getGoogleMapApiKey(), this);
			setGoogleMap(googleMap);
		}
	}

	private void setGoogleMap(FlightPathGoogleMapPastSim googleMap) {
		this.googleMap = googleMap;
		System.out.println("PAS SIM VIEW. THIS. GOOGLE MAP" + this.googleMap);
	}

	@Override
	protected void resetUI() {
		primaryFlightDisplay.resetPfd();
		altitudeChart.resetChart();
		speedChart.resetChart();
		googleMap.clearMap();
	}

}
