package cz.vutbr.fit.simulatormanager.jscomponents.enginesfuelpanel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.AbstractJavaScriptComponent;

import cz.vutbr.fit.simulatormanager.beans.AllEngineInfo;
import cz.vutbr.fit.simulatormanager.beans.EngineModelBean;
import cz.vutbr.fit.simulatormanager.beans.SimulationDevStateBean;
import cz.vutbr.fit.simulatormanager.beans.SimulatorModelBean;
import cz.vutbr.fit.simulatormanager.database.EngineModelQueries;
import cz.vutbr.fit.simulatormanager.database.SimulatorModelQueries;
import cz.vutbr.fit.simulatormanager.database.columns.SimulatorModelCols;

/**
 * Engines and fuel Panel contains gauges for displaying engines and fuel state
 * 
 * @author zhenia
 *
 */
@com.vaadin.annotations.JavaScript({ "http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js",
	"http://code.jquery.com/ui/1.11.4/jquery-ui.js", "enginesAndFuelPanel.js" })
@com.vaadin.annotations.StyleSheet({ "http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" })
public class EnginesAndFuelPanel extends AbstractJavaScriptComponent {

    private final static Logger LOG = LoggerFactory.getLogger(EnginesAndFuelPanel.class);

    private static final long serialVersionUID = 1L;
    // we use these variables to call getState() less often as it generates
    // update
    // event and sends it to ui
    private AllEngineInfo prevEngineInfo = new AllEngineInfo();
    private Map<Integer, EngineModelBean> prevEngineModel = new HashMap<>();
    private SimulationDevStateBean prevSimulationDevState = new SimulationDevStateBean();
    private SimulatorModelBean prevSimulatorModel = new SimulatorModelBean();
    private boolean isStateInitialized = false;

    public EnginesAndFuelPanel() {
	LOG.info("new EnginesPanel()");
	setWidth("1200px");
    }

    public void updateIndividualEngineValues(String simulatorId, AllEngineInfo enginesInfo,
	    SimulationDevStateBean simulationDevState) {
	LOG.debug("Update individual engine values: {}, enginesInfo: {}, simulationDevState: {}", simulatorId, enginesInfo,
		simulationDevState);
	initModelIfNeeded(simulatorId);
	updateEngineValuesIfNeeded(enginesInfo);
	updateFuelTanksValuesIfNeeded(simulationDevState);
    }

    public void initModelIfNeeded(String simulatorId) {
	if (!isStateInitialized) {
	    SQLContainer enginesModels = EngineModelQueries.getEngineModelsBySimulatorId(simulatorId);
	    isStateInitialized = true;
	    // TODO. Verify that this works OK when number of engines in model
	    // and number of engines which AWCom is sending is different
	    getState().initializeArrays(enginesModels.size());
	}
	updateEngineModelsIfNeeded(simulatorId);
	updateSimulatorModelIfNeeded(simulatorId);
    }

    private void updateFuelTanksValuesIfNeeded(SimulationDevStateBean simulationDevState) {
	LOG.debug("setting lfuvals" + simulationDevState.getLfu());
	if (prevSimulationDevState.getLfu() != simulationDevState.getLfu()) {
	    getState().lfuvals = simulationDevState.getLfu();
	}
	if (prevSimulationDevState.getRfu() != simulationDevState.getRfu()) {
	    getState().rfuvals = simulationDevState.getRfu();
	}
	if (prevSimulationDevState.getCfu() != simulationDevState.getCfu()) {
	    getState().cfuvals = simulationDevState.getCfu();
	}
	prevSimulationDevState = simulationDevState;
    }

    private void updateEngineValuesIfNeeded(AllEngineInfo enginesInfo) {
	LOG.debug("update engine values. rpm is en" + prevEngineModel.get(0).isRpm());
	if (!areArraysEqual(prevEngineInfo.getRpm(), enginesInfo.getRpm())) {
	    LOG.debug("updateEngineValuesIfNeeded() - rpm value has changed, updating. New value[0]: {}", enginesInfo.getRpm()[0]);
	    getState().rpmvals = enginesInfo.getRpm();
	}
	if (!areArraysEqual(prevEngineInfo.getPwr(), enginesInfo.getPwr())) {
	    LOG.debug("updateEngineValuesIfNeeded() - pwr value has changed, updating. New value[0]: {}", enginesInfo.getPwr()[0]);
	    getState().pwrvals = enginesInfo.getPwr();
	    LOG.debug("new pwrvals[0]:{}", getState().pwrvals[0]);
	}
	if (!areArraysEqual(prevEngineInfo.getPwp(), enginesInfo.getPwp())) {
	    getState().pwpvals = enginesInfo.getPwp();
	}
	if (!areArraysEqual(prevEngineInfo.getMp_(), enginesInfo.getMp_())) {
	    getState().mp_vals = enginesInfo.getMp_();
	}
	if (!areArraysEqual(prevEngineInfo.getEt1(), enginesInfo.getEt1())) {
	    getState().et1vals = enginesInfo.getEt1();
	}
	if (!areArraysEqual(prevEngineInfo.getEt2(), enginesInfo.getEt2())) {
	    getState().et2vals = enginesInfo.getEt2();
	}
	if (!areArraysEqual(prevEngineInfo.getCt1(), enginesInfo.getCt2())) {
	    getState().ct1vals = enginesInfo.getCt1();
	}
	if (!areArraysEqual(prevEngineInfo.getCt2(), enginesInfo.getCt2())) {
	    getState().ct2vals = enginesInfo.getCt2();
	}
	if (!areArraysEqual(prevEngineInfo.getEst(), enginesInfo.getEst())) {
	    getState().estvals = enginesInfo.getEst();
	}
	if (!areArraysEqual(prevEngineInfo.getFf_(), enginesInfo.getFf_())) {
	    getState().ff_vals = enginesInfo.getFf_();
	}
	if (!areArraysEqual(prevEngineInfo.getFp_(), enginesInfo.getFp_())) {
	    getState().fp_vals = enginesInfo.getFp_();
	}
	if (!areArraysEqual(prevEngineInfo.getOp_(), enginesInfo.getOp_())) {
	    getState().op_vals = enginesInfo.getOp_();
	}
	if (!areArraysEqual(prevEngineInfo.getOt_(), enginesInfo.getOt_())) {
	    getState().ot_vals = enginesInfo.getOt_();
	}
	if (!areArraysEqual(prevEngineInfo.getN1_(), enginesInfo.getN1_())) {
	    getState().n1_vals = enginesInfo.getN1_();
	}
	if (!areArraysEqual(prevEngineInfo.getN2_(), enginesInfo.getN2_())) {
	    getState().n2_vals = enginesInfo.getN2_();
	}
	if (!areArraysEqual(prevEngineInfo.getVib(), enginesInfo.getVib())) {
	    getState().vibvals = enginesInfo.getVib();
	}
	if (!areArraysEqual(prevEngineInfo.getVlt(), enginesInfo.getVlt())) {
	    getState().vltvals = enginesInfo.getVlt();
	}
	if (!areArraysEqual(prevEngineInfo.getAmp(), enginesInfo.getAmp())) {
	    getState().ampvals = enginesInfo.getAmp();
	}
	prevEngineInfo = enginesInfo;
    }

    /**
     * Return true if corresponding elements in array are equal (with epsilon
     * 0.01 to improve performance)
     * 
     * @param array1
     * @param array2
     * @return
     */
    private boolean areArraysEqual(Float[] array1, Float[] array2) {
	if (array1 == null && array2 == null) {
	    return true;
	}
	if ((array1 == null && array2 != null) || (array2 == null && array1 != null) || (array1.length != array2.length)) {
	    return false;
	}
	for (int i = 0; i < array1.length; i++) {
	    if (Math.abs(array1[i] - array2[i]) > 0.01) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Initialize arrays and prevEngineInfo. We need this to handle the case
     * when engine models change
     * 
     * @param numberOfEngines
     */
    private void clearState(int numberOfEngines) {
	getState().initializeArrays(numberOfEngines);
	prevEngineInfo = new AllEngineInfo();
    }

    /**
     * Return true if the model state which is cached is different from the one
     * in the database. If none is cached, return true
     * 
     * @param engineModels
     * @param engineIds
     * @return
     */
    private boolean hasEngineModelChanged(SQLContainer engineModels, Collection<RowId> engineIds) {
	for (RowId engineId : engineIds) {
	    Item engineItem = engineModels.getItem(engineId);
	    EngineModelBean engineBean = new EngineModelBean(engineItem);
	    int engineModelOrder = engineBean.getEnginemodelorder();
	    if (!engineBean.equals(prevEngineModel.get(engineModelOrder))) {
		return true;
	    }
	}
	return false;
    }

    /**
     * if simulator model hasn't been initialized, then initialize it: get
     * simulator model for this simulator from database, and set them to state
     * object, so that javascript has access to lfu, cfu, rfu.
     * 
     * @param simulatorId
     */
    private void updateSimulatorModelIfNeeded(String simulatorId) {
	Item simulatorModel = SimulatorModelQueries.getSimulatorModelBySimulatorId(simulatorId);
	SimulatorModelBean simulatorModelBean = buildSimulatorModelBean(simulatorModel);
	LOG.debug("updateSimulatorModelIfNeeded() - going to compare simulator model beans. minlfu: {}, maxlfu: {} ",
		simulatorModelBean.getMinlfu(), simulatorModelBean.getMaxlfu());
	if (!simulatorModelBean.equals(prevSimulatorModel)) {
	    LOG.info("updateSimulatorModelIfNeeded() - needed! updating simulator model");
	    getState().lfu = simulatorModelBean.isLfu();
	    getState().minlfu = simulatorModelBean.getMinlfu();
	    getState().lowlfu = simulatorModelBean.getLowlfu();
	    getState().highlfu = simulatorModelBean.getHighlfu();
	    getState().maxlfu = simulatorModelBean.getMaxlfu();

	    getState().cfu = simulatorModelBean.isCfu();
	    getState().mincfu = simulatorModelBean.getMincfu();
	    getState().lowcfu = simulatorModelBean.getLowcfu();
	    getState().highcfu = simulatorModelBean.getHighcfu();
	    getState().maxcfu = simulatorModelBean.getMaxcfu();

	    getState().rfu = simulatorModelBean.isRfu();
	    getState().minrfu = simulatorModelBean.getMinrfu();
	    getState().lowrfu = simulatorModelBean.getLowrfu();
	    getState().highrfu = simulatorModelBean.getHighrfu();
	    getState().maxrfu = simulatorModelBean.getMaxrfu();

	    prevSimulatorModel = simulatorModelBean;
	}

    }

    /**
     * Builds SimulatorModelBean based on simulatorModel info from database. Get
     * only lfu, cfu, rfu info because we don't need other info
     * 
     * @param simulatorModel
     * @return
     */
    private SimulatorModelBean buildSimulatorModelBean(Item simulatorModel) {
	SimulatorModelBean simulatorModelBean = new SimulatorModelBean();
	// LFU
	simulatorModelBean.setLfu((Boolean) simulatorModel.getItemProperty(SimulatorModelCols.lfu.toString()).getValue());
	simulatorModelBean.setMinlfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.minlfu.toString()).getValue());
	simulatorModelBean.setLowlfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.lowlfu.toString()).getValue());
	simulatorModelBean.setHighlfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.highlfu.toString()).getValue());
	simulatorModelBean.setMaxlfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.maxlfu.toString()).getValue());
	// CFU
	simulatorModelBean.setCfu((Boolean) simulatorModel.getItemProperty(SimulatorModelCols.cfu.toString()).getValue());
	simulatorModelBean.setMincfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.mincfu.toString()).getValue());
	simulatorModelBean.setLowcfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.lowcfu.toString()).getValue());
	simulatorModelBean.setHighcfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.highcfu.toString()).getValue());
	simulatorModelBean.setMaxcfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.maxcfu.toString()).getValue());

	// RFU
	simulatorModelBean.setRfu((Boolean) simulatorModel.getItemProperty(SimulatorModelCols.rfu.toString()).getValue());
	simulatorModelBean.setMinrfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.minrfu.toString()).getValue());
	simulatorModelBean.setLowrfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.lowrfu.toString()).getValue());
	simulatorModelBean.setHighrfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.highrfu.toString()).getValue());
	simulatorModelBean.setMaxrfu((Float) simulatorModel.getItemProperty(SimulatorModelCols.maxrfu.toString()).getValue());

	return simulatorModelBean;
    }

    /**
     * if engine model hasn't been initialized, then initialize it: get engines
     * models for this simulator from database, and set them to state, so that
     * javascript has access to these values
     * 
     * @param simulatorId
     */
    @SuppressWarnings("unchecked")
    private void updateEngineModelsIfNeeded(String simulatorId) {
	SQLContainer enginesModels = EngineModelQueries.getEngineModelsBySimulatorId(simulatorId);
	Collection<RowId> itemIds = (Collection<RowId>) enginesModels.getItemIds();
	if (hasEngineModelChanged(enginesModels, itemIds)) {
	    clearState(itemIds.size());
	}
	LOG.debug("Going to iterate throuh engines. Num of engines: {}", itemIds.size());
	for (RowId itemId : itemIds) {
	    Item engineItem = enginesModels.getItem(itemId);
	    EngineModelBean engineBean = new EngineModelBean(engineItem);
	    int engineModelOrder = engineBean.getEnginemodelorder();
	    // update engine model if it has changed
	    LOG.debug("going to check if engine model has changed on one of the engines. isPWR:{}", engineBean.isPwr());
	    if (!engineBean.equals(prevEngineModel.get(engineModelOrder))) {
		LOG.info("updateEngineModelsIfNeeded() - needed! updating engine models");
		LOG.debug("setting rpm {} ", engineBean.isRpm());
		// RPM
		getState().rpm[engineModelOrder] = engineBean.isRpm();
		getState().minrpm[engineModelOrder] = engineBean.getMinrpm();
		getState().lowrpm[engineModelOrder] = engineBean.getLowrpm();
		getState().highrpm[engineModelOrder] = engineBean.getHighrpm();
		getState().maxrpm[engineModelOrder] = engineBean.getMaxrpm();
		// PWR
		getState().pwr[engineModelOrder] = engineBean.isPwr();
		getState().minpwr[engineModelOrder] = engineBean.getMinpwr();
		getState().lowpwr[engineModelOrder] = engineBean.getLowpwr();
		getState().highpwr[engineModelOrder] = engineBean.getHighpwr();
		getState().maxpwr[engineModelOrder] = engineBean.getMaxpwr();
		// PWP
		getState().pwp[engineModelOrder] = engineBean.isPwp();
		getState().minpwp[engineModelOrder] = engineBean.getMinpwp();
		getState().lowpwp[engineModelOrder] = engineBean.getLowpwp();
		getState().highpwp[engineModelOrder] = engineBean.getHighpwp();
		getState().maxpwp[engineModelOrder] = engineBean.getMaxpwp();
		// MP_
		getState().mp_[engineModelOrder] = engineBean.isMp_();
		getState().minmp_[engineModelOrder] = engineBean.getMinmp_();
		getState().lowmp_[engineModelOrder] = engineBean.getLowmp_();
		getState().highmp_[engineModelOrder] = engineBean.getHighmp_();
		getState().maxmp_[engineModelOrder] = engineBean.getMaxmp_();
		// EGT1
		getState().et1[engineModelOrder] = engineBean.isEgt1();
		getState().minet1[engineModelOrder] = engineBean.getMinet1();
		getState().lowet1[engineModelOrder] = engineBean.getLowet1();
		getState().highet1[engineModelOrder] = engineBean.getHighet1();
		getState().maxet1[engineModelOrder] = engineBean.getMaxet1();
		// EGT2
		getState().et2[engineModelOrder] = engineBean.isEgt2();
		getState().minet2[engineModelOrder] = engineBean.getMinet2();
		getState().lowet2[engineModelOrder] = engineBean.getLowet2();
		getState().highet2[engineModelOrder] = engineBean.getHighet2();
		getState().maxet2[engineModelOrder] = engineBean.getMaxet2();
		// CHT1
		getState().ct1[engineModelOrder] = engineBean.isCht1();
		getState().minct1[engineModelOrder] = engineBean.getMinct1();
		getState().lowct1[engineModelOrder] = engineBean.getLowct1();
		getState().highct1[engineModelOrder] = engineBean.getHighct1();
		getState().maxct1[engineModelOrder] = engineBean.getMaxct1();
		// CHT2
		getState().ct2[engineModelOrder] = engineBean.isCht2();
		getState().minct2[engineModelOrder] = engineBean.getMinct2();
		getState().lowct2[engineModelOrder] = engineBean.getLowct2();
		getState().highct2[engineModelOrder] = engineBean.getHighct2();
		getState().maxct2[engineModelOrder] = engineBean.getMaxct2();
		// EST
		getState().est[engineModelOrder] = engineBean.isEst();
		getState().minest[engineModelOrder] = engineBean.getMinest();
		getState().lowest[engineModelOrder] = engineBean.getLowest();
		getState().highest[engineModelOrder] = engineBean.getHighest();
		getState().maxest[engineModelOrder] = engineBean.getMaxest();

		getState().ff_[engineModelOrder] = engineBean.isFf_();
		getState().minff_[engineModelOrder] = engineBean.getMinff_();
		getState().lowff_[engineModelOrder] = engineBean.getLowff_();
		getState().highff_[engineModelOrder] = engineBean.getHighff_();
		getState().maxff_[engineModelOrder] = engineBean.getMaxff_();

		getState().fp_[engineModelOrder] = engineBean.isFp_();
		getState().minfp_[engineModelOrder] = engineBean.getMinfp_();
		getState().lowfp_[engineModelOrder] = engineBean.getLowfp_();
		getState().highfp_[engineModelOrder] = engineBean.getHighfp_();
		getState().maxfp_[engineModelOrder] = engineBean.getMaxfp_();

		getState().op_[engineModelOrder] = engineBean.isOp_();
		getState().minop_[engineModelOrder] = engineBean.getMinop_();
		getState().lowop_[engineModelOrder] = engineBean.getLowop_();
		getState().highop_[engineModelOrder] = engineBean.getHighop_();
		getState().maxop_[engineModelOrder] = engineBean.getMaxop_();

		getState().ot_[engineModelOrder] = engineBean.isOt_();
		getState().minot_[engineModelOrder] = engineBean.getMinot_();
		getState().lowot_[engineModelOrder] = engineBean.getLowot_();
		getState().highot_[engineModelOrder] = engineBean.getHighot_();
		getState().maxot_[engineModelOrder] = engineBean.getMaxot_();

		getState().n1_[engineModelOrder] = engineBean.isN1_();
		getState().minn1_[engineModelOrder] = engineBean.getMinn1_();
		getState().lown1_[engineModelOrder] = engineBean.getLown1_();
		getState().highn1_[engineModelOrder] = engineBean.getHighn1_();
		getState().maxn1_[engineModelOrder] = engineBean.getMaxn1_();

		getState().n2_[engineModelOrder] = engineBean.isN2_();
		getState().minn2_[engineModelOrder] = engineBean.getMinn2_();
		getState().lown2_[engineModelOrder] = engineBean.getLown2_();
		getState().highn2_[engineModelOrder] = engineBean.getHighn2_();
		getState().maxn2_[engineModelOrder] = engineBean.getMaxn2_();

		getState().vib[engineModelOrder] = engineBean.isVib();
		getState().minvib[engineModelOrder] = engineBean.getMinvib();
		getState().lowvib[engineModelOrder] = engineBean.getLowvib();
		getState().highvib[engineModelOrder] = engineBean.getHighvib();
		getState().maxvib[engineModelOrder] = engineBean.getMaxvib();

		getState().vlt[engineModelOrder] = engineBean.isVlt();
		getState().minvlt[engineModelOrder] = engineBean.getMinvlt();
		getState().lowvlt[engineModelOrder] = engineBean.getLowvlt();
		getState().highvlt[engineModelOrder] = engineBean.getHighvlt();
		getState().maxvlt[engineModelOrder] = engineBean.getMaxvlt();

		getState().amp[engineModelOrder] = engineBean.isAmp();
		getState().minamp[engineModelOrder] = engineBean.getMinamp();
		getState().lowamp[engineModelOrder] = engineBean.getLowamp();
		getState().highamp[engineModelOrder] = engineBean.getHighamp();
		getState().maxamp[engineModelOrder] = engineBean.getMaxamp();
		prevEngineModel.put(engineModelOrder, engineBean);
		LOG.info("Initialized engine model");
	    }
	}

    }

    public EnginesAndFuelPanelState getState() {
	return (EnginesAndFuelPanelState) super.getState();
    }

    /*
     * We use this bean in adition to state class, because when we access state
     * class fields, the change state event is generated even if we don't change
     * the state. So, we use this bean to imporove performance
     */
    // private EnginesPanelStateBean enginesPanelStateBean;

}
