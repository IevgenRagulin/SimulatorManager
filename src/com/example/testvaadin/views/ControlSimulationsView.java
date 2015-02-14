package com.example.testvaadin.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;

public class ControlSimulationsView extends BasicView implements View {
	final static Logger logger = LoggerFactory.getLogger(ControlSimulationsView.class);

	private static final long serialVersionUID = -1499211740985566852L;
	private Button buttonToMainMenu;
	private Navigator navigator;

	public ControlSimulationsView(Navigator navigator) {
		logger.info("new ControlSimulationsView()");
		this.setNavigator(navigator);
		buttonToMainMenu = new ButtonToMainMenu(navigator);
		addComponent(buttonToMainMenu);
		setComponentAlignment(buttonToMainMenu, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}
