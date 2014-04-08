package com.example.testvaadin.jscomponents.jshighchart;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({
		"http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js",
		"highstock.js", "js_highchart.js" })
public class JsHighChart extends AbstractJavaScriptComponent {
	private static final long serialVersionUID = -9172268881960130470L;

	public JsHighChart() {
		getState().data = data;
		getState().title = "MyChart";
		getState().units = "MyUnits";
	}

	@Override
	public JsHighChartState getState() {
		return (JsHighChartState) super.getState();
	}

	String data = "[[1176163200000, 94], [1176163201000, 92], [1176163202000, 92], [1176163203000, 90], [1176163204000, 91]]";
}
