package com.example.testvaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class RunningSimulationsView extends VerticalLayout implements View {

	private static final long serialVersionUID = -1785707193097941934L;
	private Navigator navigator;
	private Button buttonToMainMenu = new Button("Go to start page");

	public RunningSimulationsView(Navigator navigator) {
		this.navigator = navigator;
		addComponent(buttonToMainMenu);
		setComponentAlignment(buttonToMainMenu, Alignment.MIDDLE_CENTER);
		addClickListeners();
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

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
