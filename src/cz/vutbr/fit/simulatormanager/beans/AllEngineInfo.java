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
	if (validateEngNum(engNum)) {
	    rpm.get(engNum);
	}
	return null;
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
	    values[i] = rpm.get(getRpm(i));
	}
	return values;
    }

    public void setPwr(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    pwr.put(engNum, value);
	}
    }

    public Float getPwr(int engNum) {
	if (validateEngNum(engNum)) {
	    pwr.get(engNum);
	}
	return null;
    }

    public void setPwp(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    pwp.put(engNum, value);
	}
    }

    public Float getPwp(int engNum) {
	if (validateEngNum(engNum)) {
	    pwp.get(engNum);
	}
	return null;
    }

    public void setMp_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    mp_.put(engNum, value);
	}
    }

    public Float getMp_(int engNum) {
	if (validateEngNum(engNum)) {
	    mp_.get(engNum);
	}
	return null;
    }

    public void setEt1(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    et1.put(engNum, value);
	}
    }

    public Float getEt1(int engNum) {
	if (validateEngNum(engNum)) {
	    et1.get(engNum);
	}
	return null;
    }

    public void setEt2(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    et2.put(engNum, value);
	}
    }

    public Float getEt2(int engNum) {
	if (validateEngNum(engNum)) {
	    et2.get(engNum);
	}
	return null;
    }

    public void setCt1(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    ct1.put(engNum, value);
	}
    }

    public Float getCt1(int engNum) {
	if (validateEngNum(engNum)) {
	    ct1.get(engNum);
	}
	return null;
    }

    public void setCt2(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    ct2.put(engNum, value);
	}
    }

    public Float getCt2(int engNum) {
	if (validateEngNum(engNum)) {
	    ct2.get(engNum);
	}
	return null;
    }

    public void setEst(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    est.put(engNum, value);
	}
    }

    public Float getEst(int engNum) {
	if (validateEngNum(engNum)) {
	    est.get(engNum);
	}
	return null;
    }

    public void setFf_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    ff_.put(engNum, value);
	}
    }

    public Float getFf_(int engNum) {
	if (validateEngNum(engNum)) {
	    ff_.get(engNum);
	}
	return null;
    }

    public void setFp_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    fp_.put(engNum, value);
	}
    }

    public Float getFp_(int engNum) {
	if (validateEngNum(engNum)) {
	    fp_.get(engNum);
	}
	return null;
    }

    public void setOp_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    op_.put(engNum, value);
	}
    }

    public Float getOp_(int engNum) {
	if (validateEngNum(engNum)) {
	    op_.get(engNum);
	}
	return null;
    }

    public void setOt_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    ot_.put(engNum, value);
	}
    }

    public Float getOt_(int engNum) {
	if (validateEngNum(engNum)) {
	    ot_.get(engNum);
	}
	return null;
    }

    public void setN1_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    n1_.put(engNum, value);
	}
    }

    public Float getN1_(int engNum) {
	if (validateEngNum(engNum)) {
	    n1_.get(engNum);
	}
	return null;
    }

    public void setN2_(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    n2_.put(engNum, value);
	}
    }

    public Float getN2_(int engNum) {
	if (validateEngNum(engNum)) {
	    n2_.get(engNum);
	}
	return null;
    }

    public void setVib(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    vib.put(engNum, value);
	}
    }

    public Float getVib(int engNum) {
	if (validateEngNum(engNum)) {
	    vib.get(engNum);
	}
	return null;
    }

    public void setVlt(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    vlt.put(engNum, value);
	}
    }

    public Float getVlt(int engNum) {
	if (validateEngNum(engNum)) {
	    vlt.get(engNum);
	}
	return null;
    }

    public void setAmp(int engNum, Float value) {
	if (validateEngNum(engNum)) {
	    amp.put(engNum, value);
	}
    }

    public Float getAmp(int engNum) {
	if (validateEngNum(engNum)) {
	    amp.get(engNum);
	}
	return null;
    }

}
