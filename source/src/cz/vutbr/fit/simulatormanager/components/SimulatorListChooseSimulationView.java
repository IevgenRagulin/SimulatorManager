package cz.vutbr.fit.simulatormanager.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.ui.Table;

import cz.vutbr.fit.simulatormanager.database.columns.SimulatorCols;
import cz.vutbr.fit.simulatormanager.views.ChooseSimulationView;

public class SimulatorListChooseSimulationView extends Table {
	final static Logger LOG = LoggerFactory.getLogger(SimulatorListChooseSimulationView.class);
	private static final long serialVersionUID = 1L;
	private ChooseSimulationView view = null;

	public SimulatorListChooseSimulationView(final ChooseSimulationView view) {
		super();
		this.view = view;
		setId("choose-simulation-simulator-list");
		setSizeFull();
		setDescription("Please, select a simulator");
		updateSimulatorsList();
		addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = -4721755745740872033L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				updateSimulationsList();
			}
		});

	};

	public void updateSimulationsList() {
		LOG.info("Going to update simulations list");
		RowId simulatorId = (RowId) SimulatorListChooseSimulationView.this.getValue();
		LOG.info("Simulator with row id {} is selected", simulatorId);
		// Make simulations list visible only if some simulator is
		// selected
		boolean isSimulationListVisible = (simulatorId != null);
		view.getBottomVerticalSplitPanel().setVisible(isSimulationListVisible);
		view.getSimulationSessionsLabel().setVisible(isSimulationListVisible);
		view.getBottomImage().setVisible(!isSimulationListVisible);
		// Make buttons visible only if simulation list is visible and
		// some value is selected
		view.getActionButtons().setVisible(
				view.getSimulationList().isVisible() && (view.getSimulationList().getValue() != null));
		// Set simulations list if some simulator is selected
		if (simulatorId != null) {
			String simulatorIdStr = ((Integer) (((RowId) simulatorId).getId()[0])).toString();
			view.getSimulationList().setContainerDataSourceAndVisCol(
					view.getDBHelp().getSimulationContainerOnSimulatorWithId(simulatorIdStr));
			updateSimulationsLabelMessage();
		}
	}

	private void updateSimulationsLabelMessage() {
		if (view.getSimulationList().getItemIds().size() > 0) {
			view.getSimulationSessionsLabel().setValue(ChooseSimulationView.SIMULATIONS_ON_SELECTED_SIMULATOR);
		} else {
			view.getSimulationSessionsLabel().setValue(ChooseSimulationView.NO_SIMULATIONS_ON_SELECTED_SIMULATOR);
		}
	}

	public void updateSimulatorsList() {
		setContainerDataSource(view.getDBHelp().getNewSimulatorContainer());
		LOG.info("Got new container datasource with {} elements", getContainerDataSource().getItemIds().size());
		setSelectable(true);
		setImmediate(true);
		setBuffered(false);
		setVisibleColumns((Object[]) SimulatorCols.getSimulatorMainCols());
		setColumnHeaders(SimulatorCols.getSimulatorMainColsNames());
		markAsDirtyRecursive();
	}
}
