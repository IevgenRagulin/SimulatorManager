package com.example.testvaadin;

import com.vaadin.ui.VerticalLayout;

public class BasicView extends VerticalLayout {

	private static final long serialVersionUID = -8524856579132861530L;

	protected DatabaseHelper dbHelp = new DatabaseHelper();

	public DatabaseHelper getDBHelp() {
		return dbHelp;
	}
}
