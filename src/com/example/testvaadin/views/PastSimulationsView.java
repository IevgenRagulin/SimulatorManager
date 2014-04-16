package com.example.testvaadin.views;

import com.example.testvaadin.jscomponents.jshighchart.JsHighChart.ValueChangeListener;
import com.vaadin.data.Item;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public class PastSimulationsView extends SimulationsView implements View {
	private static final long serialVersionUID = -3892686063360142032L;
	private String selectedSimulationId;
	protected static final String NO_SIMULATION_SELECTED = "Please, select simulation";

	public PastSimulationsView(Navigator navigator) {
		super(navigator);
	}

	@Override
	protected void updateUI() {
		altitudeChart
				.initChartWithDataForSimulationWithId(selectedSimulationId);
		altitudeChart.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -192918332232524200L;

			@Override
			public void valueChange() {
				setDevStateInfo(altitudeChart.getClickedId());
				setPfdInfo(altitudeChart.getClickedId());
			}

		});
		speedChart.initChartWithDataForSimulationWithId(selectedSimulationId);
	}

	protected void setDevStateInfo(int clickedId) {
		Item itemDevState = dbHelp
				.getSimulationDevStateInfoByPfdInfoId(clickedId);
		Item itemSimulator = dbHelp.getSimulatorInfoByPfdInfoId(clickedId);
		flightControls.updateIndividualFlightControlValues(itemDevState,
				itemSimulator);
	}

	private void setPfdInfo(int pfdId) {
		Item item = dbHelp.getPFDInfoByPfdInfoId(pfdId);
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

}
