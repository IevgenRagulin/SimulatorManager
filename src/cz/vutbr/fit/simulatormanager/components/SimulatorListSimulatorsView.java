package cz.vutbr.fit.simulatormanager.components;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;

import cz.vutbr.fit.simulatormanager.database.columns.SimulatorCols;
import cz.vutbr.fit.simulatormanager.views.SimulatorsView;

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
					app.getSelectedSimulatorName().setValue(
							"<b>Selected simulator: "
									+ SimulatorListSimulatorsView.this.getItem(simulatorId)
											.getItemProperty(SimulatorCols.simulatorname.toString()).getValue()
											.toString() + "</b>");
				}
				// make editor visible only if simulator has been selected
				boolean shouldEditorBeVisible = simulatorId != null;
				app.getRightPanelImage().setVisible(!shouldEditorBeVisible);
				app.getEditorLayout().setVisible(shouldEditorBeVisible);
				app.getSelectedSimulatorName().setVisible(simulatorId != null);
				app.getPingSimulatorButton().setVisible(simulatorId != null);
			}

		});
	}
}
