package com.example.testvaadin.components;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.example.testvaadin.data.SimulatorCols;
import com.example.testvaadin.views.SimulatorsView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
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
		init();
	}

	private void init() {
		setBuffered(false);
		StringToIntegerConverter plainIntegerConverter = getStringToIntegerConverter();
		for (SimulatorCols colName : SimulatorCols.values()) {
			// check if this is an active field which is a boolean
			if (colName.equals(SimulatorCols.active)) {
				CheckBox checkbox = new CheckBox(colName.getName());
				addValueChangeListener(checkbox);
				addFieldToForm(checkbox, colName);
			}
			// check if this is a timestamp field
			else if (colName.equals(SimulatorCols.timestamp)) {
				DateField dateField = new DateField(colName.getName());
				dateField.setResolution(Resolution.SECOND);
				addFieldToForm(dateField, colName);
				// check if this is a port which is a numeric
			} else if (colName.equals(SimulatorCols.port)) {
				TextField field = createInputField(colName.getName());
				field.setConverter(plainIntegerConverter);
				addFieldToForm(field, colName);
			} else {
				TextField field = createInputField(colName.getName());
				addFieldToForm(field, colName);
			}
		}

		app.getEditorLayout().addComponent(app.getRemoveSimulatorButton());
	}

	@SuppressWarnings("rawtypes")
	private void addFieldToForm(AbstractField field, SimulatorCols colName) {
		app.getEditorLayout().addComponent(field);
		if (!StringUtils.isEmpty(colName.getDescription())) {
			field.setDescription(colName.getDescription());
		} else {
			field.setDescription(colName.getName());
		}
		this.bind(field, colName.toString());
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

	@SuppressWarnings("rawtypes")
	private void addValueChangeListener(AbstractField field) {
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
		app.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.simulatorname.toString())
				.setValue("New" + random.nextInt(MAXRANDOM - MINRANDOM) + MINRANDOM);
		app.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.port.toString()).setValue(12322);
		app.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.hostname.toString()).setValue(FAKE_HOSTNAME);
		app.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.maxspeedonflaps.toString())
				.setValue(DEFAULT_MAX_SPEED_ON_FLAPS);
		app.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.numberoflandinggears.toString())
				.setValue(DEFAULT_NUM_OF_LANDING_GEARS);
		app.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.active.toString()).setValue(false);
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
