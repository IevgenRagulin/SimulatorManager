package com.example.testvaadin.components;

import com.vaadin.ui.Label;

public class ErrorLabel extends Label {
	private static final long serialVersionUID = 1034516261622173097L;
	private static final String ERROR_LABEL_CLASS_NAME = "runningSimulationsErrorLabel";

	public ErrorLabel(String caption) {
		super(caption);
		setStyleName(ERROR_LABEL_CLASS_NAME);
	}

}
