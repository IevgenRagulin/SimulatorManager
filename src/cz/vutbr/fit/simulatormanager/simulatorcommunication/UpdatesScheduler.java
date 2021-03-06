package cz.vutbr.fit.simulatormanager.simulatorcommunication;

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

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

import cz.vutbr.fit.simulatormanager.data.AppConfig;
import cz.vutbr.fit.simulatormanager.database.DatabaseHelper;
import cz.vutbr.fit.simulatormanager.database.columns.SimulatorCols;

/**
 * This class is the heart of the backend part of the application. It schedules
 * the simulators to be contacted every T milliseconds, dedicating one thread
 * for each active simulator
 * 
 * @author zhenia
 *
 */
public class UpdatesScheduler {

    final static Logger LOG = LoggerFactory.getLogger(UpdatesScheduler.class);

    protected static DatabaseHelper dbHelp = new DatabaseHelper();
    protected static final int UPDATE_RATE_MS = AppConfig.getSimulatorGetDataFrequency();

    private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static int numberOfThreads = 1;
    private static ExecutorService schedulerSimulationUpdater = Executors.newFixedThreadPool(numberOfThreads);
    // map of tasks. key - simulator id
    @SuppressWarnings("rawtypes")
    private static Map<String, Future> isTaskFinished = Collections.synchronizedMap(new HashMap<String, Future>());

    private static final Runnable beeper = new Runnable() {
	@Override
	public void run() {
	    try {
		updateRunningSimsStatus();
	    } catch (Exception e) {
		LOG.error("Unexpected exception during updating running sims status", e);
	    }
	}
    };
    static {
	LOG.info("Scheduling updates at fixed rate. UPDATE_RATE_MS: {}", UPDATE_RATE_MS);
	scheduler.scheduleAtFixedRate(beeper, 0, UPDATE_RATE_MS, TimeUnit.MILLISECONDS);

    }

    private UpdatesScheduler() {
    }

    public static void updateRunningSimsStatus() {
	SQLContainer simulatorContainer = dbHelp.getNewSimulatorContainer();
	if (simulatorContainer != null) {
	    if (numberOfThreads != simulatorContainer.size()) {
		LOG.info("Number of simulators has changed. Previously we had: {} threads, now: {}. Creating new thread pool. ",
			numberOfThreads, simulatorContainer.size());
		numberOfThreads = simulatorContainer.size();
		schedulerSimulationUpdater.shutdownNow();
		if (numberOfThreads != 0) {
		    schedulerSimulationUpdater = Executors.newFixedThreadPool(numberOfThreads);
		}
	    }
	    UpdatesScheduler.updateSimulatorsStatus(simulatorContainer);
	}
    }

    @SuppressWarnings("rawtypes")
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
		// we don't want 2 threads to be updating the same simulator at
		// the same time
		if (isPreviousTaskOnThisSimFinished(simulatorId)) {
		    Future submittedTask = schedulerSimulationUpdater.submit(simUpdater);
		    isTaskFinished.put(simulatorId, submittedTask);
		} else {
		    LOG.debug("No, prev task on this simulator is not finished. Simulator id: {} ", simulatorId);
		}
	    }
	}
    }

    private static boolean isPreviousTaskOnThisSimFinished(String simulatorId) {
	if (isTaskFinished.get(simulatorId) == null) {
	    return true;
	} else if (isTaskFinished.get(simulatorId).isDone()) {
	    return true;
	}
	return false;
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
