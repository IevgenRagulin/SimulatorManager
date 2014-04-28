package com.example.testvaadin.simulatorcommunication;

public class AllSimulationInfo {
	// System
	float fps; // Frame per second
	int sim_speed; // Simulation speed
	Double time; // Time
	Boolean simulationPaused;

	// ///////////////////////////////////
	// POSITION AND ORIENTATION //
	// variables for //
	// position and orientation //
	// ///////////////////////////////////
	// Altitude
	Double altitude_standard; // Altitude standart QNH 1013.25
	Double altitude_corrected; // Altitude corrected (QNH compensated)
	Double GPSHeight; // GPS height above elipsoid
	Double groundAltitude; // ground altitude

	// Speed
	Double IAS; // indicated air speed
	Double GS; // ground speed
	Double TAS; // true air speed
	Double VS; // vertical speed

	// Pressure
	Double dynamic_pressure; // Dynamic Pressure
	Double static_pressure; // Static Pressure

	// Velocity
	Double VE; // velocity east
	Double VN; // velocity north
	Double VU; // velocity up - GPS

	Double pitch; // angle
	Double bank; // angle
	Double heading; // angle

	//
	// DEVICES STATE
	//

	Double aileron;
	Double rudder;
	Double elevator;
	Double trimAileronPosition;// if simulator sends no data we set it to -1
	Double trimRudderPosition; // if simulator sends no data we set it to -1
	Double trimElevatorPosition;// if simulator sends no data we set it to -1

	// GPS
	Double latitude; // GPS Latitude
	Double longitude; // GPS Longitude
	Double TT; // GPS course

	Double alpha; // aerodynamic alpha (angle of attack)
	Double beta; // aerodynamic beta
	Double angleOfSideSlip; // Side slip angle

	Double P; // moment
	Double Q; // moment
	Double R; // moment

	Double local_ax; // acceleration in X axis
	Double local_ay; // acceleration in Y axis
	Double local_az; // acceleration in Z axis

	// ///////////////////////////////////
	// ENGINE //
	// variables for engine value //
	// ///////////////////////////////////
	Double throttle;
	Double engine_rpm; // engine rounds per minutes
	Double engine_pwr; // power in W
	Double engine_pwr_percent; // power in percent
	Double engine_manifold_pressure;// Manifold pressure

	// Temperature
	Double engine_exh_gas_temp1; // Exhaust Gas Temperature per engine 1
	Double engine_exh_gas_temp2; // Exhaust Gas Temperature per engine 2
	Double engine_cyl_head_temp1; // Cylinder Head Temperature per engine 1
	Double engine_cyl_head_temp2; // Cylinder Head Temperature per engine 2
	Double engine_suction_temp; // Engine suction temperature

	// Oil
	Double engine_oil_pressure; // Oil pressure
	Double engine_oil_temp; // Oil Temperature

	// Fuel
	Double engine_fuel_pressure; // Fuel pressure
	Double engine_fuel_flow; // Fuel flow
	Double lfuel; // Left fuel tank - fuel amount
	Double rfuel; // Right fuel tank - fuel amount
	Double tfuel; // Total fuel tank - fuel amount

	// ///////////////////////////////////
	// SYSTEM BUS //
	// variables for system bus //
	// ///////////////////////////////////
	Double battery_temp;
	Double battery_voltage;
	Double battery_amperage;
	Double battery_capacity;

	// ///////////////////////////////////
	// SWITCHES //
	// variables changable by switches //
	// ///////////////////////////////////

	// outside
	Double flaps;
	Double flaps_status;
	Integer landing_gear_1_status;
	Integer landing_gear_2_status;
	Integer landing_gear_3_status;
	boolean pitot_heating;
	Double brakes;
	boolean brakes_status;
	Double speed_brakes;
	Double speed_brakes_status;

	// motor
	boolean master_switch;
	boolean accu;
	boolean gen;
	int ignition;

	// avionics
	Double QNH;
	boolean avionic_switch;
	boolean efis;
	boolean comm_nav;
	boolean position_light;
	boolean data_switch;
	boolean optional_switch; // labeled as unused

	// switches status
	boolean sw_status_master_switch;
	boolean sw_status_accu;
	boolean sw_status_gen;
	boolean sw_status_avionic_switch;
	boolean sw_status_efis;

	// ///////////////////////////////////
	// AUTOPILOT //
	// variables changable by autopilot//
	// ///////////////////////////////////
	// autopilot
	Double ap_ias;
	Double ap_alt;
	Double ap_hea;
	Double ap_tt;
	Double ap_vs;
	int ap_mode;

	// ///////////////////////////////////
	// WEATHER //
	// variables for weather situation //
	// ///////////////////////////////////
	Double temperature;
	Double temperature_cabin;
	Double wind_speed;
	Double wind_direction;

	// Test variables
	Double test0;
	Double test1;
	Double test2;
	Double test3;
	Double test4;
	Double test5;
	Double test6;
	Double test7;
	Double test8;
	Double test9;

	public Boolean getSimulationPaused() {
		return simulationPaused;
	}

	protected void setSimulationPaused(Boolean simulationPaused) {
		this.simulationPaused = simulationPaused;
	}

	public AllSimulationInfo() {
		super();
	}

	public Double getAileron() {
		return aileron;
	}

	protected void setAileron(Double aileron) {
		this.aileron = aileron;
	}

	public Double getRudder() {
		return rudder;
	}

	protected void setRudder(Double rudder) {
		this.rudder = rudder;
	}

	public Double getElevator() {
		return elevator;
	}

	protected void setElevator(Double elevator) {
		this.elevator = elevator;
	}

	public Double getTrimAileronPosition() {
		return trimAileronPosition;
	}

	protected void setTrimAileronPosition(Double trimPosition) {
		if (trimPosition != null) {
			this.trimAileronPosition = trimPosition;
		} else {// if simulator doesn't send data about aileron trim
			this.trimAileronPosition = -2.0;
		}
	}

	public Double getTrimRudderPosition() {
		return trimRudderPosition;
	}

	protected void setTrimRudderPosition(Double trimRudderPosition) {
		if (trimRudderPosition != null) {
			this.trimRudderPosition = trimRudderPosition;
		} else {// if simulator doesn't send data about rudder trim
			this.trimRudderPosition = -2.0;
		}
	}

	public Double getTrimElevatorPosition() {
		return trimElevatorPosition;
	}

	protected void setTrimElevatorPosition(Double trimElevatorPosition) {
		if (trimElevatorPosition != null) {
			this.trimElevatorPosition = trimElevatorPosition;
		} else { // if simulator doesn't send data about elevator trim
			this.trimElevatorPosition = -2.0;
		}
	}

	public Double getThrottle() {
		return throttle;
	}

	protected void setThrottle(Double throttle) {
		this.throttle = throttle;
	}

	public float getFps() {
		return fps;
	}

	protected void setFps(float fps) {
		this.fps = fps;
	}

	public int getSim_speed() {
		return sim_speed;
	}

	protected void setSim_speed(int sim_speed) {
		this.sim_speed = sim_speed;
	}

	public Double getTime() {
		return time;
	}

	protected void setTime(Double time) {
		this.time = time;
	}

	public Double getAltitude_standard() {
		return altitude_standard;
	}

	protected void setAltitude_standard(Double altitude_standard) {
		this.altitude_standard = altitude_standard;
	}

	public Double getAltitude_corrected() {
		return altitude_corrected;
	}

	protected void setAltitude_corrected(Double altitude_corrected) {
		this.altitude_corrected = altitude_corrected;
	}

	public Double getGPSHeight() {
		return GPSHeight;
	}

	protected void setGPSHeight(Double gPSHeight) {
		GPSHeight = gPSHeight;
	}

	public Double getGroundAltitude() {
		return groundAltitude;
	}

	protected void setGroundAltitude(Double groundAltitude) {
		this.groundAltitude = groundAltitude;
	}

	public Double getIAS() {
		return IAS;
	}

	protected void setIAS(Double iAS) {
		IAS = iAS;
	}

	public Double getGS() {
		return GS;
	}

	protected void setGS(Double gS) {
		GS = gS;
	}

	public Double getTAS() {
		return TAS;
	}

	protected void setTAS(Double tAS) {
		TAS = tAS;
	}

	public Double getVS() {
		return VS;
	}

	protected void setVS(Double vS) {
		VS = vS;
	}

	public Double getDynamic_pressure() {
		return dynamic_pressure;
	}

	protected void setDynamic_pressure(Double dynamic_pressure) {
		this.dynamic_pressure = dynamic_pressure;
	}

	public Double getStatic_pressure() {
		return static_pressure;
	}

	protected void setStatic_pressure(Double static_pressure) {
		this.static_pressure = static_pressure;
	}

	public Double getVE() {
		return VE;
	}

	protected void setVE(Double vE) {
		VE = vE;
	}

	public Double getVN() {
		return VN;
	}

	protected void setVN(Double vN) {
		VN = vN;
	}

	public Double getVU() {
		return VU;
	}

	protected void setVU(Double vU) {
		VU = vU;
	}

	public Double getPitch() {
		return pitch;
	}

	protected void setPitch(Double pitch) {
		this.pitch = pitch;
	}

	public Double getBank() {
		return bank;
	}

	protected void setBank(Double bank) {
		this.bank = bank;
	}

	public Double getHeading() {
		return heading;
	}

	protected void setHeading(Double heading) {
		this.heading = heading;
	}

	public Double getLatitude() {
		return latitude;
	}

	protected void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	protected void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getTT() {
		return TT;
	}

	protected void setTT(Double tT) {
		TT = tT;
	}

	public Double getAlpha() {
		return alpha;
	}

	protected void setAlpha(Double alpha) {
		this.alpha = alpha;
	}

	public Double getBeta() {
		return beta;
	}

	protected void setBeta(Double beta) {
		this.beta = beta;
	}

	public Double getAngleOfSideSlip() {
		return angleOfSideSlip;
	}

	protected void setAngleOfSideSlip(Double angleOfSideSlip) {
		this.angleOfSideSlip = angleOfSideSlip;
	}

	public Double getP() {
		return P;
	}

	protected void setP(Double p) {
		P = p;
	}

	public Double getQ() {
		return Q;
	}

	protected void setQ(Double q) {
		Q = q;
	}

	public Double getR() {
		return R;
	}

	protected void setR(Double r) {
		R = r;
	}

	public Double getLocal_ax() {
		return local_ax;
	}

	protected void setLocal_ax(Double local_ax) {
		this.local_ax = local_ax;
	}

	public Double getLocal_ay() {
		return local_ay;
	}

	protected void setLocal_ay(Double local_ay) {
		this.local_ay = local_ay;
	}

	public Double getLocal_az() {
		return local_az;
	}

	protected void setLocal_az(Double local_az) {
		this.local_az = local_az;
	}

	public Double getEngine_rpm() {
		return engine_rpm;
	}

	protected void setEngine_rpm(Double engine_rpm) {
		this.engine_rpm = engine_rpm;
	}

	public Double getEngine_pwr() {
		return engine_pwr;
	}

	protected void setEngine_pwr(Double engine_pwr) {
		this.engine_pwr = engine_pwr;
	}

	public Double getEngine_pwr_percent() {
		return engine_pwr_percent;
	}

	protected void setEngine_pwr_percent(Double engine_pwr_percent) {
		this.engine_pwr_percent = engine_pwr_percent;
	}

	public Double getEngine_manifold_pressure() {
		return engine_manifold_pressure;
	}

	protected void setEngine_manifold_pressure(Double engine_manifold_pressure) {
		this.engine_manifold_pressure = engine_manifold_pressure;
	}

	public Double getEngine_exh_gas_temp1() {
		return engine_exh_gas_temp1;
	}

	protected void setEngine_exh_gas_temp1(Double engine_exh_gas_temp1) {
		this.engine_exh_gas_temp1 = engine_exh_gas_temp1;
	}

	public Double getEngine_exh_gas_temp2() {
		return engine_exh_gas_temp2;
	}

	protected void setEngine_exh_gas_temp2(Double engine_exh_gas_temp2) {
		this.engine_exh_gas_temp2 = engine_exh_gas_temp2;
	}

	public Double getEngine_cyl_head_temp1() {
		return engine_cyl_head_temp1;
	}

	protected void setEngine_cyl_head_temp1(Double engine_cyl_head_temp1) {
		this.engine_cyl_head_temp1 = engine_cyl_head_temp1;
	}

	public Double getEngine_cyl_head_temp2() {
		return engine_cyl_head_temp2;
	}

	protected void setEngine_cyl_head_temp2(Double engine_cyl_head_temp2) {
		this.engine_cyl_head_temp2 = engine_cyl_head_temp2;
	}

	public Double getEngine_suction_temp() {
		return engine_suction_temp;
	}

	protected void setEngine_suction_temp(Double engine_suction_temp) {
		this.engine_suction_temp = engine_suction_temp;
	}

	public Double getEngine_oil_pressure() {
		return engine_oil_pressure;
	}

	protected void setEngine_oil_pressure(Double engine_oil_pressure) {
		this.engine_oil_pressure = engine_oil_pressure;
	}

	public Double getEngine_oil_temp() {
		return engine_oil_temp;
	}

	protected void setEngine_oil_temp(Double engine_oil_temp) {
		this.engine_oil_temp = engine_oil_temp;
	}

	public Double getEngine_fuel_pressure() {
		return engine_fuel_pressure;
	}

	protected void setEngine_fuel_pressure(Double engine_fuel_pressure) {
		this.engine_fuel_pressure = engine_fuel_pressure;
	}

	public Double getEngine_fuel_flow() {
		return engine_fuel_flow;
	}

	protected void setEngine_fuel_flow(Double engine_fuel_flow) {
		this.engine_fuel_flow = engine_fuel_flow;
	}

	public Double getLfuel() {
		return lfuel;
	}

	protected void setLfuel(Double lfuel) {
		this.lfuel = lfuel;
	}

	public Double getRfuel() {
		return rfuel;
	}

	protected void setRfuel(Double rfuel) {
		this.rfuel = rfuel;
	}

	public Double getTfuel() {
		return tfuel;
	}

	protected void setTfuel(Double tfuel) {
		this.tfuel = tfuel;
	}

	public Double getBattery_temp() {
		return battery_temp;
	}

	protected void setBattery_temp(Double battery_temp) {
		this.battery_temp = battery_temp;
	}

	public Double getBattery_voltage() {
		return battery_voltage;
	}

	protected void setBattery_voltage(Double battery_voltage) {
		this.battery_voltage = battery_voltage;
	}

	public Double getBattery_amperage() {
		return battery_amperage;
	}

	protected void setBattery_amperage(Double battery_amperage) {
		this.battery_amperage = battery_amperage;
	}

	public Double getBattery_capacity() {
		return battery_capacity;
	}

	protected void setBattery_capacity(Double battery_capacity) {
		this.battery_capacity = battery_capacity;
	}

	public Double getFlaps() {
		return flaps;
	}

	protected void setFlaps(Double flaps) {
		this.flaps = flaps;
	}

	public Double getFlaps_status() {
		return flaps_status;
	}

	protected void setFlaps_status(Double flaps_status) {
		this.flaps_status = flaps_status;
	}

	public Integer getLanding_gear_1_status() {
		return landing_gear_1_status;
	}

	protected void setLanding_gear_1_status(Integer landing_gear_1_status) {
		this.landing_gear_1_status = landing_gear_1_status;
	}

	public Integer getLanding_gear_2_status() {
		return landing_gear_2_status;
	}

	protected void setLanding_gear_2_status(Integer landing_gear_2_status) {
		this.landing_gear_2_status = landing_gear_2_status;
	}

	public Integer getLanding_gear_3_status() {
		return landing_gear_3_status;
	}

	protected void setLanding_gear_3_status(Integer landing_gear_3_status) {
		this.landing_gear_3_status = landing_gear_3_status;
	}

	public boolean isPitot_heating() {
		return pitot_heating;
	}

	protected void setPitot_heating(boolean pitot_heating) {
		this.pitot_heating = pitot_heating;
	}

	public Double getBrakes() {
		return brakes;
	}

	protected void setBrakes(Double brakes) {
		this.brakes = brakes;
	}

	public boolean getBrakes_status() {
		return brakes_status;
	}

	protected void setBrakes_status(boolean b) {
		this.brakes_status = b;
	}

	public Double getSpeed_brakes() {
		return speed_brakes;
	}

	protected void setSpeed_brakes(Double speed_brakes) {
		this.speed_brakes = speed_brakes;
	}

	public Double getSpeed_brakes_status() {
		return speed_brakes_status;
	}

	protected void setSpeed_brakes_status(Double speed_brakes_status) {
		this.speed_brakes_status = speed_brakes_status;
	}

	public boolean isMaster_switch() {
		return master_switch;
	}

	protected void setMaster_switch(boolean master_switch) {
		this.master_switch = master_switch;
	}

	public boolean isAccu() {
		return accu;
	}

	protected void setAccu(boolean accu) {
		this.accu = accu;
	}

	public boolean isGen() {
		return gen;
	}

	protected void setGen(boolean gen) {
		this.gen = gen;
	}

	public int getIgnition() {
		return ignition;
	}

	protected void setIgnition(int ignition) {
		this.ignition = ignition;
	}

	public Double getQNH() {
		return QNH;
	}

	protected void setQNH(Double qNH) {
		QNH = qNH;
	}

	public boolean isAvionic_switch() {
		return avionic_switch;
	}

	protected void setAvionic_switch(boolean avionic_switch) {
		this.avionic_switch = avionic_switch;
	}

	public boolean isEfis() {
		return efis;
	}

	protected void setEfis(boolean efis) {
		this.efis = efis;
	}

	public boolean isComm_nav() {
		return comm_nav;
	}

	protected void setComm_nav(boolean comm_nav) {
		this.comm_nav = comm_nav;
	}

	public boolean isPosition_light() {
		return position_light;
	}

	protected void setPosition_light(boolean position_light) {
		this.position_light = position_light;
	}

	public boolean isData_switch() {
		return data_switch;
	}

	protected void setData_switch(boolean data_switch) {
		this.data_switch = data_switch;
	}

	public boolean isOptional_switch() {
		return optional_switch;
	}

	protected void setOptional_switch(boolean optional_switch) {
		this.optional_switch = optional_switch;
	}

	public boolean isSw_status_master_switch() {
		return sw_status_master_switch;
	}

	protected void setSw_status_master_switch(boolean sw_status_master_switch) {
		this.sw_status_master_switch = sw_status_master_switch;
	}

	public boolean isSw_status_accu() {
		return sw_status_accu;
	}

	protected void setSw_status_accu(boolean sw_status_accu) {
		this.sw_status_accu = sw_status_accu;
	}

	public boolean isSw_status_gen() {
		return sw_status_gen;
	}

	protected void setSw_status_gen(boolean sw_status_gen) {
		this.sw_status_gen = sw_status_gen;
	}

	public boolean isSw_status_avionic_switch() {
		return sw_status_avionic_switch;
	}

	protected void setSw_status_avionic_switch(boolean sw_status_avionic_switch) {
		this.sw_status_avionic_switch = sw_status_avionic_switch;
	}

	public boolean isSw_status_efis() {
		return sw_status_efis;
	}

	protected void setSw_status_efis(boolean sw_status_efis) {
		this.sw_status_efis = sw_status_efis;
	}

	public Double getAp_ias() {
		return ap_ias;
	}

	protected void setAp_ias(Double ap_ias) {
		this.ap_ias = ap_ias;
	}

	public Double getAp_alt() {
		return ap_alt;
	}

	protected void setAp_alt(Double ap_alt) {
		this.ap_alt = ap_alt;
	}

	public Double getAp_hea() {
		return ap_hea;
	}

	protected void setAp_hea(Double ap_hea) {
		this.ap_hea = ap_hea;
	}

	public Double getAp_tt() {
		return ap_tt;
	}

	protected void setAp_tt(Double ap_tt) {
		this.ap_tt = ap_tt;
	}

	public Double getAp_vs() {
		return ap_vs;
	}

	protected void setAp_vs(Double ap_vs) {
		this.ap_vs = ap_vs;
	}

	public int getAp_mode() {
		return ap_mode;
	}

	protected void setAp_mode(int ap_mode) {
		this.ap_mode = ap_mode;
	}

	public Double getTemperature() {
		return temperature;
	}

	protected void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getTemperature_cabin() {
		return temperature_cabin;
	}

	protected void setTemperature_cabin(Double temperature_cabin) {
		this.temperature_cabin = temperature_cabin;
	}

	public Double getWind_speed() {
		return wind_speed;
	}

	protected void setWind_speed(Double wind_speed) {
		this.wind_speed = wind_speed;
	}

	public Double getWind_direction() {
		return wind_direction;
	}

	protected void setWind_direction(Double wind_direction) {
		this.wind_direction = wind_direction;
	}

	public Double getTest0() {
		return test0;
	}

	protected void setTest0(Double test0) {
		this.test0 = test0;
	}

	public Double getTest1() {
		return test1;
	}

	protected void setTest1(Double test1) {
		this.test1 = test1;
	}

	public Double getTest2() {
		return test2;
	}

	protected void setTest2(Double test2) {
		this.test2 = test2;
	}

	public Double getTest3() {
		return test3;
	}

	protected void setTest3(Double test3) {
		this.test3 = test3;
	}

	public Double getTest4() {
		return test4;
	}

	protected void setTest4(Double test4) {
		this.test4 = test4;
	}

	public Double getTest5() {
		return test5;
	}

	protected void setTest5(Double test5) {
		this.test5 = test5;
	}

	public Double getTest6() {
		return test6;
	}

	protected void setTest6(Double test6) {
		this.test6 = test6;
	}

	public Double getTest7() {
		return test7;
	}

	protected void setTest7(Double test7) {
		this.test7 = test7;
	}

	public Double getTest8() {
		return test8;
	}

	protected void setTest8(Double test8) {
		this.test8 = test8;
	}

	public Double getTest9() {
		return test9;
	}

	protected void setTest9(Double test9) {
		this.test9 = test9;
	}

}
