package com.example.testvaadin.simulatorcommunication;

import java.util.HashMap;
import java.util.Map;

import com.example.testvaadin.items.SimulationDevStateItem;
import com.example.testvaadin.items.SimulationInfoItem;
import com.example.testvaadin.items.SimulationItem;
import com.example.testvaadin.items.SimulationPFDItem;

public class SimulatorsStatus {
	protected static final boolean SIMULATION_ON = true;
	protected static final boolean SIMULATION_OFF = false;
	protected static final boolean SIMULATION_PAUSED = true;
	protected static final boolean SIMULATION_NOT_PAUSED = false;

	// SimulationInfoItem based on current-1 data from the simulator. Used to
	// determine if the simulator has moved or not
	protected static Map<String, SimulationInfoItem> prevSimulatorIdSimInfoItem = new HashMap<String, SimulationInfoItem>();

	// Each item corresponds to a table in a database
	protected static Map<String, SimulationItem> simulatorIdSimItem = new HashMap<String, SimulationItem>();
	protected static Map<String, SimulationDevStateItem> simulatorIdSimDevStateItem = new HashMap<String, SimulationDevStateItem>();
	protected static Map<String, SimulationInfoItem> simulatorIdSimInfoItem = new HashMap<String, SimulationInfoItem>();
	protected static Map<String, SimulationPFDItem> simulatorIdSimPFDItem = new HashMap<String, SimulationPFDItem>();

	protected static void setSimulationDevStateItem(String simulatorId,
			SimulationDevStateItem simDevStateItem) {
		simulatorIdSimDevStateItem.put(simulatorId, simDevStateItem);
	}

	protected static void setSimulationItem(String simulatorId,
			SimulationItem simItem) {
		simulatorIdSimItem.put(simulatorId, simItem);
	}

	protected static void setSimulationInfoItem(String simulatorId,
			SimulationInfoItem simInfoItem) {
		// Saving old value
		SimulationInfoItem prevItem = simulatorIdSimInfoItem.get(simulatorId);
		if (prevItem != null) {
			prevSimulatorIdSimInfoItem.put(simulatorId, prevItem);
		}
		simulatorIdSimInfoItem.put(simulatorId, simInfoItem);
	}

	protected static void setSimulationPFDItem(String simulatorId,
			SimulationPFDItem simPFDItem) {
		simulatorIdSimPFDItem.put(simulatorId, simPFDItem);
	}

	public void fakeFunction() {

	}

	public static SimulationItem getSimulationItemBySimulatorId(
			String simulatorId) {
		return simulatorIdSimItem.get(simulatorId);
	}

	public static SimulationDevStateItem getSimulationDevStateItemBySimulatorId(
			String simulatorId) {
		return simulatorIdSimDevStateItem.get(simulatorId);
	}

	public static SimulationInfoItem getSimulationInfoItemBySimulatorId(
			String simulatorId) {
		return simulatorIdSimInfoItem.get(simulatorId);
	}

	public static SimulationInfoItem getPrevSimulationInfoItemBySimulatorId(
			String simulatorId) {
		return prevSimulatorIdSimInfoItem.get(simulatorId);
	}

	public static SimulationPFDItem getSimulationPFDItemBySimulatorId(
			String simulatorId) {
		return simulatorIdSimPFDItem.get(simulatorId);
	}

}