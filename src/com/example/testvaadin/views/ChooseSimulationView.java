package com.example.testvaadin.views;

import com.example.testvaadin.NavigatorUI;
import com.example.testvaadin.components.SimulationList;
import com.example.testvaadin.components.SimulatorListChooseSimulationView;
import com.example.testvaadin.data.DatabaseHelper;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

public class ChooseSimulationView extends VerticalSplitPanel implements View {
	private static final long serialVersionUID = 1279140216863014337L;
	private Navigator navigator;
	private SimulationList simulationList;
	SimulatorListChooseSimulationView simulatorList;
	private HorizontalLayout bottomLayout = new HorizontalLayout();
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
					System.out.println("GOING TO DELETE THIS SIMULATION");
					simulationList.removeSimulation();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		viewSimButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(NavigatorUI.RUNNINGSIMULATIONS + "/"
						+ getSimulatorList().getValue());
			}
		});
	}

	private void initLayout() {
		addComponent(simulatorList);
		addComponent(bottomLayout);
		bottomLayout.setSizeFull();
		bottomLayout.addComponent(simulationList);
		bottomLayout.addComponent(actionButtons);
		simulationList.setVisible(false);
		actionButtons.setVisible(false);
		actionButtons.addComponent(deleteButton);
		actionButtons.addComponent(viewSimButton);
		actionButtons.setSizeFull();
		actionButtons.setComponentAlignment(deleteButton,
				Alignment.MIDDLE_CENTER);
		actionButtons.setComponentAlignment(viewSimButton,
				Alignment.MIDDLE_CENTER);
	}

	private void initSimulationList() {
		simulationList = new SimulationList(this);
	}

	private void initSimulatorList() {
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
