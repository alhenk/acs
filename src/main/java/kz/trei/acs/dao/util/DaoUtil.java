package kz.trei.acs.dao.util;

import kz.trei.acs.exception.DateStampException;
import kz.trei.acs.exception.SecurePasswordException;
import kz.trei.acs.exception.TimeStampException;
import kz.trei.acs.office.rfid.*;
import kz.trei.acs.office.structure.*;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.PasswordHash;
import kz.trei.acs.util.TimeStamp;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;


public final class DaoUtil {
    private static final Logger LOGGER = Logger.getLogger(DaoUtil.class);

    private DaoUtil() {
    }

    public static long takeId(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeId ...");
        long id;
        try {
            id = Long.valueOf(resultSet.getString("id"));
        } catch (NumberFormatException e) {
            throw new SQLException("DB index \"id\" is not valid : " + e.getMessage());
        }
        LOGGER.debug("..." + id);
        return id;
    }

    public static RfidTag takeRfidTag(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeRfidTag ...");
        RfidTag rfidTag = new RfidTag();
        String id = null;
        try {
            id = resultSet.getString("id");
            rfidTag.setUid(resultSet.getString("uid"));
        } catch (UidFormatException e) {
            rfidTag.setEmptyUid();
            LOGGER.debug("id=" + id + " : assigned empty UID \"00000000\" date due to exception: " + e.getMessage());
        }
        LOGGER.debug("... " + rfidTag);
        return rfidTag;
    }

    public static Account1C takeAccount1C(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeAccount1C ... ");
        Account1C account1C;
        String id = null;
        try {
            id = resultSet.getString("id");
            account1C = Account1C.buildAccount1C(resultSet.getString("tableId"));
        } catch (Account1CException e) {
            account1C = Account1C.defaultAccount1C();
            LOGGER.debug("id=" + id + " : assigned default table ID due to exception: " + e.getMessage());
        }
        LOGGER.debug("..." + account1C.getTableId());
        return account1C;
    }

    public static DateStamp takeBirthDate(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeBirthDate ...");
        DateStamp birthDate;
        String id = null;
        try {
            id = resultSet.getString("id");
            birthDate = DateStamp.create(resultSet.getString("birthDate"));
        } catch (DateStampException e) {
            birthDate = DateStamp.createEmptyDate();
            LOGGER.debug("id=" + id + " : assigned empty birth date due to exception: " + e.getMessage());
        }
        LOGGER.debug("... " + birthDate.getDate());
        return birthDate;
    }

    public static PositionType takePosition(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takePosition ...");
        PositionType position;
        String id = null;
        try {
            id = resultSet.getString("id");
            position = PositionType.valueOf(resultSet.getString("jobPosition"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("id=" + id + " : assigned default position due to illegal argument : " + e.getMessage());
            position = PositionType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("id=" + id + " : assigned default position due to null value : " + e.getMessage());
            position = PositionType.DEFAULT;
        }
        LOGGER.debug("... " + position);
        return position;
    }

    public static DepartmentType takeDepartment(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeDepartment ...");
        DepartmentType department;
        String id = null;
        try {
            id = resultSet.getString("id");
            department = DepartmentType.valueOf(resultSet.getString("department"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("id=" + id + " : assigned default department due to illegal argument : " + e.getMessage());
            department = DepartmentType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("id=" + id + " : assigned default department due to null value : " + e.getMessage());
            department = DepartmentType.DEFAULT;
        }
        LOGGER.debug("... " + department);
        return department;
    }

    public static RoomType takeRoom(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeRoom ...");
        RoomType room;
        String id = null;
        try {
            id = resultSet.getString("id");
            room = RoomType.valueOf(resultSet.getString("room"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("id=" + id + " : assigned default room due to illegal argument : " + e.getMessage());
            room = RoomType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("id=" + id + " : assigned default room due to null value : " + e.getMessage());
            room = RoomType.DEFAULT;
        }
        LOGGER.debug("Room = " + room.getRoomNumber() + " - " + room.getRoomName());
        return room;
    }

    public static RfidType takeType(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeType ...");
        RfidType type;
        try {
            type = RfidType.valueOf(resultSet.getString("type"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default RFID type due to illegal argument : " + e.getMessage());
            type = RfidType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default RFID type due to null value : " + e.getMessage());
            type = RfidType.DEFAULT;
        }
        LOGGER.debug("... " + type);
        return type;
    }

    public static ProtocolType takeProtocol(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeProtocol ...");
        ProtocolType protocol;
        try {
            protocol = ProtocolType.valueOf(resultSet.getString("protocol"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default RFID protocol due to illegal argument : " + e.getMessage());
            protocol = ProtocolType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default RFID protocol due to null value : " + e.getMessage());
            protocol = ProtocolType.DEFAULT;
        }
        LOGGER.debug("... " + protocol);
        return protocol;
    }

    public static Issue buildIssue(ResultSet resultSet) throws SQLException {
        LOGGER.debug("buildIssue ...");
        DateStamp issueDate = takeIssueDate(resultSet);
        DateStamp expirationDate = takeExpiration(resultSet);
        Issue issue = new Issue.Builder()
                .issueDate(issueDate)
                .expirationDate(expirationDate)
                .build();
        LOGGER.debug("... " + issue + " is built");
        return issue;
    }

    public static DateStamp takeIssueDate(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeIssueDate ...");
        DateStamp issueDate;
        try {
            issueDate = DateStamp.create(resultSet.getString("issueDate"));
        } catch (DateStampException e) {
            issueDate = DateStamp.createEmptyDate();
            LOGGER.debug("Assigned empty issue date due to exception: " + e.getMessage());
        }
        LOGGER.debug("... " + issueDate.getDate());
        return issueDate;
    }

    public static DateStamp takeExpiration(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeExpiration ...");
        DateStamp expirationDate;
        try {
            expirationDate = DateStamp.create(resultSet.getString("expirationDate"));
        } catch (DateStampException e) {
            expirationDate = DateStamp.createEmptyDate();
            LOGGER.debug("Assigned empty expiration date due to exception: " + e.getMessage());
        }
        LOGGER.debug("... " + expirationDate.getDate());
        return expirationDate;
    }

    public static RoleType takeRole(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeRole ...");
        RoleType role;
        try {
            role = RoleType.valueOf(resultSet.getString("role"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned UNREGISTERED user due to illegal argument : " + e.getMessage());
            role = RoleType.UNREGISTERED;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned UNREGISTERED user due to null value : " + e.getMessage());
            role = RoleType.UNREGISTERED;
        }
        LOGGER.debug("... " + role);
        return role;
    }

    public static DateStamp takeWorkingDay(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeBirthDate ...");
        DateStamp workingDay;
        String id = null;
        try {
            workingDay = DateStamp.create(resultSet.getString("dDate"));
        } catch (DateStampException e) {
            workingDay = DateStamp.createEmptyDate();
            LOGGER.debug("... assigned empty birth date due to exception: " + e.getMessage());
        }
        LOGGER.debug("... " + workingDay.getDate());
        return workingDay;
    }

    public static TimeStamp takeArriving(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeArriving ...");
        TimeStamp arriving;
        try {
            arriving = TimeStamp.create(resultSet.getString("Tmin"));
        } catch (TimeStampException e) {
            arriving = TimeStamp.createEmptyTime();
            LOGGER.debug("... assigned empty arriving time due to exception: " + e.getMessage());
        }
        LOGGER.debug("... " + arriving.getTime());
        return arriving;
    }

    public static TimeStamp takeLeaving(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeLeaving ...");
        TimeStamp leaving;
        try {
            leaving = TimeStamp.create(resultSet.getString("Tmax"));
        } catch (TimeStampException e) {
            leaving = TimeStamp.createEmptyTime();
            LOGGER.debug("... assigned empty leaving time due to exception: " + e.getMessage());
        }
        LOGGER.debug("... " + leaving.getTime());
        return leaving;
    }

    public static TimeStamp takeOfficeHours(ResultSet resultSet) throws SQLException {
        LOGGER.debug("takeOfficeHours ...");
        TimeStamp officeHour;
        try {
            officeHour = TimeStamp.create(resultSet.getString("officeHours"));
        } catch (TimeStampException e) {
            officeHour = TimeStamp.createEmptyTime();
            LOGGER.debug("... assigned empty office hour due to exception: " + e.getMessage());
        }
        LOGGER.debug("... " + officeHour.getTime());
        return officeHour;
    }

    /**
     * Secure password validation
     *
     * @param resultSet
     * @param password  - plain string password
     * @return
     * @throws SQLException
     * @throws SecurePasswordException
     */
    public static boolean isPasswordValid(ResultSet resultSet, String password) throws SQLException {
        LOGGER.debug("isPasswordValid ...");
        boolean isPasswordValid = false;
        if (resultSet.next()) {
            String hash = resultSet.getString("password");
            try {
                isPasswordValid = PasswordHash.validatePassword(password, hash);
            } catch (NoSuchAlgorithmException e) {
                LOGGER.debug("Password validation Error" + e.getMessage());
                throw new SecurePasswordException("Password validation Error" + e.getMessage());
            } catch (InvalidKeySpecException e) {
                LOGGER.debug("Password validation Error" + e.getMessage());
                throw new SecurePasswordException("Password validation Error" + e.getMessage());
            }
        }
        LOGGER.debug("... " + isPasswordValid);
        return isPasswordValid;
    }
}
