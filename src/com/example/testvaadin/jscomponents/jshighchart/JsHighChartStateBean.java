package com.example.testvaadin.jscomponents.jshighchart;

public class JsHighChartStateBean {
	private String data = new String();
	private String cssid;
	private int n;// new point value

	public JsHighChartStateBean(String cssid) {
		super();
		this.cssid = cssid;
	}

	protected String getData() {
		return data;
	}

	protected void setData(String data) {
		this.data = data;
	}

	protected int getN() {
		return n;
	}

	protected void setN(int n) {
		this.n = n;
	}

	public String getCssid() {
		return cssid;
	}

	public void setCssid(String cssid) {
		this.cssid = cssid;
	}

}
