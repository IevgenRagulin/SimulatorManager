package com.example.testvaadin.javascriptcomponents;

import com.vaadin.shared.ui.JavaScriptComponentState;

@SuppressWarnings("serial")
public class PrimaryFlightDisplayStateBean extends JavaScriptComponentState {
	private String xhtml;
	private int altitude;
	private int speed;
	private int roll;
	private int pitch;
	private int yaw;
	private int compass;

	public PrimaryFlightDisplayStateBean(String xhtml, int altitude, int speed,
			int roll, int pitch, int yaw, int compass) {
		super();
		this.xhtml = xhtml;
		this.altitude = altitude;
		this.speed = speed;
		this.roll = roll;
		this.pitch = pitch;
		this.yaw = yaw;
		this.compass = compass;
	}

	public String getXhtml() {
		return xhtml;
	}

	public void setXhtml(String xhtml) {
		this.xhtml = xhtml;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getRoll() {
		return roll;
	}

	public void setRoll(int roll) {
		this.roll = roll;
	}

	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}

	public int getYaw() {
		return yaw;
	}

	public void setYaw(int yaw) {
		this.yaw = yaw;
	}

	public int getCompass() {
		return compass;
	}

	public void setCompass(int compass) {
		this.compass = compass;
	}

}
