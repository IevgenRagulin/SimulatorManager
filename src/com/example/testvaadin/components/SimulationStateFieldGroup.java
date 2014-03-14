package com.example.testvaadin.components;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public class SimulationStateFieldGroup extends FieldGroup {
	private static final long serialVersionUID = -1726362933655786619L;

	public SimulationStateFieldGroup(String[] fieldNames, FormLayout formLayout) {
		initSimulatorInfo(fieldNames, formLayout);
	}

	private void initSimulatorInfo(String[] fieldNames, FormLayout formLayout) {
		setBuffered(false);

		for (String fieldName : fieldNames) {
			TextField field = createInputField(fieldName);
			formLayout.addComponent(field);
			this.bind(field, fieldName);
		}
	}

	private TextField createInputField(String fieldName) {
		TextField field = new TextField(fieldName);
		field.setImmediate(true);
		field.setWidth("30%");
		field.setNullRepresentation("");
		return field;
	}
}
