package com.example.testvaadin.jscomponents.jshighchart;

import java.util.Date;

import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.items.SimulationPFDItem;
import com.example.testvaadin.views.RunningSimulationsView;
import com.vaadin.annotations.JavaScript;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

@JavaScript({ "js_highchart_altitude.js" })
public class JsHighChartAltitude extends JsHighChart {
	private static final long serialVersionUID = 8808324160034287613L;

	public JsHighChartAltitude(RunningSimulationsView view, String cssid) {
		super(view, cssid);
	}

	@Override
	public void initChartWithDataForSimulatorWithId(String simulatorId,
			int newAltitude) {
		SQLContainer simulationPfdData = view.getDBHelp()
				.getAllPFDInfoBySimulatorId(simulatorId);
		if (simulationPfdData.size() > 0) {
			addOldDataToChart(simulationPfdData, ColumnNames.getAltitude());
		} else {
			String dataToSet = "[[" + (new Date().getTime()) + ","
					+ newAltitude + "]]";
			this.getState().data = dataToSet;
			this.getStateBean().setData(dataToSet);
		}
	}

	public void addNewPoint(String simulatorId,
			SimulationPFDItem simulationPfdItem) {
		int newAltitude = simulationPfdItem.getBean().getAltitude().intValue();
		super.addNewPoint(simulatorId, newAltitude);
	}

}
