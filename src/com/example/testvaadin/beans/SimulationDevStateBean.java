package com.example.testvaadin.beans;

public class SimulationDevStateBean {
	private double elevator;
	private double eleron;
	private int rudder;
	private double throttle;
	private int flaps;
	private boolean speedbrakes;
	private double trim;

	public SimulationDevStateBean(double elevator, double eleron, int rudder,
			double throttle, int flaps, boolean speedbrakes, double trim) {
		super();
		this.elevator = elevator;
		this.eleron = eleron;
		this.rudder = rudder;
		this.throttle = throttle;
		this.flaps = flaps;
		this.speedbrakes = speedbrakes;
		this.trim = trim;
	}

	public double getElevator() {
		return elevator;
	}

	public double getEleron() {
		return eleron;
	}

	public int getRudder() {
		return rudder;
	}

	public double getThrottle() {
		return throttle;
	}

	public int getFlaps() {
		return flaps;
	}

	public boolean isSpeedbrakes() {
		return speedbrakes;
	}

	public double getTrim() {
		return trim;
	}

}
