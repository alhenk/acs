package kz.trei.acs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by alhen on 4/23/14.
 */
public class ShowDashboard implements Action {
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return new ActionResult(ActionType.FORWARD,"dashboard");
    }
}
