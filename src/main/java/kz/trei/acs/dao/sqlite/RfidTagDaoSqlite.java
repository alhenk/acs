package kz.trei.acs.dao.sqlite;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.office.rfid.*;
import kz.trei.acs.user.User;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.DateStampException;
import kz.trei.acs.util.FileManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class RfidTagDaoSqlite implements RfidTagDao {
    private static final Logger LOGGER = Logger.getLogger(RfidTagDaoSqlite.class);

    @Override
    public void createTable() throws DaoException {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        String createRfidTagTableSql = FileManager.readFile("rfidtag_table.sql");
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
            stmt.executeUpdate(createRfidTagTableSql);
            rs = stmt.executeQuery("SELECT * FROM RFIDTAGS");
            for (int i = 0; i < 20; i++) {
                if (rs.next()) {
                    LOGGER.debug(rs.getString("id") + "\t"
                            + rs.getString("uid") + "\t"
                            + rs.getString("type") + "\t"
                            + rs.getString("protocol"));
                }
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
    public User findById(long id) throws DaoException {
        return null;
    }

    @Override
    public void create(RfidTag entity) throws DaoException {
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
            rs = stmt.executeQuery("SELECT count(*) AS TotalNumber FROM RFIDTAGS");
            while (rs.next()) {
                totalNumber = Long.valueOf(rs.getString("TotalNumber"));
                LOGGER.debug("Total number of ROWS = " + totalNumber);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("RfidTags total number exception: " + e.getMessage());
            throw new DaoException("RfidTags total number exception");
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        return totalNumber;
    }

    @Override
    public void update(RfidTag entity) throws DaoException {
    }

    @Override
    public List<RfidTag> findAll() throws DaoException {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        RfidTag rfidTag;
        List<RfidTag> rfidTags = new LinkedList<RfidTag>();
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM RFIDTAGS");
            while (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String uid = rs.getString("uid");
                RfidType type = RfidType.valueOf(rs.getString("type"));
                ProtocolType protocol = ProtocolType.valueOf(rs.getString("protocol"));
                DateStamp issueDate;
                DateStamp expirationDate;
                String issueDateString = rs.getString("issueDate");
                if (issueDateString == null || issueDateString.isEmpty()) {
                    issueDate = DateStamp.createEmptyDate();
                } else {
                    try {
                        issueDate = DateStamp.create(rs.getString("issueDate"));
                    } catch (DateStampException e) {
                        LOGGER.error("IssueDate format exception: " + e.getMessage());
                        throw new DaoException("IssueDate format exception");
                    }
                }
                String expirationDateString = rs.getString("expirationDate");
                if (expirationDateString == null || expirationDateString.isEmpty()) {
                    expirationDate = DateStamp.createEmptyDate();
                } else {
                    try {
                        expirationDate = DateStamp.create(rs.getString("expirationDate"));
                    } catch (DateStampException e) {
                        LOGGER.error("ExpirationDate format exception: " + e.getMessage());
                        throw new DaoException("ExpirationDate UID format exception");
                    }
                }
                Issue issue = new Issue.Builder()
                        .issueDate(issueDate)
                        .expirationDate(expirationDate)
                        .build();
                rfidTag = new RfidTag.Builder(uid)
                        .id(id)
                        .uid(uid)
                        .type(type)
                        .protocol(protocol)
                        .issue(issue)
                        .build();
                rfidTags.add(rfidTag);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (UidFormatException e) {
            LOGGER.error("RFID tag building exception: " + e.getMessage());
            throw new DaoException("RFID tag building exception " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        return rfidTags;
    }

    @Override
    public List<RfidTag> findInRange(long offset, long length) throws DaoException {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        RfidTag rfidTag;
        List<RfidTag> rfidTags = new LinkedList<RfidTag>();
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM RFIDTAGS LIMIT " + length + " OFFSET " + offset);
            while (rs.next()) {
                long id = Long.valueOf(rs.getString("id"));
                String uid = rs.getString("uid");
                RfidType type = RfidType.valueOf(rs.getString("type"));
                ProtocolType protocol = ProtocolType.valueOf(rs.getString("protocol"));
                DateStamp issueDate;
                DateStamp expirationDate;
                String issueDateString = rs.getString("issueDate");
                if (issueDateString == null || issueDateString.isEmpty()) {
                    issueDate = DateStamp.createEmptyDate();
                } else {
                    try {
                        issueDate = DateStamp.create(rs.getString("issueDate"));
                    } catch (DateStampException e) {
                        LOGGER.error("IssueDate format exception: " + e.getMessage());
                        throw new DaoException("IssueDate format exception");
                    }
                }
                String expirationDateString = rs.getString("expirationDate");
                if (expirationDateString == null || expirationDateString.isEmpty()) {
                    expirationDate = DateStamp.createEmptyDate();
                } else {
                    try {
                        expirationDate = DateStamp.create(rs.getString("expirationDate"));
                    } catch (DateStampException e) {
                        LOGGER.error("ExpirationDate format exception: " + e.getMessage());
                        throw new DaoException("ExpirationDate UID format exception");
                    }
                }
                Issue issue = new Issue.Builder()
                        .issueDate(issueDate)
                        .expirationDate(expirationDate)
                        .build();
                rfidTag = new RfidTag.Builder(uid)
                        .id(id)
                        .uid(uid)
                        .type(type)
                        .protocol(protocol)
                        .issue(issue)
                        .build();
                rfidTags.add(rfidTag);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } catch (UidFormatException e) {
            LOGGER.error("RFID tag building exception: " + e.getMessage());
            throw new DaoException("RFID tag building exception " + e.getMessage());
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        return rfidTags;
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
            stmt = conn.prepareStatement("DELETE FROM RFIDTAGS WHERE id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool exception: " + e.getMessage());
            throw new DaoException("Connection pool exception");
        } catch (SQLException e) {
            LOGGER.error("Delete from rfidTags exception: " + e.getMessage());
            throw new DaoException("Delete from rfidTags exception");
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }

    }
}
