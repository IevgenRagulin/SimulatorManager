package com.example.testvaadin.views;

import com.example.testvaadin.NavigatorUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class StartView extends VerticalLayout implements View {

	private static final long serialVersionUID = 7495049073810117367L;
	private Button buttonToManager = new Button("Go to simulator manager");
	Button buttonToViewingSimulations = new Button("Go to viewing simulations");
	Button buttonToControllingSimulations = new Button(
			"Go to controlling simulations");
	private Navigator navigator;

	public StartView(final Navigator navigator) {
		this.navigator = navigator;
		setSizeFull();
		addComponent(buttonToManager);
		addComponent(buttonToViewingSimulations);
		addComponent(buttonToControllingSimulations);
		addClickListeners();
		setAllignments();
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	private void addClickListeners() {
		addButtonToManagerListener();
		addButtonToViewingSimulationsListener();
		addButtonToControllingSimulationsListner();
	}

	private void addButtonToControllingSimulationsListner() {
		buttonToControllingSimulations.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4243499910765394003L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(NavigatorUI.CONTROLSIMULATIONS);
			}
		});
	}

	private void setAllignments() {
		setComponentAlignment(buttonToManager, Alignment.MIDDLE_CENTER);
		setComponentAlignment(buttonToViewingSimulations,
				Alignment.MIDDLE_CENTER);
		setComponentAlignment(buttonToControllingSimulations,
				Alignment.MIDDLE_CENTER);
	}

	private void addButtonToViewingSimulationsListener() {
		buttonToViewingSimulations.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4243499910765394003L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(NavigatorUI.RUNNINGSIMULATIONS);
			}
		});
	}

	private void addButtonToManagerListener() {
		buttonToManager.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4243499910765394003L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(NavigatorUI.MANAGESIMULATORS);
			}
		});
	}

}
