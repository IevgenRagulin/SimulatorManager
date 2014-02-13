package com.example.testvaadin.views;

import com.example.testvaadin.data.DatabaseHelper;
import com.vaadin.ui.VerticalLayout;

public class BasicView extends VerticalLayout {

	private static final long serialVersionUID = -8524856579132861530L;

	protected DatabaseHelper dbHelp = new DatabaseHelper();

	public DatabaseHelper getDBHelp() {
		return dbHelp;
	}
}
