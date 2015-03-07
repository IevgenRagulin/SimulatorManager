package cz.vutbr.fit.simulatormanager.database.columns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum SimulatorModelCols {

	simulatormodelid("Simulator model id"), //
	simulatormodelname("Simulator model name"), //
	minspeed("Min speed"), //
	maxspeed("Max speed"), //
	highspeed("High speed"), //
	maxspeedonflaps("Max speed on flaps"), //
	minspeedonflaps("Min speed on flaps"), //
	hasgears("Has gears"), //
	numberoflandinggears("Number of landing gears"), //
	timestamp("Timestamp");//

	final static Logger LOG = LoggerFactory.getLogger(SimulatorModelCols.class);

	private String name;
	private String description = "";

	private SimulatorModelCols(String humanReadableName) {
		this.name = humanReadableName;
	}

	private SimulatorModelCols(String humanReadableName, String desc) {
		this.name = humanReadableName;
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}

	public static String[] getSimulatorModelCols() {
		SimulatorModelCols[] cols = values();
		String[] strCols = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			strCols[i] = cols[i].toString();
		}
		return strCols;
	}

	public static String[] getSimulatorModelColsNames() {
		SimulatorModelCols[] cols = values();
		String[] strColsNames = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			LOG.debug("Getting simulator cols names" + cols[i].getName());
			strColsNames[i] = cols[i].getName();
		}
		return strColsNames;
	}

	public static String[] getSimulatorModelMainCols() {
		return new String[] { simulatormodelid.toString(), simulatormodelname.toString() };
	}

	public static String[] getSimulatorModelMainColsNames() {
		return new String[] { simulatormodelid.name, simulatormodelname.name };
	}

	public String getName() {
		return name;
	}

}
