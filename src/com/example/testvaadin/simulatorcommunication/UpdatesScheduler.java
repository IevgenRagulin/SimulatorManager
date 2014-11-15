package com.example.testvaadin.simulatorcommunication;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.data.DatabaseHelper;
import com.example.testvaadin.data.SimulatorCols;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class UpdatesScheduler {

	final static Logger logger = LoggerFactory.getLogger(UpdatesScheduler.class);

	protected static DatabaseHelper dbHelp = new DatabaseHelper();
	protected static final int UPDATE_RATE_MS = ApplicationConfiguration.getSimulatorGetDataFrequency();

	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static ExecutorService schedulerSimulationUpdater = Executors.newFixedThreadPool(2);
	private static int numberOfSimulators = 2;
	private static Map<String, Future> isTaskFinished = Collections.synchronizedMap(new HashMap<String, Future>());

	private static final Runnable beeper = new Runnable() {
		@Override
		public void run() {
			try {
				updateRunningSimsStatus();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	static {
		scheduler.scheduleAtFixedRate(beeper, 0, UPDATE_RATE_MS, TimeUnit.MILLISECONDS);

	}

	private UpdatesScheduler() {
	}

	public static void updateRunningSimsStatus() {
		SQLContainer simulatorContainer = dbHelp.getNewSimulatorContainer();
		if (numberOfSimulators != simulatorContainer.size()) {
			numberOfSimulators = simulatorContainer.size();
			schedulerSimulationUpdater.shutdownNow();
			schedulerSimulationUpdater = Executors.newFixedThreadPool(numberOfSimulators);
		}
		UpdatesScheduler.updateSimulatorsStatus(simulatorContainer);
	}

	public static void updateSimulatorsStatus(SQLContainer simulatorContainer) {
		Collection<?> itemIds = simulatorContainer.getItemIds();
		// Iterate over simulators, update information about running simulations
		for (Object itemId : itemIds) {
			Item simulatorItem = simulatorContainer.getItem(itemId);
			// check if simulator.active is set to true in db
			if (getSimulatorActiveFromSimItem(simulatorItem)) {
				String simulatorId = getSimulatorIdFromSimItem(simulatorItem);
				String simulatorHostname = getSimulatorHostnameFromSimItem(simulatorItem);
				int simulatorPort = getSimulatorPortFromSimItem(simulatorItem);
				SimulationsUpdater simUpdater = new SimulationsUpdater(simulatorId, simulatorHostname, simulatorPort);
				logger.info("Going to update hostname:portname {} {}", simulatorHostname, simulatorPort);
				if (isPreviousTaskOnThisSimFinished(simulatorId)) {
					Future submittedTask = schedulerSimulationUpdater.submit(simUpdater);
					isTaskFinished.put(simulatorId, submittedTask);
				} else {
					logger.info("No, prev task on this simulator is not finished. Simulator id: {} " + simulatorId);
				}
			}
		}
	}

	private static boolean isPreviousTaskOnThisSimFinished(String simulatorId) {
		if (isTaskFinished.get(simulatorId) == null) {
			return true;
		} else if (isTaskFinished.get(simulatorId).isDone()) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean getSimulatorActiveFromSimItem(Item simulatorItem) {
		return (Boolean) simulatorItem.getItemProperty(SimulatorCols.active.toString()).getValue();
	}

	private static String getSimulatorIdFromSimItem(Item simulatorItem) {
		return simulatorItem.getItemProperty(SimulatorCols.simulatorid.toString()).getValue().toString();
	}

	private static String getSimulatorHostnameFromSimItem(Item simulatorItem) {
		return simulatorItem.getItemProperty(SimulatorCols.hostname.toString()).getValue().toString();
	}

	private static int getSimulatorPortFromSimItem(Item simulatorItem) {
		return (Integer) simulatorItem.getItemProperty(SimulatorCols.port.toString()).getValue();
	}

}
