package kz.trei.acs.action;

import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CreateEmployee implements Action {
    private static final Logger LOGGER = Logger.getLogger(CreateEmployee.class);
    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
    }
}
