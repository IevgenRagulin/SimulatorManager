package com.example.testvaadin.data;

public class ColumnNames {
	private static final String SIMULATIONENDEDTIME = "simulationendedtime";
	private static final String SIMULATIONSTARTEDTIME = "simulationstartedtime";
	private static final String ISSIMULATIONPAUSED = "issimulationpaused";
	private static final String ISSIMULATIONON = "issimulationon";
	private static final String LATITUDE = "latitude";
	private static final String LONGTITUDE = "longtitude";
	private static final String TIMESTAMP = "timestamp";
	private static final String COMPASS = "compass";
	private static final String VERTICALSPEED = "verticalspeed";
	private static final String GROUNDALTITUDE = "groundaltitude";
	private static final String ALTITUDE = "altitude";
	private static final String IAS = "ias";
	private static final String YAW = "yaw";
	private static final String PITCH = "pitch";
	private static final String ROLL = "roll";
	private static final String PFDINFOID = "pfdinfoid";
	protected static final String SIMNAME = "simulatorname";
	protected static final String PLANESIM = "aircraftmodel";
	protected static final String SIMID = "simulatorid";
	protected static final String PORT = "port";
	protected static final String HOSTNAME = "hostname";

	protected static final String[] simulatorCols = new String[] { SIMNAME,
			PLANESIM, SIMID, HOSTNAME, PORT, "minspeed", "maxspeed",
			"highspeed", "maxspeedonflaps", "minspeedonflaps", "hasgears",
			"mintempcht1", "mintempcht2", "mintempegt1", "mintempegt2",
			"maxtempcht1", "maxtempcht2", "maxtempegt1", "maxtempegt2",
			"manifoldpressure", "power", "maxamountoffuel", "minamountoffuel",
			"maxrpm", "numberofengines" };

	protected static final String SIMULATORID_FOREIGN_KEY = "simulator_simulatorid";
	protected static final String SIMULATIONID_FOREIGN_KEY = "simulation_simulationid";

	protected static final String[] simulationCols = new String[] {
			"simulationid", SIMULATORID_FOREIGN_KEY, ISSIMULATIONON,
			ISSIMULATIONPAUSED, SIMULATIONSTARTEDTIME, SIMULATIONENDEDTIME };

	protected static final String[] simulationBeanCols = new String[] {
			ISSIMULATIONON, ISSIMULATIONPAUSED, SIMULATIONSTARTEDTIME,
			SIMULATIONENDEDTIME };

	protected static final String[] simulationInfoCols = new String[] {
			"simulationinfoid", SIMULATIONID_FOREIGN_KEY, "brakeson",
			"flapson", LONGTITUDE, LATITUDE };

	protected static final String[] simulationDevicesStateCols = new String[] {
			"devstateid", SIMULATIONID_FOREIGN_KEY, "elevator", "eleron",
			"rudder", "throttle", "flaps", "speedbrakes", "trim", TIMESTAMP };

	protected static final String[] simulationPfdInfo = new String[] {
			PFDINFOID, SIMULATIONID_FOREIGN_KEY, ROLL, PITCH, YAW, IAS,
			ALTITUDE, GROUNDALTITUDE, VERTICALSPEED, COMPASS, TIMESTAMP };

	public static String getSimulationendedtime() {
		return SIMULATIONENDEDTIME;
	}

	public static String getSimulationstartedtime() {
		return SIMULATIONSTARTEDTIME;
	}

	public static String getIssimulationpaused() {
		return ISSIMULATIONPAUSED;
	}

	public static String getIssimulationon() {
		return ISSIMULATIONON;
	}

	public static String[] getSimulatorMainCols() {
		return new String[] { SIMID, SIMNAME, PLANESIM };
	}

	public static String[] getSimulatorCols() {
		return simulatorCols;
	}

	public static String getSimulatorIdPropName() {
		return SIMID;
	}

	public static String[] getSimulationCols() {
		return simulationCols;
	}

	public static String[] getSimulationBeanCols() {
		return simulationBeanCols;
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

	public static String getSimulationIdForeignKeyPropName() {
		return SIMULATIONID_FOREIGN_KEY;
	}

	public static String[] getSimulationDevicesStateCols() {
		return simulationDevicesStateCols;
	}

	public static String[] getSimulationInfoCols() {
		return simulationInfoCols;
	}

	public static String getTimestamp() {
		return TIMESTAMP;
	}

	public static String getCompass() {
		return COMPASS;
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

	public static String getYaw() {
		return YAW;
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

	public static String getSimname() {
		return SIMNAME;
	}

	public static String getPlanesim() {
		return PLANESIM;
	}

	public static String getSimid() {
		return SIMID;
	}

	public static String getPort() {
		return PORT;
	}

	public static String getHostname() {
		return HOSTNAME;
	}

	public static String[] getSimulatorcols() {
		return simulatorCols;
	}

	public static String getSimulatoridForeignKey() {
		return SIMULATORID_FOREIGN_KEY;
	}

	public static String getSimulationidForeignKey() {
		return SIMULATIONID_FOREIGN_KEY;
	}

	public static String[] getSimulationcols() {
		return simulationCols;
	}

	public static String[] getSimulationinfocols() {
		return simulationInfoCols;
	}

	public static String[] getSimulationdevicesstatecols() {
		return simulationDevicesStateCols;
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
}
