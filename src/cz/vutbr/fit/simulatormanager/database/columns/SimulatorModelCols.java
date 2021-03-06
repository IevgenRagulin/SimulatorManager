package cz.vutbr.fit.simulatormanager.database.columns;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vutbr.fit.simulatormanager.Constants;

/**
 * Column names simulatormodel
 * 
 * @author zhenia
 *
 */
public enum SimulatorModelCols {

    simulatormodelid("Simulator model id"), //
    simulatormodelname("Simulator model name"), //
    minspeed("Min speed" + Constants.KM_PER_HOUR), //
    maxspeed("Max speed" + Constants.KM_PER_HOUR), //
    maxspeedonflaps("Max speed on flaps" + Constants.KM_PER_HOUR), //
    minspeedonflaps("Min speed on flaps" + Constants.KM_PER_HOUR), //
    hasgears("Has gears", Boolean.class), //
    numberoflandinggears("Number of landing gears"), //
    lfu("Left fuel tank" + Constants.LITER, Boolean.class), //
    minlfu("Minimum amount of fuel in left tank" + Constants.LITER, Float.class), //
    lowlfu("Low LFU" + Constants.LITER, Float.class), //
    highlfu("High LFU" + Constants.LITER, Float.class), //
    maxlfu("Maximum amount of fuel in left tank" + Constants.LITER, Float.class), //
    cfu("Central fuel tank" + Constants.LITER, Boolean.class), //
    mincfu("Minimum amount of fuel in central tank" + Constants.LITER, Float.class), //
    lowcfu("Low CFU" + Constants.LITER, Float.class), //
    highcfu("High CFU" + Constants.LITER, Float.class), //
    maxcfu("Maximum amount of fuel in central tank" + Constants.LITER, Float.class), //
    rfu("Right fuel tank" + Constants.LITER, Boolean.class), //
    minrfu("Minimum amount of fuel in right tank" + Constants.LITER, Float.class), //
    lowrfu("Low RFU" + Constants.LITER, Float.class), //
    highrfu("High RFU" + Constants.LITER, Float.class), //
    maxrfu("Maximum amount of fuel in right tank" + Constants.LITER, Float.class), //
    timestamp("Timestamp");//

    final static Logger LOG = LoggerFactory.getLogger(SimulatorModelCols.class);

    private String name;
    private String description = "";
    @SuppressWarnings("rawtypes")
    private Class type = null;

    private SimulatorModelCols(String humanReadableName) {
	this.name = humanReadableName;
    }

    @SuppressWarnings("rawtypes")
    private SimulatorModelCols(String humanReadableName, Class type) {
	this.name = humanReadableName;
	this.type = type;
    }

    public String getDescription() {
	return description;
    }

    @SuppressWarnings("rawtypes")
    public Class getType() {
	return type;
    }

    public static String[] getSimulatorModelCols() {
	SimulatorModelCols[] cols = values();
	String[] strCols = new String[cols.length];
	for (int i = 0; i < cols.length; i++) {
	    strCols[i] = cols[i].toString();
	}
	return strCols;
    }

    public static String[] getSimulatorModelColsNames() {
	SimulatorModelCols[] cols = values();
	String[] strColsNames = new String[cols.length];
	for (int i = 0; i < cols.length; i++) {
	    LOG.debug("Getting simulator cols names" + cols[i].getName());
	    strColsNames[i] = cols[i].getName();
	}
	return strColsNames;
    }

    public static String[] getSimulatorModelMainCols() {
	return new String[] { simulatormodelid.toString(), simulatormodelname.toString() };
    }

    public static String[] getSimulatorModelMainColsNames() {
	return new String[] { simulatormodelid.name, simulatormodelname.name };
    }

    /**
     * @return the name of the column which represent enabling/disabling some
     *         feature in simulator model
     */
    public static List<SimulatorModelCols> getConfigurableColumnNames() {
	return Arrays.asList(lfu, rfu, cfu);
    }

    public String getName() {
	return name;
    }

}
