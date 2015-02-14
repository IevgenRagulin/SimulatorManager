package com.example.testvaadin.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.testvaadin.NavigatorUI;
import com.example.testvaadin.components.SimulationList;
import com.example.testvaadin.components.SimulatorListChooseSimulationView;
import com.example.testvaadin.data.DatabaseHelper;
import com.example.testvaadin.data.SimulationCols;
import com.vaadin.data.Item;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

public class ChooseSimulationView extends VerticalSplitPanel implements View {

	final static Logger logger = LoggerFactory.getLogger(ChooseSimulationView.class);

	private static final long serialVersionUID = 1279140216863014337L;
	private Navigator navigator;
	private SimulationList simulationList;
	SimulatorListChooseSimulationView simulatorList;
	private HorizontalSplitPanel bottomLayout = new HorizontalSplitPanel();
	private VerticalLayout actionButtons = new VerticalLayout();
	private Button deleteButton = new Button("Delete chosen simulation");
	private Button viewSimButton = new Button("View chosen simulation");
	protected DatabaseHelper dbHelp = new DatabaseHelper();

	@Override
	public void enter(ViewChangeEvent event) {
	}

	public DatabaseHelper getDBHelp() {
		return dbHelp;
	}

	public ChooseSimulationView(Navigator navigator) {
		logger.info("new ChooseSimulationView()");
		this.navigator = navigator;
		initSimulatorList();
		initSimulationList();
		initLayout();
		// initEditor();
		addClickListeners();
	}

	private void addClickListeners() {
		deleteButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 2511131902775324917L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					simulationList.removeSimulation();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		viewSimButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 2273135571079398635L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (isSelectedSimulationRunning()) {
					navigator.navigateTo(NavigatorUI.RUNNINGSIMULATIONS + "/" + getSimulatorList().getValue());
				} else {
					navigator.navigateTo(NavigatorUI.PASTSIMULATIONS + "/" + getSimulationList().getValue());
				}
			}
		});
	}

	private boolean isSelectedSimulationRunning() {
		Item item = getSimulationList().getItem(getSimulationList().getValue());
		return (Boolean) item.getItemProperty(SimulationCols.issimulationon.toString()).getValue();
	}

	private void initLayout() {
		addComponent(simulatorList);
		addComponent(bottomLayout);
		bottomLayout.setSizeFull();
		bottomLayout.addComponent(simulationList);
		bottomLayout.addComponent(actionButtons);
		bottomLayout.setSplitPosition(80);
		simulationList.setVisible(false);
		actionButtons.setVisible(false);
		actionButtons.addComponent(deleteButton);
		actionButtons.addComponent(viewSimButton);
		actionButtons.setSizeFull();
		actionButtons.setComponentAlignment(deleteButton, Alignment.MIDDLE_CENTER);
		actionButtons.setComponentAlignment(viewSimButton, Alignment.MIDDLE_CENTER);
	}

	private void initSimulationList() {
		simulationList = new SimulationList(this);
	}

	private void initSimulatorList() {
		logger.debug("Going to init simulator list");
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

}
