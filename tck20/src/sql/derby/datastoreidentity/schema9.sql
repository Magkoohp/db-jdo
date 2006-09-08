-- SchemaType: datastore identity

connect 'jdbc:derby:jdotckdb;create=true' user 'tckuser' password 'tckuser';

CREATE SCHEMA datastoreidentity9;
SET SCHEMA datastoreidentity9;

-------------------------
-- company
-------------------------

ALTER TABLE departments DROP CONSTRAINT EMP_MO_FK;
ALTER TABLE departments DROP CONSTRAINT DEPTS_COMP_FK;
ALTER TABLE persons DROP CONSTRAINT PERS_DEPT_FK;
ALTER TABLE persons DROP CONSTRAINT PERS_FUNDDEPT_FK;
ALTER TABLE persons DROP CONSTRAINT PERS_MANAGER_FK;
ALTER TABLE persons DROP CONSTRAINT PERS_MENTOR_FK;
ALTER TABLE persons DROP CONSTRAINT PERS_HRADVISOR_FK;
DROP TABLE persons;
DROP TABLE departments;
DROP TABLE companies;

CREATE TABLE companies (
    DATASTORE_IDENTITY INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    ID INTEGER NOT NULL,
    NAME VARCHAR(32) NOT NULL,
    FOUNDEDDATE VARCHAR(32) NOT NULL,
    CONSTRAINT COMPS_PK PRIMARY KEY (DATASTORE_IDENTITY)
);

CREATE TABLE departments (
    DATASTORE_IDENTITY INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    ID INTEGER NOT NULL,
    NAME VARCHAR(32) NOT NULL,
    EMP_OF_THE_MONTH INTEGER,
    COMPANYID INTEGER,
    DISCRIMINATOR VARCHAR(255),
    CONSTRAINT DEPTS_COMP_FK FOREIGN KEY (COMPANYID) REFERENCES companies,
    CONSTRAINT DEPTS_PK PRIMARY KEY (DATASTORE_IDENTITY)
);

CREATE TABLE persons (
    DATASTORE_IDENTITY INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    PERSONID INTEGER NOT NULL,
    FIRSTNAME VARCHAR(32) NOT NULL,
    LASTNAME VARCHAR(32) NOT NULL,
    MIDDLENAME VARCHAR(32),
    BIRTHDATE VARCHAR(32) NOT NULL,
    DISCRIMINATOR varchar(64) NOT NULL,
    EMPID INTEGER NOT NULL,
    HIREDATE VARCHAR(32),
    WEEKLYHOURS REAL,
    DEPARTMENT INTEGER,
    FUNDINGDEPT INTEGER,
    MANAGER INTEGER,
    MENTOR INTEGER,
    HRADVISOR INTEGER,
    SALARY REAL,
    WAGE REAL,
    CONSTRAINT PERS_DEPT_FK FOREIGN KEY (DEPARTMENT) REFERENCES departments,
    CONSTRAINT PERS_FUNDDEPT_FK FOREIGN KEY (FUNDINGDEPT)
        REFERENCES departments,
    CONSTRAINT PERS_MANAGER_FK FOREIGN KEY (MANAGER) REFERENCES persons,
    CONSTRAINT PERS_MENTOR_FK FOREIGN KEY (MENTOR) REFERENCES persons,
    CONSTRAINT PERS_HRADVISOR_FK FOREIGN KEY (HRADVISOR) REFERENCES persons,
    CONSTRAINT PERS_UK UNIQUE (PERSONID),
    CONSTRAINT PERS_PK PRIMARY KEY (DATASTORE_IDENTITY)
);

ALTER TABLE departments 
    ADD CONSTRAINT EMP_MO_FK FOREIGN KEY
        (EMP_OF_THE_MONTH) REFERENCES persons(PERSONID);

