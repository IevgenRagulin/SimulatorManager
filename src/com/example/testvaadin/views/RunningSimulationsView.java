package com.example.testvaadin.views;

import java.math.BigDecimal;

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
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Button;
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
	private FormLayout simulationLayout = new FormLayout();
	private FormLayout simulationInfoLayout = new FormLayout();
	private FormLayout simulationDevicesStateLayout = new FormLayout();
	private ErrorLabel errorLabel = new ErrorLabel("");
	private InfoLabel simulatorInfoLabel = new InfoLabel("Simulator info");
	private InfoLabel simulationLabel = new InfoLabel("Simulation");
	private InfoLabel simulationInfoLabel = new InfoLabel("Simulation info");
	private InfoLabel simulatorDevicesStateLabel = new InfoLabel(
			"Simulator devices state");
	private SimulationStateFieldGroup simulatorInfo;
	private SimulationStateFieldGroup simulation;
	private SimulationStateFieldGroup simulationInfo;
	private SimulationStateFieldGroup simulationDevicesState;
	private PrimaryFlightDisplay primaryFlightDisplay;
	private SelectSimulatorCombo selectSimulator;
	private String apiKey = "AIzaSyDObpG4jhLAo88_GE8FHJhg-COWVgi_gr4";
	GoogleMap googleMap = null;

	public SelectSimulatorCombo getSelectSimulator() {
		return selectSimulator;
	}

	private ButtonToMainMenu buttonToMainMenu;

	public SimulationStateFieldGroup getSimulatorInfo() {
		return simulatorInfo;
	}

	public SimulationStateFieldGroup getSimulation() {
		return simulation;
	}

	public SimulationStateFieldGroup getSimulationInfo() {
		return simulationInfo;
	}

	public void setSimulationInfo(SimulationStateFieldGroup simulationInfo) {
		this.simulation = simulationInfo;
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
		return simulationLayout;
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
		initSimulationInfoInfo();
		initLayout();
		setClickListeners();
		initPageRefresher();
		initPrimaryFlightDisplay();
		initGoogleMaps();

	}

	private void initGoogleMaps() {
		googleMap = new GoogleMap(new LatLon(60.440963, 22.25122), 10.0, apiKey);
		googleMap.setSizeFull();
		googleMap.addMarker("DRAGGABLE: Kakolan vankila", new LatLon(60.44291,
				22.242415), true, null);
		googleMap.setMinZoom(1.0);
		googleMap.setMaxZoom(16.0);
		googleMap.setWidth("500px");
		googleMap.setHeight("500px");
		addComponent(googleMap);
		addComponent(new Button("i am a button"));
	}

	private void initPrimaryFlightDisplay() {
		primaryFlightDisplay = new PrimaryFlightDisplay("index.html", 0, 0, 0,
				0, 0, 0);
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
		simulation = new SimulationStateFieldGroup(
				ColumnNames.getSimulationCols(), simulationLayout);
		simulation.setEnabled(false);
	}

	private void initSimulationInfoInfo() {
		simulationInfo = new SimulationStateFieldGroup(
				ColumnNames.getSimulationInfoCols(), simulationInfoLayout);
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
		addComponent(simulationLabel);
		addComponent(simulationLayout);
		addComponent(simulatorDevicesStateLabel);
		addComponent(simulationDevicesStateLayout);
		addComponent(simulationInfoLabel);
		addComponent(simulationInfoLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		selectSimulator.initSelectSimulator();
		selectSimulator.handleValueChangeEvent();
	}

	public void setAllSimulationSimulatorData(RowId rowId) {
		setSimulatorInfoData(rowId);
		setSimulationData(getSimulatorIdByRowId(rowId));
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

	private void setSimulationData(final Property<?> property) {
		final SQLContainer simulationContainer = getDBHelp()
				.getLatestRunningSimulationOnSimulatorWithId(
						property.getValue().toString());
		getErrorLabel().setValue(EMPTY_STRING);
		if (simulationContainer.size() != 0) {
			final RowId id = (RowId) simulationContainer.getIdByIndex(0);
			getSimulation().setItemDataSource(simulationContainer.getItem(id));
			getSimulation().setEnabled(true);
			getSimulation().setReadOnly(true);
		} else {
			getSimulation().setEnabled(false);
		}
	}

	private void setSimulationInfoData(Property<?> property) {
		final SQLContainer simulationInfoContainer = getDBHelp()
				.getSimulationInfoBySimulatorId(property.getValue().toString());
		if ((simulationInfoContainer != null)
				&& (simulationInfoContainer.size() != 0)) {
			// Set simulation info data
			final RowId id = (RowId) simulationInfoContainer.getIdByIndex(0);
			getSimulationInfo().setItemDataSource(
					simulationInfoContainer.getItem(id));
			getSimulationInfo().setEnabled(true);
			getSimulationInfo().setReadOnly(true);
			// Add simulation info data to map
			Item item = simulationInfoContainer.getItem(id);
			double newLongtitude = ((BigDecimal) ((Property<?>) item
					.getItemProperty("Longtitude")).getValue()).doubleValue();
			double newLatitude = ((BigDecimal) ((Property<?>) item
					.getItemProperty("Latitude")).getValue()).doubleValue();

			googleMap.addMarker("Aircraft position", new LatLon(newLatitude,
					newLongtitude), true, null);

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
		getSimulation().setEnabled(false);
		getSimulatorDevicesState().setEnabled(false);
		getSimulationInfo().setEnabled(false);
	}

	public void setNoSimulationsRunningState(RowId rowId) {
		setSimulatorInfoData(rowId);
		getErrorLabel().setValue(NO_RUNNING_SIMULATIONS);
		getSimulation().setEnabled(false);
		getSimulatorDevicesState().setEnabled(false);
		getSimulationInfo().setEnabled(false);
	}

}
