package kz.trei.acs.dao.sqlite;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.dao.util.DaoUtil;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.exception.SecurePasswordException;
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import kz.trei.acs.util.FileManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoSqlite implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoSqlite.class);

    @Override
    public User find(String username, String password) throws DaoException {
        LOGGER.debug("find(username, password) ... ");
        PreparedStatement stmt = null;
        ResultSet rs;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception : " + e.getMessage());
            throw new DaoException("Get connection pool instance exception : " + e.getMessage());
        }
        try {
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                LOGGER.debug("The username and/or password are empty or null");
                throw new DaoException("The username and/or password are empty or null");
            }
            stmt = conn.prepareStatement("SELECT * FROM USERS WHERE username = ?");
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            LOGGER.debug("Executed Query " + rs);
            if (DaoUtil.isPasswordValid(rs, password)) {
                long id = DaoUtil.takeId(rs);
                String email = rs.getString("email");
                RoleType role = DaoUtil.takeRole(rs);
                Account1C account1C = DaoUtil.takeAccount1C(rs);
                User user = new User.Builder(username, password)
                        .id(id)
                        .email(email)
                        .role(role)
                        .account1C(account1C)
                        .build();
                LOGGER.debug("... " + user);
                return user;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute : " + e.getMessage());
            throw new DaoException("SQL statement exception execute : " + e.getMessage());
        } catch (SecurePasswordException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("There is no such user");
        throw new DaoException("There is no such user");
    }

    @Override
    public User findById(long id) throws DaoException {
        LOGGER.debug("findById ... ");
        PreparedStatement stmt = null;
        ResultSet rs;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception : " + e.getMessage());
            throw new DaoException("Get connection pool instance exception : " + e.getMessage());
        }
        try {
            stmt = conn.prepareStatement("SELECT * FROM USERS WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            LOGGER.debug("Execute Query " + rs);
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                RoleType role = DaoUtil.takeRole(rs);
                Account1C account1C = DaoUtil.takeAccount1C(rs);
                LOGGER.debug("find by id = " + id);
                User user = new User.Builder(username, password)
                        .id(id)
                        .role(role)
                        .email(email)
                        .account1C(account1C)
                        .build();
                LOGGER.debug("... " + user);
                return user;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute : " + e.getMessage());
            throw new DaoException("SQL statement exception execute : " + e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("There is no such user");
        throw new DaoException("There is no such user");
    }

    @Override
    public void create(User user) throws DaoException {
        LOGGER.debug("create ... ");
        PreparedStatement stmt = null;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception : " + e.getMessage());
            throw new DaoException("Get connection pool instance exception : " + e.getMessage());
        }
        try {
            stmt = conn.prepareStatement("INSERT INTO USERS (username, password, email, tableId, role) VALUES (?,?,?,?,?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getAccount1C().getTableId());
            stmt.setString(5, user.getRole().toString());
            stmt.execute();
            LOGGER.debug("... the user is created");
        } catch (SQLException e) {
            CharSequence obj = "is not unique";
            String errorMessage = "";
            if (e.getMessage().contains(obj)) {
                errorMessage = "error.db.not-unique";
                LOGGER.error("ROW NOT UNIQUE");
            }
            LOGGER.error("SQL INSERT into USERS exception : " + e.getMessage());
            throw new DaoException(errorMessage);
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public void createTable() throws DaoException {
        LOGGER.debug("createTable ... ");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        String createUserTableSql = FileManager.readFile("user_table.sql");
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception : " + e.getMessage());
            throw new DaoException("Get connection pool instance exception : " + e.getMessage());
        }
        try {
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
            LOGGER.error("SQL statement exception execute : " + e.getMessage());
            throw new DaoException("SQL statement exception execute : " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public List<User> findByName(String username) throws DaoException {
        LOGGER.debug("findByName ... ");
        PreparedStatement stmt = null;
        ResultSet rs;
        Connection conn;
        ConnectionPool connectionPool;
        List<User> users = new LinkedList<User>();
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception : " + e.getMessage());
            throw new DaoException("Get connection pool instance exception : " + e.getMessage());
        }
        try {
            stmt = conn.prepareStatement("SELECT * FROM USERS WHERE username = ?");
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            LOGGER.debug("Execute Query " + rs);
            while (rs.next()) {
                long id = DaoUtil.takeId(rs);
                String password = rs.getString("password");
                String email = rs.getString("email");
                RoleType role = DaoUtil.takeRole(rs);
                Account1C account1C = DaoUtil.takeAccount1C(rs);
                User user = new User.Builder(username, password)
                        .id(id)
                        .role(role)
                        .email(email)
                        .account1C(account1C)
                        .build();
                users.add(user);
            }
            LOGGER.debug(" ... " + users.size() + " users");
            return users;
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute : " + e.getMessage());
            throw new DaoException("SQL statement exception execute : " + e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public long numberOfTuples() throws DaoException {
        LOGGER.debug("numberOfTuples ... ");
        long numTuples = 0;
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception : " + e.getMessage());
            throw new DaoException("Get connection pool instance exception : " + e.getMessage());
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT count(*) AS numTuples FROM USERS");
            if (rs.next()) {
                numTuples = Long.valueOf(rs.getString("numTuples"));
            } else {
                LOGGER.error("Failed to count tuples in USERS");
                throw new DaoException("Failed to count tuples in USERS");
            }
        } catch (SQLException e) {
            LOGGER.error("SQL SELECT query exception : " + e.getMessage());
            throw new DaoException("SQL SELECT query exception : " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("... " + numTuples);
        return numTuples;

    }

    @Override
    public void update(User user) throws DaoException {
        LOGGER.debug("update ... ");
        PreparedStatement stmt = null;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception : " + e.getMessage());
            throw new DaoException("Get connection pool instance exception : " + e.getMessage());
        }
        try {
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
            LOGGER.error("SQL statement exception execute : " + e.getMessage());
            throw new DaoException("SQL statement exception execute : " + e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }

    }

    @Override
    public List<User> findAll() throws DaoException {
        LOGGER.debug("findAll ... ");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        User user;
        List<User> users = new LinkedList<User>();
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception : " + e.getMessage());
            throw new DaoException("Get connection pool instance exception : " + e.getMessage());
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM USERS");
            while (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                Account1C tableId = DaoUtil.takeAccount1C(rs);
                RoleType role = DaoUtil.takeRole(rs);
                user = new User.Builder(username, password)
                        .id(id)
                        .role(role)
                        .email(email)
                        .account1C(tableId)
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute : " + e.getMessage());
            throw new DaoException("SQL statement exception execute : " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug(" ... " + users.size() + " users");
        return users;
    }

    @Override
    public List<User> findInRange(long offset, long limit) throws DaoException {
        LOGGER.debug("findInRange ... ");
        Statement stmt = null;
        ResultSet rs = null;

        User user;
        List<User> users = new LinkedList<User>();
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception : " + e.getMessage());
            throw new DaoException("Get connection pool instance exception : " + e.getMessage());
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM USERS LIMIT " + limit + " OFFSET " + offset);
            while (rs.next()) {
                long id = DaoUtil.takeId(rs);
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                Account1C account1C = DaoUtil.takeAccount1C(rs);
                RoleType role = DaoUtil.takeRole(rs);
                user = new User.Builder(username, password)
                        .id(id)
                        .role(role)
                        .email(email)
                        .account1C(account1C)
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute : " + e.getMessage());
            throw new DaoException("SQL statement exception execute : " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug(" ... " + users.size() + " users");
        return users;
    }

    @Override
    public void delete(long id) throws DaoException {
        LOGGER.debug("delete ... ");
        PreparedStatement stmt = null;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception : " + e.getMessage());
            throw new DaoException("Get connection pool instance exception : " + e.getMessage());
        }
        try {
            stmt = conn.prepareStatement("DELETE FROM USERS WHERE id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            LOGGER.debug("... id=" + id + "is deleted");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute : " + e.getMessage());
            throw new DaoException("SQL statement exception execute : " + e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }
}
