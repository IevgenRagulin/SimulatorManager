package com.example.testvaadin;

import com.example.testvaadin.components.SelectSimulatorCombo;
import com.example.testvaadin.components.SimulationStateFieldGroup;
import com.example.testvaadin.data.ColumnNames;
import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;

public class RunningSimulationsView extends BasicView implements View {

	public class StatusRefreshListener implements RefreshListener {
		private static final long serialVersionUID = 392864906906738406L;

		public void refresh(final Refresher source) {
			getSelectSimulator().handleValueChangeEvent();
		}
	}

	private static final long serialVersionUID = -1785707193097941934L;
	private Navigator navigator;
	private FormLayout simulatorInfoLayout = new FormLayout();
	private FormLayout simulationInfoLayout = new FormLayout();
	private FormLayout simulationDevicesStateLayout = new FormLayout();
	private SimulationStateFieldGroup simulatorInfo;
	private SimulationStateFieldGroup simulationInfo;
	private SimulationStateFieldGroup simulationDevicesState;
	private SelectSimulatorCombo selectSimulator;

	public SelectSimulatorCombo getSelectSimulator() {
		return selectSimulator;
	}

	private Button buttonToMainMenu;

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
		setClickListeners();

		StatusRefreshListener listener = new StatusRefreshListener();
		final Refresher refresher = new Refresher();
		refresher.addListener(listener);
		refresher.setRefreshInterval(1000);
		addExtension(refresher);

	}

	private void setClickListeners() {
		buttonToMainMenu.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4243499910765394003L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("");
				selectSimulator.unscheduleUpdates();
			}
		});
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
		buttonToMainMenu = new Button("Go to start page");
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
		selectSimulator.initSelectSimulator();
		selectSimulator.handleValueChangeEvent();
	}

}
