package kz.trei.acs.dao;

import kz.trei.acs.office.attendance.Attendance;

/**
 * Created by Admin on 07.05.14.
 */
public interface AttendanceDao extends AbstractDao<Attendance> {
    public void createTable() throws DaoException;
}
