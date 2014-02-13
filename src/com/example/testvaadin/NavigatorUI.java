package com.example.testvaadin;

import javax.servlet.annotation.WebServlet;

import com.example.testvaadin.views.ControlSimulationsView;
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
	public static final String RUNNINGSIMULATIONS = "Running simulations";
	public static final String CONTROLSIMULATIONS = "Control simulations";
	protected static RunningSimulationsView runningSimulationsView;

	// @WebServlet(value = "/*", asyncSupported = true)
	// @VaadinServletConfiguration(productionMode = false, ui =
	// NavigatorUI.class)
	// public static class Servlet extends VaadinServlet {

	// }

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
