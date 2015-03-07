package cz.vutbr.fit.simulatormanager.simulatorcommunication;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vutbr.fit.simulatormanager.beans.AllSimulationInfo;
import cz.vutbr.fit.simulatormanager.items.SimulationInfoItem;
import cz.vutbr.fit.simulatormanager.util.DistanceUtil;

public class SimulationStatusProviderSimpleImpl {
	final static Logger logger = LoggerFactory.getLogger(SimulationStatusProviderSimpleImpl.class);

	// If simulator is not responding we increase this number by 1. This is used
	// to figure out if simulator is currently running
	protected static volatile Map<String, Integer> simulatorIdNumberOfFailedRequests = Collections
			.synchronizedMap(new HashMap<String, Integer>());
	// If simulator's position has not changed we increase this number by 1.
	// This is used to figure out if simulator is currently running
	protected static volatile Map<String, Integer> simulatorIdNumberOfRespWithSimilarData = Collections
			.synchronizedMap(new HashMap<String, Integer>());
	// If the simulator doesn't respond maxFailedTolearatedRequests times, we
	// consider it not running
	private static int maxFailedTolearatedRequests = 10;
	protected static final double HALF_METER = 0.5;
	private static final double ONE_KM = 1000;

	// If the simulator has the same position maxSimilarPositions times, we
	// consider the simulator is not running
	private static int maxSimilarPositions = 10000;

	/**
	 * Ping simulator. Return true if we got some response from simulator
	 * 
	 * @param host
	 * @param port
	 * @return
	 */
	public static boolean isSimulatorResponding(String host, int port) {
		return (AWComClient.getSimulationData(host, port) != null);
	}

	public static boolean isSimulatorRunning(AllSimulationInfo dataFromSimulation, String simulatorId) {
		SimulationStatusProviderSimpleImpl.updateSimulatorRespondStatistic(dataFromSimulation, simulatorId);
		Integer currentNumbOfFailed = simulatorIdNumberOfFailedRequests.get(simulatorId);
		Integer currentNumbOfRespWithSimilarData = simulatorIdNumberOfRespWithSimilarData.get(simulatorId);
		if (currentNumbOfFailed > maxFailedTolearatedRequests) {
			return false;
		}
		if (currentNumbOfRespWithSimilarData > maxSimilarPositions) {
			return false;
		}
		return true;
	}

	private static void updateSimulatorRespondStatistic(AllSimulationInfo dataFromSimulation, String simulatorId) {
		// if simulator doesn't respond increase number of failed requests
		if (dataFromSimulation == null) {
			SimulationStatusProviderSimpleImpl.increaseNumberOfFailedRequests(simulatorId);
		} else {
			setNumberOfFailedRequests(simulatorId, 0);
		}
		// if simulator's position hasn't changed and it's not paused increase
		// number of responses with similar data
		if (!hasPlaneMovedMoreThan(simulatorId, HALF_METER) && (dataFromSimulation != null)
				&& (!dataFromSimulation.getSimulationPaused())) {
			SimulationStatusProviderSimpleImpl.increaseNumberOfRespWithSimilarPosition(simulatorId);
		} else {
			setNumOfSimilarPosDataToZero(simulatorId);
		}
		// If simulator's position has changed too much (i.e. 5kms), it means
		// that the airplane has been moved to a new position
		// not by flying there, but by setting the position through X-Plane.
		// This means, we should finish this simulation, and start a new one
		if (hasPlaneMovedMoreThan(simulatorId, ONE_KM)) {
			logger.info(
					"the plane has moved over distance which is more than {} meters. Probably, previous simulation has finished. Creating new simulation session.",
					ONE_KM);
			setNumberOfFailedRequests(simulatorId, maxFailedTolearatedRequests + 1);
		}
	}

	private static void setNumberOfFailedRequests(String simulatorId, int numberOfFailedRequests) {
		simulatorIdNumberOfFailedRequests.put(simulatorId, numberOfFailedRequests);
	}

	/**
	 * Checks if the airplane has moved on a distance more then @distInMeters
	 */
	private static boolean hasPlaneMovedMoreThan(String simulatorId, double distInMeters) {
		SimulationInfoItem currentSimItem = SimulatorsStatus.getSimulationInfoItemBySimulatorId(simulatorId);
		SimulationInfoItem prevSimItem = SimulatorsStatus.getPrevSimulationInfoItemBySimulatorId(simulatorId);
		return DistanceUtil.hasPlaneMovedMoreThan(currentSimItem, prevSimItem, distInMeters);
	}

	protected static void setNumOfFailedReqToZero(String simulatorId) {
		simulatorIdNumberOfFailedRequests.put(simulatorId, 0);
	}

	protected static void setNumOfSimilarPosDataToZero(String simulatorId) {
		simulatorIdNumberOfRespWithSimilarData.put(simulatorId, 0);
	}

	protected static void increaseNumberOfFailedRequests(String simulatorId) {
		Integer currentNumbOfFailed = simulatorIdNumberOfFailedRequests.get(simulatorId);
		if (currentNumbOfFailed == null) {
			currentNumbOfFailed = 0;
		}
		simulatorIdNumberOfFailedRequests.put(simulatorId, ++currentNumbOfFailed);
	}

	protected static void increaseNumberOfRespWithSimilarPosition(String simulatorId) {
		Integer currentNumbOfRespWithSimilarPosition = simulatorIdNumberOfRespWithSimilarData.get(simulatorId);
		if (currentNumbOfRespWithSimilarPosition == null) {
			currentNumbOfRespWithSimilarPosition = 0;
		}
		simulatorIdNumberOfRespWithSimilarData.put(simulatorId, ++currentNumbOfRespWithSimilarPosition);
	}

}
