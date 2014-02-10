package com.example.testvaadin;

import java.sql.SQLException;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DatabaseHelper {
	JDBCConnectionPool pool = null;
	private SQLContainer simulatorContainer = null;

	public DatabaseHelper() {
		initConnectionPool();
		initSimulatorContainer();
	}

	private void initSimulatorContainer() {
		TableQuery tq = new TableQuery("simulator", pool);
		tq.setVersionColumn("Timestamp");
		try {
			simulatorContainer = new SQLContainer(tq);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initConnectionPool() {
		try {
			pool = new SimpleJDBCConnectionPool("org.postgresql.Driver",
					"jdbc:postgresql://localhost/postgres", "postgres",
					"password", 2, 5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SQLContainer getSimulatorContainer() {
		return simulatorContainer;
	}

}
