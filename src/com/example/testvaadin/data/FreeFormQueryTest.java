package com.example.testvaadin.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.FreeformStatementDelegate;
import com.vaadin.data.util.sqlcontainer.query.generator.StatementHelper;
import com.vaadin.data.util.sqlcontainer.query.generator.filter.QueryBuilder;

public class FreeFormQueryTest extends FreeformQuery {

	private static final long serialVersionUID = -7901417024129895401L;

	public FreeFormQueryTest(String queryString,
			JDBCConnectionPool connectionPool, String[] primaryKeyColumns) {
		super(queryString, connectionPool, primaryKeyColumns);
	}

	@SuppressWarnings("deprecation")
	public FreeFormQueryTest(String queryString, List<String> asList,
			JDBCConnectionPool pool) {
		super(queryString, asList, pool);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean containsRowWithKey(Object... keys) throws SQLException {
		String query = null;
		boolean contains = false;
		if (getDelegate() != null) {
			if (getDelegate() instanceof FreeformStatementDelegate) {
				try {
					StatementHelper sh = ((FreeformStatementDelegate) getDelegate())
							.getContainsRowQueryStatement(keys);

					PreparedStatement pstmt = null;
					ResultSet rs = null;
					Connection c = getConnection();
					try {
						pstmt = c.prepareStatement(sh.getQueryString());
						sh.setParameterValuesToStatement(pstmt);
						rs = pstmt.executeQuery();
						contains = rs.next();
						return contains;
					} finally {
						releaseConnection(c, pstmt, rs);
					}
				} catch (UnsupportedOperationException e) {
					// Statement generation not supported, continue...
				}
			}
			try {
				query = getDelegate().getContainsRowQueryString(keys);
			} catch (UnsupportedOperationException e) {
				query = modifyWhereClause(keys);
			}
		} else {
			query = modifyWhereClause(keys);
		}
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			contains = rs.next();
		} finally {
			releaseConnection(conn, statement, rs);
		}
		return contains;
	}

	private String modifyWhereClause(Object... keys) {
		// Build the where rules for the provided keys
		StringBuffer where = new StringBuffer();
		for (int ix = 0; ix < getPrimaryKeyColumns().size(); ix++) {
			where.append(QueryBuilder.quote(getPrimaryKeyColumns().get(ix)));
			if (keys[ix] == null) {
				where.append(" IS NULL");
			} else {
				where.append(" = '").append(keys[ix]).append("'");
			}
			if (ix < getPrimaryKeyColumns().size() - 1) {
				where.append(" AND ");
			}
		}
		// Is there already a WHERE clause in the query string?
		int index = getQueryString().toLowerCase().indexOf("where ");
		if (index > -1) {
			// Rewrite the where clause
			return getQueryString().substring(0, index) + "WHERE " + where
					+ " AND " + getQueryString().substring(index + 6);
		}
		// Append a where clause
		return getQueryString() + " WHERE " + where;
	}

}
