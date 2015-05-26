package cz.vutbr.fit.simulatormanager.components;

import com.vaadin.ui.Button;

public class ButtonConfigurationView extends Button {
	private static final long serialVersionUID = 6134159481838518249L;
	private static final String CSS_STYLE_NAME = "v-button btnDtbManagement";

	public ButtonConfigurationView(String name) {
		super(name);
		setPrimaryStyleName(CSS_STYLE_NAME);
	}
}
