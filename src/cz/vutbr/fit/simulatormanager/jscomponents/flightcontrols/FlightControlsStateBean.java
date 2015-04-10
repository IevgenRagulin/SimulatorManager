package cz.vutbr.fit.simulatormanager.jscomponents.flightcontrols;

import java.io.Serializable;

public class FlightControlsStateBean implements Serializable {
    private static final long serialVersionUID = -1663050188807660753L;
    /* Plane configuration */
    private int maxonflaps;// mas speed on flaps
    private int numOfLandG;// number of landing gears
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
    public int landg_1;// landing gear 1
    public int landg_2;// landing gear 1
    public int landg_3;// landing gear 1
    private int[] test = new int[] { 1, 2, 3 };

    public FlightControlsStateBean(float aileron, float elevator, float rudder, float aileront, float elevatort,
	    float ruddert, float speedbrakes, float flaps, boolean brakes, boolean paused, int numOfLandG, int landg_1,
	    int landg_2, int landg_3) {
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
	this.numOfLandG = numOfLandG;
	this.landg_1 = landg_1;
	this.landg_2 = landg_2;
	this.landg_3 = landg_3;
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

    public int getNumOfLandG() {
	return numOfLandG;
    }

    protected void setNumOfLandG(int numOfLandG) {
	this.numOfLandG = numOfLandG;
    }

    protected int getLandg_1() {
	return landg_1;
    }

    protected void setLandg_1(int landg_1) {
	this.landg_1 = landg_1;
    }

    protected int getLandg_2() {
	return landg_2;
    }

    protected void setLandg_2(int landg_2) {
	this.landg_2 = landg_2;
    }

    protected int getLandg_3() {
	return landg_3;
    }

    protected void setLandg_3(int landg_3) {
	this.landg_3 = landg_3;
    }

    public int[] getTest() {
	return test;
    }

    public void setTest(int[] test) {
	this.test = test;
    }

}
