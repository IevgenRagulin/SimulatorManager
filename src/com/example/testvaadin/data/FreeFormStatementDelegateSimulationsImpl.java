package com.example.testvaadin.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.sqlcontainer.RowItem;
import com.vaadin.data.util.sqlcontainer.query.FreeformStatementDelegate;
import com.vaadin.data.util.sqlcontainer.query.OrderBy;
import com.vaadin.data.util.sqlcontainer.query.generator.StatementHelper;

public class FreeFormStatementDelegateSimulationsImpl implements
		FreeformStatementDelegate {
	private static final long serialVersionUID = 9191272022216991665L;
	private String simulatorId = null;
	private List<Filter> filters;
	private List<OrderBy> orderBys;

	@Override
	public String getQueryString(int offset, int limit)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Use getQueryStatement method.");
	}

	@Override
	public String getCountQuery() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Use getCountStatement method.");
	}

	@Override
	public void setFilters(List<Filter> filters)
			throws UnsupportedOperationException {
		this.filters = filters;
	}

	@Override
	public void setOrderBy(List<OrderBy> orderBys)
			throws UnsupportedOperationException {
		this.orderBys = orderBys;
	}

	@Override
	public int storeRow(Connection conn, RowItem row)
			throws UnsupportedOperationException, SQLException {
		throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public boolean removeRow(Connection conn, RowItem row)
			throws UnsupportedOperationException, SQLException {
		System.out.println("GOING TO REMOVE ROW FREE FORM DELEGATE " + row);
		Integer simulationId = (Integer) row.getItemProperty(
				ColumnNames.getSimulationid()).getValue();
		removeDataFromReferencingTables(conn, simulationId);
		PreparedStatement statement = conn
				.prepareStatement("DELETE FROM simulation WHERE simulationid = ?");
		statement.setInt(1, simulationId);
		int rowsChanged = statement.executeUpdate();
		statement.close();
		return rowsChanged == 1;
	}

	@Override
	public String getContainsRowQueryString(Object... keys)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException(
				"Please use getContainsRowQueryStatement method.");
	}

	@Override
	public StatementHelper getQueryStatement(int offset, int limit)
			throws UnsupportedOperationException {
		StatementHelper sh = new StatementHelper();
		StringBuffer query = new StringBuffer(
				"SELECT * FROM simulation WHERE Simulator_SimulatorId="
						+ simulatorId + " ORDER BY simulationid ");
		if (offset != 0 || limit != 0) {
			query.append(" LIMIT ").append(limit);
			query.append(" OFFSET ").append(offset);
		}
		sh.setQueryString(query.toString());
		return sh;
	}

	@Override
	public StatementHelper getCountStatement()
			throws UnsupportedOperationException {
		StatementHelper sh = new StatementHelper();
		StringBuffer query = new StringBuffer(
				"SELECT COUNT(*) FROM simulation WHERE Simulator_SimulatorId="
						+ simulatorId);
		sh.setQueryString(query.toString());
		return sh;
	}

	@Override
	public StatementHelper getContainsRowQueryStatement(Object... keys)
			throws UnsupportedOperationException {
		StatementHelper sh = new StatementHelper();
		StringBuffer query = new StringBuffer(
				"SELECT * FROM simulation WHERE simulationid=?");
		sh.addParameterValue(keys[0]);
		sh.setQueryString(query.toString());
		return sh;
	}

	public FreeFormStatementDelegateSimulationsImpl(String simulatorId) {
		this.simulatorId = simulatorId;
	}

	private void removeDataFromReferencingTables(Connection conn,
			Integer simulationId) throws SQLException {
		removeDataFromSimulationInfo(conn, simulationId);
		removeDataFromSimulationPfdInfo(conn, simulationId);
		removeDataFromSimulationDevStateInfo(conn, simulationId);
	}

	private void removeDataFromSimulationInfo(Connection conn,
			Integer simulationId) throws SQLException {
		PreparedStatement statement = conn
				.prepareStatement("DELETE FROM simulationinfo WHERE simulation_simulationid = ?");
		statement.setInt(1, simulationId);
		statement.executeUpdate();
		statement.close();
	}

	private void removeDataFromSimulationPfdInfo(Connection conn,
			Integer simulationId) throws SQLException {
		PreparedStatement statement = conn
				.prepareStatement("DELETE FROM simulationpfdinfo WHERE simulation_simulationid = ?");
		statement.setInt(1, simulationId);
		statement.executeUpdate();
		statement.close();
	}

	private void removeDataFromSimulationDevStateInfo(Connection conn,
			Integer simulationId) throws SQLException {
		PreparedStatement statement = conn
				.prepareStatement("DELETE FROM simulationdevicesstate WHERE simulation_simulationid = ?");
		statement.setInt(1, simulationId);
		statement.executeUpdate();
		statement.close();
	}

}
