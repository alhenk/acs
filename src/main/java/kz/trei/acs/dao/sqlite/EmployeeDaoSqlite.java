package kz.trei.acs.dao.sqlite;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.dao.util.DaoUtil;
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
        LOGGER.debug("createTable ... ");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        String createStaffTableSql = FileManager.readFile("employee_table.sql");
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
            stmt = conn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.executeUpdate(createStaffTableSql);
            rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
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
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public Person findById(long id) throws DaoException {
        LOGGER.debug("findById ... ");
        PreparedStatement stmt = null;
        ResultSet rs;
        Connection conn;
        ConnectionPool connectionPool;
        Person employee;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
            LOGGER.debug("Got connection " + conn);
            stmt = conn.prepareStatement("SELECT * FROM EMPLOYEES WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            LOGGER.debug("Execute Query " + rs);
            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String patronym = rs.getString("patronym");
                String lastName = rs.getString("lastName");
                DateStamp birthDate = DaoUtil.takeBirthDate(rs);
                PositionType position = DaoUtil.takePosition(rs);
                DepartmentType department = DaoUtil.takeDepartment(rs);
                RoomType room = DaoUtil.takeRoom(rs);
                Account1C account1C = DaoUtil.takeAccount1C(rs);
                RfidTag rfidTag = DaoUtil.takeRfidTag(rs);
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
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("There is no such employee");
        throw new DaoException("There is no such employee");
    }

    @Override
    public void create(Person employee) throws DaoException {
        LOGGER.debug("create ... ");
        PreparedStatement stmt = null;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
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
            LOGGER.debug("... the employee is created");
        } catch (SQLException e) {
            CharSequence obj = "is not unique";
            String errorMessage = "";
            if (e.getMessage().contains(obj)) {
                errorMessage = "error.db.not-unique";
                LOGGER.error("ROW NOT UNIQUE");
            }
            LOGGER.error("SQL INSERT into EMPLOYEES exception : " + e.getMessage());
            throw new DaoException(errorMessage);
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public long numberOfTuples() throws DaoException {
        LOGGER.debug("numberOfTuples ... ");
        long numTuples = 0;
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
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
            rs = stmt.executeQuery("SELECT count(*) AS numTuples FROM EMPLOYEES;");
            if (rs.next()) {
                numTuples = Long.valueOf(rs.getString("numTuples"));
            } else {
                LOGGER.error("Failed to count tuples in EMPLOYEES");
                throw new DaoException("Failed to count tuples in EMPLOYEES");
            }
        } catch (SQLException e) {
            LOGGER.error("SQL SELECT count(*) exception : " + e.getMessage());
            throw new DaoException("SQL SELECT count(*) exception : " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("... " + numTuples);
        return numTuples;
    }

    @Override
    public void update(Person employee) throws DaoException {
        LOGGER.debug("update ... ");
        PreparedStatement stmt = null;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
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
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public List<Person> findInRange(long offset, long limit) throws DaoException {
        LOGGER.debug("findInRange ... ");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        Person employee;
        List<Person> employees = new LinkedList<Person>();
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM EMPLOYEES LIMIT " + limit + " OFFSET " + offset);
            while (rs.next()) {
                long id = DaoUtil.takeId(rs);
                String firstName = rs.getString("firstName");
                String patronym = rs.getString("patronym");
                String lastName = rs.getString("lastName");
                DateStamp birthDate = DaoUtil.takeBirthDate(rs);
                PositionType position = DaoUtil.takePosition(rs);
                DepartmentType department = DaoUtil.takeDepartment(rs);
                RoomType room = DaoUtil.takeRoom(rs);
                Account1C account1C = DaoUtil.takeAccount1C(rs);
                RfidTag rfidTag = DaoUtil.takeRfidTag(rs);
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
        } catch (SQLException e) {
            LOGGER.error("Select EMPLOYEES table exception: " + e.getMessage());
            throw new DaoException("Select EMPLOYEES table exception: " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug(" ... " + employees.size() + " employees");
        return employees;
    }

    @Override
    public List<Person> findAll() throws DaoException {
        LOGGER.debug("findAll ... ");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        Person employee;
        List<Person> employees = new LinkedList<Person>();
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
            while (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                DepartmentType department = DaoUtil.takeDepartment(rs);
                PositionType position = DaoUtil.takePosition(rs);
                RoomType room = DaoUtil.takeRoom(rs);
                Account1C account1C = DaoUtil.takeAccount1C(rs);
                DateStamp birthDate = DaoUtil.takeBirthDate(rs);
                RfidTag rfidTag = DaoUtil.takeRfidTag(rs);
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
        } catch (SQLException e) {
            LOGGER.error("Select EMPLOYEES table exception: " + e.getMessage());
            throw new DaoException("Select EMPLOYEES table exception: " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug(" ... " + employees.size() + " employees");
        return employees;
    }

    @Override
    public void delete(long id) throws DaoException {
        LOGGER.debug("delete ... ");
        PreparedStatement stmt = null;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception" + e.getMessage());
        }
        try {
            stmt = conn.prepareStatement("DELETE FROM EMPLOYEES WHERE id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            LOGGER.debug("... id=" + id + "is deleted");
        } catch (SQLException e) {
            LOGGER.error("SQL DELETE form EMPLOYEES exception: " + e.getMessage());
            throw new DaoException("SQL DELETE form EMPLOYEES exception: " + e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }
}
