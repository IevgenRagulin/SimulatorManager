package com.example.testvaadin.components;

import java.sql.SQLException;
import java.util.Random;

import com.example.testvaadin.SimulatorsView;
import com.example.testvaadin.data.ColumnNames;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.TextField;

public class SimulatorForm extends FieldGroup {
	private static final long serialVersionUID = 5886087581072819926L;
	private SimulatorsView app;
	private Random random = new Random();
	private final int MINRANDOM = 1000;
	private final int MAXRANDOM = 10000;

	public SimulatorForm(SimulatorsView app) {
		this.app = app;
		initSimulatorForm();
	}

	private void initSimulatorForm() {
		setBuffered(false);
		for (String fieldName : ColumnNames.getSimulatorCols()) {
			TextField field = createInputField(fieldName);
			app.getEditorLayout().addComponent(field);
			this.bind(field, fieldName);
		}
		app.getEditorLayout().addComponent(app.getRemoveSimulatorButton());
	}

	private TextField createInputField(String fieldName) {
		TextField field = new TextField(fieldName);
		addValueChangeListener(field);
		field.setImmediate(true);
		field.setWidth("90%");
		field.setNullRepresentation("");
		return field;
	}

	private void addValueChangeListener(TextField field) {
		field.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				SimulatorForm.this.commit();
			}
		});
	}

	public void addSimulator() {
		Object simulatorId = app.getDBHelp().getSimulatorContainer().addItem();
		app.getSimulatorList()
				.getContainerProperty(simulatorId,
						ColumnNames.getSimulatorNamePropName())
				.setValue(
						"New" + random.nextInt(MAXRANDOM - MINRANDOM)
								+ MINRANDOM);
		commit();
	}

	public void removeSimulator() {
		Object simulatorId = app.getSimulatorList().getValue();
		app.getSimulatorList().removeItem(simulatorId);
		commit();
	}

	@Override
	public void commit() {
		try {
			/* Commit the data entered in the person form to the actual item. */
			super.commit();
			/* Commit changes to the database. */
			app.getDBHelp().getSimulatorContainer().commit();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CommitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void discard() {
		try {
			super.discard();
			/* On discard, roll back the changes. */
			app.getDBHelp().getSimulatorContainer().rollback();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/* Clear the form */
		setItemDataSource(null);
	}
}
