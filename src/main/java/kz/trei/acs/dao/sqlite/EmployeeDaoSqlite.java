package kz.trei.acs.dao.sqlite;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.rfid.UidFormatException;
import kz.trei.acs.office.structure.*;
import kz.trei.acs.user.User;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.DateStampException;
import kz.trei.acs.util.FileManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDaoSqlite implements EmployeeDao {
    private static final Logger LOGGER = Logger.getLogger(EmployeeDaoSqlite.class);

    @Override
    public void createTable() throws DaoException {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        String createStaffTableSql = FileManager.readFile("employee_table.sql");
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
            stmt.executeUpdate(createStaffTableSql);
            rs = stmt.executeQuery("SELECT * FROM STAFF");
            for (int i = 0; i < 20; i++) {
                if (rs.next()) {
                    LOGGER.debug(rs.getString("id") + "\t"
                            + rs.getString("firstName") + "\t"
                            + rs.getString("lastName") + "\t"
                            + rs.getString("tableId") + "\t"
                            + rs.getString("uid"));
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
    public Person findById(long id) throws DaoException {
        return null;
    }

    @Override
    public void create(Person employee) throws DaoException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement("INSERT INTO EMPLOYEES (firstName, patronym, lastName, birthDate, jobPosition, department, room, tableId, uid) VALUES (?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getPatronym());
            stmt.setString(3, employee.getLastName());
            stmt.setString(4, employee.getBirthDate().getDate().toString());
            stmt.setString(5, ((Employee)employee).getPosition().toString());
            stmt.setString(6, ((Employee)employee).getDepartment().toString());
            stmt.setString(7, ((Employee)employee).getRoom().toString());
            stmt.setString(8, ((Employee)employee).getAccount1C().getTableId());
            stmt.setString(9, ((Employee)employee).getRfidTag().getUid());
            stmt.execute();
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception : " + e.getMessage());
            CharSequence obj = "is not unique";
            String errorMessage="";
            if(e.getMessage().contains(obj)){
                errorMessage="error.db.not-unique";
                LOGGER.error("error.db.not-unique");
            }
            throw new DaoException(errorMessage);
        } catch (ConnectionPoolException e) {
            LOGGER.error("get connection exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public long totalNumber() throws DaoException {
        long totalNumber = 0;
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT count(*) AS totalNumber FROM EMPLOYEES;");
            while (rs.next()) {
                totalNumber = Long.valueOf(rs.getString("totalNumber"));
                LOGGER.debug("Total number of ROWS = " + totalNumber);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        return  totalNumber;
    }

    @Override
    public void update(Person entity) throws DaoException {

    }

    @Override
    public  List<Person> findInRange(long offset, long length) throws DaoException{
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        Person employee;
        List<Person> employees = new LinkedList<Person>();
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM EMPLOYEES LIMIT "+ length + " OFFSET "+offset);
            while (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String tableId = rs.getString("tableId");

                DepartmentType department=null;
                try {
                    department = DepartmentType.valueOf(rs.getString("department"));
                } catch (IllegalArgumentException e) {
                    LOGGER.debug("db attribute department is illegal " + e.getMessage());
                    department = DepartmentType.DEFAULT;
                } catch (NullPointerException e) {
                    LOGGER.debug("db attribute department is null " + e.getMessage());
                    department = DepartmentType.DEFAULT;
                }
                PositionType position = null;
                try{
                    position = PositionType.valueOf(rs.getString("jobPosition"));
                }catch (IllegalArgumentException e) {
                    LOGGER.debug("db attribute job position is illegal " + e);
                    position = PositionType.DEFAULT;
                } catch (NullPointerException e) {
                    LOGGER.debug("db attribute job position is null" + e);
                    position = PositionType.DEFAULT;
                }
                RoomType room;
                try{
                    room = RoomType.valueOf(rs.getString("room"));
                }catch (IllegalArgumentException e) {
                    LOGGER.debug("db attribute room is illegal " + e);
                    room = RoomType.DEFAULT;
                } catch (NullPointerException e) {
                    LOGGER.debug("db attribute room is null" + e);
                    room = RoomType.DEFAULT;
                }
                Account1C account1C;
                try {
                    account1C = Account1C.createId(tableId);
                } catch (Account1CException e) {
                    account1C = Account1C.defaultId();
                    LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
                }
                DateStamp birthDate;
                try {
                    birthDate = DateStamp.create(rs.getString("birthDate"));
                } catch (DateStampException e) {
                    birthDate = DateStamp.createEmptyDate();
                    LOGGER.debug("Assigned empty birth date due to exception: " + e.getMessage());
                }
                String uid;
                RfidTag rfidTag = new RfidTag();
                try {
                    rfidTag.setUid(rs.getString("uid"));
                } catch (UidFormatException e){
                    rfidTag.setEmptyUid();
                    LOGGER.debug("Assigned empty UID date due to exception: " + e.getMessage());
                }
                employee = new Employee.Builder()
                        .id(id)
                        .firstName(firstName)
                        .lastName(lastName)
                        .birthDate(birthDate)
                        .position(position)
                        .department(department)
                        .room(room)
                        .account1C(account1C)
                        .rfidTag(rfidTag)
                        .build();
                employees.add(employee);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("Select EMPLOYEES table exception: " + e.getMessage());
            throw new DaoException("Select EMPLOYEES table exception");
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        return employees;
    }
    @Override
    public List<Person> findAll() throws DaoException {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        Person employee;
        List<Person> employees = new LinkedList<Person>();
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
            while (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String tableId = rs.getString("tableId");
                String uid = rs.getString("uid");
                DepartmentType department=null;
                try {
                    department = DepartmentType.valueOf(rs.getString("department"));
                } catch (IllegalArgumentException e) {
                    LOGGER.debug("db attribute department is illegal " + e.getMessage());
                    department = DepartmentType.DEFAULT;
                } catch (NullPointerException e) {
                    LOGGER.debug("db attribute department is null" + e.getMessage());
                    department = DepartmentType.DEFAULT;
                }
                PositionType position = null;
                try{
                    position = PositionType.valueOf(rs.getString("jobPosition"));
                }catch (IllegalArgumentException e) {
                    LOGGER.debug("db attribute job position is illegal " + e);
                    position = PositionType.DEFAULT;
                } catch (NullPointerException e) {
                    LOGGER.debug("db attribute job position is null" + e);
                    position = PositionType.DEFAULT;
                }
                RoomType room;
                try{
                    room = RoomType.valueOf(rs.getString("room"));
                }catch (IllegalArgumentException e) {
                    LOGGER.debug("db attribute room is illegal " + e);
                    room = RoomType.DEFAULT;
                } catch (NullPointerException e) {
                    LOGGER.debug("db attribute room is null" + e);
                    room = RoomType.DEFAULT;
                }
                Account1C account1C;
                try {
                    account1C = Account1C.createId(tableId);
                } catch (Account1CException e) {
                    account1C = Account1C.defaultId();
                    LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
                }
                DateStamp birthDate;
                try {
                    birthDate = DateStamp.create(rs.getString("birthDate"));
                } catch (DateStampException e) {
                    birthDate = null;
                    LOGGER.debug("Birth Date field is invalid or empty");
                }
                RfidTag rfidTag = new RfidTag.Builder()
                        .uid(uid)
                        .build();
                employee = new Employee.Builder()
                        .id(id)
                        .firstName(firstName)
                        .lastName(lastName)
                        .birthDate(birthDate)
                        .position(position)
                        .department(department)
                        .room(room)
                        .account1C(account1C)
                        .rfidTag(rfidTag)
                        .build();
                employees.add(employee);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("Select EMPLOYEES table exception: " + e.getMessage());
            throw new DaoException("Select EMPLOYEES table exception");
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        return employees;
    }

    @Override
    public void delete(long id) throws DaoException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement("DELETE FROM EMPLOYEES WHERE id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }
}
