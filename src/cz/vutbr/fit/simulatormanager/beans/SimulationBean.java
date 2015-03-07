package cz.vutbr.fit.simulatormanager.beans;

import java.io.Serializable;
import java.util.Date;

import com.vaadin.data.Item;

import cz.vutbr.fit.simulatormanager.database.columns.SimulationCols;

public class SimulationBean implements Serializable {
	private static final long serialVersionUID = 3314547295194561766L;
	private boolean issimulationon;
	private boolean issimulationpaused;
	private Date simulationstartedtime;
	private Date simulationendedtime;

	public SimulationBean(boolean issimulationon, boolean issimulationpaused, Date simulationstartedtime, Date simulationendedtime) {
		super();
		this.issimulationon = issimulationon;
		this.issimulationpaused = issimulationpaused;
		this.simulationstartedtime = simulationstartedtime;
		this.simulationendedtime = simulationendedtime;
	}

	public SimulationBean(Item item) {
		if (item != null) {
			this.issimulationon = (Boolean) item.getItemProperty(SimulationCols.issimulationon.toString()).getValue();
			this.issimulationpaused = (Boolean) item.getItemProperty(SimulationCols.issimulationpaused.toString()).getValue();
			this.simulationstartedtime = (Date) item.getItemProperty(SimulationCols.simulationstartedtime.toString()).getValue();
			this.simulationendedtime = (Date) item.getItemProperty(SimulationCols.simulationendedtime.toString()).getValue();
		}
	}

	public boolean isIssimulationon() {
		return issimulationon;
	}

	public void setIssimulationon(boolean issimulationon) {
		this.issimulationon = issimulationon;
	}

	public boolean isIssimulationpaused() {
		return issimulationpaused;
	}

	public void setIssimulationpaused(boolean issimulationpaused) {
		this.issimulationpaused = issimulationpaused;
	}

	public Date getSimulationstartedtime() {
		return simulationstartedtime;
	}

	public void setSimulationstartedtime(Date simulationstartedtime) {
		this.simulationstartedtime = simulationstartedtime;
	}

	public Date getSimulationendedtime() {
		return simulationendedtime;
	}

	public void setSimulationendedtime(Date simulationendedtime) {
		this.simulationendedtime = simulationendedtime;
	}

}
