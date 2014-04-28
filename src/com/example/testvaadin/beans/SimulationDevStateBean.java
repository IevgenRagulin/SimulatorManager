package com.example.testvaadin.beans;

import java.io.Serializable;

import com.example.testvaadin.simulatorcommunication.AllSimulationInfo;

public class SimulationDevStateBean implements Serializable {
	private static final long serialVersionUID = -7396039034348717995L;
	private Double elevator;
	private Double aileron;
	private Double rudder;
	private Double throttle;
	private Double flaps;
	private Double speedbrakes;
	private Boolean brakes;

	private Double ailerontrim;
	private Double elevatortrim;
	private Double ruddertrim;
	private Boolean issimulationpaused;// is simulation paused. we store it both
										// in simulation and simulationdevstate
										// tables
	private Integer landinggear_1;
	private Integer landinggear_2;
	private Integer landinggear_3;

	public SimulationDevStateBean(double elevator, double aileron,
			double rudder, double throttle, double flaps, double speedbrakes,
			double trimAileron, double trimElevator, double trimRudder,
			boolean brakes, boolean paused, int landinggear_1,
			int landinggear_2, int landinggear_3) {
		super();
		this.elevator = elevator;
		this.aileron = aileron;
		this.rudder = rudder;
		this.throttle = throttle;
		this.flaps = flaps;
		this.speedbrakes = speedbrakes;
		this.ailerontrim = trimAileron;
		this.elevatortrim = trimElevator;
		this.ruddertrim = trimRudder;
		this.brakes = brakes;
		this.issimulationpaused = paused;
		this.landinggear_1 = landinggear_1;
		this.landinggear_2 = landinggear_2;
		this.landinggear_3 = landinggear_3;
	}

	public SimulationDevStateBean(AllSimulationInfo allSimInfo) {
		super();
		if (allSimInfo == null)
			throw new NullPointerException();
		this.elevator = allSimInfo.getElevator();
		this.aileron = allSimInfo.getAileron();
		this.rudder = allSimInfo.getRudder();
		this.throttle = allSimInfo.getThrottle();
		this.flaps = allSimInfo.getFlaps_status();
		this.speedbrakes = allSimInfo.getSpeed_brakes();
		this.ailerontrim = allSimInfo.getTrimAileronPosition();
		this.elevatortrim = allSimInfo.getTrimElevatorPosition();
		this.ruddertrim = allSimInfo.getTrimRudderPosition();
		this.brakes = allSimInfo.getBrakes_status();
		this.issimulationpaused = allSimInfo.getSimulationPaused();
		this.landinggear_1 = allSimInfo.getLanding_gear_1_status();
		this.landinggear_2 = allSimInfo.getLanding_gear_2_status();
		this.landinggear_3 = allSimInfo.getLanding_gear_3_status();
	}

	public Double getElevator() {
		return elevator;
	}

	public Double getEleron() {
		return aileron;
	}

	public Double getRudder() {
		return rudder;
	}

	public Double getThrottle() {
		return throttle;
	}

	public Double getFlaps() {
		return flaps;
	}

	public Double getSpeedbrakes() {
		return speedbrakes;
	}

	public Double getAilerontrim() {
		return ailerontrim;
	}

	public Double getElevatortrim() {
		return elevatortrim;
	}

	public Double getRuddertrim() {
		return ruddertrim;
	}

	public Boolean getBrakes() {
		return brakes;
	}

	public Boolean getIssimulationpaused() {
		return issimulationpaused;
	}

	public void setIssimulationpaused(Boolean paused) {
		this.issimulationpaused = paused;
	}

	public Integer getLandinggear_1() {
		return landinggear_1;
	}

	public Integer getLandinggear_2() {
		return landinggear_2;
	}

	public Integer getLandinggear_3() {
		return landinggear_3;
	}

}
