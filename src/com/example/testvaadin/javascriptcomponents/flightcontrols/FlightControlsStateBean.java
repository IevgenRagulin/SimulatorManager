package com.example.testvaadin.javascriptcomponents.flightcontrols;

public class FlightControlsStateBean {
	public float ail;// aileron
	public float el; // elevator
	public float rd; // rudder
	public float sb; // speed brakes
	public float fl; // flaps

	public FlightControlsStateBean(float aileron, float elevator, float rudder,
			float speedbrakes, float flaps) {
		this.ail = aileron;
		this.el = elevator;
		this.rd = rudder;
		this.sb = speedbrakes;
		this.fl = flaps;
	}

	protected float getSpeedBrakes() {
		return sb;
	}

	protected void setSpeedBrakes(float speedbrakes) {
		this.sb = speedbrakes;
	}

	protected float getFlaps() {
		return fl;
	}

	protected void setFlaps(float flaps) {
		this.fl = flaps;
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
