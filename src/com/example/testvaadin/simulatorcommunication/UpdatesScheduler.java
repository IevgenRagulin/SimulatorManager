package com.example.testvaadin.simulatorcommunication;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.data.DatabaseHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class UpdatesScheduler {
	protected static DatabaseHelper dbHelp = new DatabaseHelper();
	protected static final int UPDATE_RATE_MS = ApplicationConfiguration
			.getSimulatorGetDataFrequency();

	private final static ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);
	private static ExecutorService schedulerSimulationUpdater = Executors
			.newFixedThreadPool(2);
	private static int numberOfSimulators = 2;
	private static Map<String, Future> isTaskFinished = Collections
			.synchronizedMap(new HashMap<String, Future>());

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
		scheduler.scheduleAtFixedRate(beeper, 0, UPDATE_RATE_MS,
				TimeUnit.MILLISECONDS);

	}

	private UpdatesScheduler() {
	}

	public static void updateRunningSimsStatus() {
		SQLContainer simulatorContainer = dbHelp.getNewSimulatorContainer();
		if (numberOfSimulators != simulatorContainer.size()) {
			numberOfSimulators = simulatorContainer.size();
			schedulerSimulationUpdater.shutdownNow();
			schedulerSimulationUpdater = Executors
					.newFixedThreadPool(numberOfSimulators);
		}
		UpdatesScheduler.updateSimulatorsStatus(simulatorContainer);
	}

	public static void updateSimulatorsStatus(SQLContainer simulatorContainer) {
		Collection<?> itemIds = simulatorContainer.getItemIds();
		// Iterate over simulators, update information about running simulations
		for (Object itemId : itemIds) {
			Item simulatorItem = simulatorContainer.getItem(itemId);
			String simulatorId = getSimulatorIdFromSimItem(simulatorItem);
			String simulatorHostname = getSimulatorHostnameFromSimItem(simulatorItem);
			int simulatorPort = getSimulatorPortFromSimItem(simulatorItem);
			SimulationsUpdater simUpdater = new SimulationsUpdater(simulatorId,
					simulatorHostname, simulatorPort);
			if (isPreviousTaskOnThisSimFinished(simulatorId)) {
				System.out.println("before submitting task "
						+ (new Date().getTime()) + " simid" + simulatorId);
				Future submittedTask = schedulerSimulationUpdater
						.submit(simUpdater);
				isTaskFinished.put(simulatorId, submittedTask);
			} else {
				System.out
						.println("No, prev task on this simulator is not finished"
								+ simulatorId);
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

	private static String getSimulatorIdFromSimItem(Item simulatorItem) {
		return simulatorItem
				.getItemProperty(ColumnNames.getSimulatorIdPropName())
				.getValue().toString();
	}

	private static String getSimulatorHostnameFromSimItem(Item simulatorItem) {
		return simulatorItem
				.getItemProperty(ColumnNames.getSimulatorHostname()).getValue()
				.toString();
	}

	private static int getSimulatorPortFromSimItem(Item simulatorItem) {
		return (Integer) simulatorItem.getItemProperty(
				ColumnNames.getSimulatorPortName()).getValue();
	}

}
