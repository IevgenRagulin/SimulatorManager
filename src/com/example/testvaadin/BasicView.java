package com.example.testvaadin;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.VerticalLayout;

public class BasicView extends VerticalLayout {

	private static final long serialVersionUID = -8524856579132861530L;

	protected DatabaseHelper dbHelp = new DatabaseHelper();
	protected SQLContainer simulatorContainer = dbHelp.getSimulatorContainer();

	public DatabaseHelper getDBHelp() {
		return dbHelp;
	}
}