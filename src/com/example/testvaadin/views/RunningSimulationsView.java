package com.example.testvaadin.views;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.ErrorLabel;
import com.example.testvaadin.components.InfoLabel;
import com.example.testvaadin.components.SelectSimulatorCombo;
import com.example.testvaadin.components.SimulationStateFieldGroup;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.javascriptcomponents.PrimaryFlightDisplay;
import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;

public class RunningSimulationsView extends BasicView implements View {
	public class StatusRefreshListener implements RefreshListener {
		private static final long serialVersionUID = 392864906906738406L;

		public void refresh(final Refresher source) {
			getSelectSimulator().handleValueChangeEvent();
		}
	}

	private static final String NO_SIMULATOR_SELECTED = "Please, select simulator";
	private static final String EMPTY_STRING = "";
	private static final String NO_RUNNING_SIMULATIONS = "There are no simulations currently running on this simulator";

	private static final long serialVersionUID = -1785707193097941934L;
	private Navigator navigator;
	private FormLayout simulatorInfoLayout = new FormLayout();
	private FormLayout simulationInfoLayout = new FormLayout();
	private FormLayout simulationDevicesStateLayout = new FormLayout();
	private ErrorLabel errorLabel = new ErrorLabel("");
	private InfoLabel simulatorInfoLabel = new InfoLabel("Simulator info");
	private InfoLabel simulationInfoLabel = new InfoLabel("Simulation info");
	private InfoLabel simulatorDevicesStateLabel = new InfoLabel(
			"Simulator devices state");
	private SimulationStateFieldGroup simulatorInfo;
	private SimulationStateFieldGroup simulationInfo;
	private SimulationStateFieldGroup simulationDevicesState;
	private PrimaryFlightDisplay primaryFlightDisplay;
	private SelectSimulatorCombo selectSimulator;

	public SelectSimulatorCombo getSelectSimulator() {
		return selectSimulator;
	}

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

	public PrimaryFlightDisplay getPrimaryFlightDisplay() {
		return primaryFlightDisplay;
	}

	public FormLayout getSimulatorInfoLayout() {
		return simulatorInfoLayout;
	}

	public FormLayout getSimulationInfoLayout() {
		return simulationInfoLayout;
	}

	public Label getErrorLabel() {
		return errorLabel;
	}

	public RunningSimulationsView(Navigator navigator) {
		this.navigator = navigator;
		initButtonToMainMenu();
		initSelectSimulatorCombo();
		initSimulatorsInfo();
		initSimulationInfo();
		initSimulationDevicesState();
		initLayout();
		setClickListeners();
		initPageRefresher();
		initPrimaryFlightDisplay();

	}

	private void initPrimaryFlightDisplay() {
		primaryFlightDisplay = new PrimaryFlightDisplay("index.html", 0, 0, 0,
				0, 0);
		addComponent(primaryFlightDisplay);
	}

	private void initButtonToMainMenu() {
		buttonToMainMenu = new ButtonToMainMenu(navigator);
	}

	private void initPageRefresher() {
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
			}
		});
	}

	private void initSimulationInfo() {
		simulationInfo = new SimulationStateFieldGroup(
				ColumnNames.getSimulationCols(), simulationInfoLayout);
		simulationInfo.setEnabled(false);
	}

	private void initSimulationDevicesState() {
		simulationDevicesState = new SimulationStateFieldGroup(
				ColumnNames.getSimulationDevicesStateCols(),
				simulationDevicesStateLayout);
		simulationDevicesState.setEnabled(false);
	}

	private void initSelectSimulatorCombo() {
		selectSimulator = new SelectSimulatorCombo(this);
	}

	private void initSimulatorsInfo() {
		simulatorInfo = new SimulationStateFieldGroup(
				ColumnNames.getSimulatorMainCols(), simulatorInfoLayout);
		simulatorInfo.setEnabled(false);
	}

	private void initLayout() {
		addComponent(buttonToMainMenu);
		addComponent(selectSimulator);
		addComponent(errorLabel);
		addComponent(simulatorInfoLabel);
		addComponent(simulatorInfoLayout);
		addComponent(simulationInfoLabel);
		addComponent(simulationInfoLayout);
		addComponent(simulatorDevicesStateLabel);
		addComponent(simulationDevicesStateLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		selectSimulator.initSelectSimulator();
		selectSimulator.handleValueChangeEvent();
	}

	public void setAllSimulationSimulatorData(RowId rowId) {
		setSimulatorInfoData(rowId);
		setSimulationInfoData(getSimulatorIdByRowId(rowId));
		setSimulationDevicesStateInfo(getSimulatorIdByRowId(rowId));
		setPrimaryFlightDisplayInfo(getSimulatorIdByRowId(rowId));

	}

	private void setSimulatorInfoData(RowId rowId) {
		Item selectedItem = getDBHelp().getSimulatorContainer().getItem(rowId);
		getSimulatorInfo().setItemDataSource(selectedItem);
		getSimulatorInfo().setReadOnly(true);
		getSimulatorInfo().setEnabled(true);
	}

	private void setSimulationInfoData(final Property<?> property) {
		final SQLContainer simulationContainer = getDBHelp()
				.getLatestRunningSimulationOnSimulatorWithId(
						property.getValue().toString());
		getErrorLabel().setValue(EMPTY_STRING);
		if (simulationContainer.size() != 0) {
			final RowId id = (RowId) simulationContainer.getIdByIndex(0);
			getSimulationInfo().setItemDataSource(
					simulationContainer.getItem(id));
			getSimulationInfo().setEnabled(true);
			getSimulationInfo().setReadOnly(true);
		} else {
			getSimulationInfo().setEnabled(false);
		}
	}

	private void setSimulationDevicesStateInfo(final Property<?> property) {
		final SQLContainer simulationDevicesStateContainer = getDBHelp()
				.getSimulationDevicesStateBySimulatorId(
						property.getValue().toString());
		if ((simulationDevicesStateContainer != null)
				&& (simulationDevicesStateContainer.size() != 0)) {
			final RowId id = (RowId) simulationDevicesStateContainer
					.getIdByIndex(0);
			getSimulatorDevicesState().setItemDataSource(
					simulationDevicesStateContainer.getItem(id));
			getSimulatorDevicesState().setEnabled(true);
			getSimulatorDevicesState().setReadOnly(true);
		} else {
			getSimulatorDevicesState().setEnabled(false);
		}
	}

	private void setPrimaryFlightDisplayInfo(Property<?> property) {
		final SQLContainer simulationPFDContainer = getDBHelp()
				.getSimulationPFDBySimulatorId(property.getValue().toString());
		if ((simulationPFDContainer != null)
				&& (simulationPFDContainer.size() != 0)) {
			final RowId id = (RowId) simulationPFDContainer.getIdByIndex(0);
			Item item = simulationPFDContainer.getItem(id);

			primaryFlightDisplay.updateIndividualPFDValues(item);
		} else {
		}
	}

	public Property<?> getSimulatorIdByRowId(RowId rowId) {
		return getDBHelp().getSimulatorContainer().getContainerProperty(rowId,
				"SimulatorId");
	}

	public void setSimulatorNotSelectedState() {
		getErrorLabel().setValue(NO_SIMULATOR_SELECTED);
		getSimulatorInfo().setEnabled(false);
		getSimulationInfo().setEnabled(false);
		getSimulatorDevicesState().setEnabled(false);
	}

	public void setNoSimulationsRunningState(RowId rowId) {
		setSimulatorInfoData(rowId);
		getErrorLabel().setValue(NO_RUNNING_SIMULATIONS);
		getSimulationInfo().setEnabled(false);
		getSimulatorDevicesState().setEnabled(false);
	}

}
