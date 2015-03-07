package cz.vutbr.fit.simulatormanager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.sqlcontainer.RowItem;
import com.vaadin.data.util.sqlcontainer.TemporaryRowId;
import com.vaadin.data.util.sqlcontainer.query.FreeformQueryDelegate;
import com.vaadin.data.util.sqlcontainer.query.OrderBy;

import cz.vutbr.fit.simulatormanager.database.columns.EngineModelCols;

public class FreeFormStatementDelegateEngineModels implements FreeformQueryDelegate {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LoggerFactory.getLogger(FreeFormStatementDelegateEngineModels.class);

	private String simulatorModelId;

	public FreeFormStatementDelegateEngineModels(String simulatorModelId) {
		this.simulatorModelId = simulatorModelId;
	}

	@Override
	public String getQueryString(int offset, int limit) {
		return "SELECT * FROM enginemodel  WHERE simulatormodelid = " + simulatorModelId
				+ "ORDER BY \"enginemodelorder\" ASC; ";
	}

	@Override
	public String getCountQuery() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFilters(List<Filter> filters) throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOrderBy(List<OrderBy> orderBys) throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public int storeRow(Connection conn, RowItem row) throws UnsupportedOperationException, SQLException {
		Statement statement = conn.createStatement();
		String query = null;
		if (row.getId() instanceof TemporaryRowId) {
			query = getInsertQuery(row);
		} else {
			query = getUpdateQuery(row);
			LOG.info("Going to update engine model, query: {}", query);
		}
		int retval = statement.executeUpdate(query);
		statement.close();
		return retval;
	}

	private String getUpdateQuery(RowItem row) {
		StringBuilder update = new StringBuilder("UPDATE enginemodel SET ");
		for (EngineModelCols colName : EngineModelCols.values()) {
			appendUpdateValue(update, row, colName.toString());
		}
		// remove last character which is a coma
		update.setLength(update.length() - 2);
		update.append(" WHERE enginemodelid = ").append(
				row.getItemProperty(EngineModelCols.enginemodelid.toString()).getValue());
		return update.toString();
	}

	private void appendUpdateValue(StringBuilder update, RowItem row, String propId) {
		update.append(propId).append(" = ");
		Object val = row.getItemProperty(propId).getValue();
		if (val != null) {
			update.append("'").append(val).append("'");
		} else {
			update.append("NULL");
		}
		update.append(", ");
	}

	private String getInsertQuery(RowItem row) {
		StringBuffer insert = new StringBuffer("INSERT INTO enginemodel ("
				+ EngineModelCols.simulatormodelid.toString() + ", " + EngineModelCols.enginemodelorder.toString()
				+ ") VALUES ( ");
		appendInsertValue(insert, row, EngineModelCols.simulatormodelid.toString());
		appendInsertValue(insert, row, EngineModelCols.enginemodelorder.toString());
		// remove last 2 characters
		insert.setLength(insert.length() - 2);
		insert.append(")");
		return insert.toString();
	}

	private void appendInsertValue(StringBuffer insert, RowItem row, String propId) {
		Object val = row.getItemProperty(propId).getValue();
		if (val != null) {
			insert.append("'").append(val).append("'");
		} else {
			insert.append("NULL");
		}
		insert.append(", ");
	}

	@Override
	public boolean removeRow(Connection conn, RowItem row) throws UnsupportedOperationException, SQLException {
		LOG.info("Going to remove engine model: {}", row);
		Integer engineModelId = (Integer) row.getItemProperty(EngineModelCols.enginemodelid.toString()).getValue();
		PreparedStatement statement = conn.prepareStatement("DELETE FROM enginemodel WHERE enginemodelid = ?");
		statement.setInt(1, engineModelId);
		int rowsChanged = statement.executeUpdate();
		statement.close();
		return rowsChanged == 1;
	}

	@Override
	public String getContainsRowQueryString(Object... keys) throws UnsupportedOperationException {
		LOG.info("Contains row query is called on engine models");
		StringBuffer query = new StringBuffer("SELECT * FROM enginemodel WHERE ");
		int i = 0;
		for (Object key : keys) {
			Integer strKey = (Integer) key;
			query.append("enginemodelid=");
			query.append(strKey);
			if ((i + 1) != keys.length) {
				query.append(" OR ");
			}
		}
		return query.toString();
	}

}
