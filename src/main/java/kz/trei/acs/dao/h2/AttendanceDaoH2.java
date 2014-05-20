package kz.trei.acs.dao.h2;

import kz.trei.acs.dao.AttendanceDao;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.office.attendance.Attendance;
import kz.trei.acs.user.User;
import kz.trei.acs.util.FileManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class AttendanceDaoH2 implements AttendanceDao {
    private static final Logger LOGGER = Logger.getLogger(AttendanceDaoH2.class);

    @Override
    public void createTable() throws DaoException {
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
