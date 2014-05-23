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
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('admin','1000:700728b9c738f18c580ebf33101c3632fb60109d1c53ac2a:f41ac581b0c986d892bf9390918246d510ad42cabd2e9bb7','admin@example.com','KK00000263','ADMINISTRATOR');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('morgan','1000:4ab19b5ca2080e6b93197bc9e05a45ca6d38088c4b099c42:30e120e82ba245a99a8f608043a8577abf3b4fe3d1ab8d1a','morgan@trei.lan','KK00000302','EMPLOYEE');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('ballmer','1000:5e3de19a57309b9151302d1cfa6f2ff87b94629e79bc8f76:df4fa46d27381e6393e82276b836705690b50cdd46c833a0','ballmer@trei.lan','KK00000018','SUPERVISOR');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('alhen','1000:480eb71f4136210deef52f8b1f7953dbf41e0a9940105fb6:6106b12abd6b2f29c71ac28daeab868a8f7f243b2e02fcc2','alhen@trei.lan','KK00000017','ADMINISTRATOR');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('norton','1000:d84ef932eadad14e947fd22033b1b709f5b4f4f96a6d7e23:2b0ca92db3eac21bc13034578623d4fa0a331543938a030b','norton@trei.lan','KK00000254','EMPLOYEE');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('mkyong','1000:dcfd3db534e264199239282cd93b71d0bed878d7bcc5fcff:a2ebcef9ffbc1d32f34038872785e277bfffb42135d63f09','mkyong@trei.lan','KK00000240','EMPLOYEE');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('wozniak','1000:14e109268b94e7a2723c38078f497a8769ad6c89214839a8:fb629194d30d45d4a96f69647016c14c9ac20386bf5014b0','wozniak@trei.lan','KK00000074','SUPERVISOR');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('volozh','1000:0c0bd181cae14a78fc8379aa2e9b063cebe4d00f6c70c285:9c85fbdf93660362a3fc28ab5cdd9c91cb89b944c8746369','volozh@trei.lan','KK00000173','SUPERVISOR');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('julia','1000:73528bf2e0bfcd1b552305e2c8fd1c0774c97b4282a4e590:252549ccd7d8d50959892ffccc6d19c4415bf38596588047','julia@trei.lan','KK00000311','SUPERVISOR');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('torvalds','1000:c225f978b71ff90af8916f6340975f5a8373bdefbfad2629:8ef876ddd222ebf34ef907851ab26642afb14b7d675a883b','torvalds@trei.lan','KK00000007','EMPLOYEE');
INSERT INTO "USERS" (username, password, email, tableId, role) VALUES('durov','1000:3884d2bc017d42e61fa465d088d0a320599e8419d96e85d1:7959f9b3cd6d24697a6d813c941ec0286b0eb28c80222e07','vk@trei.lan','KK00000232','EMPLOYEE');