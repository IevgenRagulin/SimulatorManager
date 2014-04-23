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
										// in

	// simulation and simulationdevstate tables

	public SimulationDevStateBean(double elevator, double aileron,
			double rudder, double throttle, double flaps, double speedbrakes,
			double trimAileron, double trimElevator, double trimRudder,
			boolean brakes, boolean paused) {
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

}
