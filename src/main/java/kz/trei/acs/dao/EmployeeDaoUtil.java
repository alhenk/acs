package kz.trei.acs.dao;

import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.rfid.UidFormatException;
import kz.trei.acs.office.structure.*;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.DateStampException;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Util class for Create and Update Employee DAO methods
 */
public final class EmployeeDaoUtil {
    private static final Logger LOGGER = Logger.getLogger(EmployeeDaoUtil.class);
    private EmployeeDaoUtil(){}


    public static RfidTag takeRfidTagFromResult(ResultSet rs) throws SQLException {
        RfidTag rfidTag = new RfidTag();
        String id = null;
        try {
            id = rs.getString("id");
            rfidTag.setUid(rs.getString("uid"));
        } catch (UidFormatException e) {
            rfidTag.setEmptyUid();
            LOGGER.debug("Employee id=" + id + " : assigned empty UID \"00000000\" date due to exception: " + e.getMessage());
        }
        LOGGER.debug("RFID tag = " + rfidTag);
        return rfidTag;
    }

    public static Account1C takeAccount1CFromResult(ResultSet rs) throws SQLException {
        Account1C account1C;
        String id = null;
        try {
            id = rs.getString("id");
            account1C = Account1C.createId(rs.getString("tableId"));
        } catch (Account1CException e) {
            account1C = Account1C.defaultId();
            LOGGER.debug("Employee id=" + id + " : assigned default table ID due to exception: " + e.getMessage());
        }
        LOGGER.debug("Table ID = " + account1C.getTableId());
        return account1C;
    }

    public static DateStamp takeBirthDateFromResult(ResultSet rs) throws SQLException {
        DateStamp birthDate;
        String id = null;
        try {
            id = rs.getString("id");
            birthDate = DateStamp.create(rs.getString("birthDate"));
        } catch (DateStampException e) {
            birthDate = DateStamp.createEmptyDate();
            LOGGER.debug("Employee id=" + id + " : assigned empty birth date due to exception: " + e.getMessage());
        }
        LOGGER.debug("Birth date = "+birthDate.getDate());
        return birthDate;
    }

    public static PositionType takePositionFromResult(ResultSet rs) throws SQLException {
        PositionType position;
        String id = null;
        try {
            id = rs.getString("id");
            position = PositionType.valueOf(rs.getString("jobPosition"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Employee id=" + id + " : assigned default position due to illegal argument : " + e.getMessage());
            position = PositionType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Employee id=" + id + " : assigned default position due to null value : " + e.getMessage());
            position = PositionType.DEFAULT;
        }
        LOGGER.debug("Position = "+position);
        return position;
    }

    public static DepartmentType takeDepartmentFromResult(ResultSet rs) throws SQLException {
        DepartmentType department;
        String id = null;
        try {
            id = rs.getString("id");
            department = DepartmentType.valueOf(rs.getString("department"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Employee id=" + id + " : assigned default department due to illegal argument : " + e.getMessage());
            department = DepartmentType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Employee id=" + id + " : assigned default department due to null value : " + e.getMessage());
            department = DepartmentType.DEFAULT;
        }
        LOGGER.debug("Department = "+department);
        return department;
    }

    public static RoomType takeRoomFromResult(ResultSet rs) throws SQLException {
        RoomType room;
        String id = null;
        try {
            id = rs.getString("id");
            room = RoomType.valueOf(rs.getString("room"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Employee id=" + id + " : assigned default room due to illegal argument : " + e.getMessage());
            room = RoomType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Employee id=" + id + " : assigned default room due to null value : " + e.getMessage());
            room = RoomType.DEFAULT;
        }
        LOGGER.debug("Room = " + room.getRoomNumber() + " - " + room.getRoomName());
        return room;
    }
}
