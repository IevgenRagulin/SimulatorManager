package com.example.testvaadin.jscomponents.jshighchart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.views.SimulationsView;
import com.vaadin.annotations.JavaScript;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

@JavaScript({
		"http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js",
		"highstock.js" })
public abstract class JsHighChart extends AbstractJavaScriptComponent {
	private static final long serialVersionUID = -9172268881960130470L;
	protected boolean isChartInitializedWithMapHistory = false;
	protected SimulationsView view = null;
	protected JsHighChartStateBean stateBean = null;
	ArrayList<ValueChangeListener> listeners = new ArrayList<ValueChangeListener>();

	public interface ValueChangeListener extends Serializable {
		void valueChange();
	}

	public void addValueChangeListener(ValueChangeListener listener) {
		listeners.add(listener);
	}

	public void setClickedId(int value) {
		getState().clId = value;
		getStateBean().setClId(value);
	}

	public int getClickedId() {
		return getStateBean().getClId();
	}

	public long getTimestamp() {
		return getStateBean().getTimestamp();
	}

	public JsHighChart(SimulationsView view, String cssid) {
		this.view = view;
		getState().cssid = cssid;
		stateBean = new JsHighChartStateBean(cssid);
		// Register function for RPC

		addFunction("onClick", new JavaScriptFunction() {
			private static final long serialVersionUID = 7739429366458948661L;

			@Override
			public void call(JSONArray arguments) throws JSONException {
				try {
					getState().clId = arguments.getInt(0);
					getStateBean().setClId(arguments.getInt(0));
					getState().ts = arguments.getLong(1);
					getStateBean().setTimestamp(arguments.getLong(1));
					for (ValueChangeListener listener : listeners)
						listener.valueChange();
				} catch (Exception e) {
					System.err
							.println("Exception happened in "
									+ JsHighChart.class
									+ " while getting arguments from RPC call after clicking on a graph");
				}
			}
		});

	}

	public void resetChart() {
		isChartInitializedWithMapHistory = false;
	}

	public void addNewPoint(String simulatorId, int newValue) {
		if (!isChartInitializedWithMapHistory) {
			initChartWithDataForSimulatorWithId(simulatorId, newValue);
			isChartInitializedWithMapHistory = true;
		} else if (!this.getStateBean().getData().equals("")) {
			// We make data variable empty so that it's not send from server to
			// client together with shared state.
			// If we don't make it empty, server sends to much data and
			// performance gets worse
			getState().data = "";
			this.getStateBean().setData("");
		}
		setNewValue(newValue);
	}

	public abstract void initChartWithDataForSimulatorWithId(
			String simulatorId, int newValue);

	public abstract void initChartWithDataForSimulationWithId(
			String simulationId);

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
			int pfdId = (Integer) pfdItem.getItemProperty(
					ColumnNames.getPfdinfoid()).getValue();
			oldValues.append("{\"x\": ");
			oldValues.append(timestampMilis);
			oldValues.append(", ");
			oldValues.append("\"y\": ");
			oldValues.append(value);
			oldValues.append(", ");
			oldValues.append("\"id\": ");
			oldValues.append(pfdId);
			if (counter < simulationPfdIds.size()) {
				oldValues.append("}, ");
			} else {
				oldValues.append("}");
			}
		}
		oldValues.append("]");
		System.out.println(oldValues);
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
			getStateBean().setN(newValue);
			this.getState().n = newValue;
		}
	}

}
