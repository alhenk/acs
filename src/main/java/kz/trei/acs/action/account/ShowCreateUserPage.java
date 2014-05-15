package kz.trei.acs.action.account;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.user.RoleType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


public class ShowCreateUserPage implements Action {
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        List<String> roles = new ArrayList<String>();
        for (RoleType type : RoleType.values()) {
            roles.add(type.toString());
        }
        session.setAttribute("roles", roles);
        return new ActionResult(ActionType.FORWARD, "create-user");
    }
}
