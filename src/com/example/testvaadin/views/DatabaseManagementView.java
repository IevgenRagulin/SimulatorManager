package com.example.testvaadin.views;

import com.example.testvaadin.components.ButtonDatabaseManagementView;
import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.ErrorLabel;
import com.example.testvaadin.data.DatabaseHelperPureJDBC;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;

public class DatabaseManagementView extends BasicView implements View {
	private static final long serialVersionUID = 5205148361184720336L;
	private ErrorLabel errorLabel = new ErrorLabel("");
	private ButtonToMainMenu buttonToMainMenu;

	private ButtonDatabaseManagementView testDtbConn = new ButtonDatabaseManagementView(
			"Test database connection");
	private ButtonDatabaseManagementView cleanAllSimInfo = new ButtonDatabaseManagementView(
			"Clean database");
	private ButtonDatabaseManagementView initAllSimInfo = new ButtonDatabaseManagementView(
			"Init database");

	private Navigator navigator = null;
	private static final String MAIN_LAYOUT_CLASS = "mainVertLayout";

	@Override
	public void enter(ViewChangeEvent event) {
		errorLabel.setCaption("");
	}

	public DatabaseManagementView(Navigator navigator) {
		this.navigator = navigator;
		setPrimaryStyleName(MAIN_LAYOUT_CLASS);
		initButtonToMainMenu();
		addComponent(errorLabel);
		addComponent(testDtbConn);
		addComponent(cleanAllSimInfo);
		addComponent(initAllSimInfo);
		setClickListeners();

	}

	private void setClickListeners() {
		initAllSimInfo.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 4508884569951873790L;

			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show(DatabaseHelperPureJDBC.initDatabase(), "",
						Notification.TYPE_WARNING_MESSAGE);
			}
		});

		testDtbConn.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -4260477633606411161L;

			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				if (DatabaseHelperPureJDBC.testDatabaseConnection() == true) {
					Notification.show("Database connection is working", "",
							Notification.TYPE_HUMANIZED_MESSAGE);
				} else {
					Notification.show("Couldn't connect to database", "",
							Notification.TYPE_ERROR_MESSAGE);
				}
			}
		});

		cleanAllSimInfo.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -7683422836811100001L;

			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show(DatabaseHelperPureJDBC.cleanDatabase(), "",
						Notification.TYPE_WARNING_MESSAGE);
			}
		});
	}

	private void initButtonToMainMenu() {
		buttonToMainMenu = new ButtonToMainMenu(navigator);
		addComponent(buttonToMainMenu);
	}

}
