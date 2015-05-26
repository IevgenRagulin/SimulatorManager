package cz.vutbr.fit.simulatormanager.views;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import cz.vutbr.fit.simulatormanager.Constants;
import cz.vutbr.fit.simulatormanager.components.ButtonConfigurationView;
import cz.vutbr.fit.simulatormanager.components.ErrorLabel;
import cz.vutbr.fit.simulatormanager.components.MainMenuBar;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelperPureJDBC;
import cz.vutbr.fit.simulatormanager.types.PageType;
import cz.vutbr.fit.simulatormanager.util.ResourceUtil;

public class ConfigurationView extends BasicView implements View {

    private static final String SM_CONF_BUTTON = "sm-conf-button";

    final static Logger logger = LoggerFactory.getLogger(ConfigurationView.class);

    private static final long serialVersionUID = 5205148361184720336L;
    private ErrorLabel errorLabel = new ErrorLabel("");

    private ButtonConfigurationView testDtbConn = new ButtonConfigurationView("Test database connection");
    private ButtonConfigurationView cleanAllSimInfo = new ButtonConfigurationView("Clean database");
    private ButtonConfigurationView initAllSimInfo = new ButtonConfigurationView("Init database");
    private ButtonConfigurationView seeConfigs = new ButtonConfigurationView("View applications configs");

    private Navigator navigator = null;
    private Window configsWindow;
    private static final String MAIN_LAYOUT_CLASS = "mainVertLayout";
    private MainMenuBar mainMenu;
    private HorizontalSplitPanel mainLayout = new HorizontalSplitPanel();
    private VerticalLayout leftLayout;
    private VerticalLayout rightLayout;
    private Label applicationUsageInfo = new Label();

    @Override
    public void enter(ViewChangeEvent event) {
	errorLabel.setCaption("");
	initMenu();
	// refresh the selected page
	// mainMenu.checkTheCorrectMenuItem(PageType.CONFIGURATIONS);
    }

    public ConfigurationView(Navigator navigator) {
	logger.info("new ConfigurationView()");
	this.navigator = navigator;
	initLayout();
	setClickListeners();

    }

    private void initLayout() {
	setPrimaryStyleName(MAIN_LAYOUT_CLASS);
	initMenu();
	addComponent(mainMenu);
	addComponent(mainLayout);
	leftLayout = new VerticalLayout();
	rightLayout = new VerticalLayout();
	mainLayout.addComponent(leftLayout);
	mainLayout.addComponent(rightLayout);
	mainLayout.setSplitPosition(400, Unit.PIXELS);
	mainLayout.setMinSplitPosition(400, Unit.PIXELS);
	applicationUsageInfo.setValue(ResourceUtil.getApplicationUsageInfo());
	applicationUsageInfo.setContentMode(ContentMode.HTML);
	rightLayout.addComponent(applicationUsageInfo);
	initButtons();
	initConfigsWindow();
    }

    private void initMenu() {
	mainMenu = MainMenuBar.getInstance(navigator, PageType.CONFIGURATIONS);
    }

    private void initConfigsWindow() {
	configsWindow = new Window("Simulator manager configurations");
	configsWindow.setHeight(null);
	configsWindow.setWidth("60%");
	configsWindow.setHeight("80%");
	configsWindow.setClosable(true);
	configsWindow.setResizable(true);
	configsWindow.setClosable(true);
    }

    private void initButtons() {
	testDtbConn.setIcon(ResourceUtil.getPingImg());
	cleanAllSimInfo.setIcon(ResourceUtil.getCleanImg());
	initAllSimInfo.setIcon(ResourceUtil.getStartImg());
	seeConfigs.setIcon(ResourceUtil.getSettingsImg());
	testDtbConn.setStyleName(SM_CONF_BUTTON);
	cleanAllSimInfo.setStyleName(SM_CONF_BUTTON);
	initAllSimInfo.setStyleName(SM_CONF_BUTTON);
	seeConfigs.setStyleName(SM_CONF_BUTTON);
	leftLayout.addComponent(errorLabel);
	leftLayout.addComponent(testDtbConn);
	leftLayout.addComponent(cleanAllSimInfo);
	leftLayout.addComponent(initAllSimInfo);
	leftLayout.addComponent(seeConfigs);
	testDtbConn
		.setDescription("Tries to connect to the database which url/username/password is configured in simulatorManager.prop");
	cleanAllSimInfo
		.setDescription("Executes a script which removes information about simulations/simulators from all database tables. Warning: this will remove data about all simulators, and simulations");
	initAllSimInfo
		.setDescription("Executes a script which sets up the database for the SimulatorManager. Drops all tables, creates new tables. Warning: this will remove data about all simulators, and simulations");
	seeConfigs
		.setDescription("Show contents of simulatorManager.props file which contains configurations for the application");
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
	String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + Constants.CONFIGS_PATH;
	List<String> lines = Files.readAllLines(Paths.get(path));
	String allLines = "";
	for (String line : lines) {
	    allLines += line + "<br/>";
	}
	verticalLayout.addComponent(new Label("<h2> Path to configurations: " + path + "</h2> <h2> Configurations: </h2>" + "<p>"
		+ allLines + "</p>", ContentMode.HTML));
	verticalLayout
		.addComponent(new Label(
			"<p><b>If you wish to change any of these configs, please modify the configuration file. There is not need to restart the application</b></p>",
			ContentMode.HTML));

	return verticalLayout;
    }

}
