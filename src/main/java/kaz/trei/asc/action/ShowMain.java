package kaz.trei.asc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowMain implements Action {
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new ActionResult(ActionType.FORWARD,"/WEB-INF/jsp/main.jsp");
    }
}
