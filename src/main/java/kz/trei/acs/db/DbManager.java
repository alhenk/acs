package kz.trei.acs.db;

import org.apache.log4j.Logger;

import java.sql.*;


public class DbManager {
    private static final Logger LOGGER = Logger.getLogger(DbManager.class);

    public static boolean isTableExist(String tablename){
        Connection conn = null;
        ResultSet tables = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/acs");
            DatabaseMetaData dbm = conn.getMetaData();
            tables = dbm.getTables(null, null,tablename.toUpperCase(), null);
            if (tables.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    public static void createUserTable(){
        Statement stat = null;
        Connection conn = null;

        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/acs");
            stat =conn.createStatement();
            stat.execute("CREATE TABLE  Users (id INT, userName varchar(20), password varchar(32), userID varchar(32), userRole varchar(20) )");
            stat.execute("INSERT INTO Users VALUES (1,'admin', '123', '1234567890', 'ADMINISTRATOR')");
            stat.execute("INSERT INTO Users VALUES (2,'Alhen', '123', '0000000001', 'SUPERVISOR')");
            ResultSet rs;
            rs = stat.executeQuery("SELECT * FROM Users");

            while(rs.next()){
                LOGGER.debug(rs.getString("id")+"\t");
                LOGGER.debug(rs.getString("userName"));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
