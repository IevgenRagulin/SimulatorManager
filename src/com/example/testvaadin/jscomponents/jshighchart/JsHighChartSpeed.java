package com.example.testvaadin.jscomponents.jshighchart;

import java.util.Date;

import com.vaadin.annotations.JavaScript;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

import cz.vutbr.fit.simulatormanager.data.SimulationPfdInfoCols;
import cz.vutbr.fit.simulatormanager.items.SimulationPFDItem;
import cz.vutbr.fit.simulatormanager.views.SimulationsView;

@JavaScript({ "js_highchart_speed.js" })
public class JsHighChartSpeed extends JsHighChart {
	private static final long serialVersionUID = -5499980900026438314L;

	public JsHighChartSpeed(SimulationsView view, String title, String units, String cssid) {
		super(view, cssid);
	}

	@Override
	public void initChartWithDataForSimulatorWithId(String simulatorId, int newSpeed) {
		SQLContainer simulationPfdData = view.getDBHelp().getLatestPFDInfoBySimulatorId(simulatorId);
		if (simulationPfdData.size() > 0) {
			addOldDataToChart(simulationPfdData, SimulationPfdInfoCols.ias.toString());
		} else {
			this.getState().data = "[[" + (new Date().getTime()) + "," + newSpeed + "]]";
		}
	}

	public void addNewPoint(String simulatorId, SimulationPFDItem simulationPfdItem) {
		int newSpeed = simulationPfdItem.getBean().getIas().intValue();
		super.addNewPoint(simulatorId, newSpeed);
		markAsDirty();
	}

	@Override
	public void initChartWithDataForSimulationWithId(String simulationId) {
		SQLContainer simulationPfdData = view.getDBHelp().getPFDInfoBySimulationId(simulationId);
		addOldDataToChart(simulationPfdData, SimulationPfdInfoCols.ias.toString());
	}
}
