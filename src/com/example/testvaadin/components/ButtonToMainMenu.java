package com.example.testvaadin.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

public class ButtonToMainMenu extends Button {
	private static final long serialVersionUID = 2995926761164874245L;

	public ButtonToMainMenu(final Navigator navigator) {
		super("Go to start page");
		this.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4243499910765394003L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("");
				// NavigatorUI.goToMainMenu();
			}
		});

	}
}
