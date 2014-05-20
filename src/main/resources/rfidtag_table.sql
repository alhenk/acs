DROP TABLE IF EXISTS "RFIDTAGS";
CREATE TABLE RFIDTAGS (
  id INTEGER PRIMARY KEY,
  uid CHAR (20),
  type CHAR(10),
  protocol CHAR(16),
  issueDate CHAR(10),
  expirationDate CHAR(10),
  UNIQUE (uid));
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('7AF52830','CARD','ISO14443A','2014.05.18' ,'2015.05.18' );
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('CEBC3CF4','KEYFOB','ISO14443A','2014.04.23' ,'2015.04.23');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('EEE73CF4','CARD','ISO14443A','2014.01.14' ,'2015.01.14');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('CE6722DF','CARD','ISO14443A','2014.04.03' ,'2015.04.03');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('E0040100594737350000','CARD','ISO15693','2014.04.17' ,'2015.04.17');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('7A3AA118','CARD','ISO14443A','2014.03.11' ,'2015..03.11');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('8E2665EF','CARD','ISO14443A','2014.05.08' ,'2015.05.08');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('9E4368EF','CARD','ISO14443A','2014.04.17' ,'2015.04.17');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('0EA465EF','CARD','ISO14443A','2013.08.03' ,'2014.08.03');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('0EED69EF','CARD','ISO14443A','2012.11.23' ,'2013.11.23');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('FE0467EF','CARD','ISO14443A','2014.04.17' ,'2015.04.17');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('8EC668EF','CARD','ISO14443A','2013.08.03' ,'2014.08.03');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('E004010059474DE80000','CARD','ISO15693','2013.08.03' ,'2014.08.03');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('E00401005947586D0000','CARD','ISO15693','2013.08.03' ,'2014.08.03');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('6E016AEF','CARD','ISO14443A','2013.09.30' ,'2014.09.30');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('BE7B68EF','CARD','ISO14443A','2013.07.07' ,'2014.07.07');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('9E4367EF','CARD','ISO14443A','2014.04.14' ,'2015.04.14');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('AEC068EF','CARD','ISO14443A','2013.01.08' ,'2014.01.08');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('0EC864EF','CARD','ISO14443A','2014.04.17' ,'2015.04.17');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('7EB065EF','CARD','ISO14443A','2012.11.23' ,'2013.11.23');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('5EDA64EF','CARD','ISO14443A','2012.11.23' ,'2013.11.23');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('BE1B66EF','CARD','ISO14443A','2013.07.02' ,'2014.07.02');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('5EFF68EF','CARD','ISO14443A','2013.01.17' ,'2014.01.17');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('0A722630','CARD','ISO14443A','2014.05.08' ,'2015.05.08');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('FAFA2630','CARD','ISO14443A',NULL ,NULL);
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('8AD62630','CARD','ISO14443A',NULL ,NULL);
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('AA212830','CARD','ISO14443A','2012.10.11' ,'2013.10.11');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('FA3B2730','CARD','ISO14443A',NULL ,NULL);
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('BA6B2B30','CARD','ISO14443A',NULL ,NULL);
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('8A4D2630','CARD','ISO14443A','2013.07.02' ,'2014.07.02');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('8A442630','CARD','ISO14443A',NULL ,NULL);
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('4A262730','CARD','ISO14443A','2013.07.02' ,'2014.07.02');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('AA2AA418','CARD','ISO14443A','2012.07.21' ,'2013.07.21');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('7ADCA318','CARD','ISO14443A',NULL ,NULL);
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('6ABB9D18','CARD','ISO14443A','2014.05.08' ,'2015.05.08');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('4E2EBBB5','CARD','ISO14443A',NULL ,NULL);
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('2E8969EF','CARD','ISO14443A','2013.07.02' ,'2014.07.02');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('9EE168EF','CARD','ISO14443A',NULL ,NULL);
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('AE2665EF','CARD','ISO14443A','2013.07.02' ,'2014.07.02');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('E0040100594794590000','CARD','ISO15693',NULL ,NULL);
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('8E5E65EF','CARD','ISO14443A','2014.02.12' ,'2015.02.12');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('3ED812DF','CARD','ISO14443A',NULL ,NULL);
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('E004010059476A8B0000','CARD','ISO15693','2014.05.11' ,'2015.05.11');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('E0040100594750C60000','CARD','ISO15693','2014.02.11' ,'2015.02.11');
INSERT INTO "RFIDTAGS" (uid, type, protocol, issueDate, expirationDate) VALUES('E0040100594767450000','CARD','ISO15693','2014.02.03' ,'2015.02.03');