package kz.trei.acs.dao.h2;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.dao.util.DaoUtil;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.office.rfid.*;
import kz.trei.acs.util.FileManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class RfidTagDaoH2 implements RfidTagDao {
    private static final Logger LOGGER = Logger.getLogger(RfidTagDaoH2.class);

    @Override
    public void createTable() throws DaoException {
        LOGGER.debug("createTable ... ");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        String createRfidTagTableSql = FileManager.readFile("rfidtag_table_h2.sql");
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createRfidTagTableSql);
            rs = stmt.executeQuery("SELECT * FROM RFIDTAGS LIMIT 20 OFFSET 0");
            while (rs.next()) {
                LOGGER.debug(rs.getString("id") + "\t"
                        + rs.getString("uid") + "\t"
                        + rs.getString("type") + "\t"
                        + rs.getString("protocol"));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL statement exception execute: " + e.getMessage());
            throw new DaoException("SQL statement exception execute");
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public RfidTag findById(long id) throws DaoException {
        LOGGER.debug("findById ... ");
        PreparedStatement stmt = null;
        ResultSet rs;
        Connection conn;
        ConnectionPool connectionPool;
        RfidTag rfidTag;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
            stmt = conn.prepareStatement("SELECT * FROM RFIDTAGS WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            LOGGER.debug("Execute Query " + rs);
            if (rs.next()) {
                String uid = rs.getString("uid");
                RfidType type = DaoUtil.takeType(rs);
                ProtocolType protocol = DaoUtil.takeProtocol(rs);
                Issue issue = DaoUtil.buildIssue(rs);
                rfidTag = new RfidTag.Builder(uid)
                        .id(id)
                        .uid(uid)
                        .type(type)
                        .protocol(protocol)
                        .issue(issue)
                        .build();
                LOGGER.debug("... " + rfidTag);
                return rfidTag;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL SELECT by ID exception : " + e.getMessage());
            throw new DaoException("SQL SELECT by ID exception" + e.getMessage());
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("There is no such rfidTag");
        throw new DaoException("There is no such rfidTag");
    }

    @Override
    public void create(RfidTag rfidTag) throws DaoException {
        LOGGER.debug("create ... ");
        PreparedStatement stmt = null;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            stmt = conn.prepareStatement("INSERT INTO RFIDTAGS (uid, type, protocol, issueDate, expirationDate) VALUES (?,?,?,?,?)");
            stmt.setString(1, rfidTag.getUid());
            stmt.setString(2, rfidTag.getType().toString());
            stmt.setString(3, rfidTag.getProtocol().toString());
            stmt.setString(4, rfidTag.getIssue().getIssueDate().getDate());
            stmt.setString(5, rfidTag.getIssue().getExpirationDate().getDate());
            stmt.execute();
            LOGGER.debug("... the RFID tag is created");
        } catch (SQLException e) {
            CharSequence obj = "is not unique";
            String errorMessage = "";
            if (e.getMessage().contains(obj)) {
                errorMessage = "error.db.not-unique";
                LOGGER.error("ROW NOT UNIQUE");
            }
            LOGGER.error("SQL INSERT into RFIDTAGS exception : " + e.getMessage());
            throw new DaoException(errorMessage);
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public long numberOfTuples() throws DaoException {
        LOGGER.debug("numberOfTuples ...");
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
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT count(*) AS numTuples FROM RFIDTAGS");
            if (rs.next()) {
                numTuples = Long.valueOf(rs.getString("numTuples"));
            } else {
                LOGGER.error("Failed to count tuples in RFIDTAGS");
                throw new DaoException("Failed to count tuples in RFIDTAGS");
            }
        } catch (SQLException e) {
            LOGGER.error("RfidTags total number exception: " + e.getMessage());
            throw new DaoException("RfidTags total number exception");
        } finally {
            DbUtil.close(stmt, rs);
            connectionPool.returnConnection(conn);
        }
        LOGGER.debug("... " + numTuples);
        return numTuples;
    }

    @Override
    public void update(RfidTag rfidTag) throws DaoException {
        LOGGER.debug("update ...");
        PreparedStatement stmt = null;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception " + e.getMessage());
        }
        try {
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
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }

    @Override
    public List<RfidTag> findAll() throws DaoException {
        LOGGER.debug("findAll ...");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        RfidTag rfidTag;
        List<RfidTag> rfidTags = new LinkedList<RfidTag>();
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM RFIDTAGS");
            while (rs.next()) {
                long id = DaoUtil.takeId(rs);
                String uid = rs.getString("uid");
                RfidType type = DaoUtil.takeType(rs);
                ProtocolType protocol = DaoUtil.takeProtocol(rs);
                Issue issue = DaoUtil.buildIssue(rs);
                rfidTag = new RfidTag.Builder(uid)
                        .id(id)
                        .uid(uid)
                        .type(type)
                        .protocol(protocol)
                        .issue(issue)
                        .build();
                rfidTags.add(rfidTag);
            }
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
        LOGGER.debug(" ... " + rfidTags.size() + " RFID tags");
        return rfidTags;
    }

    @Override
    public List<RfidTag> findInRange(long offset, long limit) throws DaoException {
        LOGGER.debug("findInRange ...");
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        ConnectionPool connectionPool;
        RfidTag rfidTag;
        List<RfidTag> rfidTags = new LinkedList<RfidTag>();
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM RFIDTAGS LIMIT " + limit + " OFFSET " + offset);
            while (rs.next()) {
                long id = DaoUtil.takeId(rs);
                String uid = rs.getString("uid");
                RfidType type = DaoUtil.takeType(rs);
                ProtocolType protocol = DaoUtil.takeProtocol(rs);
                Issue issue = DaoUtil.buildIssue(rs);
                rfidTag = new RfidTag.Builder(uid)
                        .id(id)
                        .uid(uid)
                        .type(type)
                        .protocol(protocol)
                        .issue(issue)
                        .build();
                rfidTags.add(rfidTag);
            }
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
        LOGGER.debug(" ... " + rfidTags.size() + " RFID tags");
        return rfidTags;
    }

    @Override
    public void delete(long id) throws DaoException {
        LOGGER.debug("delete ...");
        PreparedStatement stmt = null;
        Connection conn;
        ConnectionPool connectionPool;
        try {
            connectionPool = ConnectionPool.getInstance();
            conn = connectionPool.getConnection();
            LOGGER.debug("Got connection " + conn);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
            throw new DaoException("Get connection pool instance exception");
        }
        try {
            stmt = conn.prepareStatement("DELETE FROM RFIDTAGS WHERE id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            LOGGER.debug("... id=" + id + "is deleted");
        } catch (SQLException e) {
            LOGGER.error("Delete from rfidTags exception: " + e.getMessage());
            throw new DaoException("Delete from rfidTags exception");
        } finally {
            DbUtil.close(stmt);
            connectionPool.returnConnection(conn);
        }
    }
}
