package cz.vutbr.fit.simulatormanager.views;

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
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import cz.vutbr.fit.simulatormanager.components.EnginesAccordion;
import cz.vutbr.fit.simulatormanager.components.MainMenuBar;
import cz.vutbr.fit.simulatormanager.components.SimulatorModelForm;
import cz.vutbr.fit.simulatormanager.components.SimulatorModelsList;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;
import cz.vutbr.fit.simulatormanager.types.PageType;
import cz.vutbr.fit.simulatormanager.util.ResourceUtil;

public class SimulatorModelsView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	final static Logger LOG = LoggerFactory.getLogger(SimulatorModelsView.class);

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
	private FormLayout formLayout = new FormLayout();
	private DatabaseHelper dbHelper = new DatabaseHelper();

	private SimulatorModelsList simulatorModelsList;
	private SimulatorModelForm simulatorModelForm;

	private EnginesAccordion enginesAccordion;
	private Panel enginesPanel;

	@Override
	public void enter(ViewChangeEvent event) {

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
	}

	private void newEngineClickListener() {
		addEngineButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				enginesAccordion.addNewEngine(((RowId) simulatorModelsList.getValue()).toString());
			}
		});
	}

	private void removeModelClickListener() {
		removeSimulatorModelButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				selectedSimulatorModelName.setVisible(false);
				simulatorModelForm.removeSimulator();
			}
		});
	}

	private void newModelClickListener() {
		addSimulatorModelButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				simulatorModelForm.addSimulatorModel();
			}
		});
	}

	private void initSimulatorModelsList() {
		simulatorModelsList = new SimulatorModelsList(this);
	}

	public SimulatorModelsList getSimulatorModelList() {
		return simulatorModelsList;
	}

	private void initLayout() {
		setSizeFull();
		addComponent(mainMenu);
		addComponent(horizontalSplitPanel);
		setExpandRatio(horizontalSplitPanel, 20);
		initHorizontalSplitPanel();
		initLeftLayout();
		initRightLayout();
	}

	private void initLeftLayout() {
		initAddSimulatorModelButton();
		leftLayout.setSizeFull();
		leftLayout.setMargin(new MarginInfo(false, false, true, true));
		leftLayout.addComponent(new Label("<b>Simulator models</b>", ContentMode.HTML));
		leftLayout.addComponent(simulatorModelsList);
		leftLayout.addComponent(addSimulatorModelButton);
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
		initRemoveSimulatorModelButton();
		addEnginesToRightPanel();

		rightLayout.addComponent(removeSimulatorModelButton);

	}

	private void addEnginesToRightPanel() {
		enginesAccordion = new EnginesAccordion(this);

		enginesPanel = new Panel("Engines on this simulator model", enginesAccordion);
		enginesPanel.setWidth("100%");
		enginesPanel.setVisible(false);
		rightLayout.addComponent(enginesPanel);

		addEngineButton.setVisible(false);
		addEngineButton.setIcon(ResourceUtil.getPlusImgResource());
		rightLayout.addComponent(addEngineButton);

	}

	private void initRemoveSimulatorModelButton() {
		removeSimulatorModelButton.setVisible(false);
		removeSimulatorModelButton.setIcon(ResourceUtil.getMinusImgResource());
	}

	private void initSimulatorModelForm() {
		simulatorModelForm = new SimulatorModelForm(this);
	}

	private void initAddSimulatorModelButton() {
		addSimulatorModelButton.setStyleName("simulatorsAddSimulator");
		addSimulatorModelButton.setIcon(ResourceUtil.getPlusImgResource());
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

	public DatabaseHelper getDbHelper() {
		return dbHelper;
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

	public EnginesAccordion getEnginesAccordeon() {
		return enginesAccordion;
	}
}
