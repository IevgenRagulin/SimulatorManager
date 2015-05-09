package cz.vutbr.fit.simulatormanager.jscomponents.enginesfuelpanel;

import com.vaadin.shared.ui.JavaScriptComponentState;

/**
 * This class is used for communicating state of fuel, engines between java and
 * javascript parts of the application
 * 
 * @author zhenia
 *
 */
public class EnginesAndFuelPanelState extends JavaScriptComponentState {
    private static final long serialVersionUID = 1L;

    public int enginesNum;

    /* Configurations of engines from database */
    public boolean rpm[];// is rpm enabled on engine#1,2..
    public Float minrpm[];// min rpm on engine#1,2..
    public Float lowrpm[];// min rpm on engine#1,2.. lowrpm-highrpm the
			  // indicator on the gauge is green
    public Float highrpm[];// min rpm on engine#1,2..highrpm-maxrpm the
			   // indicator on the gauge is red
    public Float maxrpm[];// max rpm on engine#1,2..
    public Float rpmvals[];// rpm values that came from AWCom

    public boolean pwr[];
    public Float minpwr[];
    public Float lowpwr[];
    public Float highpwr[];
    public Float maxpwr[];
    public Float pwrvals[];

    public boolean pwp[];
    public Float minpwp[];
    public Float lowpwp[];
    public Float highpwp[];
    public Float maxpwp[];
    public Float pwpvals[];

    public boolean mp_[];
    public Float minmp_[];
    public Float lowmp_[];
    public Float highmp_[];
    public Float maxmp_[];
    public Float mp_vals[];

    public boolean et1[];
    public Float minet1[];
    public Float lowet1[];
    public Float highet1[];
    public Float maxet1[];
    public Float et1vals[];

    public boolean et2[];
    public Float minet2[];
    public Float lowet2[];
    public Float highet2[];
    public Float maxet2[];
    public Float et2vals[];

    public boolean ct1[];
    public Float minct1[];
    public Float lowct1[];
    public Float highct1[];
    public Float maxct1[];
    public Float ct1vals[];

    public boolean ct2[];
    public Float minct2[];
    public Float lowct2[];
    public Float highct2[];
    public Float maxct2[];
    public Float ct2vals[];

    public boolean est[];
    public Float minest[];
    public Float lowest[];
    public Float highest[];
    public Float maxest[];
    public Float estvals[];

    public boolean ff_[];
    public Float minff_[];
    public Float lowff_[];
    public Float highff_[];
    public Float maxff_[];
    public Float ff_vals[];

    public boolean fp_[];
    public Float minfp_[];
    public Float lowfp_[];
    public Float highfp_[];
    public Float maxfp_[];
    public Float fp_vals[];

    public boolean op_[];
    public Float minop_[];
    public Float lowop_[];
    public Float highop_[];
    public Float maxop_[];
    public Float op_vals[];

    public boolean ot_[];
    public Float minot_[];
    public Float lowot_[];
    public Float highot_[];
    public Float maxot_[];
    public Float ot_vals[];

    public boolean n1_[];
    public Float minn1_[];
    public Float lown1_[];
    public Float highn1_[];
    public Float maxn1_[];
    public Float n1_vals[];

    public boolean n2_[];
    public Float minn2_[];
    public Float lown2_[];
    public Float highn2_[];
    public Float maxn2_[];
    public Float n2_vals[];

    public boolean vib[];
    public Float minvib[];
    public Float lowvib[];
    public Float highvib[];
    public Float maxvib[];
    public Float vibvals[];

    public boolean vlt[];
    public Float minvlt[];
    public Float lowvlt[];
    public Float highvlt[];
    public Float maxvlt[];
    public Float vltvals[];

    public boolean amp[];
    public Float minamp[];
    public Float lowamp[];
    public Float highamp[];
    public Float maxamp[];
    public Float ampvals[];

    // left fuel tank
    public boolean lfu;
    public Float minlfu;
    public Float lowlfu;
    public Float highlfu;
    public Float maxlfu;
    public Float lfuvals;

    // central fuel tank
    public boolean cfu;
    public Float mincfu;
    public Float lowcfu;
    public Float highcfu;
    public Float maxcfu;
    public Float cfuvals;

    // right fuel tank
    public boolean rfu;
    public Float minrfu;
    public Float lowrfu;
    public Float highrfu;
    public Float maxrfu;
    public Float rfuvals;

    /**
     * Initialize arrays with specified size
     */
    public void initializeArrays(int numberOfEngines) {
	rpm = new boolean[numberOfEngines];
	minrpm = new Float[numberOfEngines];
	lowrpm = new Float[numberOfEngines];
	highrpm = new Float[numberOfEngines];
	maxrpm = new Float[numberOfEngines];
	rpmvals = new Float[numberOfEngines];

	pwr = new boolean[numberOfEngines];
	minpwr = new Float[numberOfEngines];
	lowpwr = new Float[numberOfEngines];
	highpwr = new Float[numberOfEngines];
	maxpwr = new Float[numberOfEngines];
	pwrvals = new Float[numberOfEngines];

	pwp = new boolean[numberOfEngines];
	minpwp = new Float[numberOfEngines];
	lowpwp = new Float[numberOfEngines];
	highpwp = new Float[numberOfEngines];
	maxpwp = new Float[numberOfEngines];
	pwpvals = new Float[numberOfEngines];

	mp_ = new boolean[numberOfEngines];
	minmp_ = new Float[numberOfEngines];
	lowmp_ = new Float[numberOfEngines];
	highmp_ = new Float[numberOfEngines];
	maxmp_ = new Float[numberOfEngines];
	mp_vals = new Float[numberOfEngines];

	et1 = new boolean[numberOfEngines];
	minet1 = new Float[numberOfEngines];
	lowet1 = new Float[numberOfEngines];
	highet1 = new Float[numberOfEngines];
	maxet1 = new Float[numberOfEngines];
	et1vals = new Float[numberOfEngines];

	et2 = new boolean[numberOfEngines];
	minet2 = new Float[numberOfEngines];
	lowet2 = new Float[numberOfEngines];
	highet2 = new Float[numberOfEngines];
	maxet2 = new Float[numberOfEngines];
	et2vals = new Float[numberOfEngines];

	ct1 = new boolean[numberOfEngines];
	minct1 = new Float[numberOfEngines];
	lowct1 = new Float[numberOfEngines];
	highct1 = new Float[numberOfEngines];
	maxct1 = new Float[numberOfEngines];
	ct1vals = new Float[numberOfEngines];

	ct2 = new boolean[numberOfEngines];
	minct2 = new Float[numberOfEngines];
	lowct2 = new Float[numberOfEngines];
	highct2 = new Float[numberOfEngines];
	maxct2 = new Float[numberOfEngines];
	ct2vals = new Float[numberOfEngines];

	est = new boolean[numberOfEngines];
	minest = new Float[numberOfEngines];
	lowest = new Float[numberOfEngines];
	highest = new Float[numberOfEngines];
	maxest = new Float[numberOfEngines];
	estvals = new Float[numberOfEngines];

	ff_ = new boolean[numberOfEngines];
	minff_ = new Float[numberOfEngines];
	lowff_ = new Float[numberOfEngines];
	highff_ = new Float[numberOfEngines];
	maxff_ = new Float[numberOfEngines];
	ff_vals = new Float[numberOfEngines];

	fp_ = new boolean[numberOfEngines];
	minfp_ = new Float[numberOfEngines];
	lowfp_ = new Float[numberOfEngines];
	highfp_ = new Float[numberOfEngines];
	maxfp_ = new Float[numberOfEngines];
	fp_vals = new Float[numberOfEngines];

	op_ = new boolean[numberOfEngines];
	minop_ = new Float[numberOfEngines];
	lowop_ = new Float[numberOfEngines];
	highop_ = new Float[numberOfEngines];
	maxop_ = new Float[numberOfEngines];
	op_vals = new Float[numberOfEngines];

	ot_ = new boolean[numberOfEngines];
	minot_ = new Float[numberOfEngines];
	lowot_ = new Float[numberOfEngines];
	highot_ = new Float[numberOfEngines];
	maxot_ = new Float[numberOfEngines];
	ot_vals = new Float[numberOfEngines];

	n1_ = new boolean[numberOfEngines];
	minn1_ = new Float[numberOfEngines];
	lown1_ = new Float[numberOfEngines];
	highn1_ = new Float[numberOfEngines];
	maxn1_ = new Float[numberOfEngines];
	n1_vals = new Float[numberOfEngines];

	n2_ = new boolean[numberOfEngines];
	minn2_ = new Float[numberOfEngines];
	lown2_ = new Float[numberOfEngines];
	highn2_ = new Float[numberOfEngines];
	maxn2_ = new Float[numberOfEngines];
	n2_vals = new Float[numberOfEngines];

	vib = new boolean[numberOfEngines];
	minvib = new Float[numberOfEngines];
	lowvib = new Float[numberOfEngines];
	highvib = new Float[numberOfEngines];
	maxvib = new Float[numberOfEngines];
	vibvals = new Float[numberOfEngines];

	vlt = new boolean[numberOfEngines];
	minvlt = new Float[numberOfEngines];
	lowvlt = new Float[numberOfEngines];
	highvlt = new Float[numberOfEngines];
	maxvlt = new Float[numberOfEngines];
	vltvals = new Float[numberOfEngines];

	amp = new boolean[numberOfEngines];
	minamp = new Float[numberOfEngines];
	lowamp = new Float[numberOfEngines];
	highamp = new Float[numberOfEngines];
	maxamp = new Float[numberOfEngines];
	ampvals = new Float[numberOfEngines];

	// we need to set these values to 0 so that fuel gauges are visible even
	// if there are no values available, see
	// enginesAndFuelPanel.js#isFuelFeatureEnabled()
	lfuvals = 0.0f;
	rfuvals = 0.0f;
	cfuvals = 0.0f;
    }
}
