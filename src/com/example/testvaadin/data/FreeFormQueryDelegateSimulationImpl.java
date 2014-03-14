package com.example.testvaadin.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.sqlcontainer.RowItem;
import com.vaadin.data.util.sqlcontainer.TemporaryRowId;
import com.vaadin.data.util.sqlcontainer.query.FreeformQueryDelegate;
import com.vaadin.data.util.sqlcontainer.query.OrderBy;

public class FreeFormQueryDelegateSimulationImpl implements
		FreeformQueryDelegate {

	private static final long serialVersionUID = 3351214943213409809L;
	private String simulatorId = null;

	@Override
	public String getQueryString(int offset, int limit)
			throws UnsupportedOperationException {
		return "SELECT * FROM simulation WHERE Simulator_SimulatorId="
				+ simulatorId + " ORDER BY SimulationStartedTime DESC LIMIT 1";
	}

	@Override
	public String getCountQuery() throws UnsupportedOperationException {
		return "SELECT count(DISTINCT Simulator_SimulatorId) FROM simulation WHERE Simulator_SimulatorId="
				+ simulatorId;
	}

	@Override
	public void setFilters(List<Filter> filters)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOrderBy(List<OrderBy> orderBys)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public int storeRow(Connection conn, RowItem row)
			throws UnsupportedOperationException, SQLException {
		Statement statement = conn.createStatement();

		String query = null;
		if (row.getId() instanceof TemporaryRowId) {
			query = getInsertQuery(row);
		} else {
			query = getUpdateQuery(row);
		}
		System.out.println("store row " + query);
		int retval = statement.executeUpdate(query);
		statement.close();
		return retval;
	}

	private String getUpdateQuery(RowItem row) {
		StringBuffer update = new StringBuffer("UPDATE simulation SET ");
		appendUpdateValue(update, row, ColumnNames.getIssimulationon());
		appendUpdateValue(update, row, ColumnNames.getIssimulationpaused());
		update.append(" simulationendedtime = now()");
		update.append(" WHERE simulationid = ").append(
				row.getItemProperty(ColumnNames.getSimulationid()).getValue());
		return update.toString();
	}

	private void appendUpdateValue(StringBuffer update, RowItem row,
			String propId) {
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
		StringBuffer insert = new StringBuffer("INSERT INTO simulation ("
				+ ColumnNames.getSimulatoridForeignKey() + ", "
				+ ColumnNames.getIssimulationon() + ", "
				+ ColumnNames.getIssimulationpaused() + ", "
				+ ColumnNames.getSimulationstartedtime() + ", "
				+ ColumnNames.getSimulationendedtime() + ") VALUES ( ");
		appendInsertValue(insert, row, ColumnNames.getSimulatoridForeignKey());
		appendInsertValue(insert, row, ColumnNames.getIssimulationon());
		appendInsertValue(insert, row, ColumnNames.getIssimulationpaused());
		insert.append(" now(), now() ");
		insert.append(")");
		return insert.toString();
	}

	private void appendInsertValue(StringBuffer insert, RowItem row,
			String propId) {
		Object val = row.getItemProperty(propId).getValue();
		if (val != null) {
			insert.append("'").append(val).append("'");
		} else {
			insert.append("NULL");
		}
		insert.append(", ");
	}

	@Override
	public boolean removeRow(Connection conn, RowItem row)
			throws UnsupportedOperationException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getContainsRowQueryString(Object... keys)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	public FreeFormQueryDelegateSimulationImpl(String simulatorId) {
		this.simulatorId = simulatorId;
	}

}
