package com.example.testvaadin.beans;

import com.example.testvaadin.data.ColumnNames;
import com.vaadin.data.Item;

public class SimulationBean {

	private boolean issimulationon;
	private boolean issimulationpaused;
	private String simulationstartedtime;
	private String simulationendedtime;

	public SimulationBean(boolean issimulationon, boolean issimulationpaused,
			String simulationstartedtime, String simulationendedtime) {
		super();
		this.issimulationon = issimulationon;
		this.issimulationpaused = issimulationpaused;
		this.simulationstartedtime = simulationstartedtime;
		this.simulationendedtime = simulationendedtime;
	}

	public SimulationBean(Item item) {
		if (item != null) {
			this.issimulationon = (Boolean) item.getItemProperty(
					ColumnNames.getIssimulationon()).getValue();
			this.issimulationpaused = (Boolean) item.getItemProperty(
					ColumnNames.getIssimulationpaused()).getValue();
			this.simulationstartedtime = (String) item.getItemProperty(
					ColumnNames.getSimulationstartedtime()).getValue();
			this.simulationendedtime = (String) item.getItemProperty(
					ColumnNames.getSimulationendedtime()).getValue();
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

	public String getSimulationstartedtime() {
		return simulationstartedtime;
	}

	public void setSimulationstartedtime(String simulationstartedtime) {
		this.simulationstartedtime = simulationstartedtime;
	}

	public String getSimulationendedtime() {
		return simulationendedtime;
	}

	public void setSimulationendedtime(String simulationendedtime) {
		this.simulationendedtime = simulationendedtime;
	}

}
