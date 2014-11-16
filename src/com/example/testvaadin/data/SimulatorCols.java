package com.example.testvaadin.data;

public enum SimulatorCols {
	simulatorid, // simulator.simulatorid
	simulatorname, // simulator.simulatorname
	hostname, // simulator.hostname
	port, // simulator.port
	aircraftmodel, //
	minspeed, //
	maxspeed, //
	highspeed, //
	maxspeedonflaps, //
	minspeedonflaps, //
	hasgears, //
	mintempcht1, //
	mintempcht2, //
	mintempegt1, //
	mintempegt2, //
	maxtempcht1, //
	maxtempcht2, //
	maxtempegt1, //
	maxtempegt2, //
	manifoldpressure, //
	power, //
	maxamountoffuel, //
	minamountoffuel, //
	maxrpm, //
	numberofengines, //
	numberoflandinggears, //
	active;//

	public static String[] getSimulatorCols() {
		SimulatorCols[] cols = values();
		String[] strCols = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			strCols[i] = cols[i].toString();
		}
		return strCols;
	}

	public static String[] getSimulatorMainCols() {
		return new String[] { simulatorid.toString(), simulatorname.toString(), aircraftmodel.toString() };
	}
}
