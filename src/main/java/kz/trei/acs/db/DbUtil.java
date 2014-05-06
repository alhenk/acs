package kz.trei.acs.db;

import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import java.sql.*;

public final class DbUtil {
    static {
        PropertyManager.load("configure.properties");
    }

    private static final Logger LOGGER = Logger.getLogger(DbUtil.class);
    public static final String CREATE_STAFF_TABLE=
            "CREATE TABLE IF NOT EXISTS STAFF (id INTEGER PRIMARY KEY, "
            +"firstName CHAR(40), patronym CHAR(40), lastName CHAR (40),"
            +"birthDate CHAR (10), jobPosition CHAR (40), department CHAR (40),"
            +"room CHAR(10), tableId CHAR (10), uid CHAR (40), UNIQUE (tableId) );"
            +"INSERT INTO STAFF (firstName, lastName, tableId)VALUES ('Iar','Blinov','KK00000001');"
            +"INSERT INTO STAFF (firstName, lastName, tableId)VALUES ('Anton','Keks','KK00000002');"
            +"INSERT INTO STAFF (firstName, lastName, tableId)VALUES ('Bob','Martin','KK00000003');";

    private DbUtil() {
    }

    public static boolean isTableExist(String tablename) {
        Connection conn = null;
        ResultSet tables;
        DatabaseMetaData dbm;
        ConnectionPool connectionPool=null;
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
