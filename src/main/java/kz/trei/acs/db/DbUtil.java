package kz.trei.acs.db;

import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import java.sql.*;

public final class DbUtil {
    static {
        PropertyManager.load("configure.properties");
    }

    private static final Logger LOGGER = Logger.getLogger(DbUtil.class);

    private DbUtil() {
    }

    public static boolean isTableExist(String tablename) {
        Connection conn = null;
        ResultSet tables;
        DatabaseMetaData dbm;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
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
