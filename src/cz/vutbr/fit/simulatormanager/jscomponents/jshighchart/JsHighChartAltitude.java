package cz.vutbr.fit.simulatormanager.jscomponents.jshighchart;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.JavaScript;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

import cz.vutbr.fit.simulatormanager.database.columns.SimulationPfdInfoCols;
import cz.vutbr.fit.simulatormanager.items.SimulationPFDItem;
import cz.vutbr.fit.simulatormanager.views.SimulationsView;

@JavaScript({ "js_highchart_altitude.js" })
public class JsHighChartAltitude extends JsHighChart {
    private static final long serialVersionUID = 8808324160034287613L;
    private final static Logger LOG = LoggerFactory.getLogger(JsHighChartAltitude.class);

    public JsHighChartAltitude(SimulationsView view, String cssid) {
	super(view, cssid);
    }

    @Override
    public void initChartWithDataForSimulatorWithId(String simulatorId, int newAltitude) {
	SQLContainer simulationPfdData = view.getDBHelp().getLatestPFDInfoBySimulatorId(simulatorId);
	LOG.info("initChartWithDataForSimulatorWithId() - simulatorId: {}, going to add: {} points to the chart", simulatorId,
		simulationPfdData.size());
	if (simulationPfdData.size() > 0) {
	    addOldDataToChart(simulationPfdData, SimulationPfdInfoCols.altitude.toString());
	} else {
	    String dataToSet = "[[" + (new Date().getTime()) + "," + newAltitude + "]]";
	    this.getState().data = dataToSet;
	    this.getStateBean().setData(dataToSet);
	}
    }

    public void addNewPoint(String simulatorId, SimulationPFDItem simulationPfdItem) {
	int newAltitude = simulationPfdItem.getBean().getAltitude().intValue();
	super.addNewPoint(simulatorId, newAltitude);
	markAsDirty();
    }

    @Override
    public void initChartWithDataForSimulationWithId(String simulationId) {
	SQLContainer simulationPfdData = view.getDBHelp().getPFDInfoBySimulationId(simulationId);
	addOldDataToChart(simulationPfdData, SimulationPfdInfoCols.altitude.toString());
    }

}
