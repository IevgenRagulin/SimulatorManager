package cz.vutbr.fit.simulatormanager.components;

import java.sql.SQLException;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

import cz.vutbr.fit.simulatormanager.database.columns.SimulatorModelCols;
import cz.vutbr.fit.simulatormanager.views.SimulatorModelsView;

public class SimulatorModelForm extends FieldGroup {

	private SimulatorModelsView view;
	private Random random = new Random();
	private final int MINRANDOM = 1000;
	private final int MAXRANDOM = 10000;
	private static final Object DEFAULT_MAX_SPEED_ON_FLAPS = 200;
	private static final int DEFAULT_NUM_OF_LANDING_GEARS = 3;

	public SimulatorModelForm(SimulatorModelsView simulatorModelsView) {
		this.view = simulatorModelsView;
		init();
	}

	private void init() {
		setBuffered(false);
		for (SimulatorModelCols colName : SimulatorModelCols.values()) {
			// check if this is an active field which is a boolean
			if (colName.equals(SimulatorModelCols.hasgears)) {
				CheckBox checkbox = new CheckBox(colName.getName());
				addValueChangeListener(checkbox);
				addFieldToForm(checkbox, colName);
			}
			// check if this is a timestamp field
			else if (colName.equals(SimulatorModelCols.timestamp)) {
				DateField dateField = new DateField(colName.getName());
				dateField.setResolution(Resolution.SECOND);
				addFieldToForm(dateField, colName);
				// check if this is a port which is a numeric
			} else {
				TextField field = createInputField(colName.getName());
				addFieldToForm(field, colName);
			}
		}
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
	private void addFieldToForm(AbstractField field, SimulatorModelCols colName) {
		view.getFormLayout().addComponent(field);
		if (!StringUtils.isEmpty(colName.getDescription())) {
			field.setDescription(colName.getDescription());
		} else {
			field.setDescription(colName.getName());
		}
		this.bind(field, colName.toString());
	}

	@SuppressWarnings("rawtypes")
	private void addValueChangeListener(AbstractField field) {
		field.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				SimulatorModelForm.this.commit();
			}
		});
	}

	@Override
	public void commit() {
		try {
			/* Commit the data entered in the form to the actual item. */
			super.commit();
			/* Commit changes to the database. */
			view.getDbHelper().getSimulatorModelContainer().commit();
		} catch (UnsupportedOperationException | SQLException | CommitException e) {
			throw new RuntimeException("Could not commit changes to simulator container. Database problem?", e);
		}
	}

	@Override
	public void discard() {
		try {
			super.discard();
			/* On discard, roll back the changes. */
			view.getDbHelper().getSimulatorModelContainer().rollback();
		} catch (UnsupportedOperationException | SQLException e) {
			throw new RuntimeException("Could not commit changes to simulator container. Database problem?", e);
		}
		/* Clear the form */
		setItemDataSource(null);
	}

	@SuppressWarnings("unchecked")
	public void addSimulatorModel() {
		Object simulatorModelId = view.getDbHelper().getSimulatorModelContainer().addItem();
		view.getSimulatorModelList()
				.getContainerProperty(simulatorModelId, SimulatorModelCols.simulatormodelname.toString())
				.setValue("New" + random.nextInt(MAXRANDOM - MINRANDOM) + MINRANDOM);

		view.getSimulatorModelList()
				.getContainerProperty(simulatorModelId, SimulatorModelCols.maxspeedonflaps.toString())
				.setValue(DEFAULT_MAX_SPEED_ON_FLAPS);
		view.getSimulatorModelList()
				.getContainerProperty(simulatorModelId, SimulatorModelCols.numberoflandinggears.toString())
				.setValue(DEFAULT_NUM_OF_LANDING_GEARS);
		commit();
	}

	public void removeSimulator() {
		Object simulatorId = view.getSimulatorModelList().getValue();
		view.getSimulatorModelList().removeItem(simulatorId);
		commit();
	}

}
