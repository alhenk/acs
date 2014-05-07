package kz.trei.acs.dao;

import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.Person;

/**
 * Created by Admin on 07.05.14.
 */
public interface EmployeeDao  extends AbstractDao<Person>{
    public void createTable() throws DaoException;
}
