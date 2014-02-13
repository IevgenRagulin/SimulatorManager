package com.example.testvaadin.logic;

import com.example.testvaadin.views.RunningSimulationsView;

public class RunningSimulationsDataSetter {
	private static final String NO_SIMULATOR_SELECTED = "Please, select simulator";

	public static void setSimulatorNotSelectedState(
			RunningSimulationsView runningSims) {
		runningSims.getErrorLabel().setValue(NO_SIMULATOR_SELECTED);
		runningSims.getSimulatorInfo().setEnabled(false);
		runningSims.getSimulationInfo().setEnabled(false);
		runningSims.getSimulatorDevicesState().setEnabled(false);
	}

}
