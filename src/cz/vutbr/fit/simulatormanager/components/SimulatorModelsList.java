package cz.vutbr.fit.simulatormanager.components;

import java.sql.SQLException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Table;

import cz.vutbr.fit.simulatormanager.Constants;
import cz.vutbr.fit.simulatormanager.database.SimulatorModelQueries;
import cz.vutbr.fit.simulatormanager.database.columns.SimulatorModelCols;
import cz.vutbr.fit.simulatormanager.views.SimulatorModelsView;

public class SimulatorModelsList extends Table {
    private static final long serialVersionUID = 1L;
    final static Logger LOG = LoggerFactory.getLogger(SimulatorModelsList.class);
    private SimulatorModelsView view = null;
    private Random random = new Random();
    private final int MINRANDOM = 1000;
    private final int MAXRANDOM = 10000;
    private static final Object DEFAULT_MAX_SPEED_ON_FLAPS = 200;
    private static final int DEFAULT_NUM_OF_LANDING_GEARS = 3;

    public SimulatorModelsList(final SimulatorModelsView view) {
	this.view = view;
	updateContainerDataSource();
	setSizeFull();
	setSelectable(true);
	setImmediate(true);
	setBuffered(false);

	addValueChangeListener(new Property.ValueChangeListener() {
	    private static final long serialVersionUID = -4721755745740872033L;

	    @Override
	    public void valueChange(Property.ValueChangeEvent event) {
		Object simulatorId = SimulatorModelsList.this.getValue();
		if (simulatorId != null) {
		    view.getSimulatorModelForm().setItemDataSource(SimulatorModelsList.this.getItem(simulatorId));
		    view.getEnginesTabSheet().setEnginesForSimulator(((RowId) simulatorId).toString());
		    view.getSelectedSimulatorModelName().setValue(
			    "<b>Selected simulator model: "
				    + SimulatorModelsList.this.getItem(simulatorId)
					    .getItemProperty(SimulatorModelCols.simulatormodelname.toString()).getValue()
					    .toString() + "</b>");
		}
		// make editor visible only if simulator has been selected
		boolean shouldEditorBeVisible = simulatorId != null;
		view.getRightPanelImage().setVisible(!shouldEditorBeVisible);
		view.getFormLayout().setVisible(shouldEditorBeVisible);
		view.getSelectedSimulatorModelName().setVisible(shouldEditorBeVisible);
		view.getEnginesPanel().setVisible(shouldEditorBeVisible);
		view.getAddEngineButton().setVisible(shouldEditorBeVisible);
		view.getRemoveSimulatorModelButton().setEnabled(shouldEditorBeVisible);
		view.getSaveButton().setEnabled(shouldEditorBeVisible);
	    }
	});
    }

    public void updateContainerDataSource() {
	setContainerDataSource(view.getDbHelper().getNewSimulatorModelContainer());
	setVisibleColumns((Object[]) SimulatorModelCols.getSimulatorModelMainCols());
	setColumnHeaders(SimulatorModelCols.getSimulatorModelMainColsNames());
    }

    /**
     * This method is called when the user clicks a button removeSimulator. It
     * remove the currently selected simulator
     */
    public void removeSimulator() {
	Object simulatorId = this.getValue();
	this.removeItem(simulatorId);
	commit();
    }

    /**
     * This method is called when user clicks a button addSimulatorModel. A new
     * model is added with randomly generated values which are required to not
     * null. The new model is then commited to the database
     */
    @SuppressWarnings("unchecked")
    public void addSimulatorModel() {
	Object simulatorModelId = getContainerDataSource().addItem();
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.simulatormodelname.toString()).setValue(
		"New" + random.nextInt(MAXRANDOM - MINRANDOM) + MINRANDOM);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.maxspeedonflaps.toString()).setValue(
		DEFAULT_MAX_SPEED_ON_FLAPS);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.numberoflandinggears.toString()).setValue(
		DEFAULT_NUM_OF_LANDING_GEARS);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.lfu.toString()).setValue(Constants.LFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.minlfu.toString()).setValue(Constants.MINLFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.lowlfu.toString()).setValue(Constants.LOWLFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.highlfu.toString()).setValue(Constants.HIGHLFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.maxlfu.toString()).setValue(Constants.MAXLFU);

	this.getContainerProperty(simulatorModelId, SimulatorModelCols.cfu.toString()).setValue(Constants.CFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.mincfu.toString()).setValue(Constants.MINCFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.lowcfu.toString()).setValue(Constants.LOWCFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.highcfu.toString()).setValue(Constants.HIGHCFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.maxcfu.toString()).setValue(Constants.MAXCFU);

	this.getContainerProperty(simulatorModelId, SimulatorModelCols.rfu.toString()).setValue(Constants.RFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.minrfu.toString()).setValue(Constants.MINRFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.lowrfu.toString()).setValue(Constants.LOWRFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.highrfu.toString()).setValue(Constants.HIGHRFU);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.maxrfu.toString()).setValue(Constants.MAXRFU);

	this.getContainerProperty(simulatorModelId, SimulatorModelCols.hasgears.toString()).setValue(true);
	this.getContainerProperty(simulatorModelId, SimulatorModelCols.numberoflandinggears.toString()).setValue(1);
	commit();
	String newSimulatorModelId = SimulatorModelQueries.getLatestSimulatorModelId().toString();
	LOG.info("A new simulator model with id: {} has been added", newSimulatorModelId);
	this.view.getEnginesTabSheet().addNewEngine(newSimulatorModelId);
    }

    /**
     * Commit the data from SimulatorModelForm to database
     */
    @Override
    public void commit() {
	try {
	    /* Commit the data entered in the form to the actual item. */
	    super.commit();
	    /* Commit changes to the database. */
	    ((SQLContainer) getContainerDataSource()).commit();
	} catch (UnsupportedOperationException | SQLException e) {
	    throw new RuntimeException("Could not commit changes to simulator container. Database problem?", e);
	}
    }

    @Override
    public void discard() {
	try {
	    super.discard();
	    /* On discard, roll back the changes. */
	    ((SQLContainer) getContainerDataSource()).rollback();
	} catch (UnsupportedOperationException | SQLException e) {
	    throw new RuntimeException("Could not commit changes to simulator container. Database problem?", e);
	}
    }
}
