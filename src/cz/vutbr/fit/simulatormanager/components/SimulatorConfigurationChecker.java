package cz.vutbr.fit.simulatormanager.components;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

import cz.vutbr.fit.simulatormanager.simulatorcommunication.SimulationStatusProviderSimpleImpl;
import cz.vutbr.fit.simulatormanager.util.SimulatorValidator;

/**
 * This is a class for checking availability/configuration on selected
 * simulator. Verifies configuration, shows message to user if there was an
 * error
 * 
 * @author zhenia
 *
 */
public class SimulatorConfigurationChecker {

    private String hostname;
    private int port;
    private String simulatorId;

    public static final boolean SHOW_SUCCESS_MESSAGE = true;
    public static final boolean DO_NOT_SHOW_SUCCESS_MESSAGE = false;
    final static String PING_SUCCESS_MESSAGE = "Success. The simulator is up and running";
    final static String PING_SUCCESS_CONFIGURATION_FAIL_MESSAGE = "The simulator is running. <br/> However, you need to check its configuration";
    final static String PING_FAIL_MESSAGE = "Connection error.<br/> The selected simulator is not responding.<br/> Check that hostname (IP address), port are set correctly. <br/>Make sure that AWCom plugin is installed in XPlane";

    /**
     * 
     * @param hostname
     * @param port
     * @param simulatorId
     *            in the database (simulator.simulatorid)
     */
    public SimulatorConfigurationChecker(String hostname, int port, String simulatorId) {
	this.hostname = hostname;
	this.port = port;
	this.simulatorId = simulatorId;
    }

    private Notification createShowNotification(String message, String description, Notification.Type type) {
	Notification notification = new Notification(message, description, type, true);
	notification.show(Page.getCurrent());
	return notification;
    }

    /**
     * 
     * @param showSuccessMessage
     *            if this is true and if configuration is valida, we display a
     *            message. If false, we don't display success message, but we
     *            still display error message
     * @return
     */
    public boolean verifyConfiguration(boolean showSuccessMessage, Notification.Type errorNotificationType) {
	boolean isRespoding = SimulationStatusProviderSimpleImpl.isSimulatorResponding(hostname, port);
	if (isRespoding) {
	    String errorsInConfiguration = SimulatorValidator.isSimulatorModelConfiguredCorrectly(simulatorId.toString(),
		    hostname, port);
	    if (errorsInConfiguration.isEmpty() && showSuccessMessage) {
		createShowNotification("", PING_SUCCESS_MESSAGE, Notification.Type.HUMANIZED_MESSAGE);
		return true;
	    } else if (!errorsInConfiguration.isEmpty()) {
		createShowNotification(PING_SUCCESS_CONFIGURATION_FAIL_MESSAGE, errorsInConfiguration.toString(),
			errorNotificationType);
		return false;
	    } else {
		// return true but don't display any message
		return true;
	    }
	} else {
	    createShowNotification(PING_FAIL_MESSAGE, "", errorNotificationType);
	    return false;
	}
    }
}
