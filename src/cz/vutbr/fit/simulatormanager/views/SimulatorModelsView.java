package cz.vutbr.fit.simulatormanager.views;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import cz.vutbr.fit.simulatormanager.components.EnginesTabSheet;
import cz.vutbr.fit.simulatormanager.components.MainMenuBar;
import cz.vutbr.fit.simulatormanager.components.SimulatorModelConfigurationChecker;
import cz.vutbr.fit.simulatormanager.components.SimulatorModelForm;
import cz.vutbr.fit.simulatormanager.components.SimulatorModelsList;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;
import cz.vutbr.fit.simulatormanager.types.PageType;
import cz.vutbr.fit.simulatormanager.util.ResourceUtil;

public class SimulatorModelsView extends VerticalLayout implements View {
    private static final long serialVersionUID = 1L;
    final static Logger LOG = LoggerFactory.getLogger(SimulatorModelsView.class);
    private final static String INVALID_CONFIG = "Couldn't save the configuration, because it's invalid";
    private final static String VALID_CONFIG = "Successfully updated the configuration";

    private Navigator navigator;
    private MainMenuBar mainMenu;
    private Image ev97Img;
    private HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
    private Label selectedSimulatorModelName = new Label("", ContentMode.HTML);
    private VerticalLayout leftLayout = new VerticalLayout();
    private VerticalLayout rightLayout = new VerticalLayout();
    private Button removeSimulatorModelButton = new Button("Remove simulator model");
    private Button addSimulatorModelButton = new Button("Add a simulator model");
    private Button addEngineButton = new Button("Add a new engine");
    private Button saveButton = new Button("Save changes to simulator model");
    private FormLayout formLayout = new FormLayout();
    private DatabaseHelper dbHelper = new DatabaseHelper();

    private SimulatorModelsList simulatorModelsList;
    private SimulatorModelForm simulatorModelForm;

    private EnginesTabSheet enginesTabsheet;
    private Panel enginesPanel;

    public DatabaseHelper getDbHelper() {
	return dbHelper;
    }

    @Override
    public void enter(ViewChangeEvent event) {
	simulatorModelsList.updateContainerDataSource();
	// deselect the previously selected simulator model
	this.simulatorModelsList.select(simulatorModelsList.getNullSelectionItemId());
    }

    public SimulatorModelsView(Navigator navigator) {
	LOG.info("new SimulatorModelsView()");
	this.navigator = navigator;
	initMenu();
	initSimulatorModelsList();
	initLayout();
	addClickListeners();
    }

    private void addClickListeners() {
	newModelClickListener();
	removeModelClickListener();
	newEngineClickListener();
	saveButtonClickListener();
    }

    /**
     * Save button click listener. On clicking save button validate the entered
     * data. If validation is successful, save updates to database. If not,
     * display an error message
     */
    private void saveButtonClickListener() {
	saveButton.addClickListener(new ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		try {
		    SimulatorModelConfigurationChecker validator = new SimulatorModelConfigurationChecker(
			    enginesTabsheet, simulatorModelForm);
		    boolean isConfigurationValid = validator.verifyConfiguration(INVALID_CONFIG, VALID_CONFIG);
		    if (isConfigurationValid) {
			simulatorModelForm.commit();
			simulatorModelsList.commit();
			enginesTabsheet.getEnginesContainer().commit();
		    } else {
			if (enginesTabsheet.getEnginesContainer().size() == 0) {
			    // reset engines for simulator in case the last
			    // engine was attempted to be removed
			    enginesTabsheet.getEnginesContainer().rollback();
			    enginesTabsheet.setEnginesForSimulator(((RowId) simulatorModelsList.getValue()).toString());
			}

		    }
		} catch (UnsupportedOperationException | SQLException e) {
		    Notification.show(
			    "Error occured when trying to commit engines form. Error message: " + e.getMessage(), "",
			    Notification.Type.ERROR_MESSAGE);
		}
	    }
	});
    }

    private void newEngineClickListener() {
	addEngineButton.addClickListener(new ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		enginesTabsheet.addNewEngine(((RowId) simulatorModelsList.getValue()).toString());
	    }
	});
    }

    private void removeModelClickListener() {
	removeSimulatorModelButton.addClickListener(new ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		selectedSimulatorModelName.setVisible(false);
		simulatorModelsList.removeSimulator();
	    }
	});
    }

    private void newModelClickListener() {
	addSimulatorModelButton.addClickListener(new ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		simulatorModelsList.addSimulatorModel();
	    }
	});
    }

    private void initSimulatorModelsList() {
	simulatorModelsList = new SimulatorModelsList(this);
    }

    private void initLayout() {
	setSizeFull();
	initButtons();
	addComponent(mainMenu);
	addComponent(horizontalSplitPanel);
	setExpandRatio(horizontalSplitPanel, 20);
	initHorizontalSplitPanel();
	initLeftLayout();
	initRightLayout();
    }

    private void initLeftLayout() {
	leftLayout.setSizeFull();
	leftLayout.setMargin(new MarginInfo(false, false, true, true));
	leftLayout.addComponent(new Label("<b>Simulator models</b>", ContentMode.HTML));
	leftLayout.addComponent(simulatorModelsList);
	leftLayout.addComponent(addSimulatorModelButton);
	leftLayout.addComponent(removeSimulatorModelButton);
	leftLayout.addComponent(saveButton);
	leftLayout.setExpandRatio(simulatorModelsList, 20);
    }

    private void initRightLayout() {
	rightLayout.setMargin(new MarginInfo(true, true, true, true));
	rightLayout.addComponent(selectedSimulatorModelName);
	ev97Img = new Image("After selecting simulator on the left, you will be able to configure it here",
		ResourceUtil.getEv97Img());
	ev97Img.setSizeFull();
	rightLayout.addComponent(ev97Img);
	formLayout.setVisible(false);
	rightLayout.addComponent(formLayout);
	initSimulatorModelForm();
	addEnginesToRightPanel();

    }

    private void addEnginesToRightPanel() {
	enginesTabsheet = new EnginesTabSheet(this);
	enginesPanel = new Panel("Engines on this simulator model", enginesTabsheet);
	enginesPanel.setWidth("100%");
	enginesPanel.setVisible(false);
	rightLayout.addComponent(addEngineButton);
	rightLayout.addComponent(enginesPanel);
    }

    private void initButtons() {
	removeSimulatorModelButton.setEnabled(false);
	removeSimulatorModelButton.setIcon(ResourceUtil.getMinusImgResource());
	addSimulatorModelButton.setStyleName("simulatorsAddSimulator");
	addSimulatorModelButton.setIcon(ResourceUtil.getPlusImgResource());
	addEngineButton.setIcon(ResourceUtil.getPlusImgResource());
	addEngineButton.setVisible(false);
	saveButton.setIcon(ResourceUtil.getSaveImgResource());
	saveButton.setEnabled(false);

    }

    private void initSimulatorModelForm() {
	simulatorModelForm = new SimulatorModelForm(this);
    }

    private void initHorizontalSplitPanel() {
	horizontalSplitPanel.addComponent(leftLayout);
	horizontalSplitPanel.addComponent(rightLayout);
	horizontalSplitPanel.setSplitPosition(35);
    }

    private void initMenu() {
	mainMenu = MainMenuBar.getInstance(navigator, PageType.MANAGE_SIMULATOR_MODELS);
    }

    public FormLayout getFormLayout() {
	return formLayout;
    }

    public SimulatorModelForm getSimulatorModelForm() {
	return simulatorModelForm;
    }

    public Label getSelectedSimulatorModelName() {
	return selectedSimulatorModelName;
    }

    public Image getRightPanelImage() {
	return ev97Img;
    }

    public Panel getEnginesPanel() {
	return enginesPanel;
    }

    public Button getAddEngineButton() {
	return addEngineButton;
    }

    public Button getRemoveSimulatorModelButton() {
	return removeSimulatorModelButton;
    }

    public EnginesTabSheet getEnginesTabSheet() {
	return enginesTabsheet;
    }

    public Button getSaveButton() {
	return saveButton;
    }

    public SimulatorModelsList getSimulatorModelList() {
	return simulatorModelsList;
    }
}
