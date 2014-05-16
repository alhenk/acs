package kz.trei.acs.action.general;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowDashboard implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowDashboard.class);
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        LOGGER.debug("..." );
        return new ActionResult(ActionType.FORWARD,"dashboard");
    }
}
