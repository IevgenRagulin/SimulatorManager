package com.example.testvaadin.javascriptcomponents;

import java.math.BigDecimal;

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
			final int speed, final int roll, final int pitch, final int yaw,
			final int compass) {
		getState().xhtml = xhtml;
		getState().altitude = altitude;
		getState().speed = speed;
		getState().roll = roll;
		getState().pitch = pitch;
		getState().yaw = yaw;
		getState().compass = compass;
		pfdStateBean = new PrimaryFlightDisplayStateBean(xhtml, altitude,
				speed, roll, pitch, yaw, compass);
		// JsLabelState state = getState();

	}

	@Override
	public PrimaryFlightDisplayState getState() {
		return (PrimaryFlightDisplayState) super.getState();
	}

	public PrimaryFlightDisplayStateBean getStateBean() {
		return pfdStateBean;
	}

	public int bigDecimalToInt(BigDecimal value) {
		return value.intValue();
	}

	public void updateIndividualPFDValues(Item item) {
		int newRoll = bigDecimalToInt((BigDecimal) ((Property<?>) item
				.getItemProperty("Roll")).getValue());
		int newPitch = bigDecimalToInt((BigDecimal) ((Property<?>) item
				.getItemProperty("Pitch")).getValue());
		int newYaw = bigDecimalToInt((BigDecimal) ((Property<?>) item
				.getItemProperty("Yaw")).getValue());
		int newIAS = bigDecimalToInt((BigDecimal) ((Property<?>) item
				.getItemProperty("IAS")).getValue());
		int newAltitude = bigDecimalToInt((BigDecimal) ((Property<?>) item
				.getItemProperty("Altitude")).getValue());
		int newGroundAltitude = bigDecimalToInt((BigDecimal) ((Property<?>) item
				.getItemProperty("GroundAltitude")).getValue());
		int newVerticalSpeed = bigDecimalToInt((BigDecimal) ((Property<?>) item
				.getItemProperty("VerticalSpeed")).getValue());
		int newCompass = bigDecimalToInt((BigDecimal) ((Property<?>) item
				.getItemProperty("Compass")).getValue());
		setRoll(newRoll);
		setPitch(newPitch);
		setYaw(newYaw);
		setSpeed(newIAS);// IAS - indicated airspeed
		setAltitude(newAltitude);
		setCompass(newCompass);

	}

	private void setCompass(int newCompass) {
		if (getStateBean().getCompass() != newCompass) {
			getState().compass = newCompass;
			getStateBean().setCompass(newCompass);
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

	public void setYaw(final int yaw) {
		if (getStateBean().getYaw() != yaw) {
			getState().yaw = yaw;
			getStateBean().setYaw(yaw);
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
