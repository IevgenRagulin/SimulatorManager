package com.example.testvaadin;

import com.example.testvaadin.components.ButtonToMainMenu;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;

public class ControlSimulationsView extends BasicView implements View {

	private static final long serialVersionUID = -1499211740985566852L;
	private Button buttonToMainMenu;
	private Navigator navigator;

	public ControlSimulationsView(Navigator navigator) {
		this.navigator = navigator;
		buttonToMainMenu = new ButtonToMainMenu(navigator);
		addComponent(buttonToMainMenu);
		setComponentAlignment(buttonToMainMenu, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
