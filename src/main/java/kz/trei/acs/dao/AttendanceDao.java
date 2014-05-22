package kz.trei.acs.dao;

import kz.trei.acs.office.attendance.Attendance;
import kz.trei.acs.office.attendance.OfficeHour;
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.util.DateStamp;

import java.util.List;


public interface AttendanceDao extends Dao<Attendance> {
    public void createTable() throws DaoException;

    public void createView() throws DaoException;

    public List<OfficeHour> groupMonthlyReport(String year, String month, List<String> sorts) throws DaoException;

    public List<OfficeHour> individualMonthlyReport(String year, String month, Account1C account1C, List<String> sorts) throws DaoException;

    public List<OfficeHour> groupDailyReport(DateStamp dateStamp, List<String> sorts) throws DaoException;

    public List<OfficeHour> individualDailyReport(DateStamp dateStamp, Account1C account1C, List<String> sorts) throws DaoException;
}
