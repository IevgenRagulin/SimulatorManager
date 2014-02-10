package com.example.testvaadin;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.HorizontalLayout;

public class BasicView extends HorizontalLayout {

	private static final long serialVersionUID = -8524856579132861530L;
	protected static final String SIMNAME = "SimulatorName";
	protected static final String PLANESIM = "AircraftModel";
	protected static final String SIMID = "SimulatorId";

	protected DatabaseHelper dbHelp = new DatabaseHelper();
	protected SQLContainer simulatorContainer = dbHelp.getSimulatorContainer();

	protected static final String[] columnNames = new String[] { SIMNAME,
			PLANESIM, SIMID, "MinSpeed", "MaxSpeed", "HighSpeed",
			"MaxSpeedOnFlaps", "MinSpeedOnFlaps", "HasGears", "MinTempCHT1",
			"MinTempCHT2", "MinTempEGT1", "MinTempEGT2", "MaxTempCHT1",
			"MaxTempCHT2", "MaxTempEGT1", "MaxTempEGT2", "ManifoldPressure",
			"Power", "MaxAmountOfFuel", "MinAmountOfFuel", "MaxRPM",
			"NumberOfEngines" };

	public String getSimNamePropertyName() {
		return SIMNAME;
	}

	public String[] getVisibleColumns() {
		return new String[] { SIMID, SIMNAME, PLANESIM };
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public DatabaseHelper getDBHelp() {
		return dbHelp;
	}
}
