package com.example.testvaadin.simulatorcommunication;

import java.sql.SQLException;
import java.util.Collection;

import com.example.testvaadin.beans.SimulationInfoBean;
import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.data.DatabaseHelper;
import com.example.testvaadin.data.SimulationCols;
import com.example.testvaadin.items.SimulationDevStateItem;
import com.example.testvaadin.items.SimulationEnginesStateItem;
import com.example.testvaadin.items.SimulationInfoItem;
import com.example.testvaadin.items.SimulationPFDItem;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class DatabaseUpdater {
	protected static final double HALF_METER = 0.5;
	private static DatabaseHelper dbHelp = new DatabaseHelper();

	// in combination with saveToDbFrequency used to determine if we should save
	// data to db
	private static int addedCount = 0;
	private static int addedSimInfoCount = 0;

	public static void addSimulationInfoToDatabase(SQLContainer lastSimCont, String simulatorId, RowId simulationId) {
		int saveToDbFrequency = Math.round(ApplicationConfiguration.getWriteToDbFrequency()
				/ ApplicationConfiguration.getSimulatorGetDataFrequency());
		int saveSimInfoToDbFrequency = Math.round(ApplicationConfiguration.getWritePositionToDbFrequency()
				/ ApplicationConfiguration.getSimulatorGetDataFrequency());

		Integer simulationIdInt = Integer.valueOf(simulationId.toString());
		addedCount = (addedCount + 1) % saveToDbFrequency;
		addedSimInfoCount = (addedSimInfoCount + 1) % saveSimInfoToDbFrequency;
		if (hasPlaneMovedMoreThan(simulatorId, HALF_METER) && (shouldWeSaveDataToDb())) {
			addDevicesStateInfoToDatabase(simulationIdInt, simulatorId);
			if (shouldWeSaveSimulationInfoToDatabase()) {
				addSimulationInfoInfoToDatabase(simulationIdInt, simulatorId);
			}
			addPfdInfoToDatabase(simulationIdInt, simulatorId);
			System.out.println("going to add engines info to dtb" + simulatorId);
			// addEnginesInfoToDatabase(simulationIdInt, simulatorId);
		}
		// TODO: MOVE IT UP
		addEnginesInfoToDatabase(simulationIdInt, simulatorId);
	}

	// Returns true if addedCount==0 (we save data to db every saveToDbFrequency
	// time)
	private static boolean shouldWeSaveDataToDb() {
		return addedCount == 0;
	}

	// Returns true if addedSimInfoCount==0 (we save data to db every
	// saveSimInfoToDbFrequency time)
	private static boolean shouldWeSaveSimulationInfoToDatabase() {
		return addedSimInfoCount == 0;
	}

	/*
	 * Returns true if the plane has moved n a distance more then @distInMeters
	 */
	public static boolean hasPlaneMovedMoreThan(String simulatorId, double distInMeters) {
		boolean hasPlaneMoved = true;
		SimulationInfoItem currentSimItem = SimulatorsStatus.getSimulationInfoItemBySimulatorId(simulatorId);
		SimulationInfoItem prevSimItem = SimulatorsStatus.getPrevSimulationInfoItemBySimulatorId(simulatorId);
		if (prevSimItem != null) {
			Double prevLatitude = prevSimItem.getBean().getLatitude();
			Double prevLongtitude = prevSimItem.getBean().getLongtitude();
			Double currentLatitude = currentSimItem.getBean().getLatitude();
			Double currentLongtitude = currentSimItem.getBean().getLongtitude();
			if (distanceBetweenTwoPoints(prevLatitude, prevLongtitude, currentLatitude, currentLongtitude) > distInMeters) {
				hasPlaneMoved = true;
			} else {
				hasPlaneMoved = false;
			}
		} else {
		}
		return hasPlaneMoved;
	}

	// calculates distance between two points in meters
	private static double distanceBetweenTwoPoints(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344 * 1000;
		return (dist);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts decimal degrees to radians : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts radians to decimal degrees : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	/*
	 * Saves data about devices state to database
	 */
	@SuppressWarnings("unchecked")
	private static void addDevicesStateInfoToDatabase(Integer simulationIdInt, String simulatorId) {
		SQLContainer simDevStCont = dbHelp.getSimulationDevicesStateContainer();
		SimulationDevStateItem simDevStItem = SimulatorsStatus.getSimulationDevStateItemBySimulatorId(simulatorId);
		RowId newSimDvStId = (RowId) simDevStCont.addItem();
		Collection<?> itemPropIds = simDevStItem.getItemPropertyIds();
		// set reference key to simulation id
		simDevStCont.getContainerProperty(newSimDvStId, ColumnNames.getSimulationidForeignKey()).setValue(simulationIdInt);

		// Set values: elevator, eleron, rudder, throttle...
		for (Object prop : itemPropIds) {
			String propertyName = ((String) prop);
			// System.out.println(itemStr
			// + simDevStItem.getItemProperty(itemStr).getValue());
			simDevStCont.getContainerProperty(newSimDvStId, propertyName).setValue(simDevStItem.getItemProperty(propertyName).getValue());
		}

		commitChangeInSQLContainer(simDevStCont);
	}

	/*
	 * Adds primary flight display info to database
	 */
	@SuppressWarnings("unchecked")
	private static void addPfdInfoToDatabase(Integer simulationIdInt, String simulatorId) {
		SQLContainer simPfdCont = dbHelp.getSimulationPFDContainer();
		SimulationPFDItem simPfdItem = SimulatorsStatus.getSimulationPFDItemBySimulatorId(simulatorId);
		RowId newPfdId = (RowId) simPfdCont.addItem();
		// set reference key to simulation id
		simPfdCont.getContainerProperty(newPfdId, ColumnNames.getSimulationidForeignKey()).setValue(simulationIdInt);

		Collection<?> itemPropIds = simPfdItem.getItemPropertyIds();
		// set values roll, pitch, heading, truecourse...
		for (Object prop : itemPropIds) {
			String propertyName = ((String) prop);
			// System.out.println(propertyName
			// + simPfdItem.getItemProperty(propertyName).getValue());
			simPfdCont.getContainerProperty(newPfdId, propertyName).setValue(simPfdItem.getItemProperty(propertyName).getValue());
		}
		commitChangeInSQLContainer(simPfdCont);

	}

	@SuppressWarnings("unchecked")
	private static void addSimulationInfoInfoToDatabase(Integer simulationIdInt, String simulatorId) {
		SQLContainer simInfoCont = dbHelp.getSimulationInfoContainer();
		SimulationInfoBean simInfoBean = SimulatorsStatus.getSimulationInfoItemBySimulatorId(simulatorId).getBean();
		RowId newInfoId = (RowId) simInfoCont.addItem();
		simInfoCont.getContainerProperty(newInfoId, ColumnNames.getSimulationidForeignKey()).setValue(simulationIdInt);
		simInfoCont.getContainerProperty(newInfoId, ColumnNames.getLongtitude()).setValue(simInfoBean.getLongtitude());
		simInfoCont.getContainerProperty(newInfoId, ColumnNames.getLatitude()).setValue(simInfoBean.getLatitude());
		commitChangeInSQLContainer(simInfoCont);

	}

	/*
	 * Saves data about engines state to database
	 */
	@SuppressWarnings("unchecked")
	private static void addEnginesInfoToDatabase(Integer simulationIdInt, String simulatorId) {
		SimulationEnginesStateItem simEnginesInfoItem = SimulatorsStatus.getSimulationEngineItemBySimulatorId(simulatorId);
		if (simEnginesInfoItem != null) {
			SQLContainer simEnginesStCont = dbHelp.getSimulationEnginesStateContainer();
			Collection<?> itemPropIdsCont = simEnginesStCont.getContainerPropertyIds();
			System.out.println("item prop ids cont" + itemPropIdsCont);
			for (Object prop : itemPropIdsCont) {
				String propertyName = ((String) prop);
				System.out.println("cont prop name" + propertyName);
			}

			RowId newSimEngStId = (RowId) simEnginesStCont.addItem();
			// set reference key to simulation id
			simEnginesStCont.getContainerProperty(newSimEngStId, ColumnNames.getSimulationidForeignKey()).setValue(simulationIdInt);
			Collection<?> itemPropIds = simEnginesInfoItem.getItemPropertyIds();
			// set values E1RPM, E1PWR, E1PWP...
			for (Object prop : itemPropIds) {
				String propertyName = ((String) prop);
				System.out.println(propertyName + simEnginesInfoItem.getItemProperty(propertyName).getValue());

				System.out.println("Property" + simEnginesStCont.getContainerProperty(newSimEngStId, propertyName));
				simEnginesStCont.getContainerProperty(newSimEngStId, propertyName).setValue(
						simEnginesInfoItem.getItemProperty(propertyName).getValue());
			}
			commitChangeInSQLContainer(simEnginesStCont);
		}
	}

	@SuppressWarnings("unchecked")
	public static RowId createNewRunningPausedSimulation(SQLContainer lastSimCont, String simulatorId) {
		RowId id = (RowId) lastSimCont.addItem();
		lastSimCont.getContainerProperty(id, SimulationCols.simulator_simulatorid.toString()).setValue(Integer.valueOf(simulatorId));
		lastSimCont.getContainerProperty(id, SimulationCols.issimulationon.toString()).setValue(true);
		lastSimCont.getContainerProperty(id, SimulationCols.issimulationpaused.toString()).setValue(true);
		commitChangeInSQLContainer(lastSimCont);
		return id;
	}

	/*
	 * Create new simulation session which is in RUNNING, NOT PAUSED state
	 */
	@SuppressWarnings("unchecked")
	public static RowId createNewRunningNotPausedSimulation(SQLContainer lastSimCont, String simulatorId) {
		RowId id = (RowId) lastSimCont.addItem();
		lastSimCont.getContainerProperty(id, SimulationCols.simulator_simulatorid.toString()).setValue(Integer.valueOf(simulatorId));
		lastSimCont.getContainerProperty(id, SimulationCols.issimulationon.toString()).setValue(true);
		lastSimCont.getContainerProperty(id, SimulationCols.issimulationpaused.toString()).setValue(false);
		commitChangeInSQLContainer(lastSimCont);
		return id;
	}

	/*
	 * Set simulation to RUNNING, PAUSED state
	 */
	@SuppressWarnings("unchecked")
	public static void setSimOnPausedState(SQLContainer lastSimCont, Item simulation) {
		simulation.getItemProperty(SimulationCols.issimulationon.toString()).setValue(SimulatorsStatus.SIMULATION_ON);
		simulation.getItemProperty(SimulationCols.issimulationpaused.toString()).setValue(SimulatorsStatus.SIMULATION_PAUSED);
		commitChangeInSQLContainer(lastSimCont);
	}

	/*
	 * Set simulation to RUNNING, NOT PAUSED state
	 */
	@SuppressWarnings("unchecked")
	public static void setSimOnNotPausedState(SQLContainer lastSimCont, Item simulation) {
		simulation.getItemProperty(SimulationCols.issimulationon.toString()).setValue(SimulatorsStatus.SIMULATION_ON);
		simulation.getItemProperty(SimulationCols.issimulationpaused.toString()).setValue(SimulatorsStatus.SIMULATION_NOT_PAUSED);
		commitChangeInSQLContainer(lastSimCont);
	}

	private static void commitChangeInSQLContainer(SQLContainer sqlCont) {
		try {
			sqlCont.commit();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Set simulation to OFF, NOT PAUSED state
	 */
	@SuppressWarnings("unchecked")
	public static void setSimOffNotPausedState(SQLContainer lastSimCont, Item lastSim) {
		lastSim.getItemProperty(SimulationCols.issimulationon.toString()).setValue(SimulatorsStatus.SIMULATION_OFF);
		lastSim.getItemProperty(SimulationCols.issimulationpaused.toString()).setValue(SimulatorsStatus.SIMULATION_NOT_PAUSED);
		commitChangeInSQLContainer(lastSimCont);
	}
}
