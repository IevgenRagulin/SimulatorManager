package com.example.testvaadin.views;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.SimulatorForm;
import com.example.testvaadin.components.SimulatorListSimulatorsView;
import com.example.testvaadin.data.DatabaseHelper;
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
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class SimulatorsView extends HorizontalSplitPanel implements View {

	private SimulatorListSimulatorsView simulatorList;
	private DatabaseHelper dbHelp = new DatabaseHelper();

	private FormLayout editorLayout = new FormLayout();
	private SimulatorForm simulatorForm;
	private Button removeSimulatorButton = new Button("Remove simulator");
	private Button addSimulatorButton = new Button("Add simulator");
	private ButtonToMainMenu buttonToMainMenu;
	private Navigator navigator;
	private Label selectedSimulatorName;

	public Label getSelectedSimulatorName() {
		return selectedSimulatorName;
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

	public SimulatorListSimulatorsView getSimulatorList() {
		return simulatorList;
	}

	public DatabaseHelper getDBHelp() {
		return dbHelp;
	}

	public SimulatorsView(Navigator navigator) {
		this.navigator = navigator;
		initSimulatorList();
		initLayout();
		initSimulatorForm();
		addClickListeners();
	}

	private void initSimulatorList() {
		simulatorList = new SimulatorListSimulatorsView(this);
	}

	private void initLayout() {
		initLeftLayout();
		initRightLayout();
	}

	private void initLeftLayout() {
		buttonToMainMenu = new ButtonToMainMenu(navigator);
		VerticalLayout leftLayout = new VerticalLayout();
		addComponent(leftLayout);
		leftLayout.setMargin(new MarginInfo(true, false, true, true));
		leftLayout.addComponent(buttonToMainMenu);
		leftLayout.addComponent(simulatorList);
		leftLayout.addComponent(addSimulatorButton);
		leftLayout.setSizeFull();
		leftLayout.setExpandRatio(simulatorList, 1);
	}

	private void initRightLayout() {
		VerticalLayout rightLayout = new VerticalLayout();
		addComponent(rightLayout);
		rightLayout.setMargin(new MarginInfo(true, false, true, true));
		selectedSimulatorName = new Label("", ContentMode.HTML);
		rightLayout.addComponent(selectedSimulatorName);
		editorLayout = new FormLayout();
		rightLayout.addComponent(editorLayout);
		editorLayout.setVisible(false);
	}

	private void initSimulatorForm() {
		simulatorForm = new SimulatorForm(this);
	}

	private void addClickListeners() {
		initAddButton();
		initRemoveButton();
	}

	private void initAddButton() {
		addSimulatorButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				simulatorForm.addSimulator();
				// dbHelp.updateSimulatorContainer();
			}
		});
	}

	private void initRemoveButton() {
		removeSimulatorButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				selectedSimulatorName.setVisible(false);
				simulatorForm.removeSimulator();
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}