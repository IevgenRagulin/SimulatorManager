package cz.vutbr.fit.simulatormanager.database.columns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum EngineCols {

    enginestateid("Engine state id"), //
    simulationid("Simulation id"), //
    engines_num("Number of engines"), //
    rpm("RPM"), //
    pwr("PWR - power"), //
    pwp("PWP - power %"), //
    mp_("MP - Manifold pressure"), //
    et1("EGT1"), //
    et2("EGT2"), //
    ct1("CHT1"), //
    ct2("CHT2"), //
    est("EST"), //
    ff_("FF - fuel flow"), //
    fp_("FP - fuel pressure"), //
    op_("OP - oil pressure"), //
    ot_("OT - oil temperature"), //
    n1_("N1"), //
    n2_("N2"), //
    vib("VIB - vibration"), //
    vlt("VLT - voltage"), //
    amp("AMP - ampere"), //
    timestamp("Timestamp");//

    final static Logger LOG = LoggerFactory.getLogger(EngineCols.class);

    private String name;

    private EngineCols(String humanReadableName) {
	this.name = humanReadableName;
    }

    public static String[] getEngineCols() {
	EngineCols[] cols = values();
	String[] strCols = new String[cols.length];
	for (int i = 0; i < cols.length; i++) {
	    strCols[i] = cols[i].toString();
	}
	return strCols;
    }

    public static String[] getEngineColsNames() {
	EngineCols[] cols = values();
	String[] strColsNames = new String[cols.length];
	for (int i = 0; i < cols.length; i++) {
	    LOG.debug("Getting engine col names" + cols[i].getName());
	    strColsNames[i] = cols[i].getName();
	}
	return strColsNames;
    }

    public String getName() {
	return name;
    }

}
