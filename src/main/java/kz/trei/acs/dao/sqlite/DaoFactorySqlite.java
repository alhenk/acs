package kz.trei.acs.dao.sqlite;

import kz.trei.acs.dao.*;

/**
 * Created by alhen on 4/25/14.
 */
public class DaoFactorySqlite extends DaoFactory {
    @Override
    public UserDao getUserDao() {
        return new UserDaoSqlite();
    }

    @Override
    public EmployeeDao getEmployeeDao() {
        return new EmployeeDaoSqlite();
    }

    @Override
    public RfidTagDao getRfidTagDao() {
        return new RfidTagDaoSqlite();
    }

    @Override
    public AttendanceDao getAttendanceDao() {
        return new AttendanceDaoSqlite();
    }
}
