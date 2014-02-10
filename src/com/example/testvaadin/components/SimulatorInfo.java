package com.example.testvaadin.components;

import com.example.testvaadin.RunningSimulationsView;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.TextField;

public class SimulatorInfo extends FieldGroup {
	private static final long serialVersionUID = -1726362933655786619L;
	private RunningSimulationsView runningSimulations;

	public SimulatorInfo(RunningSimulationsView runningSimulations) {
		this.runningSimulations = runningSimulations;
		initSimulatorInfo();
	}

	private void initSimulatorInfo() {
		setBuffered(false);
		for (String fieldName : runningSimulations.getVisibleColumns()) {
			TextField field = createInputField(fieldName);
			runningSimulations.getEditorLayout().addComponent(field);
			this.bind(field, fieldName);
			System.out.println("generating form" + fieldName);
		}
	}

	private TextField createInputField(String fieldName) {
		TextField field = new TextField(fieldName);
		field.setImmediate(true);
		field.setWidth("90%");
		field.setNullRepresentation("");
		return field;
	}
}
