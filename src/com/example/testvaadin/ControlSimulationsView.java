package com.example.testvaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ControlSimulationsView extends BasicView implements View {

	private static final long serialVersionUID = -1499211740985566852L;
	private Button buttonToMainMenu = new Button("Go to start page");
	private Navigator navigator;

	public ControlSimulationsView(Navigator navigator) {
		this.navigator = navigator;
		addComponent(buttonToMainMenu);
		setComponentAlignment(buttonToMainMenu, Alignment.MIDDLE_CENTER);
		addClickListeners();
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	private void addClickListeners() {
		buttonToMainMenu.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4243499910765394003L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("");
			}
		});
	}

}
