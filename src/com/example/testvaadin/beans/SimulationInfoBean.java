package com.example.testvaadin.beans;

import java.io.Serializable;

public class SimulationInfoBean implements Serializable {

	private static final long serialVersionUID = 3769922290842743952L;
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
