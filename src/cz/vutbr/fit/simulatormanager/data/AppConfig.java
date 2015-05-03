package cz.vutbr.fit.simulatormanager.data;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Notification;

/**
 * This class is used for getting values from a property file. Whenever a
 * property file changes, this class automatically reloads its content into
 * memory
 * 
 * @author zhenia
 *
 */
public class AppConfig {

    private final static Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    private static final String BASEPATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
    public static final String CONF_FILE_PATH = BASEPATH + "/WEB-INF/simulatorManager.prop";
    private static final String DB_URL_PROP = "db_url";
    private static final String DB_USERNAME_PROP = "db_username";
    private static final String DB_PASS_PROP = "db_password";
    private static final String GOOGLE_MAP_API_KEY_PROP = "google_map_api_key";
    private static final String SIMULATOR_GET_DATA_FREQ_PROP = "simulator_get_data_frequency_ms";
    private static final String USER_INT_REFRESH_FREQ_PROP = "user_interface_refresh_frequency_ms";
    private static final String WRITE_TO_DB_FREQ_PROP = "write_to_database_frequency_ms";
    private static final String WRITE_POS_TO_DB_FREQ_PROP = "write_position_to_database_frequency_ms";
    private static final String UPDATE_AIRPL_POS_FREQ_PROP = "update_airplane_position_frequency_ms";

    private static PropertiesConfiguration propConf;

    public static void initApplicationConfigFromConfFile() {
	LOG.info("Going to init application configuration from file...");

	try {
	    propConf = new PropertiesConfiguration(CONF_FILE_PATH);
	    // reload file automatically if it has changed
	    propConf.setReloadingStrategy(new FileChangedReloadingStrategy());
	} catch (ConfigurationException e) {
	    Notification.show("Couldn't load application configuration from path" + CONF_FILE_PATH);
	    LOG.error("Couldn't load application configuration from file", e);
	}
    }

    public static Float getFloatByKey(String key) {
	return propConf.getFloat(key, 0);
    }

    public static Boolean getBoolByKey(String key) {
	return propConf.getBoolean(key, false);
    }

    public static Integer getIntByKey(String key) {
	return propConf.getInteger(key, 1);
    }

    public static String getStringByKey(String key) {
	return propConf.getString(key, "New");
    }

    public static String getBasepath() {
	return BASEPATH;
    }

    public static String getConfFilePath() {
	return CONF_FILE_PATH;
    }

    public static String getDbUrl() {
	return propConf.getString(DB_URL_PROP);
    }

    public static String getDbUserName() {
	return propConf.getString(DB_USERNAME_PROP);
    }

    public static String getDbUserPassword() {
	return propConf.getString(DB_PASS_PROP);
    }

    public static String getGoogleMapApiKey() {
	return propConf.getString(GOOGLE_MAP_API_KEY_PROP);
    }

    public static Integer getSimulatorGetDataFrequency() {
	return propConf.getInt(SIMULATOR_GET_DATA_FREQ_PROP);
    }

    public static Integer getRefreshUiFrequency() {
	return propConf.getInt(USER_INT_REFRESH_FREQ_PROP);
    }

    public static Integer getWriteToDbFrequency() {
	return propConf.getInt(WRITE_TO_DB_FREQ_PROP);
    }

    public static Integer getWritePositionToDbFrequency() {
	return propConf.getInt(WRITE_POS_TO_DB_FREQ_PROP);
    }

    public static Integer getUpdateAirplanePositionFrequency() {
	return propConf.getInt(UPDATE_AIRPL_POS_FREQ_PROP);
    }

}
