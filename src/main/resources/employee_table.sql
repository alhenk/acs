DROP TABLE IF EXISTS "EMPLOYEES";
CREATE TABLE EMPLOYEES (
        id INTEGER PRIMARY KEY,
        firstName CHAR(40)  NOT NULL ,
        patronym CHAR(40),
        lastName CHAR (40)  NOT NULL ,
        birthDate CHAR (10) NOT NULL ,
        jobPosition CHAR (40),
        department CHAR (40),
        room CHAR(10),
        tableId CHAR (10),
        uid CHAR (20),
        UNIQUE (tableId),
        FOREIGN KEY (uid) REFERENCES RFIDTAGS(uid));
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('Iar','Blinov','1983.05.14','KK00000001','7AF52830');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('Anton','Keks','1980.08.02','KK00000002','CEBC3CF4');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('Bob','Martin','1952.01.01','KK00000003','EEE73CF4');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA' ,'1990.01.04.','KK00000254','CE6722DF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1991.05.13','KK00000256','E0040100594737350000');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1955.11.05','KK00000022','7A3AA118');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1978.07.17','KK00000127','8E2665EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1989.01.25','KK00000232','9E4368EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1983.08.07','KK00000074','0EA465EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1982.02.11','KK00000131','0EED69EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('Alexandr' ,'Koryagin','1967.06.10','KK00000017','FE0467EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1958.05.08','KK00000014','8EC668EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1986.08.03','KK00000077','E004010059474DE80000');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1976.10.28','KK00000018','E00401005947586D0000');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1986.11.26','KK00000115','6E016AEF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1984.06.23','KK00000090','BE7B68EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1989.08.23','KK00000261','9E4367EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1990.06.12','KK00000176','AEC068EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1990.03.07','KK00000240','0EC864EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1975.11.12','KK00000263','7EB065EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1974.12.04','KK00000182','5EDA64EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1986.11.17','KK00000132','BE1B66EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1987.11.02','KK00000118','5EFF68EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1977.04.21','KK00000173','0A722630');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1989.02.19','KK00000262','FAFA2630');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1987.10.14','KK00000236','8AD62630');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1961.11.29','KK00000221','AA212830');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1971.12.27','KK00000138','FA3B2730');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1989.03.05','KK00000272','BA6B2B30');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1978.09.02','KK00000095','8A4D2630');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1984.04.06','KK00000130','8A442630');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1984.09.10','KK00000128','4A262730');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1982.05.28','KK00000257','AA2AA418');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1976.05.03','KK00000184','7ADCA318');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1980.08.23','KK00000012','6ABB9D18');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1990.06.04','KK00000276','4E2EBBB5');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1990.08.02','KK00000287','2E8969EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1981.10.15','KK00000289','9EE168EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1989.10.03','KK00000292','AE2665EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1981.10.14','KK00000125','E0040100594794590000');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1983.08.21','KK00000062','8E5E65EF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1986.11.05','KK00000302','3ED812DF');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1973.06.23','KK00000007','E004010059476A8B0000');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1983.07.04','KK00000013','E0040100594750C60000');
INSERT INTO "EMPLOYEES" (firstName, lastName, birthDate, tableId, uid) VALUES('NA' ,'NA','1990.12.17','KK00000311','E0040100594767450000');
