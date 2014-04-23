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


  
