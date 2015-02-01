package com.example.testvaadin.simulatorcommunication;

import java.sql.SQLException;
import java.util.Collection;

import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.testvaadin.beans.SimulationInfoBean;
import com.example.testvaadin.data.ApplicationConfiguration;
import com.example.testvaadin.data.DatabaseHelper;
import com.example.testvaadin.data.SimulationCols;
import com.example.testvaadin.data.SimulationInfoCols;
import com.example.testvaadin.items.SimulationDevStateItem;
import com.example.testvaadin.items.SimulationEnginesStateItem;
import com.example.testvaadin.items.SimulationInfoItem;
import com.example.testvaadin.items.SimulationPFDItem;
import com.example.testvaadin.util.DistanceUtil;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class DatabaseUpdater {
	protected static final double HALF_METER = 0.5;
	private static DatabaseHelper dbHelp = new DatabaseHelper();
	public final static Logger logger = LoggerFactory.getLogger(DatabaseUpdater.class);

	// in combination with saveToDbFrequency used to determine if we should save
	// data to db
	private static int addedCount = 0;
	private static int addedSimInfoCount = 0;

	public static void addSimulationInfoToDatabase(SQLContainer lastSimCont, String simulatorId, RowId simulationId) throws UnsupportedOperationException, SQLException {
		int saveToDbFrequency = Math.round(ApplicationConfiguration.getWriteToDbFrequency()
				/ ApplicationConfiguration.getSimulatorGetDataFrequency());
		int saveSimInfoToDbFrequency = Math.round(ApplicationConfiguration.getWritePositionToDbFrequency()
				/ ApplicationConfiguration.getSimulatorGetDataFrequency());

		Integer simulationIdInt = Integer.valueOf(simulationId.toString());
		addedCount = (addedCount + 1) % saveToDbFrequency;
		addedSimInfoCount = (addedSimInfoCount + 1) % saveSimInfoToDbFrequency;
		SimulationInfoItem currentSimItem = SimulatorsStatus.getSimulationInfoItemBySimulatorId(simulatorId);
		SimulationInfoItem prevSimItem = SimulatorsStatus.getPrevSimulationInfoItemBySimulatorId(simulatorId);
		if (DistanceUtil.hasPlaneMovedMoreThan(currentSimItem, prevSimItem, HALF_METER) && (shouldWeSaveDataToDb())) {
			addDevicesStateInfoToDatabase(simulationIdInt, simulatorId);
			addPfdInfoToDatabase(simulationIdInt, simulatorId);
			// logger.info ("going to add engines info to dtb for simulator id: ", simulatorId);
			addEnginesInfoToDatabase(simulationIdInt, simulatorId);
			if (shouldWeSaveSimulationInfoToDatabase()) {
				addSimulationInfoInfoToDatabase(simulationIdInt, simulatorId);
			}
		}
	}

	/** 
	* Returns true if addedCount==0 (we save data to db every saveToDbFrequency
	* time)
	*/
	private static boolean shouldWeSaveDataToDb() {
		return addedCount == 0;
	}

	/** 
	* Returns true if addedSimInfoCount==0 (we save data to db every
	* saveSimInfoToDbFrequency time)
	*/
	private static boolean shouldWeSaveSimulationInfoToDatabase() {
		return addedSimInfoCount == 0;
	}

	/*
	 * Saves data about devices state to database
	 */
	@SuppressWarnings("unchecked")
	private static void addDevicesStateInfoToDatabase(Integer simulationIdInt, String simulatorId) throws UnsupportedOperationException, SQLException {
		SQLContainer simDevStCont = dbHelp.getSimulationDevicesStateContainer();
		SimulationDevStateItem simDevStItem = SimulatorsStatus.getSimulationDevStateItemBySimulatorId(simulatorId);
		RowId newSimDvStId = (RowId) simDevStCont.addItem();
		Collection<?> itemPropIds = simDevStItem.getItemPropertyIds();
		// set reference key to simulation id
		simDevStCont.getContainerProperty(newSimDvStId, SimulationInfoCols.simulation_simulationid.toString()).setValue(simulationIdInt);

		// Set values: elevator, eleron, rudder, throttle...
		for (Object prop : itemPropIds) {
			String propertyName = ((String) prop);
			simDevStCont.getContainerProperty(newSimDvStId, propertyName).setValue(simDevStItem.getItemProperty(propertyName).getValue());
		}

		commitChangeInSQLContainer(simDevStCont);
	}

	/*
	 * Adds primary flight display info to database
	 */
	@SuppressWarnings("unchecked")
	private static void addPfdInfoToDatabase(Integer simulationIdInt, String simulatorId) throws UnsupportedOperationException, SQLException {
		SQLContainer simPfdCont = dbHelp.getSimulationPFDContainer();
		SimulationPFDItem simPfdItem = SimulatorsStatus.getSimulationPFDItemBySimulatorId(simulatorId);
		RowId newPfdId = (RowId) simPfdCont.addItem();
		// set reference key to simulation id
		simPfdCont.getContainerProperty(newPfdId, SimulationInfoCols.simulation_simulationid.toString()).setValue(simulationIdInt);

		Collection<?> itemPropIds = simPfdItem.getItemPropertyIds();
		// set values roll, pitch, heading, truecourse...
		for (Object prop : itemPropIds) {
			String propertyName = ((String) prop);
			simPfdCont.getContainerProperty(newPfdId, propertyName).setValue(simPfdItem.getItemProperty(propertyName).getValue());
		}
		commitChangeInSQLContainer(simPfdCont);

	}

	@SuppressWarnings("unchecked")
	private static void addSimulationInfoInfoToDatabase(Integer simulationIdInt, String simulatorId) throws UnsupportedOperationException, SQLException {
		SQLContainer simInfoCont = dbHelp.getSimulationInfoContainer();
		SimulationInfoBean simInfoBean = SimulatorsStatus.getSimulationInfoItemBySimulatorId(simulatorId).getBean();
		RowId newInfoId = (RowId) simInfoCont.addItem();
		simInfoCont.getContainerProperty(newInfoId, SimulationInfoCols.simulation_simulationid.toString()).setValue(simulationIdInt);
		simInfoCont.getContainerProperty(newInfoId, SimulationInfoCols.longtitude.toString()).setValue(simInfoBean.getLongtitude());
		simInfoCont.getContainerProperty(newInfoId, SimulationInfoCols.latitude.toString()).setValue(simInfoBean.getLatitude());
		commitChangeInSQLContainer(simInfoCont);

	}

	/*
	 * Saves data about engines state to database
	 */
	@SuppressWarnings("unchecked")
	private static void addEnginesInfoToDatabase(Integer simulationIdInt, String simulatorId) throws UnsupportedOperationException, SQLException {
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
			simEnginesStCont.getContainerProperty(newSimEngStId, SimulationInfoCols.simulation_simulationid.toString()).setValue(
					simulationIdInt);
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
	public static RowId createNewRunningPausedSimulation(SQLContainer lastSimCont, String simulatorId) throws UnsupportedOperationException, SQLException {
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
	public static RowId createNewRunningNotPausedSimulation(SQLContainer lastSimCont, String simulatorId) throws UnsupportedOperationException, SQLException {
		logger.info("Create new RUNNING, NOT PAUSED simulation");
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
	public static void setSimOnPausedState(SQLContainer lastSimCont, Item simulation) throws UnsupportedOperationException, SQLException {
		setSimState(lastSimCont, simulation, SimulatorsStatus.SIMULATION_ON, SimulatorsStatus.SIMULATION_PAUSED);
	}

	/*
	 * Set simulation to RUNNING, NOT PAUSED state
	 */
	public static void setSimOnNotPausedState(SQLContainer lastSimCont, Item simulation) throws UnsupportedOperationException, SQLException {
		setSimState(lastSimCont, simulation, SimulatorsStatus.SIMULATION_ON, SimulatorsStatus.SIMULATION_NOT_PAUSED);
	}
	
	@SuppressWarnings("unchecked")
	private static void setSimState(SQLContainer lastSimCont, Item simulation, boolean statusRun, boolean statusPause) throws UnsupportedOperationException, SQLException {
		logger.info("Set sim state. ACTIVE: {}, PAUSED: {}", statusRun, statusPause);
		simulation.getItemProperty(SimulationCols.issimulationon.toString()).setValue(statusRun);
		simulation.getItemProperty(SimulationCols.issimulationpaused.toString()).setValue(statusPause);
		commitChangeInSQLContainer(lastSimCont);
	}

	/*
	 * Set simulation to OFF, NOT PAUSED state
	 */
	@SuppressWarnings("unchecked")
	public static void setSimOffNotPausedState(SQLContainer lastSimCont, Item lastSim) throws UnsupportedOperationException, SQLException {
		logger.info("Set sim state. ACTIVE: {}, PAUSED: {}", false, false);
		lastSim.getItemProperty(SimulationCols.issimulationon.toString()).setValue(SimulatorsStatus.SIMULATION_OFF);
		lastSim.getItemProperty(SimulationCols.issimulationpaused.toString()).setValue(SimulatorsStatus.SIMULATION_NOT_PAUSED);
		commitChangeInSQLContainer(lastSimCont);
	}
	
	private static void commitChangeInSQLContainer(SQLContainer sqlCont) throws UnsupportedOperationException, SQLException {
		sqlCont.commit();
	}
}
