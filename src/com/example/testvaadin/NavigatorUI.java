package com.example.testvaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

public class NavigatorUI extends UI {
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = NavigatorUI.class, widgetset = "com.example.testvaadin.widgetset.TestvaadinWidgetset")
	public static class Servlet extends VaadinServlet {

	}

	private static final long serialVersionUID = -2218352764682942955L;
	Navigator navigator;
	protected static final String MANAGESIMULATORS = "Manage simulators";
	protected static final String RUNNINGSIMULATIONS = "Running simulations";
	protected static final String CONTROLSIMULATIONS = "Control simulations";

	private static final String SIMNAME = "SimulatorName";
	private static final String PLANESIM = "AircraftModel";
	private static final String SIMID = "SimulatorId";

	private static final String[] columnNames = new String[] { SIMNAME,
			PLANESIM, SIMID, "MinSpeed", "MaxSpeed", "HighSpeed",
			"MaxSpeedOnFlaps", "MinSpeedOnFlaps", "HasGears", "MinTempCHT1",
			"MinTempCHT2", "MinTempEGT1", "MinTempEGT2", "MaxTempCHT1",
			"MaxTempCHT2", "MaxTempEGT1", "MaxTempEGT2", "ManifoldPressure",
			"Power", "MaxAmountOfFuel", "MinAmountOfFuel", "MaxRPM",
			"NumberOfEngines" };

	// @WebServlet(value = "/*", asyncSupported = true)
	// @VaadinServletConfiguration(productionMode = false, ui =
	// NavigatorUI.class)
	// public static class Servlet extends VaadinServlet {

	// }

	public static String getSimNamePropertyName() {
		return SIMNAME;
	}

	public static String[] getVisibleColumns() {
		return new String[] { SIMID, SIMNAME, PLANESIM };
	}

	public static String[] getColumnNames() {
		return columnNames;
	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Main menu");
		navigator = new Navigator(this, this);
		// Create and register the views
		navigator.addView("", new StartView(this.navigator));
		navigator.addView(MANAGESIMULATORS, new SimulatorsView(this.navigator));
		navigator.addView(RUNNINGSIMULATIONS, new RunningSimulationsView(
				this.navigator));
		navigator.addView(CONTROLSIMULATIONS, new ControlSimulationsView(
				this.navigator));
	}

	public Navigator getNavigator() {
		return navigator;
	}
}
