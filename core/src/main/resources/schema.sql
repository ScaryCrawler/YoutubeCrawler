-- executed by spring boot on H2 DB at test and application start time
CREATE TABLE IF NOT EXISTS JOBS  (
  ID               UUID          NOT NULL  PRIMARY KEY,
  STATUS           VARCHAR(255)  NOT NULL
);
