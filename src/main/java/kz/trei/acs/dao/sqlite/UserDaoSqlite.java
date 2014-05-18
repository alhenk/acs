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
import kz.trei.acs.util.FileManager;
import kz.trei.acs.util.PasswordHash;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoSqlite implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoSqlite.class);

    @Override
    public User find(String username, String password) throws DaoException {
        PreparedStatement stmt = null;
        ResultSet rs;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        boolean isPasswordValid = false;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                LOGGER.debug("There are no attributes username/password");
                throw new DaoException("There are no attributes username/password");
            }
            stmt = conn.prepareStatement("SELECT * FROM USERS WHERE username = ?");
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            LOGGER.debug("Executed Query " + rs);
            if (rs.next()) {
                String hash = rs.getString("password");
                try {
                    isPasswordValid = PasswordHash.validatePassword(password, hash);
                } catch (NoSuchAlgorithmException e) {
                    LOGGER.debug("Password validation Error" + e.getMessage());
                    throw new DaoException("Password validation Error" + e.getMessage());
                } catch (InvalidKeySpecException e) {
                    LOGGER.debug("Password validation Error" + e.getMessage());
                    throw new DaoException("Password validation Error" + e.getMessage());
                }
            } else {
                LOGGER.debug("There is no such user");
                throw new DaoException("There is no such user");
            }
            if (isPasswordValid) {
                long id = Long.valueOf(rs.getString("id"));
                String email = rs.getString("email");
                RoleType role = RoleType.valueOf(rs.getString("role"));
                Account1C tableId;
                try {
                    tableId = Account1C.buildAccount1C(rs.getString("tableId"));
                } catch (Account1CException e) {
                    tableId = Account1C.defaultAccount1C();
                    LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
                }
                User user = new User.Builder(username, password)
                        .id(id)
                        .email(email)
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
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("There is no such user");
        throw new DaoException("There is no such user");
    }

    @Override
    public User findById(long id) throws DaoException {
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
            stmt = conn.prepareStatement("SELECT * FROM USERS WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            LOGGER.debug("Execute Query " + rs);
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                RoleType role = RoleType.valueOf(rs.getString("role"));
                Account1C tableId;
                try {
                    tableId = Account1C.buildAccount1C(rs.getString("tableId"));
                } catch (Account1CException e) {
                    tableId = Account1C.defaultAccount1C();
                    LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
                }
                LOGGER.debug("find by id = " + id);
                User user = new User.Builder(username, password)
                        .id(id)
                        .role(role)
                        .email(email)
                        .tableId(tableId)
                        .build();
                LOGGER.debug("Found by id " + user);
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
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement("INSERT INTO USERS (username, password, email, tableId, role) VALUES (?,?,?,?,?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getAccount1C().getTableId());
            stmt.setString(5, user.getRole().toString());
            stmt.execute();
        } catch (SQLException e) {
            CharSequence obj = "is not unique";
            String errorMessage = "";
            if (e.getMessage().contains(obj)) {
                errorMessage = "error.db.not-unique";
                LOGGER.error("ROW NOT UNIQUE");
            }
            LOGGER.error("SQL INSERT into USERS exception : " + e.getMessage());
            throw new DaoException(errorMessage);
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
        String createUserTableSql = FileManager.readFile("user_table.sql");
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.executeUpdate(createUserTableSql);
            rs = stmt.executeQuery("SELECT * FROM USERS");
            while (rs.next()) {
                LOGGER.debug(rs.getString("id") + "\t"
                        + rs.getString("username") + "\t"
                        + rs.getString("tableId") + "\t"
                        + rs.getString("role"));
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
    public List<User> findByName(String username) throws DaoException {
        return null;
    }

    @Override
    public long totalNumber() throws DaoException {
        long totalNumber = 0;
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
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT count(*) AS TotalNumber FROM USERS");
            if (rs.next()) {
                totalNumber = Long.valueOf(rs.getString("TotalNumber"));
                LOGGER.debug("Total number of ROWS in USERS = " + totalNumber);
            }else{
                LOGGER.error("Failed to count ROWS in USERS");
                throw new DaoException("Failed to count ROWS in USERS");
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("SQL SELECT query exception : " + e.getMessage());
            throw new DaoException("SQL SELECT query exception");
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
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement("UPDATE USERS SET username = ?, password = ?, email = ?, tableId = ?, role = ? WHERE id = ?");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getAccount1C().getTableId());
            stmt.setString(5, user.getRole().toString());
            stmt.setString(6, Long.toString(user.getId()));
            stmt.execute();
            LOGGER.debug("id :" + user.getId());
            LOGGER.debug("username :" + user.getUsername());
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
    public List<User> findAll() throws DaoException {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        User user;
        List<User> users = new LinkedList<User>();
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM USERS");
            while (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                Account1C tableId;
                try {
                    tableId = Account1C.buildAccount1C(rs.getString("tableId"));
                } catch (Account1CException e) {
                    tableId = Account1C.defaultAccount1C();
                    LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
                }
                RoleType role = RoleType.valueOf(rs.getString("role"));
                user = new User.Builder(username, password)
                        .id(id)
                        .role(role)
                        .email(email)
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
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        return users;
    }

    @Override
    public List<User> findInRange(long offset, long length) throws DaoException {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        User user;
        List<User> users = new LinkedList<User>();
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM USERS LIMIT " + length + " OFFSET " + offset);
            while (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                Account1C tableId;
                try {
                    tableId = Account1C.buildAccount1C(rs.getString("tableId"));
                } catch (Account1CException e) {
                    tableId = Account1C.defaultAccount1C();
                    LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
                }
                RoleType role = RoleType.valueOf(rs.getString("role"));
                user = new User.Builder(username, password)
                        .id(id)
                        .role(role)
                        .email(email)
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
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement("DELETE FROM USERS WHERE id = ?");
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
