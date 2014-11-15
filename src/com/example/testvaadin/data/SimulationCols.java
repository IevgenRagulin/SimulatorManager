package com.example.testvaadin.data;

public enum SimulationCols {
	simulationid, //
	simulator_simulatorid, //
	issimulationon, //
	issimulationpaused, //
	simulationstartedtime, //
	simulationendedtime;

	public static String[] getSimulationCols() {
		SimulationCols[] cols = values();
		String[] strCols = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			strCols[i] = cols[i].toString();
		}
		return strCols;
	}
}
