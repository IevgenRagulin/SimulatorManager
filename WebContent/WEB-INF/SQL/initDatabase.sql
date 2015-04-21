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
  maxspeedonflaps integer NOT NULL,
  minspeedonflaps integer,
  hasgears boolean, 
  numberoflandinggears integer NOT NULL,
  lfu boolean NOT NULL DEFAULT true,
  minlfu real,
  lowlfu real,
  highlfu real,
  maxlfu real,
  cfu boolean NOT NULL DEFAULT false,
  mincfu real,
  lowcfu real,
  highcfu real,
  maxcfu real,
  rfu boolean NOT NULL DEFAULT true,
  minrfu real,
  lowrfu real,
  highrfu real,
  maxrfu real,
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
  lowrpm real,
  highrpm real,
  maxrpm real,
  pwr boolean DEFAULT true,
  minpwr real,
  lowpwr real,
  highpwr real,
  maxpwr real,
  pwp boolean DEFAULT true,
  lowpwp real,
  highpwp real,
  minpwp real,
  maxpwp real,
  mp_ boolean DEFAULT true,
  minmp real,
  lowmp real,
  highmp real,
  maxmp real,
  et1 boolean DEFAULT true,
  minet1 real,
  lowet1 real,
  highet1 real,
  maxet1 real,
  et2 boolean DEFAULT true,
  minet2 real,
  lowet2 real,
  highet2 real,
  maxet2 real,
  ct1 boolean DEFAULT true,
  minct1 real,
  lowct1 real,
  highct1 real,
  maxct1 real,
  ct2 boolean DEFAULT true,
  minct2 real,
  lowct2 real,
  highct2 real,
  maxct2 real,
  est boolean DEFAULT true,
  minest real,
  lowest real,
  highest real,
  maxest real,
  ff_ boolean DEFAULT true,
  minff real,
  lowff real,
  highff real,
  maxff real,
  fp_ boolean DEFAULT true,
  minfp real,
  lowfp real,
  highfp real,
  maxfp real,
  op_ boolean DEFAULT true,
  minop real,
  lowop real,
  highop real,
  maxop real,
  ot_ boolean DEFAULT true,
  minot real,
  lowot real,
  highot real,
  maxot real,
  n1_ boolean DEFAULT true,
  minn1 real,
  lown1 real,
  highn1 real,
  maxn1 real,
  n2_ boolean DEFAULT true,
  minn2 real,
  lown2 real,
  highn2 real,
  maxn2 real,
  vib boolean DEFAULT true,
  minvib real,
  lowvib real,
  highvib real,
  maxvib real,
  vlt boolean DEFAULT true,
  minvlt real,
  lowvlt real,
  highvlt real,
  maxvlt real,
  amp boolean DEFAULT true,
  minamp real,
  lowamp real,
  highamp real,
  maxamp real,
  "timestamp" timestamp without time zone DEFAULT now(),
   CONSTRAINT enginemodel_pkey PRIMARY KEY (enginemodelid),
   CONSTRAINT enginemodel_enginemodelorder_key UNIQUE (simulatormodelid, enginemodelorder),
   CONSTRAINT enginemodel_simulatormodelid_key FOREIGN KEY (simulatormodelid) REFERENCES simulatormodel (simulatormodelid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE 
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
      ON UPDATE NO ACTION ON DELETE CASCADE 
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
  latestupdatetime timestamp without time zone,
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
      ON UPDATE NO ACTION ON DELETE CASCADE
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
  lfu real,
  rfu real,
  cfu real,
  "timestamp" timestamp without time zone DEFAULT now(),
  CONSTRAINT simulationdevicesstate_pkey PRIMARY KEY (devstateid),
  CONSTRAINT simulationdevicesstate_simulation_simulationid_fkey FOREIGN KEY (simulation_simulationid)
      REFERENCES simulation (simulationid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
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
      ON UPDATE NO ACTION ON DELETE CASCADE
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
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);

