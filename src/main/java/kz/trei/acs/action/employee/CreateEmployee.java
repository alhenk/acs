package kz.trei.acs.action.employee;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.office.hr.Person;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateEmployee implements Action {
    private static final Logger LOGGER = Logger.getLogger(CreateEmployee.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("execute ...");
        response.setCharacterEncoding("UTF-8");
        EmployeeUtil.createFieldAttributes(request);
        Person employee;
        if (isFormValid(request)) {
            LOGGER.debug("Form is valid, ready to build Employee");
            employee = EmployeeUtil.buildNewEmployee(request);
            DaoFactory daoFactory = DaoFactory.getFactory();
            EmployeeDao employeeDao = daoFactory.getEmployeeDao();
            try {
                employeeDao.create(employee);
            } catch (DaoException e) {
                request.setAttribute("error", e.getMessage());
                request.setAttribute("status", "form.employee.create.fail");
                LOGGER.error("... SQL INSERT statement exception : " + e.getMessage());
                return new ActionResult(ActionType.REDIRECT, "create-employee" + EmployeeUtil.fetchParameters(request));
            }
            EmployeeUtil.killFieldAttributes(request);
            request.setAttribute("status", "form.employee.create.success");
            LOGGER.debug("... create employee success");
            return new ActionResult(ActionType.REDIRECT, "employee-list" + EmployeeUtil.fetchParameters(request));
        }
        request.setAttribute("error", "form.employee.incomplete");
        LOGGER.debug("... create employee fail");
        return new ActionResult(ActionType.REDIRECT, "create-employee" + EmployeeUtil.fetchParameters(request));
    }

    private boolean isFormValid(HttpServletRequest request) {
        LOGGER.debug("isFormValid...");
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
