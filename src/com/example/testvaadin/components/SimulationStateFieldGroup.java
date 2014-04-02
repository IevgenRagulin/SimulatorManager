package com.example.testvaadin.components;

import java.util.Collection;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class SimulationStateFieldGroup extends FieldGroup {
	private static final long serialVersionUID = -1726362933655786619L;

	public SimulationStateFieldGroup(String[] fieldNames,
			HorizontalLayout horizontalLayout) {
		initSimulatorInfo(fieldNames, horizontalLayout);
	}

	private void initSimulatorInfo(String[] fieldNames,
			HorizontalLayout horizontalLayout) {
		setBuffered(false);

		for (String fieldName : fieldNames) {
			TextField field = createInputField(fieldName + ": ");
			field.setId(fieldName);
			horizontalLayout.addComponent(field);
			this.bind(field, fieldName);
		}
	}

	/*
	 * 
	 * Returns true if all of the current fields values are equal to values in
	 * Item. False - otherwise.
	 */
	public boolean equalsItem(Item simStateCompare) {
		try {
			Collection<Field<?>> thisFields = this.getFields();
			for (Field<?> field : thisFields) {
				String id = field.getId();
				String value = field.getValue().toString();
				Property prop = simStateCompare.getItemProperty(id);
				String compareVal = prop.getValue().toString();
				if (!compareVal.equals(value)) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private TextField createInputField(String fieldName) {
		TextField field = new TextField(fieldName);
		field.setImmediate(true);
		field.setWidth("30%");
		field.setNullRepresentation("");
		return field;
	}
}
