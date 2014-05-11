package kz.trei.acs.dao.h2;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.user.User;
import kz.trei.acs.util.FileManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EmployeeDaoH2 implements EmployeeDao {
    private static final Logger LOGGER = Logger.getLogger(EmployeeDaoH2.class);

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
        return null;
    }

    @Override
    public List<Person> findInRange(long offset, long length) throws DaoException {
        return null;
    }

    @Override
    public void delete(long id) throws DaoException {

    }
}
