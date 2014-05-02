package com.example.testvaadin.simulatorcommunication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.example.testvaadin.beans.AllEngineInfo;
import com.example.testvaadin.beans.AllSimulationInfo;

public class SocketHelper {

	public static AllSimulationInfo getSimulationData(String host, int port) {
		AllSimulationInfo simData = null;
		String query = "GET:DATA:END:";
		Socket queryProcessorSocket = createSocket(host, port);
		PrintWriter out = getPrintWriter(queryProcessorSocket);
		BufferedReader queryProcessorReader = getQueryProcessorReader(queryProcessorSocket);
		out = writeQuery(query, out);
		String response = getResponseFromSocket(queryProcessorReader);
		closeSocket(queryProcessorSocket);
		System.out.println("From " + host + " " + response);
		simData = parseSimulatorResponse(response);

		return simData;
	}

	public static AllEngineInfo getEngineData(String host, int port) {
		AllEngineInfo engineData = null;
		String query = "GET:ENGINE:END:";
		Socket queryProcessorSocket = createSocket(host, port);
		PrintWriter out = getPrintWriter(queryProcessorSocket);
		BufferedReader queryProcessorReader = getQueryProcessorReader(queryProcessorSocket);
		out = writeQuery(query, out);
		String response = getResponseFromSocket(queryProcessorReader);
		closeSocket(queryProcessorSocket);
		System.out.println("From " + host + " engines " + response);
		// if we got some data, parse it
		if ((response != null) && (response.length() > 15)) {
			engineData = parseEngineResponse(response);
		}
		return engineData;
	}

	private static AllEngineInfo parseEngineResponse(String response) {
		AllEngineInfo engineData = null;
		String value = null;
		if (response != null) {
			engineData = new AllEngineInfo();

			// RPM
			value = getValueOf(":E1RPM:", response);
			engineData.setE1rpm(stringToFloat(value));
			value = getValueOf(":E2RPM:", response);
			engineData.setE2rpm(stringToFloat(value));
			value = getValueOf(":E3RPM:", response);
			engineData.setE3rpm(stringToFloat(value));
			value = getValueOf(":E4RPM:", response);
			engineData.setE4rpm(stringToFloat(value));

			// PWR (Watt)
			value = getValueOf(":E1PWR:", response);
			engineData.setE1pwr(stringToFloat(value));
			value = getValueOf(":E2PWR:", response);
			engineData.setE2pwr(stringToFloat(value));
			value = getValueOf(":E3PWR:", response);
			engineData.setE3pwr(stringToFloat(value));
			value = getValueOf(":E4PWR:", response);
			engineData.setE4pwr(stringToFloat(value));

			// PWR percent
			value = getValueOf(":E1PWP:", response);
			engineData.setE1pwp(stringToFloat(value));
			value = getValueOf(":E2PWP:", response);
			engineData.setE2pwp(stringToFloat(value));
			value = getValueOf(":E3PWP:", response);
			engineData.setE3pwp(stringToFloat(value));
			value = getValueOf(":E4PWP:", response);
			engineData.setE4pwp(stringToFloat(value));

			// Manifold pressure
			value = getValueOf(":E1MP_:", response);
			engineData.setE1mp_(stringToFloat(value));
			value = getValueOf(":E2MP_:", response);
			engineData.setE2mp_(stringToFloat(value));
			value = getValueOf(":E3MP_:", response);
			engineData.setE3mp_(stringToFloat(value));
			value = getValueOf(":E4MP_:", response);
			engineData.setE4mp_(stringToFloat(value));

			// EGT1
			value = getValueOf(":E1ET1:", response);
			engineData.setE1et1(stringToFloat(value));
			value = getValueOf(":E2ET1:", response);
			engineData.setE2et1(stringToFloat(value));
			value = getValueOf(":E3ET1:", response);
			engineData.setE3et1(stringToFloat(value));
			value = getValueOf(":E4ET1:", response);
			engineData.setE4et1(stringToFloat(value));

			// EGT2
			value = getValueOf(":E1ET2:", response);
			engineData.setE1et2(stringToFloat(value));
			value = getValueOf(":E2ET2:", response);
			engineData.setE2et2(stringToFloat(value));
			value = getValueOf(":E3ET2:", response);
			engineData.setE3et2(stringToFloat(value));
			value = getValueOf(":E4ET2:", response);
			engineData.setE4et2(stringToFloat(value));

			// CHT1
			value = getValueOf(":E1CT1:", response);
			engineData.setE1ct1(stringToFloat(value));
			value = getValueOf(":E2CT1:", response);
			engineData.setE2ct1(stringToFloat(value));
			value = getValueOf(":E3CT1:", response);
			engineData.setE3ct1(stringToFloat(value));
			value = getValueOf(":E4CT1:", response);
			engineData.setE4ct1(stringToFloat(value));

			// CHT2
			value = getValueOf(":E1CT2:", response);
			engineData.setE1ct2(stringToFloat(value));
			value = getValueOf(":E2CT2:", response);
			engineData.setE2ct2(stringToFloat(value));
			value = getValueOf(":E3CT2:", response);
			engineData.setE3ct2(stringToFloat(value));
			value = getValueOf(":E4CT2:", response);
			engineData.setE4ct2(stringToFloat(value));

			// Suction temperature
			value = getValueOf(":E1EST:", response);
			engineData.setE1est(stringToFloat(value));
			value = getValueOf(":E2EST:", response);
			engineData.setE2est(stringToFloat(value));
			value = getValueOf(":E3EST:", response);
			engineData.setE3est(stringToFloat(value));
			value = getValueOf(":E4EST:", response);
			engineData.setE4est(stringToFloat(value));

			// Fuel flow
			value = getValueOf(":E1FF_:", response);
			engineData.setE1ff_(stringToFloat(value));
			value = getValueOf(":E2FF_:", response);
			engineData.setE2ff_(stringToFloat(value));
			value = getValueOf(":E3FF_:", response);
			engineData.setE3ff_(stringToFloat(value));
			value = getValueOf(":E4FF_:", response);
			engineData.setE4ff_(stringToFloat(value));

			// Fuel pressure
			value = getValueOf(":E1FP_:", response);
			engineData.setE1fp_(stringToFloat(value));
			value = getValueOf(":E2FP_:", response);
			engineData.setE2fp_(stringToFloat(value));
			value = getValueOf(":E3FP_:", response);
			engineData.setE3fp_(stringToFloat(value));
			value = getValueOf(":E4FP_:", response);
			engineData.setE4fp_(stringToFloat(value));

			// Oil pressure
			value = getValueOf(":E1OP_:", response);
			engineData.setE1op_(stringToFloat(value));
			value = getValueOf(":E2OP_:", response);
			engineData.setE2op_(stringToFloat(value));
			value = getValueOf(":E3OP_:", response);
			engineData.setE3op_(stringToFloat(value));
			value = getValueOf(":E4OP_:", response);
			engineData.setE4op_(stringToFloat(value));

			// Oil temperature
			value = getValueOf(":E1OT_:", response);
			engineData.setE1ot_(stringToFloat(value));
			value = getValueOf(":E2OT_:", response);
			engineData.setE2ot_(stringToFloat(value));
			value = getValueOf(":E3OT_:", response);
			engineData.setE3ot_(stringToFloat(value));
			value = getValueOf(":E4OT_:", response);
			engineData.setE4ot_(stringToFloat(value));

			// N1 - jet engine
			value = getValueOf(":E1N1_:", response);
			engineData.setE1n1_(stringToFloat(value));
			value = getValueOf(":E2N1_:", response);
			engineData.setE2n1_(stringToFloat(value));
			value = getValueOf(":E3N1_:", response);
			engineData.setE3n1_(stringToFloat(value));
			value = getValueOf(":E4N1_:", response);
			engineData.setE4n1_(stringToFloat(value));

			// N2 - jet engine
			value = getValueOf(":E2N2_:", response);
			engineData.setE1n2_(stringToFloat(value));
			value = getValueOf(":E2N2_:", response);
			engineData.setE2n2_(stringToFloat(value));
			value = getValueOf(":E3N2_:", response);
			engineData.setE3n2_(stringToFloat(value));
			value = getValueOf(":E4N2_:", response);
			engineData.setE4n2_(stringToFloat(value));

			// Vibration gauch - jet engine
			value = getValueOf(":E1VIB:", response);
			engineData.setE1vib(stringToFloat(value));
			value = getValueOf(":E2VIB:", response);
			engineData.setE2vib(stringToFloat(value));
			value = getValueOf(":E3VIB:", response);
			engineData.setE3vib(stringToFloat(value));
			value = getValueOf(":E4VIB:", response);
			engineData.setE4vib(stringToFloat(value));

			// Voltage. Electric motor - system voltage
			value = getValueOf(":E1VLT:", response);
			engineData.setE1vlt(stringToFloat(value));
			value = getValueOf(":E2VLT:", response);
			engineData.setE2vlt(stringToFloat(value));
			value = getValueOf(":E3VLT:", response);
			engineData.setE3vlt(stringToFloat(value));
			value = getValueOf(":E4VLT:", response);
			engineData.setE4vlt(stringToFloat(value));

			// Current.
			value = getValueOf(":E1AMP:", response);
			engineData.setE1amp(stringToFloat(value));
			value = getValueOf(":E2AMP:", response);
			engineData.setE2amp(stringToFloat(value));
			value = getValueOf(":E3AMP:", response);
			engineData.setE3amp(stringToFloat(value));
			value = getValueOf(":E4AMP:", response);
			engineData.setE4amp(stringToFloat(value));

		}
		return engineData;
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
		if ((a = response.indexOf(keyName)) != -1) {
			a += keyNameLength;
			b = response.indexOf(":", a);
			value = response.substring(a, b);
		} else {
			// System.out.println("KEY NOT FOUND " + keyName);
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

	private static Float stringToFloat(String strVal) {
		if (strVal != null) {
			return Float.parseFloat(strVal);
		} else {
			return null;
		}
	}

	private static Integer doubleToInt(Double dblVal) {
		if (dblVal != null) {
			return dblVal.intValue();
		} else {
			return null;
		}
	}

	private static Boolean isSimulationPaused(Integer intValue) {
		if (intValue == null)
			throw new IllegalArgumentException(
					"Simulator doesn't send information about its Paused or not status");
		return intValue == 0;
	}

	private static Double metersPerSecondToKts(Double metersPerSecond) {
		return metersPerSecond * 1.94;
	}

	private static Double metersToFeet(Double meters) {
		return meters * 3.2808;
	}

	private static Double metersPerSecondToFeetPerMin(Double metersPerSecond) {
		return metersPerSecond * 196.850;
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
			simData.setAltitude_corrected(metersToFeet(stringToDouble(value)));

			// Altitude standard
			value = getValueOf(":ALTS:", response);
			simData.setAltitude_standard(metersToFeet(stringToDouble(value)));

			// Ground altitude
			value = getValueOf(":GAL:", response);
			simData.setGroundAltitude(metersToFeet(stringToDouble(value)));

			// IAS
			value = getValueOf(":IAS:", response);
			simData.setIAS(metersPerSecondToKts(stringToDouble(value)));

			// TAS
			value = getValueOf(":TAS:", response);
			simData.setTAS(metersPerSecondToKts(stringToDouble(value)));

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
			simData.setVS(metersPerSecondToFeetPerMin(stringToDouble(value)));

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
			value = getValueOf(":CA_:", response);
			simData.setAileron(stringToDouble(value));
			// elevator
			value = getValueOf(":CE_:", response);
			simData.setElevator(stringToDouble(value));
			// rudder
			value = getValueOf(":CR_:", response);
			simData.setRudder(stringToDouble(value));

			// elevator trim
			value = getValueOf(":DTE:", response);
			simData.setTrimElevatorPosition(stringToDouble(value));

			// aileron trim
			value = getValueOf(":DTA:", response);
			simData.setTrimAileronPosition(stringToDouble(value));

			// rudder trim
			value = getValueOf(":DTR:", response);
			simData.setTrimRudderPosition(stringToDouble(value));

			// ///////////////////////////////////////////////////////
			// SWITCHES POSITIONS
			// ///////////////////////////////////////////////////////
			// FLP Flaps
			value = getValueOf(":FLP:", response);
			simData.setFlaps_status(stringToDouble(value));

			// Speed brakes
			value = getValueOf(":SBRK:", response);
			simData.setSpeed_brakes(stringToDouble(value));

			// Brakes
			value = getValueOf(":BRK:", response);
			simData.setBrakes_status(brakesDoubleToBoolean(value));

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

			// Landing gears 1, 2, 3
			value = getValueOf(":LG_1:", response);
			simData.setLanding_gear_1_status(doubleToInt(stringToDouble(value)));
			value = getValueOf(":LG_2:", response);
			simData.setLanding_gear_2_status(doubleToInt(stringToDouble(value)));
			value = getValueOf(":LG_3:", response);
			simData.setLanding_gear_3_status(doubleToInt(stringToDouble(value)));
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

			// Simulation paused
			value = getValueOf(":SSPD:", response);
			Boolean isSimPaused = isSimulationPaused(doubleToInt(stringToDouble(value)));
			simData.setSimulationPaused(isSimPaused);
		}
		return simData;
	}

	public static boolean brakesDoubleToBoolean(String value) {
		Double doubleValue = Double.parseDouble(value);
		return (doubleValue > 0.01);
	}

	public static boolean stringToBoolean(String value) {
		char firstChar = value.charAt(0);
		return (firstChar == '1');
	}

}
