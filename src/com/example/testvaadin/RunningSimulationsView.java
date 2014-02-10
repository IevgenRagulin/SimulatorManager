package com.example.testvaadin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.example.testvaadin.components.SimulatorInfo;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;

public class RunningSimulationsView extends BasicView implements View {

	private static final long serialVersionUID = -1785707193097941934L;
	private Navigator navigator;
	private Button buttonToMainMenu = new Button("Go to start page");
	private DatabaseHelper dbHelp = new DatabaseHelper();
	private SQLContainer simulatorContainer = dbHelp.getSimulatorContainer();
	private FormLayout editorLayout = new FormLayout();
	private Map<String, RowId> simulatorsIdNamesMapping = new HashMap<String, RowId>();
	private SimulatorInfo simulatorInfo;
	private ComboBox selectSimulator = new ComboBox("Simulator name:");;

	public SimulatorInfo getSimulatorInfo() {
		return simulatorInfo;
	}

	public FormLayout getEditorLayout() {
		return editorLayout;
	}

	public RunningSimulationsView(Navigator navigator) {
		addComponent(buttonToMainMenu);
		initLayout();
		initSelectSimulator();
		initSimulatorsInfo();
		this.navigator = navigator;
		setComponentAlignment(buttonToMainMenu, Alignment.MIDDLE_CENTER);
		addClickListeners();
	}

	private void initSimulatorsInfo() {
		simulatorInfo = new SimulatorInfo(this);
	}

	private void initLayout() {
		addComponent(selectSimulator);
		editorLayout = new FormLayout();
		editorLayout.setVisible(true);
		addComponent(editorLayout);
	}

	private void initSelectSimulator() {
		selectSimulator.setImmediate(true);
		Collection<?> itemIds = simulatorContainer.getItemIds();
		for (Object itemId : itemIds) {
			Property simulatorName = simulatorContainer.getItem(itemId)
					.getItemProperty("SimulatorName");
			selectSimulator.addItem(simulatorName.getValue());
			simulatorsIdNamesMapping.put((String) simulatorName.getValue(),
					(RowId) itemId);
		}

		selectSimulator.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = (String) selectSimulator.getValue();
				RowId rowId = simulatorsIdNamesMapping.get(value);
				Item selectedItem = simulatorContainer.getItem(rowId);
				RunningSimulationsView.this.getSimulatorInfo()
						.setItemDataSource(selectedItem);
				RunningSimulationsView.this.getSimulatorInfo()
						.setReadOnly(true);
			}
		});
	}

	private void addClickListeners() {
		buttonToMainMenu.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4243499910765394003L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("");
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
