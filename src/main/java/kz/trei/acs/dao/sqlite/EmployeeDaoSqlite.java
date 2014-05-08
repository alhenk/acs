package kz.trei.acs.dao.sqlite;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.office.structure.Account1CException;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.user.User;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.DateStampException;
import kz.trei.acs.util.FileManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public User findById(long id) throws DaoException {
        return null;
    }

    @Override
    public void create(Person entity) throws DaoException {

    }

    @Override
    public long totalNumber() throws DaoException {
        return 0;
    }

    @Override
    public void update(Person entity) throws DaoException {

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
                String uid = rs.getString("uid");
                String tableId = rs.getString("tableId");
                DepartmentType department=null;
                try {
                    department = DepartmentType.valueOf(rs.getString("department"));
                } catch (IllegalArgumentException e) {
                    LOGGER.debug("db attribute department is illegal " + e);
                    department = null;
                } catch (NullPointerException e) {
                    LOGGER.debug("db attribute department is null" + e);
                    department = null;
                }
                PositionType position = null;
                try{
                    position = PositionType.valueOf(rs.getString("jobPosition"));
                }catch (IllegalArgumentException e) {
                    LOGGER.debug("db attribute job position is illegal " + e);
                    department = null;
                } catch (NullPointerException e) {
                    LOGGER.debug("db attribute job position is null" + e);
                    department = null;
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

    }
}
