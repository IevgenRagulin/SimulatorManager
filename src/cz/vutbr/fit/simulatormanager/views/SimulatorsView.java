package cz.vutbr.fit.simulatormanager.views;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
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
import com.vaadin.ui.VerticalLayout;

import cz.vutbr.fit.simulatormanager.components.AreYouSureDialog;
import cz.vutbr.fit.simulatormanager.components.MainMenuBar;
import cz.vutbr.fit.simulatormanager.components.SimulatorConfigurationChecker;
import cz.vutbr.fit.simulatormanager.components.SimulatorForm;
import cz.vutbr.fit.simulatormanager.components.SimulatorListSimulatorsView;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;
import cz.vutbr.fit.simulatormanager.database.columns.SimulatorCols;
import cz.vutbr.fit.simulatormanager.types.PageType;
import cz.vutbr.fit.simulatormanager.util.ResourceUtil;

/**
 * Page on which the user can add/remove/modify simulators information
 * 
 * @author ievgen
 * 
 */
@SuppressWarnings("serial")
public class SimulatorsView extends VerticalLayout implements View {
    final static Logger LOG = LoggerFactory.getLogger(SimulatorsView.class);
    private DatabaseHelper dbHelp = new DatabaseHelper();

    private HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
    private VerticalLayout leftLayout = new VerticalLayout();
    private VerticalLayout rightLayout = new VerticalLayout();
    private FormLayout editorLayout = new FormLayout();
    private Button removeSimulatorButton = new Button("Remove simulator");
    private Button addSimulatorButton = new Button("Add simulator");
    private Navigator navigator;
    private Label selectedSimulatorName = new Label("", ContentMode.HTML);
    private Image ev97Img;
    private MainMenuBar mainMenu;
    private Button saveButton = new Button("Save changes to simulator configuration");
    private SimulatorListSimulatorsView simulatorList;
    private SimulatorForm simulatorForm;
    private FieldGroup fieldGroupTest;

    public Label getSelectedSimulatorName() {
	return selectedSimulatorName;
    }

    public Button getSaveButton() {
	return saveButton;
    }

    public FormLayout getEditorLayout() {
	return editorLayout;
    }

    public Button getRemoveSimulatorButton() {
	return removeSimulatorButton;
    }

    public SimulatorForm getSimulatorForm() {
	return simulatorForm;
    }

    public DatabaseHelper getDBHelp() {
	return dbHelp;
    }

    public SimulatorsView(Navigator navigator) {
	LOG.debug("new SimulatorsView()");
	this.navigator = navigator;
	initSimulatorList();
	initLayout();
	addClickListeners();
    }

    private void initHorizontalSplitPanel() {
	horizontalSplitPanel.addComponent(leftLayout);
	horizontalSplitPanel.addComponent(rightLayout);
    }

    private void initSimulatorList() {
	this.simulatorList = new SimulatorListSimulatorsView(this);
    }

    private void initLayout() {
	setSizeFull();
	initMenu();
	initButtons();
	addComponent(mainMenu);
	addComponent(horizontalSplitPanel);
	setExpandRatio(horizontalSplitPanel, 20);
	initHorizontalSplitPanel();
	initLeftLayout();
	initRightLayout();
    }

    private void initButtons() {
	addSimulatorButton.setStyleName("simulatorsAddSimulator");
	addSimulatorButton.setIcon(ResourceUtil.getPlusImgResource());
	int numberOfSimModels = getNumberOfSimulatorModels();
	// add simulator button is enabled only if there are some simulator
	// models configured in the database
	addSimulatorButton.setEnabled(numberOfSimModels > 0 ? true : false);
	addSimulatorButton.setDescription("You can only add simulators if there is at lease one simulator model configured");
	removeSimulatorButton.setIcon(ResourceUtil.getMinusImgResource());
	removeSimulatorButton.setEnabled(false);
	saveButton.setIcon(ResourceUtil.getSaveImgResource());
	saveButton.setEnabled(false);
    }

    private void initMenu() {
	mainMenu = MainMenuBar.getInstance(navigator, PageType.MANAGE_SIMULATORS);
    }

    private void initLeftLayout() {
	leftLayout.setSizeFull();
	leftLayout.setMargin(new MarginInfo(false, false, true, true));
	leftLayout.addComponent(new Label("<b>Managed simulators</b>", ContentMode.HTML));
	leftLayout.addComponent(simulatorList);
	leftLayout.addComponent(saveButton);
	leftLayout.addComponent(addSimulatorButton);
	leftLayout.addComponent(removeSimulatorButton);
	leftLayout.setExpandRatio(simulatorList, 20);

    }

    /**
     * Retrieves a number of simulator models configured.
     * 
     * @return
     */
    private int getNumberOfSimulatorModels() {
	return getDBHelp().getNewSimulatorModelContainer().getItemIds().size();
    }

    private void initRightLayout() {
	rightLayout.setMargin(new MarginInfo(true, true, true, true));
	rightLayout.addComponent(selectedSimulatorName);
	ev97Img = new Image("After selecting simulator on the left, you will be able to configure it here",
		ResourceUtil.getEv97Img());
	ev97Img.setSizeFull();
	rightLayout.addComponent(ev97Img);
	editorLayout.setVisible(false);
	rightLayout.addComponent(editorLayout);
	initSimulatorForm();
    }

    public void commit() throws CommitException, UnsupportedOperationException, SQLException {
	LOG.info("Going to commit simulator form new!.. ");
	this.fieldGroupTest.commit();
	dbHelp.getCachedSimulatorContainer().commit();
    }

    private void initSimulatorForm() {
	simulatorForm = new SimulatorForm(this);
    }

    private void addClickListeners() {
	addAddClickListener();
	addRemoveClickListener();
	addSaveClickListener();
    }

    private void addSaveClickListener() {
	saveButton.addClickListener(new ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		simulatorList.commit();
		verifyConfiguration();
	    }
	});
    }

    private void verifyConfiguration() {
	RowId simulatorId = (RowId) simulatorList.getValue();
	LOG.info("SimulatorList" + simulatorList.getValue());
	String host = simulatorList.getItem(simulatorId).getItemProperty(SimulatorCols.hostname.toString()).getValue().toString();
	int port = Integer.valueOf(simulatorList.getItem(simulatorId).getItemProperty(SimulatorCols.port.toString()).getValue()
		.toString());
	new SimulatorConfigurationChecker(host, port, simulatorId.toString()).verifyConfiguration(
		SimulatorConfigurationChecker.SHOW_SUCCESS_MESSAGE, Notification.Type.ERROR_MESSAGE);
    }

    private void addAddClickListener() {
	addSimulatorButton.addClickListener(new ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		simulatorList.addSimulator();
	    }
	});
    }

    private void addRemoveClickListener() {
	removeSimulatorButton.addClickListener(new ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		AreYouSureDialog reallyDeleteDialog = new AreYouSureDialog(new ClickListener() {

		    @Override
		    public void buttonClick(ClickEvent event) {
			selectedSimulatorName.setVisible(false);
			simulatorList.removeSimulator();
		    }
		});

		navigator.getUI().addWindow(reallyDeleteDialog);

	    }
	});
    }

    @Override
    public void enter(ViewChangeEvent event) {
	LOG.debug("enter() - entering SimulatorsView");
	// update datasource. Don't call initSimulatorList, it causes an ugly
	// defect with 2 instances of SimulatorListSimulatorsView
	this.simulatorList.updateContainerDatasource();
	// deselect the previously selected simulator
	this.simulatorList.select(simulatorList.getNullSelectionItemId());
	int simulatorModelsNum = getNumberOfSimulatorModels();
	if (getNumberOfSimulatorModels() == 0) {
	    Notification
		    .show("You should first configure a simulator model. It's not possible to add a simulator without having a simulator model",
			    "Please, go to 'Manage simulator models' page and configure a simulator model there",
			    Notification.Type.HUMANIZED_MESSAGE);
	}
	addSimulatorButton.setEnabled(simulatorModelsNum != 0);
    }

    public Image getRightPanelImage() {
	return ev97Img;
    }

}