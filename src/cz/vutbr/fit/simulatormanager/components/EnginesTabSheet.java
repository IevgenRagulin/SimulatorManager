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
	enginesContainer = view.getDbHelper().getEnginesModelsOnSimulatorModel(simulatorModelId.toString());
	@SuppressWarnings("unchecked")
	Collection<RowId> itemIds = (Collection<RowId>) enginesContainer.getItemIds();
	for (RowId itemId : itemIds) {
	    Item engineItem = enginesContainer.getItem(itemId);
	    addEngineInfoFromDatabase(engineItem);
	}
    }

    private void addEngineInfoFromDatabase(Item engineItem) {
	GridLayout engineGridLayout = new GridLayout(3, 20);
	engineGridLayout.setWidth("60%");
	engineGridLayout.setMargin(true);
	enginesForm = new EngineModelForm(this, engineGridLayout, enginesContainer);
	enginesForm.setItemDataSource(engineItem);
	Integer itemOrder = (Integer) engineItem.getItemProperty(EngineModelCols.enginemodelorder.toString())
		.getValue();
	addRemoveButtonToGrid(engineGridLayout, engineItem);
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
	Button removeEngineButton = new Button("Remove this engine");
	Integer engineModelId = (Integer) engineItem.getItemProperty(EngineModelCols.enginemodelid.toString())
		.getValue();
	RowId engineModelRowId = new RowId(engineModelId);
	removeEngineButton.setIcon(ResourceUtil.getMinusImgResource());
	removeEngineButton.addClickListener(new ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		Tab tabWithForm = getTab(grid);
		removeTab(tabWithForm);
		try {
		    enginesContainer.removeItem(engineModelRowId);
		    enginesContainer.commit();
		} catch (UnsupportedOperationException | SQLException e) {
		    Notification.show("Couldn't remove this engine. Error: " + e.getMessage());
		}
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

    /**
     * Add new engine to accordion. Changes are not commited to database. Commit
     * happens on clicking Save button
     * 
     * @param string
     */
    @SuppressWarnings("unchecked")
    public void addNewEngine(String simulatorModelId) {
	int nextEngineOrder = getNextAvailableEngineModelOrder(simulatorModelId);
	int numberOfEngines = enginesContainer.size();
	if (numberOfEngines < Constants.MAX_ENGINES_NUM) {
	    Object engineModelId = enginesContainer.addItem();
	    LOG.info("Adding new engine. engineModelId: {}", (RowId) engineModelId);
	    enginesContainer.getContainerProperty(engineModelId, EngineModelCols.simulatormodelid.toString()).setValue(
		    Integer.valueOf(simulatorModelId));
	    enginesContainer.getContainerProperty(engineModelId, EngineModelCols.enginemodelorder.toString()).setValue(
		    nextEngineOrder);
	    try {
		enginesContainer.commit();
	    } catch (SQLException e) {
		Notification.show("Couldn't add a new engine. Error: " + e.getMessage());
	    }
	    // get and set all engines data from the database
	    setEnginesForSimulator(simulatorModelId);
	} else {
	    Notification
		    .show("A new engine model can't be added to this simulator model, because the maximum number of engines is reached",
			    Notification.Type.ERROR_MESSAGE);
	}

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
	    Integer order = (Integer) enginesContainer.getItem(id)
		    .getItemProperty(EngineModelCols.enginemodelorder.toString()).getValue();
	    if ((order != null) && (order > maxOrder)) {
		maxOrder = order;
	    }
	}
	LOG.info("Getting max engine model order. Max: {} ", maxOrder);
	return maxOrder;
    }
}
