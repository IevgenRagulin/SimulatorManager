package cz.vutbr.fit.simulatormanager.jscomponents.enginespanel;

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
import cz.vutbr.fit.simulatormanager.database.EngineModelQueries;

@com.vaadin.annotations.JavaScript({ "enginesPanel.js" })
public class EnginesPanel extends AbstractJavaScriptComponent {

    final static Logger LOG = LoggerFactory.getLogger(EnginesPanel.class);

    private static final long serialVersionUID = 1L;
    private static final String CSS_CLASS = "ENGINES_PANEL";
    // we use these variables to call getState() less often as it generates
    // update
    // event and sends it to ui
    private AllEngineInfo prevEngineInfo = new AllEngineInfo();
    private Map<Integer, EngineModelBean> prevEngineModel = new HashMap<>();
    private boolean isStateInitialized = false;

    public EnginesPanel() {
	LOG.info("new EnginesPanel()");
	setWidth("1200px");
    }

    public void updateIndividualEngineValues(String simulatorId, AllEngineInfo enginesInfo) {
	if (!isStateInitialized) {
	    isStateInitialized = true;
	    getState().initializeArrays(enginesInfo.getNumberOfEngines());
	}
	updateEngineModelsIfNeeded(simulatorId);
	updateEngineValuesIfNeeded(enginesInfo);
    }

    private void updateEngineValuesIfNeeded(AllEngineInfo enginesInfo) {
	LOG.info("update engine values. rpm is en" + prevEngineModel.get(0).isRpm());
	if (!areArraysEqual(prevEngineInfo.getRpm(), enginesInfo.getRpm())) {
	    getState().rpmvals = enginesInfo.getRpm();
	}
	if (!areArraysEqual(prevEngineInfo.getPwr(), enginesInfo.getPwr())) {
	    getState().pwrvals = enginesInfo.getPwr();
	}
	if (!areArraysEqual(prevEngineInfo.getPwp(), enginesInfo.getPwp())) {
	    getState().pwpvals = enginesInfo.getPwp();
	}
	if (!areArraysEqual(prevEngineInfo.getMp_(), enginesInfo.getMp_())) {
	    getState().mp_vals = enginesInfo.getMp_();
	}
	if (!areArraysEqual(prevEngineInfo.getEt1(), enginesInfo.getEt1())) {
	    getState().egt1vals = enginesInfo.getEt1();
	}
	if (!areArraysEqual(prevEngineInfo.getEt2(), enginesInfo.getEt2())) {
	    getState().egt2vals = enginesInfo.getEt2();
	}
	if (!areArraysEqual(prevEngineInfo.getCt1(), enginesInfo.getCt2())) {
	    getState().cht1vals = enginesInfo.getCt1();
	}
	if (!areArraysEqual(prevEngineInfo.getCt2(), enginesInfo.getCt2())) {
	    getState().cht2vals = enginesInfo.getCt2();
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
	if ((array1 == null && array2 != null) || (array2 == null && array1 != null)
		|| (array1.length != array2.length)) {
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
     * if engine model hasn't been initialized, then initialize it: get engines
     * models for this simulator from database, and set them to state, so that
     * javascript has access to these values Note: this is done only once after
     * this object is created, so that we don't pass this data around all the
     * time
     * 
     * @param simulatorId
     */
    private void updateEngineModelsIfNeeded(String simulatorId) {
	SQLContainer enginesModels = EngineModelQueries.getEngineModelsBySimulatorId(simulatorId);
	Collection<RowId> itemIds = (Collection<RowId>) enginesModels.getItemIds();
	LOG.info("Going to iterate throuh engines. Num of engines: {}", itemIds.size());
	for (RowId itemId : itemIds) {
	    Item engineItem = enginesModels.getItem(itemId);
	    EngineModelBean engineBean = new EngineModelBean(engineItem);
	    int engineModelOrder = engineBean.getEnginemodelorder();
	    // update engine model if it has changed
	    if (!engineBean.equals(prevEngineModel.get(engineModelOrder))) {
		LOG.info("updateEngineModelsIfNeeded() - updating engine models");
		LOG.info("setting rpm {} ", engineBean.isRpm());
		// RPM
		getState().rpm[engineModelOrder] = engineBean.isRpm();
		getState().minrpm[engineModelOrder] = engineBean.getMinrpm();
		getState().maxrpm[engineModelOrder] = engineBean.getMaxrpm();
		// PWR
		getState().pwr[engineModelOrder] = engineBean.isPwr();
		getState().minpwr[engineModelOrder] = engineBean.getMinpwr();
		getState().maxpwr[engineModelOrder] = engineBean.getMaxpwr();
		// PWP
		getState().pwp[engineModelOrder] = engineBean.isPwp();
		getState().minpwp[engineModelOrder] = engineBean.getMinpwp();
		getState().maxpwp[engineModelOrder] = engineBean.getMaxpwp();

		getState().mp_[engineModelOrder] = engineBean.isMp_();
		getState().minmp_[engineModelOrder] = engineBean.getMinmp_();
		getState().maxmp_[engineModelOrder] = engineBean.getMaxmp_();

		getState().egt1[engineModelOrder] = engineBean.isEgt1();
		getState().minegt1[engineModelOrder] = engineBean.getMinegt1();
		getState().maxegt1[engineModelOrder] = engineBean.getMaxegt1();

		getState().egt2[engineModelOrder] = engineBean.isEgt2();
		getState().minegt2[engineModelOrder] = engineBean.getMinegt2();
		getState().maxegt2[engineModelOrder] = engineBean.getMaxegt2();

		getState().cht1[engineModelOrder] = engineBean.isCht1();
		getState().mincht1[engineModelOrder] = engineBean.getMincht1();
		getState().maxcht1[engineModelOrder] = engineBean.getMaxcht1();

		getState().cht2[engineModelOrder] = engineBean.isCht2();
		getState().mincht2[engineModelOrder] = engineBean.getMincht2();
		getState().maxcht2[engineModelOrder] = engineBean.getMaxcht2();

		getState().est[engineModelOrder] = engineBean.isEst();
		getState().minest[engineModelOrder] = engineBean.getMinest();
		getState().maxest[engineModelOrder] = engineBean.getMaxest();

		getState().ff_[engineModelOrder] = engineBean.isFf_();
		getState().minff_[engineModelOrder] = engineBean.getMinff_();
		getState().maxff_[engineModelOrder] = engineBean.getMaxff_();

		getState().fp_[engineModelOrder] = engineBean.isFp_();
		getState().minfp_[engineModelOrder] = engineBean.getMinfp_();
		getState().maxfp_[engineModelOrder] = engineBean.getMaxfp_();

		getState().op_[engineModelOrder] = engineBean.isOp_();
		getState().minop_[engineModelOrder] = engineBean.getMinop_();
		getState().maxop_[engineModelOrder] = engineBean.getMaxop_();

		getState().ot_[engineModelOrder] = engineBean.isOt_();
		getState().minot_[engineModelOrder] = engineBean.getMinot_();
		getState().maxot_[engineModelOrder] = engineBean.getMaxot_();

		getState().n1_[engineModelOrder] = engineBean.isN1_();
		getState().minn1_[engineModelOrder] = engineBean.getMinn1_();
		getState().maxn1_[engineModelOrder] = engineBean.getMaxn1_();

		getState().n2_[engineModelOrder] = engineBean.isN2_();
		getState().minn2_[engineModelOrder] = engineBean.getMinn2_();
		getState().maxn2_[engineModelOrder] = engineBean.getMaxn2_();

		getState().vib[engineModelOrder] = engineBean.isVib();
		getState().minvib[engineModelOrder] = engineBean.getMinvib();
		getState().maxvib[engineModelOrder] = engineBean.getMaxvib();

		getState().vlt[engineModelOrder] = engineBean.isVlt();
		getState().minvlt[engineModelOrder] = engineBean.getMinvlt();
		getState().maxvlt[engineModelOrder] = engineBean.getMaxvlt();

		getState().amp[engineModelOrder] = engineBean.isAmp();
		getState().minamp[engineModelOrder] = engineBean.getMinamp();
		getState().maxamp[engineModelOrder] = engineBean.getMaxamp();
		prevEngineModel.put(engineModelOrder, engineBean);
		LOG.info("Initialized engine model");
	    }
	}

    }

    public EnginesPanelState getState() {
	return (EnginesPanelState) super.getState();
    }

    /*
     * We use this bean in adition to state class, because when we access state
     * class fields, the change state event is generated even if we don't change
     * the state. So, we use this bean to imporove performance
     */
    // private EnginesPanelStateBean enginesPanelStateBean;

}
