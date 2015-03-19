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
		LOG.info("Convert presentation to model" + value);
		LOG.info("Convert presentation to model. current item caption: {}, id: {}, value: {}", select.getCaption(),
				select.getId(), select.getValue());
		if (value != null) {
			LOG.info("Convert value to model class" + value.getClass());
		}
		if (value == null) {
			return null;
		}
		if (value.getClass().equals(RowId.class)) {
			return ((RowId) value).getId()[0];
		}
		return getIdBySimulatorModelName((String) value);
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
		LOG.info("Convert model to presentation" + value);
		if (value != null) {
			LOG.info("Convert model to presentation class" + value.getClass());
		}
		if (value == null) {
			return null;
		}
		RowId itemId = new RowId(value);
		LOG.info("item found:" + getContainerDatasource().getItem(itemId));
		Object simulatorName = getContainerDatasource().getItem(itemId)
				.getItemProperty(SimulatorModelCols.simulatormodelname.toString()).getValue();
		LOG.info("Simulator name: {}", simulatorName);
		return simulatorName;
	}

	@Override
	public Class getModelType() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public Class getPresentationType() {
		// TODO Auto-generated method stub
		return String.class;
	}

}
