package kz.trei.acs.dao.sqlite;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.dao.EmployeeDaoUtil;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.office.structure.RoomType;
import kz.trei.acs.util.DateStamp;
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
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
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
            LOGGER.error("SQL CREATE table EMPLOYEES or SELECT from it exception : " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception: " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public Person findById(long id) throws DaoException {
        PreparedStatement stmt = null;
        ResultSet rs;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        Person employee;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
            stmt = conn.prepareStatement("SELECT * FROM EMPLOYEES WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            LOGGER.debug("Execute Query " + rs);
            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String patronym = rs.getString("patronym");
                String lastName = rs.getString("lastName");
                DateStamp birthDate = EmployeeDaoUtil.takeBirthDateFromResult(rs);
                PositionType position = EmployeeDaoUtil.takePositionFromResult(rs);
                DepartmentType department = EmployeeDaoUtil.takeDepartmentFromResult(rs);
                RoomType room = EmployeeDaoUtil.takeRoomFromResult(rs);
                Account1C account1C = EmployeeDaoUtil.takeAccount1CFromResult(rs);
                RfidTag rfidTag = EmployeeDaoUtil.takeRfidTagFromResult(rs);
                employee = new Employee.Builder()
                        .id(id)
                        .firstName(firstName)
                        .patronym(patronym)
                        .lastName(lastName)
                        .birthDate(birthDate)
                        .position(position)
                        .department(department)
                        .room(room)
                        .account1C(account1C)
                        .rfidTag(rfidTag)
                        .build();
                LOGGER.debug("Found by id " + employee);
                return employee;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL SELECT by ID exception : " + e.getMessage());
            throw new DaoException("SQL SELECT by ID exception" + e.getMessage());
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection exception: " + e.getMessage());
            throw new DaoException("Get connection exception: " + e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("There is no such employee");
        throw new DaoException("There is no such employee");
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
            stmt.setString(4, employee.getBirthDate().getDate());
            stmt.setString(5, ((Employee) employee).getPosition().toString());
            stmt.setString(6, ((Employee) employee).getDepartment().toString());
            stmt.setString(7, ((Employee) employee).getRoom().toString());
            stmt.setString(8, ((Employee) employee).getAccount1C().getTableId());
            stmt.setString(9, ((Employee) employee).getRfidTag().getUid());
            stmt.execute();
        } catch (SQLException e) {
            CharSequence obj = "is not unique";
            String errorMessage = "";
            if (e.getMessage().contains(obj)) {
                errorMessage = "error.db.not-unique";
                LOGGER.error("ROW NOT UNIQUE");
            }
            LOGGER.error("SQL INSERT into EMPLOYEES exception : " + e.getMessage());
            throw new DaoException(errorMessage);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection exception: " + e.getMessage());
            throw new DaoException("Get connection exception: " + e.getMessage());
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
            LOGGER.error("SQL SELECT count(*) exception : " + e.getMessage());
            throw new DaoException("SQL SELECT count(*) exception : " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        return totalNumber;
    }

    @Override
    public void update(Person employee) throws DaoException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement("UPDATE EMPLOYEES SET firstName=?, patronym=?, lastName=?, birthDate=?, jobPosition=?, department=?, room=?, tableId=?, uid=? WHERE id = ?");
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getPatronym());
            stmt.setString(3, employee.getLastName());
            stmt.setString(4, employee.getBirthDate().getDate());
            stmt.setString(5, ((Employee) employee).getPosition().toString());
            stmt.setString(6, ((Employee) employee).getDepartment().toString());
            stmt.setString(7, ((Employee) employee).getRoom().toString());
            stmt.setString(8, ((Employee) employee).getAccount1C().getTableId());
            stmt.setString(9, ((Employee) employee).getRfidTag().getUid());
            stmt.setString(10, Long.toString(((Employee) employee).getId()));
            stmt.executeUpdate();
            LOGGER.debug("id : " + ((Employee) employee).getId());
            LOGGER.debug("First name : " + employee.getFirstName());
            LOGGER.debug("Last name : " + employee.getLastName());
            LOGGER.debug("Job position : " + ((Employee) employee).getPosition());
            LOGGER.debug("Table ID : " + ((Employee) employee).getAccount1C().getTableId());
        } catch (SQLException e) {
            LOGGER.error("SQL UPDATE EMPLOYEES exception: " + e.getMessage());
            throw new DaoException("SQL UPDATE EMPLOYEES exception: " + e.getMessage());
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection exception: " + e.getMessage());
            throw new DaoException("Get connection exception: " + e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public List<Person> findInRange(long offset, long length) throws DaoException {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool;
        Person employee;
        List<Person> employees = new LinkedList<Person>();
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM EMPLOYEES LIMIT " + length + " OFFSET " + offset);
            while (rs.next()) {
                long id = EmployeeDaoUtil.takeIdFromResult(rs);
                String firstName = rs.getString("firstName");
                String patronym = rs.getString("patronym");
                String lastName = rs.getString("lastName");
                DateStamp birthDate = EmployeeDaoUtil.takeBirthDateFromResult(rs);
                PositionType position = EmployeeDaoUtil.takePositionFromResult(rs);
                DepartmentType department = EmployeeDaoUtil.takeDepartmentFromResult(rs);
                RoomType room = EmployeeDaoUtil.takeRoomFromResult(rs);
                Account1C account1C = EmployeeDaoUtil.takeAccount1CFromResult(rs);
                RfidTag rfidTag = EmployeeDaoUtil.takeRfidTagFromResult(rs);
                employee = new Employee.Builder()
                        .id(id)
                        .firstName(firstName)
                        .patronym(patronym)
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
            throw new DaoException("Select EMPLOYEES table exception: " + e.getMessage());
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
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
            while (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                DepartmentType department = EmployeeDaoUtil.takeDepartmentFromResult(rs);
                PositionType position = EmployeeDaoUtil.takePositionFromResult(rs);
                RoomType room = EmployeeDaoUtil.takeRoomFromResult(rs);
                Account1C account1C = EmployeeDaoUtil.takeAccount1CFromResult(rs);
                DateStamp birthDate = EmployeeDaoUtil.takeBirthDateFromResult(rs);
                RfidTag rfidTag = EmployeeDaoUtil.takeRfidTagFromResult(rs);
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
            throw new DaoException("Select EMPLOYEES table exception: " + e.getMessage());
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
            throw new DaoException("Get connection pool instance exception" + e.getMessage());
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
            LOGGER.error("SQL DELETE form EMPLOYEES exception: " + e.getMessage());
            throw new DaoException("SQL DELETE form EMPLOYEES exception: " + e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }


}
