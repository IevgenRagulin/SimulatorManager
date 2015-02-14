package com.example.testvaadin.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.testvaadin.data.SimulatorCols;
import com.example.testvaadin.views.ChooseSimulationView;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.ui.Table;

public class SimulatorListChooseSimulationView extends Table {
	final static Logger logger = LoggerFactory.getLogger(SimulatorListChooseSimulationView.class);
	private static final long serialVersionUID = -3406488565393821554L;
	private ChooseSimulationView view = null;

	public SimulatorListChooseSimulationView(final ChooseSimulationView view) {
		super();
		this.view = view;
		setSizeFull();
		setDescription("Please, select a simulator");
		setContainerDataSource(view.getDBHelp().getNewSimulatorContainer());
		setSelectable(true);
		setImmediate(true);
		setBuffered(false);
		setVisibleColumns((Object[]) SimulatorCols.getSimulatorMainCols());
		setColumnHeaders(SimulatorCols.getSimulatorMainColsNames());
		addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = -4721755745740872033L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				updateSimulationsList();
			}
		});
		markAsDirty();
	};

	public void updateSimulationsList() {
		logger.info("Going to update simulations list");
		RowId simulatorId = (RowId) SimulatorListChooseSimulationView.this.getValue();
		// Make simulations list visible only if some simulator is
		// selected
		logger.info("Simulator with row id {} is selected", simulatorId);
		view.getSimulationList().setVisible(simulatorId != null);
		// Make buttons visible only if simulation list is visible and
		// some value is selected
		view.getActionButtons().setVisible(
				view.getSimulationList().isVisible() && (view.getSimulationList().getValue() != null));
		// Set simulations list if some simulator is selected
		if (simulatorId != null) {
			String simulatorIdStr = ((Integer) (((RowId) simulatorId).getId()[0])).toString();
			view.getSimulationList().setContainerDataSourceAndVisCol(
					view.getDBHelp().getSimulationContainerOnSimulatorWithId(simulatorIdStr));
		}
	}
}
