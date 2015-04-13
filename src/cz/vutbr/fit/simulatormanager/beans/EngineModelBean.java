package cz.vutbr.fit.simulatormanager.beans;

import com.vaadin.data.Item;

import cz.vutbr.fit.simulatormanager.database.columns.EngineModelCols;

public class EngineModelBean {

    private int enginemodelorder;

    private boolean rpm;
    // we use Float instead of float to allow null values
    private Float minrpm;
    private Float maxrpm;

    private boolean pwr;
    private Float minpwr;
    private Float maxpwr;

    private boolean pwp;
    private Float minpwp;
    private Float maxpwp;

    private boolean mp_;
    private Float minmp_;
    private Float maxmp_;

    private boolean egt1;
    private Float minegt1;
    private Float maxegt1;

    private boolean egt2;
    private Float minegt2;
    private Float maxegt2;

    private boolean cht1;
    private Float mincht1;
    private Float maxcht1;

    private boolean cht2;
    private Float mincht2;
    private Float maxcht2;

    private boolean est;
    private Float minest;
    private Float maxest;

    private boolean ff_;
    private Float minff_;
    private Float maxff_;

    private boolean fp_;
    private Float minfp_;
    private Float maxfp_;

    private boolean op_;
    private Float minop_;
    private Float maxop_;

    private boolean ot_;
    private Float minot_;
    private Float maxot_;

    private boolean n1_;
    private Float minn1_;
    private Float maxn1_;

    private boolean n2_;
    private Float minn2_;
    private Float maxn2_;

    private boolean vib;
    private Float minvib;
    private Float maxvib;

    private boolean vlt;
    private Float minvlt;
    private Float maxvlt;

    private boolean amp;
    private Float minamp;
    private Float maxamp;

    public EngineModelBean(Item engineModelItem) {
	setEnginemodelorder((int) engineModelItem.getItemProperty(EngineModelCols.enginemodelorder.toString())
		.getValue());

	setRpm((boolean) engineModelItem.getItemProperty(EngineModelCols.rpm.toString()).getValue());
	setMinrpm((Float) engineModelItem.getItemProperty(EngineModelCols.minrpm.toString()).getValue());
	setMaxrpm((Float) engineModelItem.getItemProperty(EngineModelCols.maxrpm.toString()).getValue());

	setPwr((boolean) engineModelItem.getItemProperty(EngineModelCols.pwr.toString()).getValue());
	setMinpwr((Float) engineModelItem.getItemProperty(EngineModelCols.minpwr.toString()).getValue());
	setMaxpwr((Float) engineModelItem.getItemProperty(EngineModelCols.maxpwr.toString()).getValue());

	setPwp((boolean) engineModelItem.getItemProperty(EngineModelCols.pwp.toString()).getValue());
	setMinpwp((Float) engineModelItem.getItemProperty(EngineModelCols.minpwp.toString()).getValue());
	setMaxpwp((Float) engineModelItem.getItemProperty(EngineModelCols.maxpwp.toString()).getValue());

	setMp_((boolean) engineModelItem.getItemProperty(EngineModelCols.mp_.toString()).getValue());
	setMinmp_((Float) engineModelItem.getItemProperty(EngineModelCols.minmp.toString()).getValue());
	setMaxmp_((Float) engineModelItem.getItemProperty(EngineModelCols.maxmp.toString()).getValue());

	setEgt1((boolean) engineModelItem.getItemProperty(EngineModelCols.egt1.toString()).getValue());
	setMinegt1((Float) engineModelItem.getItemProperty(EngineModelCols.minegt1.toString()).getValue());
	setMaxegt1((Float) engineModelItem.getItemProperty(EngineModelCols.maxegt1.toString()).getValue());

	setEgt2((boolean) engineModelItem.getItemProperty(EngineModelCols.egt2.toString()).getValue());
	setMinegt2((Float) engineModelItem.getItemProperty(EngineModelCols.minegt2.toString()).getValue());
	setMaxegt2((Float) engineModelItem.getItemProperty(EngineModelCols.maxegt2.toString()).getValue());

	setCht1((boolean) engineModelItem.getItemProperty(EngineModelCols.cht1.toString()).getValue());
	setMincht1((Float) engineModelItem.getItemProperty(EngineModelCols.mincht1.toString()).getValue());
	setMaxcht1((Float) engineModelItem.getItemProperty(EngineModelCols.maxcht1.toString()).getValue());

	setCht2((boolean) engineModelItem.getItemProperty(EngineModelCols.cht2.toString()).getValue());
	setMincht2((Float) engineModelItem.getItemProperty(EngineModelCols.mincht2.toString()).getValue());
	setMaxcht2((Float) engineModelItem.getItemProperty(EngineModelCols.maxcht2.toString()).getValue());

	setEst((boolean) engineModelItem.getItemProperty(EngineModelCols.est.toString()).getValue());
	setMinest((Float) engineModelItem.getItemProperty(EngineModelCols.minest.toString()).getValue());
	setMaxest((Float) engineModelItem.getItemProperty(EngineModelCols.maxest.toString()).getValue());

	setFf_((boolean) engineModelItem.getItemProperty(EngineModelCols.ff_.toString()).getValue());
	setMinff_((Float) engineModelItem.getItemProperty(EngineModelCols.minff.toString()).getValue());
	setMaxff_((Float) engineModelItem.getItemProperty(EngineModelCols.maxff.toString()).getValue());

	setFp_((boolean) engineModelItem.getItemProperty(EngineModelCols.fp_.toString()).getValue());
	setMinfp_((Float) engineModelItem.getItemProperty(EngineModelCols.minfp.toString()).getValue());
	setMaxfp_((Float) engineModelItem.getItemProperty(EngineModelCols.maxfp.toString()).getValue());

	setOp_((boolean) engineModelItem.getItemProperty(EngineModelCols.op_.toString()).getValue());
	setMinop_((Float) engineModelItem.getItemProperty(EngineModelCols.minop.toString()).getValue());
	setMaxop_((Float) engineModelItem.getItemProperty(EngineModelCols.maxop.toString()).getValue());

	setOt_((boolean) engineModelItem.getItemProperty(EngineModelCols.ot_.toString()).getValue());
	setMinot_((Float) engineModelItem.getItemProperty(EngineModelCols.minot.toString()).getValue());
	setMaxot_((Float) engineModelItem.getItemProperty(EngineModelCols.maxot.toString()).getValue());

	setN1_((boolean) engineModelItem.getItemProperty(EngineModelCols.n1_.toString()).getValue());
	setMinn1_((Float) engineModelItem.getItemProperty(EngineModelCols.minn1.toString()).getValue());
	setMaxn1_((Float) engineModelItem.getItemProperty(EngineModelCols.maxn1.toString()).getValue());

	setN2_((boolean) engineModelItem.getItemProperty(EngineModelCols.n2_.toString()).getValue());
	setMinn2_((Float) engineModelItem.getItemProperty(EngineModelCols.minn2.toString()).getValue());
	setMaxn2_((Float) engineModelItem.getItemProperty(EngineModelCols.maxn2.toString()).getValue());

	setVib((boolean) engineModelItem.getItemProperty(EngineModelCols.vib.toString()).getValue());
	setMinvib((Float) engineModelItem.getItemProperty(EngineModelCols.minvib.toString()).getValue());
	setMaxvib((Float) engineModelItem.getItemProperty(EngineModelCols.maxvib.toString()).getValue());

	setVlt((boolean) engineModelItem.getItemProperty(EngineModelCols.vlt.toString()).getValue());
	setMinvlt((Float) engineModelItem.getItemProperty(EngineModelCols.minvlt.toString()).getValue());
	setMaxvlt((Float) engineModelItem.getItemProperty(EngineModelCols.maxvlt.toString()).getValue());

	setAmp((boolean) engineModelItem.getItemProperty(EngineModelCols.amp.toString()).getValue());
	setMinamp((Float) engineModelItem.getItemProperty(EngineModelCols.minamp.toString()).getValue());
	setMaxamp((Float) engineModelItem.getItemProperty(EngineModelCols.maxamp.toString()).getValue());
    }

    public EngineModelBean() {
    }

    public boolean isRpm() {
	return rpm;
    }

    public void setRpm(boolean rpm) {
	this.rpm = rpm;
    }

    public Float getMinrpm() {
	return minrpm;
    }

    public void setMinrpm(Float minrpm) {
	this.minrpm = minrpm;
    }

    public Float getMaxrpm() {
	return maxrpm;
    }

    public void setMaxrpm(Float maxrpm) {
	this.maxrpm = maxrpm;
    }

    public boolean isPwr() {
	return pwr;
    }

    public void setPwr(boolean pwr) {
	this.pwr = pwr;
    }

    public Float getMinpwr() {
	return minpwr;
    }

    public void setMinpwr(Float minpwr) {
	this.minpwr = minpwr;
    }

    public Float getMaxpwr() {
	return maxpwr;
    }

    public void setMaxpwr(Float maxpwr) {
	this.maxpwr = maxpwr;
    }

    public boolean isPwp() {
	return pwp;
    }

    public void setPwp(boolean pwp) {
	this.pwp = pwp;
    }

    public Float getMinpwp() {
	return minpwp;
    }

    public void setMinpwp(Float minpwp) {
	this.minpwp = minpwp;
    }

    public Float getMaxpwp() {
	return maxpwp;
    }

    public void setMaxpwp(Float maxpwp) {
	this.maxpwp = maxpwp;
    }

    public boolean isMp_() {
	return mp_;
    }

    public void setMp_(boolean mp_) {
	this.mp_ = mp_;
    }

    public Float getMinmp_() {
	return minmp_;
    }

    public void setMinmp_(Float minmp_) {
	this.minmp_ = minmp_;
    }

    public Float getMaxmp_() {
	return maxmp_;
    }

    public void setMaxmp_(Float maxmp_) {
	this.maxmp_ = maxmp_;
    }

    public boolean isEgt1() {
	return egt1;
    }

    public void setEgt1(boolean egt1) {
	this.egt1 = egt1;
    }

    public Float getMinegt1() {
	return minegt1;
    }

    public void setMinegt1(Float mineg1) {
	this.minegt1 = mineg1;
    }

    public Float getMaxegt1() {
	return maxegt1;
    }

    public void setMaxegt1(Float maxeg1) {
	this.maxegt1 = maxeg1;
    }

    public boolean isEgt2() {
	return egt2;
    }

    public void setEgt2(boolean egt2) {
	this.egt2 = egt2;
    }

    public Float getMinegt2() {
	return minegt2;
    }

    public void setMinegt2(Float minegt2) {
	this.minegt2 = minegt2;
    }

    public Float getMaxegt2() {
	return maxegt2;
    }

    public void setMaxegt2(Float maxegt2) {
	this.maxegt2 = maxegt2;
    }

    public boolean isCht1() {
	return cht1;
    }

    public void setCht1(boolean cht1) {
	this.cht1 = cht1;
    }

    public Float getMincht1() {
	return mincht1;
    }

    public void setMincht1(Float mincht1) {
	this.mincht1 = mincht1;
    }

    public Float getMaxcht1() {
	return maxcht1;
    }

    public void setMaxcht1(Float maxcht1) {
	this.maxcht1 = maxcht1;
    }

    public boolean isCht2() {
	return cht2;
    }

    public void setCht2(boolean cht2) {
	this.cht2 = cht2;
    }

    public Float getMincht2() {
	return mincht2;
    }

    public void setMincht2(Float mincht2) {
	this.mincht2 = mincht2;
    }

    public Float getMaxcht2() {
	return maxcht2;
    }

    public void setMaxcht2(Float maxcht2) {
	this.maxcht2 = maxcht2;
    }

    public boolean isEst() {
	return est;
    }

    public void setEst(boolean est) {
	this.est = est;
    }

    public Float getMinest() {
	return minest;
    }

    public void setMinest(Float minest) {
	this.minest = minest;
    }

    public Float getMaxest() {
	return maxest;
    }

    public void setMaxest(Float maxest) {
	this.maxest = maxest;
    }

    public boolean isFf_() {
	return ff_;
    }

    public void setFf_(boolean ff_) {
	this.ff_ = ff_;
    }

    public Float getMinff_() {
	return minff_;
    }

    public void setMinff_(Float minff_) {
	this.minff_ = minff_;
    }

    public Float getMaxff_() {
	return maxff_;
    }

    public void setMaxff_(Float maxff_) {
	this.maxff_ = maxff_;
    }

    public boolean isFp_() {
	return fp_;
    }

    public void setFp_(boolean fp_) {
	this.fp_ = fp_;
    }

    public Float getMinfp_() {
	return minfp_;
    }

    public void setMinfp_(Float minfp_) {
	this.minfp_ = minfp_;
    }

    public Float getMaxfp_() {
	return maxfp_;
    }

    public void setMaxfp_(Float maxfp_) {
	this.maxfp_ = maxfp_;
    }

    public boolean isOp_() {
	return op_;
    }

    public void setOp_(boolean op_) {
	this.op_ = op_;
    }

    public Float getMinop_() {
	return minop_;
    }

    public void setMinop_(Float minop_) {
	this.minop_ = minop_;
    }

    public Float getMaxop_() {
	return maxop_;
    }

    public void setMaxop_(Float maxop_) {
	this.maxop_ = maxop_;
    }

    public boolean isOt_() {
	return ot_;
    }

    public void setOt_(boolean ot_) {
	this.ot_ = ot_;
    }

    public Float getMinot_() {
	return minot_;
    }

    public void setMinot_(Float minot_) {
	this.minot_ = minot_;
    }

    public Float getMaxot_() {
	return maxot_;
    }

    public void setMaxot_(Float maxot_) {
	this.maxot_ = maxot_;
    }

    public boolean isN1_() {
	return n1_;
    }

    public void setN1_(boolean n1_) {
	this.n1_ = n1_;
    }

    public Float getMinn1_() {
	return minn1_;
    }

    public void setMinn1_(Float minn1_) {
	this.minn1_ = minn1_;
    }

    public Float getMaxn1_() {
	return maxn1_;
    }

    public void setMaxn1_(Float maxn1_) {
	this.maxn1_ = maxn1_;
    }

    public boolean isN2_() {
	return n2_;
    }

    public void setN2_(boolean n2_) {
	this.n2_ = n2_;
    }

    public Float getMinn2_() {
	return minn2_;
    }

    public void setMinn2_(Float minn2_) {
	this.minn2_ = minn2_;
    }

    public Float getMaxn2_() {
	return maxn2_;
    }

    public void setMaxn2_(Float maxn2_) {
	this.maxn2_ = maxn2_;
    }

    public boolean isVib() {
	return vib;
    }

    public void setVib(boolean vib) {
	this.vib = vib;
    }

    public Float getMinvib() {
	return minvib;
    }

    public void setMinvib(Float minvib) {
	this.minvib = minvib;
    }

    public Float getMaxvib() {
	return maxvib;
    }

    public void setMaxvib(Float maxvib) {
	this.maxvib = maxvib;
    }

    public boolean isVlt() {
	return vlt;
    }

    public void setVlt(boolean vlt) {
	this.vlt = vlt;
    }

    public Float getMinvlt() {
	return minvlt;
    }

    public void setMinvlt(Float minvlt) {
	this.minvlt = minvlt;
    }

    public Float getMaxvlt() {
	return maxvlt;
    }

    public void setMaxvlt(Float maxvlt) {
	this.maxvlt = maxvlt;
    }

    public boolean isAmp() {
	return amp;
    }

    public void setAmp(boolean amp) {
	this.amp = amp;
    }

    public Float getMinamp() {
	return minamp;
    }

    public void setMinamp(Float minamp) {
	this.minamp = minamp;
    }

    public Float getMaxamp() {
	return maxamp;
    }

    public void setMaxamp(Float maxamp) {
	this.maxamp = maxamp;
    }

    public int getEnginemodelorder() {
	return enginemodelorder;
    }

    public void setEnginemodelorder(int enginemodelorder) {
	this.enginemodelorder = enginemodelorder;
    }

}
