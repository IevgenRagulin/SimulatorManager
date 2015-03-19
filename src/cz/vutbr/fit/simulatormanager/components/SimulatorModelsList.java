package cz.vutbr.fit.simulatormanager.components;

import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.ui.Table;

import cz.vutbr.fit.simulatormanager.database.columns.SimulatorModelCols;
import cz.vutbr.fit.simulatormanager.views.SimulatorModelsView;

public class SimulatorModelsList extends Table {
	private static final long serialVersionUID = 1L;

	public SimulatorModelsList(final SimulatorModelsView view) {
		setSizeFull();
		setContainerDataSource(view.getDbHelper().getSimulatorModelContainer());
		setSelectable(true);
		setImmediate(true);
		setBuffered(false);
		setVisibleColumns((Object[]) SimulatorModelCols.getSimulatorModelMainCols());
		setColumnHeaders(SimulatorModelCols.getSimulatorModelMainColsNames());
		addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = -4721755745740872033L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				Object simulatorId = SimulatorModelsList.this.getValue();
				if (simulatorId != null) {
					view.getSimulatorModelForm().setItemDataSource(SimulatorModelsList.this.getItem(simulatorId));
					view.getEnginesAccordeon().setEnginesForSimulator(((RowId) simulatorId).toString());
					view.getSelectedSimulatorModelName().setValue(
							"<b>Selected simulator model: "
									+ SimulatorModelsList.this.getItem(simulatorId)
											.getItemProperty(SimulatorModelCols.simulatormodelname.toString())
											.getValue().toString() + "</b>");
				}
				// make editor visible only if simulator has been selected
				boolean shouldEditorBeVisible = simulatorId != null;
				view.getRightPanelImage().setVisible(!shouldEditorBeVisible);
				view.getFormLayout().setVisible(shouldEditorBeVisible);
				view.getSelectedSimulatorModelName().setVisible(shouldEditorBeVisible);
				view.getEnginesPanel().setVisible(shouldEditorBeVisible);
				view.getAddEngineButton().setVisible(shouldEditorBeVisible);
				view.getRemoveSimulatorModelButton().setVisible(shouldEditorBeVisible);
			}

		});
	}
}
