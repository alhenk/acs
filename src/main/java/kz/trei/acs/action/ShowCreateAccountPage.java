package kz.trei.acs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Admin on 23.04.14.
 */
public class ShowCreateAccountPage implements Action {
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return new ActionResult(ActionType.FORWARD,"create-account");
    }
}
