package cz.vutbr.fit.simulatormanager;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.VaadinServlet;

import cz.vutbr.fit.simulatormanager.data.ApplicationConfiguration;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelperPureJDBC;
import cz.vutbr.fit.simulatormanager.simulatorcommunication.UpdatesScheduler;

/**
 * This class is needed to initialize the application on startup
 * 
 * @author zhenia
 *
 */
public class InitializerServlet extends VaadinServlet {
    private static final long serialVersionUID = 1L;
    final static Logger LOG = LoggerFactory.getLogger(InitializerServlet.class);

    @Override
    public void servletInitialized() throws ServletException {
	super.servletInitialized();
	LOG.info("Initializing application configuration");
	initApplicationConfiguration();
	// we do it to initialize static stuff in SimulationUpdater, and start
	// fetching data from simulators every x seconds
	try {
	    Class.forName(UpdatesScheduler.class.getName());
	} catch (ClassNotFoundException e) {
	    throw new IllegalStateException("Could not initilize application properly", e);
	}
	DatabaseHelperPureJDBC.initDatabaseIfNeeded();
	DatabaseHelper.getPool();

    }

    /**
     * Reads the application configuration data from the file: dtb username,
     * password, google maps apikey etc., and sets it to static fields of
     * ApplicationConfiguration class
     */
    private void initApplicationConfiguration() {
	ApplicationConfiguration.initApplicationConfigFromConfFile();
    }

}