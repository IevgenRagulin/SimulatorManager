package cm.example.testvaadin.simulatorcommunication;

import java.sql.SQLException;

import com.example.testvaadin.beans.SimulationDevStateBean;
import com.example.testvaadin.beans.SimulationInfoBean;
import com.example.testvaadin.beans.SimulationPFDBean;
import com.example.testvaadin.data.ColumnNames;
import com.example.testvaadin.data.DatabaseHelper;
import com.example.testvaadin.items.SimulationInfoItem;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class DatabaseUpdater {
	private static final double HALF_METER = 0.5;
	private static DatabaseHelper dbHelp = new DatabaseHelper();

	public static void addSimulationInfoToDatabase(SQLContainer lastSimCont,
			String simulatorId, RowId simulationId) {
		Integer simulationIdInt = Integer.valueOf(simulationId.toString());
		if (shouldWeAddSimulationInfoToDatabase(simulatorId)) {
			addDevicesStateInfoToDatabase(simulationIdInt, simulatorId);
			addSimulationInfoToDatabase(simulationIdInt, simulatorId);
			addPfdInfoToDatabase(simulationIdInt, simulatorId);
		}
	}

	private static boolean shouldWeAddSimulationInfoToDatabase(
			String simulatorId) {
		boolean shouldWeAdd = true;
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
					currentLatitude, currentLongtitude) > HALF_METER) {
				shouldWeAdd = true;
			} else {
				shouldWeAdd = false;
			}
		} else {
			System.out
					.println("PREV ITEM IS NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
		}
		return shouldWeAdd;
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

	@SuppressWarnings("unchecked")
	private static void addDevicesStateInfoToDatabase(Integer simulationIdInt,
			String simulatorId) {
		SQLContainer simDevStCont = dbHelp.getSimulationDevicesStateContainer();
		SimulationDevStateBean simDevStBean = SimulatorsStatus
				.getSimulationDevStateItemBySimulatorId(simulatorId).getBean();
		RowId newSimDvStId = (RowId) simDevStCont.addItem();
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getSimulationidForeignKey()).setValue(
				simulationIdInt);
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getElevator()).setValue(simDevStBean.getElevator());
		simDevStCont
				.getContainerProperty(newSimDvStId, ColumnNames.getEleron())
				.setValue(simDevStBean.getEleron());
		simDevStCont
				.getContainerProperty(newSimDvStId, ColumnNames.getRudder())
				.setValue(simDevStBean.getRudder().intValue());
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getThrottle()).setValue(simDevStBean.getThrottle());
		simDevStCont.getContainerProperty(newSimDvStId, ColumnNames.getFlaps())
				.setValue(simDevStBean.getFlaps().intValue());
		simDevStCont.getContainerProperty(newSimDvStId,
				ColumnNames.getSpeedbrakes()).setValue(
				simDevStBean.getSpeedbrakes());
		simDevStCont.getContainerProperty(newSimDvStId, ColumnNames.getTrim())
				.setValue(simDevStBean.getTrim());
		commitChangeInSQLContainer(simDevStCont);
	}

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

	private static void addSimulationInfoToDatabase(Integer simulationIdInt,
			String simulatorId) {
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
	public static void setSimOnPausedState(SQLContainer lastSimCont,
			Item simulation) {
		simulation.getItemProperty(ColumnNames.getIssimulationon()).setValue(
				SimulatorsStatus.SIMULATION_ON);
		simulation.getItemProperty(ColumnNames.getIssimulationpaused())
				.setValue(SimulatorsStatus.SIMULATION_PAUSED);
		commitChangeInSQLContainer(lastSimCont);
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
