package com.example.testvaadin.components;

import java.sql.SQLException;

import com.example.testvaadin.data.SimulationCols;
import com.example.testvaadin.views.ChooseSimulationView;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Table;

public class SimulationList extends Table {
	private static final long serialVersionUID = 8353709397675469654L;
	private ChooseSimulationView view = null;

	public SimulationList(final ChooseSimulationView view) {
		this.view = view;
		setSizeFull();
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
			}

		});
	}

	protected void setContainerDataSourceAndVisCol(SQLContainer newDataSource) {
		setContainerDataSource(newDataSource);
		setColumnHeaders(SimulationCols.getSimulationColsNames());
		setVisibleColumns((Object[]) SimulationCols.getVisibleCols());
	}

	@Override
	public void commit() {
		try {
			/* Commit the data entered in the person form to the actual item. */
			super.commit();
			/* Commit changes to the database. */
			SQLContainer container = (SQLContainer) this.getContainerDataSource();
			container.commit();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeSimulation() {
		Object simulationId = this.getValue();
		this.removeItem(simulationId);
		commit();
	}

}
