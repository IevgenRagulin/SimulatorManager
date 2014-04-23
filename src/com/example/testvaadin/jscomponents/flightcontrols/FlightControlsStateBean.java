package com.example.testvaadin.jscomponents.flightcontrols;

import java.io.Serializable;

public class FlightControlsStateBean implements Serializable {
	private static final long serialVersionUID = -1663050188807660753L;
	/* Plane configuration */
	private int maxonflaps;// mas speed on flaps
	/* Flight controls status */
	private float ail;// aileron
	private float el; // elevator
	private float rd; // rudder
	private float ailt;// aileron trim
	private float elt; // elevator trim
	private float rdt; // rudder trim
	private float sb; // speed brakes
	private boolean b; // brakes
	private float fl; // flaps
	private boolean p;// simulation paused

	public FlightControlsStateBean(float aileron, float elevator, float rudder,
			float aileront, float elevatort, float ruddert, float speedbrakes,
			float flaps, boolean brakes, boolean paused) {
		this.ail = aileron;
		this.el = elevator;
		this.rd = rudder;
		this.ailt = aileront;
		this.elt = elevatort;
		this.rdt = ruddert;
		this.sb = speedbrakes;
		this.fl = flaps;
		this.b = brakes;
		this.p = paused;
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

	protected boolean getBrakes() {
		return b;
	}

	protected void setBrakes(boolean brakes) {
		this.b = brakes;
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

	public boolean isPaused() {
		return p;
	}

	public void setPaused(boolean p) {
		this.p = p;
	}

}
