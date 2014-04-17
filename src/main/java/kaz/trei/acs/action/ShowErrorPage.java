package kaz.trei.acs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowErrorPage implements Action {
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new ActionResult(ActionType.FORWARD, "/WEB-INF/jsp/errorPage.jsp");
    }
}
