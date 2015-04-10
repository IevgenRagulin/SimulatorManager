package cz.vutbr.fit.simulatormanager.jscomponents.enginespanel;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class EnginesPanelState extends JavaScriptComponentState {
    private static final long serialVersionUID = 1L;

    /* Configurations of engines from database */
    public boolean rpm[];// is rpm enabled on engine#1,2..
    public Float minrpm[];// min rpm on engine#1,2..
    public Float maxrpm[];// max rpm on engine#1,2..

    public boolean pwr[];
    public Float minpwr[];
    public Float maxpwr[];

    public boolean pwp[];
    public Float minpwp[];
    public Float maxpwp[];

    public boolean mp_[];
    public Float minmp_[];
    public Float maxmp_[];

    public boolean egt1[];
    public Float minegt1[];
    public Float maxegt1[];

    public boolean egt2[];
    public Float minegt2[];
    public Float maxegt2[];

    public boolean cht1[];
    public Float mincht1[];
    public Float maxcht1[];

    public boolean cht2[];
    public Float mincht2[];
    public Float maxcht2[];

    public boolean est[];
    public Float minest[];
    public Float maxest[];

    public boolean ff_[];
    public Float minff_[];
    public Float maxff_[];

    public boolean fp_[];
    public Float minfp_[];
    public Float maxfp_[];

    public boolean op_[];
    public Float minop_[];
    public Float maxop_[];

    public boolean ot_[];
    public Float minot_[];
    public Float maxot_[];

    public boolean n1_[];
    public Float minn1_[];
    public Float maxn1_[];

    public boolean n2_[];
    public Float minn2_[];
    public Float maxn2_[];

    public boolean vib[];
    public Float minvib[];
    public Float maxvib[];

    public boolean vlt[];
    public Float minvlt[];
    public Float maxvlt[];

    public boolean amp[];
    public Float minamp[];
    public Float maxamp[];

    /**
     * Initialize arrays with specified size
     */
    public void initializeArrays(int numberOfEngines) {
	rpm = new boolean[numberOfEngines];
	minrpm = new Float[numberOfEngines];
	maxrpm = new Float[numberOfEngines];

	pwr = new boolean[numberOfEngines];
	minpwr = new Float[numberOfEngines];
	maxpwr = new Float[numberOfEngines];

	pwp = new boolean[numberOfEngines];
	minpwp = new Float[numberOfEngines];
	maxpwp = new Float[numberOfEngines];

	mp_ = new boolean[numberOfEngines];
	minmp_ = new Float[numberOfEngines];
	maxmp_ = new Float[numberOfEngines];

	egt1 = new boolean[numberOfEngines];
	minegt1 = new Float[numberOfEngines];
	maxegt1 = new Float[numberOfEngines];

	egt2 = new boolean[numberOfEngines];
	minegt2 = new Float[numberOfEngines];
	maxegt2 = new Float[numberOfEngines];

	cht1 = new boolean[numberOfEngines];
	mincht1 = new Float[numberOfEngines];
	maxcht1 = new Float[numberOfEngines];

	cht2 = new boolean[numberOfEngines];
	mincht2 = new Float[numberOfEngines];
	maxcht2 = new Float[numberOfEngines];

	est = new boolean[numberOfEngines];
	minest = new Float[numberOfEngines];
	maxest = new Float[numberOfEngines];

	ff_ = new boolean[numberOfEngines];
	minff_ = new Float[numberOfEngines];
	maxff_ = new Float[numberOfEngines];

	fp_ = new boolean[numberOfEngines];
	minfp_ = new Float[numberOfEngines];
	maxfp_ = new Float[numberOfEngines];

	op_ = new boolean[numberOfEngines];
	minop_ = new Float[numberOfEngines];
	maxop_ = new Float[numberOfEngines];

	ot_ = new boolean[numberOfEngines];
	minot_ = new Float[numberOfEngines];
	maxot_ = new Float[numberOfEngines];

	n1_ = new boolean[numberOfEngines];
	minn1_ = new Float[numberOfEngines];
	maxn1_ = new Float[numberOfEngines];

	n2_ = new boolean[numberOfEngines];
	minn2_ = new Float[numberOfEngines];
	maxn2_ = new Float[numberOfEngines];

	vib = new boolean[numberOfEngines];
	minvib = new Float[numberOfEngines];
	maxvib = new Float[numberOfEngines];

	vlt = new boolean[numberOfEngines];
	minvlt = new Float[numberOfEngines];
	maxvlt = new Float[numberOfEngines];

	amp = new boolean[numberOfEngines];
	minamp = new Float[numberOfEngines];
	maxamp = new Float[numberOfEngines];
    }
}
