package kz.trei.acs.dao;

import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDao.class);

    public User find(String username, String password) throws Exception {
        LOGGER.debug("Entered User DAO");
        String userTable = PropertyManager.getValue("db.user.table");
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
}
