package com.example.testvaadin.data;

public class ColumnNames {

	private static final String SIMULATIONINFOID = "simulationinfoid";
	private static final String AILERONTRIM = "ailerontrim";
	private static final String ELEVATORTRIM = "elevatortrim";
	private static final String RUDDERTRIM = "ruddertrim";
	private static final String SPEEDBRAKES = "speedbrakes";
	private static final String FLAPS = "flaps";
	private static final String THROTTLE = "throttle";
	private static final String RUDDER = "rudder";
	private static final String AILERON = "eleron";
	private static final String ELEVATOR = "elevator";
	private static final String BRAKES = "brakes";
	private static final String LATITUDE = "latitude";
	private static final String LONGTITUDE = "longtitude";
	private static final String TIMESTAMP = "timestamp";
	private static final String VERTICALSPEED = "verticalspeed";
	private static final String GROUNDALTITUDE = "groundaltitude";
	private static final String ALTITUDE = "altitude";
	private static final String IAS = "ias";
	private static final String HEADING = "heading";
	private static final String TRUE_COURSE = "truecourse";
	private static final String PITCH = "pitch";
	private static final String ROLL = "roll";
	private static final String PFDINFOID = "pfdinfoid";
	protected static final String SIMULATORACTIVE = "active";
	private static final String LANDINGGEAR_1 = "landinggear_1";
	private static final String LANDINGGEAR_2 = "landinggear_2";
	private static final String LANDINGGEAR_3 = "landinggear_3";

	protected static final String SIMULATIONID_FOREIGN_KEY = "simulation_simulationid";

	protected static final String[] simulationBeanCols = new String[] { SimulationCols.issimulationon.toString(),
			SimulationCols.issimulationpaused.toString(), SimulationCols.simulationstartedtime.toString(),
			SimulationCols.simulationendedtime.toString() };

	protected static final String[] simulationInfoBeanCols = new String[] { LONGTITUDE, LATITUDE };

	protected static final String[] simulationInfoCols = new String[] { SIMULATIONINFOID, SIMULATIONID_FOREIGN_KEY, LONGTITUDE, LATITUDE };

	protected static final String[] simulationDevicesStateBeanCols = new String[] { ELEVATOR, AILERON, RUDDER, THROTTLE, FLAPS,
			SPEEDBRAKES, BRAKES, AILERONTRIM, ELEVATORTRIM, RUDDERTRIM };

	protected static final String[] simulationDevicesStateCols = new String[] { "devstateid", SIMULATIONID_FOREIGN_KEY, ELEVATOR, AILERON,
			RUDDER, THROTTLE, FLAPS, SPEEDBRAKES, AILERONTRIM, ELEVATORTRIM, RUDDERTRIM, TIMESTAMP, LANDINGGEAR_1, LANDINGGEAR_2,
			LANDINGGEAR_3 };

	protected static final String[] simulationBeanPfdInfo = new String[] { ROLL, PITCH, HEADING, TRUE_COURSE, IAS, ALTITUDE,
			GROUNDALTITUDE, VERTICALSPEED };

	protected static final String[] simulationPfdInfo = new String[] { PFDINFOID, SIMULATIONID_FOREIGN_KEY, ROLL, PITCH, HEADING,
			TRUE_COURSE, IAS, ALTITUDE, GROUNDALTITUDE, VERTICALSPEED };

	public static String getTrueCourse() {
		return TRUE_COURSE;
	}

	public static String[] getSimulatorMainCols() {
		return new String[] { SimulatorCols.simulatorid.toString(), SimulatorCols.simulatorname.toString(),
				SimulatorCols.aircraftmodel.toString() };
	}

	public static String[] getSimulationBeanCols() {
		return simulationBeanCols;
	}

	public static String getSimulationIdForeignKeyPropName() {
		return SIMULATIONID_FOREIGN_KEY;
	}

	public static String[] getSimulationDevicesStateBeanCols() {
		return simulationDevicesStateBeanCols;
	}

	public static String[] getSimulationDevicesStateCols() {
		return simulationDevicesStateCols;
	}

	public static String[] getSimulationInfoBeanCols() {
		return simulationInfoBeanCols;
	}

	public static String[] getSimulationInfoCols() {
		return simulationInfoCols;
	}

	public static String getTimestamp() {
		return TIMESTAMP;
	}

	public static String getVerticalspeed() {
		return VERTICALSPEED;
	}

	public static String getGroundaltitude() {
		return GROUNDALTITUDE;
	}

	public static String getAltitude() {
		return ALTITUDE;
	}

	public static String getIas() {
		return IAS;
	}

	public static String getHeading() {
		return HEADING;
	}

	public static String getPitch() {
		return PITCH;
	}

	public static String getRoll() {
		return ROLL;
	}

	public static String getPfdinfoid() {
		return PFDINFOID;
	}

	public static String getSimulationidForeignKey() {
		return SIMULATIONID_FOREIGN_KEY;
	}

	public static String[] getSimulationinfocols() {
		return simulationInfoCols;
	}

	public static String[] getSimulationdevicesstatecols() {
		return simulationDevicesStateCols;
	}

	public static String[] getSimulationBeanpfdinfo() {
		return simulationBeanPfdInfo;
	}

	public static String[] getSimulationpfdinfo() {
		return simulationPfdInfo;
	}

	public static String getLatitude() {
		return LATITUDE;
	}

	public static String getLongtitude() {
		return LONGTITUDE;
	}

	public static String getAileronTrim() {
		return AILERONTRIM;
	}

	public static String getElevatorTrim() {
		return ELEVATORTRIM;
	}

	public static String getRudderTrim() {
		return RUDDERTRIM;
	}

	public static String getSpeedbrakes() {
		return SPEEDBRAKES;
	}

	public static String getBrakes() {
		return BRAKES;
	}

	public static String getFlaps() {
		return FLAPS;
	}

	public static String getThrottle() {
		return THROTTLE;
	}

	public static String getRudder() {
		return RUDDER;
	}

	public static String getAileron() {
		return AILERON;
	}

	public static String getElevator() {
		return ELEVATOR;
	}

	public static String getSimulationinfoid() {
		return SIMULATIONINFOID;
	}

	public static String getLandinggear1() {
		return LANDINGGEAR_1;
	}

	public static String getLandinggear2() {
		return LANDINGGEAR_2;
	}

	public static String getLandinggear3() {
		return LANDINGGEAR_3;
	}

}
