package cz.vutbr.fit.simulatormanager.database.columns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum EngineModelCols {

	enginemodelid("Engine model id"), //
	simulatormodelid("Simulator model id"), //
	enginemodelorder(
			"Engine id (AWCom)",
			"This value is very important: it is the id by which we can match this configuration and data which comes from AWCom plugin"), //
	rpm("RPM", Boolean.class), //
	minrpm("Min RPM"), //
	maxrpm("Max RPM"), //
	pwr("PWR - power", Boolean.class), //
	minpwr("Min PWR"), //
	maxpwr("Max PWR"), //
	pwp("PWP - power %", Boolean.class), //
	minpwp("Min PWP"), //
	maxpwp("Max PWP"), //
	mp("MP - Manifold pressure", Boolean.class), //
	minmp("Min MP"), //
	maxmp("Max MP"), //
	egt1("EGT1", Boolean.class), //
	minegt1("Min EGT1"), //
	maxegt1("Max EGT1"), //
	egt2("EGT2", Boolean.class), //
	minegt2("Min EGT2"), //
	maxegt2("Max EGT2"), //
	cht1("CHT1", Boolean.class), //
	mincht1("Min CHT1"), //
	maxcht1("Max CHT1"), //
	cht2("CHT2", Boolean.class), //
	mincht2("Min CHT2"), //
	maxcht2("Max CHT2"), //
	est("EST", Boolean.class), //
	minest("Min EST"), //
	maxest("Max EST"), //
	ff("FF - fuel flow", Boolean.class), //
	minff("Min FF"), //
	maxff("Max FF"), //
	fp("FP - fuel pressure", Boolean.class), //
	minfp("Min FP"), // ,
	maxfp("Max FP"), //
	op("OP - oil pressure", Boolean.class), //
	minop("Min op"), //
	maxop("Max op"), //
	ot("OT - oil temperature", Boolean.class), //
	minot("Min oil temperature"), //
	maxot("Max oil temperature"), //
	n1("N1", Boolean.class), //
	minn1("Min N1"), //
	maxn1("Max N1"), //
	n2("N2", Boolean.class), //
	minn2("Min N2"), //
	maxn2("Max N2"), //
	vib("VIB - vibration", Boolean.class), //
	minvib("Min VIB"), //
	maxvib("Max VIB"), //
	vlt("VLT - voltage", Boolean.class), //
	minvlt("Min VLT"), //
	maxvlt("Max VLT"), //
	amp("AMP - ampere", Boolean.class), //
	minamp("Min AMP"), //
	maxamp("Max AMP"), //
	timestamp("Timestamp");//

	final static Logger LOG = LoggerFactory.getLogger(EngineModelCols.class);

	private String name;
	private String description = "";
	// for boolean type is "boolean"
	private Class type = null;

	private EngineModelCols(String humanReadableName) {
		this.name = humanReadableName;
	}

	private EngineModelCols(String humanReadableName, String desc) {
		this.name = humanReadableName;
		this.description = desc;
	}

	private EngineModelCols(String humanReadableName, Class type) {
		this.name = humanReadableName;
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public Class getType() {
		return this.type;
	}

	public static String[] getSimulatorModelCols() {
		EngineModelCols[] cols = values();
		String[] strCols = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			strCols[i] = cols[i].toString();
		}
		return strCols;
	}

	public static String[] getSimulatorModelColsNames() {
		EngineModelCols[] cols = values();
		String[] strColsNames = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			LOG.debug("Getting simulator cols names" + cols[i].getName());
			strColsNames[i] = cols[i].getName();
		}
		return strColsNames;
	}

	public String getName() {
		return name;
	}

}
