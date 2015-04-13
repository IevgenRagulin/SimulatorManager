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

    private EnginesAccordion enginesModels;
    private SimulatorModelForm simulatorModelForm;

    public static final boolean SHOW_SUCCESS_MESSAGE = false;

    public SimulatorModelConfigurationChecker(EnginesAccordion enginesAccordion, SimulatorModelForm simulatorModelForm) {
	this.enginesModels = enginesAccordion;
	this.simulatorModelForm = simulatorModelForm;
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
	String errorsInConfig = SimulatorModelValidator.isSimulatorModelConfiguredCorrectly(enginesModels,
		simulatorModelForm);
	if (StringUtils.isEmpty(errorsInConfig)) {
	    Notification.show(successMessage, "", Notification.Type.HUMANIZED_MESSAGE);
	    return true;
	} else {
	    new Notification(errorMessage, errorsInConfig, Notification.Type.ERROR_MESSAGE, true).show(Page
		    .getCurrent());
	    return false;
	}
    }
}
