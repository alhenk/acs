package kz.trei.acs.dao;

import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alhen on 4/25/14.
 */
public class UserDaoSqlite implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoSqlite.class);

    @Override
    public User find(String username, String password) throws Exception {
        LOGGER.debug("Entered User DAO");
        String userTable = PropertyManager.getValue("user.table");
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection conn = connectionPool.getConnection();
        LOGGER.debug("Got connection " + conn);
        Statement stat = conn.createStatement();

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            stat.close();
            connectionPool.returnConnection(conn);
            LOGGER.debug("There are no attributes username/password");
            throw new Exception("There are no attributes username/password");
        }
        ResultSet rs;
        LOGGER.debug("Before Query ");
        rs = stat.executeQuery("SELECT * FROM " + userTable +
                " WHERE username = '" + username + "' AND password = '" + password + "'");
        LOGGER.debug("Execute Query " + rs);
        if (rs.next()) {
            RoleType userRole = RoleType.valueOf(rs.getString("userRole"));
            String tableID = rs.getString("tableID");
            User user = new User(username, password, tableID, userRole);
            stat.close();
            connectionPool.returnConnection(conn);
            LOGGER.debug(user);
            return user;
        }
        LOGGER.debug("There is no such user");
        stat.close();
        connectionPool.returnConnection(conn);
        throw new Exception("There is no such user");
    }

    @Override
    public User find(long id) throws Exception {
        return null;
    }

    @Override
    public void create(User user) {
        Statement stat = null;
        Connection conn = null;
        ConnectionPool connectionPool=null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
        }
        String users = PropertyManager.getValue("user.table");
        String username = user.getUsername();
        String password = user.getPassword();
        RoleType userRole = user.getRole();
        String tableID = user.getTableId();
        try {
            conn = connectionPool.getConnection();
            stat = conn.createStatement();
            stat.execute("INSERT INTO " + users + "(username, password, tableID, userRole) VALUES ('"+username
                    +"', '"+password+"', '"+tableID+"', '"+userRole+"')");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
        }catch (ConnectionPoolException e) {
            LOGGER.error("get connection exception: " + e.getMessage());
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    LOGGER.error("SQL statement close exception: " + e.getMessage());
                }
            }
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public void createUserTable() {
        Statement stat = null;
        Connection conn = null;
        ResultSet rs = null;
        ConnectionPool connectionPool=null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
        }
        String users = PropertyManager.getValue("user.table");
        try {
            conn = connectionPool.getConnection();
            stat = conn.createStatement();
            stat.execute("CREATE TABLE " + users + " (id INTEGER PRIMARY KEY, username CHAR(20), password CHAR(32), tableID CHAR(32), userRole CHAR(20) )");
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
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection exception: " + e.getMessage());
        } finally {
            DbUtil.close(stat, rs);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public List<User> list() throws Exception{
        List<User> users = new LinkedList<User>();
        String userTable = PropertyManager.getValue("user.table");
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection conn = connectionPool.getConnection();
        Statement stat = conn.createStatement();
        ResultSet rs;
        rs = stat.executeQuery("SELECT * FROM " + userTable);
        User user;
        while (rs.next()){
            long id = Long.valueOf(rs.getString("id"));
            String username = rs.getString("username");
            String password = rs.getString("password");
            String tableId = rs.getString("tableId");
            RoleType role = RoleType.valueOf(rs.getString("userRole"));
            user = new User(id, username, password, tableId, role);
            users.add(user);
        }
        return users;
    }
}
