package com.example.testvaadin.components;

import com.example.testvaadin.NavigatorUI;
import com.example.testvaadin.SimulatorsView;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

public class SimulatorList extends Table {
	private static final long serialVersionUID = -7766030055886458431L;

	public SimulatorList(final SimulatorsView app) {
		setSizeFull();
		setContainerDataSource(app.getDBHelp().getSimulatorContainer());
		setSelectable(true);
		setImmediate(true);
		setBuffered(false);
		setVisibleColumns(NavigatorUI.getVisibleColumns());
		addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = -4721755745740872033L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				Object simulatorId = SimulatorList.this.getValue();
				if (simulatorId != null) {
					app.getSimulatorForm().setItemDataSource(
							SimulatorList.this.getItem(simulatorId));
				}
				app.getEditorLayout().setVisible(simulatorId != null);
			}

		});
	}
}
