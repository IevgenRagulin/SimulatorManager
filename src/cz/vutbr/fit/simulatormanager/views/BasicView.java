package cz.vutbr.fit.simulatormanager.views;

import com.vaadin.ui.VerticalLayout;

import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;

public class BasicView extends VerticalLayout {

	private static final long serialVersionUID = -8524856579132861530L;

	protected DatabaseHelper dbHelp = new DatabaseHelper();

	public DatabaseHelper getDBHelp() {
		return dbHelp;
	}
}
