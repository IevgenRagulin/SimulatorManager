package com.example.testvaadin.components;

import com.vaadin.ui.Label;

public class InfoLabel extends Label {
	private static final long serialVersionUID = 1034516261622173097L;
	private static final String INFO_LABEL_CLASS_NAME = "runningSimulationsInfoLabel";

	public InfoLabel(String caption) {
		super(caption);
		setStyleName(INFO_LABEL_CLASS_NAME);
	}
}
