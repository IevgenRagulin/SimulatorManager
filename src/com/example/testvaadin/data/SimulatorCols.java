package com.example.testvaadin.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum SimulatorCols {

	active("Is simulator active", "If checked, Simulator Manager will try to fetch data from the simulator every X seconds"), //
	simulatorid("Simulator id"), // simulator.simulatorid
	simulatorname("Simulator name"), // simulator.simulatorname
	hostname("Hostname"), // simulator.hostname
	port("Port"), // simulator.port
	aircraftmodel("Aircraft model"), //
	minspeed("Min speed"), //
	maxspeed("Max speed"), //
	highspeed("High speed"), //
	maxspeedonflaps("Max speed on flaps"), //
	minspeedonflaps("Min speed on flaps"), //
	hasgears("Has gears"), //
	mintempcht1("Min temperature CHT1"), //
	mintempcht2("Min temperature CHT2"), //
	mintempegt1("Min temperature EGT1"), //
	mintempegt2("Min temperature EGT2"), //
	maxtempcht1("Max temperature CHT1"), //
	maxtempcht2("Max temperature CHT2"), //
	maxtempegt1("Max temperature EGT1"), //
	maxtempegt2("Max temperature EGT2"), //
	manifoldpressure("Manifold pressure"), //
	power("Power"), //
	maxamountoffuel("Max amount of fuel"), //
	minamountoffuel("Min amount of fuel"), //
	maxrpm("Max RPM"), //
	numberofengines("Number of engines"), //
	numberoflandinggears("Number of landing gears"), //
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
		return new String[] { active.toString(), simulatorid.toString(), simulatorname.toString(), aircraftmodel.toString() };
	}

	public static String[] getSimulatorMainColsNames() {
		return new String[] { active.name, simulatorid.name, simulatorname.name, aircraftmodel.name };
	}

	public String getName() {
		return name;
	}

}
