package com.example.testvaadin.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.testvaadin.NavigatorUI;
import com.example.testvaadin.components.MainMenuBar;
import com.example.testvaadin.components.SimulationList;
import com.example.testvaadin.components.SimulatorListChooseSimulationView;
import com.example.testvaadin.data.DatabaseHelper;
import com.example.testvaadin.data.SimulationCols;
import com.example.testvaadin.types.PageType;
import com.vaadin.data.Item;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

public class ChooseSimulationView extends VerticalLayout implements View {

	final static Logger logger = LoggerFactory
			.getLogger(ChooseSimulationView.class);
	private static final long serialVersionUID = 1279140216863014337L;
	private Navigator navigator;
	private SimulationList simulationList;
	private SimulatorListChooseSimulationView simulatorList;
	private VerticalSplitPanel bottomVerticalSplitPanel = new VerticalSplitPanel();
	private VerticalLayout actionButtons = new VerticalLayout();
	private Button deleteButton = new Button("Delete chosen simulation");
	private Button viewSimButton = new Button("View chosen simulation");
	protected DatabaseHelper dbHelp = new DatabaseHelper();
	MainMenuBar mainMenu;
	Label simulationSessionsLabel;

	@Override
	public void enter(ViewChangeEvent event) {
		logger.info("Entering ChooseSimulationView");
		setSizeFull();
		initMenu();
		// in case we added/removed simulators on simulator management page, the
		// simulators list on this page needs to be updated
		simulatorList.updateSimulatorsList();
	}

	public DatabaseHelper getDBHelp() {
		return dbHelp;
	}

	public ChooseSimulationView(Navigator navigator) {
		super();
		logger.info("new ChooseSimulationView()");
		this.navigator = navigator;
		initMenu();
		initSimulatorList();
		initSimulationList();
		initLayout();
		addClickListeners();
		setMargin(new MarginInfo(false, true, false, true));
	}

	private void initMenu() {
		mainMenu = MainMenuBar.getInstance(navigator,
				PageType.CHOOSE_SIMULATION);
		mainMenu.setHeight("35px");
	}

	private void addClickListeners() {
		deleteButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 2511131902775324917L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					simulationList.removeSimulation();
				} catch (Exception e) {
					throw new RuntimeException("Could not remove simulation", e);
				}
			}
		});

		viewSimButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 2273135571079398635L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (isSelectedSimulationRunning()) {
					navigator.navigateTo(NavigatorUI.RUNNINGSIMULATIONS + "/"
							+ getSimulatorList().getValue());
				} else {
					navigator.navigateTo(NavigatorUI.PASTSIMULATIONS + "/"
							+ getSimulationList().getValue());
				}
			}
		});
	}

	private boolean isSelectedSimulationRunning() {
		Item item = getSimulationList().getItem(getSimulationList().getValue());
		return (Boolean) item.getItemProperty(
				SimulationCols.issimulationon.toString()).getValue();
	}

	private void initLayout() {
		addComponent(mainMenu);
		addComponent(new Label(
				"<p style='text-align: center;'><b>Select simulator</b></p>",
				ContentMode.HTML));
		addComponent(simulatorList);
		simulationSessionsLabel = new Label(
				"<p style='text-align: center;'><b>Simulation sessions on the selected simulator</b></p>",
				ContentMode.HTML);
		simulationSessionsLabel.setVisible(false);
		addComponent(simulationSessionsLabel);
		addComponent(bottomVerticalSplitPanel);
		bottomVerticalSplitPanel.setSizeFull();
		VerticalLayout bottomLeftLayout = new VerticalLayout();
		bottomVerticalSplitPanel.addComponent(bottomLeftLayout);
		bottomLeftLayout.addComponent(simulationList);
		bottomVerticalSplitPanel.addComponent(actionButtons);
		bottomVerticalSplitPanel.setSplitPosition(80);
		simulationList.setVisible(false);
		actionButtons.setVisible(false);
		actionButtons.addComponent(deleteButton);
		actionButtons.addComponent(viewSimButton);
		actionButtons.setSizeFull();
		actionButtons.setComponentAlignment(deleteButton,
				Alignment.MIDDLE_CENTER);
		actionButtons.setComponentAlignment(viewSimButton,
				Alignment.MIDDLE_CENTER);

		setExpandRatio(mainMenu, 1);
		setExpandRatio(simulatorList, 10);
		setExpandRatio(bottomVerticalSplitPanel, 10);
	}

	private void initSimulationList() {
		logger.info("Going to init simulation list");
		simulationList = new SimulationList(this);
	}

	private void initSimulatorList() {
		logger.info("Going to init simulator list");
		simulatorList = new SimulatorListChooseSimulationView(this);
	}

	public SimulatorListChooseSimulationView getSimulatorList() {
		return simulatorList;
	}

	public VerticalLayout getActionButtons() {
		return actionButtons;
	}

	public SimulationList getSimulationList() {
		return simulationList;
	}

	public Label getSimulationSessionsLabel() {
		return simulationSessionsLabel;
	}

}
