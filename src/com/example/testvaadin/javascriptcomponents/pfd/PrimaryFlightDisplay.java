package com.example.testvaadin.javascriptcomponents.pfd;

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

	public PrimaryFlightDisplay(final int resetpfd, final String xhtml,
			final int altitude, final int speed, final int roll,
			final int pitch, final int heading, final int truecourse,
			final float verticalspeed) {
		getState().resetpfd = resetpfd;
		getState().xhtml = xhtml;
		getState().altitude = altitude;
		getState().speed = speed;
		getState().roll = roll;
		getState().pitch = pitch;
		getState().heading = heading;
		getState().truecourse = truecourse;
		pfdStateBean = new PrimaryFlightDisplayStateBean(xhtml, altitude,
				speed, roll, pitch, heading, truecourse, verticalspeed,
				resetpfd);
		System.out.println("CALLED PFD CONSTRUCTOR");
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

	public float doubleToFloat(Double value) {
		return value.floatValue();
	}

	public void resetPfd() {
		getStateBean().setResetpfd(1);
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
		float newAltitude = doubleToFloat((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getAltitude())).getValue());
		float newGroundAltitude = doubleToFloat((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getGroundaltitude())).getValue());
		float newVertSpeed = doubleToFloat((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getVerticalspeed())).getValue());

		setRoll(newRoll);
		setPitch(newPitch);
		setHeading(newHeading);
		setTrueCourse(newTrueCourse);
		setSpeed(newIAS);// IAS - indicated airspeed
		setAltitude(newAltitude);
		setVerticalSpeed(newVertSpeed);
		getStateBean().setResetpfd(0);
		getState().resetpfd = 0;
	}

	private void setVerticalSpeed(float newVertSpeed) {
		if (getStateBean().getVerticalspeed() != newVertSpeed) {
			getState().verticalspeed = newVertSpeed;
			getStateBean().setVerticalspeed(newVertSpeed);
		}
	}

	private void setTrueCourse(int newTrueCourse) {
		if (getStateBean().getTruecourse() != newTrueCourse) {
			getState().truecourse = newTrueCourse;
			getStateBean().setTruecourse(newTrueCourse);
		}
	}

	public void setRoll(final int roll) {
		if (getStateBean().getRoll() != roll) {
			getState().roll = roll;
			getStateBean().setRoll(roll);
		}
	}

	public void setPitch(final int pitch) {
		if (getStateBean().getPitch() != pitch) {
			getState().pitch = pitch;
			getStateBean().setPitch(pitch);
		}
	}

	public void setHeading(final int heading) {
		if (getStateBean().getHeading() != heading) {
			getState().heading = heading;
			getStateBean().setHeading(heading);
		}
	}

	public void setAltitude(final float altitude) {
		if (getStateBean().getAltitude() != altitude) {
			getState().altitude = altitude;
			getStateBean().setAltitude(altitude);
		}
	}

	public void setSpeed(final int speed) {
		if (getStateBean().getSpeed() != speed) {
			getState().speed = speed;
			getStateBean().setSpeed(speed);
		}
	}

}