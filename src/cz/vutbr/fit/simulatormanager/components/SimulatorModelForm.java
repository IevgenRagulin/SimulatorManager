package cz.vutbr.fit.simulatormanager.components;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.converter.StringToFloatConverter;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import cz.vutbr.fit.simulatormanager.database.columns.SimulatorModelCols;
import cz.vutbr.fit.simulatormanager.util.ConverterUtil;
import cz.vutbr.fit.simulatormanager.views.SimulatorModelsView;

public class SimulatorModelForm extends FieldGroup {
    private static final long serialVersionUID = 1L;
    final static Logger LOG = LoggerFactory.getLogger(SimulatorModelForm.class);
    private SimulatorModelsView view;

    public SimulatorModelForm(SimulatorModelsView simulatorModelsView) {
	this.view = simulatorModelsView;
	init();
    }

    private void init() {
	setBuffered(false);
	StringToFloatConverter stringToFloatConverter = ConverterUtil.getStringToFloatConverter();
	for (SimulatorModelCols colName : SimulatorModelCols.values()) {
	    AbstractField<?> field = null;
	    // check if this is a boolean, use checkbox then
	    if (Boolean.class.equals(colName.getType())) {
		field = new CheckBox(colName.getName());
		addFieldToForm(field, colName);
	    }
	    // check if this is a timestamp field
	    else if (colName.equals(SimulatorModelCols.timestamp)) {
		DateField dateField = new DateField(colName.getName());
		dateField.setResolution(Resolution.SECOND);
		addFieldToForm(dateField, colName);
		field = dateField;
	    } else {
		TextField textField = createInputField(colName.getName());
		if (Float.class.equals(colName.getType())) {
		    textField.setConverter(stringToFloatConverter);
		}
		addFieldToForm(textField, colName);
	    }
	}
    }

    private TextField createInputField(String fieldName) {
	TextField field = new TextField(fieldName);
	field.setImmediate(true);
	field.setWidth("90%");
	field.setNullRepresentation("");
	return field;
    }

    /**
     * Adds the field to a SimulatorModelForm, binds the field with the
     * propertyId of the item from the SQLContainer
     * 
     * @param field
     * @param colName
     */
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

    /**
     * Commit the data from SimulatorModelForm to database
     */
    @Override
    public void commit() {
	try {
	    /* Commit the data entered in the form to the actual item. */
	    super.commit();
	    /* Commit changes to the database. */
	    view.getDbHelper().getSimulatorModelContainer().commit();
	} catch (UnsupportedOperationException | SQLException | CommitException e) {
	    LOG.error("Couldn't commit changes to simulator model form", e);
	    Notification.show("Error when commiting changes to simulator container. Database problem?", "",
		    Notification.Type.ERROR_MESSAGE);
	}
    }

    @Override
    public void discard() {
	try {
	    super.discard();
	    /* On discard, roll back the changes. */
	    view.getDbHelper().getSimulatorModelContainer().rollback();
	} catch (UnsupportedOperationException | SQLException e) {
	    LOG.error("Couldn't discard changes ", e);
	    Notification.show("Error when discarding changes. Database problem?", "", Notification.Type.ERROR_MESSAGE);
	}
	/* Clear the form */
	setItemDataSource(null);
    }

}
