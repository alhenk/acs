package kz.trei.acs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowCreateUserPage implements Action {
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return new ActionResult(ActionType.FORWARD,"create-user");
    }
}