package kz.trei.acs.dao.sqlite;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.office.structure.Account1CException;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoSqlite implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoSqlite.class);

    @Override
    public User find(String username, String password) throws DaoException {
        String userTable = PropertyManager.getValue("user.table");
        Statement stmt = null;
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
            stmt = conn.createStatement();
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                LOGGER.debug("There are no attributes username/password");
                throw new DaoException("There are no attributes username/password");
            }
            rs = stmt.executeQuery("SELECT * FROM " + userTable +
                    " WHERE username = '" + username + "' AND password = '" + password + "'");
            LOGGER.debug("Execute Query " + rs);
            if (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String email = rs.getString("email");
                RoleType userRole = RoleType.valueOf(rs.getString("userRole"));
                Account1C tableId;
                try {
                    tableId = Account1C.createId(rs.getString("tableId"));
                } catch (Account1CException e) {
                    tableId = Account1C.defaultId();
                    LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
                }
                User user = new User.Builder(username, password)
                        .id(id)
                        .email(email)
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
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("There is no such user");
        throw new DaoException("There is no such user");
    }

    @Override
    public User find(long id) throws DaoException {
        String users = PropertyManager.getValue("user.table");
        PreparedStatement stmt = null;
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
            stmt = conn.prepareStatement("SELECT * FROM " + users + " WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            LOGGER.debug("Execute Query " + rs);
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                RoleType userRole = RoleType.valueOf(rs.getString("userRole"));
                Account1C tableId;
                try {
                    tableId = Account1C.createId(rs.getString("tableId"));
                } catch (Account1CException e) {
                    tableId = Account1C.defaultId();
                    LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
                }
                LOGGER.debug("find by id = " + id);
                User user = new User.Builder(username, password)
                        .id(id)
                        .role(userRole)
                        .email(email)
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
            DbUtil.close(stmt);
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
        String users = PropertyManager.getValue("user.table");
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement("INSERT INTO " + users + " (username, password, email, tableId, userRole) VALUES (?,?,?,?,?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getAccount1C().getTableId());
            stmt.setString(5, user.getRole().toString());
            stmt.execute();
        } catch (SQLException e) {
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
        Statement stmt = null;
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
            stmt = conn.createStatement();
            stmt.execute("CREATE TABLE " + users + " (id INTEGER PRIMARY KEY, username CHAR(20), password CHAR(32), email CHAR(32), tableId CHAR(32), userRole CHAR(20) )");
            stmt.execute("INSERT INTO " + users + "(username, password, email, tableId, userRole) VALUES ('admin', '123', 'admin@example.com', 'KK00000001', 'ADMINISTRATOR')");
            stmt.execute("INSERT INTO " + users + "(username, password, email, tableId, userRole) VALUES ('Alhen', '123', 'alhen@example.com', 'KK00000002', 'SUPERVISOR')");
            stmt.execute("INSERT INTO " + users + "(username, password, email, tableId, userRole) VALUES ('Bob', '123', 'bob@example.com', 'KK00000003', 'EMPLOYEE')");
            rs = stmt.executeQuery("SELECT * FROM " + users);
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
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public long totalNumber() throws DaoException {
        long totalNumber = 0;
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        String userTable = PropertyManager.getValue("user.table");
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT count(*) AS TotalNumber FROM " + userTable);
            while (rs.next()) {
                totalNumber = Long.valueOf(rs.getString("TotalNumber"));
                LOGGER.debug("Total number of ROWS = " + totalNumber);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        return totalNumber;

    }

    @Override
    public void update(User user) throws DaoException {
        PreparedStatement stmt = null;
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
            stmt = conn.prepareStatement("UPDATE " + users + " SET username = ?, password = ?, email = ?, tableId = ?, userRole = ? WHERE id = ?");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getAccount1C().getTableId());
            stmt.setString(5, user.getRole().toString());
            stmt.setString(6, Long.toString(user.getId()));
            stmt.execute();
            LOGGER.debug("id :" + user.getId());
            LOGGER.debug("username :" + user.getUsername());
            LOGGER.debug("password :" + user.getPassword());
            LOGGER.debug("Email :" + user.getEmail());
            LOGGER.debug("Role :" + user.getRole());
            LOGGER.debug("Table ID :" + user.getAccount1C().getTableId());
        } catch (SQLException e) {
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
    public List<User> list() throws DaoException {
        Statement stmt = null;
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
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + userTable);
            while (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                Account1C tableId;
                try {
                    tableId = Account1C.createId(rs.getString("tableId"));
                } catch (Account1CException e) {
                    tableId = Account1C.defaultId();
                    LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
                }
                RoleType role = RoleType.valueOf(rs.getString("userRole"));
                user = new User.Builder(username, password)
                        .id(id)
                        .role(role)
                        .email(email)
                        .tableId(tableId)
                        .build();
                users.add(user);
            }
            rs = stmt.executeQuery("SELECT count(*) AS TotalNumber FROM " + userTable);
            while (rs.next()) {
                LOGGER.debug("Total number of ROWS = " + rs.getString("TotalNumber"));
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        return users;
    }

    @Override
    public void delete(long id) throws DaoException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        String users = PropertyManager.getValue("user.table");
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement("DELETE FROM " + users + " WHERE id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }
}
