package kz.trei.acs.action.employee;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.exception.GetParameterException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DeleteEmployee implements Action {
    private static final Logger LOGGER = Logger.getLogger(DeleteEmployee.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("execute ...");
        response.setCharacterEncoding("UTF-8");
        long id;
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        try {
            id = EmployeeUtil.takeIdFrom(request);
            if (id == 0 || id == 1) {
                throw new GetParameterException("trying to delete the read-only record");
            }
            employeeDao.delete(id);
        } catch (DaoException e) {
            request.setAttribute("status", "delete.employee.fail");
            LOGGER.error("... DAO delete error " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "employee-list" + EmployeeUtil.fetchParameters(request));
        } catch (GetParameterException e) {
            request.setAttribute("status", "error.parameter.id.invalid");
            LOGGER.error("..." + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "employee-list" + EmployeeUtil.fetchParameters(request));
        }
        request.setAttribute("status", "delete.employee.success");
        LOGGER.debug("... the employee is deleted successfully");
        return new ActionResult(ActionType.REDIRECT, "employee-list" + EmployeeUtil.fetchParameters(request));
    }
}
