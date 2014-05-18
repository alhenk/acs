package kz.trei.acs.dao.sqlite;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.dao.RfidTagDaoUtil;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.office.rfid.*;
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
        ConnectionPool connectionPool;
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
    public RfidTag findById(long id) throws DaoException {
        PreparedStatement stmt = null;
        ResultSet rs;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        RfidTag rfidTag;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
            stmt = conn.prepareStatement("SELECT * FROM RFIDTAGS WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            LOGGER.debug("Execute Query " + rs);
            if (rs.next()) {
                String uid = rs.getString("uid");
                RfidType type = RfidTagDaoUtil.takeTypeFromResult(rs);
                ProtocolType protocol = RfidTagDaoUtil.takeProtocolFromResult(rs);
                Issue issue = RfidTagDaoUtil.buildIssueFromResult(rs);
                rfidTag = new RfidTag.Builder(uid)
                        .id(id)
                        .uid(uid)
                        .type(type)
                        .protocol(protocol)
                        .issue(issue)
                        .build();
                LOGGER.debug("Found by id " + rfidTag);
                return rfidTag;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL SELECT by ID exception : " + e.getMessage());
            throw new DaoException("SQL SELECT by ID exception" + e.getMessage());
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection exception: " + e.getMessage());
            throw new DaoException("Get connection exception: " + e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("There is no such rfidTag");
        throw new DaoException("There is no such rfidTag");
    }

    @Override
    public void create(RfidTag rfidTag) throws DaoException {
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
            stmt = conn.prepareStatement("INSERT INTO RFIDTAGS (uid, type, protocol, issueDate, expirationDate) VALUES (?,?,?,?,?)");
            stmt.setString(1, rfidTag.getUid());
            stmt.setString(2, rfidTag.getType().toString());
            stmt.setString(3, rfidTag.getProtocol().toString());
            stmt.setString(4, rfidTag.getIssue().getIssueDate().getDate());
            stmt.setString(5, rfidTag.getIssue().getExpirationDate().getDate());
            stmt.execute();
        } catch (SQLException e) {
            CharSequence obj = "is not unique";
            String errorMessage = "";
            if (e.getMessage().contains(obj)) {
                errorMessage = "error.db.not-unique";
                LOGGER.error("ROW NOT UNIQUE");
            }
            LOGGER.error("SQL INSERT into RFIDTAGS exception : " + e.getMessage());
            throw new DaoException(errorMessage);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection exception: " + e.getMessage());
            throw new DaoException("Get connection exception: " + e.getMessage());
        } finally {
            DbUtil.close(stmt);
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
    public void update(RfidTag rfidTag) throws DaoException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
            conn = connectionPool.getConnection();
            stmt = conn.prepareStatement("UPDATE RFIDTAGS SET uid=?, type=?, protocol=?, issueDate=?, expirationDate=? WHERE id = ?");
            stmt.setString(1, rfidTag.getUid());
            stmt.setString(2, rfidTag.getType().toString());
            stmt.setString(3, rfidTag.getProtocol().toString());
            stmt.setString(4, rfidTag.getIssue().getIssueDate().getDate());
            stmt.setString(5, rfidTag.getIssue().getExpirationDate().getDate());
            stmt.setString(6, Long.toString(rfidTag.getId()));
            stmt.executeUpdate();
            LOGGER.debug("id : " + rfidTag.getId());
            LOGGER.debug("RFID UID : " + rfidTag.getUid());
            LOGGER.debug("RFID type : " + rfidTag.getType());
            LOGGER.debug("RFID protocol : " + rfidTag.getProtocol());
            LOGGER.debug("Issue date : " + rfidTag.getIssue().getIssueDate());
            LOGGER.debug("Expiration date : " + rfidTag.getIssue().getIssueDate());
        } catch (SQLException e) {
            LOGGER.error("SQL UPDATE RFIDTAGS exception: " + e.getMessage());
            throw new DaoException("SQL UPDATE RFIDTAGS exception: " + e.getMessage());
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection exception: " + e.getMessage());
            throw new DaoException("Get connection exception: " + e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
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
                long id = RfidTagDaoUtil.takeIdFromResult(rs);
                String uid = rs.getString("uid");
                RfidType type = RfidTagDaoUtil.takeTypeFromResult(rs);
                ProtocolType protocol = RfidTagDaoUtil.takeProtocolFromResult(rs);
                Issue issue = RfidTagDaoUtil.buildIssueFromResult(rs);
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
                long id = RfidTagDaoUtil.takeIdFromResult(rs);
                String uid = rs.getString("uid");
                RfidType type = RfidTagDaoUtil.takeTypeFromResult(rs);
                ProtocolType protocol = RfidTagDaoUtil.takeProtocolFromResult(rs);
                Issue issue = RfidTagDaoUtil.buildIssueFromResult(rs);
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
