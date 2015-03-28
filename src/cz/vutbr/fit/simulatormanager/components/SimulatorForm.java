package cz.vutbr.fit.simulatormanager.components;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import cz.vutbr.fit.simulatormanager.database.columns.SimulatorCols;
import cz.vutbr.fit.simulatormanager.database.columns.SimulatorModelCols;
import cz.vutbr.fit.simulatormanager.util.SingleSelectConverter;
import cz.vutbr.fit.simulatormanager.views.SimulatorsView;

/**
 * We implement RowId change listener for setting the simulator's model to an
 * appropriate simulator model in a combobox:
 * https://vaadin.com/tutorial/sql/-/section/update-ui.map-city-to-contacts.html
 * 
 * @author zhenia
 *
 */
public class SimulatorForm extends FieldGroup {

    final static Logger LOG = LoggerFactory.getLogger(SimulatorForm.class);
    private static final long serialVersionUID = 1L;
    private SimulatorsView view;
    private ComboBox simulatorModel = new ComboBox();
    private static StringToIntegerConverter plainIntegerConverter = getStringToIntegerConverter();;

    public SimulatorForm(SimulatorsView view) {
	this.view = view;
	init();
    }

    /**
     * Sets item's datasource (simulator). Convert value for combobox from
     * Integer to RowId
     */
    @Override
    public void setItemDataSource(Item newDataSource) {
	super.setItemDataSource(newDataSource);
	setSimulatorModelValue(newDataSource);
    }

    /**
     * Refreshes the list of possible values, sets the current value to
     * simulator model combo box
     * 
     * @param newDataSource
     */
    public void setSimulatorModelValue(Item newDataSource) {
	// refresh simulator models in case some simulator models were changed
	if (simulatorModel.getContainerDataSource() != null) {
	    ((SQLContainer) simulatorModel.getContainerDataSource()).refresh();
	}
	// we have integer in database, but combobox requires RowId
	if (newDataSource.getItemProperty(SimulatorCols.simulatormodelid.toString()).getValue() != null) {
	    Integer id = (Integer) newDataSource.getItemProperty(SimulatorCols.simulatormodelid.toString()).getValue();
	    simulatorModel.select(new RowId(id));
	}
    }

    @SuppressWarnings("rawtypes")
    private void init() {
	setBuffered(false);
	for (SimulatorCols colName : SimulatorCols.values()) {
	    AbstractField field = createField(colName);
	    addValueChangeListener(field);
	    addFieldToForm(field, colName);
	}
    }

    /**
     * Create the field of the correct type based on column name
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    private AbstractField createField(SimulatorCols colName) {
	// check if this is an active field which is a boolean
	if (colName.equals(SimulatorCols.active)) {
	    return new CheckBox(colName.getName());
	}
	// check if this is a timestamp field
	else if (colName.equals(SimulatorCols.timestamp)) {
	    DateField dateField = new DateField(colName.getName());
	    dateField.setResolution(Resolution.SECOND);
	    return dateField;
	    // check if this is a port which is a numeric
	} else if (colName.equals(SimulatorCols.port)) {
	    TextField field = createInputField(colName.getName());
	    field.setConverter(plainIntegerConverter);
	    return field;
	    // check if this is a simulator model selection for which we use
	    // a combobox
	} else if (colName.equals(SimulatorCols.simulatormodelid)) {
	    return initSimulatorModelsCombo();
	}
	return createInputField(colName.getName());
    }

    /**
     * Initialized the combobox with simulator models, returns it
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public ComboBox initSimulatorModelsCombo() {
	LOG.info("initSimulatorModelsCombo() - initialize the combobox with simulator models");
	SQLContainer modelsContainer = view.getDBHelp().getNewSimulatorModelContainer();
	simulatorModel.setTextInputAllowed(false);
	simulatorModel.setContainerDataSource(modelsContainer);
	simulatorModel.setNullSelectionAllowed(false);
	// we display simulator model name (if we don't set this,
	// simulatormodelid will be displayed instead
	simulatorModel.setItemCaptionPropertyId(SimulatorModelCols.simulatormodelname.toString());
	// Solves the conversion issue described here
	// https://vaadin.com/forum#!/thread/2729098
	simulatorModel.setConverter(new SingleSelectConverter(simulatorModel));
	return simulatorModel;
    }

    /**
     * Adds the field to a SimulatorForm, binds the field with the propertyId of
     * the item from the SQLContainer
     * 
     * @param field
     * @param colName
     */
    @SuppressWarnings("rawtypes")
    private void addFieldToForm(AbstractField field, SimulatorCols colName) {
	view.getEditorLayout().addComponent(field);
	if (!StringUtils.isEmpty(colName.getDescription())) {
	    field.setDescription(colName.getDescription());
	} else {
	    field.setDescription(colName.getName());
	}
	this.bind(field, colName.toString());
    }

    private static StringToIntegerConverter getStringToIntegerConverter() {
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

    /**
     * When a value is changed by user, commit data to database
     * 
     * @param field
     */
    @SuppressWarnings("rawtypes")
    private void addValueChangeListener(AbstractField field) {
	field.addValueChangeListener(new ValueChangeListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void valueChange(ValueChangeEvent event) {
		LOG.debug("Going to commit simulator form.. ");
		SimulatorForm.this.commit();
	    }
	});
    }

    @Override
    public void commit() {
	LOG.debug("Going to commit data from simulator form");
	try {
	    /* Commit the data entered in the form to the actual item. */
	    super.commit();
	    /* Commit changes to the database. */
	    view.getDBHelp().getCachedSimulatorContainer().commit();
	    // view.getDBHelp().updateCachedSimulatorContainer();
	} catch (UnsupportedOperationException | SQLException | CommitException e) {
	    Notification.show("Error when commiting changes to simulator container. Database problem?", "",
		    Notification.Type.ERROR_MESSAGE);
	}
    }

    @Override
    public void discard() {
	try {
	    super.discard();
	    /* On discard, roll back the changes. */
	    view.getDBHelp().getCachedSimulatorContainer().rollback();
	} catch (UnsupportedOperationException | SQLException e) {
	    Notification.show("Error when discarding changes from container. Database problem?", "",
		    Notification.Type.ERROR_MESSAGE);
	}
	/* Clear the form */
	setItemDataSource(null);
    }

}
