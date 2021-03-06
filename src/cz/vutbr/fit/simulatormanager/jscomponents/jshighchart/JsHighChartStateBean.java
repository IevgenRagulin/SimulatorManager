package cz.vutbr.fit.simulatormanager.jscomponents.jshighchart;

import java.io.Serializable;

/**
 * This class is used by classes JsHighChartAltitude and JsHighChartSpeed, so
 * that they access data in class JsHighChartState less frequently, as accessing
 * that class results Vaadin in sending data to UI, and sometimes we don't want
 * it
 * 
 * @author zhenia
 *
 */
public class JsHighChartStateBean implements Serializable {
    private static final long serialVersionUID = 5032000954553816757L;
    // data contains info about previous speeds, altitudes, used for
    // initializing the graphs
    private String data = new String();
    private String cssid;
    // new point value. -100 means not initialized
    private int n = -100;
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
