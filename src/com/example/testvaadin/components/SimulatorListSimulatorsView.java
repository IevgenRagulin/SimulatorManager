package com.example.testvaadin.components;

import com.example.testvaadin.data.SimulatorCols;
import com.example.testvaadin.views.SimulatorsView;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

public class SimulatorListSimulatorsView extends Table {
	private static final long serialVersionUID = -7766030055886458431L;

	public SimulatorListSimulatorsView(final SimulatorsView app) {
		setSizeFull();
		setContainerDataSource(app.getDBHelp().getSimulatorContainer());
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
					app.getSimulatorForm().setItemDataSource(SimulatorListSimulatorsView.this.getItem(simulatorId));
				}
				app.getEditorLayout().setVisible(simulatorId != null);
			}

		});
	}
}
