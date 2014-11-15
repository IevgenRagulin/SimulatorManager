package com.example.testvaadin.data;

public enum SimulationInfoCols {

	simulationinfoid, //
	simulation_simulationid, //
	longtitude, //
	latitude;//

	public static String[] getSimulationInfoCols() {
		SimulationInfoCols[] cols = values();
		String[] strCols = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			strCols[i] = cols[i].toString();
		}
		return strCols;
	}

}
