package com.example.testvaadin.jscomponents.jshighchart;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class JsHighChartState extends JavaScriptComponentState {
	private static final long serialVersionUID = -1016346299843904188L;
	public String data;
	public String title;
	public String units;
	public String cssid;
	// new point value. -100 means not initialized
	public int n = -100;
	// clickedID. Id of the primary flight display in
	// database corresponding to point on graph where
	// user has clicked
	public int clId = -100;
}