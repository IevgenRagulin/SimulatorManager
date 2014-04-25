package com.example.testvaadin;

import javax.servlet.annotation.WebServlet;

import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.views.ChooseSimulationView;
import com.example.testvaadin.views.ControlSimulationsView;
import com.example.testvaadin.views.DatabaseManagementView;
import com.example.testvaadin.views.PastSimulationsView;
import com.example.testvaadin.views.RunningSimulationsView;
import com.example.testvaadin.views.SimulatorsView;
import com.example.testvaadin.views.StartView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("testvaadin")
public class NavigatorUI extends UI {
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = NavigatorUI.class, widgetset = "com.example.testvaadin.widgetset.TestvaadinWidgetset")
	public static class Servlet extends VaadinServlet {
		private static final long serialVersionUID = -5802992110157497963L;
	}

	private static final long serialVersionUID = -2218352764682942955L;
	Navigator navigator;
	public static final String MANAGESIMULATORS = "Manage simulators";
	public static final String VIEWINGSIMULATIONS = "Viewing simulations";
	public static final String RUNNINGSIMULATIONS = "Running simulations";
	public static final String PASTSIMULATIONS = "Past simulations";
	public static final String CONTROLSIMULATIONS = "Control simulations";
	public static final String DATABASE_MANAGEMENT = "Manage database";
	protected static RunningSimulationsView runningSimulationsView;

	@Override
	protected void init(VaadinRequest request) {
		initApplicationConfiguration();
		// we do it to initialize static stuff in SimulationUpdater class
		try {
			Class.forName("com.example.testvaadin.simulatorcommunication.UpdatesScheduler");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		getPage().setTitle("Main menu");
		navigator = new Navigator(this, this);
		createRegisterViews();
	}

	private void createRegisterViews() {
		// Create and register the views
		navigator.addView("", new StartView(this.navigator));
		navigator.addView(MANAGESIMULATORS, new SimulatorsView(this.navigator));
		navigator.addView(VIEWINGSIMULATIONS, new ChooseSimulationView(
				this.navigator));
		navigator.addView(RUNNINGSIMULATIONS, new RunningSimulationsView(
				this.navigator));
		navigator.addView(PASTSIMULATIONS, new PastSimulationsView(
				this.navigator));
		navigator.addView(CONTROLSIMULATIONS, new ControlSimulationsView(
				this.navigator));
		navigator.addView(DATABASE_MANAGEMENT, new DatabaseManagementView(
				this.navigator));
	}

	/*
	 * Important: reads the application configuration data from the file: dtb
	 * username, password, google maps apikey etc., and sets it to static fields
	 * of ApplicationConfiguration class
	 */
	private void initApplicationConfiguration() {
		ApplicationConfiguration.initApplicationConfigFromConfFile();
	}

	public Navigator getNavigator() {
		return navigator;
	}
}
