package cz.vutbr.fit.simulatormanager.database.columns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum SimulatorCols {

    active("Is simulator active",
	    "If checked, Simulator Manager will try to fetch data from the simulator every X seconds"), //
    simulatorid("Simulator id"), // simulator.simulatorid
    simulatormodelid("Simulator model"), // simulator.simulatormodelid
    simulatorname("Simulator name"), // simulator.simulatorname
    hostname("Hostname"), // simulator.hostname
    port("Port"), // simulator.port
    timestamp("Timestamp");

    final static Logger logger = LoggerFactory.getLogger(SimulatorCols.class);

    private String name;
    private String description = "";

    private SimulatorCols(String humanReadableName) {
	this.name = humanReadableName;
    }

    private SimulatorCols(String humanReadableName, String desc) {
	this.name = humanReadableName;
	this.description = desc;
    }

    public String getDescription() {
	return description;
    }

    public static String[] getSimulatorCols() {
	SimulatorCols[] cols = values();
	String[] strCols = new String[cols.length];
	for (int i = 0; i < cols.length; i++) {
	    strCols[i] = cols[i].toString();
	}
	return strCols;
    }

    public static String[] getSimulatorColsNames() {
	SimulatorCols[] cols = values();
	String[] strColsNames = new String[cols.length];
	for (int i = 0; i < cols.length; i++) {
	    logger.info("Getting simulator cols names" + cols[i].getName());
	    strColsNames[i] = cols[i].getName();
	}
	return strColsNames;
    }

    public static String[] getSimulatorMainCols() {
	return new String[] { active.toString(), simulatorid.toString(), simulatorname.toString() };
    }

    public static String[] getSimulatorMainColsNames() {
	return new String[] { active.name, simulatorid.name, simulatorname.name };
    }

    public String getName() {
	return name;
    }

}
