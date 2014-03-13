package com.example.testvaadin.beans;

import cm.example.testvaadin.simulatorcommunication.AllSimulationInfo;

public class SimulationInfoBean {
	private Double longtitude;
	private Double latitude;

	public Double getLongtitude() {
		return longtitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public SimulationInfoBean(Double longtitude, Double latitude) {
		super();
		this.longtitude = longtitude;
		this.latitude = latitude;
	}

	public SimulationInfoBean(AllSimulationInfo allSimInfo) {
		super();
		this.longtitude = allSimInfo.getLongitude();
		this.latitude = allSimInfo.getLatitude();
	}
}
