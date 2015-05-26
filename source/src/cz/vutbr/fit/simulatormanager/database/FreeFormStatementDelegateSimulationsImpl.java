package cz.vutbr.fit.simulatormanager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.sqlcontainer.RowItem;
import com.vaadin.data.util.sqlcontainer.TemporaryRowId;
import com.vaadin.data.util.sqlcontainer.query.FreeformStatementDelegate;
import com.vaadin.data.util.sqlcontainer.query.OrderBy;
import com.vaadin.data.util.sqlcontainer.query.generator.StatementHelper;

import cz.vutbr.fit.simulatormanager.database.columns.SimulationCols;

public class FreeFormStatementDelegateSimulationsImpl implements FreeformStatementDelegate {
    final static Logger LOG = LoggerFactory.getLogger(FreeFormStatementDelegateSimulationsImpl.class);

    private static final long serialVersionUID = 9191272022216991665L;
    private String simulatorId = null;
    @SuppressWarnings("unused")
    private List<Filter> filters;
    @SuppressWarnings("unused")
    private List<OrderBy> orderBys;

    @Override
    public String getQueryString(int offset, int limit) throws UnsupportedOperationException {
	throw new UnsupportedOperationException("Use getQueryStatement method.");
    }

    @Override
    public String getCountQuery() throws UnsupportedOperationException {
	throw new UnsupportedOperationException("Use getCountStatement method.");
    }

    @Override
    public void setFilters(List<Filter> filters) throws UnsupportedOperationException {
	this.filters = filters;
    }

    @Override
    public void setOrderBy(List<OrderBy> orderBys) throws UnsupportedOperationException {
	this.orderBys = orderBys;
    }

    @Override
    public int storeRow(Connection conn, RowItem row) throws UnsupportedOperationException, SQLException {
	LOG.info("Going to store row free form delegate, row: {}", row);
	Integer simulationId = (Integer) row.getItemProperty(SimulationCols.simulationid.toString()).getValue();
	Boolean isSimOn = (Boolean) row.getItemProperty(SimulationCols.issimulationon.toString()).getValue();
	Boolean isPaused = (Boolean) row.getItemProperty(SimulationCols.issimulationpaused.toString()).getValue();
	String query = null;
	if (row.getId() instanceof TemporaryRowId) {
	    throw new UnsupportedOperationException();
	} else {
	    query = "UPDATE simulation SET issimulationon=?, issimulationpaused=?" + "WHERE simulationid=?";
	}
	PreparedStatement statement = conn.prepareStatement(query);
	statement.setBoolean(1, isSimOn);
	statement.setBoolean(2, isPaused);
	statement.setInt(3, simulationId);
	int retval = statement.executeUpdate();
	return retval;
    }

    @Override
    public boolean removeRow(Connection conn, RowItem row) throws UnsupportedOperationException, SQLException {
	LOG.info("Going to remove row free form delegate, row: {}", row);
	Integer simulationId = (Integer) row.getItemProperty(SimulationCols.simulationid.toString()).getValue();
	removeDataFromReferencingTables(conn, simulationId);
	PreparedStatement statement = conn.prepareStatement("DELETE FROM simulation WHERE simulationid = ?");
	statement.setInt(1, simulationId);
	int rowsChanged = statement.executeUpdate();
	statement.close();
	return rowsChanged == 1;
    }

    @Override
    public String getContainsRowQueryString(Object... keys) throws UnsupportedOperationException {
	throw new UnsupportedOperationException("Please use getContainsRowQueryStatement method.");
    }

    @Override
    public StatementHelper getQueryStatement(int offset, int limit) throws UnsupportedOperationException {
	StatementHelper sh = new StatementHelper();
	StringBuffer query = new StringBuffer("SELECT * FROM simulation WHERE simulatorid=").append(simulatorId);
	String stringJoiner = "";
	// add order bys
	if (CollectionUtils.isNotEmpty(orderBys)) {
	    query.append(" ORDER BY ");
	    for (OrderBy orderBy : orderBys) {
		query.append(stringJoiner);
		stringJoiner = ", ";
		query.append(orderBy.getColumn()).append(" ");
		if (orderBy.isAscending()) {
		    query.append("ASC");
		} else {
		    query.append("DESC");
		}
	    }
	}

	if (offset != 0 || limit != 0) {
	    query.append(" LIMIT ").append(limit);
	    query.append(" OFFSET ").append(offset);
	}
	LOG.info("getQueryStatement() - generated query: {}", query.toString());
	sh.setQueryString(query.toString());
	return sh;
    }

    @Override
    public StatementHelper getCountStatement() throws UnsupportedOperationException {
	StatementHelper sh = new StatementHelper();
	StringBuffer query = new StringBuffer("SELECT COUNT(*) FROM simulation WHERE simulatorid=" + simulatorId);
	sh.setQueryString(query.toString());
	return sh;
    }

    @Override
    public StatementHelper getContainsRowQueryStatement(Object... keys) throws UnsupportedOperationException {
	StatementHelper sh = new StatementHelper();
	StringBuffer query = new StringBuffer("SELECT * FROM simulation WHERE simulationid=?");
	sh.addParameterValue(keys[0]);
	sh.setQueryString(query.toString());
	return sh;
    }

    public FreeFormStatementDelegateSimulationsImpl(String simulatorId) {
	this.simulatorId = simulatorId;
    }

    private void removeDataFromReferencingTables(Connection conn, Integer simulationId) throws SQLException {
	removeDataFromSimulationInfo(conn, simulationId);
	removeDataFromSimulationPfdInfo(conn, simulationId);
	removeDataFromSimulationDevStateInfo(conn, simulationId);
    }

    private void removeDataFromSimulationInfo(Connection conn, Integer simulationId) throws SQLException {
	PreparedStatement statement = conn.prepareStatement("DELETE FROM simulationinfo WHERE simulationid = ?");
	statement.setInt(1, simulationId);
	statement.executeUpdate();
	statement.close();
    }

    private void removeDataFromSimulationPfdInfo(Connection conn, Integer simulationId) throws SQLException {
	PreparedStatement statement = conn.prepareStatement("DELETE FROM simulationpfdinfo WHERE simulationid = ?");
	statement.setInt(1, simulationId);
	statement.executeUpdate();
	statement.close();
    }

    private void removeDataFromSimulationDevStateInfo(Connection conn, Integer simulationId) throws SQLException {
	PreparedStatement statement = conn.prepareStatement("DELETE FROM simulationdevicesstate WHERE simulationid = ?");
	statement.setInt(1, simulationId);
	statement.executeUpdate();
	statement.close();
    }

}
