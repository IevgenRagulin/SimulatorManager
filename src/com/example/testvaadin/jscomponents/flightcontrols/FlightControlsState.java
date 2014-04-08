package com.example.testvaadin.jscomponents.flightcontrols;

import com.vaadin.shared.ui.JavaScriptComponentState;

@SuppressWarnings("serial")
public class FlightControlsState extends JavaScriptComponentState {
	// the variable names are so short so that we pass less data through the
	// network
	public float ail; // aileron
	public float el; // elevator
	public float rd; // rudder
	public float sb; // speed brakes
	public float fl; // flaps
	public float ailt; // aileron trim
	public float elt; // elevator trim
	public float rdt; // rudder trim
	public int maxonflaps; // max speed on flaps

}
