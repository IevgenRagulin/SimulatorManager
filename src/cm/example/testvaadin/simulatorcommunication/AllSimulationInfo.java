package cm.example.testvaadin.simulatorcommunication;

public class AllSimulationInfo {
	// System
	float fps; // Frame per second
	int sim_speed; // Simulation speed
	Double time; // Time

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
	int landing_gear;
	int landing_gear_1_status;
	int landing_gear_2_status;
	int landing_gear_3_status;
	boolean pitot_heating;
	Double brakes;
	Double brakes_status;
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

	public AllSimulationInfo() {
		super();
	}

	protected float getFps() {
		return fps;
	}

	protected void setFps(float fps) {
		this.fps = fps;
	}

	protected int getSim_speed() {
		return sim_speed;
	}

	protected void setSim_speed(int sim_speed) {
		this.sim_speed = sim_speed;
	}

	protected Double getTime() {
		return time;
	}

	protected void setTime(Double time) {
		this.time = time;
	}

	protected Double getAltitude_standard() {
		return altitude_standard;
	}

	protected void setAltitude_standard(Double altitude_standard) {
		this.altitude_standard = altitude_standard;
	}

	protected Double getAltitude_corrected() {
		return altitude_corrected;
	}

	protected void setAltitude_corrected(Double altitude_corrected) {
		this.altitude_corrected = altitude_corrected;
	}

	protected Double getGPSHeight() {
		return GPSHeight;
	}

	protected void setGPSHeight(Double gPSHeight) {
		GPSHeight = gPSHeight;
	}

	protected Double getGroundAltitude() {
		return groundAltitude;
	}

	protected void setGroundAltitude(Double groundAltitude) {
		this.groundAltitude = groundAltitude;
	}

	protected Double getIAS() {
		return IAS;
	}

	protected void setIAS(Double iAS) {
		IAS = iAS;
	}

	protected Double getGS() {
		return GS;
	}

	protected void setGS(Double gS) {
		GS = gS;
	}

	protected Double getTAS() {
		return TAS;
	}

	protected void setTAS(Double tAS) {
		TAS = tAS;
	}

	protected Double getVS() {
		return VS;
	}

	protected void setVS(Double vS) {
		VS = vS;
	}

	protected Double getDynamic_pressure() {
		return dynamic_pressure;
	}

	protected void setDynamic_pressure(Double dynamic_pressure) {
		this.dynamic_pressure = dynamic_pressure;
	}

	protected Double getStatic_pressure() {
		return static_pressure;
	}

	protected void setStatic_pressure(Double static_pressure) {
		this.static_pressure = static_pressure;
	}

	protected Double getVE() {
		return VE;
	}

	protected void setVE(Double vE) {
		VE = vE;
	}

	protected Double getVN() {
		return VN;
	}

	protected void setVN(Double vN) {
		VN = vN;
	}

	protected Double getVU() {
		return VU;
	}

	protected void setVU(Double vU) {
		VU = vU;
	}

	protected Double getPitch() {
		return pitch;
	}

	protected void setPitch(Double pitch) {
		this.pitch = pitch;
	}

	protected Double getBank() {
		return bank;
	}

	protected void setBank(Double bank) {
		this.bank = bank;
	}

	protected Double getHeading() {
		return heading;
	}

	protected void setHeading(Double heading) {
		this.heading = heading;
	}

	protected Double getLatitude() {
		return latitude;
	}

	protected void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	protected Double getLongitude() {
		return longitude;
	}

	protected void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	protected Double getTT() {
		return TT;
	}

	protected void setTT(Double tT) {
		TT = tT;
	}

	protected Double getAlpha() {
		return alpha;
	}

	protected void setAlpha(Double alpha) {
		this.alpha = alpha;
	}

	protected Double getBeta() {
		return beta;
	}

	protected void setBeta(Double beta) {
		this.beta = beta;
	}

	protected Double getAngleOfSideSlip() {
		return angleOfSideSlip;
	}

	protected void setAngleOfSideSlip(Double angleOfSideSlip) {
		this.angleOfSideSlip = angleOfSideSlip;
	}

	protected Double getP() {
		return P;
	}

	protected void setP(Double p) {
		P = p;
	}

	protected Double getQ() {
		return Q;
	}

	protected void setQ(Double q) {
		Q = q;
	}

	protected Double getR() {
		return R;
	}

	protected void setR(Double r) {
		R = r;
	}

	protected Double getLocal_ax() {
		return local_ax;
	}

	protected void setLocal_ax(Double local_ax) {
		this.local_ax = local_ax;
	}

	protected Double getLocal_ay() {
		return local_ay;
	}

	protected void setLocal_ay(Double local_ay) {
		this.local_ay = local_ay;
	}

	protected Double getLocal_az() {
		return local_az;
	}

	protected void setLocal_az(Double local_az) {
		this.local_az = local_az;
	}

	protected Double getEngine_rpm() {
		return engine_rpm;
	}

	protected void setEngine_rpm(Double engine_rpm) {
		this.engine_rpm = engine_rpm;
	}

	protected Double getEngine_pwr() {
		return engine_pwr;
	}

	protected void setEngine_pwr(Double engine_pwr) {
		this.engine_pwr = engine_pwr;
	}

	protected Double getEngine_pwr_percent() {
		return engine_pwr_percent;
	}

	protected void setEngine_pwr_percent(Double engine_pwr_percent) {
		this.engine_pwr_percent = engine_pwr_percent;
	}

	protected Double getEngine_manifold_pressure() {
		return engine_manifold_pressure;
	}

	protected void setEngine_manifold_pressure(Double engine_manifold_pressure) {
		this.engine_manifold_pressure = engine_manifold_pressure;
	}

	protected Double getEngine_exh_gas_temp1() {
		return engine_exh_gas_temp1;
	}

	protected void setEngine_exh_gas_temp1(Double engine_exh_gas_temp1) {
		this.engine_exh_gas_temp1 = engine_exh_gas_temp1;
	}

	protected Double getEngine_exh_gas_temp2() {
		return engine_exh_gas_temp2;
	}

	protected void setEngine_exh_gas_temp2(Double engine_exh_gas_temp2) {
		this.engine_exh_gas_temp2 = engine_exh_gas_temp2;
	}

	protected Double getEngine_cyl_head_temp1() {
		return engine_cyl_head_temp1;
	}

	protected void setEngine_cyl_head_temp1(Double engine_cyl_head_temp1) {
		this.engine_cyl_head_temp1 = engine_cyl_head_temp1;
	}

	protected Double getEngine_cyl_head_temp2() {
		return engine_cyl_head_temp2;
	}

	protected void setEngine_cyl_head_temp2(Double engine_cyl_head_temp2) {
		this.engine_cyl_head_temp2 = engine_cyl_head_temp2;
	}

	protected Double getEngine_suction_temp() {
		return engine_suction_temp;
	}

	protected void setEngine_suction_temp(Double engine_suction_temp) {
		this.engine_suction_temp = engine_suction_temp;
	}

	protected Double getEngine_oil_pressure() {
		return engine_oil_pressure;
	}

	protected void setEngine_oil_pressure(Double engine_oil_pressure) {
		this.engine_oil_pressure = engine_oil_pressure;
	}

	protected Double getEngine_oil_temp() {
		return engine_oil_temp;
	}

	protected void setEngine_oil_temp(Double engine_oil_temp) {
		this.engine_oil_temp = engine_oil_temp;
	}

	protected Double getEngine_fuel_pressure() {
		return engine_fuel_pressure;
	}

	protected void setEngine_fuel_pressure(Double engine_fuel_pressure) {
		this.engine_fuel_pressure = engine_fuel_pressure;
	}

	protected Double getEngine_fuel_flow() {
		return engine_fuel_flow;
	}

	protected void setEngine_fuel_flow(Double engine_fuel_flow) {
		this.engine_fuel_flow = engine_fuel_flow;
	}

	protected Double getLfuel() {
		return lfuel;
	}

	protected void setLfuel(Double lfuel) {
		this.lfuel = lfuel;
	}

	protected Double getRfuel() {
		return rfuel;
	}

	protected void setRfuel(Double rfuel) {
		this.rfuel = rfuel;
	}

	protected Double getTfuel() {
		return tfuel;
	}

	protected void setTfuel(Double tfuel) {
		this.tfuel = tfuel;
	}

	protected Double getBattery_temp() {
		return battery_temp;
	}

	protected void setBattery_temp(Double battery_temp) {
		this.battery_temp = battery_temp;
	}

	protected Double getBattery_voltage() {
		return battery_voltage;
	}

	protected void setBattery_voltage(Double battery_voltage) {
		this.battery_voltage = battery_voltage;
	}

	protected Double getBattery_amperage() {
		return battery_amperage;
	}

	protected void setBattery_amperage(Double battery_amperage) {
		this.battery_amperage = battery_amperage;
	}

	protected Double getBattery_capacity() {
		return battery_capacity;
	}

	protected void setBattery_capacity(Double battery_capacity) {
		this.battery_capacity = battery_capacity;
	}

	protected Double getFlaps() {
		return flaps;
	}

	protected void setFlaps(Double flaps) {
		this.flaps = flaps;
	}

	protected Double getFlaps_status() {
		return flaps_status;
	}

	protected void setFlaps_status(Double flaps_status) {
		this.flaps_status = flaps_status;
	}

	protected int getLanding_gear() {
		return landing_gear;
	}

	protected void setLanding_gear(int landing_gear) {
		this.landing_gear = landing_gear;
	}

	protected int getLanding_gear_1_status() {
		return landing_gear_1_status;
	}

	protected void setLanding_gear_1_status(int landing_gear_1_status) {
		this.landing_gear_1_status = landing_gear_1_status;
	}

	protected int getLanding_gear_2_status() {
		return landing_gear_2_status;
	}

	protected void setLanding_gear_2_status(int landing_gear_2_status) {
		this.landing_gear_2_status = landing_gear_2_status;
	}

	protected int getLanding_gear_3_status() {
		return landing_gear_3_status;
	}

	protected void setLanding_gear_3_status(int landing_gear_3_status) {
		this.landing_gear_3_status = landing_gear_3_status;
	}

	protected boolean isPitot_heating() {
		return pitot_heating;
	}

	protected void setPitot_heating(boolean pitot_heating) {
		this.pitot_heating = pitot_heating;
	}

	protected Double getBrakes() {
		return brakes;
	}

	protected void setBrakes(Double brakes) {
		this.brakes = brakes;
	}

	protected Double getBrakes_status() {
		return brakes_status;
	}

	protected void setBrakes_status(Double brakes_status) {
		this.brakes_status = brakes_status;
	}

	protected Double getSpeed_brakes() {
		return speed_brakes;
	}

	protected void setSpeed_brakes(Double speed_brakes) {
		this.speed_brakes = speed_brakes;
	}

	protected Double getSpeed_brakes_status() {
		return speed_brakes_status;
	}

	protected void setSpeed_brakes_status(Double speed_brakes_status) {
		this.speed_brakes_status = speed_brakes_status;
	}

	protected boolean isMaster_switch() {
		return master_switch;
	}

	protected void setMaster_switch(boolean master_switch) {
		this.master_switch = master_switch;
	}

	protected boolean isAccu() {
		return accu;
	}

	protected void setAccu(boolean accu) {
		this.accu = accu;
	}

	protected boolean isGen() {
		return gen;
	}

	protected void setGen(boolean gen) {
		this.gen = gen;
	}

	protected int getIgnition() {
		return ignition;
	}

	protected void setIgnition(int ignition) {
		this.ignition = ignition;
	}

	protected Double getQNH() {
		return QNH;
	}

	protected void setQNH(Double qNH) {
		QNH = qNH;
	}

	protected boolean isAvionic_switch() {
		return avionic_switch;
	}

	protected void setAvionic_switch(boolean avionic_switch) {
		this.avionic_switch = avionic_switch;
	}

	protected boolean isEfis() {
		return efis;
	}

	protected void setEfis(boolean efis) {
		this.efis = efis;
	}

	protected boolean isComm_nav() {
		return comm_nav;
	}

	protected void setComm_nav(boolean comm_nav) {
		this.comm_nav = comm_nav;
	}

	protected boolean isPosition_light() {
		return position_light;
	}

	protected void setPosition_light(boolean position_light) {
		this.position_light = position_light;
	}

	protected boolean isData_switch() {
		return data_switch;
	}

	protected void setData_switch(boolean data_switch) {
		this.data_switch = data_switch;
	}

	protected boolean isOptional_switch() {
		return optional_switch;
	}

	protected void setOptional_switch(boolean optional_switch) {
		this.optional_switch = optional_switch;
	}

	protected boolean isSw_status_master_switch() {
		return sw_status_master_switch;
	}

	protected void setSw_status_master_switch(boolean sw_status_master_switch) {
		this.sw_status_master_switch = sw_status_master_switch;
	}

	protected boolean isSw_status_accu() {
		return sw_status_accu;
	}

	protected void setSw_status_accu(boolean sw_status_accu) {
		this.sw_status_accu = sw_status_accu;
	}

	protected boolean isSw_status_gen() {
		return sw_status_gen;
	}

	protected void setSw_status_gen(boolean sw_status_gen) {
		this.sw_status_gen = sw_status_gen;
	}

	protected boolean isSw_status_avionic_switch() {
		return sw_status_avionic_switch;
	}

	protected void setSw_status_avionic_switch(boolean sw_status_avionic_switch) {
		this.sw_status_avionic_switch = sw_status_avionic_switch;
	}

	protected boolean isSw_status_efis() {
		return sw_status_efis;
	}

	protected void setSw_status_efis(boolean sw_status_efis) {
		this.sw_status_efis = sw_status_efis;
	}

	protected Double getAp_ias() {
		return ap_ias;
	}

	protected void setAp_ias(Double ap_ias) {
		this.ap_ias = ap_ias;
	}

	protected Double getAp_alt() {
		return ap_alt;
	}

	protected void setAp_alt(Double ap_alt) {
		this.ap_alt = ap_alt;
	}

	protected Double getAp_hea() {
		return ap_hea;
	}

	protected void setAp_hea(Double ap_hea) {
		this.ap_hea = ap_hea;
	}

	protected Double getAp_tt() {
		return ap_tt;
	}

	protected void setAp_tt(Double ap_tt) {
		this.ap_tt = ap_tt;
	}

	protected Double getAp_vs() {
		return ap_vs;
	}

	protected void setAp_vs(Double ap_vs) {
		this.ap_vs = ap_vs;
	}

	protected int getAp_mode() {
		return ap_mode;
	}

	protected void setAp_mode(int ap_mode) {
		this.ap_mode = ap_mode;
	}

	protected Double getTemperature() {
		return temperature;
	}

	protected void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	protected Double getTemperature_cabin() {
		return temperature_cabin;
	}

	protected void setTemperature_cabin(Double temperature_cabin) {
		this.temperature_cabin = temperature_cabin;
	}

	protected Double getWind_speed() {
		return wind_speed;
	}

	protected void setWind_speed(Double wind_speed) {
		this.wind_speed = wind_speed;
	}

	protected Double getWind_direction() {
		return wind_direction;
	}

	protected void setWind_direction(Double wind_direction) {
		this.wind_direction = wind_direction;
	}

	protected Double getTest0() {
		return test0;
	}

	protected void setTest0(Double test0) {
		this.test0 = test0;
	}

	protected Double getTest1() {
		return test1;
	}

	protected void setTest1(Double test1) {
		this.test1 = test1;
	}

	protected Double getTest2() {
		return test2;
	}

	protected void setTest2(Double test2) {
		this.test2 = test2;
	}

	protected Double getTest3() {
		return test3;
	}

	protected void setTest3(Double test3) {
		this.test3 = test3;
	}

	protected Double getTest4() {
		return test4;
	}

	protected void setTest4(Double test4) {
		this.test4 = test4;
	}

	protected Double getTest5() {
		return test5;
	}

	protected void setTest5(Double test5) {
		this.test5 = test5;
	}

	protected Double getTest6() {
		return test6;
	}

	protected void setTest6(Double test6) {
		this.test6 = test6;
	}

	protected Double getTest7() {
		return test7;
	}

	protected void setTest7(Double test7) {
		this.test7 = test7;
	}

	protected Double getTest8() {
		return test8;
	}

	protected void setTest8(Double test8) {
		this.test8 = test8;
	}

	protected Double getTest9() {
		return test9;
	}

	protected void setTest9(Double test9) {
		this.test9 = test9;
	}

}
