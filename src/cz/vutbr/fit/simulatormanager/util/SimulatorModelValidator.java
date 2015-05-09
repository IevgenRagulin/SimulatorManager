package cz.vutbr.fit.simulatormanager.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

import cz.vutbr.fit.simulatormanager.Constants;
import cz.vutbr.fit.simulatormanager.beans.EngineModelBean;
import cz.vutbr.fit.simulatormanager.beans.SimulatorModelBean;
import cz.vutbr.fit.simulatormanager.components.EnginesTabSheet;
import cz.vutbr.fit.simulatormanager.components.SimulatorModelForm;
import cz.vutbr.fit.simulatormanager.database.columns.EngineModelCols;
import cz.vutbr.fit.simulatormanager.database.columns.SimulatorModelCols;

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

    public static String isSimulatorModelConfiguredCorrectly(EnginesTabSheet enginesModels, SimulatorModelForm simulatorModelForm) {
	List<String> errorsAll = new ArrayList<String>();
	List<String> enginesErrors = validateEnginesConfiguration(enginesModels);
	errorsAll.addAll(enginesErrors);
	List<String> simulatorModelErrors = validateModelConfiguration(simulatorModelForm);
	errorsAll.addAll(simulatorModelErrors);
	return StringUtils.join(errorsAll.toArray(), "<br/>");
    }

    private static List<String> validateModelConfiguration(SimulatorModelForm simulatorModelForm) {
	List<String> errors = new ArrayList<String>();
	SimulatorModelBean sim = buildSimulatorModelBean(simulatorModelForm);
	LOG.debug("IS llfu" + sim.isLfu());
	addError(
		errors,
		validateVal(SimulatorModelCols.lfu, sim.isLfu(), sim.getMinlfu(), sim.getLowlfu(), sim.getHighlfu(),
			sim.getMaxlfu()));
	addError(
		errors,
		validateVal(SimulatorModelCols.cfu, sim.isCfu(), sim.getMincfu(), sim.getLowcfu(), sim.getHighcfu(),
			sim.getMaxcfu()));
	addError(
		errors,
		validateVal(SimulatorModelCols.rfu, sim.isRfu(), sim.getMinrfu(), sim.getLowrfu(), sim.getHighrfu(),
			sim.getMaxrfu()));
	return errors;
    }

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
    private static void validateEngineModelOrders(List<String> errors, Set<Integer> engineModelOrders, int numberOfEngines) {
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
	addError(
		errors,
		validateEngVal(EngineModelCols.rpm, eng.isRpm(), eng.getMinrpm(), eng.getLowrpm(), eng.getHighrpm(),
			eng.getMaxrpm(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.pwr, eng.isPwr(), eng.getMinpwr(), eng.getLowpwr(), eng.getHighpwr(),
			eng.getMaxpwr(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.pwp, eng.isPwp(), eng.getMinpwp(), eng.getLowpwp(), eng.getHighpwp(),
			eng.getMaxpwp(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.mp_, eng.isMp_(), eng.getMinmp_(), eng.getLowmp_(), eng.getHighmp_(),
			eng.getMaxmp_(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.et1, eng.isEgt1(), eng.getMinet1(), eng.getLowet1(), eng.getHighet1(),
			eng.getMaxet1(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.et2, eng.isEgt2(), eng.getMinet2(), eng.getLowet2(), eng.getHighet2(),
			eng.getMaxet2(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.ct1, eng.isCht1(), eng.getMinct1(), eng.getLowct1(), eng.getHighct1(),
			eng.getMaxct1(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.ct2, eng.isCht2(), eng.getMinct2(), eng.getLowct2(), eng.getHighct2(),
			eng.getMaxct2(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.est, eng.isEst(), eng.getMinest(), eng.getLowest(), eng.getHighest(),
			eng.getMaxest(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.ff_, eng.isFf_(), eng.getMinff_(), eng.getLowff_(), eng.getHighff_(),
			eng.getMaxff_(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.fp_, eng.isFp_(), eng.getMinfp_(), eng.getLowfp_(), eng.getHighfp_(),
			eng.getMaxfp_(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.op_, eng.isOp_(), eng.getMinop_(), eng.getLowop_(), eng.getHighop_(),
			eng.getMaxop_(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.n1_, eng.isN1_(), eng.getMinn1_(), eng.getLown1_(), eng.getHighn1_(),
			eng.getMaxn1_(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.n2_, eng.isN2_(), eng.getMinn2_(), eng.getLown2_(), eng.getHighn2_(),
			eng.getMaxn2_(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.vib, eng.isVib(), eng.getMinvib(), eng.getLowvib(), eng.getHighvib(),
			eng.getMaxvib(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.vlt, eng.isVlt(), eng.getMinvlt(), eng.getLowvlt(), eng.getHighvlt(),
			eng.getMaxvlt(), engOrd));
	addError(
		errors,
		validateEngVal(EngineModelCols.amp, eng.isAmp(), eng.getMinamp(), eng.getLowamp(), eng.getHighamp(),
			eng.getMaxamp(), engOrd));
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
    private static String validateEngVal(EngineModelCols featureName, Boolean isEnabled, Float minValue, Float lowValue,
	    Float highValue, Float maxValue, Integer engineOrder) {
	if (isEnabled == null || !isEnabled) {
	    return "";
	} else if (minValue != null && lowValue != null && highValue != null && maxValue != null && (minValue <= lowValue)
		&& (lowValue <= highValue) && (highValue <= maxValue)) {
	    return "";
	} else {
	    return new StringBuilder().append(featureName.toString().toUpperCase())
		    .append(" is enabled, but min/max are misconfigured on engine with order").append(engineOrder).toString();
	}
    }

    private static String validateVal(SimulatorModelCols featureName, Boolean isEnabled, Float minValue, Float lowValue,
	    Float highValue, Float maxValue) {
	if (isEnabled == null || !isEnabled) {
	    return "";
	} else if (minValue != null && lowValue != null && highValue != null && maxValue != null && (minValue <= lowValue)
		&& (lowValue <= highValue) && (highValue <= maxValue)) {
	    return "";
	} else {
	    return new StringBuilder().append(featureName.toString().toUpperCase())
		    .append(" is enabled, but min/max are misconfigured for this simulator model").toString();
	}
    }

    /**
     * Based on a form layout of a simulator model, build a simulator model
     * bean. We do this to simplify/make more readable validation later
     * 
     * @param simulatorForm
     * @return
     */
    private static SimulatorModelBean buildSimulatorModelBean(SimulatorModelForm simulatorForm) {
	SimulatorModelBean simulatorModelBean = new SimulatorModelBean();
	Collection<Field<?>> fields = simulatorForm.getFields();
	for (Field<?> field : fields) {
	    if (StringUtils.isNotEmpty(field.getCaption())) {
		if (field.getCaption().equals(SimulatorModelCols.minspeed.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setMinspeed(ConverterUtil.stringToInt(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.maxspeed.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setMaxspeed(ConverterUtil.stringToInt(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.minspeedonflaps.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setMinspeedonflaps(ConverterUtil.stringToInt(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.maxspeedonflaps.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setMaxspeedonflaps(ConverterUtil.stringToInt(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.hasgears.getName())) {
		    CheckBox textField = (CheckBox) field;
		    simulatorModelBean.setHasgears(Boolean.valueOf(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.numberoflandinggears.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setNumberoflandinggears(ConverterUtil.stringToInt(textField.getValue()));

		} else if (field.getCaption().equals(SimulatorModelCols.lfu.getName())) {
		    CheckBox textField = (CheckBox) field;
		    simulatorModelBean.setLfu(Boolean.valueOf(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.minlfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setMinlfu(ConverterUtil.stringToFloat(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.lowlfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setLowlfu(ConverterUtil.stringToFloat(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.highlfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setHighlfu(ConverterUtil.stringToFloat(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.maxlfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setMaxlfu(ConverterUtil.stringToFloat(textField.getValue()));

		} else if (field.getCaption().equals(SimulatorModelCols.cfu.getName())) {
		    CheckBox textField = (CheckBox) field;
		    simulatorModelBean.setCfu(Boolean.valueOf(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.mincfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setMincfu(ConverterUtil.stringToFloat(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.lowcfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setLowcfu(ConverterUtil.stringToFloat(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.highcfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setHighcfu(ConverterUtil.stringToFloat(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.maxcfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setMaxcfu(ConverterUtil.stringToFloat(textField.getValue()));

		} else if (field.getCaption().equals(SimulatorModelCols.rfu.getName())) {
		    CheckBox textField = (CheckBox) field;
		    simulatorModelBean.setRfu(Boolean.valueOf(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.minrfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setMinrfu(ConverterUtil.stringToFloat(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.lowrfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setLowrfu(ConverterUtil.stringToFloat(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.highrfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setHighrfu(ConverterUtil.stringToFloat(textField.getValue()));
		} else if (field.getCaption().equals(SimulatorModelCols.maxrfu.getName())) {
		    TextField textField = (TextField) field;
		    simulatorModelBean.setMaxrfu(ConverterUtil.stringToFloat(textField.getValue()));

		}
	    }
	}
	return simulatorModelBean;
    }

    /**
     * Based on a grid layout of an engine model, build an engine model bean. We
     * do this to simplify/make more readable validation later
     * 
     * @return
     */
    private static EngineModelBean buildEngineModelItem(GridLayout engineGrid) {
	EngineModelBean engineModelBean = new EngineModelBean();
	Iterator<Component> engineFormFields = engineGrid.iterator();
	while (engineFormFields.hasNext()) {
	    Component engineFormField = engineFormFields.next();
	    if (StringUtils.isNotEmpty(engineFormField.getCaption())) {
		if (engineFormField.getCaption().equals(EngineModelCols.enginemodelorder.getName())) {
		    TextField engineModelOrder = (TextField) engineFormField;
		    engineModelBean.setEnginemodelorder(ConverterUtil.stringToInt(engineModelOrder.getValue()));

		} else if (engineFormField.getCaption().equals(EngineModelCols.rpm.getName())) {
		    CheckBox rpm = (CheckBox) engineFormField;
		    engineModelBean.setRpm(Boolean.valueOf(rpm.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.minrpm.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setMinrpm(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowrpm.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowrpm(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highrpm.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighrpm(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowpwr.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowpwr(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highpwr.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighpwr(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowpwp.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowpwp(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highpwp.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighpwp(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowmp.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowmp_(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highmp.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighmp_(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.maxmp.getName())) {
		    TextField maxmp = (TextField) engineFormField;
		    engineModelBean.setMaxmp_(ConverterUtil.stringToFloat(maxmp.getValue()));
		}

		else if (engineFormField.getCaption().equals(EngineModelCols.et1.getName())) {
		    CheckBox et1 = (CheckBox) engineFormField;
		    engineModelBean.setEgt1(Boolean.valueOf(et1.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.minet1.getName())) {
		    TextField minet1 = (TextField) engineFormField;
		    engineModelBean.setMinet1(ConverterUtil.stringToFloat(minet1.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowet1.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowet1(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highet1.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighet1(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.maxet1.getName())) {
		    TextField maxet1 = (TextField) engineFormField;
		    engineModelBean.setMaxet1(ConverterUtil.stringToFloat(maxet1.getValue()));
		}

		else if (engineFormField.getCaption().equals(EngineModelCols.et2.getName())) {
		    CheckBox et2 = (CheckBox) engineFormField;
		    engineModelBean.setEgt2(Boolean.valueOf(et2.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.minet2.getName())) {
		    TextField minet2 = (TextField) engineFormField;
		    engineModelBean.setMinet2(ConverterUtil.stringToFloat(minet2.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowet2.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowet2(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highet2.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighet2(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.maxet2.getName())) {
		    TextField maxet2 = (TextField) engineFormField;
		    engineModelBean.setMaxet2(ConverterUtil.stringToFloat(maxet2.getValue()));
		}

		else if (engineFormField.getCaption().equals(EngineModelCols.ct1.getName())) {
		    CheckBox ct1 = (CheckBox) engineFormField;
		    engineModelBean.setCht1(Boolean.valueOf(ct1.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.minct1.getName())) {
		    TextField minct1 = (TextField) engineFormField;
		    engineModelBean.setMinct1(ConverterUtil.stringToFloat(minct1.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowct1.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowct1(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highct1.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighct1(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.maxct1.getName())) {
		    TextField maxct1 = (TextField) engineFormField;
		    engineModelBean.setMaxct1(ConverterUtil.stringToFloat(maxct1.getValue()));
		}

		else if (engineFormField.getCaption().equals(EngineModelCols.ct2.getName())) {
		    CheckBox ct1 = (CheckBox) engineFormField;
		    engineModelBean.setCht2(Boolean.valueOf(ct1.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.minct2.getName())) {
		    TextField minct2 = (TextField) engineFormField;
		    engineModelBean.setMinct2(ConverterUtil.stringToFloat(minct2.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowct2.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowct2(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highct2.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighct2(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.maxct2.getName())) {
		    TextField maxct2 = (TextField) engineFormField;
		    engineModelBean.setMaxct2(ConverterUtil.stringToFloat(maxct2.getValue()));
		}

		else if (engineFormField.getCaption().equals(EngineModelCols.est.getName())) {
		    CheckBox est = (CheckBox) engineFormField;
		    engineModelBean.setEst(Boolean.valueOf(est.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.minest.getName())) {
		    TextField minest = (TextField) engineFormField;
		    engineModelBean.setMinest(ConverterUtil.stringToFloat(minest.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowest.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowest(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highest.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighest(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowff.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowff_(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highff.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighff_(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowfp.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowfp_(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highfp.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighfp_(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowop.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowop_(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highop.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighop_(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowot.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowot_(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highot.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighot_(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lown1.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLown1_(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highn1.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighn1(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lown2.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLown2_(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highn2.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighn2_(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowvib.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowvib(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highvib.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighvib(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowvlt.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowvlt(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highvlt.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighvlt(ConverterUtil.stringToFloat(field.getValue()));
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
		} else if (engineFormField.getCaption().equals(EngineModelCols.lowamp.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setLowamp(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.highamp.getName())) {
		    TextField field = (TextField) engineFormField;
		    engineModelBean.setHighamp(ConverterUtil.stringToFloat(field.getValue()));
		} else if (engineFormField.getCaption().equals(EngineModelCols.maxamp.getName())) {
		    TextField maxamp = (TextField) engineFormField;
		    engineModelBean.setMaxamp(ConverterUtil.stringToFloat(maxamp.getValue()));
		}
	    }
	}
	return engineModelBean;
    }
}
