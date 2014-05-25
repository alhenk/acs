package kz.trei.acs.dao.h2;

import kz.trei.acs.dao.*;


public class DaoFactoryH2 extends DaoFactory {
    @Override
    public UserDao getUserDao() {
        return new UserDaoH2();
    }

    @Override
    public EmployeeDao getEmployeeDao() {
        return new EmployeeDaoH2();
    }

    @Override
    public RfidTagDao getRfidTagDao() {
        return new RfidTagDaoH2();
    }

    @Override
    public AttendanceDao getAttendanceDao() {
        return new AttendanceDaoH2();
    }
}
