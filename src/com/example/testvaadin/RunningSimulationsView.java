package com.example.testvaadin;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.SelectSimulatorCombo;
import com.example.testvaadin.components.SimulationStateFieldGroup;
import com.example.testvaadin.data.ColumnNames;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.FormLayout;

public class RunningSimulationsView extends BasicView implements View {

	private static final long serialVersionUID = -1785707193097941934L;
	private Navigator navigator;
	private DatabaseHelper dbHelp = new DatabaseHelper();
	private SQLContainer simulatorContainer = dbHelp.getSimulatorContainer();
	private FormLayout simulatorInfoLayout = new FormLayout();
	private FormLayout simulationInfoLayout = new FormLayout();
	private FormLayout simulationDevicesStateLayout = new FormLayout();
	private SimulationStateFieldGroup simulatorInfo;
	private SimulationStateFieldGroup simulationInfo;
	private SimulationStateFieldGroup simulationDevicesState;
	private SelectSimulatorCombo selectSimulator;
	private ButtonToMainMenu buttonToMainMenu;

	public SimulationStateFieldGroup getSimulatorInfo() {
		return simulatorInfo;
	}

	public SimulationStateFieldGroup getSimulationInfo() {
		return simulationInfo;
	}

	public void setSimulationInfo(SimulationStateFieldGroup simulationInfo) {
		this.simulationInfo = simulationInfo;
	}

	public SimulationStateFieldGroup getSimulatorDevicesState() {
		return simulationDevicesState;
	}

	public SQLContainer getSqlContainer() {
		return simulatorContainer;
	}

	public FormLayout getSimulatorInfoLayout() {
		return simulatorInfoLayout;
	}

	public FormLayout getSimulationInfoLayout() {
		return simulationInfoLayout;
	}

	public RunningSimulationsView(Navigator navigator) {
		this.navigator = navigator;
		initSelectSimulator();
		initLayout();
		initSimulatorsInfo();
		initSimulationInfo();
		initSimulationDevicesState();
	}

	private void initSimulationInfo() {
		simulationInfo = new SimulationStateFieldGroup(this,
				ColumnNames.getSimulationCols(), simulationInfoLayout);
		simulationInfo.setEnabled(false);
	}

	private void initSimulationDevicesState() {
		simulationDevicesState = new SimulationStateFieldGroup(this,
				ColumnNames.getSimulationDevicesStateCols(),
				simulationDevicesStateLayout);
		simulationDevicesState.setEnabled(false);
	}

	private void initSelectSimulator() {
		selectSimulator = new SelectSimulatorCombo(this);
	}

	private void initSimulatorsInfo() {
		simulatorInfo = new SimulationStateFieldGroup(this,
				ColumnNames.getSimulatorMainCols(), simulatorInfoLayout);
		simulatorInfo.setEnabled(false);
	}

	private void initLayout() {
		buttonToMainMenu = new ButtonToMainMenu(navigator);
		addComponent(buttonToMainMenu);
		addComponent(selectSimulator);
		simulatorInfoLayout.setCaption("Simulator info");
		simulationInfoLayout.setCaption("Simulation info");
		simulationDevicesStateLayout.setCaption("Simulation devices state");
		addComponent(simulatorInfoLayout);
		addComponent(simulationInfoLayout);
		addComponent(simulationDevicesStateLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
