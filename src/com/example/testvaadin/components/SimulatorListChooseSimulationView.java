package com.example.testvaadin.components;

import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.views.ChooseSimulationView;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.ui.Table;

public class SimulatorListChooseSimulationView extends Table {
	private static final long serialVersionUID = -3406488565393821554L;

	public SimulatorListChooseSimulationView(final ChooseSimulationView view) {
		setSizeFull();
		setContainerDataSource(view.getDBHelp().getSimulatorContainer());
		setSelectable(true);
		setImmediate(true);
		setBuffered(false);
		setVisibleColumns((Object[]) ColumnNames.getSimulatorMainCols());
		addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = -4721755745740872033L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				Object simulatorId = SimulatorListChooseSimulationView.this
						.getValue();
				// Make simulations list visible only if some simulator is
				// selected
				view.getSimulationList().setVisible(simulatorId != null);
				// Make buttons visible only if simulation list is visible and
				// some value is selected
				view.getActionButtons()
						.setVisible(
								view.getSimulationList().isVisible()
										&& (view.getSimulationList().getValue() != null));
				// Set simulations list if some simulator is selected
				if (simulatorId != null) {
					String simulatorIdStr = ((Integer) (((RowId) simulatorId)
							.getId()[0])).toString();
					view.getSimulationList().setContainerDataSourceAndVisCol(
							view.getDBHelp()
									.getSimulationContainerOnSimulatorWithId(
											simulatorIdStr));
					System.out
							.println("CHOSE SIMULATOR 2"
									+ view.getSimulationList()
											.getVisibleColumns().length);
				}
			}

		});
	}
}
