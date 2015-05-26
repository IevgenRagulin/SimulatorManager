package cz.vutbr.fit.simulatormanager.simulatorcommunication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vutbr.fit.simulatormanager.beans.AllEngineInfo;
import cz.vutbr.fit.simulatormanager.beans.AllSimulationInfo;
import cz.vutbr.fit.simulatormanager.util.ConverterUtil;

/**
 * Class for parsing data received from simulators
 * 
 * @author zhenia
 *
 */
public class AWComParser {

    final static Logger LOG = LoggerFactory.getLogger(AWComParser.class);

    public static AllSimulationInfo parseSimulatorResponse(String response) {
	AllSimulationInfo simData = null;
	String value = null;
	LOG.info("Going to parse simulator response: {}", response);
	if (response != null) {
	    simData = new AllSimulationInfo();
	    // Longtitude
	    value = getValueOf(":LON:", response);
	    simData.setLongitude(ConverterUtil.stringToDouble(value));

	    // Latitude
	    value = getValueOf(":LAT:", response);
	    simData.setLatitude(ConverterUtil.stringToDouble(value));

	    // Pitch
	    value = getValueOf(":PIT:", response);
	    simData.setPitch(ConverterUtil.stringToDouble(value));

	    // Bank
	    value = getValueOf(":BAN:", response);
	    simData.setBank(ConverterUtil.stringToDouble(value));

	    // Heading
	    value = getValueOf(":HEA:", response);
	    simData.setHeading(ConverterUtil.stringToDouble(value));

	    // Alpha. Not used
	    // value = getValueOf(":ALP:", response);
	    // simData.setAlpha(ConverterUtil.stringToDouble(value));

	    // Beta. Not used
	    // value = getValueOf(":BET:", response);
	    // simData.setBeta(ConverterUtil.stringToDouble(value));

	    // Altitude corrected
	    // value = getValueOf(":ALT:", response);
	    // simData.setAltitude_corrected(metersToFeet(ConverterUtil.stringToDouble(value)));

	    // Altitude standard
	    value = getValueOf(":ALTS:", response);
	    simData.setAltitude_standard(metersToFeet(ConverterUtil.stringToDouble(value)));

	    // Ground altitude
	    value = getValueOf(":GAL:", response);
	    simData.setGroundAltitude(metersToFeet(ConverterUtil.stringToDouble(value)));

	    // IAS
	    value = getValueOf(":IAS:", response);
	    simData.setIAS(metersPerSecondToKts(ConverterUtil.stringToDouble(value)));

	    // TAS
	    value = getValueOf(":TAS:", response);
	    simData.setTAS(metersPerSecondToKts(ConverterUtil.stringToDouble(value)));

	    // PWR. Not used any more, see parseEngineResponse
	    // value = getValueOf(":PWR:", response);
	    // simData.setEngine_pwr(ConverterUtil.stringToDouble(value));

	    // RPM. Not used any more, see parseEngineResponse
	    // value = getValueOf(":RPM:", response);
	    // simData.setEngine_rpm(ConverterUtil.stringToDouble(value));

	    // MP. Not used any more, see parseEngineResponse
	    // value = getValueOf(":MP_:", response);
	    // simData.setEngine_manifold_pressure(ConverterUtil.stringToDouble(value));

	    // VS
	    value = getValueOf(":VS_:", response);
	    simData.setVS(metersPerSecondToFeetPerMin(ConverterUtil.stringToDouble(value)));

	    // fflow. Not used any more, see parseEngineResponse
	    // value = getValueOf(":FFL:", response);
	    // simData.setEngine_fuel_flow(ConverterUtil.stringToDouble(value));

	    // GS
	    value = getValueOf(":GS_:", response);
	    simData.setGS(ConverterUtil.stringToDouble(value));

	    // temperature
	    value = getValueOf(":TMP:", response);
	    simData.setTemperature(ConverterUtil.stringToDouble(value));

	    // barometricPressure. Not sure if this is correct and if we need
	    // this
	    // value = getValueOf(":BRP:", response);
	    // simData.setStatic_pressure(stringToDouble(value));
	    // QNH
	    value = getValueOf(":QNH:", response);
	    simData.setQNH(ConverterUtil.stringToDouble(value));

	    // angleOfSideSlip. Not used
	    // value = getValueOf(":AOS:", response);
	    // simData.setAngleOfSideSlip(ConverterUtil.stringToDouble(value));

	    // Left fuel
	    value = getValueOf(":LFU:", response);
	    simData.setLfuel(ConverterUtil.stringToFloat(value));

	    // Right fuel
	    value = getValueOf(":RFU:", response);
	    simData.setRfuel(ConverterUtil.stringToFloat(value));

	    // Central fuel
	    value = getValueOf(":CFU:", response);
	    simData.setCfuel(ConverterUtil.stringToFloat(value));

	    // Oil Pressure. Not used any more, see parseEngineResponse
	    // value = getValueOf(":OPR:", response);
	    // simData.setEngine_oil_pressure(ConverterUtil.stringToDouble(value));

	    // Oil Temperature. Not used any more, see parseEngineResponse
	    // value = getValueOf(":OTM:", response);
	    // simData.setEngine_oil_pressure(ConverterUtil.stringToDouble(value));

	    // Time
	    value = getValueOf(":TIM:", response);
	    simData.setTime(ConverterUtil.stringToDouble(value));

	    // P. Not used
	    value = getValueOf(":BRR:", response);
	    simData.setP(ConverterUtil.stringToDouble(value));

	    // Q. Not used
	    // value = getValueOf(":BPR:", response);
	    // simData.setQ(Double.parseDouble(value));

	    // R. Not used
	    // value = getValueOf(":BYR:", response);
	    // simData.setR(ConverterUtil.stringToDouble(value));

	    // Local AX. Not used
	    // value = getValueOf(":BAX:", response);
	    // simData.setLocal_ax(ConverterUtil.stringToDouble(value));

	    // Local AY. Not used
	    // value = getValueOf(":BAY:", response);
	    // simData.setLocal_ay(ConverterUtil.stringToDouble(value));

	    // Local AZ. Not used
	    // value = getValueOf(":BAZ:", response);
	    // simData.setLocal_az(ConverterUtil.stringToDouble(value));

	    // True Track
	    value = getValueOf(":TT_:", response);
	    simData.setTT(ConverterUtil.stringToDouble(value));

	    // ////////////
	    // DEVICES STATE
	    // ////////////
	    // aileron
	    value = getValueOf(":CA_:", response);
	    simData.setAileron(ConverterUtil.stringToDouble(value));
	    // elevator
	    value = getValueOf(":CE_:", response);
	    simData.setElevator(ConverterUtil.stringToDouble(value));
	    // rudder
	    value = getValueOf(":CR_:", response);
	    simData.setRudder(ConverterUtil.stringToDouble(value));

	    // elevator trim
	    value = getValueOf(":DTE:", response);
	    simData.setTrimElevatorPosition(ConverterUtil.stringToDouble(value));

	    // aileron trim
	    value = getValueOf(":DTA:", response);
	    simData.setTrimAileronPosition(ConverterUtil.stringToDouble(value));

	    // rudder trim
	    value = getValueOf(":DTR:", response);
	    simData.setTrimRudderPosition(ConverterUtil.stringToDouble(value));

	    // ///////////////////////////////////////////////////////
	    // SWITCHES POSITIONS
	    // ///////////////////////////////////////////////////////
	    // FLP Flaps
	    value = getValueOf(":FLP:", response);
	    simData.setFlaps_status(ConverterUtil.stringToDouble(value));

	    // Speed brakes
	    value = getValueOf(":SBRK:", response);
	    simData.setSpeed_brakes(ConverterUtil.stringToDouble(value));

	    // Brakes
	    value = getValueOf(":BRK:", response);
	    simData.setBrakes_status(brakesDoubleToBoolean(value));

	    // Switch Master switch. Not used
	    // value = getValueOf(":SWMA:", response);
	    // simData.setSw_status_master_switch(ConverterUtil.stringToBoolean(value));

	    // Switch Accumulator switch. Not used
	    // value = getValueOf(":SWAC:", response);
	    // simData.setSw_status_accu(ConverterUtil.stringToBoolean(value));

	    // Switch Generator switch. Not used
	    // value = getValueOf(":SWGE:", response);
	    // simData.setSw_status_gen(ConverterUtil.stringToBoolean(value));

	    // Switch Avionic switch. Not used
	    // value = getValueOf(":SWAV:", response);
	    // simData.setSw_status_avionic_switch(ConverterUtil.stringToBoolean(value));

	    // Switch Efis switch. Not used
	    // value = getValueOf(":SWEF:", response);
	    // simData.setSw_status_efis(stringToBoolean(value));

	    // Switch Ignition. Not used
	    // value = getValueOf(":SWIG:", response);
	    // simData.setIgnition(stringToDouble(value).intValue());

	    // Landing gears 1, 2, 3
	    value = getValueOf(":LG_1:", response);
	    simData.setLanding_gear_1_status(doubleToInt(ConverterUtil.stringToDouble(value)));
	    value = getValueOf(":LG_2:", response);
	    simData.setLanding_gear_2_status(doubleToInt(ConverterUtil.stringToDouble(value)));
	    value = getValueOf(":LG_3:", response);
	    simData.setLanding_gear_3_status(doubleToInt(ConverterUtil.stringToDouble(value)));
	    // ///////////////////////////////////////////////////////
	    // AUTOPILOT TARGET VALUES
	    // ///////////////////////////////////////////////////////
	    // Autopilot IAS. Not used.
	    // value = getValueOf(":APIAS:", response);
	    // simData.setAp_ias(stringToDouble(value));

	    // Autopilot Altitude. Not used.
	    // value = getValueOf(":APALT:", response);
	    // simData.setAp_alt(stringToDouble(value));

	    // Autopilot Heading Angle. COMMENTED THIS, BECAUSE SIMULATOR DOESNT
	    // SEND THIS VALUE
	    // value = getValueOf(":APHEA:", response);
	    // simData.setAp_hea(stringToDouble(value));

	    // Autopilot True Track. Not used.
	    // value = getValueOf(":APTT_:", response);
	    // simData.setAp_tt(stringToDouble(value));

	    // Autopilot Vertical speed. Not used
	    // value = getValueOf(":APVS_:", response);
	    // simData.setAp_vs(stringToDouble(value));

	    // Autopilot Mode. Not used
	    // value = getValueOf(":APMOD:", response);
	    // simData.setAp_mode(ConverterUtil.stringToDouble(value).intValue());

	    // ///////////////////////////////////////////////////////
	    // ENGINE
	    // ///////////////////////////////////////////////////////
	    // AWCom v1.1
	    // Throttle. Not used
	    // value = getValueOf(":THR:", response);
	    // simData.setThrottle(stringToDouble(value));

	    // EGT1. Not used any more, see parseEngineResponse
	    value = getValueOf(":EGT1:", response);
	    simData.setEngine_exh_gas_temp1(ConverterUtil.stringToDouble(value));

	    // EGT2. Not used any more, see parseEngineResponse
	    // value = getValueOf(":EGT2:", response);
	    // simData.setEngine_exh_gas_temp2(ConverterUtil.stringToDouble(value));

	    // CHT1. Not used any more, see parseEngineResponse
	    // value = getValueOf(":CHT1:", response);
	    // simData.setEngine_exh_gas_temp2(ConverterUtil.stringToDouble(value));

	    // CHT2. Not used any more, see parseEngineResponse
	    // value = getValueOf(":CHT2:", response);
	    // simData.setEngine_exh_gas_temp2(ConverterUtil.stringToDouble(value));

	    // BATV. Not used
	    // value = getValueOf(":BATV:", response);
	    // simData.setBattery_voltage(stringToDouble(value));

	    // BATA. Not used
	    // value = getValueOf(":BATA:", response);
	    // simData.setBattery_amperage(stringToDouble(value));

	    // FPR. Not used
	    // value = getValueOf(":FPR:", response);
	    // simData.setEngine_fuel_pressure(stringToDouble(value));

	    // EST. Not used
	    // value = getValueOf(":EST:", response);
	    // simData.setEngine_suction_temp(stringToDouble(value));

	    // DP. Not used
	    // value = getValueOf(":DP_:", response);
	    // simData.setDynamic_pressure(stringToDouble(value));

	    // SP. Not used
	    // value = getValueOf(":SP_:", response);
	    // simData.setStatic_pressure(stringToDouble(value));

	    // MP. Not used
	    // value = getValueOf(":MP_:", response);
	    // simData.setEngine_manifold_pressure(stringToDouble(value));

	    // Simulation paused
	    value = getValueOf(":SSPD:", response);
	    Boolean isSimPaused = isSimulationPaused(doubleToInt(ConverterUtil.stringToDouble(value)));
	    simData.setSimulationPaused(isSimPaused);
	}
	return simData;
    }

    /**
     * @param engineNumber
     *            number of the engine for which we build the key
     * @param key
     *            example: RPM
     * @return example: E1RPM
     */
    public static String buildEngineKey(int engineNumber, String key) {
	return new StringBuilder(8).append(":E").append(engineNumber).append(key).append(":").toString();
    }

    /**
     * @param key
     *            example: RPM
     * @return example: :RPM:
     */
    public static String buildSimulationKey(String key) {
	return new StringBuilder(8).append(":").append(key).append(":").toString();
    }

    public static AllEngineInfo parseEngineResponse(String response) {
	AllEngineInfo engineData = null;
	Float value = null;
	LOG.debug("Going to parse engine response: {}", response);
	if (response != null) {
	    engineData = new AllEngineInfo();
	    int numOfEngines = Integer.valueOf(getValueOf(":E0CNT:", response));
	    engineData.setNumberOfEngines(numOfEngines);
	    for (int i = 0; i < numOfEngines; i++) {
		// RPM
		value = getValueForEngineKey(i, "RPM", response);
		engineData.setRpm(i, value);
		// Power (Watt)
		value = getValueForEngineKey(i, "PWR", response);
		engineData.setPwr(i, value);
		// Power (Percent)
		value = getValueForEngineKey(i, "PWP", response);
		engineData.setPwp(i, value);
		// Manifold pressure
		value = getValueForEngineKey(i, "MP_", response);
		engineData.setMp_(i, value);
		// EGT1
		value = getValueForEngineKey(i, "ET1", response);
		engineData.setEt1(i, value);
		// EGT2
		value = getValueForEngineKey(i, "ET2", response);
		engineData.setEt2(i, value);
		// CHT1
		value = getValueForEngineKey(i, "CT1", response);
		engineData.setCt1(i, value);
		// CHT1
		value = getValueForEngineKey(i, "CT2", response);
		engineData.setCt2(i, value);
		// Suction temperature
		value = getValueForEngineKey(i, "EST", response);
		engineData.setEst(i, value);
		// Fuel flow
		value = getValueForEngineKey(i, "FF_", response);
		engineData.setFf_(i, value);
		// Fuel pressure
		value = getValueForEngineKey(i, "FP_", response);
		engineData.setFp_(i, value);
		// Oil pressure
		value = getValueForEngineKey(i, "OP_", response);
		engineData.setOp_(i, value);
		// Oil temperature
		value = getValueForEngineKey(i, "OT_", response);
		engineData.setOt_(i, value);
		// N1
		value = getValueForEngineKey(i, "N1_", response);
		engineData.setN1_(i, value);
		// N2
		value = getValueForEngineKey(i, "N2_", response);
		engineData.setN2_(i, value);
		// Vibration gauch - jet engine
		value = getValueForEngineKey(i, "VIB", response);
		engineData.setVib(i, value);
		// Voltage. Electric motor - system voltage
		value = getValueForEngineKey(i, "VLT", response);
		engineData.setVlt(i, value);
		// Current
		value = getValueForEngineKey(i, "AMP", response);
		engineData.setAmp(i, value);
	    }
	}
	return engineData;
    }

    private static Integer doubleToInt(Double dblVal) {
	if (dblVal != null) {
	    return dblVal.intValue();
	}
	return null;

    }

    private static Boolean isSimulationPaused(Integer intValue) {
	if (intValue == null)
	    throw new IllegalArgumentException("Simulator doesn't send information about its Paused or not status");
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

    /**
     * 
     * @param keyName
     *            example: AMP
     * @param engineNum
     *            example: 0
     * @param response
     *            The string response from AWCom
     * @return the retrieved value. Example: 123.2
     */
    public static Float getValueForEngineKey(int engineNum, String keyName, String response) {
	String value = getValueOf(buildEngineKey(engineNum, keyName), response);
	return ConverterUtil.stringToFloat(value);
    }

    public static Float getValueForSimulationKey(String keyName, String responseFromAwCom) {
	String value = getValueOf(buildSimulationKey(keyName), responseFromAwCom);
	return ConverterUtil.stringToFloat(value);
    }

    public static String getValueOf(String keyName, String response) {
	int a = 0;
	int b = 0;
	String value = null;
	int keyNameLength = keyName.length();
	if ((a = response.indexOf(keyName)) != -1) {
	    a += keyNameLength;
	    b = response.indexOf(":", a);
	    value = response.substring(a, b);
	} else {
	    LOG.error("KEY NOT FOUND: {}", keyName);
	}
	return value;
    }

    public static boolean brakesDoubleToBoolean(String value) {
	Double doubleValue = Double.parseDouble(value);
	return (doubleValue > 0.01);
    }

}
