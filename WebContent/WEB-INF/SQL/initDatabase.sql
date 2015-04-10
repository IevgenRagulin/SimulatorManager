DROP TABLE IF EXISTS simulationenginesstate;
DROP TABLE IF EXISTS simulationdevicesstate;
DROP TABLE IF EXISTS simulationinfo;
DROP TABLE IF EXISTS simulationpfdinfo;
DROP TABLE IF EXISTS simulation;
DROP TABLE IF EXISTS simulator;
DROP TABLE IF EXISTS enginemodel;
DROP TABLE IF EXISTS simulatormodel;



CREATE TABLE simulatormodel
(
  simulatormodelid serial NOT NULL,
  simulatormodelname character varying(255) NOT NULL,
  minspeed integer,
  maxspeed integer,
  highspeed integer,
  maxspeedonflaps integer NOT NULL,
  minspeedonflaps integer,
  hasgears boolean, 
  numberoflandinggears integer NOT NULL,
  lfu boolean NOT NULL DEFAULT true,
  rfu boolean NOT NULL DEFAULT true,
  cfu boolean NOT NULL DEFAULT false,
  "timestamp" timestamp without time zone DEFAULT now(),
  CONSTRAINT simulatormodel_pkey PRIMARY KEY (simulatormodelid),
  CONSTRAINT simulatormodel_simulatormodelname_key UNIQUE (simulatormodelname)
);


CREATE TABLE enginemodel 
(
  enginemodelid serial NOT NULL,
  simulatormodelid integer,
  enginemodelorder integer,
  rpm boolean DEFAULT true,
  minrpm real,
  maxrpm real,
  pwr boolean DEFAULT true,
  minpwr real,
  maxpwr real,
  pwp boolean DEFAULT true,
  minpwp real,
  maxpwp real,
  mp_ boolean DEFAULT true,
  minmp real,
  maxmp real,
  egt1 boolean DEFAULT true,
  minegt1 real,
  maxegt1 real,
  egt2 boolean DEFAULT true,
  minegt2 real,
  maxegt2 real,
  cht1 boolean DEFAULT true,
  mincht1 real,
  maxcht1 real,
  cht2 boolean DEFAULT true,
  mincht2 real,
  maxcht2 real,
  est boolean DEFAULT true,
  minest real,
  maxest real,
  ff_ boolean DEFAULT true,
  minff real,
  maxff real,
  fp_ boolean DEFAULT true,
  minfp real,
  maxfp real,
  op_ boolean DEFAULT true,
  minop real,
  maxop real,
  ot_ boolean DEFAULT true,
  minot real,
  maxot real,
  n1_ boolean DEFAULT true,
  minn1 real,
  maxn1 real,
  n2_ boolean DEFAULT true,
  minn2 real,
  maxn2 real,
  vib boolean DEFAULT true,
  minvib real,
  maxvib real,
  vlt boolean DEFAULT true,
  minvlt real,
  maxvlt real,
  amp boolean DEFAULT true,
  minamp real,
  maxamp real,
  "timestamp" timestamp without time zone DEFAULT now(),
   CONSTRAINT enginemodel_pkey PRIMARY KEY (enginemodelid),
   CONSTRAINT enginemodel_enginemodelorder_key UNIQUE (simulatormodelid, enginemodelorder)
);

CREATE TABLE simulator
(
  simulatorid serial NOT NULL,
  simulatorname character varying(255) NOT NULL,
  hostname character varying(255) NOT NULL,
  port integer NOT NULL,
  simulatormodelid integer NOT NULL,
  active boolean DEFAULT FALSE,
  "timestamp" timestamp without time zone DEFAULT now(),
  CONSTRAINT simulator_pkey PRIMARY KEY (simulatorid),
  CONSTRAINT simulator_simulatorname_key UNIQUE (simulatorname),
  CONSTRAINT simulatormodel_simulatormodelid_key FOREIGN KEY (simulatormodelid) REFERENCES simulatormodel (simulatormodelid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
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
  ENGINES_NUM int,
  RPM real [],
  PWR real [], 
  PWP real [], 
  MP_ real [], 
  ET1 real [], 
  ET2 real [], 
  CT1 real [], 
  CT2 real [], 
  EST real [], 
  FF_ real [], 
  FP_ real [], 
  OP_ real [], 
  OT_ real [], 
  N1_ real [], 
  N2_ real [], 
  VIB real [], 
  VLT real [], 
  AMP real [], 
  "timestamp" timestamp without time zone DEFAULT now(),
  CONSTRAINT simulationenginesstate_pkey PRIMARY KEY (enginesstateid),
  CONSTRAINT simulationenginesstate_simulation_simulationid_fkey FOREIGN KEY (simulation_simulationid)
      REFERENCES simulation (simulationid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

