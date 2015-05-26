package cz.vutbr.fit.simulatormanager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;

/**
 * This class is needed to destory connection pool on application shutdown so
 * that we don't have idle connections in database
 * 
 * @author zhenia
 *
 */
public class ConnectionPoolDestroyer implements ServletContextListener {

    final static Logger LOG = LoggerFactory.getLogger(ConnectionPoolDestroyer.class);

    /**
     * This is run once during application shutdown
     */
    public void contextDestroyed(ServletContextEvent contextEvent) {
	LOG.info("Destroying connection pool...");
	DatabaseHelper.destroyConnectionPool();
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

    }

}