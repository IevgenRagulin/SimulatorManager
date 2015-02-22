package cz.vutbr.fit.simulatormanager.data;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.VaadinService;

public class ApplicationConfiguration {

	final static Logger logger = LoggerFactory
			.getLogger(ApplicationConfiguration.class);

	private static final String BASEPATH = VaadinService.getCurrent()
			.getBaseDirectory().getAbsolutePath();
	private static final String CONF_FILE_PATH = BASEPATH
			+ "/WEB-INF/simulatorManager.prop";
	private static final String DB_URL_PROP = "db_url";
	private static final String DB_USERNAME_PROP = "db_username";
	private static final String DB_PASS_PROP = "db_password";
	private static final String GOOGLE_MAP_API_KEY_PROP = "google_map_api_key";
	private static final String SIMULATOR_GET_DATA_FREQ_PROP = "simulator_get_data_frequency_ms";
	private static final String USER_INT_REFRESH_FREQ_PROP = "user_interface_refresh_frequency_ms";
	private static final String WRITE_TO_DB_FREQ_PROP = "write_to_database_frequency_ms";
	private static final String WRITE_POS_TO_DB_FREQ_PROP = "write_position_to_database_frequency_ms";
	private static final String UPDATE_AIRPL_POS_FREQ_PROP = "update_airplane_position_frequency_ms";

	private static String dbUrl = null;
	private static String dbUserName = null;
	private static String dbUserPassword = null;
	private static String googleMapApiKey = null;

	private static Integer simulatorGetDataFrequency = null;
	private static Integer refreshUiFrequency = null;
	// how often we save data to database. Here - every 1000/UPDATE_RATE MS
	// times. I.e. if UPDATE_RATE_MS = 300, we save data every 3 times
	private static Integer writeToDbFrequency = null;
	// how often we save position data to database. We make this frequency
	// smaller than
	// for other data, because data in simulation info contains
	// plane coordinates and it's to overwhelming for googlemaps to display too
	// many points for the flight path.
	private static Integer writePositionToDbFrequency = null;
	// how often we add data about plane position to the map
	private static Integer updateAirplanePositionFrequency = null;

	public static void initApplicationConfigFromConfFile() {
		logger.info("Going to init application configuration from file...");

		try {
			PropertiesConfiguration propConf = new PropertiesConfiguration(
					CONF_FILE_PATH);
			setDbUrl(propConf.getString(DB_URL_PROP));
			setDbUserName(propConf.getString(DB_USERNAME_PROP));
			setDbUserPassword(propConf.getString(DB_PASS_PROP));
			setGoogleMapApiKey(propConf.getString(GOOGLE_MAP_API_KEY_PROP));
			setSimulatorGetDataFrequency(propConf
					.getInt(SIMULATOR_GET_DATA_FREQ_PROP));
			setRefreshUiFrequency(propConf.getInt(USER_INT_REFRESH_FREQ_PROP));
			setWriteToDbFrequency(propConf.getInt(WRITE_TO_DB_FREQ_PROP));
			setWritePositionToDbFrequency(propConf
					.getInt(WRITE_POS_TO_DB_FREQ_PROP));
			setUpdateAirplanePositionFrequency(propConf
					.getInt(UPDATE_AIRPL_POS_FREQ_PROP));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	private static void setDbUrl(String dbUrl) {
		ApplicationConfiguration.dbUrl = dbUrl;
	}

	private static void setDbUserName(String dbUserName) {
		ApplicationConfiguration.dbUserName = dbUserName;
	}

	private static void setDbUserPassword(String dbUserPassword) {
		ApplicationConfiguration.dbUserPassword = dbUserPassword;
	}

	private static void setGoogleMapApiKey(String googleMapApiKey) {
		ApplicationConfiguration.googleMapApiKey = googleMapApiKey;
	}

	private static void setSimulatorGetDataFrequency(int i) {
		ApplicationConfiguration.simulatorGetDataFrequency = i;
	}

	private static void setRefreshUiFrequency(Integer refreshUiFrequency) {
		ApplicationConfiguration.refreshUiFrequency = refreshUiFrequency;
	}

	private static void setWriteToDbFrequency(Integer writeToDbFrequency) {
		ApplicationConfiguration.writeToDbFrequency = writeToDbFrequency;
	}

	private static void setWritePositionToDbFrequency(
			Integer writePositionToDbFrequency) {
		ApplicationConfiguration.writePositionToDbFrequency = writePositionToDbFrequency;
	}

	private static void setUpdateAirplanePositionFrequency(
			Integer updateAirplanePositionFrequency) {
		ApplicationConfiguration.updateAirplanePositionFrequency = updateAirplanePositionFrequency;
	}

	public static String getBasepath() {
		return BASEPATH;
	}

	public static String getConfFilePath() {
		return CONF_FILE_PATH;
	}

	public static String getDbUrl() {
		if (dbUrl == null)
			throw new NullPointerException("Database url is not set");
		return dbUrl;
	}

	public static String getDbUserName() {
		if (dbUserName == null)
			throw new NullPointerException("Database user name is not set");
		return dbUserName;
	}

	public static String getDbUserPassword() {
		if (dbUserPassword == null)
			throw new NullPointerException("Database user password is not set");
		return dbUserPassword;
	}

	public static String getGoogleMapApiKey() {
		if (googleMapApiKey == null)
			throw new NullPointerException("Google map api key is not set");
		return googleMapApiKey;
	}

	public static Integer getSimulatorGetDataFrequency() {
		if (simulatorGetDataFrequency == null)
			throw new NullPointerException(
					"simulatorGetDataFrequency is not set");
		return simulatorGetDataFrequency;
	}

	public static Integer getRefreshUiFrequency() {
		if (refreshUiFrequency == null)
			throw new NullPointerException("refreshUiFrequency is not set");
		return refreshUiFrequency;
	}

	public static Integer getWriteToDbFrequency() {
		if (writeToDbFrequency == null)
			throw new NullPointerException("writeToDbFrequency is not set");
		return writeToDbFrequency;
	}

	public static Integer getWritePositionToDbFrequency() {
		if (writePositionToDbFrequency == null)
			throw new NullPointerException(
					"writePositionToDbFrequency is not set");
		return writePositionToDbFrequency;
	}

	public static Integer getUpdateAirplanePositionFrequency() {
		if (updateAirplanePositionFrequency == null)
			throw new NullPointerException(
					"updateAirplanePositionFrequency is not set");
		return updateAirplanePositionFrequency;
	}

}
