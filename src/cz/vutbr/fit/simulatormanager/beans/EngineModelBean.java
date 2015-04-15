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

    public Integer getEnginemodelorder() {
	return enginemodelorder;
    }

    public void setEnginemodelorder(Integer enginemodelorder) {
	this.enginemodelorder = enginemodelorder;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (amp ? 1231 : 1237);
	result = prime * result + (cht1 ? 1231 : 1237);
	result = prime * result + (cht2 ? 1231 : 1237);
	result = prime * result + (egt1 ? 1231 : 1237);
	result = prime * result + (egt2 ? 1231 : 1237);
	result = prime * result + ((enginemodelorder == null) ? 0 : enginemodelorder.hashCode());
	result = prime * result + (est ? 1231 : 1237);
	result = prime * result + (ff_ ? 1231 : 1237);
	result = prime * result + (fp_ ? 1231 : 1237);
	result = prime * result + ((maxamp == null) ? 0 : maxamp.hashCode());
	result = prime * result + ((maxcht1 == null) ? 0 : maxcht1.hashCode());
	result = prime * result + ((maxcht2 == null) ? 0 : maxcht2.hashCode());
	result = prime * result + ((maxegt1 == null) ? 0 : maxegt1.hashCode());
	result = prime * result + ((maxegt2 == null) ? 0 : maxegt2.hashCode());
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
	result = prime * result + ((mincht1 == null) ? 0 : mincht1.hashCode());
	result = prime * result + ((mincht2 == null) ? 0 : mincht2.hashCode());
	result = prime * result + ((minegt1 == null) ? 0 : minegt1.hashCode());
	result = prime * result + ((minegt2 == null) ? 0 : minegt2.hashCode());
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
	if (cht1 != other.cht1)
	    return false;
	if (cht2 != other.cht2)
	    return false;
	if (egt1 != other.egt1)
	    return false;
	if (egt2 != other.egt2)
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
	if (maxamp == null) {
	    if (other.maxamp != null)
		return false;
	} else if (!maxamp.equals(other.maxamp))
	    return false;
	if (maxcht1 == null) {
	    if (other.maxcht1 != null)
		return false;
	} else if (!maxcht1.equals(other.maxcht1))
	    return false;
	if (maxcht2 == null) {
	    if (other.maxcht2 != null)
		return false;
	} else if (!maxcht2.equals(other.maxcht2))
	    return false;
	if (maxegt1 == null) {
	    if (other.maxegt1 != null)
		return false;
	} else if (!maxegt1.equals(other.maxegt1))
	    return false;
	if (maxegt2 == null) {
	    if (other.maxegt2 != null)
		return false;
	} else if (!maxegt2.equals(other.maxegt2))
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
	if (mincht1 == null) {
	    if (other.mincht1 != null)
		return false;
	} else if (!mincht1.equals(other.mincht1))
	    return false;
	if (mincht2 == null) {
	    if (other.mincht2 != null)
		return false;
	} else if (!mincht2.equals(other.mincht2))
	    return false;
	if (minegt1 == null) {
	    if (other.minegt1 != null)
		return false;
	} else if (!minegt1.equals(other.minegt1))
	    return false;
	if (minegt2 == null) {
	    if (other.minegt2 != null)
		return false;
	} else if (!minegt2.equals(other.minegt2))
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
