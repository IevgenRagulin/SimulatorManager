package com.example.testvaadin.simulatorcommunication;

import java.sql.SQLException;

import com.example.testvaadin.beans.SimulationDevStateBean;
import com.example.testvaadin.beans.SimulationInfoBean;
import com.example.testvaadin.beans.SimulationPFDBean;
import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.data.DatabaseHelper;
import com.example.testvaadin.items.SimulationInfoItem;
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

	public static void addSimulationInfoToDatabase(SQLContainer lastSimCont,
			String simulatorId, RowId simulationId) {
		int saveToDbFrequency = Math.round(ApplicationConfiguration
				.getWriteToDbFrequency()
				/ ApplicationConfiguration.getSimulatorGetDataFrequency());
		int saveSimInfoToDbFrequency = Math.round(ApplicationConfiguration
				.getWritePositionToDbFrequency()
				/ ApplicationConfiguration.getSimulatorGetDataFrequency());

		Integer simulationIdInt = Integer.valueOf(simulationId.toString());
		addedCount = (addedCount + 1) % saveToDbFrequency;
		addedSimInfoCount = (addedSimInfoCount + 1) % saveSimInfoToDbFrequency;
		if (hasPlaneMovedMoreThan(simulatorId, HALF_METER)
				&& (shouldWeSaveDataToDb())) {
			addDevicesStateInfoToDatabase(simulationIdInt, simulatorId);
			if (shouldWeSaveSimulationInfoToDatabase()) {
				addSimulationInfoInfoToDatabase(simulationIdInt, simulatorId);
			}
			addPfdInfoToDatabase(simulationIdInt, simulatorId);
		}
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
	public static boolean hasPlaneMovedMoreThan(String simulatorId,
			double distInMeters) {
		boolean hasPlaneMoved = true;
		SimulationInfoItem currentSimItem = SimulatorsStatus
				.getSimulationInfoItemBySimulatorId(simulatorId);
		SimulationInfoItem prevSimItem = SimulatorsStatus
				.getPrevSimulationInfoItemBySimulatorId(simulatorId);
		if (prevSimItem != null) {
			Double prevLatitude = prevSimItem.getBean().getLatitude();
			Double prevLongtitude = prevSimItem.getBean().getLongtitude();
			Double currentLatitude = currentSimItem.getBean().getLatitude();
			Double currentLongtitude = currentSimItem.getBean().getLongtitude();
			if (distanceBetweenTwoPoints(prevLatitude, prevLongtitude,
					currentLatitude, currentLongtitude) > distInMeters) {
				hasPlaneMoved = true;
			} else {
				hasPlaneMoved = false;
			}
		} else {
		}
		return hasPlaneMoved;
	}

	// calculates distance between two points in meters
	private static double distanceBetweenTwoPoints(double lat1, double lon1,
			double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
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
	private static void addDevicesStateInfoToDatabase(Integer simulationIdInt,
			String simulatorId) {
		SQLContainer simDevStCont = dbHelp.getSimulationDevicesStateContainer();
		SimulationDevStateBean simDevStBean = SimulatorsStatus
				.getSimulationDevStateItemBySimulatorId(simulatorId).getBean();
		RowId newSimDvStId = (RowId) simDevStCont.addItem();
		// set reference key to simulation id
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getSimulationidForeignKey()).setValue(
				simulationIdInt);
		// save elevator
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getElevator()).setValue(simDevStBean.getElevator());
		// save aileron
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getAileron()).setValue(simDevStBean.getEleron());
		// save rudder
		simDevStCont
				.getContainerProperty(newSimDvStId, ColumnNames.getRudder())
				.setValue(simDevStBean.getRudder());
		// save throttle
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getThrottle()).setValue(simDevStBean.getThrottle());
		// save flaps
		simDevStCont.getContainerProperty(newSimDvStId, ColumnNames.getFlaps())
				.setValue(simDevStBean.getFlaps());
		// save speed brakes
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getSpeedbrakes()).setValue(
				simDevStBean.getSpeedbrakes());
		// save aileron trim
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getAileronTrim()).setValue(
				simDevStBean.getAilerontrim());
		// save elevator trim
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getElevatorTrim()).setValue(
				simDevStBean.getElevatortrim());
		// save rudder trim
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getRudderTrim()).setValue(
				simDevStBean.getRuddertrim());
		// save brakes status
		simDevStCont
				.getContainerProperty(newSimDvStId, ColumnNames.getBrakes())
				.setValue(simDevStBean.getBrakes());
		// save is simulation paused data
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getIssimulationpaused()).setValue(
				simDevStBean.getIssimulationpaused());
		// save landing gear data
		System.out.println("land gear 1" + simDevStBean.getLandinggear_1());
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getLandinggear1()).setValue(
				simDevStBean.getLandinggear_1());
		System.out.println("land gear 2" + simDevStBean.getLandinggear_2());
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getLandinggear2()).setValue(
				simDevStBean.getLandinggear_2());
		System.out.println("land gear 3" + simDevStBean.getLandinggear_3());
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getLandinggear3()).setValue(
				simDevStBean.getLandinggear_3());
		commitChangeInSQLContainer(simDevStCont);
	}

	/*
	 * Adds primary flight display info to database
	 */
	@SuppressWarnings("unchecked")
	private static void addPfdInfoToDatabase(Integer simulationIdInt,
			String simulatorId) {
		SQLContainer simPfdCont = dbHelp.getSimulationPFDContainer();
		SimulationPFDBean simPfdBean = SimulatorsStatus
				.getSimulationPFDItemBySimulatorId(simulatorId).getBean();
		RowId newPfdId = (RowId) simPfdCont.addItem();
		simPfdCont.getContainerProperty(newPfdId,
				ColumnNames.getSimulationidForeignKey()).setValue(
				simulationIdInt);
		simPfdCont.getContainerProperty(newPfdId, ColumnNames.getRoll())
				.setValue(simPfdBean.getRoll());
		simPfdCont.getContainerProperty(newPfdId, ColumnNames.getPitch())
				.setValue(simPfdBean.getPitch());
		simPfdCont.getContainerProperty(newPfdId, ColumnNames.getHeading())
				.setValue(simPfdBean.getHeading());
		simPfdCont.getContainerProperty(newPfdId, ColumnNames.getTrueCourse())
				.setValue(simPfdBean.getTruecourse());
		simPfdCont.getContainerProperty(newPfdId, ColumnNames.getIas())
				.setValue(simPfdBean.getIas());
		simPfdCont.getContainerProperty(newPfdId, ColumnNames.getAltitude())
				.setValue(simPfdBean.getAltitude());
		simPfdCont.getContainerProperty(newPfdId,
				ColumnNames.getGroundaltitude()).setValue(
				simPfdBean.getGroundaltitude());
		simPfdCont.getContainerProperty(newPfdId,
				ColumnNames.getVerticalspeed()).setValue(
				simPfdBean.getVerticalspeed());
		commitChangeInSQLContainer(simPfdCont);

	}

	@SuppressWarnings("unchecked")
	private static void addSimulationInfoInfoToDatabase(
			Integer simulationIdInt, String simulatorId) {
		SQLContainer simInfoCont = dbHelp.getSimulationInfoContainer();
		SimulationInfoBean simInfoBean = SimulatorsStatus
				.getSimulationInfoItemBySimulatorId(simulatorId).getBean();
		RowId newInfoId = (RowId) simInfoCont.addItem();
		simInfoCont.getContainerProperty(newInfoId,
				ColumnNames.getSimulationidForeignKey()).setValue(
				simulationIdInt);
		simInfoCont
				.getContainerProperty(newInfoId, ColumnNames.getLongtitude())
				.setValue(simInfoBean.getLongtitude());
		simInfoCont.getContainerProperty(newInfoId, ColumnNames.getLatitude())
				.setValue(simInfoBean.getLatitude());
		commitChangeInSQLContainer(simInfoCont);

	}

	@SuppressWarnings("unchecked")
	public static RowId createNewRunningPausedSimulation(
			SQLContainer lastSimCont, String simulatorId) {
		RowId id = (RowId) lastSimCont.addItem();
		lastSimCont.getContainerProperty(id,
				ColumnNames.getSimulatoridForeignKey()).setValue(
				Integer.valueOf(simulatorId));
		lastSimCont.getContainerProperty(id, ColumnNames.getIssimulationon())
				.setValue(true);
		lastSimCont.getContainerProperty(id,
				ColumnNames.getIssimulationpaused()).setValue(true);
		commitChangeInSQLContainer(lastSimCont);
		return id;
	}

	/*
	 * Create new simulation session which is in RUNNING, NOT PAUSED state
	 */
	@SuppressWarnings("unchecked")
	public static RowId createNewRunningNotPausedSimulation(
			SQLContainer lastSimCont, String simulatorId) {
		RowId id = (RowId) lastSimCont.addItem();
		lastSimCont.getContainerProperty(id,
				ColumnNames.getSimulatoridForeignKey()).setValue(
				Integer.valueOf(simulatorId));
		lastSimCont.getContainerProperty(id, ColumnNames.getIssimulationon())
				.setValue(true);
		lastSimCont.getContainerProperty(id,
				ColumnNames.getIssimulationpaused()).setValue(false);
		commitChangeInSQLContainer(lastSimCont);
		return id;
	}

	/*
	 * Set simulation to RUNNING, PAUSED state
	 */
	@SuppressWarnings("unchecked")
	public static void setSimOnPausedState(SQLContainer lastSimCont,
			Item simulation) {
		simulation.getItemProperty(ColumnNames.getIssimulationon()).setValue(
				SimulatorsStatus.SIMULATION_ON);
		simulation.getItemProperty(ColumnNames.getIssimulationpaused())
				.setValue(SimulatorsStatus.SIMULATION_PAUSED);
		commitChangeInSQLContainer(lastSimCont);
	}

	/*
	 * Set simulation to RUNNING, NOT PAUSED state
	 */
	@SuppressWarnings("unchecked")
	public static void setSimOnNotPausedState(SQLContainer lastSimCont,
			Item simulation) {
		simulation.getItemProperty(ColumnNames.getIssimulationon()).setValue(
				SimulatorsStatus.SIMULATION_ON);
		simulation.getItemProperty(ColumnNames.getIssimulationpaused())
				.setValue(SimulatorsStatus.SIMULATION_NOT_PAUSED);
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
	public static void setSimOffNotPausedState(SQLContainer lastSimCont,
			Item lastSim) {
		lastSim.getItemProperty(ColumnNames.getIssimulationon()).setValue(
				SimulatorsStatus.SIMULATION_OFF);
		lastSim.getItemProperty(ColumnNames.getIssimulationpaused()).setValue(
				SimulatorsStatus.SIMULATION_NOT_PAUSED);
		commitChangeInSQLContainer(lastSimCont);
	}
}
