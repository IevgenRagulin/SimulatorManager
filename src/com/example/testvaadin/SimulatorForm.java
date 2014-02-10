package com.example.testvaadin;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.TextField;

public class SimulatorForm extends FieldGroup {

	private static final long serialVersionUID = 5886087581072819926L;
	private TestvaadinUI app;

	public SimulatorForm(TestvaadinUI app) {
		this.app = app;
		initSimulatorForm();
	}

	private void initSimulatorForm() {
		setBuffered(false);
		for (String fieldName : app.getColumnNames()) {
			TextField field = createInputField(fieldName);
			app.getEditorLayout().addComponent(field);
			this.bind(field, fieldName);
		}
		app.getEditorLayout().addComponent(app.getRemoveSimulatorButton());
	}

	private TextField createInputField(String fieldName) {
		TextField field = new TextField(fieldName);
		addValueChangeListener(field);
		field.setWidth("100%");
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
				.getContainerProperty(simulatorId, app.getSimNamePropertyName())
				.setValue("New");
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
