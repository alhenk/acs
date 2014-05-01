package kz.trei.acs.action;


import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowExceptionPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowExceptionPage.class);
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return new ActionResult(ActionType.FORWARD, "exception");
    }
}
