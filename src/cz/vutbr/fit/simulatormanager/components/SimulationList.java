package cz.vutbr.fit.simulatormanager.components;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

import cz.vutbr.fit.simulatormanager.database.columns.SimulationCols;
import cz.vutbr.fit.simulatormanager.views.ChooseSimulationView;

public class SimulationList extends Table {
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = LoggerFactory.getLogger(SimulationList.class);
    private ChooseSimulationView view = null;

    public SimulationList(final ChooseSimulationView view) {
	this.view = view;
	setSizeFull();
	setWidth("95%");
	setSelectable(true);
	setContainerDataSourceAndVisCol(view.getDBHelp().getSimulationContainer());
	setImmediate(true);

	setBuffered(false);
	addValueChangeListener(new Property.ValueChangeListener() {
	    private static final long serialVersionUID = -4721755745740872033L;

	    @Override
	    public void valueChange(Property.ValueChangeEvent event) {
		Object simulationId = SimulationList.this.getValue();
		view.getActionButtons().setVisible(simulationId != null);
		view.getFinishSimulationButton().setVisible(simulationId != null && view.isSelectedSimulationRunning());
	    }

	});
    }

    protected void setContainerDataSourceAndVisCol(SQLContainer newDataSource) {
	setContainerDataSource(newDataSource);
	setColumnHeaders(SimulationCols.getSimulationColsNames());
	setVisibleColumns((Object[]) SimulationCols.getVisibleCols());
	setSortAscending(false);
	setSortEnabled(true);
	setSortContainerPropertyId("simulationid");
	sort();
    }

    @Override
    public void commit() {
	try {
	    super.commit();
	    /* Commit changes to the database. */
	    SQLContainer container = (SQLContainer) this.getContainerDataSource();
	    container.commit();
	} catch (UnsupportedOperationException | SQLException e) {
	    Notification.show("Could not commit simulation list", e.getMessage(), Notification.Type.ERROR_MESSAGE);
	    LOG.error("Could not commit simulation list", e);
	}
    }

    public void removeSimulation() {
	Object simulationId = this.getValue();
	this.removeItem(simulationId);
	commit();
    }

    @SuppressWarnings("unchecked")
    public void finishSimulation() {
	Object simulationId = this.getValue();
	this.getItem(simulationId).getItemProperty(SimulationCols.issimulationon.toString()).setValue(new Boolean(false));
	this.getItem(simulationId).getItemProperty(SimulationCols.issimulationpaused.toString()).setValue(new Boolean(false));
	commit();
	setContainerDataSourceAndVisCol(view.getDBHelp().getSimulationContainer());

    }

}
