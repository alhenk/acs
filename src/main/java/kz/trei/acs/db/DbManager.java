package kz.trei.acs.db;

import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import java.sql.*;

public final class DbManager {
    static {
        PropertyManager.load("configure.properties");
    }

    private static final Logger LOGGER = Logger.getLogger(DbManager.class);

    private DbManager() {
    }

    public static boolean isTableExist(String tablename) {
        Connection conn = null;
        ResultSet tables = null;
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

    public static void createUserTable() {
        Statement stat = null;
        Connection conn = null;
        ResultSet rs = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        String users = PropertyManager.getValue("user.table");
        try {
            conn = connectionPool.getConnection();
            stat = conn.createStatement();
            stat.execute("CREATE TABLE " + users + " (id bigint auto_increment, username varchar(20), password varchar(32), tableID varchar(32), userRole varchar(20) )");
            stat.execute("INSERT INTO " + users + "(username, password, tableID, userRole) VALUES ('admin', '123', '1234567890', 'ADMINISTRATOR')");
            stat.execute("INSERT INTO " + users + "(username, password, tableID, userRole) VALUES ('Alhen', '123', '0000000001', 'SUPERVISOR')");
            stat.execute("INSERT INTO " + users + "(username, password, tableID, userRole) VALUES ('Bob', '123', '0000000002', 'EMPLOYEE')");

            rs = stat.executeQuery("SELECT * FROM " + users);
            while (rs.next()) {
                LOGGER.debug(rs.getString("id") + "\t");
                LOGGER.debug(rs.getString("username"));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            e.printStackTrace();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection exception: " + e.getMessage());
        } finally {
            close(stat, rs);
            connectionPool.returnConnection(conn);
        }
    }

    private static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("SQL statement close exception: " + e.getMessage());
            }
        }
    }

    private static void close(Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error("Result Set close exception: " + e.getMessage());
            }
        }
    }
}
