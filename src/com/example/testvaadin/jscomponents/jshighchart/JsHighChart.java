package com.example.testvaadin.jscomponents.jshighchart;

import java.util.Collection;

import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.views.RunningSimulationsView;
import com.vaadin.annotations.JavaScript;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({
		"http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js",
		"highstock.js" })
public abstract class JsHighChart extends AbstractJavaScriptComponent {
	private static final long serialVersionUID = -9172268881960130470L;
	protected boolean isChartInitializedWithMapHistory = false;
	protected RunningSimulationsView view = null;
	protected JsHighChartStateBean stateBean = null;

	public JsHighChart(RunningSimulationsView view, String cssid) {
		this.view = view;
		getState().cssid = cssid;
		stateBean = new JsHighChartStateBean(cssid);
	}

	public void addNewPoint(String simulatorId, int newValue) {
		if (!isChartInitializedWithMapHistory) {
			System.out.println("INIT DATA");
			initChartWithDataForSimulatorWithId(simulatorId, newValue);
			isChartInitializedWithMapHistory = true;
		} else if (!this.getStateBean().getData().equals("[0,0]")) {
			// We make data variable empty so that it's not send from server to
			// client together with shared state.
			// If we don't make it empty, server sends to much data and
			// performance gets worse
			System.out.println("going to change state");
			getState().data = "[0,0]";
			this.getStateBean().setData("[0,0]");
		}
		setNewValue(newValue);
	}

	public abstract void initChartWithDataForSimulatorWithId(
			String simulatorId, int newValue);

	protected void addOldDataToChart(SQLContainer simulationPfdData,
			String itemPropertyName) {
		StringBuffer oldValues = new StringBuffer();
		oldValues.append("[");
		Collection<?> simulationPfdIds = simulationPfdData.getItemIds();
		int counter = 0;
		for (Object id : simulationPfdIds) {
			counter++;
			RowId rowId = (RowId) id;
			Item pfdItem = simulationPfdData.getItem(rowId);
			int value = ((Double) pfdItem.getItemProperty(itemPropertyName)
					.getValue()).intValue();
			long timestampMilis = ((java.sql.Timestamp) pfdItem
					.getItemProperty(ColumnNames.getTimestamp()).getValue())
					.getTime();
			oldValues.append("[");
			oldValues.append(timestampMilis);
			oldValues.append(",");
			oldValues.append(value);
			if (counter < simulationPfdIds.size()) {
				oldValues.append("],");
			} else {
				oldValues.append("]");
			}
		}
		oldValues.append("]");
		this.getState().data = oldValues.toString();
	}

	@Override
	public JsHighChartState getState() {
		return (JsHighChartState) super.getState();
	}

	public JsHighChartStateBean getStateBean() {
		return this.stateBean;
	}

	protected void setNewValue(int newValue) {
		if (getStateBean().getN() != newValue) {
			System.out.println("going to set new value, state changed");
			getStateBean().setN(newValue);
			this.getState().n = newValue;
		}
	}

}
