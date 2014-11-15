DROP TABLE IF EXISTS simulationenginesstate;
DROP TABLE IF EXISTS simulationdevicesstate;
DROP TABLE IF EXISTS simulationinfo;
DROP TABLE IF EXISTS simulationpfdinfo;
DROP TABLE IF EXISTS simulation;
DROP TABLE IF EXISTS simulator;

CREATE TABLE simulator
(
  simulatorid serial NOT NULL,
  simulatorname character varying(255) NOT NULL,
  hostname character varying(255) NOT NULL,
  port integer NOT NULL,
  aircraftmodel character varying(255),
  minspeed integer,
  maxspeed integer,
  highspeed integer,
  maxspeedonflaps integer NOT NULL,
  minspeedonflaps integer,
  hasgears boolean,
  mintempcht1 real,
  mintempcht2 real,
  mintempegt1 real,
  mintempegt2 real,
  maxtempcht1 real,
  maxtempcht2 real,
  maxtempegt1 real,
  maxtempegt2 real,
  manifoldpressure integer,
  power integer,
  maxamountoffuel real,
  minamountoffuel real,
  maxrpm integer,
  numberofengines integer,
  numberoflandinggears integer,
  active boolean DEFAULT FALSE,
  "timestamp" timestamp without time zone DEFAULT now(),
  CONSTRAINT simulator_pkey PRIMARY KEY (simulatorid),
  CONSTRAINT simulator_simulatorname_key UNIQUE (simulatorname)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE simulation
(
  simulationid serial NOT NULL,
  simulator_simulatorid integer NOT NULL,
  issimulationon boolean,
  issimulationpaused boolean,
  simulationstartedtime timestamp without time zone,
  simulationendedtime timestamp without time zone,
  "timestamp" timestamp without time zone DEFAULT now(),
  CONSTRAINT simulation_pkey PRIMARY KEY (simulationid)
)
WITH (
  OIDS=FALSE
);


  CREATE TABLE simulationinfo
(
  simulationinfoid serial NOT NULL,
  simulation_simulationid integer NOT NULL,
  longtitude double precision,
  latitude double precision,
  "timestamp" timestamp without time zone DEFAULT now(),
  CONSTRAINT simulationinfo_pkey PRIMARY KEY (simulationinfoid),
  CONSTRAINT simulationinfo_simulation_simulationid_fkey FOREIGN KEY (simulation_simulationid)
      REFERENCES simulation (simulationid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE simulationdevicesstate
(
  devstateid serial NOT NULL,
  simulation_simulationid integer NOT NULL,
  elevator double precision,
  eleron double precision,
  rudder double precision,
  throttle double precision,
  flaps double precision,
  speedbrakes double precision,
  brakes boolean,
  issimulationpaused boolean,
  elevatortrim double precision,
  ailerontrim double precision,
  ruddertrim double precision,
  landinggear_1 int,
  landinggear_2 int,
  landinggear_3 int,
  fuel_left double precision,
  fuel_right double precision,
  fuel_center double precision,
  "timestamp" timestamp without time zone DEFAULT now(),
  CONSTRAINT simulationdevicesstate_pkey PRIMARY KEY (devstateid),
  CONSTRAINT simulationdevicesstate_simulation_simulationid_fkey FOREIGN KEY (simulation_simulationid)
      REFERENCES simulation (simulationid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE simulationpfdinfo
(
  pfdinfoid serial NOT NULL,
  simulation_simulationid integer NOT NULL,
  roll double precision,
  pitch double precision,
  heading double precision,
  truecourse double precision,
  ias double precision,
  altitude double precision,
  groundaltitude double precision,
  verticalspeed double precision,
  "timestamp" timestamp without time zone DEFAULT now(),
  CONSTRAINT simulationpfdinfo_pkey PRIMARY KEY (pfdinfoid),
  CONSTRAINT simulationpfdinfo_simulation_simulationid_fkey FOREIGN KEY (simulation_simulationid)
      REFERENCES simulation (simulationid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE simulationenginesstate
(
  enginesstateid serial NOT NULL,
  simulation_simulationid integer NOT NULL,
  E1RPM real,
  E1PWR real, 
  E1PWP real, 
  E1MP_ real, 
  E1ET1 real, 
  E1ET2 real, 
  E1CT1 real, 
  E1CT2 real, 
  E1EST real, 
  E1FF_ real, 
  E1FP_ real, 
  E1OP_ real, 
  E1OT_ real, 
  E1N1_ real, 
  E1N2_ real, 
  E1VIB real, 
  E1VLT real, 
  E1AMP real, 
  E2RPM real, 
  E2PWR real, 
  E2PWP real,
  E2MP_ real, 
  E2ET1 real,
  E2ET2 real,
  E2CT1 real, 
  E2CT2 real, 
  E2EST real, 
  E2FF_ real, 
  E2FP_ real, 
  E2OP_ real, 
  E2OT_ real, 
  E2N1_ real, 
  E2N2_ real, 
  E2VIB real, 
  E2VLT real, 
  E2AMP real, 
  E3RPM real, 
  E3PWR real,
  E3PWP real,
  E3MP_ real, 
  E3ET1 real, 
  E3ET2 real, 
  E3CT1 real,
  E3CT2 real, 
  E3EST real, 
  E3FF_ real, 
  E3FP_ real, 
  E3OP_ real, 
  E3OT_ real, 
  E3N1_ real, 
  E3N2_ real, 
  E3VIB real, 
  E3VLT real, 
  E3AMP real, 
  E4RPM real, 
  E4PWR real, 
  E4PWP real, 
  E4MP_ real, 
  E4ET1 real, 
  E4ET2 real, 
  E4CT1 real, 
  E4CT2 real, 
  E4EST real, 
  E4FF_ real, 
  E4FP_ real, 
  E4OP_ real,
  E4OT_ real, 
  E4N1_ real, 
  E4N2_ real, 
  E4VIB real, 
  E4VLT real, 
  E4AMP real,  
  "timestamp" timestamp without time zone DEFAULT now(),
  CONSTRAINT simulationenginesstate_pkey PRIMARY KEY (enginesstateid),
  CONSTRAINT simulationenginesstate_simulation_simulationid_fkey FOREIGN KEY (simulation_simulationid)
      REFERENCES simulation (simulationid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

