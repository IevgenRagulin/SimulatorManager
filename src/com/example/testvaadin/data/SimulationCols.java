package com.example.testvaadin.data;

public enum SimulationCols {
	simulationid("Simulation id"), //
	simulator_simulatorid("Simulator id"), //
	issimulationon("Is on"), //
	issimulationpaused("Is paused"), //
	simulationstartedtime("Started time"), //
	simulationendedtime("Ended time"), //
	timestamp("Timestamp");

	private String name;

	private SimulationCols(String humanReadableName) {
		this.name = humanReadableName;
	}

	public static String[] getSimulationColsNames() {
		SimulationCols[] cols = values();
		String[] strColsNames = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			strColsNames[i] = cols[i].name;
		}
		return strColsNames;
	}

	public static String[] getSimulationCols() {
		SimulationCols[] cols = values();
		String[] strCols = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			strCols[i] = cols[i].toString();
		}
		return strCols;
	}

	public static String[] getVisibleCols() {
		return new String[] { simulationid.toString(), simulator_simulatorid.toString(), issimulationon.toString(),
				issimulationpaused.toString(), simulationstartedtime.toString() };
	}
}
