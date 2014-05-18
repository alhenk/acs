package kz.trei.acs.action.employee;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CancelEditEmployee implements Action {
    private static final Logger LOGGER = Logger.getLogger(CancelEditEmployee.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        EmployeeUtil.killFieldAttributes(request);
        LOGGER.debug("Edit employee canceled");
        return new ActionResult(ActionType.FORWARD, "employee-list");
    }
}
