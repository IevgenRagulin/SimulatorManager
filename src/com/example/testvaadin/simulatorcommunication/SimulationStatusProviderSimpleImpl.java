package com.example.testvaadin.simulatorcommunication;

import java.util.HashMap;
import java.util.Map;

public class SimulationStatusProviderSimpleImpl implements
		SimulationStatusProviderInt {
	// If simulator is not responding we increase this number by 1. This is used
	// to figure out if simulator is currently running
	protected static Map<String, Integer> simulatorIdNumberOfFailedRequests = new HashMap<String, Integer>();
	// If simulator's position has not changed we increase this number by 1.
	// This is used to figure out if simulator is currently running
	protected static Map<String, Integer> simulatorIdNumberOfRespWithSimilarData = new HashMap<String, Integer>();
	// If the simulator doesn't respond maxFailedTolearatedRequests times, we
	// consider it not running
	private static int maxFailedTolearatedRequests = 5;

	// If the simulator has the same position maxSimilarPositions times, we
	// consider the simulator is not running
	private static int maxSimilarPositions = 1000;

	@Override
	public boolean isSimulatorRunning(AllSimulationInfo dataFromSimulation,
			String simulatorId) {
		updateSimulatorRespondStatistic(dataFromSimulation, simulatorId);
		Integer currentNumbOfFailed = simulatorIdNumberOfFailedRequests
				.get(simulatorId);
		Integer currentNumbOfRespWithSimilarData = simulatorIdNumberOfRespWithSimilarData
				.get(simulatorId);
		if (currentNumbOfFailed > maxFailedTolearatedRequests) {
			return false;
		}
		if (currentNumbOfRespWithSimilarData > maxSimilarPositions) {
			return false;
		}
		return true;
	}

	private void updateSimulatorRespondStatistic(
			AllSimulationInfo dataFromSimulation, String simulatorId) {
		// if simulator doesn't respond increase number of failed requests
		if (dataFromSimulation == null) {
			SimulationStatusProviderSimpleImpl
					.increaseNumberOfFailedRequests(simulatorId);
		} else {
			setNumOfFailedReqToZero(simulatorId);
		}
		// if simulator's position hasn't changed and it's not paused increase
		// number of responses with similar data
		if (!DatabaseUpdater.hasPlaneMoved(simulatorId)
				&& (dataFromSimulation != null)
				&& (!dataFromSimulation.getSimulationPaused())) {
			SimulationStatusProviderSimpleImpl
					.increaseNumberOfRespWithSimilarPosition(simulatorId);
		} else {
			setNumOfSimilarPosDataToZero(simulatorId);
		}
	}

	protected static void setNumOfFailedReqToZero(String simulatorId) {
		simulatorIdNumberOfFailedRequests.put(simulatorId, 0);
	}

	protected static void setNumOfSimilarPosDataToZero(String simulatorId) {
		simulatorIdNumberOfRespWithSimilarData.put(simulatorId, 0);
	}

	protected static void increaseNumberOfFailedRequests(String simulatorId) {
		Integer currentNumbOfFailed = simulatorIdNumberOfFailedRequests
				.get(simulatorId);
		if (currentNumbOfFailed == null) {
			currentNumbOfFailed = 0;
		}
		currentNumbOfFailed++;
		simulatorIdNumberOfFailedRequests.put(simulatorId, currentNumbOfFailed);
	}

	protected static void increaseNumberOfRespWithSimilarPosition(
			String simulatorId) {
		Integer currentNumbOfRespWithSimilarPosition = simulatorIdNumberOfRespWithSimilarData
				.get(simulatorId);
		if (currentNumbOfRespWithSimilarPosition == null) {
			currentNumbOfRespWithSimilarPosition = 0;
		}
		currentNumbOfRespWithSimilarPosition++;
		simulatorIdNumberOfRespWithSimilarData.put(simulatorId,
				currentNumbOfRespWithSimilarPosition);
	}

}
