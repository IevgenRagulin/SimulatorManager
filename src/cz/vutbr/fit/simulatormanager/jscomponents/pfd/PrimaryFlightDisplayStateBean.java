package cz.vutbr.fit.simulatormanager.jscomponents.pfd;

import java.io.Serializable;

public class PrimaryFlightDisplayStateBean implements Serializable {
	private static final long serialVersionUID = 570874937764548937L;

	private int a;// altitude
	private int s;// speed
	private int r;// roll
	private int p;// pitch
	private int h;// heading
	private int tc;// truecourse
	private float vs;// verticalspeed
	private int rpfd;// resetpfd

	public PrimaryFlightDisplayStateBean(int altitude, int speed, int roll,
			int pitch, int heading, int truecourse, float verticalspeed,
			int resetpfd) {
		super();
		this.a = altitude;
		this.s = speed;
		this.r = roll;
		this.p = pitch;
		this.h = heading;
		this.tc = truecourse;
		this.setVerticalspeed(verticalspeed);
		this.rpfd = resetpfd;
	}

	public float getAltitude() {
		return a;
	}

	public void setAltitude(int altitude) {
		this.a = altitude;
	}

	public int getSpeed() {
		return s;
	}

	public void setSpeed(int speed) {
		this.s = speed;
	}

	public int getRoll() {
		return r;
	}

	public void setRoll(int roll) {
		this.r = roll;
	}

	public int getPitch() {
		return p;
	}

	public void setPitch(int pitch) {
		this.p = pitch;
	}

	public int getHeading() {
		return h;
	}

	public void setHeading(int heading) {
		this.h = heading;
	}

	public int getTruecourse() {
		return tc;
	}

	public void setTruecourse(int truecourse) {
		this.tc = truecourse;
	}

	public int getResetpfd() {
		return rpfd;
	}

	public void setResetpfd(int resetpfd) {
		this.rpfd = resetpfd;
	}

	public float getVerticalspeed() {
		return vs;
	}

	public void setVerticalspeed(float verticalspeed) {
		this.vs = verticalspeed;
	}

}
