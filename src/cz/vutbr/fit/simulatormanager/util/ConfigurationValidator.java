package cz.vutbr.fit.simulatormanager.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

import cz.vutbr.fit.simulatormanager.database.EngineModelQueries;
import cz.vutbr.fit.simulatormanager.database.SimulatorModelQueries;
import cz.vutbr.fit.simulatormanager.database.columns.EngineModelCols;
import cz.vutbr.fit.simulatormanager.database.columns.SimulatorModelCols;
import cz.vutbr.fit.simulatormanager.simulatorcommunication.AWComClient;
import cz.vutbr.fit.simulatormanager.simulatorcommunication.AWComParser;

/**
 * Class for checking if the Simulator Model corresponds to the data sent by
 * simulator through AWCOM.
 * 
 * Currently checks engine configuration
 * 
 * TODO: check fuel configuration, number of landing gears
 * 
 * @author zhenia
 *
 */
public class ConfigurationValidator {
    final static Logger LOG = LoggerFactory.getLogger(ConfigurationValidator.class);

    /**
     * Returns a String describing errors in configuration. If there are no
     * errors found, the String is empty
     * 
     * @param simulatorId
     * @param simulationInfo
     * @param allEngineInfo
     * @return
     */
    public static String isSimulatorModelConfiguredCorrectly(String simulatorId, String simulatorHost, int port) {
	Item simulatorModel = SimulatorModelQueries.getSimulatorModelBySimulatorId(simulatorId);
	SQLContainer enginesModels = EngineModelQueries.getEngineModelsBySimulatorId(simulatorId);
	String simulationDataFromAwCom = AWComClient.getSimulationDataUnparsed(simulatorHost, port);
	String enginesDataFromAwCom = AWComClient.getEngineDataUnparsed(simulatorHost, port);

	List<String> errorsAll = new ArrayList<String>();
	List<String> enginesErrors = validateEnginesConfiguration(enginesModels, enginesDataFromAwCom);
	errorsAll.addAll(enginesErrors);
	List<String> simulatorModelErrors = validateModelConfiguration(simulatorModel, simulationDataFromAwCom);
	errorsAll.addAll(simulatorModelErrors);
	return StringUtils.join(errorsAll.toArray(), "<br/>");
    }

    public static List<String> validateModelConfiguration(Item simulatorModel, String responseFromAwCom) {
	List<String> errors = new ArrayList<>();
	for (SimulatorModelCols simulatorModelCol : SimulatorModelCols.getConfigurableColumnNames()) {
	    boolean isFeatureEnabledInDb = isFeatureEnabledInDb(simulatorModel, simulatorModelCol.toString());
	    Float valueFromAwCom = AWComParser.getValueForSimulationKey(simulatorModelCol.toString().toUpperCase(),
		    responseFromAwCom);
	    boolean isFeatureEnabledAccordingToAwCom = isAwComSendingValueForFeature(valueFromAwCom);
	    if (isFeatureConfigurationWrong(isFeatureEnabledInDb, isFeatureEnabledAccordingToAwCom)) {
		errors.add(buildSimulatorModelErrorMessage(simulatorModelCol.toString(), valueFromAwCom));
	    }
	}
	return errors;
    }

    @SuppressWarnings("unchecked")
    private static List<String> validateEnginesConfiguration(SQLContainer enginesModels, String responseFromAwCom) {
	List<String> errorsInConfiguration = new ArrayList<String>();
	Collection<RowId> itemIds = (Collection<RowId>) enginesModels.getItemIds();
	LOG.debug("Going to iterate throuh engines. Num of engines: {}", itemIds.size());
	for (RowId itemId : itemIds) {
	    Item engineItem = enginesModels.getItem(itemId);
	    int engineNumber = (int) engineItem.getItemProperty(EngineModelCols.enginemodelorder.toString()).getValue();
	    LOG.info("Going to validate engine number: {}", engineNumber);
	    List<String> errorsForThisEngine = validateEngineConfiguration(engineItem, responseFromAwCom, engineNumber);
	    errorsInConfiguration.addAll(errorsForThisEngine);
	}
	return errorsInConfiguration;
    }

    private static List<String> validateEngineConfiguration(Item engineItem, String responseFromAwCom, int engineNumber) {
	List<String> listOfErrors = new ArrayList<String>();
	for (EngineModelCols engineModelColumn : EngineModelCols.getEngineModelConfigurationCols()) {
	    boolean isFeatureEnabledInDb = isFeatureEnabledInDb(engineItem, engineModelColumn.toString());
	    Float valueFromAwCom = AWComParser.getValueForEngineKey(engineNumber, engineModelColumn.toString()
		    .toUpperCase(), responseFromAwCom);
	    boolean isFeatureEnabledAccordingToAwCom = isAwComSendingValueForFeature(valueFromAwCom);
	    if (isFeatureConfigurationWrong(isFeatureEnabledInDb, isFeatureEnabledAccordingToAwCom)) {
		listOfErrors.add(buildErrorEngineMessage(engineModelColumn.toString(), valueFromAwCom, engineNumber));
	    }
	}
	return listOfErrors;
    }

    private static String buildSimulatorModelErrorMessage(String featureName, Float valueFromAwcom) {
	return new StringBuilder(100).append(featureName.toUpperCase())
		.append(" needs to be disabled. Value from awcom: ").append(valueFromAwcom).toString();
    }

    private static String buildErrorEngineMessage(String featureName, Float valueFromAwcom, int engineNumber) {
	return new StringBuilder(100).append(featureName.toUpperCase()).append(" on engine ").append(engineNumber)
		.append(" needs to be disabled. Value from awcom: ").append(valueFromAwcom).toString();
    }

    /**
     * If feature is enabled in database, but its value is not sent by awcom
     * than we consider that the configuration is wrong
     * 
     * @param isEnabledInDb
     * @param isEnabledAccordingToAwcom
     * @return
     */
    private static boolean isFeatureConfigurationWrong(boolean isEnabledInDb, boolean isEnabledAccordingToAwcom) {
	return (isEnabledInDb && !isEnabledAccordingToAwcom);
    }

    /**
     * Not sure if this method is implemented right. If AWCom sends null, or 0,
     * or -1, we consider that this feature is disabled according to AWCom
     * 
     * @param value
     * @return
     */
    private static boolean isAwComSendingValueForFeature(Float value) {
	return (value != null && value != 0.0 && value != -1.0);
    }

    /**
     * 
     * @param item
     *            - item representing a row in the database
     * @param featureName
     *            - column name
     * @return
     */
    private static boolean isFeatureEnabledInDb(Item item, String featureName) {
	LOG.info("Checking if feature enabled in db. feature name: {}. item: {}", featureName, item);
	return (boolean) item.getItemProperty(featureName.toString()).getValue();
    }
}
