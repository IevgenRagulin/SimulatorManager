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
import cz.vutbr.fit.simulatormanager.data.AppConfig;
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
    private void setDefaultValueForBooleanProperty(Object engineModelId, SQLContainer enginesContainer, EngineModelCols col) {
	enginesContainer.getContainerProperty(engineModelId, col.toString()).setValue(AppConfig.getBoolByKey(col.toString()));
    }

    @SuppressWarnings("unchecked")
    private void setDefaultValueForFloatProperty(Object engineModelId, SQLContainer enginesContainer, EngineModelCols col) {
	enginesContainer.getContainerProperty(engineModelId, col.toString()).setValue(AppConfig.getFloatByKey(col.toString()));
    }

    @SuppressWarnings("unchecked")
    private SQLContainer setNewEngineDefaultValues(SQLContainer enginesContainer, String simulatorModelId, Object engineModelId) {
	int nextEngineOrder = getNextAvailableEngineModelOrder(simulatorModelId);
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.simulatormodelid.toString()).setValue(
		Integer.valueOf(simulatorModelId));
	enginesContainer.getContainerProperty(engineModelId, EngineModelCols.enginemodelorder.toString()).setValue(
		nextEngineOrder);
	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.rpm);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minrpm);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowrpm);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highrpm);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxrpm);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.pwr);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minpwr);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowpwr);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highpwr);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxpwr);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.pwp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minpwp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowpwp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highpwp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxpwp);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.mp_);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minmp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowmp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highmp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxmp);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.et1);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minet1);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowet1);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highet1);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxet1);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.et2);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minet2);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowet2);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highet2);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxet2);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.ct1);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minct1);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowct1);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highct1);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxct1);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.ct2);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minct2);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowct2);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highct2);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxct2);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.est);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minest);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowest);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highest);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxest);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.ff_);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minff);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowff);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highff);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxff);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.fp_);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minfp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowfp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highfp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxfp);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.op_);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minop);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowop);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highop);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxop);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.ot_);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minot);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowot);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highot);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxot);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.n1_);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minn1);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lown1);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highn1);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxn1);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.n2_);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minn2);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lown2);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highn2);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxn2);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.vib);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minvib);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowvib);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highvib);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxvib);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.vlt);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minvlt);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowvlt);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highvlt);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxvlt);

	setDefaultValueForBooleanProperty(engineModelId, enginesContainer, EngineModelCols.amp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.minamp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.lowamp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.highamp);
	setDefaultValueForFloatProperty(engineModelId, enginesContainer, EngineModelCols.maxamp);

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
