package com.example.testvaadin.beans;

import java.io.Serializable;

import cm.example.testvaadin.simulatorcommunication.AllSimulationInfo;

public class SimulationPFDBean implements Serializable {

	private static final long serialVersionUID = -5185271892922972352L;
	private Double roll;
	private Double pitch;
	private Double yaw;
	private Double ias;
	private Double altitude;
	private Double groundaltitude;
	private Double verticalspeed;
	private Double heading;
	private Double truecourse;

	public SimulationPFDBean(AllSimulationInfo allSimInfo) {
		super();
		this.roll = allSimInfo.getBank();
		this.pitch = allSimInfo.getPitch();
		this.ias = allSimInfo.getIAS();
		this.altitude = allSimInfo.getAltitude_standard();
		this.groundaltitude = allSimInfo.getGroundAltitude();
		this.verticalspeed = allSimInfo.getVS();
		this.heading = allSimInfo.getHeading();
		this.truecourse = allSimInfo.getTT();
	}

	public SimulationPFDBean(Double roll, Double pitch, Double yaw, Double ias,
			Double altitude, Double groundAltitude, Double verticalSpeed,
			Double heading, Double truecourse) {
		super();
		this.roll = roll;
		this.pitch = pitch;
		this.yaw = yaw;
		this.ias = ias;
		this.altitude = altitude;
		this.groundaltitude = groundAltitude;
		this.verticalspeed = verticalSpeed;
		this.heading = heading;
		this.truecourse = truecourse;
	}

	public Double getRoll() {
		return roll;
	}

	public Double getPitch() {
		return pitch;
	}

	public Double getYaw() {
		return yaw;
	}

	public Double getIas() {
		return ias;
	}

	public Double getAltitude() {
		return altitude;
	}

	public Double getGroundaltitude() {
		return groundaltitude;
	}

	public Double getVerticalspeed() {
		return verticalspeed;
	}

	public Double getHeading() {
		return heading;
	}

	public Double getTruecourse() {
		return truecourse;
	}

	public void setTruecourse(Double truecourse) {
		this.truecourse = truecourse;
	}

}
