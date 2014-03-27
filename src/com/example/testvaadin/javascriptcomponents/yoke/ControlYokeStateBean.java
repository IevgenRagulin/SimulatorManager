package com.example.testvaadin.javascriptcomponents.yoke;

public class ControlYokeStateBean {
	public float aileron;
	public float elevator;
	public float rudder;

	public ControlYokeStateBean(float aileron, float elevator, float rudder) {
		this.aileron = aileron;
		this.elevator = elevator;
		this.rudder = rudder;
	}

	protected float getAileron() {
		return aileron;
	}

	protected void setAileron(float aileron) {
		this.aileron = aileron;
	}

	protected float getElevator() {
		return elevator;
	}

	protected void setElevator(float elevator) {
		this.elevator = elevator;
	}

	protected float getRudder() {
		return rudder;
	}

	protected void setRudder(float rudder) {
		this.rudder = rudder;
	}

}
