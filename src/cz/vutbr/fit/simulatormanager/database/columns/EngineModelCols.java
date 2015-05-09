package cz.vutbr.fit.simulatormanager.database.columns;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vutbr.fit.simulatormanager.Constants;

/**
 * Database column names for table enginemodel
 * 
 * @author zhenia
 *
 */
public enum EngineModelCols {

    enginemodelid("Engine model id"), //
    simulatormodelid("Simulator model id"), //
    enginemodelorder("Engine id (AWCom)",
	    "This value is very important: it is the id by which we can match this configuration and data which comes from AWCom plugin"), //
    rpm("RPM" + Constants.RPM_UNIT, Boolean.class), //
    minrpm("Min RPM" + Constants.RPM_UNIT), //
    lowrpm("Low RPM" + Constants.RPM_UNIT), //
    highrpm("High RPM" + Constants.RPM_UNIT), //
    maxrpm("Max RPM" + Constants.RPM_UNIT), //
    pwr("PWR" + Constants.KWATT, Boolean.class), //
    minpwr("Min PWR" + Constants.KWATT), //
    lowpwr("Low PWR" + Constants.KWATT), //
    highpwr("High PWR" + Constants.KWATT), //
    maxpwr("Max PWR" + Constants.KWATT), //
    pwp("PWP" + Constants.PERCENT_UNIT, Boolean.class), //
    minpwp("Min PWP" + Constants.PERCENT_UNIT), //
    lowpwp("Low PWP" + Constants.PERCENT_UNIT), //
    highpwp("High PWP" + Constants.PERCENT_UNIT), //
    maxpwp("Max PWP" + Constants.PERCENT_UNIT), //
    mp_("MP" + Constants.KPASCAL, Boolean.class), //
    minmp("Min MP" + Constants.KPASCAL), //
    lowmp("Low MP" + Constants.KPASCAL), //
    highmp("High MP" + Constants.KPASCAL), //
    maxmp("Max MP" + Constants.KPASCAL), //
    et1("EGT1" + Constants.DEGREE_CELSIUS, Boolean.class), //
    minet1("Min EGT1" + Constants.DEGREE_CELSIUS), //
    lowet1("Low EGT1" + Constants.DEGREE_CELSIUS), //
    highet1("High EGT1" + Constants.DEGREE_CELSIUS), //
    maxet1("Max EGT1" + Constants.DEGREE_CELSIUS), //
    et2("EGT2" + Constants.DEGREE_CELSIUS, Boolean.class), //
    minet2("Min EGT2" + Constants.DEGREE_CELSIUS), //
    lowet2("Low EGT2" + Constants.DEGREE_CELSIUS), //
    highet2("High EGT2" + Constants.DEGREE_CELSIUS), //
    maxet2("Max EGT2" + Constants.DEGREE_CELSIUS), //
    ct1("CHT1" + Constants.DEGREE_CELSIUS, Boolean.class), //
    minct1("Min CHT1" + Constants.DEGREE_CELSIUS), //
    lowct1("Low CHT1" + Constants.DEGREE_CELSIUS), //
    highct1("High CHT1" + Constants.DEGREE_CELSIUS), //
    maxct1("Max CHT1" + Constants.DEGREE_CELSIUS), //
    ct2("CHT2" + Constants.DEGREE_CELSIUS, Boolean.class), //
    minct2("Min CHT2" + Constants.DEGREE_CELSIUS), //
    lowct2("Low CHT2" + Constants.DEGREE_CELSIUS), //
    highct2("High CHT2" + Constants.DEGREE_CELSIUS), //
    maxct2("Max CHT2" + Constants.DEGREE_CELSIUS), //
    est("EST" + Constants.DEGREE_CELSIUS, Boolean.class), //
    minest("Min EST" + Constants.DEGREE_CELSIUS), //
    lowest("Low EST" + Constants.DEGREE_CELSIUS), //
    highest("High EST" + Constants.DEGREE_CELSIUS), //
    maxest("Max EST" + Constants.DEGREE_CELSIUS), //
    ff_("FF" + Constants.LITER_PER_HOUR, Boolean.class), //
    minff("Min FF" + Constants.LITER_PER_HOUR), //
    lowff("Low FF" + Constants.LITER_PER_HOUR), //
    highff("High FF" + Constants.LITER_PER_HOUR), //
    maxff("Max FF" + Constants.LITER_PER_HOUR), //
    fp_("FP" + Constants.KPASCAL, Boolean.class), //
    minfp("Min FP" + Constants.KPASCAL), // ,
    lowfp("Low FP" + Constants.KPASCAL), //
    highfp("High FP" + Constants.KPASCAL), //
    maxfp("Max FP" + Constants.KPASCAL), //
    op_("OP" + Constants.KPASCAL, Boolean.class), //
    minop("Min OP" + Constants.KPASCAL), //
    lowop("Low OP" + Constants.KPASCAL), //
    highop("High OP" + Constants.KPASCAL), //
    maxop("Max OP" + Constants.KPASCAL), //
    ot_("OT" + Constants.DEGREE_CELSIUS, Boolean.class), //
    minot("Min OT" + Constants.DEGREE_CELSIUS), //
    lowot("Low OT" + Constants.DEGREE_CELSIUS), //
    highot("High OT" + Constants.DEGREE_CELSIUS), //
    maxot("Max OT" + Constants.DEGREE_CELSIUS), //
    n1_("N1" + Constants.PERCENT_UNIT, Boolean.class), //
    minn1("Min N1" + Constants.PERCENT_UNIT), //
    lown1("Low N1" + Constants.PERCENT_UNIT), //
    highn1("High N1" + Constants.PERCENT_UNIT), //
    maxn1("Max N1" + Constants.PERCENT_UNIT), //
    n2_("N2" + Constants.PERCENT_UNIT, Boolean.class), //
    minn2("Min N2" + Constants.PERCENT_UNIT), //
    lown2("Low N2" + Constants.PERCENT_UNIT), //
    highn2("High N2" + Constants.PERCENT_UNIT), //
    maxn2("Max N2" + Constants.PERCENT_UNIT), //
    vib("VIB", Boolean.class), //
    minvib("Min VIB"), //
    lowvib("Low VIB"), //
    highvib("High VIB"), //
    maxvib("Max VIB"), //
    vlt("VLT" + Constants.VLT_UNIT, Boolean.class), //
    minvlt("Min VLT" + Constants.VLT_UNIT), //
    lowvlt("Low VLT" + Constants.VLT_UNIT), //
    highvlt("High VLT" + Constants.VLT_UNIT), //
    maxvlt("Max VLT" + Constants.VLT_UNIT), //
    amp("AMP" + Constants.AMP_UNIT, Boolean.class), //
    minamp("Min AMP" + Constants.AMP_UNIT), //
    lowamp("Low AMP" + Constants.AMP_UNIT), //
    highamp("High AMP" + Constants.AMP_UNIT), //
    maxamp("Max AMP" + Constants.AMP_UNIT), //
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
