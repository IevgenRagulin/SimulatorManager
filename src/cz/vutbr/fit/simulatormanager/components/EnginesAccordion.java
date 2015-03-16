package cz.vutbr.fit.simulatormanager.components;

import java.sql.SQLException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;

import cz.vutbr.fit.simulatormanager.database.columns.EngineModelCols;
import cz.vutbr.fit.simulatormanager.util.ResourceUtil;
import cz.vutbr.fit.simulatormanager.views.SimulatorModelsView;

public class EnginesAccordion extends Accordion {
	private static final long serialVersionUID = 1L;
	final static Logger LOG = LoggerFactory.getLogger(EnginesAccordion.class);

	private SimulatorModelsView view;
	private SQLContainer enginesContainer;

	public EnginesAccordion(SimulatorModelsView view) {
		setSizeFull();
		this.view = view;
		setImmediate(true);
	}

	public void setEnginesForSimulator(String simulatorModelId) {
		LOG.info("Setting engines for simulator with id: {}", simulatorModelId);
		removeAllComponents();
		enginesContainer = view.getDbHelper().getEnginesModelsOnSimulatorModel(simulatorModelId.toString());
		Collection<RowId> itemIds = (Collection<RowId>) enginesContainer.getItemIds();
		for (RowId itemId : itemIds) {
			Item engineItem = enginesContainer.getItem(itemId);
			addEngineInfoFromDatabase(engineItem);
		}
	}

	private void addEngineInfoFromDatabase(Item engineItem) {
		FormLayout engineFormLayout = new FormLayout();
		EngineModelForm form = new EngineModelForm(this, engineFormLayout, enginesContainer);
		form.setItemDataSource(engineItem);
		Integer itemOrder = (Integer) engineItem.getItemProperty(EngineModelCols.enginemodelorder.toString())
				.getValue();
		addRemoveButtonToForm(engineFormLayout, engineItem);
		addTab(engineFormLayout, "Engine " + itemOrder);
	}

	private void addRemoveButtonToForm(FormLayout form, Item engineItem) {
		Button removeEngineButton = new Button("Remove this engine");
		Integer engineModelId = (Integer) engineItem.getItemProperty(EngineModelCols.enginemodelid.toString())
				.getValue();
		RowId engineModelRowId = new RowId(engineModelId);
		Item engineItemFromDb = enginesContainer.getItem(engineModelRowId);
		removeEngineButton.setIcon(ResourceUtil.getMinusImgResource());
		removeEngineButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Tab tabWithForm = getTab(form);
				removeTab(tabWithForm);
				try {
					enginesContainer.removeItem(engineModelRowId);
					enginesContainer.commit();
				} catch (UnsupportedOperationException | SQLException e) {
					throw new RuntimeException("There was an error removing engine from database ", e);
				}
			}
		});
		form.addComponent(removeEngineButton);
	}

	/**
	 * Precondition: tab have been initialized, and one of the tabs contains
	 * formLayout <br/>
	 * Gets the enginemodelorder from item, and sets it as tab caption
	 * 
	 * @param formLayout
	 * @param item
	 */
	public void updateTabNames(FormLayout formLayout, Item item) {
		Tab tab = getTab(formLayout);
		if (tab != null) {
			LOG.info("Updating tab name in engines accordion");
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
	 * Add new engine to database and to accordion
	 * 
	 * @param string
	 */
	public void addNewEngine(String simulatorModelId) {
		Object engineModelId = enginesContainer.addItem();
		LOG.info("Adding new engine. engineModelId: {}", (RowId) engineModelId);
		enginesContainer.getContainerProperty(engineModelId, EngineModelCols.simulatormodelid.toString()).setValue(
				Integer.valueOf(simulatorModelId));
		enginesContainer.getContainerProperty(engineModelId, EngineModelCols.enginemodelorder.toString()).setValue(
				getNextAvailableEngineModelOrder(simulatorModelId));
		try {
			enginesContainer.commit();
		} catch (UnsupportedOperationException | SQLException e) {
			throw new RuntimeException("Error occured while adding new engine to simulator ", e);
		}
		// get and set all engines data from the database
		setEnginesForSimulator(simulatorModelId);
	}

	public void removeEngine(String simulatorModelId) {

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
		Collection<RowId> itemIds = (Collection<RowId>) enginesContainer.getItemIds();
		int maxOrder = 0;
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