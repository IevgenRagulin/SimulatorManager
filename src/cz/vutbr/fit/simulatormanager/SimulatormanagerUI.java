package cz.vutbr.fit.simulatormanager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import cz.vutbr.fit.simulatormanager.data.ApplicationConfiguration;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelperPureJDBC;
import cz.vutbr.fit.simulatormanager.views.ChooseSimulationView;
import cz.vutbr.fit.simulatormanager.views.ConfigurationView;
import cz.vutbr.fit.simulatormanager.views.PastSimulationsView;
import cz.vutbr.fit.simulatormanager.views.RunningSimulationsView;
import cz.vutbr.fit.simulatormanager.views.SimulatorModelsView;
import cz.vutbr.fit.simulatormanager.views.SimulatorsView;
import cz.vutbr.fit.simulatormanager.views.StartView;

@Theme("testvaadinn")
public class SimulatormanagerUI extends UI {

    private static final String APPLICATION_NAME = "Simulator manager";
    final static Logger LOG = LoggerFactory.getLogger(SimulatormanagerUI.class);

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = SimulatormanagerUI.class, widgetset = "cz.vutbr.fit.simulatormanager.widgetset.SimulatorManagerWidgetset")
    public static class Servlet extends VaadinServlet {
	private static final long serialVersionUID = -5802992110157497963L;

	public void init() throws ServletException {
	    LOG.info("simulator manager ui");
	}
    }

    private static final long serialVersionUID = -2218352764682942955L;
    Navigator navigator;
    public static final String MANAGESIMULATORMODELS = "ManageSimulatorModels";
    public static final String MANAGESIMULATORS = "ManageSimulators";
    public static final String VIEWINGSIMULATIONS = "ViewingSimulations";
    public static final String RUNNINGSIMULATIONS = "RunningSimulations";
    public static final String PASTSIMULATIONS = "PastSimulations";
    public static final String CONTROLSIMULATIONS = "ControlSimulations";
    public static final String CONFIGURATION = "Configuration";
    protected static RunningSimulationsView runningSimulationsView;

    private boolean uiInitialized = false;

    @Override
    protected void init(VaadinRequest request) {
	LOG.info("Going to initialize application");
	if (!uiInitialized) {
	    // init connection pool
	    DatabaseHelper.getPool();
	    if (!DatabaseHelperPureJDBC.isDatabaseRunning()) {
		Notification.show("Please, check if database is running and configured properly",
			"Current configuration. DB URL: " + ApplicationConfiguration.getDbUrl() + " DB Username: "
				+ ApplicationConfiguration.getDbUserName(), Notification.Type.ERROR_MESSAGE);
	    } else {
		getPage().setTitle(APPLICATION_NAME);
		navigator = new Navigator(this, this);
		createRegisterViews();
		uiInitialized = true;
	    }
	}
    }

    /**
     * Create and register the views
     */
    private void createRegisterViews() {
	navigator.addView("", new StartView(this.navigator));
	navigator.addView(MANAGESIMULATORMODELS, new SimulatorModelsView(this.navigator));
	navigator.addView(MANAGESIMULATORS, new SimulatorsView(this.navigator));
	navigator.addView(VIEWINGSIMULATIONS, new ChooseSimulationView(this.navigator));
	navigator.addView(RUNNINGSIMULATIONS, new RunningSimulationsView(this.navigator));
	navigator.addView(PASTSIMULATIONS, new PastSimulationsView(this.navigator));
	// navigator.addView(CONTROLSIMULATIONS, new
	// ControlSimulationsView(this.navigator));
	navigator.addView(CONFIGURATION, new ConfigurationView(this.navigator));
    }

    public Navigator getNavigator() {
	return navigator;
    }
}
