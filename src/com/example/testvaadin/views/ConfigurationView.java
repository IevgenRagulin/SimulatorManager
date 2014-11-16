package com.example.testvaadin.views;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.example.testvaadin.SimulatorManagerConstants;
import com.example.testvaadin.components.ButtonConfigurationView;
import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.ErrorLabel;
import com.example.testvaadin.data.DatabaseHelperPureJDBC;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ConfigurationView extends BasicView implements View {
	private static final long serialVersionUID = 5205148361184720336L;
	private ErrorLabel errorLabel = new ErrorLabel("");
	private ButtonToMainMenu buttonToMainMenu;

	private ButtonConfigurationView testDtbConn = new ButtonConfigurationView("Test database connection");
	private ButtonConfigurationView cleanAllSimInfo = new ButtonConfigurationView("Clean database");
	private ButtonConfigurationView initAllSimInfo = new ButtonConfigurationView("Init database");
	private ButtonConfigurationView seeConfigs = new ButtonConfigurationView("View applications configs");

	private Navigator navigator = null;
	final Window configsWindow;
	private static final String MAIN_LAYOUT_CLASS = "mainVertLayout";

	@Override
	public void enter(ViewChangeEvent event) {
		errorLabel.setCaption("");
	}

	public ConfigurationView(Navigator navigator) {
		this.navigator = navigator;
		setPrimaryStyleName(MAIN_LAYOUT_CLASS);
		initButtonToMainMenu();
		addComponent(errorLabel);
		addComponent(testDtbConn);
		addComponent(cleanAllSimInfo);
		addComponent(initAllSimInfo);
		addComponent(seeConfigs);
		// window for displaying configs
		configsWindow = new Window("Simulator manager configurations");
		configsWindow.setHeight(null);
		configsWindow.setWidth("640px");
		configsWindow.setClosable(true);
		configsWindow.setResizable(true);

		setClickListeners();

	}

	private void setClickListeners() {
		initAllSimInfo.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 4508884569951873790L;

			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show(DatabaseHelperPureJDBC.initDatabase(), "", Notification.TYPE_WARNING_MESSAGE);
			}
		});

		testDtbConn.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -4260477633606411161L;

			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				if (DatabaseHelperPureJDBC.testDatabaseConnection() == true) {
					Notification.show("Database connection is working", "", Notification.TYPE_HUMANIZED_MESSAGE);
				} else {
					Notification.show("Couldn't connect to database", "", Notification.TYPE_ERROR_MESSAGE);
				}
			}
		});

		cleanAllSimInfo.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -7683422836811100001L;

			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show(DatabaseHelperPureJDBC.cleanDatabase(), "", Notification.TYPE_WARNING_MESSAGE);
			}
		});

		seeConfigs.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7455204020537174946L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().addWindow(configsWindow);
				configsWindow.center();
				configsWindow.focus();
				try {
					configsWindow.setContent(getConfigWindowContent());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private VerticalLayout getConfigWindowContent() throws IOException {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setMargin(true);
		// Find the application directory
		String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + SimulatorManagerConstants.CONFIGS_PATH;
		List<String> lines = Files.readAllLines(Paths.get(path));
		String allLines = "";
		for (String line : lines) {
			allLines += line + "<br/>";
		}
		verticalLayout.addComponent(new Label("<h2>" + path + "</h2>" + "<p>" + allLines + "</p>", ContentMode.HTML));
		return verticalLayout;
	}

	private void initButtonToMainMenu() {
		buttonToMainMenu = new ButtonToMainMenu(navigator);
		addComponent(buttonToMainMenu);
	}

}
