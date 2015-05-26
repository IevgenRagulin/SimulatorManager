package cz.vutbr.fit.simulatormanager.components;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

import cz.vutbr.fit.simulatormanager.util.SimulatorModelValidator;

/**
 * This is a class for checking simulator model configuration. error
 * 
 * This class is different from SimulatorConfigurationChecker, because this
 * checks if a simulator model is valid, while SimulatorConfigurationChecker
 * checks if a SimulatorModel corresponds to Simulator configuration
 * 
 * @author zhenia
 *
 */
public class SimulatorModelConfigurationChecker {

    private EnginesTabSheet enginesModels;
    private SimulatorModelForm simulatorModelForm;

    public static final boolean SHOW_SUCCESS_MESSAGE = false;

    public SimulatorModelConfigurationChecker(EnginesTabSheet enginesAccordion, SimulatorModelForm simulatorModelForm) {
	this.enginesModels = enginesAccordion;
	this.simulatorModelForm = simulatorModelForm;
    }

    private void buildAndShowNotification(String message, String description, Notification.Type type) {
	Notification notification = new Notification(message, description, type);
	notification.setHtmlContentAllowed(true);
	notification.show(Page.getCurrent());
    }

    /**
     * 
     * @param errorMessage
     *            - if configurations is invalid, this error message will be
     *            shown
     * @param successMessage
     *            - if configuration is valid, this message will be shown
     * @return
     */
    public boolean verifyConfiguration(String errorMessage, String successMessage) {
	String errorsInConfig = SimulatorModelValidator.isSimulatorModelConfiguredCorrectly(enginesModels, simulatorModelForm);
	if (StringUtils.isEmpty(errorsInConfig)) {
	    buildAndShowNotification(successMessage, "", Notification.Type.HUMANIZED_MESSAGE);
	    return true;
	} else {
	    buildAndShowNotification(errorMessage, errorsInConfig, Notification.Type.ERROR_MESSAGE);
	    return false;
	}
    }
}
