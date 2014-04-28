package com.example.testvaadin.components;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.views.SimulatorsView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.ui.TextField;

public class SimulatorForm extends FieldGroup {
	private static final int DEFAULT_NUM_OF_LANDING_GEARS = 3;
	private static final long serialVersionUID = 5886087581072819926L;
	private static final Object FAKE_HOSTNAME = "hostname.fit.vutbr.cz";
	private static final Object DEFAULT_MAX_SPEED_ON_FLAPS = 200;
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
		StringToIntegerConverter plainIntegerConverter = getStringToIntegerConverter();
		for (String fieldName : ColumnNames.getSimulatorCols()) {
			TextField field = createInputField(fieldName);
			app.getEditorLayout().addComponent(field);
			this.bind(field, fieldName);
			if (isUnformattedIntConverterNeeded(fieldName)) {
				field.setConverter(plainIntegerConverter);
			}
		}
		app.getEditorLayout().addComponent(app.getRemoveSimulatorButton());
	}

	private boolean isUnformattedIntConverterNeeded(String fieldName) {
		return fieldName.equals(ColumnNames.getSimulatorPortName());
	}

	private StringToIntegerConverter getStringToIntegerConverter() {
		StringToIntegerConverter plainIntegerConverter = new StringToIntegerConverter() {
			private static final long serialVersionUID = -7227277283837048291L;

			protected java.text.NumberFormat getFormat(Locale locale) {
				NumberFormat format = super.getFormat(locale);
				format.setGroupingUsed(false);
				return format;
			};
		};
		return plainIntegerConverter;
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

	@SuppressWarnings("unchecked")
	public void addSimulator() {
		Object simulatorId = app.getDBHelp().getSimulatorContainer().addItem();
		System.out.println("SIMULATOR ID" + simulatorId);
		System.out.println("SIMULATOR NAME"
				+ app.getSimulatorList().getContainerProperty(simulatorId,
						ColumnNames.getSimulatorNamePropName()));

		app.getSimulatorList()
				.getContainerProperty(simulatorId,
						ColumnNames.getSimulatorNamePropName())
				.setValue(
						"New" + random.nextInt(MAXRANDOM - MINRANDOM)
								+ MINRANDOM);
		app.getSimulatorList()
				.getContainerProperty(simulatorId,
						ColumnNames.getSimulatorPortName()).setValue(12322);
		app.getSimulatorList()
				.getContainerProperty(simulatorId,
						ColumnNames.getSimulatorHostname())
				.setValue(FAKE_HOSTNAME);
		app.getSimulatorList()
				.getContainerProperty(simulatorId,
						ColumnNames.getMaxspeedonflaps())
				.setValue(DEFAULT_MAX_SPEED_ON_FLAPS);
		app.getSimulatorList()
				.getContainerProperty(simulatorId,
						ColumnNames.getNumberoflandinggears()).setValue(DEFAULT_NUM_OF_LANDING_GEARS);
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
