package com.example.testvaadin.javascriptcomponents;

import com.vaadin.shared.ui.JavaScriptComponentState;

@SuppressWarnings("serial")
public class PrimaryFlightDisplayState extends JavaScriptComponentState {
	public String xhtml;
	public int altitude;
	public int speed;
	public int roll;
	public int pitch;
	public int yaw;

	public int getPitch() {
		return pitch;
	}
}
