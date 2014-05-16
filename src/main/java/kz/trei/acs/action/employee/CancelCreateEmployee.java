package kz.trei.acs.action.employee;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CancelCreateEmployee implements Action {
    private static final Logger LOGGER = Logger.getLogger(CancelCreateEmployee.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("positions");
        session.removeAttribute("departments");
        session.removeAttribute("rooms");
        session.removeAttribute("employee");
        LOGGER.debug("Create employee canceled");
        return new ActionResult(ActionType.FORWARD, "employee-list");
    }
}
