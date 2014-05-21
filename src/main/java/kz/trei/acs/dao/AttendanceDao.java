package kz.trei.acs.dao;

import kz.trei.acs.office.attendance.Attendance;
import kz.trei.acs.office.attendance.OfficeHour;
import kz.trei.acs.util.DateStamp;

import java.util.List;


public interface AttendanceDao extends Dao<Attendance> {
    public void createTable() throws DaoException;
    public void createView() throws DaoException;
    public List<OfficeHour> lateArrivalReportMonthly(String year, String month) throws DaoException;
    public List<OfficeHour> leavingBeforeReportMonthly(String year, String month) throws DaoException;
}
