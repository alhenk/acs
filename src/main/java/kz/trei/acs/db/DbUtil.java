package kz.trei.acs.db;

import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import java.sql.*;

public final class DbUtil {
    private static final Logger LOGGER = Logger.getLogger(DbUtil.class);

    static {
        PropertyManager.load("configure.properties");
    }

    private DbUtil() {
    }

    public static void convertStaffDb(String dbName) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String uid, date, time;
        String[] splittedDate = new String[3];
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:STAFF_DB.3db");
            stmt=conn.prepareStatement("CREATE TABLE ATTEND (id INTEGER PRIMARY KEY, uid CHAR (20), direction CHAR(8), dDate CHAR(10), tTime CHAR(8));");
            stmt.executeUpdate();
            stmt = conn.prepareStatement("SELECT * FROM ATTENDANCE");
            rs = stmt.executeQuery();
            while (rs.next()) {
                uid = rs.getString("UID");
                date = rs.getString("Date");
                time = rs.getString("Time");
                splittedDate = date.split("\\.");
                date = splittedDate[2] +"."+ splittedDate[1] +"."+  splittedDate[0];
                LOGGER.debug("UID : " + uid + " DATE -> " + date + " TIME -> " + time);
                stmt = conn.prepareStatement("INSERT INTO ATTEND (uid, dDate, tTime) VALUES (?,?,?)");
                stmt.setString(1, uid);
                stmt.setString(2, date);
                stmt.setString(3, time);
                stmt.executeUpdate();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(stmt, rs);
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isTableExist(String tablename) {
        Connection conn = null;
        ResultSet tables;
        DatabaseMetaData dbm;
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
        }
        try {
            conn = connectionPool.getConnection();
            dbm = conn.getMetaData();
            tables = dbm.getTables(null, null, tablename.toUpperCase(), null);
            if (tables.next()) return true;
            return false;
        } catch (SQLException e) {
            LOGGER.error("get table " + tablename + " exception: " + e.getMessage());
        } catch (ConnectionPoolException e) {
            LOGGER.error("get connection exception: " + e.getMessage());
        } finally {
            connectionPool.returnConnection(conn);
        }
        return false;
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("SQL statement close exception: " + e.getMessage());
            }
        }
    }

    public static void close(Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error("Result Set close exception: " + e.getMessage());
            }
        }
    }
}
