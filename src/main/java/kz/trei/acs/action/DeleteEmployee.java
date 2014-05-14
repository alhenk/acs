package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DeleteEmployee implements Action{
    private static final Logger LOGGER = Logger.getLogger(DeleteEmployee.class);

    static {
        PropertyManager.load("configure.properties");
    }
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        long id;
        try {
            id = Long.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.error("Wrong id parameter " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "employee-list?status=id.parameter.error");
        }
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        try {
            employeeDao.delete(id);
        } catch (DaoException e) {
            LOGGER.error("DAO delete error " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "employee-list?status=delete.employee.fail");
        }
        LOGGER.debug("The employee is deleted successfully");
        return new ActionResult(ActionType.REDIRECT, "employee-list?status=delete.employee.success");
    }
}
