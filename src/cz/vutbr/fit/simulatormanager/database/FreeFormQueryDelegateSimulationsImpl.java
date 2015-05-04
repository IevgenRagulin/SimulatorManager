package cz.vutbr.fit.simulatormanager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.sqlcontainer.RowItem;
import com.vaadin.data.util.sqlcontainer.query.FreeformQueryDelegate;
import com.vaadin.data.util.sqlcontainer.query.OrderBy;

import cz.vutbr.fit.simulatormanager.database.columns.SimulationCols;

public class FreeFormQueryDelegateSimulationsImpl implements FreeformQueryDelegate {
    private static final long serialVersionUID = 1L;
    final static Logger LOG = LoggerFactory.getLogger(FreeFormQueryDelegateSimulationsImpl.class);

    private String simulatorId = null;

    @Override
    public String getQueryString(int offset, int limit) throws UnsupportedOperationException {
	return "SELECT * FROM simulation WHERE simulatorid=" + simulatorId + " ORDER BY simulationid LIMIT ALL";

    }

    public FreeFormQueryDelegateSimulationsImpl(String simulatorId) {
	this.simulatorId = simulatorId;
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
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public boolean removeRow(Connection conn, RowItem row) throws UnsupportedOperationException, SQLException {
	Integer simulationId = (Integer) row.getItemProperty(SimulationCols.simulationid.toString()).getValue();
	removeDataFromReferencingTables(conn, simulationId);
	PreparedStatement statement = conn.prepareStatement("DELETE FROM simulation WHERE simulationid = ?");
	statement.setInt(1, simulationId);
	int rowsChanged = statement.executeUpdate();
	statement.close();
	return rowsChanged == 1;
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

    @Override
    public String getContainsRowQueryString(Object... keys) {
	StringBuffer query = new StringBuffer("SELECT * FROM simulation WHERE simulatorid=" + simulatorId + " AND ( ");
	int i = 0;
	for (Object key : keys) {
	    Integer strKey = (Integer) key;
	    query.append("simulationid=");
	    query.append(strKey);
	    if ((i + 1) != keys.length) {
		query.append(" OR ");
	    }
	}
	query.append(")");
	return query.toString();
    }
}
