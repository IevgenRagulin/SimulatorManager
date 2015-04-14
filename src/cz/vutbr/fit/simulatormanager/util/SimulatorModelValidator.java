package cz.vutbr.fit.simulatormanager.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

import cz.vutbr.fit.simulatormanager.Constants;
import cz.vutbr.fit.simulatormanager.beans.EngineModelBean;
import cz.vutbr.fit.simulatormanager.components.EnginesTabSheet;
import cz.vutbr.fit.simulatormanager.components.SimulatorModelForm;
import cz.vutbr.fit.simulatormanager.database.columns.EngineModelCols;

/**
 * Class for checking if Simulator model is configured correctly. Three
 * requirements: <br/>
 * 1. if some feature is enabled, it has to have min and max values <br/>
 * 2. Plane has to have at least one engine <br/>
 * 3. Engine model order should go like this: 0, 1, 2.. It shouldn't be possible
 * for AWCom to be sending values for engine 1, and not sending values for
 * engine 0, or sending values for engine 2, and not sending for engine 1, etc.
 * 
 * This class is different from SimulatorValidator, because this one checks if a
 * simulator model is valid, while SimulatorValidator checks if a SimulatorModel
 * corresponds to Simulator configuration
 * 
 * @author zhenia
 *
 */
public class SimulatorModelValidator {
    final static Logger LOG = LoggerFactory.getLogger(SimulatorModelValidator.class);

    public static String isSimulatorModelConfiguredCorrectly(EnginesTabSheet enginesModels,
	    SimulatorModelForm simulatorModelForm) {
	List<String> errorsAll = new ArrayList<String>();
	List<String> enginesErrors = validateEnginesConfiguration(enginesModels);
	// List<String> simulatorModelErrors =
	// validateModelConfiguration(simulatorModel);
	errorsAll.addAll(enginesErrors);
	// errorsAll.addAll(simulatorModelErrors);
	return StringUtils.join(errorsAll.toArray(), "<br/>");
    }

    @SuppressWarnings("rawtypes")
    private static List<String> validateEnginesConfiguration(EnginesTabSheet enginesModels) {
	List<String> errors = new ArrayList<String>();
	if (enginesModels.getComponentCount() == 0) {
	    errors.add("Simulator model must have at least one engine model configured");
	} else {
	    Iterator<Component> engineModelForms = enginesModels.iterator();
	    Set<Integer> engineModelOrders = new HashSet<Integer>();
	    while (engineModelForms.hasNext()) {
		GridLayout engineForm = (GridLayout) engineModelForms.next();
		EngineModelBean eng = buildEngineModelItem(engineForm);
		Integer engOrd = eng.getEnginemodelorder();
		engineModelOrders.add(engOrd);
		validateEngineModelOrder(errors, engOrd);
		validateEnginesFeaturesConfiguration(errors, eng, engOrd);
	    }
	    validateEngineModelOrders(errors, engineModelOrders, enginesModels.getComponentCount());

	}
	return errors;
    }

    /**
     * Verify that engine model orders go like this 0,1,2,3... If there are 3
     * engines than engine model orders should be 0,1,2
     * 
     * @param errors
     *            errors are added here
     * @param engineModelOrders
     *            a set of engine model orders on this simulator model
     * @param numberOfEngines
     *            - number of engines on this simulator model
     */
    private static void validateEngineModelOrders(List<String> errors, Set<Integer> engineModelOrders,
	    int numberOfEngines) {
	for (int i = 0; i < numberOfEngines; i++) {
	    if (!engineModelOrders.contains(i)) {
		errors.add("Engine model order configuration is wrong. The number of engines is " + numberOfEngines
			+ " So there should be AWCom engine id with value: " + i);
		return;
	    }
	}
    }

    private static void validateEngineModelOrder(List<String> errors, Integer engOrd) {
	if ((engOrd == null) || (engOrd >= Constants.MAX_ENGINES_NUM || engOrd < 0)) {
	    errors.add("Engine model order on engine model#" + engOrd + " should be lying in the range from 0 to  "
		    + (Constants.MAX_ENGINES_NUM - 1));
	}
    }

    /**
     * Check that for all features that are enabled on this engine, the min and
     * max values are available
     * 
     * @param errors
     *            errors are added to this list
     * @param eng
     * @param engOrd
     */
    private static void validateEnginesFeaturesConfiguration(List<String> errors, EngineModelBean eng, Integer engOrd) {
	addError(errors, validateVal(EngineModelCols.rpm, eng.isRpm(), eng.getMinrpm(), eng.getMaxrpm(), engOrd));
	addError(errors, validateVal(EngineModelCols.pwr, eng.isPwr(), eng.getMinpwr(), eng.getMaxpwr(), engOrd));
	addError(errors, validateVal(EngineModelCols.pwp, eng.isPwp(), eng.getMinpwp(), eng.getMaxpwp(), engOrd));
	addError(errors, validateVal(EngineModelCols.mp_, eng.isMp_(), eng.getMinmp_(), eng.getMaxmp_(), engOrd));
	addError(errors, validateVal(EngineModelCols.egt1, eng.isEgt1(), eng.getMinegt1(), eng.getMaxegt1(), engOrd));
	addError(errors, validateVal(EngineModelCols.egt2, eng.isEgt2(), eng.getMinegt2(), eng.getMaxegt2(), engOrd));
	addError(errors, validateVal(EngineModelCols.cht1, eng.isCht1(), eng.getMincht1(), eng.getMaxcht1(), engOrd));
	addError(errors, validateVal(EngineModelCols.cht2, eng.isCht2(), eng.getMincht2(), eng.getMaxcht2(), engOrd));
	addError(errors, validateVal(EngineModelCols.est, eng.isEst(), eng.getMinest(), eng.getMaxest(), engOrd));
	addError(errors, validateVal(EngineModelCols.ff_, eng.isFf_(), eng.getMinff_(), eng.getMaxff_(), engOrd));
	addError(errors, validateVal(EngineModelCols.fp_, eng.isFp_(), eng.getMinfp_(), eng.getMaxfp_(), engOrd));
	addError(errors, validateVal(EngineModelCols.op_, eng.isOp_(), eng.getMinop_(), eng.getMaxop_(), engOrd));
	addError(errors, validateVal(EngineModelCols.n1_, eng.isN1_(), eng.getMinn1_(), eng.getMaxn1_(), engOrd));
	addError(errors, validateVal(EngineModelCols.n2_, eng.isN2_(), eng.getMinn2_(), eng.getMaxn2_(), engOrd));
	addError(errors, validateVal(EngineModelCols.vib, eng.isVib(), eng.getMinvib(), eng.getMaxvib(), engOrd));
	addError(errors, validateVal(EngineModelCols.vlt, eng.isVlt(), eng.getMinvlt(), eng.getMaxvlt(), engOrd));
	addError(errors, validateVal(EngineModelCols.amp, eng.isAmp(), eng.getMinamp(), eng.getMaxamp(), engOrd));
    }

    private static void addError(List<String> listOfErrors, String newError) {
	if (StringUtils.isNotEmpty(newError)) {
	    listOfErrors.add(newError);
	}
    }

    /**
     * If a feature with feature name is enabled, then check if minValue and
     * maxValue are not null. If they are null, or if max>min, then return error
     * message. Otherwise, return an empty string
     * 
     * @param featureName
     * @param isEnabled
     * @param minValue
     * @param maxValue
     * @return
     */
    private static String validateVal(EngineModelCols featureName, Boolean isEnabled, Float minValue, Float maxValue,
	    Integer engineOrder) {
	if ((isEnabled != null && isEnabled)
		&& ((minValue == null || maxValue == null) || (minValue != null && maxValue != null && minValue > maxValue))) {
	    return new StringBuilder().append(featureName.toString().toUpperCase())
		    .append(" is enabled, but min/max are misconfigured on engine with order").append(engineOrder)
		    .toString();
	}
	return "";
    }

    /**
     * Based on a form layout of a simulator model, build a simulator model
     * bean. We do this to simplify/make more readable validation later
     * 
     * @return
     */
    private static EngineModelBean buildEngineModelItem(GridLayout engineGrid) {
	EngineModelBean engineModelBean = new EngineModelBean();
	Iterator<Component> engineFormFields = engineGrid.iterator();
	while (engineFormFields.hasNext()) {
	    Component engineFormField = engineFormFields.next();
	    if (engineFormField.getCaption().equals(EngineModelCols.enginemodelorder.getName())) {
		TextField engineModelOrder = (TextField) engineFormField;
		engineModelBean.setEnginemodelorder(ConverterUtil.stringToInt(engineModelOrder.getValue()));

	    } else if (engineFormField.getCaption().equals(EngineModelCols.rpm.getName())) {
		CheckBox rpm = (CheckBox) engineFormField;
		engineModelBean.setRpm(Boolean.valueOf(rpm.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minrpm.getName())) {
		TextField minrpm = (TextField) engineFormField;
		engineModelBean.setMinrpm(ConverterUtil.stringToFloat(minrpm.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxrpm.getName())) {
		TextField maxrpm = (TextField) engineFormField;
		engineModelBean.setMaxrpm(ConverterUtil.stringToFloat(maxrpm.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.pwr.getName())) {
		CheckBox pwr = (CheckBox) engineFormField;
		engineModelBean.setPwr(Boolean.valueOf(pwr.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minpwr.getName())) {
		TextField minpwr = (TextField) engineFormField;
		engineModelBean.setMinpwr(ConverterUtil.stringToFloat(minpwr.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxpwr.getName())) {
		TextField maxpwr = (TextField) engineFormField;
		engineModelBean.setMaxpwr(ConverterUtil.stringToFloat(maxpwr.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.pwp.getName())) {
		CheckBox pwp = (CheckBox) engineFormField;
		engineModelBean.setPwp(Boolean.valueOf(pwp.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minpwp.getName())) {
		TextField minpwp = (TextField) engineFormField;
		engineModelBean.setMinpwp(ConverterUtil.stringToFloat(minpwp.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxpwp.getName())) {
		TextField maxpwp = (TextField) engineFormField;
		engineModelBean.setMaxpwp(ConverterUtil.stringToFloat(maxpwp.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.mp_.getName())) {
		CheckBox mp_ = (CheckBox) engineFormField;
		engineModelBean.setMp_(Boolean.valueOf(mp_.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minmp.getName())) {
		TextField minmp = (TextField) engineFormField;
		engineModelBean.setMinmp_(ConverterUtil.stringToFloat(minmp.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxmp.getName())) {
		TextField maxmp = (TextField) engineFormField;
		engineModelBean.setMaxmp_(ConverterUtil.stringToFloat(maxmp.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.egt1.getName())) {
		CheckBox egt1 = (CheckBox) engineFormField;
		engineModelBean.setEgt1(Boolean.valueOf(egt1.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minegt1.getName())) {
		TextField minegt1 = (TextField) engineFormField;
		engineModelBean.setMinegt1(ConverterUtil.stringToFloat(minegt1.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxegt1.getName())) {
		TextField maxegt1 = (TextField) engineFormField;
		engineModelBean.setMaxegt1(ConverterUtil.stringToFloat(maxegt1.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.egt2.getName())) {
		CheckBox egt2 = (CheckBox) engineFormField;
		engineModelBean.setEgt2(Boolean.valueOf(egt2.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minegt2.getName())) {
		TextField minegt2 = (TextField) engineFormField;
		engineModelBean.setMinegt2(ConverterUtil.stringToFloat(minegt2.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxegt2.getName())) {
		TextField maxegt2 = (TextField) engineFormField;
		engineModelBean.setMaxegt2(ConverterUtil.stringToFloat(maxegt2.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.cht1.getName())) {
		CheckBox cht1 = (CheckBox) engineFormField;
		engineModelBean.setCht1(Boolean.valueOf(cht1.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.mincht1.getName())) {
		TextField mincht1 = (TextField) engineFormField;
		engineModelBean.setMincht1(ConverterUtil.stringToFloat(mincht1.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxcht1.getName())) {
		TextField maxcht1 = (TextField) engineFormField;
		engineModelBean.setMaxcht1(ConverterUtil.stringToFloat(maxcht1.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.cht2.getName())) {
		CheckBox cht1 = (CheckBox) engineFormField;
		engineModelBean.setCht2(Boolean.valueOf(cht1.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.mincht2.getName())) {
		TextField mincht2 = (TextField) engineFormField;
		engineModelBean.setMincht2(ConverterUtil.stringToFloat(mincht2.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxcht2.getName())) {
		TextField maxcht2 = (TextField) engineFormField;
		engineModelBean.setMaxcht2(ConverterUtil.stringToFloat(maxcht2.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.est.getName())) {
		CheckBox est = (CheckBox) engineFormField;
		engineModelBean.setEst(Boolean.valueOf(est.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minest.getName())) {
		TextField minest = (TextField) engineFormField;
		engineModelBean.setMinest(ConverterUtil.stringToFloat(minest.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxest.getName())) {
		TextField maxest = (TextField) engineFormField;
		engineModelBean.setMaxest(ConverterUtil.stringToFloat(maxest.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.ff_.getName())) {
		CheckBox ff_ = (CheckBox) engineFormField;
		engineModelBean.setFf_(Boolean.valueOf(ff_.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minff.getName())) {
		TextField minff = (TextField) engineFormField;
		engineModelBean.setMinff_(ConverterUtil.stringToFloat(minff.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxff.getName())) {
		TextField maxff = (TextField) engineFormField;
		engineModelBean.setMaxff_(ConverterUtil.stringToFloat(maxff.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.fp_.getName())) {
		CheckBox fp_ = (CheckBox) engineFormField;
		engineModelBean.setFp_(Boolean.valueOf(fp_.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minfp.getName())) {
		TextField minfp = (TextField) engineFormField;
		engineModelBean.setMinfp_(ConverterUtil.stringToFloat(minfp.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxfp.getName())) {
		TextField maxfp = (TextField) engineFormField;
		engineModelBean.setMaxfp_(ConverterUtil.stringToFloat(maxfp.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.op_.getName())) {
		CheckBox op_ = (CheckBox) engineFormField;
		engineModelBean.setOp_(Boolean.valueOf(op_.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minop.getName())) {
		TextField minop = (TextField) engineFormField;
		engineModelBean.setMinop_(ConverterUtil.stringToFloat(minop.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxop.getName())) {
		TextField maxop = (TextField) engineFormField;
		engineModelBean.setMaxop_(ConverterUtil.stringToFloat(maxop.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.ot_.getName())) {
		CheckBox ot_ = (CheckBox) engineFormField;
		engineModelBean.setOt_(Boolean.valueOf(ot_.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minot.getName())) {
		TextField minot = (TextField) engineFormField;
		engineModelBean.setMinot_(ConverterUtil.stringToFloat(minot.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxot.getName())) {
		TextField maxot = (TextField) engineFormField;
		engineModelBean.setMaxot_(ConverterUtil.stringToFloat(maxot.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.n1_.getName())) {
		CheckBox n1_ = (CheckBox) engineFormField;
		engineModelBean.setN1_(Boolean.valueOf(n1_.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minn1.getName())) {
		TextField minn1 = (TextField) engineFormField;
		engineModelBean.setMinn1_(ConverterUtil.stringToFloat(minn1.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxn1.getName())) {
		TextField maxn1 = (TextField) engineFormField;
		engineModelBean.setMaxn1_(ConverterUtil.stringToFloat(maxn1.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.n2_.getName())) {
		CheckBox n2_ = (CheckBox) engineFormField;
		engineModelBean.setN2_(Boolean.valueOf(n2_.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minn2.getName())) {
		TextField minn2 = (TextField) engineFormField;
		engineModelBean.setMinn2_(ConverterUtil.stringToFloat(minn2.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxn2.getName())) {
		TextField maxn2 = (TextField) engineFormField;
		engineModelBean.setMaxn2_(ConverterUtil.stringToFloat(maxn2.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.vib.getName())) {
		CheckBox vib = (CheckBox) engineFormField;
		engineModelBean.setVib(Boolean.valueOf(vib.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minvib.getName())) {
		TextField minvib = (TextField) engineFormField;
		engineModelBean.setMinvib(ConverterUtil.stringToFloat(minvib.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxvib.getName())) {
		TextField maxvib = (TextField) engineFormField;
		engineModelBean.setMaxvib(ConverterUtil.stringToFloat(maxvib.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.vlt.getName())) {
		CheckBox vlt = (CheckBox) engineFormField;
		engineModelBean.setVlt(Boolean.valueOf(vlt.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minvlt.getName())) {
		TextField minvlt = (TextField) engineFormField;
		engineModelBean.setMinvlt(ConverterUtil.stringToFloat(minvlt.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxvlt.getName())) {
		TextField maxvlt = (TextField) engineFormField;
		engineModelBean.setMaxvlt(ConverterUtil.stringToFloat(maxvlt.getValue()));
	    }

	    else if (engineFormField.getCaption().equals(EngineModelCols.amp.getName())) {
		CheckBox amp = (CheckBox) engineFormField;
		engineModelBean.setAmp(Boolean.valueOf(amp.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.minamp.getName())) {
		TextField minamp = (TextField) engineFormField;
		engineModelBean.setMinamp(ConverterUtil.stringToFloat(minamp.getValue()));
	    } else if (engineFormField.getCaption().equals(EngineModelCols.maxamp.getName())) {
		TextField maxamp = (TextField) engineFormField;
		engineModelBean.setMaxamp(ConverterUtil.stringToFloat(maxamp.getValue()));
	    }
	}
	return engineModelBean;
    }
}
