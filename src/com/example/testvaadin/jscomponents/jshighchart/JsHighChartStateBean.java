package com.example.testvaadin.jscomponents.jshighchart;

import java.io.Serializable;

public class JsHighChartStateBean implements Serializable {
	private static final long serialVersionUID = 5032000954553816757L;

	private String data = new String();
	private String cssid;
	private int n = -100;// new point value. -100 means not initialized
	// clickedID. Id of the primary flight display in
	// database corresponding to point on graph where
	// user has clicked
	private int clId = -100;
	// timestamp. Timestamp of the point corresponding to clicked id
	public long ts = -100;

	public JsHighChartStateBean(String cssid) {
		super();
		this.cssid = cssid;
	}

	protected long getTimestamp() {
		return this.ts;
	}

	protected void setTimestamp(long timestamp) {
		this.ts = timestamp;
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

	public int getClId() {
		return clId;
	}

	public void setClId(int clId) {
		this.clId = clId;
	}

}
