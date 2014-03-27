package com.example.testvaadin.javascriptcomponents.pfd;

public class PrimaryFlightDisplayStateBean {
	private String xhtml;
	private float altitude;
	private int speed;
	private int roll;
	private int pitch;
	private int heading;
	private int truecourse;
	private float verticalspeed;
	private int resetpfd;

	public PrimaryFlightDisplayStateBean(String xhtml, float altitude,
			int speed, int roll, int pitch, int heading, int truecourse,
			float verticalspeed, int resetpfd) {
		super();
		this.xhtml = xhtml;
		this.altitude = altitude;
		this.speed = speed;
		this.roll = roll;
		this.pitch = pitch;
		this.heading = heading;
		this.truecourse = truecourse;
		this.setVerticalspeed(verticalspeed);
		this.resetpfd = resetpfd;
	}

	public String getXhtml() {
		return xhtml;
	}

	public void setXhtml(String xhtml) {
		this.xhtml = xhtml;
	}

	public float getAltitude() {
		return altitude;
	}

	public void setAltitude(float altitude) {
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

	public int getHeading() {
		return heading;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public int getTruecourse() {
		return truecourse;
	}

	public void setTruecourse(int truecourse) {
		this.truecourse = truecourse;
	}

	public int getResetpfd() {
		return resetpfd;
	}

	public void setResetpfd(int resetpfd) {
		this.resetpfd = resetpfd;
	}

	public float getVerticalspeed() {
		return verticalspeed;
	}

	public void setVerticalspeed(float verticalspeed) {
		this.verticalspeed = verticalspeed;
	}

}
