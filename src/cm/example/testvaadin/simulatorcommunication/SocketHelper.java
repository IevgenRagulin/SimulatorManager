package cm.example.testvaadin.simulatorcommunication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHelper {

	public static AllSimulationInfo getSimulationData(String host, int port) {
		AllSimulationInfo simData = null;
		String query = "GET:DATA:END:";
		Socket queryProcessorSocket = createSocket(host, port);
		PrintWriter out = getPrintWriter(queryProcessorSocket);
		BufferedReader queryProcessorReader = getQueryProcessorReader(queryProcessorSocket);
		out = writeQuery(query, out);
		String response = getResponseFromSocket(queryProcessorReader);
		System.out.println(response + " from " + host);
		closeSocket(queryProcessorSocket);
		simData = parseSimulatorResponse(response);
		return simData;
	}

	private static PrintWriter getPrintWriter(Socket queryProcessorSocket) {
		PrintWriter out = null;
		DataOutputStream dataOutputStream = getDataOutputStream(queryProcessorSocket);
		if (dataOutputStream != null) {
			out = new PrintWriter(dataOutputStream, true);
		}
		return out;
	}

	private static Socket createSocket(String host, int port) {
		Socket queryProcessorSocket = null;
		try {
			queryProcessorSocket = new Socket(host, port);
		} catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("Cant creat socket for host: " + host);
		}
		return queryProcessorSocket;
	}

	private static DataOutputStream getDataOutputStream(Socket socket) {
		DataOutputStream queryProcessorDos = null;
		if (socket != null) {
			try {
				queryProcessorDos = new DataOutputStream(
						socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return queryProcessorDos;
	}

	private static BufferedReader getQueryProcessorReader(Socket socket) {
		BufferedReader queryProcessorReader = null;
		if (socket != null) {
			try {
				queryProcessorReader = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return queryProcessorReader;
	}

	private static PrintWriter writeQuery(String query, PrintWriter out) {
		if (out != null) {
			out.println(query);
		}
		return out;
	}

	private static String getResponseFromSocket(
			BufferedReader queryProcessorReader) {
		String response = null;
		if (queryProcessorReader != null) {
			try {
				response = queryProcessorReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	private static void closeSocket(Socket socket) {
		if ((socket != null) && (!socket.isClosed())) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static String getValueOf(String keyName, String response) {
		int a = 0;
		int b = 0;
		String value = null;
		int keyNameLength = keyName.length();
		if ((a = response.indexOf(keyName) + keyNameLength) != 4) {
			b = response.indexOf(":", a);
			value = response.substring(a, b);
		} else {
			System.out.println("KEY NOT FOUND " + keyName);
		}
		return value;
	}

	private static Double stringToDouble(String strVal) {
		if (strVal != null) {
			return Double.parseDouble(strVal);
		} else {
			return null;
		}
	}

	private static AllSimulationInfo parseSimulatorResponse(String response) {
		AllSimulationInfo simData = null;
		String value = null;
		if (response != null) {
			simData = new AllSimulationInfo();
			// Longtitude
			value = getValueOf(":LON:", response);
			simData.setLongitude(stringToDouble(value));

			// Latitude
			value = getValueOf(":LAT:", response);
			simData.setLatitude(stringToDouble(value));

			// Pitch
			value = getValueOf(":PIT:", response);
			simData.setPitch(stringToDouble(value));

			// Bank
			value = getValueOf(":BAN:", response);
			simData.setBank(stringToDouble(value));

			// Heading
			value = getValueOf(":HEA:", response);
			simData.setHeading(stringToDouble(value));

			// Alpha
			value = getValueOf(":ALP:", response);
			simData.setAlpha(stringToDouble(value));

			// Beta
			value = getValueOf(":BET:", response);
			simData.setBeta(stringToDouble(value));

			// Altitude corrected
			value = getValueOf(":ALT:", response);
			simData.setAltitude_corrected(stringToDouble(value));

			// Altitude standard
			value = getValueOf(":ALTS:", response);
			simData.setAltitude_standard(stringToDouble(value));

			// Ground altitude
			value = getValueOf(":GAL:", response);
			simData.setGroundAltitude(stringToDouble(value));

			// IAS
			value = getValueOf(":IAS:", response);
			simData.setIAS(stringToDouble(value));

			// TAS
			value = getValueOf(":TAS:", response);
			simData.setTAS(stringToDouble(value));

			// PWR
			value = getValueOf(":PWR:", response);
			simData.setEngine_pwr(stringToDouble(value));

			// RPM
			value = getValueOf(":RPM:", response);
			simData.setEngine_rpm(stringToDouble(value));

			// MP
			value = getValueOf(":MP_:", response);
			simData.setEngine_manifold_pressure(stringToDouble(value));

			// VS
			value = getValueOf(":VS_:", response);
			simData.setVS(stringToDouble(value));

			// fflow
			value = getValueOf(":FFL:", response);
			simData.setEngine_fuel_flow(stringToDouble(value));

			// GS
			value = getValueOf(":GS_:", response);
			simData.setGS(stringToDouble(value));

			// temperature
			value = getValueOf(":TMP:", response);
			simData.setTemperature(stringToDouble(value));

			// barometricPressure
			value = getValueOf(":BRP:", response);
			simData.setStatic_pressure(stringToDouble(value));
			// QNH
			value = getValueOf(":QNH:", response);
			simData.setQNH(stringToDouble(value));

			// angleOfSideSlip
			value = getValueOf(":AOS:", response);
			simData.setAngleOfSideSlip(stringToDouble(value));

			// Left fuel
			value = getValueOf(":LFU:", response);
			simData.setLfuel(stringToDouble(value));

			// Right fuel
			value = getValueOf(":RFU:", response);
			simData.setRfuel(stringToDouble(value));

			// Oil Pressure
			value = getValueOf(":OPR:", response);
			simData.setEngine_oil_pressure(stringToDouble(value));

			// Oil Temperature
			value = getValueOf(":OTM:", response);
			simData.setEngine_oil_pressure(stringToDouble(value));

			// Time
			value = getValueOf(":TIM:", response);
			simData.setTime(stringToDouble(value));

			// P
			value = getValueOf(":BRR:", response);
			simData.setP(stringToDouble(value));

			// Q
			value = getValueOf(":BPR:", response);
			simData.setQ(Double.parseDouble(value));

			// R
			value = getValueOf(":BYR:", response);
			simData.setR(stringToDouble(value));

			// Local AX
			value = getValueOf(":BAX:", response);
			simData.setLocal_ax(stringToDouble(value));

			// Local AY
			value = getValueOf(":BAY:", response);
			simData.setLocal_ay(stringToDouble(value));

			// Local AZ
			value = getValueOf(":BAZ:", response);
			simData.setLocal_az(stringToDouble(value));

			// True Track
			value = getValueOf(":TT_:", response);
			simData.setTT(stringToDouble(value));

			// ////////////
			// DEVICES STATE
			// ////////////
			// aileron
			value = getValueOf(":DA_:", response);
			simData.setAileron(stringToDouble(value));
			// elevator
			value = getValueOf(":DE_:", response);
			simData.setElevator(stringToDouble(value));
			// rudder
			value = getValueOf(":DR_:", response);
			simData.setRudder(stringToDouble(value));
			// trim
			value = getValueOf(":DTE:", response);
			simData.setTrimPosition(stringToDouble(value));

			// ///////////////////////////////////////////////////////
			// SWITCHES POSITIONS
			// ///////////////////////////////////////////////////////
			// FLP Flaps
			value = getValueOf(":FLP:", response);
			simData.setFlaps_status(stringToDouble(value));

			// Brakes
			value = getValueOf(":BRK:", response);
			simData.setBrakes_status(stringToDouble(value));

			// Switch Master switch
			value = getValueOf(":SWMA:", response);
			simData.setSw_status_master_switch(stringToBoolean(value));

			// Switch Accumulator switch
			value = getValueOf(":SWAC:", response);
			simData.setSw_status_accu(stringToBoolean(value));

			// Switch Generator switch
			value = getValueOf(":SWGE:", response);
			simData.setSw_status_gen(stringToBoolean(value));

			// Switch Avionic switch
			value = getValueOf(":SWAV:", response);
			simData.setSw_status_avionic_switch(stringToBoolean(value));

			// Switch Efis switch
			value = getValueOf(":SWEF:", response);
			simData.setSw_status_efis(stringToBoolean(value));

			// Switch Ignition
			value = getValueOf(":SWIG:", response);
			simData.setIgnition(stringToDouble(value).intValue());

			// ///////////////////////////////////////////////////////
			// AUTOPILOT TARGET VALUES
			// ///////////////////////////////////////////////////////
			// Autopilot IAS
			value = getValueOf(":APIAS:", response);
			simData.setAp_ias(stringToDouble(value));

			// Autopilot Altitude
			value = getValueOf(":APALT:", response);
			simData.setAp_alt(stringToDouble(value));

			// Autopilot Heading Angle. COMMENTED THIS, BECAUSE SIMULATOR DOESNT
			// SEND THIS VALUE
			// value = getValueOf(":APHEA:", response);
			// simData.setAp_hea(stringToDouble(value));

			// Autopilot True Track
			value = getValueOf(":APTT_:", response);
			simData.setAp_tt(stringToDouble(value));

			// Autopilot Vertical speed
			value = getValueOf(":APVS_:", response);
			simData.setAp_vs(stringToDouble(value));

			// Autopilot Mode
			value = getValueOf(":APMOD:", response);
			simData.setAp_mode(stringToDouble(value).intValue());

			// ///////////////////////////////////////////////////////
			// ENGINE
			// ///////////////////////////////////////////////////////
			// AWCom v1.1
			// Throttle
			value = getValueOf(":THR:", response);
			simData.setThrottle(stringToDouble(value));

			// EGT1
			value = getValueOf(":EGT1:", response);
			simData.setEngine_exh_gas_temp1(stringToDouble(value));

			// EGT2
			value = getValueOf(":EGT2:", response);
			simData.setEngine_exh_gas_temp2(stringToDouble(value));

			// CHT1
			value = getValueOf(":CHT1:", response);
			simData.setEngine_exh_gas_temp2(stringToDouble(value));

			// CHT2
			value = getValueOf(":CHT2:", response);
			simData.setEngine_exh_gas_temp2(stringToDouble(value));

			// BATV
			value = getValueOf(":BATV:", response);
			simData.setBattery_voltage(stringToDouble(value));

			// BATA
			value = getValueOf(":BATA:", response);
			simData.setBattery_amperage(stringToDouble(value));

			// FPR
			value = getValueOf(":FPR:", response);
			simData.setEngine_fuel_pressure(stringToDouble(value));

			// EST
			value = getValueOf(":EST:", response);
			simData.setEngine_suction_temp(stringToDouble(value));

			// DP
			value = getValueOf(":DP_:", response);
			simData.setDynamic_pressure(stringToDouble(value));

			// SP
			value = getValueOf(":SP_:", response);
			simData.setStatic_pressure(stringToDouble(value));

			// MP
			value = getValueOf(":MP_:", response);
			simData.setEngine_manifold_pressure(stringToDouble(value));
		}
		return simData;
	}

	public static boolean stringToBoolean(String value) {
		char firstChar = value.charAt(0);
		return (firstChar == '1');
	}

}
