package kz.trei.acs.dao;

import kz.trei.acs.office.attendance.Attendance;


public interface AttendanceDao extends Dao<Attendance> {
    public void createTable() throws DaoException;
}
