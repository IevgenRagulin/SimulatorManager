package com.example.testvaadin.javascriptcomponents.yoke;

public class ControlYokeStateBean {
	public float ail;
	public float el;
	public float rd;

	public ControlYokeStateBean(float aileron, float elevator, float rudder) {
		this.ail = aileron;
		this.el = elevator;
		this.rd = rudder;
	}

	protected float getAileron() {
		return ail;
	}

	protected void setAileron(float aileron) {
		this.ail = aileron;
	}

	protected float getElevator() {
		return el;
	}

	protected void setElevator(float elevator) {
		this.el = elevator;
	}

	protected float getRudder() {
		return rd;
	}

	protected void setRudder(float rudder) {
		this.rd = rudder;
	}

}
