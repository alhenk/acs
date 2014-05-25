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
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.FileManager;
import kz.trei.acs.util.MonthType;
import kz.trei.acs.util.TimeStamp;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        Connection conn;
        ConnectionPool connectionPool;
        String createAttendanceTableSql = FileManager.readFile("attendance_table.sql");
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            stmt = conn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.executeUpdate(createAttendanceTableSql);
            rs = stmt.executeQuery("SELECT * FROM ATTENDANCE LIMIT 20 OFFSET 0");
            while (rs.next()) {
                LOGGER.debug(rs.getString("id") + "\t"
                        + rs.getString("uid") + "\t"
                        + rs.getString("dDate") + "\t"
                        + rs.getString("tTime"));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
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
        Connection conn;
        ConnectionPool connectionPool = null;
        String createOfficeHoursViewSql = FileManager.readFile("office_hours_view.sql");
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createOfficeHoursViewSql);
            rs = stmt.executeQuery("SELECT * FROM OFFICEHOURS LIMIT 20 OFFSET 0");
            while (rs.next()) {
                LOGGER.debug(rs.getString("LastName") + "\t"
                        + rs.getString("dDate") + "\t"
                        + rs.getString("Tmin") + "\t"
                        + rs.getString("Tmax") + "\t"
                        + rs.getString("officeHours"));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public List<OfficeHour> groupMonthlyReport(String year, String month, List<String> sorts) throws DaoException {
        LOGGER.debug("groupMonthlyReport ...");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        OfficeHour officeHour;
        String MonthDoubleDigitString = MonthType.valueOf(month.toUpperCase()).getMonthDoubleDigitString();
        List<OfficeHour> officeHourList = new LinkedList<OfficeHour>();
        StringBuilder groupBy = new StringBuilder();
        if (sorts.size() == 0) {
            groupBy.append("dDate, lastName");
        } else {
            for (String sort : sorts) {
                groupBy.append(sort);
                groupBy.append(",");
            }
            groupBy.deleteCharAt(groupBy.lastIndexOf(","));
        }
        groupBy.append(";");
        String groups = groupBy.toString();
        LOGGER.debug("SORT BY = " + groups);
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("Select * FROM OFFICEHOURS  WHERE substr(dDate,1,4)='"
                    + year + "' AND substr(dDate,6,2)='"
                    + MonthDoubleDigitString
                    + "' AND (Tmin > '09:00' OR Tmax < '18:00')AND Tmax<>Tmin ORDER BY " + groups);
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                PositionType position = DaoUtil.takePosition(rs);
                DepartmentType department = DaoUtil.takeDepartment(rs);
                DateStamp workingDay = DaoUtil.takeWorkingDay(rs);
                TimeStamp arriving = DaoUtil.takeArriving(rs);
                TimeStamp leaving = DaoUtil.takeLeaving(rs);
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
    public List<OfficeHour> individualMonthlyReport(String year, String month, Account1C account1C, List<String> sorts) throws DaoException {
        LOGGER.debug("groupMonthlyReport ...");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        OfficeHour officeHour;
        String MonthDoubleDigitString = MonthType.valueOf(month.toUpperCase()).getMonthDoubleDigitString();
        String tableId = account1C.getTableId();
        List<OfficeHour> officeHourList = new LinkedList<OfficeHour>();
        StringBuilder groupBy = new StringBuilder();
        if (sorts.size() == 0) {
            groupBy.append("dDate, lastName");
        } else {
            for (String sort : sorts) {
                groupBy.append(sort);
                groupBy.append(",");
            }
            groupBy.deleteCharAt(groupBy.lastIndexOf(","));
        }
        groupBy.append(";");
        String groups = groupBy.toString();
        LOGGER.debug("SORT BY = " + groups);
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("Select * FROM OFFICEHOURS  WHERE substr(dDate,1,4)='"
                    + year + "' AND substr(dDate,6,2)='"
                    + MonthDoubleDigitString
                    + "' AND (Tmin > '09:00' OR Tmax < '18:00')AND Tmax<>Tmin AND tableId='"
                    + tableId
                    + "' ORDER BY " + groups);
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                PositionType position = DaoUtil.takePosition(rs);
                DepartmentType department = DaoUtil.takeDepartment(rs);
                DateStamp workingDay = DaoUtil.takeWorkingDay(rs);
                TimeStamp arriving = DaoUtil.takeArriving(rs);
                TimeStamp leaving = DaoUtil.takeLeaving(rs);
                TimeStamp total = DaoUtil.takeOfficeHours(rs);
                RfidTag rfidTag = DaoUtil.takeRfidTag(rs);
                Person employee = new Employee.Builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .position(position)
                        .department(department)
                        .rfidTag(rfidTag)
                        .account1C(account1C)
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
    public List<OfficeHour> groupDailyReport(DateStamp dateStamp, List<String> sorts) throws DaoException {
        LOGGER.debug("groupDailyReport ...");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        OfficeHour officeHour;
        List<OfficeHour> officeHourList = new LinkedList<OfficeHour>();
        StringBuilder groupBy = new StringBuilder();
        if (sorts.size() == 0) {
            groupBy.append("dDate, lastName");
        } else {
            for (String sort : sorts) {
                groupBy.append(sort);
                groupBy.append(",");
            }
            groupBy.deleteCharAt(groupBy.lastIndexOf(","));
        }
        groupBy.append(";");
        String groups = groupBy.toString();
        LOGGER.debug("SORT BY = " + groups);
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        String date = dateStamp.getDate();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("Select * FROM OFFICEHOURS  WHERE dDate = '"
                    + date
                    + "' AND (Tmin > '09:00' OR Tmax < '18:00') AND Tmax<>Tmin ORDER BY " + groups);
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                PositionType position = DaoUtil.takePosition(rs);
                DepartmentType department = DaoUtil.takeDepartment(rs);
                DateStamp workingDay = DaoUtil.takeWorkingDay(rs);
                TimeStamp arriving = DaoUtil.takeArriving(rs);
                TimeStamp leaving = DaoUtil.takeLeaving(rs);
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
    public List<OfficeHour> individualDailyReport(DateStamp dateStamp, Account1C account1C, List<String> sorts) throws DaoException {
        LOGGER.debug("individualDailyReport ...");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        OfficeHour officeHour;
        String tableId = account1C.getTableId();
        List<OfficeHour> officeHourList = new LinkedList<OfficeHour>();
        StringBuilder groupBy = new StringBuilder();
        if (sorts.size() == 0) {
            groupBy.append("dDate, lastName");
        } else {
            for (String sort : sorts) {
                groupBy.append(sort);
                groupBy.append(",");
            }
            groupBy.deleteCharAt(groupBy.lastIndexOf(","));
        }
        groupBy.append(";");
        String groups = groupBy.toString();
        LOGGER.debug("SORT BY = " + groups);
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        String date = dateStamp.getDate();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("Select * FROM OFFICEHOURS  WHERE dDate = '"
                    + date
                    + "' AND (Tmin > '09:00' OR Tmax < '18:00') AND Tmax<>Tmin AND tableId='"
                    + tableId
                    + "' ORDER BY " + groups);
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                PositionType position = DaoUtil.takePosition(rs);
                DepartmentType department = DaoUtil.takeDepartment(rs);
                DateStamp workingDay = DaoUtil.takeWorkingDay(rs);
                TimeStamp arriving = DaoUtil.takeArriving(rs);
                TimeStamp leaving = DaoUtil.takeLeaving(rs);
                TimeStamp total = DaoUtil.takeOfficeHours(rs);
                RfidTag rfidTag = DaoUtil.takeRfidTag(rs);
                Person employee = new Employee.Builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .position(position)
                        .department(department)
                        .rfidTag(rfidTag)
                        .account1C(account1C)
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
        throw new NotImplementedException();
    }

    @Override
    public void create(Attendance entity) throws DaoException {
        throw new NotImplementedException();
    }

    @Override
    public long numberOfTuples() throws DaoException {
        throw new NotImplementedException();
    }

    @Override
    public void update(Attendance entity) throws DaoException {
        throw new NotImplementedException();
    }

    @Override
    public List<Attendance> findAll() throws DaoException {
        throw new NotImplementedException();
    }

    @Override
    public List<Attendance> findInRange(long offset, long limit) throws DaoException {
        throw new NotImplementedException();
    }

    @Override
    public void delete(long id) throws DaoException {
        throw new NotImplementedException();
    }
}
