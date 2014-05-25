package kz.trei.acs.dao;

import kz.trei.acs.office.hr.Person;

public interface EmployeeDao  extends Dao<Person> {
    public void createTable() throws DaoException;
}
