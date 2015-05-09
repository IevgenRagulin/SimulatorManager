package cz.vutbr.fit.simulatormanager.database.columns;

/**
 * Column names for table simulationinfo
 * 
 * @author zhenia
 *
 */
public enum SimulationInfoCols {

    simulationinfoid, //
    simulationid, //
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
