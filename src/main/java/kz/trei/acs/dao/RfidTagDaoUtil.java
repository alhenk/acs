package kz.trei.acs.dao;

import kz.trei.acs.office.rfid.Issue;
import kz.trei.acs.office.rfid.ProtocolType;
import kz.trei.acs.office.rfid.RfidType;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.exception.DateStampException;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class RfidTagDaoUtil {
    private static final Logger LOGGER = Logger.getLogger(RfidTagDaoUtil.class);

    private RfidTagDaoUtil() {
    }

    public static long takeIdFromResult(ResultSet rs) throws SQLException {
        LOGGER.debug("takeIdFromResult ...");
        long id;
        try {
            id = Long.valueOf(rs.getString("id"));
        } catch (NumberFormatException e) {
            throw new SQLException("DB index \"id\" is not valid : " + e.getMessage());
        }
        LOGGER.debug(id);
        return id;
    }

    public static RfidType takeTypeFromResult(ResultSet rs) throws SQLException {
        LOGGER.debug("takeTypeFromResult ...");
        RfidType type;
        try {
            type = RfidType.valueOf(rs.getString("type"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default RFID type due to illegal argument : " + e.getMessage());
            type = RfidType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default RFID type due to null value : " + e.getMessage());
            type = RfidType.DEFAULT;
        }
        LOGGER.debug(type);
        return type;
    }

    public static ProtocolType takeProtocolFromResult(ResultSet rs) throws SQLException {
        LOGGER.debug("takeProtocolFromRequest ...");
        ProtocolType protocol;
        try {
            protocol = ProtocolType.valueOf(rs.getString("protocol"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default RFID protocol due to illegal argument : " + e.getMessage());
            protocol = ProtocolType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default RFID protocol due to null value : " + e.getMessage());
            protocol = ProtocolType.DEFAULT;
        }
        LOGGER.debug(protocol);
        return protocol;
    }

    public static Issue buildIssueFromResult(ResultSet rs) throws SQLException {
        LOGGER.debug("buildIssueFromResult ...");
        DateStamp issueDate = takeIssueDateFromResult(rs);
        DateStamp expirationDate = takeExpirationDateFromResult(rs);
        Issue issue = new Issue.Builder()
                .issueDate(issueDate)
                .expirationDate(expirationDate)
                .build();
        LOGGER.debug("Issue " + issue + " is built");
        return issue;
    }

    public static DateStamp takeIssueDateFromResult(ResultSet rs) throws SQLException {
        LOGGER.debug("takeIssueDateFromResult ...");
        DateStamp issueDate;
        try {
            issueDate = DateStamp.create(rs.getString("issueDate"));
        } catch (DateStampException e) {
            issueDate = DateStamp.createEmptyDate();
            LOGGER.debug("Assigned empty issue date due to exception: " + e.getMessage());
        }
        LOGGER.debug(issueDate.getDate());
        return issueDate;
    }

    public static DateStamp takeExpirationDateFromResult(ResultSet rs) throws SQLException {
        LOGGER.debug("takeExpirationDateFromResult ...");
        DateStamp expirationDate;
        try {
            expirationDate = DateStamp.create(rs.getString("expirationDate"));
        } catch (DateStampException e) {
            expirationDate = DateStamp.createEmptyDate();
            LOGGER.debug("Assigned empty expiration date due to exception: " + e.getMessage());
        }
        LOGGER.debug(expirationDate.getDate());
        return expirationDate;
    }
}
