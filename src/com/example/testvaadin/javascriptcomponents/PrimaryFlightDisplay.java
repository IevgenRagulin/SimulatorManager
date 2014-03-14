package com.example.testvaadin.javascriptcomponents;

import com.example.testvaadin.data.ColumnNames;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

@SuppressWarnings("serial")
@com.vaadin.annotations.JavaScript({ "primaryFlightDisplay.js" })
public class PrimaryFlightDisplay extends
		com.vaadin.ui.AbstractJavaScriptComponent {

	/*
	 * We use this bean in adition to state class, because when we access state
	 * class fields, the change state event is generated even if we don't change
	 * the state. So, we use this bean to imporove performance
	 */
	private PrimaryFlightDisplayStateBean pfdStateBean;

	public PrimaryFlightDisplay(final String xhtml, final int altitude,
			final int speed, final int roll, final int pitch,
			final int heading, final int truecourse) {
		getState().resetpfd = 1;
		getState().xhtml = xhtml;
		getState().altitude = altitude;
		getState().speed = speed;
		getState().roll = roll;
		getState().pitch = pitch;
		getState().heading = heading;
		getState().truecourse = truecourse;
		pfdStateBean = new PrimaryFlightDisplayStateBean(xhtml, altitude,
				speed, roll, pitch, heading, truecourse);
		// JsLabelState state = getState();

	}

	@Override
	public PrimaryFlightDisplayState getState() {
		return (PrimaryFlightDisplayState) super.getState();
	}

	public PrimaryFlightDisplayStateBean getStateBean() {
		return pfdStateBean;
	}

	public int doubleToInt(Double value) {
		return value.intValue();
	}

	public void resetPfd() {
		getStateBean().setResetpfd(true);
	}

	public void updateIndividualPFDValues(Item item) {
		int newRoll = doubleToInt((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getRoll())).getValue());
		int newPitch = doubleToInt((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getPitch())).getValue());
		int newHeading = doubleToInt((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getHeading())).getValue());
		int newTrueCourse = doubleToInt((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getTrueCourse())).getValue());
		int newIAS = doubleToInt((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getIas())).getValue());
		int newAltitude = doubleToInt((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getAltitude())).getValue());
		int newGroundAltitude = doubleToInt((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getGroundaltitude())).getValue());
		int newVerticalSpeed = doubleToInt((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getVerticalspeed())).getValue());

		setRoll(newRoll);
		setPitch(newPitch);
		setHeading(newHeading);
		setTrueCourse(newTrueCourse);
		setSpeed(newIAS);// IAS - indicated airspeed
		setAltitude(newAltitude);

	}

	private void setTrueCourse(int newTrueCourse) {
		if (getStateBean().getTruecourse() != newTrueCourse) {
			getState().truecourse = newTrueCourse;
			getStateBean().setTruecourse(newTrueCourse);
		} else {
		}
	}

	public void setRoll(final int roll) {
		if (getStateBean().getRoll() != roll) {
			getState().roll = roll;
			getStateBean().setRoll(roll);
		} else {
		}
	}

	public void setPitch(final int pitch) {
		if (getStateBean().getPitch() != pitch) {
			getState().pitch = pitch;
			getStateBean().setPitch(pitch);
		} else {
		}
	}

	public void setHeading(final int heading) {
		if (getStateBean().getHeading() != heading) {
			getState().heading = heading;
			getStateBean().setHeading(heading);
		} else {
		}
	}

	public void setAltitude(final int altitude) {
		if (getStateBean().getAltitude() != altitude) {
			getState().altitude = altitude;
			getStateBean().setAltitude(altitude);
		} else {
		}
	}

	public void setSpeed(final int speed) {
		if (getStateBean().getSpeed() != speed) {
			getState().speed = speed;
			getStateBean().setSpeed(speed);
		} else {
		}
	}

}
