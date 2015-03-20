package cz.vutbr.fit.simulatormanager.util;

import java.util.Collection;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.AbstractSelect;

import cz.vutbr.fit.simulatormanager.database.columns.SimulatorModelCols;

public class SingleSelectConverter implements Converter {

    private static final long serialVersionUID = 1L;
    final static Logger LOG = LoggerFactory.getLogger(SingleSelectConverter.class);

    private final AbstractSelect select;

    public SingleSelectConverter(AbstractSelect select) {
	this.select = select;
    }

    private SQLContainer getContainerDatasource() {
	return (SQLContainer) select.getContainerDataSource();
    }

    @Override
    public Object convertToModel(Object value, Class targetType, Locale locale) throws ConversionException {
	LOG.info("Convert presentation to model. Presentation value: {}", select.getValue());
	if (value == null) {
	    return null;
	}
	if (value.getClass().equals(RowId.class)) {
	    return ((RowId) value).getId()[0];
	}
	Integer modelValue = getIdBySimulatorModelName((String) select.getValue());
	LOG.info("After converting to model. Model value: {}", modelValue);
	return modelValue;
    }

    private Integer getIdBySimulatorModelName(String simulatorName) {
	SQLContainer container = getContainerDatasource();
	Collection<RowId> rowIds = (Collection<RowId>) container.getItemIds();
	for (RowId rowId : rowIds) {
	    Item item = container.getItem(rowId);
	    if (item.getItemProperty(SimulatorModelCols.simulatormodelname.toString()).getValue().equals(simulatorName)) {
		LOG.info("item found, and its id is"
			+ item.getItemProperty(SimulatorModelCols.simulatormodelid.toString()).getValue());
		return (Integer) item.getItemProperty(SimulatorModelCols.simulatormodelid.toString()).getValue();
	    }
	}
	LOG.info("Item was not found for simulator name" + simulatorName);
	return null;
    }

    @Override
    public Object convertToPresentation(Object value, Class targetType, Locale locale) throws ConversionException {
	if (value != null) {
	    LOG.info("Convert model to presentation. Model value: {}. Model class: {}", value, value.getClass());
	}
	if (value == null) {
	    return null;
	}
	RowId itemId = new RowId(value);
	Object simulatorModelName = getContainerDatasource().getItem(itemId)
		.getItemProperty(SimulatorModelCols.simulatormodelname.toString()).getValue();
	LOG.info("Presentation: Simulator model name: {}", simulatorModelName);
	return simulatorModelName;
    }

    @Override
    public Class getModelType() {
	return Integer.class;
    }

    @Override
    public Class getPresentationType() {
	return String.class;
    }

}
