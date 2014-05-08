DROP TABLE IF EXISTS "USERS";
CREATE TABLE USERS (
  id INTEGER PRIMARY KEY,
  username CHAR(20) NOT NULL ,
  password CHAR(128) NOT NULL ,
  email CHAR(32),
  tableId CHAR(32),
  role CHAR(20),
  UNIQUE (username),
  FOREIGN KEY (tableId) REFERENCES EMPLOYEES(tableId));
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('admin','1000:700728b9c738f18c580ebf33101c3632fb60109d1c53ac2a:f41ac581b0c986d892bf9390918246d510ad42cabd2e9bb7','admin@example.com','KK00000001','ADMINISTRATOR');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('angryziber','1000:1ae3764520ed4ebbf796b7531471fba0f78e554313a0d248:96c59518cb3dbc00ccc39a1001d75e82d87308bae33d1a52','angryziber@example.com','KK00000002','SUPERVISOR');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('Bob','1000:80c0d3ba64091c8a53f3a00f6e5e7fd2da7ae5b72dea6431:eb890192abad283f8301c493d2ff956e12296d38088bc170','bob@example.com','KK00000003','EMPLOYEE');
