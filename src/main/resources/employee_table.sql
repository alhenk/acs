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
INSERT INTO "EMPLOYEES" VALUES(1,'Ihar',NULL,'Blinou','1966.04.03',NULL,NULL,NULL,'KK00000001','7AF52830');
INSERT INTO "EMPLOYEES" VALUES(2,'Anton',NULL,'Keks','1980.08.02','SOFTWARE_ENGINEER',NULL,NULL,'KK00000002','CEBC3CF4');
INSERT INTO "EMPLOYEES" VALUES(3,'Bob',NULL,'Martin','1952.01.01','LEAD_SOFTWARE_ENGINEER',NULL,NULL,'KK00000003','EEE73CF4');
INSERT INTO "EMPLOYEES" VALUES(4,'Peter',NULL,'Norton','1943.11.14.','SOFTWARE_ENGINEER','RESEARCH_AND_DEVELOPMENT','ROOM107','KK00000254','CE6722DF');
INSERT INTO "EMPLOYEES" VALUES(5,'Bruce',NULL,'Willis','1955.03.19','SECURITY_GUARD','ADMINISTRATIVE_SERVICE_UTILITY','ROOM101','KK00000022','7A3AA118');
INSERT INTO "EMPLOYEES" VALUES(6,'Игорь','Анатольевич','Данилов','1964.04.22','COMMISSIONING_ENGINEER','ASSEMBLY','ROOM102','KK00000127','8E2665EF');
INSERT INTO "EMPLOYEES" VALUES(7,'Павел','Валерьевич','Дуров','1984.10.10','SOFTWARE_ENGINEER','ENGINEERING','ROOM204','KK00000232','9E4368EF');
INSERT INTO "EMPLOYEES" VALUES(8,'Steve',NULL,'Wozniak','1950.08.11','TECHNICAL_DIRECTOR',NULL,'ROOM203','KK00000074','0EA465EF');
INSERT INTO "EMPLOYEES" VALUES(9,'Алишер','Бурханович','Усманов','1953.09.09','COMMERCIAL_DIRECTOR','COMMERCIAL','ROOM203','KK00000131','0EED69EF');
INSERT INTO "EMPLOYEES" VALUES(10,'Александр','Геннадьевич','Корягин','1967.06.10','DEPARTMENT_HEAD','RESEARCH_AND_DEVELOPMENT','ROOM107','KK00000017','FE0467EF');
INSERT INTO "EMPLOYEES" VALUES(11,'Christopher','Allen','Lloyd','1938.10.22','DEPARTMENT_HEAD','ASSEMBLY','ROOM102','KK00000014','8EC668EF');
INSERT INTO "EMPLOYEES" VALUES(12,'Борис','Георгиевич','Нуралиев','1958.07.18','LEAD_APCS_ENGINEER','ENGINEERING','ROOM204','KK00000077','E004010059474DE80000');
INSERT INTO "EMPLOYEES" VALUES(13,'Steve',NULL,'Ballmer','1956.03.24','GENERAL_DIRECTOR',NULL,'ROOM202','KK00000018','E00401005947586D0000');
INSERT INTO "EMPLOYEES" VALUES(14,'Eric',NULL,'Freeman','1986.11.26','ENGINEER_METROLOGIST','VERIFICATION_LABORATORY','ROOM105','KK00000115','6E016AEF');
INSERT INTO "EMPLOYEES" VALUES(15,'Bjarne',NULL,'Stroustrup','1950.12.30','LEAD_SOFTWARE_ENGINEER','ENGINEERING','ROOM204','KK00000090','BE7B68EF');
INSERT INTO "EMPLOYEES" VALUES(16,'Richard','Matthew','Stallman','1953.03.16','CHIEF_METROLOGIST','METROLOGICAL_SERVICE','ROOM105','KK00000261','9E4367EF');
INSERT INTO "EMPLOYEES" VALUES(17,'Анатолий','Эдуардович','Сердюков','1962.01.08','WAREHOUSE_MANAGER','WAREHOUSE','ROOM103','KK00000176','AEC068EF');
INSERT INTO "EMPLOYEES" VALUES(18,'Yong','Mook','Kim','1966.04.21','SOFTWARE_ENGINEER','ENGINEERING','ROOM204','KK00000240','0EC864EF');
INSERT INTO "EMPLOYEES" VALUES(19,'Евгений','Валентинович','Касперский','1965.10.04','SYSTEM_ADMINISTRATOR',NULL,'ROOM204','KK00000263','7EB065EF');
INSERT INTO "EMPLOYEES" VALUES(20,'Joshua',NULL,'Bloch','1961.08.28','ELECTRONICS_ENGINEER','PRODUCTION','ROOM207','KK00000182','5EDA64EF');
INSERT INTO "EMPLOYEES" VALUES(21,'James','Arthur','Gosling','1955.05.19','DESIGN_ENGINEER','ENGINEERING','ROOM204','KK00000132','BE1B66EF');
INSERT INTO "EMPLOYEES" VALUES(22,'Алексей','Леонидович','Пажитнов','1956.03.14','ELECTRONICS_ENGINEER','PRODUCTION','ROOM207','KK00000118','5EFF68EF');
INSERT INTO "EMPLOYEES" VALUES(23,'Аркадий','Юрьевич','Волож','1964.02.11','DEPARTMENT_HEAD','PRODUCTION','ROOM207','KK00000173','0A722630');
INSERT INTO "EMPLOYEES" VALUES(24,'Mark','Elliot','Zuckerberg','1984.05.14','EQUIPMENT_ENGINEER','COMMERCIAL','ROOM105','KK00000236','8AD62630');
INSERT INTO "EMPLOYEES" VALUES(25,'Сергей','Михайлович','Брин','1973.08.21','DEPUTY_TECHNICAL_DIRECTOR',NULL,'ROOM204','KK00000221','AA212830');
INSERT INTO "EMPLOYEES" VALUES(26,'Герман','Оскарович','Греф','1964.02.08','DEPUTY_CHIEF_ACCOUNTANT','ACCOUNTANCY','ROOM205','KK00000138','FA3B2730');
INSERT INTO "EMPLOYEES" VALUES(27,'Cory','Efram','Doctorow','1971.07.17','DESIGN_ENGINEER','ENGINEERING','ROOM204','KK00000272','BA6B2B30');
INSERT INTO "EMPLOYEES" VALUES(28,'Анатолий','Борисович','Чубайс','1955.06.16','HR_ADMINISTRATION','ACCOUNTANCY','ROOM205','KK00000095','8A4D2630');
INSERT INTO "EMPLOYEES" VALUES(29,'Paul',NULL,'Horowitz','1942.04.06','CONSTRUCTION_ELECTRICIAN','ASSEMBLY','ROOM102','KK00000130','8A442630');
INSERT INTO "EMPLOYEES" VALUES(30,'Lawrence',NULL,'Page','1973.03.26','LEAD_APCS_ENGINEER','ENGINEERING','ROOM204','KK00000128','4A262730');
INSERT INTO "EMPLOYEES" VALUES(31,'Константин','Борисович','Цзю','1969.09.19','SECURITY_GUARD','ADMINISTRATIVE_SERVICE_UTILITY','ROOM101','KK00000257','AA2AA418');
INSERT INTO "EMPLOYEES" VALUES(32,'Jason',NULL,'Statham','1967.07.26','DRIVER','ADMINISTRATIVE_SERVICE_UTILITY','ROOM101','KK00000184','7ADCA318');
INSERT INTO "EMPLOYEES" VALUES(33,'Hugo','Wallace','Weaving','1960.04.04','SECURITY_GUARD','ADMINISTRATIVE_SERVICE_UTILITY','ROOM101','KK00000012','6ABB9D18');
INSERT INTO "EMPLOYEES" VALUES(34,'Jack',NULL,'Sparrow','1990.06.04','ACCOUNTANT_MATERIALISTS','ACCOUNTANCY','ROOM205','KK00000276','4E2EBBB5');
INSERT INTO "EMPLOYEES" VALUES(35,'John','Matthew','Vlissides','1961.08.02','EQUIPMENT_ENGINEER','COMMERCIAL','ROOM105','KK00000287','2E8969EF');
INSERT INTO "EMPLOYEES" VALUES(36,'Kathy',NULL,'Sierra','1957.10.15','ACCOUNTANT_MATERIALISTS','ACCOUNTANCY','ROOM205','KK00000289','9EE168EF');
INSERT INTO "EMPLOYEES" VALUES(37,'Bruce',NULL,'Eckel','1957.07.08','DEPARTMENT_HEAD','VERIFICATION_LABORATORY','ROOM105','KK00000292','AE2665EF');
INSERT INTO "EMPLOYEES" VALUES(38,'Erich',NULL,'Gamma','1961.06.13','COMMISSIONING_ENGINEER','ASSEMBLY','ROOM102','KK00000125','E0040100594794590000');
INSERT INTO "EMPLOYEES" VALUES(39,'Эльвира','Сахипзадовна','Набиулина','1963.10.29','CHIEF_ACCOUNTANT','ACCOUNTANCY','ROOM205','KK00000062','8E5E65EF');
INSERT INTO "EMPLOYEES" VALUES(40,'Morgan',NULL,'Freeman','1937.07.01','JANITOR','ADMINISTRATIVE_SERVICE_UTILITY',NULL,'KK00000302','3ED812DF');
INSERT INTO "EMPLOYEES" VALUES(41,'Linus','Benedict','Torvalds','1969.12.28','LEAD_APCS_ENGINEER','ENGINEERING','ROOM204','KK00000007','E004010059476A8B0000');
INSERT INTO "EMPLOYEES" VALUES(42,'Ralph',NULL,'Johnson','1955.10.07','CONSTRUCTION_ELECTRICIAN','ASSEMBLY','ROOM102','KK00000013','E0040100594750C60000');
INSERT INTO "EMPLOYEES" VALUES(43,'Julia','Fiona','Roberts','1967.10.28','SECRETARY_ADVISER',NULL,'ROOM201','KK00000311','E0040100594767450000');
