package cz.vutbr.fit.simulatormanager.database.columns;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum EngineModelCols {

    enginemodelid("Engine model id"), //
    simulatormodelid("Simulator model id"), //
    enginemodelorder("Engine id (AWCom)",
	    "This value is very important: it is the id by which we can match this configuration and data which comes from AWCom plugin"), //
    rpm("RPM", Boolean.class), //
    minrpm("Min RPM"), //
    lowrpm("Low RPM"), //
    highrpm("High RPM"), //
    maxrpm("Max RPM"), //
    pwr("PWR", Boolean.class), //
    minpwr("Min PWR"), //
    lowpwr("Low PWR"), //
    highpwr("High PWR"), //
    maxpwr("Max PWR"), //
    pwp("PWP", Boolean.class), //
    minpwp("Min PWP"), //
    lowpwp("Low PWP"), //
    highpwp("High PWP"), //
    maxpwp("Max PWP"), //
    mp_("MP", Boolean.class), //
    minmp("Min MP"), //
    lowmp("Low MP"), //
    highmp("High MP"), //
    maxmp("Max MP"), //
    et1("EGT1", Boolean.class), //
    minet1("Min EGT1"), //
    lowet1("Low EGT1"), //
    highet1("High EGT1"), //
    maxet1("Max EGT1"), //
    et2("EGT2", Boolean.class), //
    minet2("Min EGT2"), //
    lowet2("Low EGT2"), //
    highet2("High EGT2"), //
    maxet2("Max EGT2"), //
    ct1("CHT1", Boolean.class), //
    minct1("Min CHT1"), //
    lowct1("Low CHT1"), //
    highct1("High CHT1"), //
    maxct1("Max CHT1"), //
    ct2("CHT2", Boolean.class), //
    minct2("Min CHT2"), //
    lowct2("Low CHT2"), //
    highct2("High CHT2"), //
    maxct2("Max CHT2"), //
    est("EST", Boolean.class), //
    minest("Min EST"), //
    lowest("Low EST"), //
    highest("High EST"), //
    maxest("Max EST"), //
    ff_("FF", Boolean.class), //
    minff("Min FF"), //
    lowff("Low FF"), //
    highff("High FF"), //
    maxff("Max FF"), //
    fp_("FP", Boolean.class), //
    minfp("Min FP"), // ,
    lowfp("Low FP"), //
    highfp("High FP"), //
    maxfp("Max FP"), //
    op_("OP", Boolean.class), //
    minop("Min OP"), //
    lowop("Low OP"), //
    highop("High OP"), //
    maxop("Max OP"), //
    ot_("OT", Boolean.class), //
    minot("Min OT"), //
    lowot("Low OT"), //
    highot("High OT"), //
    maxot("Max OT"), //
    n1_("N1", Boolean.class), //
    minn1("Min N1"), //
    lown1("Low N1"), //
    highn1("High N1"), //
    maxn1("Max N1"), //
    n2_("N2", Boolean.class), //
    minn2("Min N2"), //
    lown2("Low N2"), //
    highn2("High N2"), //
    maxn2("Max N2"), //
    vib("VIB", Boolean.class), //
    minvib("Min VIB"), //
    lowvib("Low VIB"), //
    highvib("High VIB"), //
    maxvib("Max VIB"), //
    vlt("VLT", Boolean.class), //
    minvlt("Min VLT"), //
    lowvlt("Low VLT"), //
    highvlt("High VLT"), //
    maxvlt("Max VLT"), //
    amp("AMP", Boolean.class), //
    minamp("Min AMP"), //
    lowamp("Low AMP"), //
    highamp("High AMP"), //
    maxamp("Max AMP"), //
    timestamp("Timestamp");//

    final static Logger LOG = LoggerFactory.getLogger(EngineModelCols.class);

    private String name;
    private String description = "";
    // for boolean type is "boolean"
    @SuppressWarnings("rawtypes")
    private Class type = null;

    private EngineModelCols(String humanReadableName) {
	this.name = humanReadableName;
    }

    private EngineModelCols(String humanReadableName, String desc) {
	this.name = humanReadableName;
	this.description = desc;
    }

    @SuppressWarnings("rawtypes")
    private EngineModelCols(String humanReadableName, Class type) {
	this.name = humanReadableName;
	this.type = type;
    }

    public String getDescription() {
	return description;
    }

    @SuppressWarnings("rawtypes")
    public Class getType() {
	return this.type;
    }

    public static String[] getEngineModelCols() {
	EngineModelCols[] cols = values();
	String[] strCols = new String[cols.length];
	for (int i = 0; i < cols.length; i++) {
	    strCols[i] = cols[i].toString();
	}
	return strCols;
    }

    public static String[] getEngineModelColsNames() {
	EngineModelCols[] cols = values();
	String[] strColsNames = new String[cols.length];
	for (int i = 0; i < cols.length; i++) {
	    LOG.debug("Getting engine model col names" + cols[i].getName());
	    strColsNames[i] = cols[i].getName();
	}
	return strColsNames;
    }

    /**
     * Gets the names of the columns which describe if specific feature on this
     * engine is enabled or disabled. In other words, returns only boolean
     * columns
     * 
     * @return
     */
    public static List<EngineModelCols> getEngineModelConfigurationCols() {
	List<EngineModelCols> engineModelCols = new ArrayList<EngineModelCols>();
	for (EngineModelCols column : values()) {
	    if (column.getType() != null && column.getType() == Boolean.class) {
		engineModelCols.add(column);
	    }
	}
	return engineModelCols;
    }

    public String getName() {
	return name;
    }

}
