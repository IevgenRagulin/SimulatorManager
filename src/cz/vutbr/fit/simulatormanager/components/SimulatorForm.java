package cz.vutbr.fit.simulatormanager.components;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Random;

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
    private static final int DEFAULT_NUM_OF_LANDING_GEARS = 3;
    private static final Object FAKE_HOSTNAME = "hostname.fit.vutbr.cz";
    private static final Object DEFAULT_MAX_SPEED_ON_FLAPS = 200;
    private SimulatorsView view;
    private Random random = new Random();
    private final int MINRANDOM = 1000;
    private final int MAXRANDOM = 10000;

    private ComboBox simulatorModel = new ComboBox();

    private static StringToIntegerConverter plainIntegerConverter = getStringToIntegerConverter();;

    public SimulatorForm(SimulatorsView view) {
	this.view = view;
	init();
    }

    /**
     * Sets item's datasource. Convert value for combobox from Integer to RowId
     */
    @Override
    public void setItemDataSource(Item newDataSource) {
	super.setItemDataSource(newDataSource);
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
	    SQLContainer modelsContainer = view.getDBHelp().getSimulatorModelContainer();
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
	return createInputField(colName.getName());
    }

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

    @SuppressWarnings("unchecked")
    public void addSimulator() {
	Object simulatorId = view.getDBHelp().getSimulatorContainer().addItem();
	view.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.simulatorname.toString())
		.setValue("New" + random.nextInt(MAXRANDOM - MINRANDOM) + MINRANDOM);
	view.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.port.toString()).setValue(12322);
	view.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.hostname.toString())
		.setValue(FAKE_HOSTNAME);
	view.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.maxspeedonflaps.toString())
		.setValue(DEFAULT_MAX_SPEED_ON_FLAPS);
	view.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.numberoflandinggears.toString())
		.setValue(DEFAULT_NUM_OF_LANDING_GEARS);
	view.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.active.toString()).setValue(false);
	view.getSimulatorList().getContainerProperty(simulatorId, SimulatorCols.simulatormodelid.toString())
		.setValue(getAnySimulatorModelId());
	commit();
    }

    /**
     * Gets an id of an existing simulator model
     * 
     * @return
     */
    private Integer getAnySimulatorModelId() {
	Collection<RowId> modelsIds = (Collection<RowId>) view.getDBHelp().getSimulatorModelContainer().getItemIds();
	return (Integer) modelsIds.iterator().next().getId()[0];
    }

    public void removeSimulator() {
	Object simulatorId = view.getSimulatorList().getValue();
	view.getSimulatorList().removeItem(simulatorId);
	commit();
    }

    @Override
    public void commit() {
	LOG.debug("Going to commit data from simulator form");
	try {
	    /* Commit the data entered in the form to the actual item. */
	    super.commit();
	    /* Commit changes to the database. */
	    view.getDBHelp().getSimulatorContainer().commit();
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
	    view.getDBHelp().getSimulatorContainer().rollback();
	} catch (UnsupportedOperationException | SQLException e) {
	    Notification.show("Error when discarding changes from container. Database problem?", "",
		    Notification.Type.ERROR_MESSAGE);
	}
	/* Clear the form */
	setItemDataSource(null);
    }

}
