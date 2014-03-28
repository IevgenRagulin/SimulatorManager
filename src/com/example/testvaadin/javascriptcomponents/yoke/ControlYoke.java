package com.example.testvaadin.javascriptcomponents.yoke;

import com.example.testvaadin.data.ColumnNames;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.AbstractJavaScriptComponent;

@SuppressWarnings("serial")
@com.vaadin.annotations.JavaScript({ "controlYoke.js" })
public class ControlYoke extends AbstractJavaScriptComponent {

	/*
	 * We use this bean in adition to state class, because when we access state
	 * class fields, the change state event is generated even if we don't change
	 * the state. So, we use this bean to imporove performance
	 */
	private ControlYokeStateBean controlYokeStateBean;

	public ControlYoke(float aileron, float elevator, float rudder) {
		getState().ail = aileron;
		getState().el = elevator;
		getState().rd = rudder;
		controlYokeStateBean = new ControlYokeStateBean(aileron, elevator,
				rudder);
	}

	public void updateIndividualControlYokeValues(Item item) {
		float newAileron = doubleToFloat((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getAileron())).getValue());
		float newElevator = doubleToFloat((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getElevator())).getValue());
		float newRudder = doubleToFloat((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getRudder())).getValue());
		setAileron(newAileron);
		setElevator(newElevator);
		setRudder(newRudder);

	}

	public float doubleToFloat(Double value) {
		return value.floatValue();
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

	public ControlYokeStateBean getStateBean() {
		return controlYokeStateBean;
	}

	@Override
	public ControlYokeState getState() {
		return (ControlYokeState) super.getState();
	}

}
