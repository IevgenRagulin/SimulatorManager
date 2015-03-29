package cz.vutbr.fit.simulatormanager.components;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

import cz.vutbr.fit.simulatormanager.database.columns.SimulatorCols;
import cz.vutbr.fit.simulatormanager.views.SimulatorsView;

public class SimulatorListSimulatorsView extends Table {
    private static final long serialVersionUID = -7766030055886458431L;
    final static Logger LOG = LoggerFactory.getLogger(SimulatorListSimulatorsView.class);
    SimulatorsView view = null;
    private static final Object FAKE_HOSTNAME = "hostname.fit.vutbr.cz";
    private Random random = new Random();
    private final int MINRANDOM = 1000;
    private final int MAXRANDOM = 10000;

    public SimulatorListSimulatorsView(final SimulatorsView view) {
	this.view = view;
	setSizeFull();
	setContainerDataSource(view.getDBHelp().getCachedSimulatorContainer());
	setSelectable(true);
	setImmediate(true);
	setBuffered(false);
	setVisibleColumns((Object[]) SimulatorCols.getSimulatorMainCols());
	setColumnHeaders(SimulatorCols.getSimulatorMainColsNames());
	addValueChangeListener(new Property.ValueChangeListener() {
	    private static final long serialVersionUID = -4721755745740872033L;

	    @Override
	    public void valueChange(Property.ValueChangeEvent event) {
		Object simulatorId = SimulatorListSimulatorsView.this.getValue();
		if (simulatorId != null) {
		    view.getSimulatorForm().setItemDataSource(
			    view.getDBHelp().getCachedSimulatorContainer().getItem(simulatorId));
		    view.getSelectedSimulatorName().setValue(
			    "<b>Selected simulator: "
				    + SimulatorListSimulatorsView.this.getItem(simulatorId)
					    .getItemProperty(SimulatorCols.simulatorname.toString()).getValue()
					    .toString() + "</b>");
		}
		// make editor visible only if simulator has been selected
		boolean shouldEditorBeVisible = simulatorId != null;
		view.getRightPanelImage().setVisible(!shouldEditorBeVisible);
		view.getEditorLayout().setVisible(shouldEditorBeVisible);
		view.getSelectedSimulatorName().setVisible(simulatorId != null);
		view.getPingSimulatorButton().setVisible(simulatorId != null);
	    }

	});
    }

    /**
     * Add a new simulator to a database. Generate random values for fields
     * which must not be null
     */
    @SuppressWarnings("unchecked")
    public void addSimulator() {
	Object simulatorId = view.getDBHelp().getCachedSimulatorContainer().addItem();
	this.getContainerProperty(simulatorId, SimulatorCols.simulatorname.toString()).setValue(
		"New" + random.nextInt(MAXRANDOM - MINRANDOM) + MINRANDOM);
	this.getContainerProperty(simulatorId, SimulatorCols.port.toString()).setValue(12322);
	this.getContainerProperty(simulatorId, SimulatorCols.hostname.toString()).setValue(FAKE_HOSTNAME);
	this.getContainerProperty(simulatorId, SimulatorCols.active.toString()).setValue(false);
	this.getContainerProperty(simulatorId, SimulatorCols.simulatormodelid.toString()).setValue(
		getAnySimulatorModelId());
	commit();
    }

    /**
     * Gets an id of an existing simulator model
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    private Integer getAnySimulatorModelId() {
	Collection<RowId> modelsIds = (Collection<RowId>) view.getDBHelp().getSimulatorModelContainer().getItemIds();
	return (Integer) modelsIds.iterator().next().getId()[0];
    }

    /**
     * This method is called when a user clicks a button "remove simulator".
     * Removes the currently selected simulator from a database
     */
    public void removeSimulator() {
	Object simulatorId = this.getValue();
	this.removeItem(simulatorId);
	commit();
    }

    @Override
    public void commit() {
	LOG.debug("Going to commit data from simulator form");
	try {
	    /* Commit the data entered in the form to the actual item. */
	    super.commit();
	    /* Commit changes to the database. */
	    view.getDBHelp().getCachedSimulatorContainer().commit();
	} catch (UnsupportedOperationException | SQLException e) {
	    Notification.show("Error when commiting changes to simulator container. Database problem?", "",
		    Notification.Type.ERROR_MESSAGE);
	}
    }

    @Override
    public void discard() {
	try {
	    super.discard();
	    /* On discard, roll back the changes. */
	    view.getDBHelp().getCachedSimulatorContainer().rollback();
	} catch (UnsupportedOperationException | SQLException e) {
	    Notification.show("Error when discarding changes from container. Database problem?", "",
		    Notification.Type.ERROR_MESSAGE);
	}
    }
}
