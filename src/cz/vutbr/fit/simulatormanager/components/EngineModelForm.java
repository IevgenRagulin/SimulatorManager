package cz.vutbr.fit.simulatormanager.components;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

import cz.vutbr.fit.simulatormanager.Constants;
import cz.vutbr.fit.simulatormanager.database.columns.EngineModelCols;
import cz.vutbr.fit.simulatormanager.exception.IllegalEngineIdException;

public class EngineModelForm extends FieldGroup {
    private static final long serialVersionUID = 1L;
    final static Logger LOG = LoggerFactory.getLogger(EngineModelForm.class);
    private EnginesTabSheet enginesAccordion;
    private GridLayout engGrid;

    public EngineModelForm(EnginesTabSheet enginesAccordion, GridLayout formLayout, SQLContainer enginesContainer) {
	this.enginesAccordion = enginesAccordion;
	this.engGrid = formLayout;
	setBuffered(false);
	for (EngineModelCols engineModelCol : EngineModelCols.values()) {
	    // check if this is a boolean field
	    if (Boolean.class.equals(engineModelCol.getType())) {
		CheckBox checkbox = new CheckBox(engineModelCol.getName());
		addFieldToForm(checkbox, engineModelCol);
		// if simulator model id, don't add it to the form
	    } else if (engineModelCol.equals(EngineModelCols.simulatormodelid)) {

	    } else if (engineModelCol.equals(EngineModelCols.enginemodelorder)) {
		TextField field = createModelOrderInputField(engineModelCol.getName());
		field.addValidator(new IntegerRangeValidator("Engine model order must be between 0 and 7", 0,
			Constants.MAX_ENGINES_NUM - 1));
		field.addValidator(new NullValidator("Engine model order must not be null", false));
		addFieldToForm(field, engineModelCol);
	    }
	    // check if this is a timestamp field
	    else if (engineModelCol.equals(EngineModelCols.timestamp)) {
		DateField dateField = new DateField(engineModelCol.getName());
		dateField.setResolution(Resolution.SECOND);
		dateField.setEnabled(false);
		dateField.setReadOnly(true);
		addFieldToForm(dateField, engineModelCol);
	    } else {
		TextField field = createInputField(engineModelCol.getName());
		addFieldToForm(field, engineModelCol);
	    }
	}
    }

    /**
     * Create input field for engine model order
     * 
     * @param name
     * @return
     */
    private TextField createModelOrderInputField(String name) {
	TextField field = createInputField(name);
	addEngineModelOrderChangeListener(field);
	return field;
    }

    private TextField createInputField(String fieldName) {
	TextField field = new TextField(fieldName);
	field.setBuffered(false);
	field.setImmediate(true);
	field.setWidth("90%");
	field.setNullRepresentation("");
	return field;
    }

    @SuppressWarnings("rawtypes")
    private void addFieldToForm(AbstractField field, EngineModelCols colName) {
	// if this is enginemodel order, we want it to take 2 cells in the 0th
	// row
	if (colName.equals(EngineModelCols.enginemodelorder)) {
	    engGrid.addComponent(field, 1, 0, 2, 0);
	    // if this is a boolean, allign it appropriately
	} else if (Boolean.class.equals(colName.getType())) {
	    engGrid.addComponent(field);
	    engGrid.setComponentAlignment(field, Alignment.MIDDLE_LEFT);
	    // if timestamp, make it take 3 cols
	} else if (colName.equals(EngineModelCols.timestamp)) {
	    engGrid.addComponent(field, 0, 19, 2, 19);
	} else {
	    engGrid.addComponent(field);
	}

	if (!StringUtils.isEmpty(colName.getDescription())) {
	    field.setDescription(colName.getDescription());
	} else {
	    field.setDescription(colName.getName());
	}
	this.bind(field, colName.toString());
    }

    /**
     * The listener for engine model order field. Updates the tab names in the
     * accordion
     * 
     * @param field
     */
    @SuppressWarnings("rawtypes")
    private void addEngineModelOrderChangeListener(AbstractField field) {
	field.addValueChangeListener(new ValueChangeListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void valueChange(ValueChangeEvent event) {
		enginesAccordion.updateTabNames(engGrid, getItemDataSource());
		enginesAccordion.markAsDirtyRecursive();
	    }
	});
    }

    @Override
    public void commit() throws IllegalEngineIdException {
	try {
	    /* Commit the data entered in the form to the actual item. */
	    super.commit();
	    /* Commit changes to the database. */
	    enginesAccordion.getEnginesContainer().commit();
	} catch (PSQLException e1) {
	    LOG.error("Exception occured while trying to commit changes to EngineModelForm", e1);
	    if (e1.getMessage().contains("ERROR: duplicate key value violates unique constraint ")) {
		throw new IllegalEngineIdException(
			"There can't be 2 engines with the same AWCom Engine id for one simulator model. I.e. There can't be 2 engines with engine id 1 on the same simulator. ");
	    }
	} catch (UnsupportedOperationException | CommitException | SQLException e2) {
	    throw new RuntimeException("Could not commit changes to simulator container. Database problem?", e2);
	}
    }
}
