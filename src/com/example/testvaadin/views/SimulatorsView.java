package com.example.testvaadin.views;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.example.testvaadin.components.SimulatorForm;
import com.example.testvaadin.components.SimulatorListSimulatorsView;
import com.example.testvaadin.data.DatabaseHelper;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("testvaadin")
public class SimulatorsView extends HorizontalSplitPanel implements View {

	private SimulatorListSimulatorsView simulatorList;
	private DatabaseHelper dbHelp = new DatabaseHelper();

	private FormLayout editorLayout = new FormLayout();
	private SimulatorForm simulatorForm;
	private Button removeSimulatorButton = new Button("Remove simulator");
	private Button addSimulatorButton = new Button("Add simulator");
	private ButtonToMainMenu buttonToMainMenu;
	private Navigator navigator;

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
		initEditor();
		addClickListeners();
	}

	private void initSimulatorList() {
		simulatorList = new SimulatorListSimulatorsView(this);
	}

	private void initLayout() {
		buttonToMainMenu = new ButtonToMainMenu(navigator);
		VerticalLayout leftLayout = new VerticalLayout();
		editorLayout = new FormLayout();
		addComponent(leftLayout);
		addComponent(editorLayout);

		leftLayout.addComponent(buttonToMainMenu);
		leftLayout.addComponent(simulatorList);
		leftLayout.addComponent(addSimulatorButton);

		leftLayout.setSizeFull();
		leftLayout.setExpandRatio(simulatorList, 1);
		editorLayout.setVisible(false);
	}

	private void initEditor() {
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
				simulatorForm.removeSimulator();
				// dbHelp.updateSimulatorContainer();
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}