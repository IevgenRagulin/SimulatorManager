package cz.vutbr.fit.simulatormanager.components;

import java.sql.SQLException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;

import cz.vutbr.fit.simulatormanager.Constants;
import cz.vutbr.fit.simulatormanager.database.columns.EngineModelCols;
import cz.vutbr.fit.simulatormanager.util.ResourceUtil;
import cz.vutbr.fit.simulatormanager.views.SimulatorModelsView;

public class EnginesTabSheet extends TabSheet {
    private static final long serialVersionUID = 1L;
    final static Logger LOG = LoggerFactory.getLogger(EnginesTabSheet.class);

    private SimulatorModelsView view;
    private SQLContainer enginesContainer;
    private EngineModelForm enginesForm;

    public EnginesTabSheet(SimulatorModelsView view) {
	setSizeFull();
	this.view = view;
	setImmediate(true);
    }

    /**
     * Based on simulator id, get data about engines models from database, set
     * this data to this EnginesAccordion object
     * 
     * @param simulatorModelId
     */
    public void setEnginesForSimulator(String simulatorModelId) {
	LOG.debug("Setting engines for simulator with id: {}", simulatorModelId);
	removeAllComponents();
	enginesContainer = getEnginesContainer(simulatorModelId);
	@SuppressWarnings("unchecked")
	Collection<RowId> itemIds = (Collection<RowId>) enginesContainer.getItemIds();
	for (RowId itemId : itemIds) {
	    Item engineItem = enginesContainer.getItem(itemId);
	    addEngineInfoFromDatabase(engineItem);
	}
    }

    private void addEngineInfoFromDatabase(Item engineItem) {
	GridLayout engineGridLayout = new GridLayout(5, 20 + 18);
	engineGridLayout.setWidth("100%");
	engineGridLayout.setMargin(true);
	enginesForm = new EngineModelForm(this, engineGridLayout, enginesContainer);
	enginesForm.setItemDataSource(engineItem);
	Integer itemOrder = (Integer) engineItem.getItemProperty(EngineModelCols.enginemodelorder.toString()).getValue();
	addRemoveButtonToGrid(engineGridLayout, engineItem);
	engineGridLayout.setColumnExpandRatio(0, 0);
	engineGridLayout.setColumnExpandRatio(1, 70);
	engineGridLayout.setColumnExpandRatio(2, 70);
	engineGridLayout.setColumnExpandRatio(3, 70);
	engineGridLayout.setColumnExpandRatio(4, 70);
	addTab(engineGridLayout, "Engine " + itemOrder);

    }

    /**
     * When clicking remove button, the selected engine is removed. However,
     * changes are not commited immediately. The commit happens only after
     * clicking "Save" button
     * 
     * @param grid
     * @param engineItem
     */
    private void addRemoveButtonToGrid(GridLayout grid, Item engineItem) {
	Button removeEngineButton = new Button("Remove engine");
	Integer engineModelId = (Integer) engineItem.getItemProperty(EngineModelCols.enginemodelid.toString()).getValue();
	RowId engineModelRowId = new RowId(engineModelId);
	removeEngineButton.setIcon(ResourceUtil.getMinusImgResource());
	removeEngineButton.addClickListener(new ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		Tab tabWithForm = getTab(grid);
		removeTab(tabWithForm);
		enginesContainer.removeItem(engineModelRowId);
	    }
	});
	grid.addComponent(removeEngineButton);
    }

    /**
     * Precondition: tab have been initialized, and one of the tabs contains
     * formLayout <br/>
     * Gets the enginemodelorder from item, and sets it as tab caption
     * 
     * @param formLayout
     * @param item
     */
    public void updateTabNames(GridLayout formLayout, Item item) {
	Tab tab = getTab(formLayout);
	if (tab != null) {
	    LOG.debug("Updating tab name in engines accordion");
	    Integer itemOrder = (Integer) item.getItemProperty(EngineModelCols.enginemodelorder.toString()).getValue();
	    tab.setCaption("Engine " + itemOrder);
	} else {
	    LOG.error("Could not update tab name in engines accordion, tab is null");
	}

    }

    /**
     * Get container which contains information about all the engines for the
     * selected simulator
     * 
     * @return
     */
    public SQLContainer getEnginesContainer() {
	return enginesContainer;
    }

    /**
     * Get the engine model form containing data corresponding to this accordion
     * 
     * @return
     */
    public EngineModelForm getEnginesForm() {
	return enginesForm;
    }

    public SQLContainer getEnginesContainer(String simulatorModelId) {
	return view.getDbHelper().getEnginesModelsOnSimulatorModel(simulatorModelId);
    }

    /**
     * Add new engine to accordion. Changes are commited to database
     * 
     * @param string
     */
    public void addNewEngine(String simulatorModelId) {
	LOG.info("adding new engine on simulator" + simulatorModelId);
	if (enginesContainer == null) {
	    enginesContainer = getEnginesContainer(simulatorModelId);
	}
	int numberOfEngines = enginesContainer.size();
	if (numberOfEngines < Constants.MAX_ENGINES_NUM) {
	    Object engineModelId = enginesContainer.addItem();
	    LOG.info("Adding new engine. engineModelId: {}", (RowId) engineModelId);
	    enginesContainer = setNewEngineDefaultValues(enginesContainer, simulatorModelId, engineModelId);
	    try {
		LOG.info("Going to commit new engine..");
		enginesContainer.commit();
	    } catch (SQLException e) {
		Notification.show("Couldn't add a new engine. Error: " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
	    }
	    // get and set all engines data from the database
	    setEnginesForSimulator(simulatorModelId);
	} else {
	    Notification
		    .show("A new engine model can't be added to this simulator model, because the maximum number of engines is reached",
			    Notification.Type.ERROR_MESSAGE);
	}
    }

    @SuppressWarnings("unchecked")
    private SQLContainer setNewEngineDefaultValues(SQLContainer enginesContainer, String simulatorModelId, Object engineModelId) {
	int nextEngineOrder = getNextAvailableEngineModelOrder(simulatorModelId);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.simulatormodelid.toString()).setValue(
		Integer.valueOf(simulatorModelId));
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.enginemodelorder.toString()).setValue(
		nextEngineOrder);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.rpm.toString()).setValue(Constants.RPM);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minrpm.toString()).setValue(Constants.MINRPM);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowrpm.toString()).setValue(Constants.LOWRPM);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highrpm.toString()).setValue(Constants.HIGHRPM);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxrpm.toString()).setValue(Constants.MAXRPM);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.pwr.toString()).setValue(Constants.PWR);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minpwr.toString()).setValue(Constants.MINPWR);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowpwr.toString()).setValue(Constants.LOWPWR);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highpwr.toString()).setValue(Constants.HIGHPWR);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxpwr.toString()).setValue(Constants.MAXPWR);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.pwp.toString()).setValue(Constants.PWP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minpwp.toString()).setValue(Constants.MINPWP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowpwp.toString()).setValue(Constants.LOWPWP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highpwp.toString()).setValue(Constants.HIGHPWP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxpwp.toString()).setValue(Constants.MAXPWP);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.mp_.toString()).setValue(Constants.MP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minmp.toString()).setValue(Constants.MINMP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowmp.toString()).setValue(Constants.LOWMP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highmp.toString()).setValue(Constants.HIGHMP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxmp.toString()).setValue(Constants.MAXMP);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.et1.toString()).setValue(Constants.EGT1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minet1.toString()).setValue(Constants.MINEGT1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowet1.toString()).setValue(Constants.LOWEGT1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highet1.toString()).setValue(Constants.HIGHEGT1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxet1.toString()).setValue(Constants.MAXEGT1);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.et2.toString()).setValue(Constants.EGT2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minet2.toString()).setValue(Constants.MINEGT2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowet2.toString()).setValue(Constants.LOWEGT2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highet2.toString()).setValue(Constants.HIGHEGT2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxet2.toString()).setValue(Constants.MAXEGT2);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.ct1.toString()).setValue(Constants.CHT1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minct1.toString()).setValue(Constants.MINCHT1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowct1.toString()).setValue(Constants.LOWCHT1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highct1.toString()).setValue(Constants.HIGHCHT1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxct1.toString()).setValue(Constants.MAXCHT1);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.ct2.toString()).setValue(Constants.CHT2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minct2.toString()).setValue(Constants.MINCHT2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowct2.toString()).setValue(Constants.LOWCHT2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highct2.toString()).setValue(Constants.HIGHCHT2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxct2.toString()).setValue(Constants.MAXCHT2);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.est.toString()).setValue(Constants.EST);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minest.toString()).setValue(Constants.MINEST);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowest.toString()).setValue(Constants.LOWEST);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highest.toString()).setValue(Constants.HIGHEST);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxest.toString()).setValue(Constants.MAXEST);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.ff_.toString()).setValue(Constants.FF);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minff.toString()).setValue(Constants.MINFF);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowff.toString()).setValue(Constants.LOWFF);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highff.toString()).setValue(Constants.HIGHFF);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxff.toString()).setValue(Constants.MAXFF);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.fp_.toString()).setValue(Constants.FP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minfp.toString()).setValue(Constants.MINFP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowfp.toString()).setValue(Constants.LOWFP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highfp.toString()).setValue(Constants.HIGHFP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxfp.toString()).setValue(Constants.MAXFP);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.op_.toString()).setValue(Constants.OP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minop.toString()).setValue(Constants.MINOP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowop.toString()).setValue(Constants.LOWOP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highop.toString()).setValue(Constants.HIGHOP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxop.toString()).setValue(Constants.MAXOP);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.ot_.toString()).setValue(Constants.OT);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minot.toString()).setValue(Constants.MINOT);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowot.toString()).setValue(Constants.LOWOT);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highot.toString()).setValue(Constants.HIGHOT);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxot.toString()).setValue(Constants.MAXOT);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.n1_.toString()).setValue(Constants.N1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minn1.toString()).setValue(Constants.MINN1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lown1.toString()).setValue(Constants.LOWN1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highn1.toString()).setValue(Constants.HIGHN1);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxn1.toString()).setValue(Constants.MAXN1);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.n2_.toString()).setValue(Constants.N2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minn2.toString()).setValue(Constants.MINN2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lown2.toString()).setValue(Constants.LOWN2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highn2.toString()).setValue(Constants.HIGHN2);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxn2.toString()).setValue(Constants.MAXN2);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.vib.toString()).setValue(Constants.VIB);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minvib.toString()).setValue(Constants.MINVIB);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowvib.toString()).setValue(Constants.LOWVIB);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highvib.toString()).setValue(Constants.HIGHVIB);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxvib.toString()).setValue(Constants.MAXVIB);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.vlt.toString()).setValue(Constants.VLT);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minvlt.toString()).setValue(Constants.MINVLT);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowvlt.toString()).setValue(Constants.LOWVLT);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highvlt.toString()).setValue(Constants.HIGHVLT);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxvlt.toString()).setValue(Constants.MAXVLT);

	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.amp.toString()).setValue(Constants.AMP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.minamp.toString()).setValue(Constants.MINAMP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.lowamp.toString()).setValue(Constants.LOWAMP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.highamp.toString()).setValue(Constants.HIGHAMP);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.maxamp.toString()).setValue(Constants.MAXAMP);

	return enginesContainer;
    }

    /**
     * Looks up the max engine model order on this simulator, adds 1 to it,
     * returns the value
     * 
     * @param simulatorModelId
     * @return
     */
    private int getNextAvailableEngineModelOrder(String simulatorModelId) {
	return getMaxEngineModelOrder(simulatorModelId) + 1;
    }

    /**
     * Get the biggest engine model order on simulator
     * 
     * @param simulatorModelId
     */
    private int getMaxEngineModelOrder(String simulatorModelId) {
	enginesContainer.size();
	@SuppressWarnings("unchecked")
	Collection<RowId> itemIds = (Collection<RowId>) enginesContainer.getItemIds();
	int maxOrder = -1;// -1 means there are no engines yet
	for (RowId id : itemIds) {
	    Integer order = (Integer) enginesContainer.getItem(id).getItemProperty(EngineModelCols.enginemodelorder.toString())
		    .getValue();
	    if ((order != null) && (order > maxOrder)) {
		maxOrder = order;
	    }
	}
	LOG.info("Getting max engine model order. Max: {} ", maxOrder);
	return maxOrder;
    }
}
