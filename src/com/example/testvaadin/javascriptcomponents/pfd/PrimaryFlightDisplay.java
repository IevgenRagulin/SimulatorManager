package com.example.testvaadin.javascriptcomponents.pfd;

import com.example.testvaadin.data.ColumnNames;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

@SuppressWarnings("serial")
@com.vaadin.annotations.JavaScript({ "primaryFlightDisplay.js" })
public class PrimaryFlightDisplay extends
		com.vaadin.ui.AbstractJavaScriptComponent {

	private final String CSS_CLASS = "PFD_DISPLAY";

	/*
	 * We use this bean in adition to state class, because when we access state
	 * class fields, the change state event is generated even if we don't change
	 * the state. So, we use this bean to imporove performance
	 */
	private PrimaryFlightDisplayStateBean pfdStateBean;

	public PrimaryFlightDisplay(final int resetpfd, final int altitude,
			final int speed, final int roll, final int pitch,
			final int heading, final int truecourse, final float verticalspeed) {
		getState().rpfd = resetpfd;
		getState().a = altitude;
		getState().s = speed;
		getState().r = roll;
		getState().p = pitch;
		getState().h = heading;
		getState().tc = truecourse;
		pfdStateBean = new PrimaryFlightDisplayStateBean(altitude, speed, roll,
				pitch, heading, truecourse, verticalspeed, resetpfd);
		setPrimaryStyleName(CSS_CLASS);
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

	public float doubleToRoundedFloat(Double value) {
		return (float) (Math.round(value.floatValue() * 100.0) / 100.0);
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
		int newAltitude = doubleToInt((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getAltitude())).getValue());
		float newGroundAltitude = doubleToRoundedFloat((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getGroundaltitude())).getValue());
		float newVertSpeed = doubleToRoundedFloat((Double) ((Property<?>) item
				.getItemProperty(ColumnNames.getVerticalspeed())).getValue());

		setNewValues(newRoll, newPitch, newHeading, newTrueCourse, newIAS,
				newAltitude, newVertSpeed);
		// getState().rpfd = 0;
	}

	/*
	 * If any data has changed, set all data to state bean.
	 */
	private void setNewValues(int newRoll, int newPitch, int newHeading,
			int newTrueCourse, int newIas, int newAltitude, float newVertSpeed) {
		if ((getStateBean().getVerticalspeed() != newVertSpeed)
				|| (getStateBean().getTruecourse() != newTrueCourse)
				|| (getStateBean().getRoll() != newRoll)
				|| (getStateBean().getPitch() != newPitch)
				|| (getStateBean().getHeading() != newHeading)
				|| (getStateBean().getAltitude() != newAltitude)
				|| (getStateBean().getSpeed() != newIas)) {
			setVerticalSpeed(newVertSpeed);
			setTrueCourse(newTrueCourse);
			setRoll(newRoll);
			setPitch(newPitch);
			setHeading(newHeading);
			setAltitude(newAltitude);
			setSpeed(newIas);
		}
	}

	private void setVerticalSpeed(float newVertSpeed) {
		getState().vs = newVertSpeed;
		getStateBean().setVerticalspeed(newVertSpeed);
	}

	private void setTrueCourse(int newTrueCourse) {
		getState().tc = newTrueCourse;
		getStateBean().setTruecourse(newTrueCourse);
	}

	public void setRoll(final int roll) {
		getState().r = roll;
		getStateBean().setRoll(roll);
	}

	public void setPitch(final int pitch) {
		getState().p = pitch;
		getStateBean().setPitch(pitch);
	}

	public void setHeading(final int heading) {
		getState().h = heading;
		getStateBean().setHeading(heading);
	}

	public void setAltitude(final int altitude) {
		getState().a = altitude;
		getStateBean().setAltitude(altitude);
	}

	public void setSpeed(final int speed) {
		getState().s = speed;
		getStateBean().setSpeed(speed);
	}

}
