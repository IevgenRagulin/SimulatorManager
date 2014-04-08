package com.example.testvaadin.jscomponents.flightcontrols;

import java.io.Serializable;

public class FlightControlsStateBean implements Serializable {
	private static final long serialVersionUID = -1663050188807660753L;
	/* Plane configuration */
	public int maxonflaps;// mas speed on flaps
	/* Flight controls status */
	public float ail;// aileron
	public float el; // elevator
	public float rd; // rudder
	public float ailt;// aileron trim
	public float elt; // elevator trim
	public float rdt; // rudder trim
	public float sb; // speed brakes
	public float fl; // flaps

	public FlightControlsStateBean(float aileron, float elevator, float rudder,
			float aileront, float elevatort, float ruddert, float speedbrakes,
			float flaps) {
		this.ail = aileron;
		this.el = elevator;
		this.rd = rudder;
		this.ailt = aileront;
		this.elt = elevatort;
		this.rdt = ruddert;
		this.sb = speedbrakes;
		this.fl = flaps;
	}

	protected int getMaxonflaps() {
		return maxonflaps;
	}

	protected float getAileronTrim() {
		return ailt;
	}

	protected void setAileronTrim(float ailt) {
		this.ailt = ailt;
	}

	protected float getElevatorTrim() {
		return elt;
	}

	protected void setElevatorTrim(float elt) {
		this.elt = elt;
	}

	protected float getRudderTrim() {
		return rdt;
	}

	protected void setRudderTrim(float rdt) {
		this.rdt = rdt;
	}

	protected void setMaxonflaps(int maxonflaps) {
		this.maxonflaps = maxonflaps;
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
