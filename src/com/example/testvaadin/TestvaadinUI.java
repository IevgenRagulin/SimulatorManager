package com.example.testvaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
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
public class TestvaadinUI extends HorizontalSplitPanel implements View {

	// @WebServlet(value = "/*", asyncSupported = true)
	// @VaadinServletConfiguration(productionMode = false, ui =
	// TestvaadinUI.class)
	// public static class Servlet extends VaadinServlet {

	// }

	private static final String SIMNAME = "SimulatorName";
	private static final String PLANESIM = "AircraftModel";
	private static final String SIMID = "SimulatorId";
	private DatabaseHelper dbHelp = new DatabaseHelper();
	private SimulatorList simulatorList;

	private SQLContainer simulatorContainer = dbHelp.getSimulatorContainer();//

	private static final String[] columnNames = new String[] { SIMNAME,
			PLANESIM, SIMID, "MinSpeed", "MaxSpeed", "HighSpeed",
			"MaxSpeedOnFlaps", "MinSpeedOnFlaps", "HasGears", "MinTempCHT1",
			"MinTempCHT2", "MinTempEGT1", "MinTempEGT2", "MaxTempCHT1",
			"MaxTempCHT2", "MaxTempEGT1", "MaxTempEGT2", "ManifoldPressure",
			"Power", "MaxAmountOfFuel", "MinAmountOfFuel", "MaxRPM",
			"NumberOfEngines" };

	private FormLayout editorLayout = new FormLayout();
	private SimulatorForm editorFields;
	private Button removeSimulatorButton = new Button("Remove simulator");
	private Button addSimulatorButton = new Button("Add simulator");
	private Button buttonToMainMenu = new Button("Go to start page");
	private Navigator navigator;

	public String getSimNamePropertyName() {
		return SIMNAME;
	}

	public String[] getVisibleColumns() {
		return new String[] { SIMID, SIMNAME, PLANESIM };
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public FormLayout getEditorLayout() {
		return editorLayout;
	}

	public Button getRemoveSimulatorButton() {
		return removeSimulatorButton;
	}

	public SimulatorForm getEditorFields() {
		return editorFields;
	}

	public SimulatorList getSimulatorList() {
		return simulatorList;
	}

	/*
	 * @Override protected void init(VaadinRequest request) {
	 * 
	 * initSimulatorList(); initLayout(); initEditor(); initAddRemoveButtons();
	 * }
	 */

	public DatabaseHelper getDBHelp() {
		return dbHelp;
	}

	public TestvaadinUI(Navigator navigator) {
		this.navigator = navigator;
		initSimulatorList();
		initLayout();
		initEditor();
		addClickListeners();

	}

	private void initSimulatorList() {
		simulatorList = new SimulatorList(this);
	}

	private void initLayout() {
		// HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		// setContent(splitPanel);

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
		editorFields = new SimulatorForm(this);
	}

	private void addClickListeners() {
		initAddButton();
		initRemoveButton();
		initGoToMainMenuButton();
	}

	private void initGoToMainMenuButton() {
		buttonToMainMenu.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4243499910765394003L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("");
			}
		});
	}

	private void initAddButton() {
		addSimulatorButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				editorFields.addSimulator();
			}
		});
	}

	private void initRemoveButton() {
		removeSimulatorButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				editorFields.removeSimulator();
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}