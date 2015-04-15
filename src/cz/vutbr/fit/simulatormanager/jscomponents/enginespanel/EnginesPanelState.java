package cz.vutbr.fit.simulatormanager.jscomponents.enginespanel;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class EnginesPanelState extends JavaScriptComponentState {
    private static final long serialVersionUID = 1L;

    public int enginesNum;

    /* Configurations of engines from database */
    public boolean rpm[];// is rpm enabled on engine#1,2..
    public Float minrpm[];// min rpm on engine#1,2..
    public Float maxrpm[];// max rpm on engine#1,2..
    public Float rpmvals[];// rpm values that came from AWCom

    public boolean pwr[];
    public Float minpwr[];
    public Float maxpwr[];
    public Float pwrvals[];

    public boolean pwp[];
    public Float minpwp[];
    public Float maxpwp[];
    public Float pwpvals[];

    public boolean mp_[];
    public Float minmp_[];
    public Float maxmp_[];
    public Float mp_vals[];

    public boolean egt1[];
    public Float minegt1[];
    public Float maxegt1[];
    public Float egt1vals[];

    public boolean egt2[];
    public Float minegt2[];
    public Float maxegt2[];
    public Float egt2vals[];

    public boolean cht1[];
    public Float mincht1[];
    public Float maxcht1[];
    public Float cht1vals[];

    public boolean cht2[];
    public Float mincht2[];
    public Float maxcht2[];
    public Float cht2vals[];

    public boolean est[];
    public Float minest[];
    public Float maxest[];
    public Float estvals[];

    public boolean ff_[];
    public Float minff_[];
    public Float maxff_[];
    public Float ff_vals[];

    public boolean fp_[];
    public Float minfp_[];
    public Float maxfp_[];
    public Float fp_vals[];

    public boolean op_[];
    public Float minop_[];
    public Float maxop_[];
    public Float op_vals[];

    public boolean ot_[];
    public Float minot_[];
    public Float maxot_[];
    public Float ot_vals[];

    public boolean n1_[];
    public Float minn1_[];
    public Float maxn1_[];
    public Float n1_vals[];

    public boolean n2_[];
    public Float minn2_[];
    public Float maxn2_[];
    public Float n2_vals[];

    public boolean vib[];
    public Float minvib[];
    public Float maxvib[];
    public Float vibvals[];

    public boolean vlt[];
    public Float minvlt[];
    public Float maxvlt[];
    public Float vltvals[];

    public boolean amp[];
    public Float minamp[];
    public Float maxamp[];
    public Float ampvals[];

    // central fuel tank
    public boolean cfu;
    public Float mincfu;
    public Float maxcfu;
    public Float cfuvals;

    // left fuel tank
    public boolean lfu;
    public Float minlfu;
    public Float maxlfu;
    public Float lfuvals;

    // right fuel tank
    public boolean rfu;
    public Float minrfu;
    public Float maxrfu;
    public Float rfuvals;

    /**
     * Initialize arrays with specified size
     */
    public void initializeArrays(int numberOfEngines) {
	rpm = new boolean[numberOfEngines];
	minrpm = new Float[numberOfEngines];
	maxrpm = new Float[numberOfEngines];
	rpmvals = new Float[numberOfEngines];

	pwr = new boolean[numberOfEngines];
	minpwr = new Float[numberOfEngines];
	maxpwr = new Float[numberOfEngines];
	pwrvals = new Float[numberOfEngines];

	pwp = new boolean[numberOfEngines];
	minpwp = new Float[numberOfEngines];
	maxpwp = new Float[numberOfEngines];
	pwpvals = new Float[numberOfEngines];

	mp_ = new boolean[numberOfEngines];
	minmp_ = new Float[numberOfEngines];
	maxmp_ = new Float[numberOfEngines];
	mp_vals = new Float[numberOfEngines];

	egt1 = new boolean[numberOfEngines];
	minegt1 = new Float[numberOfEngines];
	maxegt1 = new Float[numberOfEngines];
	egt1vals = new Float[numberOfEngines];

	egt2 = new boolean[numberOfEngines];
	minegt2 = new Float[numberOfEngines];
	maxegt2 = new Float[numberOfEngines];
	egt2vals = new Float[numberOfEngines];

	cht1 = new boolean[numberOfEngines];
	mincht1 = new Float[numberOfEngines];
	maxcht1 = new Float[numberOfEngines];
	cht1vals = new Float[numberOfEngines];

	cht2 = new boolean[numberOfEngines];
	mincht2 = new Float[numberOfEngines];
	maxcht2 = new Float[numberOfEngines];
	cht2vals = new Float[numberOfEngines];

	est = new boolean[numberOfEngines];
	minest = new Float[numberOfEngines];
	maxest = new Float[numberOfEngines];
	estvals = new Float[numberOfEngines];

	ff_ = new boolean[numberOfEngines];
	minff_ = new Float[numberOfEngines];
	maxff_ = new Float[numberOfEngines];
	ff_vals = new Float[numberOfEngines];

	fp_ = new boolean[numberOfEngines];
	minfp_ = new Float[numberOfEngines];
	maxfp_ = new Float[numberOfEngines];
	fp_vals = new Float[numberOfEngines];

	op_ = new boolean[numberOfEngines];
	minop_ = new Float[numberOfEngines];
	maxop_ = new Float[numberOfEngines];
	op_vals = new Float[numberOfEngines];

	ot_ = new boolean[numberOfEngines];
	minot_ = new Float[numberOfEngines];
	maxot_ = new Float[numberOfEngines];
	ot_vals = new Float[numberOfEngines];

	n1_ = new boolean[numberOfEngines];
	minn1_ = new Float[numberOfEngines];
	maxn1_ = new Float[numberOfEngines];
	n1_vals = new Float[numberOfEngines];

	n2_ = new boolean[numberOfEngines];
	minn2_ = new Float[numberOfEngines];
	maxn2_ = new Float[numberOfEngines];
	n2_vals = new Float[numberOfEngines];

	vib = new boolean[numberOfEngines];
	minvib = new Float[numberOfEngines];
	maxvib = new Float[numberOfEngines];
	vibvals = new Float[numberOfEngines];

	vlt = new boolean[numberOfEngines];
	minvlt = new Float[numberOfEngines];
	maxvlt = new Float[numberOfEngines];
	vltvals = new Float[numberOfEngines];

	amp = new boolean[numberOfEngines];
	minamp = new Float[numberOfEngines];
	maxamp = new Float[numberOfEngines];
	ampvals = new Float[numberOfEngines];
    }
}
