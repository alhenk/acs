CREATE VIEW OFFICEHOURS AS
SELECT  MIN.ID as id,
        MIN.FN as firstName,
        MIN.LN as lastName,
        MIN.JP as jobPosition,
        MIN.DP as department,
        MIN.UID as uid,
        MIN.D as dDate,
        MIN.T as Tmin,
        MAX.T as Tmax,
        Time(strftime('%s',MAX.T)-strftime('%s',MIN.T), 'unixepoch' ) as officeHours FROM

(SELECT E.id as ID, E.firstName as FN, E.lastName as LN, E.jobPosition as JP, E.department as DP, A.UID as UID, A.dDate as D, A.tTime as T FROM Attendance A, EMPLOYEES E WHERE A.UID=E.UID
EXCEPT
SELECT T1.ID as ID, T1.FN as FN, T1.LN as LN, T1.JP as JP, T1.DP as DP, T1.UID as UID, T1.D as D, T1.T as T
FROM
(SELECT E.id as ID, E.firstName as FN, E.lastName as LN, E.jobPosition as JP, E.department as DP, A.UID as UID, A.dDate as D, A.tTime as T FROM Attendance A, EMPLOYEES E WHERE A.UID=E.UID) as T1,
(SELECT E.id as ID, E.firstName as FN, E.lastName as LN, E.jobPosition as JP, E.department as DP, A.UID as UID, A.dDate as D, A.tTime as T FROM Attendance A, EMPLOYEES E WHERE A.UID=E.UID) as T2 WHERE T1.T>T2.T AND T1.UID =T2.UID AND T1.LN=T2.LN AND T1.D=T2.D) MIN,

(SELECT E.id as ID, E.firstName as FN, E.lastName as LN, E.jobPosition as JP, E.department as DP, A.UID as UID, A.dDate as D, A.tTime as T FROM Attendance A, EMPLOYEES E WHERE A.UID=E.UID
EXCEPT
SELECT T1.ID as ID, T1.FN as FN, T1.LN as LN, T1.JP as JP, T1.DP as DP, T1.UID as UID, T1.D as D, T1.T as T
FROM
(SELECT E.id as ID, E.firstName as FN, E.lastName as LN, E.jobPosition as JP, E.department as DP, A.UID as UID, A.dDate as D, A.tTime as T FROM Attendance A, EMPLOYEES E WHERE A.UID=E.UID) as T1,
(SELECT E.id as ID, E.firstName as FN, E.lastName as LN, E.jobPosition as JP, E.department as DP, A.UID as UID, A.dDate as D, A.tTime as T FROM Attendance A, EMPLOYEES E WHERE A.UID=E.UID) as T2 WHERE T1.T<T2.T AND T1.UID =T2.UID  AND T1.LN=T2.LN AND T1.D=T2.D)  MAX
WHERE
MIN.UID=MAX.UID AND MIN.LN=MAX.LN AND MAX.D=MIN.D;