package kz.trei.acs.dao;

import kz.trei.acs.office.hr.Employee;

/**
 * Created by Admin on 07.05.14.
 */
public interface EmployeeDao  extends AbstractDao<Employee>{
    public void createTable() throws DaoException;
}
