package com.example.testvaadin.data;

public class ColumnNames {
	protected static final String SIMNAME = "SimulatorName";
	protected static final String PLANESIM = "AircraftModel";
	protected static final String SIMID = "SimulatorId";
	protected static final String PORT = "Port";
	protected static final String HOSTNAME = "Hostname";

	protected static final String[] simulatorCols = new String[] { SIMNAME,
			PLANESIM, SIMID, HOSTNAME, PORT, "MinSpeed", "MaxSpeed",
			"HighSpeed", "MaxSpeedOnFlaps", "MinSpeedOnFlaps", "HasGears",
			"MinTempCHT1", "MinTempCHT2", "MinTempEGT1", "MinTempEGT2",
			"MaxTempCHT1", "MaxTempCHT2", "MaxTempEGT1", "MaxTempEGT2",
			"ManifoldPressure", "Power", "MaxAmountOfFuel", "MinAmountOfFuel",
			"MaxRPM", "NumberOfEngines" };

	protected static final String SIMULATORID_FOREIGN_KEY = "Simulator_SimulatorId";

	protected static final String[] simulationCols = new String[] {
			"SimulationId", SIMULATORID_FOREIGN_KEY, "IsSimulationOn",
			"IsSimulationPaused", "SimulationStartedTime",
			"SimulationEndedTime" };

	protected static final String[] simulationInfoCols = new String[] {
			"SimulationInfoId", "Simulation_SimulationId", "BrakesOn",
			"FlapsOn", "Longtitude", "Latitude" };

	protected static final String[] simulationDevicesStateCols = new String[] {
			"DevStateId", "Simulation_SimulationId", "Elevator", "Eleron",
			"Rudder", "Throttle", "Flaps", "SpeedBrakes", "Trim", "Timestamp" };

	public static String[] getSimulatorMainCols() {
		return new String[] { SIMID, SIMNAME, PLANESIM };
	}

	public static String[] getSimulatorCols() {
		return simulatorCols;
	}

	public static String[] getSimulationCols() {
		return simulationCols;
	}

	public static String getSimulatorNamePropName() {
		return SIMNAME;
	}

	public static String getSimulatorPortName() {
		return PORT;
	}

	public static String getSimulatorHostname() {
		return HOSTNAME;
	}

	public static String getSimulatorIdForeignKeyPropName() {
		return SIMULATORID_FOREIGN_KEY;
	}

	public static String[] getSimulationDevicesStateCols() {
		return simulationDevicesStateCols;
	}

	public static String[] getSimulationInfoCols() {
		return simulationInfoCols;
	}
}
