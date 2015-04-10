package cz.vutbr.fit.simulatormanager.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * After getting data from simulator and parsing in, we put it to this object
 * 
 * Index in the array list corresponds to an engine number
 * 
 * @author zhenia
 *
 */
public class AllEngineInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = LoggerFactory.getLogger(AllEngineInfo.class);
    private final static int DEFAULT_NUM_OF_ENGINES = 1;
    Integer numberOfEngines = DEFAULT_NUM_OF_ENGINES;
    // rpm
    Map<Integer, Float> rpm = new HashMap<Integer, Float>();
    // pwr (watt)
    Map<Integer, Float> pwr = new HashMap<Integer, Float>();
    // pwr percent
    Map<Integer, Float> pwp = new HashMap<Integer, Float>();
    // manifold pressure
    Map<Integer, Float> mp_ = new HashMap<Integer, Float>();
    // egt1
    Map<Integer, Float> et1 = new HashMap<Integer, Float>();
    // egt2
    Map<Integer, Float> et2 = new HashMap<Integer, Float>();
    // cht1
    Map<Integer, Float> ct1 = new HashMap<Integer, Float>();
    // cht2
    Map<Integer, Float> ct2 = new HashMap<Integer, Float>();
    // suction temperature
    Map<Integer, Float> est = new HashMap<Integer, Float>();
    // fuel flow
    Map<Integer, Float> ff_ = new HashMap<Integer, Float>();
    // fuel pressure
    Map<Integer, Float> fp_ = new HashMap<Integer, Float>();
    // oil pressure
    Map<Integer, Float> op_ = new HashMap<Integer, Float>();
    // oil temperature
    Map<Integer, Float> ot_ = new HashMap<Integer, Float>();
    // n1 - jet engine
    Map<Integer, Float> n1_ = new HashMap<Integer, Float>();
    // n2 - jet engine
    Map<Integer, Float> n2_ = new HashMap<Integer, Float>();
    // vibration gauch - jet engine
    Map<Integer, Float> vib = new HashMap<Integer, Float>();
    // voltage. electric motor - system voltage.
    Map<Integer, Float> vlt = new HashMap<Integer, Float>();
    // current.
    Map<Integer, Float> amp = new HashMap<Integer, Float>();

    private boolean validateEngNum(int engNum) {
	// we have more or equals here because engine enumeration starts with 0
	if (engNum >= numberOfEngines) {
	    LOG.error("Engine number can't be<0 or >=number of engines. Engine number: {}", engNum);
	    return false;
	}
	return true;
    }

    public int getNumberOfEngines() {
	return numberOfEngines;
    }

    public void setNumberOfEngines(int numberOfEngines) {
	this.numberOfEngines = numberOfEngines;
    }

    public void setRpm(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    rpm.put(engNum, value);
	}
    }

    /**
     * Get RPM on engineNumber
     * 
     * @param engNum
     * @return
     */
    public Float getRpm(int engNum) {
	return rpm.get(engNum);
    }

    /**
     * Get RPMs for all active engines. If there are 2 engines, get RPM for 0th
     * and 1st engine
     * 
     * @return
     */
    public Float[] getRpm() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getRpm(i);
	}
	return values;
    }

    public void setPwr(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    pwr.put(engNum, value);
	}
    }

    public Float getPwr(int engNum) {
	return pwr.get(engNum);
    }

    public Float[] getPwr() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getPwr(i);
	}
	return values;
    }

    public void setPwp(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    pwp.put(engNum, value);
	}
    }

    public Float getPwp(int engNum) {
	return pwp.get(engNum);
    }

    public Float[] getPwp() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getPwp(i);
	}
	return values;
    }

    public void setMp_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    mp_.put(engNum, value);
	}
    }

    public Float getMp_(int engNum) {
	return mp_.get(engNum);
    }

    public Float[] getMp_() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getMp_(i);
	}
	return values;
    }

    public void setEt1(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    et1.put(engNum, value);
	}
    }

    public Float getEt1(int engNum) {
	return et1.get(engNum);
    }

    public Float[] getEt1() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getEt1(i);
	}
	return values;
    }

    public void setEt2(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    et2.put(engNum, value);
	}
    }

    public Float getEt2(int engNum) {
	return et2.get(engNum);
    }

    public Float[] getEt2() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getEt2(i);
	}
	return values;
    }

    public void setCt1(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    ct1.put(engNum, value);
	}
    }

    public Float getCt1(int engNum) {
	return ct1.get(engNum);
    }

    public Float[] getCt1() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getCt1(i);
	}
	return values;
    }

    public void setCt2(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    ct2.put(engNum, value);
	}
    }

    public Float getCt2(int engNum) {
	return ct2.get(engNum);
    }

    public Float[] getCt2() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getCt2(i);
	}
	return values;
    }

    public void setEst(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    est.put(engNum, value);
	}
    }

    public Float getEst(int engNum) {
	return est.get(engNum);
    }

    public Float[] getEst() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getEst(i);
	}
	return values;
    }

    public void setFf_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    ff_.put(engNum, value);
	}
    }

    public Float getFf_(int engNum) {
	return ff_.get(engNum);
    }

    public Float[] getFf_() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getFf_(i);
	}
	return values;
    }

    public void setFp_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    fp_.put(engNum, value);
	}
    }

    public Float getFp_(int engNum) {
	return fp_.get(engNum);
    }

    public Float[] getFp_() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getFp_(i);
	}
	return values;
    }

    public void setOp_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    op_.put(engNum, value);
	}
    }

    public Float getOp_(int engNum) {
	return op_.get(engNum);
    }

    public Float[] getOp_() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getOp_(i);
	}
	return values;
    }

    public void setOt_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    ot_.put(engNum, value);
	}
    }

    public Float getOt_(int engNum) {
	return ot_.get(engNum);
    }

    public Float[] getOt_() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getOt_(i);
	}
	return values;
    }

    public void setN1_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    n1_.put(engNum, value);
	}
    }

    public Float getN1_(int engNum) {
	return n1_.get(engNum);

    }

    public Float[] getN1_() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getN1_(i);
	}
	return values;
    }

    public void setN2_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    n2_.put(engNum, value);
	}
    }

    public Float getN2_(int engNum) {
	return n2_.get(engNum);

    }

    public Float[] getN2_() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getN2_(i);
	}
	return values;
    }

    public void setVib(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    vib.put(engNum, value);
	}
    }

    public Float getVib(int engNum) {
	return vib.get(engNum);
    }

    public Float[] getVib() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getVib(i);
	}
	return values;
    }

    public void setVlt(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    vlt.put(engNum, value);
	}
    }

    public Float getVlt(int engNum) {
	return vlt.get(engNum);
    }

    public Float[] getVlt() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getVlt(i);
	}
	return values;
    }

    public void setAmp(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    amp.put(engNum, value);
	}
    }

    public Float getAmp(int engNum) {
	return amp.get(engNum);
    }

    public Float[] getAmp() {
	Float[] values = new Float[numberOfEngines];
	for (int i = 0; i < numberOfEngines; i++) {
	    values[i] = getAmp(i);
	}
	return values;
    }

}
