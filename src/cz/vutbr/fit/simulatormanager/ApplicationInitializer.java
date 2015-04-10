package cz.vutbr.fit.simulatormanager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;

public class ApplicationInitializer implements ServletContextListener {

    final static Logger LOG = LoggerFactory.getLogger(ApplicationInitializer.class);

    ServletContext context;

    /**
     * This is run once during application startup.
     * 
     * Note: this ServletContextListener is configured in web.xml
     */
    public void contextInitialized(ServletContextEvent contextEvent) {
	LOG.info("Going to initialize application..");
	context = contextEvent.getServletContext();
    }

    /**
     * This is run once during application shutdown
     */
    public void contextDestroyed(ServletContextEvent contextEvent) {
	LOG.info("Shutting down application.. Destroying context.. destroying connection pool...");
	context = contextEvent.getServletContext();
	DatabaseHelper.destroyConnectionPool();
    }

}