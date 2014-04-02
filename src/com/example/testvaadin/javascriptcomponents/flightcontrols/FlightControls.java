package com.example.testvaadin.javascriptcomponents.flightcontrols;

import com.example.testvaadin.data.ColumnNames;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.AbstractJavaScriptComponent;

@SuppressWarnings("serial")
@com.vaadin.annotations.JavaScript({ "flightControls.js" })
public class FlightControls extends AbstractJavaScriptComponent {

	/*
	 * We use this bean in adition to state class, because when we access state
	 * class fields, the change state event is generated even if we don't change
	 * the state. So, we use this bean to imporove performance
	 */
	private FlightControlsStateBean controlYokeStateBean;

	public FlightControls(float aileron, float elevator, float rudder,
			float speedbrakes, float flaps) {

		getState().ail = aileron;
		getState().el = elevator;
		getState().rd = rudder;
		controlYokeStateBean = new FlightControlsStateBean(aileron, elevator,
				rudder, speedbrakes, flaps);
	}

	public void updateIndividualFlightControlValues(Item flightControlItem,
			Item simulatorItem) {
		float newAileron = doubleToFloat((Double) ((Property<?>) flightControlItem
				.getItemProperty(ColumnNames.getAileron())).getValue());
		float newElevator = doubleToFloat((Double) ((Property<?>) flightControlItem
				.getItemProperty(ColumnNames.getElevator())).getValue());
		float newRudder = doubleToFloat((Double) ((Property<?>) flightControlItem
				.getItemProperty(ColumnNames.getRudder())).getValue());
		float newSpeedBrakes = doubleToFloat((Double) ((Property<?>) flightControlItem
				.getItemProperty(ColumnNames.getSpeedbrakes())).getValue());
		float newFlaps = doubleToFloat((Double) ((Property<?>) flightControlItem
				.getItemProperty(ColumnNames.getFlaps())).getValue());
		setAileron(newAileron);
		setElevator(newElevator);
		setRudder(newRudder);
		setFlaps(newFlaps);
		setPlaneConfiguration(simulatorItem);
		setSpeedBrakes(newSpeedBrakes);

	}

	private void setPlaneConfiguration(Item simulatorItem) {
		int maxSpeedOnFlaps = (Integer) ((Property<?>) simulatorItem
				.getItemProperty(ColumnNames.getMaxspeedonflaps())).getValue();
		if (getStateBean().getMaxonflaps() != maxSpeedOnFlaps) {
			getState().maxonflaps = maxSpeedOnFlaps;
			getStateBean().setMaxonflaps(maxSpeedOnFlaps);
		}

	}

	private void setSpeedBrakes(float newSpeedBrakes) {
		if (getStateBean().getSpeedBrakes() != newSpeedBrakes) {
			getState().sb = newSpeedBrakes;
			getStateBean().setSpeedBrakes(newSpeedBrakes);
		}
	}

	public float doubleToFloat(Double value) {
		if (value != null) {
			return value.floatValue();
		}
		return 0;
	}

	private void setFlaps(float newFlaps) {
		if (getStateBean().getFlaps() != newFlaps) {
			getState().fl = newFlaps;
			getStateBean().setFlaps(newFlaps);
		}
	}

	private void setAileron(float newAileron) {
		if (getStateBean().getAileron() != newAileron) {
			getState().ail = newAileron;
			getStateBean().setAileron(newAileron);
		}
	}

	private void setElevator(float newElevator) {
		if (getStateBean().getElevator() != newElevator) {
			getState().el = newElevator;
			getStateBean().setElevator(newElevator);
		}
	}

	private void setRudder(float newRudder) {
		if (getStateBean().getRudder() != newRudder) {
			getState().rd = newRudder;
			getStateBean().setRudder(newRudder);
		}
	}

	public FlightControlsStateBean getStateBean() {
		return controlYokeStateBean;
	}

	@Override
	public FlightControlsState getState() {
		return (FlightControlsState) super.getState();
	}

}