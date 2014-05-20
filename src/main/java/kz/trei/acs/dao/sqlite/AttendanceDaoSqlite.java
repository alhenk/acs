package kz.trei.acs.dao.sqlite;

import kz.trei.acs.dao.AttendanceDao;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.util.DaoUtil;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.office.attendance.Attendance;
import kz.trei.acs.office.attendance.OfficeHour;
import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.rfid.UidFormatException;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.FileManager;
import kz.trei.acs.util.TimeStamp;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class AttendanceDaoSqlite implements AttendanceDao {
    private static final Logger LOGGER = Logger.getLogger(AttendanceDaoSqlite.class);

    @Override
    public void createTable() throws DaoException {
        LOGGER.debug("createTable ...");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        String createAttendanceTableSql = FileManager.readFile("attendance_table.sql");
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.executeUpdate(createAttendanceTableSql);
            rs = stmt.executeQuery("SELECT * FROM ATTENDANCE");
            for (int i = 0; i < 20; i++) {
                if (rs.next()) {
                    LOGGER.debug(rs.getString("id") + "\t"
                            + rs.getString("uid") + "\t"
                            + rs.getString("dDate") + "\t"
                            + rs.getString("tTime"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public void createView() throws DaoException {
        LOGGER.debug("createView ...");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        String createOfficeHoursViewSql = FileManager.readFile("office_hours_view.sql");
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(createOfficeHoursViewSql);
            rs = stmt.executeQuery("SELECT * FROM OFFICEHOURS");
            for (int i = 0; i < 20; i++) {
                if (rs.next()) {
                    LOGGER.debug(rs.getString("LastName") + "\t"
                            + rs.getString("dDate") + "\t"
                            + rs.getString("Tmin") + "\t"
                            + rs.getString("Tmax") + "\t"
                            + rs.getString("officeHours"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public List<OfficeHour> lateArrivalReport(DateStamp date) throws DaoException {
        LOGGER.debug("lateArrivalReport ...");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        OfficeHour officeHour;
        List<OfficeHour> officeHourList = new LinkedList<OfficeHour>();
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("Select * FROM OFFICEHOURS WHERE substr(dDate,1,4)='2013' AND substr(dDate,6,2)='04' AND Tmin > '09:00' AND Tmax<>Tmin GROUP BY dDate, lastName;");
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                PositionType position = DaoUtil.takePosition(rs);
                DepartmentType department = DaoUtil.takeDepartment(rs);
                DateStamp workingDay = DaoUtil.takeWorkingDay(rs);
                TimeStamp arriving = DaoUtil.takeArriving(rs);
                TimeStamp leaving =  DaoUtil.takeLeaving(rs);
                TimeStamp total = DaoUtil.takeOfficeHours(rs);
                RfidTag rfidTag = DaoUtil.takeRfidTag(rs);
                Person employee = new Employee.Builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .position(position)
                        .department(department)
                        .rfidTag(rfidTag)
                        .build();
                officeHour = new OfficeHour.Builder()
                        .employee(employee)
                        .workingDay(workingDay)
                        .arriving(arriving)
                        .leaving(leaving)
                        .total(total)
                        .build();
                officeHourList.add(officeHour);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (UidFormatException e) {
            LOGGER.error("RFID tag building exception: " + e.getMessage());
            throw new DaoException("RFID tag building exception " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug(" ... " + officeHourList.size() + " office hour records");
        return officeHourList;
    }

    @Override
    public List<OfficeHour> leavingBeforeReport(DateStamp date) throws DaoException {
        LOGGER.debug("avingBeforeReport ...");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        OfficeHour officeHour;
        List<OfficeHour> officeHourList = new LinkedList<OfficeHour>();
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("Select * FROM OFFICEHOURS WHERE substr(dDate,1,4)='2013' AND substr(dDate,6,2)='04' AND Tmax < '18:00' AND Tmax<>Tmin GROUP BY dDate, lastName;");
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String uid = rs.getString("UID");
                DateStamp workingDay = DateStamp.create(rs.getString("dDate"));
                TimeStamp arriving = TimeStamp.create(rs.getString("Tmin"));
                TimeStamp leaving = TimeStamp.create(rs.getString("Tmax"));
                TimeStamp total = TimeStamp.create(rs.getString("officeHours"));
                RfidTag rfidTag = new RfidTag.Builder()
                        .uid(uid)
                        .build();
                Person employee = new Employee.Builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .rfidTag(rfidTag)
                        .build();
                officeHour = new OfficeHour.Builder()
                        .employee(employee)
                        .workingDay(workingDay)
                        .arriving(arriving)
                        .leaving(leaving)
                        .total(total)
                        .build();
                officeHourList.add(officeHour);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (UidFormatException e) {
            LOGGER.error("RFID tag building exception: " + e.getMessage());
            throw new DaoException("RFID tag building exception " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug(" ... " + officeHourList.size() + " office hour records");
        return officeHourList;
    }

    @Override
    public Attendance findById(long id) throws DaoException {
        return null;
    }

    @Override
    public void create(Attendance entity) throws DaoException {

    }

    @Override
    public long numberOfTuples() throws DaoException {
        return 0;
    }

    @Override
    public void update(Attendance entity) throws DaoException {

    }

    @Override
    public List<Attendance> findAll() throws DaoException {
        return null;
    }

    @Override
    public List<Attendance> findInRange(long offset, long limit) throws DaoException {
        return null;
    }

    @Override
    public void delete(long id) throws DaoException {

    }
}
