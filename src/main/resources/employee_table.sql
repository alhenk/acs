DROP TABLE IF EXISTS "EMPLOYEES";
CREATE TABLE EMPLOYEES (
  id          INTEGER PRIMARY KEY,
  firstName   CHAR(40) NOT NULL,
  patronym    CHAR(40),
  lastName    CHAR(40) NOT NULL,
  birthDate   CHAR(10) NOT NULL,
  jobPosition CHAR(40),
  department  CHAR(40),
  room        CHAR(10),
  tableId     CHAR(10),
  uid         CHAR(20),
  UNIQUE (tableId),
  FOREIGN KEY (uid) REFERENCES RFIDTAGS (uid));
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('Iar', NULL, 'Blinov', '1983.05.14', 'SENIOR_SOFTWARE_ENGINEER', NULL, NULL, 'KK00000001', '7AF52830');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('Anton', NULL, 'Keks', '1980.08.02', 'SOFTWARE_ENGINEER', NULL, NULL, 'KK00000002', 'CEBC3CF4');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('Bob', NULL, 'Martin', '1952.01.01', 'LEAD_SOFTWARE_ENGINEER', NULL, NULL, 'KK00000003', 'EEE73CF4');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1990.01.04.', 'SOFTWARE_ENGINEER', 'RESEARCH_AND_DEVELOPMENT', 'ROOM107', 'KK00000254', 'CE6722DF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('Bruce', NULL, 'Willis', '1955.03.19', 'SECURITY_GUARD', 'ADMINISTRATIVE_SERVICE_UTILITY', 'ROOM101', 'KK00000022', '7A3AA118');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1978.07.17', 'COMMISSIONING_ENGINEER', 'ASSEMBLY', 'ROOM102', 'KK00000127', '8E2665EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1989.01.25', 'SOFTWARE_ENGINEER', 'ENGINEERING', 'ROOM204', 'KK00000232', '9E4368EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1983.08.07', 'TECHNICAL_DIRECTOR', NULL, 'ROOM203', 'KK00000074', '0EA465EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1982.02.11', 'COMMERCIAL_DIRECTOR', 'COMMERCIAL', 'ROOM203', 'KK00000131', '0EED69EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('Alexandr',NULL, 'Koryagin', '1967.06.10', 'DEPARTMENT_HEAD', 'RESEARCH_AND_DEVELOPMENT', 'ROOM107', 'KK00000017', 'FE0467EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1958.05.08', 'DEPARTMENT_HEAD', 'ASSEMBLY', 'ROOM102', 'KK00000014', '8EC668EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1986.08.03', 'LEAD_APCS_ENGINEER', 'ENGINEERING', 'ROOM204', 'KK00000077', 'E004010059474DE80000');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1976.10.28', 'GENERAL_DIRECTOR', NULL, 'ROOM202', 'KK00000018', 'E00401005947586D0000');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1986.11.26', 'ENGINEER_METROLOGIST', 'VERIFICATION_LABORATORY', 'ROOM105', 'KK00000115', '6E016AEF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1984.06.23', 'LEAD_SOFTWARE_ENGINEER', 'ENGINEERING', 'ROOM204', 'KK00000090', 'BE7B68EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1989.08.23', 'CHIEF_METROLOGIST', 'METROLOGICAL_SERVICE', 'ROOM105', 'KK00000261', '9E4367EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1990.06.12', 'WAREHOUSE_MANAGER', 'WAREHOUSE', 'ROOM103', 'KK00000176', 'AEC068EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1990.03.07', 'SOFTWARE_ENGINEER', 'ENGINEERING', 'ROOM204', 'KK00000240', '0EC864EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1975.11.12', 'SYSTEM_ADMINISTRATOR', NULL, 'ROOM204', 'KK00000263', '7EB065EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1974.12.04', 'ELECTRONICS_ENGINEER', 'PRODUCTION', 'ROOM207', 'KK00000182', '5EDA64EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1986.11.17', 'DESIGN_ENGINEER', 'ENGINEERING', 'ROOM204', 'KK00000132', 'BE1B66EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1987.11.02', 'ELECTRONICS_ENGINEER', 'PRODUCTION', 'ROOM207', 'KK00000118', '5EFF68EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1977.04.21', 'DEPARTMENT_HEAD','PRODUCTION', 'ROOM207', 'KK00000173', '0A722630');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1987.10.14', 'EQUIPMENT_ENGINEER', 'COMMERCIAL', 'ROOM105', 'KK00000236', '8AD62630');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1961.11.29', 'DEPUTY_TECHNICAL_DIRECTOR', NULL, 'ROOM204', 'KK00000221', 'AA212830');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1971.12.27', 'DEPUTY_CHIEF_ACCOUNTANT', 'ACCOUNTANCY', 'ROOM205', 'KK00000138', 'FA3B2730');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1989.03.05', 'DESIGN_ENGINEER', 'ENGINEERING', 'ROOM204', 'KK00000272', 'BA6B2B30');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1978.09.02', 'HR_ADMINISTRATION', 'ACCOUNTANCY', 'ROOM205', 'KK00000095', '8A4D2630');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1984.04.06', 'CONSTRUCTION_ELECTRICIAN', 'ASSEMBLY', 'ROOM102', 'KK00000130', '8A442630');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1984.09.10', 'LEAD_APCS_ENGINEER', 'ENGINEERING', 'ROOM204', 'KK00000128', '4A262730');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('Константин', 'Борисович', 'Цзю', '1969.09.19', 'SECURITY_GUARD', 'ADMINISTRATIVE_SERVICE_UTILITY', 'ROOM101', 'KK00000257', 'AA2AA418');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1976.05.03', 'DRIVER', 'ADMINISTRATIVE_SERVICE_UTILITY', 'ROOM101', 'KK00000184', '7ADCA318');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1980.08.23', 'SECURITY_GUARD', 'ADMINISTRATIVE_SERVICE_UTILITY', 'ROOM101', 'KK00000012', '6ABB9D18');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1990.06.04', 'ACCOUNTANT_MATERIALISTS', 'ACCOUNTANCY', 'ROOM205', 'KK00000276', '4E2EBBB5');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1990.08.02', 'EQUIPMENT_ENGINEER', 'COMMERCIAL', 'ROOM105', 'KK00000287', '2E8969EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1981.10.15','ACCOUNTANT_MATERIALISTS', 'ACCOUNTANCY', 'ROOM205' , 'KK00000289', '9EE168EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1989.10.03', 'DEPARTMENT_HEAD', 'VERIFICATION_LABORATORY', 'ROOM105', 'KK00000292', 'AE2665EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1981.10.14', 'COMMISSIONING_ENGINEER', 'ASSEMBLY', 'ROOM102', 'KK00000125', 'E0040100594794590000');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1983.08.21', 'CHIEF_ACCOUNTANT', 'ACCOUNTANCY', 'ROOM205', 'KK00000062', '8E5E65EF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1986.11.05', 'JANITOR', 'ADMINISTRATIVE_SERVICE_UTILITY', NULL, 'KK00000302', '3ED812DF');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1973.06.23', 'LEAD_APCS_ENGINEER', 'ENGINEERING', 'ROOM204', 'KK00000007', 'E004010059476A8B0000');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1983.07.04', 'CONSTRUCTION_ELECTRICIAN', 'ASSEMBLY', 'ROOM102', 'KK00000013', 'E0040100594750C60000');
INSERT INTO "EMPLOYEES" (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid)
VALUES ('NA', NULL, 'NA', '1990.12.17', 'SECRETARY_ADVISER', NULL, 'ROOM201', 'KK00000311', 'E0040100594767450000');
