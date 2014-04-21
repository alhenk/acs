package kz.trei.acs.db;

import org.apache.log4j.Logger;

import java.sql.*;


public class DbManager {
    private static final Logger LOGGER = Logger.getLogger(DbManager.class);

    public static boolean isTableExist(String tablename){
        Connection conn = null;
        ResultSet tables = null;
        DatabaseMetaData dbm;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            conn = connectionPool.getConnection();
            dbm = conn.getMetaData();
            tables = dbm.getTables(null, null,tablename.toUpperCase(), null);
            if (tables.next()) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(conn);
        }
        return false;
    }
    public static void createUserTable(){
        Statement stat = null;
        Connection conn = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            conn = connectionPool.getConnection();
            stat =conn.createStatement();
            stat.execute("CREATE TABLE  Users (id INT, userName varchar(20), password varchar(32), userID varchar(32), userRole varchar(20) )");
            stat.execute("INSERT INTO Users VALUES (1,'admin', '123', '1234567890', 'ADMINISTRATOR')");
            stat.execute("INSERT INTO Users VALUES (2,'Alhen', '123', '0000000001', 'SUPERVISOR')");
            stat.execute("INSERT INTO Users VALUES (3,'Bob', '123', '0000000002', 'EMPLOYEE')");
            ResultSet rs;
            rs = stat.executeQuery("SELECT * FROM Users");
            while(rs.next()){
                LOGGER.debug(rs.getString("id")+"\t");
                LOGGER.debug(rs.getString("userName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            connectionPool.returnConnection(conn);
        }
    }
}
