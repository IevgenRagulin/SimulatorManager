package cz.vutbr.fit.simulatormanager.beans;

import java.io.Serializable;

import com.vaadin.data.Item;

import cz.vutbr.fit.simulatormanager.database.columns.EngineModelCols;

public class EngineModelBean implements Serializable {
    private static final long serialVersionUID = 1L;
    // is this 0th, 1st, 2nd.. engine
    private Integer enginemodelorder;

    private boolean rpm;
    // we use Float instead of float to allow null values
    private Float minrpm;
    private Float lowrpm;
    private Float highrpm;
    private Float maxrpm;

    private boolean pwr;
    private Float minpwr;
    private Float lowpwr;
    private Float highpwr;
    private Float maxpwr;

    private boolean pwp;
    private Float minpwp;
    private Float lowpwp;
    private Float highpwp;
    private Float maxpwp;

    private boolean mp_;
    private Float minmp_;
    private Float lowmp_;
    private Float highmp_;
    private Float maxmp_;

    private boolean et1;
    private Float minet1;
    private Float lowet1;
    private Float highet1;
    private Float maxet1;

    private boolean et2;
    private Float minet2;
    private Float lowet2;
    private Float highet2;
    private Float maxet2;

    private boolean ct1;
    private Float minct1;
    private Float lowct1;
    private Float highct1;
    private Float maxct1;

    private boolean ct2;
    private Float minct2;
    private Float lowct2;
    private Float highct2;
    private Float maxct2;

    private boolean est;
    private Float minest;
    private Float lowest;
    private Float highest;
    private Float maxest;

    private boolean ff_;
    private Float minff_;
    private Float lowff_;
    private Float highff_;
    private Float maxff_;

    private boolean fp_;
    private Float minfp_;
    private Float lowfp_;
    private Float highfp_;
    private Float maxfp_;

    private boolean op_;
    private Float minop_;
    private Float lowop_;
    private Float highop_;
    private Float maxop_;

    private boolean ot_;
    private Float minot_;
    private Float lowot_;
    private Float highot_;
    private Float maxot_;

    private boolean n1_;
    private Float minn1_;
    private Float lown1_;
    private Float highn1_;
    private Float maxn1_;

    private boolean n2_;
    private Float minn2_;
    private Float lown2_;
    private Float highn2_;
    private Float maxn2_;

    private boolean vib;
    private Float minvib;
    private Float lowvib;
    private Float highvib;
    private Float maxvib;

    private boolean vlt;
    private Float minvlt;
    private Float lowvlt;
    private Float highvlt;
    private Float maxvlt;

    private boolean amp;
    private Float minamp;
    private Float lowamp;
    private Float highamp;
    private Float maxamp;

    public EngineModelBean(Item engineModelItem) {
	setEnginemodelorder((int) engineModelItem.getItemProperty(EngineModelCols.enginemodelorder.toString()).getValue());

	setRpm((boolean) engineModelItem.getItemProperty(EngineModelCols.rpm.toString()).getValue());
	setMinrpm((Float) engineModelItem.getItemProperty(EngineModelCols.minrpm.toString()).getValue());
	setLowrpm((Float) engineModelItem.getItemProperty(EngineModelCols.lowrpm.toString()).getValue());
	setHighrpm((Float) engineModelItem.getItemProperty(EngineModelCols.highrpm.toString()).getValue());
	setMaxrpm((Float) engineModelItem.getItemProperty(EngineModelCols.maxrpm.toString()).getValue());

	setPwr((boolean) engineModelItem.getItemProperty(EngineModelCols.pwr.toString()).getValue());
	setMinpwr((Float) engineModelItem.getItemProperty(EngineModelCols.minpwr.toString()).getValue());
	setLowpwr((Float) engineModelItem.getItemProperty(EngineModelCols.lowpwr.toString()).getValue());
	setHighpwr((Float) engineModelItem.getItemProperty(EngineModelCols.highpwr.toString()).getValue());
	setMaxpwr((Float) engineModelItem.getItemProperty(EngineModelCols.maxpwr.toString()).getValue());

	setPwp((boolean) engineModelItem.getItemProperty(EngineModelCols.pwp.toString()).getValue());
	setMinpwp((Float) engineModelItem.getItemProperty(EngineModelCols.minpwp.toString()).getValue());
	setLowpwp((Float) engineModelItem.getItemProperty(EngineModelCols.lowpwp.toString()).getValue());
	setHighpwp((Float) engineModelItem.getItemProperty(EngineModelCols.highpwp.toString()).getValue());
	setMaxpwp((Float) engineModelItem.getItemProperty(EngineModelCols.maxpwp.toString()).getValue());

	setMp_((boolean) engineModelItem.getItemProperty(EngineModelCols.mp_.toString()).getValue());
	setMinmp_((Float) engineModelItem.getItemProperty(EngineModelCols.minmp.toString()).getValue());
	setLowmp_((Float) engineModelItem.getItemProperty(EngineModelCols.lowmp.toString()).getValue());
	setHighmp_((Float) engineModelItem.getItemProperty(EngineModelCols.highmp.toString()).getValue());
	setMaxmp_((Float) engineModelItem.getItemProperty(EngineModelCols.maxmp.toString()).getValue());

	setEgt1((boolean) engineModelItem.getItemProperty(EngineModelCols.et1.toString()).getValue());
	setMinet1((Float) engineModelItem.getItemProperty(EngineModelCols.minet1.toString()).getValue());
	setLowet1((Float) engineModelItem.getItemProperty(EngineModelCols.lowet1.toString()).getValue());
	setHighet1((Float) engineModelItem.getItemProperty(EngineModelCols.highet1.toString()).getValue());
	setMaxet1((Float) engineModelItem.getItemProperty(EngineModelCols.maxet1.toString()).getValue());

	setEgt2((boolean) engineModelItem.getItemProperty(EngineModelCols.et2.toString()).getValue());
	setMinet2((Float) engineModelItem.getItemProperty(EngineModelCols.minet2.toString()).getValue());
	setLowet2((Float) engineModelItem.getItemProperty(EngineModelCols.lowet2.toString()).getValue());
	setHighet2((Float) engineModelItem.getItemProperty(EngineModelCols.highet2.toString()).getValue());
	setMaxet2((Float) engineModelItem.getItemProperty(EngineModelCols.maxet2.toString()).getValue());

	setCht1((boolean) engineModelItem.getItemProperty(EngineModelCols.ct1.toString()).getValue());
	setMinct1((Float) engineModelItem.getItemProperty(EngineModelCols.minct1.toString()).getValue());
	setLowct1((Float) engineModelItem.getItemProperty(EngineModelCols.lowct1.toString()).getValue());
	setHighct1((Float) engineModelItem.getItemProperty(EngineModelCols.highct1.toString()).getValue());
	setMaxct1((Float) engineModelItem.getItemProperty(EngineModelCols.maxct1.toString()).getValue());

	setCht2((boolean) engineModelItem.getItemProperty(EngineModelCols.ct2.toString()).getValue());
	setMinct2((Float) engineModelItem.getItemProperty(EngineModelCols.minct2.toString()).getValue());
	setLowct2((Float) engineModelItem.getItemProperty(EngineModelCols.lowct2.toString()).getValue());
	setHighct2((Float) engineModelItem.getItemProperty(EngineModelCols.highct2.toString()).getValue());
	setMaxct2((Float) engineModelItem.getItemProperty(EngineModelCols.maxct2.toString()).getValue());

	setEst((boolean) engineModelItem.getItemProperty(EngineModelCols.est.toString()).getValue());
	setMinest((Float) engineModelItem.getItemProperty(EngineModelCols.minest.toString()).getValue());
	setLowest((Float) engineModelItem.getItemProperty(EngineModelCols.lowest.toString()).getValue());
	setHighest((Float) engineModelItem.getItemProperty(EngineModelCols.highest.toString()).getValue());
	setMaxest((Float) engineModelItem.getItemProperty(EngineModelCols.maxest.toString()).getValue());

	setFf_((boolean) engineModelItem.getItemProperty(EngineModelCols.ff_.toString()).getValue());
	setMinff_((Float) engineModelItem.getItemProperty(EngineModelCols.minff.toString()).getValue());
	setLowff_((Float) engineModelItem.getItemProperty(EngineModelCols.lowff.toString()).getValue());
	setHighff_((Float) engineModelItem.getItemProperty(EngineModelCols.highff.toString()).getValue());
	setMaxff_((Float) engineModelItem.getItemProperty(EngineModelCols.maxff.toString()).getValue());

	setFp_((boolean) engineModelItem.getItemProperty(EngineModelCols.fp_.toString()).getValue());
	setMinfp_((Float) engineModelItem.getItemProperty(EngineModelCols.minfp.toString()).getValue());
	setLowfp_((Float) engineModelItem.getItemProperty(EngineModelCols.lowfp.toString()).getValue());
	setHighfp_((Float) engineModelItem.getItemProperty(EngineModelCols.highfp.toString()).getValue());
	setMaxfp_((Float) engineModelItem.getItemProperty(EngineModelCols.maxfp.toString()).getValue());

	setOp_((boolean) engineModelItem.getItemProperty(EngineModelCols.op_.toString()).getValue());
	setMinop_((Float) engineModelItem.getItemProperty(EngineModelCols.minop.toString()).getValue());
	setLowop_((Float) engineModelItem.getItemProperty(EngineModelCols.lowop.toString()).getValue());
	setHighop_((Float) engineModelItem.getItemProperty(EngineModelCols.highop.toString()).getValue());
	setMaxop_((Float) engineModelItem.getItemProperty(EngineModelCols.maxop.toString()).getValue());

	setOt_((boolean) engineModelItem.getItemProperty(EngineModelCols.ot_.toString()).getValue());
	setMinot_((Float) engineModelItem.getItemProperty(EngineModelCols.minot.toString()).getValue());
	setLowot_((Float) engineModelItem.getItemProperty(EngineModelCols.lowot.toString()).getValue());
	setHighot_((Float) engineModelItem.getItemProperty(EngineModelCols.highot.toString()).getValue());
	setMaxot_((Float) engineModelItem.getItemProperty(EngineModelCols.maxot.toString()).getValue());

	setN1_((boolean) engineModelItem.getItemProperty(EngineModelCols.n1_.toString()).getValue());
	setMinn1_((Float) engineModelItem.getItemProperty(EngineModelCols.minn1.toString()).getValue());
	setLown1_((Float) engineModelItem.getItemProperty(EngineModelCols.lown1.toString()).getValue());
	setHighn1((Float) engineModelItem.getItemProperty(EngineModelCols.highn1.toString()).getValue());
	setMaxn1_((Float) engineModelItem.getItemProperty(EngineModelCols.maxn1.toString()).getValue());

	setN2_((boolean) engineModelItem.getItemProperty(EngineModelCols.n2_.toString()).getValue());
	setMinn2_((Float) engineModelItem.getItemProperty(EngineModelCols.minn2.toString()).getValue());
	setLown2_((Float) engineModelItem.getItemProperty(EngineModelCols.lown2.toString()).getValue());
	setHighn2_((Float) engineModelItem.getItemProperty(EngineModelCols.highn2.toString()).getValue());
	setMaxn2_((Float) engineModelItem.getItemProperty(EngineModelCols.maxn2.toString()).getValue());

	setVib((boolean) engineModelItem.getItemProperty(EngineModelCols.vib.toString()).getValue());
	setMinvib((Float) engineModelItem.getItemProperty(EngineModelCols.minvib.toString()).getValue());
	setLowvib((Float) engineModelItem.getItemProperty(EngineModelCols.lowvib.toString()).getValue());
	setHighvib((Float) engineModelItem.getItemProperty(EngineModelCols.highvib.toString()).getValue());
	setMaxvib((Float) engineModelItem.getItemProperty(EngineModelCols.maxvib.toString()).getValue());

	setVlt((boolean) engineModelItem.getItemProperty(EngineModelCols.vlt.toString()).getValue());
	setMinvlt((Float) engineModelItem.getItemProperty(EngineModelCols.minvlt.toString()).getValue());
	setLowvlt((Float) engineModelItem.getItemProperty(EngineModelCols.lowvlt.toString()).getValue());
	setHighvlt((Float) engineModelItem.getItemProperty(EngineModelCols.highvlt.toString()).getValue());
	setMaxvlt((Float) engineModelItem.getItemProperty(EngineModelCols.maxvlt.toString()).getValue());

	setAmp((boolean) engineModelItem.getItemProperty(EngineModelCols.amp.toString()).getValue());
	setMinamp((Float) engineModelItem.getItemProperty(EngineModelCols.minamp.toString()).getValue());
	setLowamp((Float) engineModelItem.getItemProperty(EngineModelCols.lowamp.toString()).getValue());
	setHighamp((Float) engineModelItem.getItemProperty(EngineModelCols.highamp.toString()).getValue());
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
	return et1;
    }

    public void setEgt1(boolean et1) {
	this.et1 = et1;
    }

    public Float getMinet1() {
	return minet1;
    }

    public void setMinet1(Float mineg1) {
	this.minet1 = mineg1;
    }

    public Float getMaxet1() {
	return maxet1;
    }

    public void setMaxet1(Float maxeg1) {
	this.maxet1 = maxeg1;
    }

    public boolean isEgt2() {
	return et2;
    }

    public void setEgt2(boolean et2) {
	this.et2 = et2;
    }

    public Float getMinet2() {
	return minet2;
    }

    public void setMinet2(Float minet2) {
	this.minet2 = minet2;
    }

    public Float getMaxet2() {
	return maxet2;
    }

    public void setMaxet2(Float maxet2) {
	this.maxet2 = maxet2;
    }

    public boolean isCht1() {
	return ct1;
    }

    public void setCht1(boolean ct1) {
	this.ct1 = ct1;
    }

    public Float getMinct1() {
	return minct1;
    }

    public void setMinct1(Float minct1) {
	this.minct1 = minct1;
    }

    public Float getMaxct1() {
	return maxct1;
    }

    public void setMaxct1(Float maxct1) {
	this.maxct1 = maxct1;
    }

    public boolean isCht2() {
	return ct2;
    }

    public void setCht2(boolean ct2) {
	this.ct2 = ct2;
    }

    public Float getMinct2() {
	return minct2;
    }

    public void setMinct2(Float minct2) {
	this.minct2 = minct2;
    }

    public Float getMaxct2() {
	return maxct2;
    }

    public void setMaxct2(Float maxct2) {
	this.maxct2 = maxct2;
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

    public Integer getEnginemodelorder() {
	return enginemodelorder;
    }

    public void setEnginemodelorder(Integer enginemodelorder) {
	this.enginemodelorder = enginemodelorder;
    }

    public Float getLowrpm() {
	return lowrpm;
    }

    public void setLowrpm(Float lowrpm) {
	this.lowrpm = lowrpm;
    }

    public Float getHighrpm() {
	return highrpm;
    }

    public void setHighrpm(Float highrpm) {
	this.highrpm = highrpm;
    }

    public Float getLowpwr() {
	return lowpwr;
    }

    public void setLowpwr(Float lowpwr) {
	this.lowpwr = lowpwr;
    }

    public Float getHighpwr() {
	return highpwr;
    }

    public void setHighpwr(Float highpwr) {
	this.highpwr = highpwr;
    }

    public Float getLowpwp() {
	return lowpwp;
    }

    public void setLowpwp(Float lowpwp) {
	this.lowpwp = lowpwp;
    }

    public Float getHighpwp() {
	return highpwp;
    }

    public void setHighpwp(Float highpwp) {
	this.highpwp = highpwp;
    }

    public Float getLowmp_() {
	return lowmp_;
    }

    public void setLowmp_(Float lowmp_) {
	this.lowmp_ = lowmp_;
    }

    public Float getHighmp_() {
	return highmp_;
    }

    public void setHighmp_(Float highmp_) {
	this.highmp_ = highmp_;
    }

    public Float getLowet1() {
	return lowet1;
    }

    public void setLowet1(Float lowet1) {
	this.lowet1 = lowet1;
    }

    public Float getHighet1() {
	return highet1;
    }

    public void setHighet1(Float highet1) {
	this.highet1 = highet1;
    }

    public Float getLowet2() {
	return lowet2;
    }

    public void setLowet2(Float lowet2) {
	this.lowet2 = lowet2;
    }

    public Float getHighet2() {
	return highet2;
    }

    public void setHighet2(Float highet2) {
	this.highet2 = highet2;
    }

    public Float getLowct1() {
	return lowct1;
    }

    public void setLowct1(Float lowct1) {
	this.lowct1 = lowct1;
    }

    public Float getHighct1() {
	return highct1;
    }

    public void setHighct1(Float highct1) {
	this.highct1 = highct1;
    }

    public Float getLowct2() {
	return lowct2;
    }

    public void setLowct2(Float lowct2) {
	this.lowct2 = lowct2;
    }

    public Float getHighct2() {
	return highct2;
    }

    public void setHighct2(Float highct2) {
	this.highct2 = highct2;
    }

    public Float getLowest() {
	return lowest;
    }

    public void setLowest(Float lowest) {
	this.lowest = lowest;
    }

    public Float getHighest() {
	return highest;
    }

    public void setHighest(Float highest) {
	this.highest = highest;
    }

    public Float getLowff_() {
	return lowff_;
    }

    public void setLowff_(Float lowff_) {
	this.lowff_ = lowff_;
    }

    public Float getHighff_() {
	return highff_;
    }

    public void setHighff_(Float highff_) {
	this.highff_ = highff_;
    }

    public Float getLowfp_() {
	return lowfp_;
    }

    public void setLowfp_(Float lowfp_) {
	this.lowfp_ = lowfp_;
    }

    public Float getHighfp_() {
	return highfp_;
    }

    public void setHighfp_(Float highfp_) {
	this.highfp_ = highfp_;
    }

    public Float getLowop_() {
	return lowop_;
    }

    public void setLowop_(Float lowop_) {
	this.lowop_ = lowop_;
    }

    public Float getHighop_() {
	return highop_;
    }

    public void setHighop_(Float highop_) {
	this.highop_ = highop_;
    }

    public Float getLowot_() {
	return lowot_;
    }

    public void setLowot_(Float lowot_) {
	this.lowot_ = lowot_;
    }

    public Float getHighot_() {
	return highot_;
    }

    public void setHighot_(Float highot_) {
	this.highot_ = highot_;
    }

    public Float getLown1_() {
	return lown1_;
    }

    public void setLown1_(Float lown1_) {
	this.lown1_ = lown1_;
    }

    public Float getHighn1_() {
	return highn1_;
    }

    public void setHighn1(Float highn1_) {
	this.highn1_ = highn1_;
    }

    public Float getLown2_() {
	return lown2_;
    }

    public void setLown2_(Float lown2_) {
	this.lown2_ = lown2_;
    }

    public Float getHighn2_() {
	return highn2_;
    }

    public void setHighn2_(Float highn2_) {
	this.highn2_ = highn2_;
    }

    public Float getLowvib() {
	return lowvib;
    }

    public void setLowvib(Float lowvib) {
	this.lowvib = lowvib;
    }

    public Float getHighvib() {
	return highvib;
    }

    public void setHighvib(Float highvib) {
	this.highvib = highvib;
    }

    public Float getLowvlt() {
	return lowvlt;
    }

    public void setLowvlt(Float lowvlt) {
	this.lowvlt = lowvlt;
    }

    public Float getHighvlt() {
	return highvlt;
    }

    public void setHighvlt(Float highvlt) {
	this.highvlt = highvlt;
    }

    public Float getLowamp() {
	return lowamp;
    }

    public void setLowamp(Float lowamp) {
	this.lowamp = lowamp;
    }

    public Float getHighamp() {
	return highamp;
    }

    public void setHighamp(Float highamp) {
	this.highamp = highamp;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (amp ? 1231 : 1237);
	result = prime * result + (ct1 ? 1231 : 1237);
	result = prime * result + (ct2 ? 1231 : 1237);
	result = prime * result + (et1 ? 1231 : 1237);
	result = prime * result + (et2 ? 1231 : 1237);
	result = prime * result + ((enginemodelorder == null) ? 0 : enginemodelorder.hashCode());
	result = prime * result + (est ? 1231 : 1237);
	result = prime * result + (ff_ ? 1231 : 1237);
	result = prime * result + (fp_ ? 1231 : 1237);
	result = prime * result + ((highamp == null) ? 0 : highamp.hashCode());
	result = prime * result + ((highct1 == null) ? 0 : highct1.hashCode());
	result = prime * result + ((highct2 == null) ? 0 : highct2.hashCode());
	result = prime * result + ((highet1 == null) ? 0 : highet1.hashCode());
	result = prime * result + ((highet2 == null) ? 0 : highet2.hashCode());
	result = prime * result + ((highest == null) ? 0 : highest.hashCode());
	result = prime * result + ((highff_ == null) ? 0 : highff_.hashCode());
	result = prime * result + ((highfp_ == null) ? 0 : highfp_.hashCode());
	result = prime * result + ((highmp_ == null) ? 0 : highmp_.hashCode());
	result = prime * result + ((highn1_ == null) ? 0 : highn1_.hashCode());
	result = prime * result + ((highn2_ == null) ? 0 : highn2_.hashCode());
	result = prime * result + ((highop_ == null) ? 0 : highop_.hashCode());
	result = prime * result + ((highot_ == null) ? 0 : highot_.hashCode());
	result = prime * result + ((highpwp == null) ? 0 : highpwp.hashCode());
	result = prime * result + ((highpwr == null) ? 0 : highpwr.hashCode());
	result = prime * result + ((highrpm == null) ? 0 : highrpm.hashCode());
	result = prime * result + ((highvib == null) ? 0 : highvib.hashCode());
	result = prime * result + ((highvlt == null) ? 0 : highvlt.hashCode());
	result = prime * result + ((lowamp == null) ? 0 : lowamp.hashCode());
	result = prime * result + ((lowct1 == null) ? 0 : lowct1.hashCode());
	result = prime * result + ((lowct2 == null) ? 0 : lowct2.hashCode());
	result = prime * result + ((lowet1 == null) ? 0 : lowet1.hashCode());
	result = prime * result + ((lowet2 == null) ? 0 : lowet2.hashCode());
	result = prime * result + ((lowest == null) ? 0 : lowest.hashCode());
	result = prime * result + ((lowff_ == null) ? 0 : lowff_.hashCode());
	result = prime * result + ((lowfp_ == null) ? 0 : lowfp_.hashCode());
	result = prime * result + ((lowmp_ == null) ? 0 : lowmp_.hashCode());
	result = prime * result + ((lown1_ == null) ? 0 : lown1_.hashCode());
	result = prime * result + ((lown2_ == null) ? 0 : lown2_.hashCode());
	result = prime * result + ((lowop_ == null) ? 0 : lowop_.hashCode());
	result = prime * result + ((lowot_ == null) ? 0 : lowot_.hashCode());
	result = prime * result + ((lowpwp == null) ? 0 : lowpwp.hashCode());
	result = prime * result + ((lowpwr == null) ? 0 : lowpwr.hashCode());
	result = prime * result + ((lowrpm == null) ? 0 : lowrpm.hashCode());
	result = prime * result + ((lowvib == null) ? 0 : lowvib.hashCode());
	result = prime * result + ((lowvlt == null) ? 0 : lowvlt.hashCode());
	result = prime * result + ((maxamp == null) ? 0 : maxamp.hashCode());
	result = prime * result + ((maxct1 == null) ? 0 : maxct1.hashCode());
	result = prime * result + ((maxct2 == null) ? 0 : maxct2.hashCode());
	result = prime * result + ((maxet1 == null) ? 0 : maxet1.hashCode());
	result = prime * result + ((maxet2 == null) ? 0 : maxet2.hashCode());
	result = prime * result + ((maxest == null) ? 0 : maxest.hashCode());
	result = prime * result + ((maxff_ == null) ? 0 : maxff_.hashCode());
	result = prime * result + ((maxfp_ == null) ? 0 : maxfp_.hashCode());
	result = prime * result + ((maxmp_ == null) ? 0 : maxmp_.hashCode());
	result = prime * result + ((maxn1_ == null) ? 0 : maxn1_.hashCode());
	result = prime * result + ((maxn2_ == null) ? 0 : maxn2_.hashCode());
	result = prime * result + ((maxop_ == null) ? 0 : maxop_.hashCode());
	result = prime * result + ((maxot_ == null) ? 0 : maxot_.hashCode());
	result = prime * result + ((maxpwp == null) ? 0 : maxpwp.hashCode());
	result = prime * result + ((maxpwr == null) ? 0 : maxpwr.hashCode());
	result = prime * result + ((maxrpm == null) ? 0 : maxrpm.hashCode());
	result = prime * result + ((maxvib == null) ? 0 : maxvib.hashCode());
	result = prime * result + ((maxvlt == null) ? 0 : maxvlt.hashCode());
	result = prime * result + ((minamp == null) ? 0 : minamp.hashCode());
	result = prime * result + ((minct1 == null) ? 0 : minct1.hashCode());
	result = prime * result + ((minct2 == null) ? 0 : minct2.hashCode());
	result = prime * result + ((minet1 == null) ? 0 : minet1.hashCode());
	result = prime * result + ((minet2 == null) ? 0 : minet2.hashCode());
	result = prime * result + ((minest == null) ? 0 : minest.hashCode());
	result = prime * result + ((minff_ == null) ? 0 : minff_.hashCode());
	result = prime * result + ((minfp_ == null) ? 0 : minfp_.hashCode());
	result = prime * result + ((minmp_ == null) ? 0 : minmp_.hashCode());
	result = prime * result + ((minn1_ == null) ? 0 : minn1_.hashCode());
	result = prime * result + ((minn2_ == null) ? 0 : minn2_.hashCode());
	result = prime * result + ((minop_ == null) ? 0 : minop_.hashCode());
	result = prime * result + ((minot_ == null) ? 0 : minot_.hashCode());
	result = prime * result + ((minpwp == null) ? 0 : minpwp.hashCode());
	result = prime * result + ((minpwr == null) ? 0 : minpwr.hashCode());
	result = prime * result + ((minrpm == null) ? 0 : minrpm.hashCode());
	result = prime * result + ((minvib == null) ? 0 : minvib.hashCode());
	result = prime * result + ((minvlt == null) ? 0 : minvlt.hashCode());
	result = prime * result + (mp_ ? 1231 : 1237);
	result = prime * result + (n1_ ? 1231 : 1237);
	result = prime * result + (n2_ ? 1231 : 1237);
	result = prime * result + (op_ ? 1231 : 1237);
	result = prime * result + (ot_ ? 1231 : 1237);
	result = prime * result + (pwp ? 1231 : 1237);
	result = prime * result + (pwr ? 1231 : 1237);
	result = prime * result + (rpm ? 1231 : 1237);
	result = prime * result + (vib ? 1231 : 1237);
	result = prime * result + (vlt ? 1231 : 1237);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	EngineModelBean other = (EngineModelBean) obj;
	if (amp != other.amp)
	    return false;
	if (ct1 != other.ct1)
	    return false;
	if (ct2 != other.ct2)
	    return false;
	if (et1 != other.et1)
	    return false;
	if (et2 != other.et2)
	    return false;
	if (enginemodelorder == null) {
	    if (other.enginemodelorder != null)
		return false;
	} else if (!enginemodelorder.equals(other.enginemodelorder))
	    return false;
	if (est != other.est)
	    return false;
	if (ff_ != other.ff_)
	    return false;
	if (fp_ != other.fp_)
	    return false;
	if (highamp == null) {
	    if (other.highamp != null)
		return false;
	} else if (!highamp.equals(other.highamp))
	    return false;
	if (highct1 == null) {
	    if (other.highct1 != null)
		return false;
	} else if (!highct1.equals(other.highct1))
	    return false;
	if (highct2 == null) {
	    if (other.highct2 != null)
		return false;
	} else if (!highct2.equals(other.highct2))
	    return false;
	if (highet1 == null) {
	    if (other.highet1 != null)
		return false;
	} else if (!highet1.equals(other.highet1))
	    return false;
	if (highet2 == null) {
	    if (other.highet2 != null)
		return false;
	} else if (!highet2.equals(other.highet2))
	    return false;
	if (highest == null) {
	    if (other.highest != null)
		return false;
	} else if (!highest.equals(other.highest))
	    return false;
	if (highff_ == null) {
	    if (other.highff_ != null)
		return false;
	} else if (!highff_.equals(other.highff_))
	    return false;
	if (highfp_ == null) {
	    if (other.highfp_ != null)
		return false;
	} else if (!highfp_.equals(other.highfp_))
	    return false;
	if (highmp_ == null) {
	    if (other.highmp_ != null)
		return false;
	} else if (!highmp_.equals(other.highmp_))
	    return false;
	if (highn1_ == null) {
	    if (other.highn1_ != null)
		return false;
	} else if (!highn1_.equals(other.highn1_))
	    return false;
	if (highn2_ == null) {
	    if (other.highn2_ != null)
		return false;
	} else if (!highn2_.equals(other.highn2_))
	    return false;
	if (highop_ == null) {
	    if (other.highop_ != null)
		return false;
	} else if (!highop_.equals(other.highop_))
	    return false;
	if (highot_ == null) {
	    if (other.highot_ != null)
		return false;
	} else if (!highot_.equals(other.highot_))
	    return false;
	if (highpwp == null) {
	    if (other.highpwp != null)
		return false;
	} else if (!highpwp.equals(other.highpwp))
	    return false;
	if (highpwr == null) {
	    if (other.highpwr != null)
		return false;
	} else if (!highpwr.equals(other.highpwr))
	    return false;
	if (highrpm == null) {
	    if (other.highrpm != null)
		return false;
	} else if (!highrpm.equals(other.highrpm))
	    return false;
	if (highvib == null) {
	    if (other.highvib != null)
		return false;
	} else if (!highvib.equals(other.highvib))
	    return false;
	if (highvlt == null) {
	    if (other.highvlt != null)
		return false;
	} else if (!highvlt.equals(other.highvlt))
	    return false;
	if (lowamp == null) {
	    if (other.lowamp != null)
		return false;
	} else if (!lowamp.equals(other.lowamp))
	    return false;
	if (lowct1 == null) {
	    if (other.lowct1 != null)
		return false;
	} else if (!lowct1.equals(other.lowct1))
	    return false;
	if (lowct2 == null) {
	    if (other.lowct2 != null)
		return false;
	} else if (!lowct2.equals(other.lowct2))
	    return false;
	if (lowet1 == null) {
	    if (other.lowet1 != null)
		return false;
	} else if (!lowet1.equals(other.lowet1))
	    return false;
	if (lowet2 == null) {
	    if (other.lowet2 != null)
		return false;
	} else if (!lowet2.equals(other.lowet2))
	    return false;
	if (lowest == null) {
	    if (other.lowest != null)
		return false;
	} else if (!lowest.equals(other.lowest))
	    return false;
	if (lowff_ == null) {
	    if (other.lowff_ != null)
		return false;
	} else if (!lowff_.equals(other.lowff_))
	    return false;
	if (lowfp_ == null) {
	    if (other.lowfp_ != null)
		return false;
	} else if (!lowfp_.equals(other.lowfp_))
	    return false;
	if (lowmp_ == null) {
	    if (other.lowmp_ != null)
		return false;
	} else if (!lowmp_.equals(other.lowmp_))
	    return false;
	if (lown1_ == null) {
	    if (other.lown1_ != null)
		return false;
	} else if (!lown1_.equals(other.lown1_))
	    return false;
	if (lown2_ == null) {
	    if (other.lown2_ != null)
		return false;
	} else if (!lown2_.equals(other.lown2_))
	    return false;
	if (lowop_ == null) {
	    if (other.lowop_ != null)
		return false;
	} else if (!lowop_.equals(other.lowop_))
	    return false;
	if (lowot_ == null) {
	    if (other.lowot_ != null)
		return false;
	} else if (!lowot_.equals(other.lowot_))
	    return false;
	if (lowpwp == null) {
	    if (other.lowpwp != null)
		return false;
	} else if (!lowpwp.equals(other.lowpwp))
	    return false;
	if (lowpwr == null) {
	    if (other.lowpwr != null)
		return false;
	} else if (!lowpwr.equals(other.lowpwr))
	    return false;
	if (lowrpm == null) {
	    if (other.lowrpm != null)
		return false;
	} else if (!lowrpm.equals(other.lowrpm))
	    return false;
	if (lowvib == null) {
	    if (other.lowvib != null)
		return false;
	} else if (!lowvib.equals(other.lowvib))
	    return false;
	if (lowvlt == null) {
	    if (other.lowvlt != null)
		return false;
	} else if (!lowvlt.equals(other.lowvlt))
	    return false;
	if (maxamp == null) {
	    if (other.maxamp != null)
		return false;
	} else if (!maxamp.equals(other.maxamp))
	    return false;
	if (maxct1 == null) {
	    if (other.maxct1 != null)
		return false;
	} else if (!maxct1.equals(other.maxct1))
	    return false;
	if (maxct2 == null) {
	    if (other.maxct2 != null)
		return false;
	} else if (!maxct2.equals(other.maxct2))
	    return false;
	if (maxet1 == null) {
	    if (other.maxet1 != null)
		return false;
	} else if (!maxet1.equals(other.maxet1))
	    return false;
	if (maxet2 == null) {
	    if (other.maxet2 != null)
		return false;
	} else if (!maxet2.equals(other.maxet2))
	    return false;
	if (maxest == null) {
	    if (other.maxest != null)
		return false;
	} else if (!maxest.equals(other.maxest))
	    return false;
	if (maxff_ == null) {
	    if (other.maxff_ != null)
		return false;
	} else if (!maxff_.equals(other.maxff_))
	    return false;
	if (maxfp_ == null) {
	    if (other.maxfp_ != null)
		return false;
	} else if (!maxfp_.equals(other.maxfp_))
	    return false;
	if (maxmp_ == null) {
	    if (other.maxmp_ != null)
		return false;
	} else if (!maxmp_.equals(other.maxmp_))
	    return false;
	if (maxn1_ == null) {
	    if (other.maxn1_ != null)
		return false;
	} else if (!maxn1_.equals(other.maxn1_))
	    return false;
	if (maxn2_ == null) {
	    if (other.maxn2_ != null)
		return false;
	} else if (!maxn2_.equals(other.maxn2_))
	    return false;
	if (maxop_ == null) {
	    if (other.maxop_ != null)
		return false;
	} else if (!maxop_.equals(other.maxop_))
	    return false;
	if (maxot_ == null) {
	    if (other.maxot_ != null)
		return false;
	} else if (!maxot_.equals(other.maxot_))
	    return false;
	if (maxpwp == null) {
	    if (other.maxpwp != null)
		return false;
	} else if (!maxpwp.equals(other.maxpwp))
	    return false;
	if (maxpwr == null) {
	    if (other.maxpwr != null)
		return false;
	} else if (!maxpwr.equals(other.maxpwr))
	    return false;
	if (maxrpm == null) {
	    if (other.maxrpm != null)
		return false;
	} else if (!maxrpm.equals(other.maxrpm))
	    return false;
	if (maxvib == null) {
	    if (other.maxvib != null)
		return false;
	} else if (!maxvib.equals(other.maxvib))
	    return false;
	if (maxvlt == null) {
	    if (other.maxvlt != null)
		return false;
	} else if (!maxvlt.equals(other.maxvlt))
	    return false;
	if (minamp == null) {
	    if (other.minamp != null)
		return false;
	} else if (!minamp.equals(other.minamp))
	    return false;
	if (minct1 == null) {
	    if (other.minct1 != null)
		return false;
	} else if (!minct1.equals(other.minct1))
	    return false;
	if (minct2 == null) {
	    if (other.minct2 != null)
		return false;
	} else if (!minct2.equals(other.minct2))
	    return false;
	if (minet1 == null) {
	    if (other.minet1 != null)
		return false;
	} else if (!minet1.equals(other.minet1))
	    return false;
	if (minet2 == null) {
	    if (other.minet2 != null)
		return false;
	} else if (!minet2.equals(other.minet2))
	    return false;
	if (minest == null) {
	    if (other.minest != null)
		return false;
	} else if (!minest.equals(other.minest))
	    return false;
	if (minff_ == null) {
	    if (other.minff_ != null)
		return false;
	} else if (!minff_.equals(other.minff_))
	    return false;
	if (minfp_ == null) {
	    if (other.minfp_ != null)
		return false;
	} else if (!minfp_.equals(other.minfp_))
	    return false;
	if (minmp_ == null) {
	    if (other.minmp_ != null)
		return false;
	} else if (!minmp_.equals(other.minmp_))
	    return false;
	if (minn1_ == null) {
	    if (other.minn1_ != null)
		return false;
	} else if (!minn1_.equals(other.minn1_))
	    return false;
	if (minn2_ == null) {
	    if (other.minn2_ != null)
		return false;
	} else if (!minn2_.equals(other.minn2_))
	    return false;
	if (minop_ == null) {
	    if (other.minop_ != null)
		return false;
	} else if (!minop_.equals(other.minop_))
	    return false;
	if (minot_ == null) {
	    if (other.minot_ != null)
		return false;
	} else if (!minot_.equals(other.minot_))
	    return false;
	if (minpwp == null) {
	    if (other.minpwp != null)
		return false;
	} else if (!minpwp.equals(other.minpwp))
	    return false;
	if (minpwr == null) {
	    if (other.minpwr != null)
		return false;
	} else if (!minpwr.equals(other.minpwr))
	    return false;
	if (minrpm == null) {
	    if (other.minrpm != null)
		return false;
	} else if (!minrpm.equals(other.minrpm))
	    return false;
	if (minvib == null) {
	    if (other.minvib != null)
		return false;
	} else if (!minvib.equals(other.minvib))
	    return false;
	if (minvlt == null) {
	    if (other.minvlt != null)
		return false;
	} else if (!minvlt.equals(other.minvlt))
	    return false;
	if (mp_ != other.mp_)
	    return false;
	if (n1_ != other.n1_)
	    return false;
	if (n2_ != other.n2_)
	    return false;
	if (op_ != other.op_)
	    return false;
	if (ot_ != other.ot_)
	    return false;
	if (pwp != other.pwp)
	    return false;
	if (pwr != other.pwr)
	    return false;
	if (rpm != other.rpm)
	    return false;
	if (vib != other.vib)
	    return false;
	if (vlt != other.vlt)
	    return false;
	return true;
    }

}
