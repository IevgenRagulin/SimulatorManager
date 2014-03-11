package com.example.testvaadin.beans;

public class SimulationBean {
	private String simulationid;
	private String simulator_simulatorid;
	private boolean issimulationon;
	private boolean issimulationpaused;
	private String simulationstartedtime;
	private String simulationendedtime;

	public SimulationBean(String simulationid, String simulator_simulatorid,
			boolean issimulationon, boolean issimulationpaused,
			String simulationstartedtime, String simulationendedtime) {
		super();
		this.simulationid = simulationid;
		this.simulator_simulatorid = simulator_simulatorid;
		this.issimulationon = issimulationon;
		this.issimulationpaused = issimulationpaused;
		this.simulationstartedtime = simulationstartedtime;
		this.simulationendedtime = simulationendedtime;
	}

	public String getSimulationid() {
		return simulationid;
	}

	public void setSimulationid(String simulationid) {
		this.simulationid = simulationid;
	}

	public String getSimulator_simulatorid() {
		return simulator_simulatorid;
	}

	public void setSimulator_simulatorid(String simulator_simulatorid) {
		this.simulator_simulatorid = simulator_simulatorid;
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
