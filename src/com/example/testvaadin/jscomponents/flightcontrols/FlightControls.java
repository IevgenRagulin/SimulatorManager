package com.example.testvaadin.jscomponents.flightcontrols;

import com.example.testvaadin.data.ColumnNames;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.AbstractJavaScriptComponent;

@SuppressWarnings("serial")
@com.vaadin.annotations.JavaScript({ "flightControls.js" })
public class FlightControls extends AbstractJavaScriptComponent {

	private static final String CSS_CLASS = "FLIGHT_CONTROLS";
	/*
	 * We use this bean in adition to state class, because when we access state
	 * class fields, the change state event is generated even if we don't change
	 * the state. So, we use this bean to imporove performance
	 */
	private FlightControlsStateBean controlYokeStateBean;

	public FlightControls(float aileron, float elevator, float rudder,
			float aileront, float elevatort, float ruddert, float speedbrakes,
			float flaps, boolean brakes, boolean paused,
			int numbOfLandingGears, int landing_gear_1, int landing_gear_2,
			int landing_gear_3) {
		getState().ail = aileron;
		getState().el = elevator;
		getState().rd = rudder;
		getState().ailt = aileront;
		getState().elt = elevatort;
		getState().rdt = ruddert;
		getState().b = brakes;
		getState().p = paused;
		getState().numoflandg = numbOfLandingGears;
		getState().landg_1 = landing_gear_1;
		getState().landg_2 = landing_gear_2;
		getState().landg_3 = landing_gear_3;
		controlYokeStateBean = new FlightControlsStateBean(aileron, elevator,
				rudder, aileront, elevatort, ruddert, speedbrakes, flaps,
				brakes, paused, numbOfLandingGears, landing_gear_1,
				landing_gear_2, landing_gear_3);
		setPrimaryStyleName(CSS_CLASS);
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
		float newAileronTrim = doubleToFloat((Double) ((Property<?>) flightControlItem
				.getItemProperty(ColumnNames.getAileronTrim())).getValue());
		float newElevatorTrim = doubleToFloat((Double) ((Property<?>) flightControlItem
				.getItemProperty(ColumnNames.getElevatorTrim())).getValue());
		float newRudderTrim = doubleToFloat((Double) ((Property<?>) flightControlItem
				.getItemProperty(ColumnNames.getRudderTrim())).getValue());
		boolean newBrakes = (Boolean) ((Property<?>) flightControlItem
				.getItemProperty(ColumnNames.getBrakes())).getValue();
		boolean newPaused = (Boolean) ((Property<?>) flightControlItem
				.getItemProperty(ColumnNames.getIssimulationpaused()))
				.getValue();
		int landingGear_1 = getLandingGearValue(ColumnNames.getLandinggear1(),
				flightControlItem);
		int landingGear_2 = getLandingGearValue(ColumnNames.getLandinggear2(),
				flightControlItem);
		int landingGear_3 = getLandingGearValue(ColumnNames.getLandinggear3(),
				flightControlItem);

		setAileron(newAileron);
		setElevator(newElevator);
		setRudder(newRudder);
		setAileronTrim(newAileronTrim);
		setElevatorTrim(newElevatorTrim);
		setRudderTrim(newRudderTrim);
		setFlaps(newFlaps);
		setPlaneConfiguration(simulatorItem);
		setSpeedBrakes(newSpeedBrakes);
		setBrakes(newBrakes);
		setPaused(newPaused);
		setLandingGear(landingGear_1, landingGear_2, landingGear_3);
	}

	private int getLandingGearValue(String columnName, Item flightControlItem) {
		Object propertyValue = ((Property<?>) flightControlItem
				.getItemProperty(columnName)).getValue();
		if (propertyValue != null) {
			return (Integer) propertyValue;
		} else {
			return -1;
		}
	}

	private void setLandingGear(int landingGear_1, int landingGear_2,
			int landingGear_3) {
		System.out.println("set landing gear one two three" + landingGear_1
				+ " " + landingGear_2 + " " + landingGear_3 + " ");
		if (getStateBean().getLandg_1() != landingGear_1) {
			getState().landg_1 = landingGear_1;
			getStateBean().setLandg_1(landingGear_1);
		}
		if (getStateBean().getLandg_2() != landingGear_2) {
			getState().landg_2 = landingGear_2;
			getStateBean().setLandg_2(landingGear_2);
		}
		if (getStateBean().getLandg_3() != landingGear_3) {
			getState().landg_3 = landingGear_3;
			getStateBean().setLandg_3(landingGear_3);
		}
	}

	private void setPaused(boolean newPaused) {
		if (getStateBean().isPaused() != newPaused) {
			getState().p = newPaused;
			getStateBean().setPaused(newPaused);
		}
	}

	private void setBrakes(boolean newBrakes) {
		if (getStateBean().getBrakes() != newBrakes) {
			getState().b = newBrakes;
			getStateBean().setBrakes(newBrakes);
		}
	}

	private void setRudderTrim(float newRudderTrim) {
		if (getStateBean().getRudderTrim() != newRudderTrim) {
			getState().rdt = newRudderTrim;
			getStateBean().setRudderTrim(newRudderTrim);
		}
	}

	private void setElevatorTrim(float newElevatorTrim) {
		if (getStateBean().getElevatorTrim() != newElevatorTrim) {
			getState().elt = newElevatorTrim;
			getStateBean().setElevatorTrim(newElevatorTrim);
		}
	}

	private void setAileronTrim(float newAileronTrim) {
		if (getStateBean().getAileronTrim() != newAileronTrim) {
			getState().ailt = newAileronTrim;
			getStateBean().setAileronTrim(newAileronTrim);
		}
	}

	private void setPlaneConfiguration(Item simulatorItem) {
		int maxSpeedOnFlaps = (Integer) ((Property<?>) simulatorItem
				.getItemProperty(ColumnNames.getMaxspeedonflaps())).getValue();
		if (getStateBean().getMaxonflaps() != maxSpeedOnFlaps) {
			getState().maxonflaps = maxSpeedOnFlaps;
			getStateBean().setMaxonflaps(maxSpeedOnFlaps);
		}
		int numberOfLandingGears = (Integer) ((Property<?>) simulatorItem
				.getItemProperty(ColumnNames.getNumberoflandinggears()))
				.getValue();
		if (getStateBean().getNumOfLandG() != numberOfLandingGears) {
			getState().numoflandg = numberOfLandingGears;
			getStateBean().setNumOfLandG(numberOfLandingGears);
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
