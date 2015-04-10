package cz.vutbr.fit.simulatormanager.jscomponents.enginespanel;

import java.util.Collection;

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
    private boolean isEngineModelInitialized = false;

    public void updateIndividualEngineValues(String simulatorId, AllEngineInfo enginesInfo) {
	initEngineModelsIfNeeded(simulatorId);
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
    private void initEngineModelsIfNeeded(String simulatorId) {
	if (!isEngineModelInitialized) {
	    SQLContainer enginesModels = EngineModelQueries.getEngineModelsBySimulatorId(simulatorId);
	    Collection<RowId> itemIds = (Collection<RowId>) enginesModels.getItemIds();
	    LOG.debug("Going to iterate throuh engines. Num of engines: {}", itemIds.size());
	    int numberOfEngines = enginesModels.size();
	    getState().initializeArrays(numberOfEngines);
	    LOG.info("Going to init engines panel with engines models data");
	    for (RowId itemId : itemIds) {
		Item engineItem = enginesModels.getItem(itemId);
		EngineModelBean engineBean = new EngineModelBean(engineItem);
		LOG.info("setting rpm {} ", engineBean.isRpm());
		getState().rpm[engineBean.getEnginemodelorder()] = engineBean.isRpm();
		getState().minrpm[engineBean.getEnginemodelorder()] = engineBean.getMinrpm();
		getState().maxrpm[engineBean.getEnginemodelorder()] = engineBean.getMaxrpm();

		getState().pwr[engineBean.getEnginemodelorder()] = engineBean.isPwr();
		getState().minpwr[engineBean.getEnginemodelorder()] = engineBean.getMinpwr();
		getState().maxpwr[engineBean.getEnginemodelorder()] = engineBean.getMaxpwr();

		getState().pwp[engineBean.getEnginemodelorder()] = engineBean.isPwp();
		getState().minpwp[engineBean.getEnginemodelorder()] = engineBean.getMinpwp();
		getState().maxpwp[engineBean.getEnginemodelorder()] = engineBean.getMaxpwp();

		getState().mp_[engineBean.getEnginemodelorder()] = engineBean.isMp_();
		getState().minmp_[engineBean.getEnginemodelorder()] = engineBean.getMinmp_();
		getState().maxmp_[engineBean.getEnginemodelorder()] = engineBean.getMaxmp_();

		getState().egt1[engineBean.getEnginemodelorder()] = engineBean.isEgt1();
		getState().minegt1[engineBean.getEnginemodelorder()] = engineBean.getMinegt1();
		getState().maxegt1[engineBean.getEnginemodelorder()] = engineBean.getMaxegt1();

		getState().egt2[engineBean.getEnginemodelorder()] = engineBean.isEgt2();
		getState().minegt2[engineBean.getEnginemodelorder()] = engineBean.getMinegt2();
		getState().maxegt2[engineBean.getEnginemodelorder()] = engineBean.getMaxegt2();

		getState().cht1[engineBean.getEnginemodelorder()] = engineBean.isCht1();
		getState().mincht1[engineBean.getEnginemodelorder()] = engineBean.getMincht1();
		getState().maxcht1[engineBean.getEnginemodelorder()] = engineBean.getMaxcht1();

		getState().cht2[engineBean.getEnginemodelorder()] = engineBean.isCht2();
		getState().mincht2[engineBean.getEnginemodelorder()] = engineBean.getMincht2();
		getState().maxcht2[engineBean.getEnginemodelorder()] = engineBean.getMaxcht2();

		getState().est[engineBean.getEnginemodelorder()] = engineBean.isEst();
		getState().minest[engineBean.getEnginemodelorder()] = engineBean.getMinest();
		getState().maxest[engineBean.getEnginemodelorder()] = engineBean.getMaxest();

		getState().ff_[engineBean.getEnginemodelorder()] = engineBean.isFf_();
		getState().minff_[engineBean.getEnginemodelorder()] = engineBean.getMinff_();
		getState().maxff_[engineBean.getEnginemodelorder()] = engineBean.getMaxff_();

		getState().fp_[engineBean.getEnginemodelorder()] = engineBean.isFp_();
		getState().minfp_[engineBean.getEnginemodelorder()] = engineBean.getMinfp_();
		getState().maxfp_[engineBean.getEnginemodelorder()] = engineBean.getMaxfp_();

		getState().op_[engineBean.getEnginemodelorder()] = engineBean.isOp_();
		getState().minop_[engineBean.getEnginemodelorder()] = engineBean.getMinop_();
		getState().maxop_[engineBean.getEnginemodelorder()] = engineBean.getMaxop_();

		getState().ot_[engineBean.getEnginemodelorder()] = engineBean.isOt_();
		getState().minot_[engineBean.getEnginemodelorder()] = engineBean.getMinot_();
		getState().maxot_[engineBean.getEnginemodelorder()] = engineBean.getMaxot_();

		getState().n1_[engineBean.getEnginemodelorder()] = engineBean.isN1_();
		getState().minn1_[engineBean.getEnginemodelorder()] = engineBean.getMinn1_();
		getState().maxn1_[engineBean.getEnginemodelorder()] = engineBean.getMaxn1_();

		getState().n2_[engineBean.getEnginemodelorder()] = engineBean.isN2_();
		getState().minn2_[engineBean.getEnginemodelorder()] = engineBean.getMinn2_();
		getState().maxn2_[engineBean.getEnginemodelorder()] = engineBean.getMaxn2_();

		getState().vib[engineBean.getEnginemodelorder()] = engineBean.isVib();
		getState().minvib[engineBean.getEnginemodelorder()] = engineBean.getMinvib();
		getState().maxvib[engineBean.getEnginemodelorder()] = engineBean.getMaxvib();

		getState().vlt[engineBean.getEnginemodelorder()] = engineBean.isVlt();
		getState().minvlt[engineBean.getEnginemodelorder()] = engineBean.getMinvlt();
		getState().maxvlt[engineBean.getEnginemodelorder()] = engineBean.getMaxvlt();

		getState().amp[engineBean.getEnginemodelorder()] = engineBean.isAmp();
		getState().minamp[engineBean.getEnginemodelorder()] = engineBean.getMinamp();
		getState().maxamp[engineBean.getEnginemodelorder()] = engineBean.getMaxamp();
		isEngineModelInitialized = true;
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
