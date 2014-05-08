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

import java.sql.*;
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
                RoleType role = RoleType.valueOf(rs.getString("role"));
                Account1C tableId = Account1C.createId(rs.getString("tableId"));
                User user = new User.Builder(username, password)
                        .id(id)
                        .role(role)
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
    public User findById(long id) throws DaoException {
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
                RoleType role = RoleType.valueOf(rs.getString("role"));
                Account1C tableId = Account1C.createId(rs.getString("tableId"));
                User user = new User.Builder(username, password)
                        .id(id)
                        .role(role)
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
        PreparedStatement stmt = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        String username = user.getUsername();
        String password = user.getPassword();
        String role = user.getRole().toString();
        String tableId = user.getAccount1C().getTableId();
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement("INSERT INTO USERS (username, password, email, tableId, userRole) VALUES (?,?,?,?,?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getAccount1C().getTableId());
            stmt.setString(5, user.getRole().toString());
            stmt.execute();        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (ConnectionPoolException e) {
            LOGGER.error("get connection exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } finally {
            DbUtil.close(stmt);
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
            stat.execute("CREATE TABLE USERS (id bigint auto_increment, username varchar(20), password varchar(32), tableId varchar(32), role varchar(20) )");
            stat.execute("INSERT INTO USERS (username, password, tableId, role) VALUES ('admin', '123', 'KK00000001', 'ADMINISTRATOR')");
            stat.execute("INSERT INTO USERS (username, password, tableId, role) VALUES ('Alhen', '123', 'KK00000002', 'SUPERVISOR')");
            stat.execute("INSERT INTO USERS (username, password, tableId, role) VALUES ('Bob', '123', 'KK00000003', 'EMPLOYEE')");

            rs = stat.executeQuery("SELECT * FROM USERS");
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
    public List<User> findByName(String username) throws DaoException {
        return null;
    }

    @Override
    public long totalNumber() throws DaoException {
        return 0;
    }

    @Override
    public void update(User user) throws DaoException {

    }

    @Override
    public List<User> findAll() throws DaoException {
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
                RoleType role = RoleType.valueOf(rs.getString("role"));
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
