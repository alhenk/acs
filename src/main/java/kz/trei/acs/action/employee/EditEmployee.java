package kz.trei.acs.action.employee;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.util.GetParameterException;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditEmployee implements Action {
    private static final Logger LOGGER = Logger.getLogger(EditEmployee.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        LOGGER.debug("...");
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        long id;
        try {
            id = EmployeeUtil.takeIdFromRequest(request);
        } catch (GetParameterException e) {
            LOGGER.error(e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error?status=error.parameter.id.invalid");
        }
        LOGGER.debug("id - " + id);
        if (isFormValid(request)) {
            Person employee = EmployeeUtil.buildEditedEmployeeFromRequest(request);
            try {
                employeeDao.update(employee);
                LOGGER.debug("employee -> " + employee);
            } catch (DaoException e) {
                LOGGER.error("SQL UPDATE EMPLOYEES exception : " + e.getMessage());
                request.setAttribute("status", "form.employee.create.fail");
                return new ActionResult(ActionType.REDIRECT, "edit-employee" + EmployeeUtil.fetchParameters(request));
            }
            EmployeeUtil.killFieldAttributes(request);
            request.setAttribute("status", "form.employee.edit.success");
            LOGGER.debug("Form employee edit success");
            return new ActionResult(ActionType.REDIRECT, "employee-list" + EmployeeUtil.fetchParameters(request));
        }
        request.setAttribute("error", "form.employee.incomplete");
        LOGGER.error("Form employee incomplete");
        return new ActionResult(ActionType.REDIRECT, "edit-employee" + EmployeeUtil.fetchParameters(request));
    }

    private boolean isFormValid(HttpServletRequest request) {
        return EmployeeUtil.isFirstNameValid(request)
                & EmployeeUtil.isPatronymValid(request)
                & EmployeeUtil.isLastNameValid(request)
                & EmployeeUtil.isBirthDateValid(request)
                & EmployeeUtil.isPositionValid(request)
                & EmployeeUtil.isDepartmentValid(request)
                & EmployeeUtil.isRoomValid(request)
                & EmployeeUtil.isTableIdValid(request)
                & EmployeeUtil.isUidValid(request);
    }
}
