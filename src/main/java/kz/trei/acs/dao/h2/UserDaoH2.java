package kz.trei.acs.dao.h2;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.office.structure.Account1C;
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

public class UserDaoH2 implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoH2.class);

    @Override
    public User find(String username, String password) throws DaoException {
        String userTable = PropertyManager.getValue("user.table");
        Statement stat = null;
        ResultSet rs;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
            stat = conn.createStatement();
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                LOGGER.debug("There are no attributes username/password");
                throw new DaoException("There are no attributes username/password");
            }
            rs = stat.executeQuery("SELECT * FROM " + userTable +
                    " WHERE username = '" + username + "' AND password = '" + password + "'");
            LOGGER.debug("Execute Query " + rs);
            if (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                RoleType userRole = RoleType.valueOf(rs.getString("userRole"));
                Account1C tableId = Account1C.createId(rs.getString("tableId"));
                User user = new User.Builder(username, password)
                        .id(id)
                        .role(userRole)
                        .tableId(tableId)
                        .build();
                LOGGER.debug(user);
                return user;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (ConnectionPoolException e) {
            LOGGER.error("get connection exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } finally {
            DbUtil.close(stat);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("There is no such user");
        throw new DaoException("There is no such user");
    }

    @Override
    public User find(long id) throws DaoException {
        String userTable = PropertyManager.getValue("user.table");
        Statement stat = null;
        ResultSet rs;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT * FROM " + userTable +
                    " WHERE id = '" + id + "'");
            LOGGER.debug("Execute Query " + rs);
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                RoleType userRole = RoleType.valueOf(rs.getString("userRole"));
                Account1C tableId = Account1C.createId(rs.getString("tableId"));
                User user = new User.Builder(username, password)
                        .id(id)
                        .role(userRole)
                        .tableId(tableId)
                        .build();
                LOGGER.debug(user);
                return user;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (ConnectionPoolException e) {
            LOGGER.error("get connection exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } finally {
            DbUtil.close(stat);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("There is no such user");
        throw new DaoException("There is no such user");
    }

    @Override
    public void create(User user) throws DaoException {
        Statement stat = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        String users = PropertyManager.getValue("user.table");
        String username = user.getUsername();
        String password = user.getPassword();
        String role = user.getRole().toString();
        String tableId = user.getAccount1C().getTableId();
        try {
            conn = connectionPool.getConnection();
            stat = conn.createStatement();
            stat.execute("INSERT INTO " + users + "(username, password, tableId, userRole) VALUES ('" + username
                    + "', '" + password + "', '" + tableId + "', '" + role + "')");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (ConnectionPoolException e) {
            LOGGER.error("get connection exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } finally {
            DbUtil.close(stat);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public void createTable() throws DaoException {
        Statement stat = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        String users = PropertyManager.getValue("user.table");
        try {
            conn = connectionPool.getConnection();
            stat = conn.createStatement();
            stat.execute("CREATE TABLE " + users + " (id bigint auto_increment, username varchar(20), password varchar(32), tableId varchar(32), userRole varchar(20) )");
            stat.execute("INSERT INTO " + users + "(username, password, tableId, userRole) VALUES ('admin', '123', 'KK00000001', 'ADMINISTRATOR')");
            stat.execute("INSERT INTO " + users + "(username, password, tableId, userRole) VALUES ('Alhen', '123', 'KK00000002', 'SUPERVISOR')");
            stat.execute("INSERT INTO " + users + "(username, password, tableId, userRole) VALUES ('Bob', '123', 'KK00000003', 'EMPLOYEE')");

            rs = stat.executeQuery("SELECT * FROM " + users);
            while (rs.next()) {
                LOGGER.debug(rs.getString("id") + "\t");
                LOGGER.debug(rs.getString("username"));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } finally {
            DbUtil.close(stat, rs);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public long totalNumber() throws DaoException {
        return 0;
    }

    @Override
    public void update(User user) throws DaoException {

    }

    @Override
    public List<User> list() throws DaoException {
        Statement stat = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        User user;
        List<User> users = new LinkedList<User>();
        String userTable = PropertyManager.getValue("user.table");
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT * FROM " + userTable);
            while (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String username = rs.getString("username");
                String password = rs.getString("password");
                Account1C tableId = Account1C.createId(rs.getString("tableId"));
                RoleType role = RoleType.valueOf(rs.getString("userRole"));
                user = new User.Builder(username, password)
                        .id(id)
                        .role(role)
                        .tableId(tableId)
                        .build();
                users.add(user);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } finally {
            DbUtil.close(stat, rs);
            connectionPool.returnConnection(conn);
        }
        return users;
    }

    @Override
    public void delete(long id) throws DaoException {

    }
}
